package dev.eckler.cashflow.domain.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.eckler.cashflow.domain.category.Category;
import dev.eckler.cashflow.domain.category.CategoryRepository;
import dev.eckler.cashflow.domain.identifier.Identifier;
import dev.eckler.cashflow.domain.identifier.IdentifierService;
import dev.eckler.cashflow.domain.util.CsvFileHandler;
import dev.eckler.cashflow.domain.util.ParserUtil;
import dev.eckler.cashflow.exception.PeriodExistsException;
import dev.eckler.cashflow.openapi.model.FileDescription;
import dev.eckler.cashflow.openapi.model.TransactionRequest;
import dev.eckler.cashflow.openapi.model.TransactionResponse;

@Service
public class TransactionService {

    private final TransactionRepository tr;
    private final CategoryRepository cr;
    private final IdentifierService is;
    private final CsvFileHandler csvFileHandler;
    private final ParserUtil parser;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private TransactionService(TransactionRepository tr, CategoryRepository cr, CsvFileHandler csvFileHandler,
            ParserUtil parser, IdentifierService is) {
        this.tr = tr;
        this.cr = cr;
        this.csvFileHandler = csvFileHandler;
        this.parser = parser;
        this.is = is;
    }

    List<TransactionResponse> findAllByIdentifierIsNullAndUserID(String userID) {
        List<Transaction> transactions = tr.findAllByIdentifierIsNullAndUserID(userID);
        return TransactionMapper.transactionToTransactionResponse(transactions);
    }

    void createTransactions(MultipartFile file, FileDescription fileDescription, String userID) {
        try {
            InputStream stream = file.getInputStream();
            List<Transaction> transactions = parseCsv(stream, userID, fileDescription);
            tr.saveAll(transactions);
            logger.info("FileUpload done");
        } catch (IOException e) {
            logger.error("Cant handle this case");
        }

    }

    void categorizeTransactions(List<TransactionRequest> transactions) {
        transactions.forEach(transactionRequest -> {
            long identifierId = transactionRequest.getIdentifier().getId();
            Identifier newIdentifier = is.findIdentifierByID(identifierId);
            tr.findById(transactionRequest.getId())
                    .ifPresentOrElse(persistedTransaction -> {
                        persistedTransaction.setIdentifier(newIdentifier);
                        tr.save(persistedTransaction);
                    }, () -> logger.info("Cant find Transaction with ID: {}", transactionRequest));
        });
    }

    List<Transaction> parseCsv(InputStream is, final String USERID, FileDescription fs) {
        List<Transaction> transactions = new ArrayList<>();
        List<Category> categories = cr.findAllByUserID(USERID);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            throwIfPeriodAlreadyExist(fs.getYear(), fs.getMonth());
            List<String> lines = reader.lines().toList();
            int skipRowsCount = csvFileHandler.identifyParsableRow(lines, fs.getDateIdx(), fs.getAmountIdx());

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

    private Transaction createTransaction(String USERID, String row, FileDescription fs,
            List<Category> categories) {
        try {
            String[] col = row.split(";");
            LocalDate date = parser.parseDate(col[fs.getDateIdx()]);
            BigDecimal amount = parser.parseAmount(col[fs.getAmountIdx()]);
            String source = col[fs.getSourceIdx()];
            String purpose = col[fs.getPurposeIdx()];
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
