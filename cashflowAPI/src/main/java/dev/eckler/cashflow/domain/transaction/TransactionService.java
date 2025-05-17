package dev.eckler.cashflow.domain.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.exception.PeriodExistsException;
import dev.eckler.cashflow.openapi.model.TransactionResponse;
import dev.eckler.cashflow.shared.FileStructure;

@Service
public class TransactionService {

    private final TransactionRepository tr;
    private final CategoryRepository cr;
    // private final CsvParser, Calculator, or something like that
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private TransactionService(TransactionRepository tr, CategoryRepository cr) {
        this.tr = tr;
        this.cr = cr;
    }

    List<TransactionResponse> findAllByIdentifierIsNullAndUserID(String userID) {
        // TransactionMapper
        List<Transaction> transactions = tr.findAllByIdentifierIsNullAndUserID(userID);
        return null;
    }

    List<Transaction> parseCsv(InputStream is, final String USERID, FileStructure fs) {
        List<Transaction> transactions = new ArrayList<>();
        List<Category> categories = cr.findAllByUserID(USERID);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            throwIfPeriodAlreadyExist(fs.year(), fs.month());
            List<String> lines = reader.lines().toList();
            int skipRowsCount = identifyParsableRow(lines, fs.dateIdx(), fs.amountIdx());

            lines.stream().skip(skipRowsCount).forEach(row -> {
                Transaction transaction = createTransaction(USERID, row, fs, categories);
                transactions.add(transaction);
            });

            return transactions;
        } catch (IOException | PeriodExistsException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwIfPeriodAlreadyExist(String year, String month) throws PeriodExistsException {
        if (tr.getNumberOfYearMonthMatches(year, month) > 0) {
            throw new PeriodExistsException(
                    "This Period exists with year: " + year + " and month: " + month);
        }
    }

    private int identifyParsableRow(List<String> lines, int dateIdx, int amountIdx) {
        int skipLines = 0;
        int maxIdx = Math.max(dateIdx, amountIdx);
        for (String line : lines) {
            String[] col = line.split(";");
            try {
                if (col.length > maxIdx) {
                    parseDate(col[dateIdx]);
                    parseAmount(col[amountIdx]);
                    logger.info("skipped {} lines", skipLines);
                    return skipLines;
                }
                skipLines++;
            } catch (DateTimeParseException | ParseException e) {
                skipLines++;
            }
        }
        return skipLines;
    }

    private LocalDate parseDate(String date) throws DateTimeParseException {
        String format = "dd.MM.yyyy";
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
    }

    public static BigDecimal parseAmount(String amount) throws ParseException {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,###.00", symbols);
        return new BigDecimal(String.valueOf(df.parse(amount)));
    }

    private Transaction createTransaction(String USERID, String row, FileStructure fs,
            List<Category> categories) {
        try {
            String[] col = row.split(";");
            LocalDate date = parseDate(col[fs.dateIdx()]);
            BigDecimal amount = parseAmount(col[fs.amountIdx()]);
            String source = col[fs.sourceIdx()];
            String purpose = col[fs.purposeIdx()];
            Identifier identifier = categorize(categories, source, purpose);
            return new Transaction(date, amount, USERID, purpose, source,
                    identifier);
        } catch (ParseException e) {
            logger.info("Error in creating Transaction");
            throw new RuntimeException();
        }
    }

    private Identifier categorize(List<Category> categories, String source, String purpose) {
        for (Category category : categories) {
            for (Identifier identifier : category.getIdentifier()) {
                if (source.trim().toLowerCase()
                        .contains(identifier.getLabel().trim().toLowerCase())
                        || purpose.trim().toLowerCase()
                                .contains(identifier.getLabel().trim().toLowerCase())) {
                    return identifier;
                }
            }
        }
        return null;
    }

    void recategorize(String USERID) {
        List<Transaction> transactions = tr.findAllByUserID(USERID);
        List<Category> categories = cr.findAllByUserID(USERID);
        transactions.forEach(t -> {
            if (t.getIdentifier() == null) {
                t.setIdentifier(categorize(categories, t.getSource(), t.getPurpose()));
            }
        });
        tr.saveAll(transactions);
    }

}
