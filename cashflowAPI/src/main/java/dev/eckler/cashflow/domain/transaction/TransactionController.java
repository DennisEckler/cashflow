package dev.eckler.cashflow.domain.transaction;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.eckler.cashflow.domain.util.JwtUtil;
import dev.eckler.cashflow.openapi.api.TransactionApi;
import dev.eckler.cashflow.openapi.model.CashflowErrorResponse;
import dev.eckler.cashflow.openapi.model.FileDescription;
import dev.eckler.cashflow.openapi.model.TransactionRequest;
import dev.eckler.cashflow.openapi.model.TransactionResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/api")
public class TransactionController implements TransactionApi {

    private final TransactionService ts;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionRepository transactionRepository, TransactionService ts, JwtUtil jwtUtil) {
        this.ts = ts;
        this.jwtUtil = jwtUtil;
    }

    @ExceptionHandler({ TransactionNotFoundException.class })
    public ResponseEntity<CashflowErrorResponse> error(TransactionNotFoundException ex) {
        CashflowErrorResponse error = new CashflowErrorResponse();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(error.getStatusCode()).body(error);
    }

    @Override
    public ResponseEntity<Void> categorizeTransactions(@Valid List<TransactionRequest> transactionRequest) {
        logger.debug("categorizeTransactions");
        ts.categorizeTransactions(transactionRequest);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createTransactions(MultipartFile file, @Valid FileDescription fileDescription) {
        logger.debug("createTransactions");
        String userID = jwtUtil.readSubjectFromSecurityContext();
        ts.createTransactions(file, fileDescription, userID);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<TransactionResponse>> getUncategorizedTransactions() {
        String userID = jwtUtil.readSubjectFromSecurityContext();
        logger.info(String.join("User: ", userID, " is calling /transaction/uncategorized"));
        return ResponseEntity.ok(ts.findAllByIdentifierIsNullAndUserID(userID));
    }

    @Override
    public ResponseEntity<Void> recategorizeTransactions() {
        String userID = jwtUtil.readSubjectFromSecurityContext();
        ts.recategorize(userID);
        return ResponseEntity.noContent().build();
    }

}
