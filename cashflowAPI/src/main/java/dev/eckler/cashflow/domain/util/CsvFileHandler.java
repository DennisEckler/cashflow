package dev.eckler.cashflow.domain.util;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CsvFileHandler {

    private static final Logger logger = LoggerFactory.getLogger(CsvFileHandler.class);
    private final ParserUtil parser;

    CsvFileHandler(ParserUtil parser) {
        this.parser = parser;
    }

    public int identifyParsableRow(List<String> lines, int dateIdx, int amountIdx) {
        int skipLines = 0;
        int maxIdx = Math.max(dateIdx, amountIdx);
        for (String line : lines) {
            String[] col = line.split(";");
            try {
                if (col.length > maxIdx) {
                    parser.parseDate(col[dateIdx]);
                    parser.parseAmount(col[amountIdx]);
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

}
