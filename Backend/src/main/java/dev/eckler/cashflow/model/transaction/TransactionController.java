package dev.eckler.cashflow.model.transaction;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import dev.eckler.cashflow.model.identifier.IdentifierService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/transaction")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;
  private final TransactionRepository transactionRepository;
  private final TransactionService transactionService;
  private final IdentifierService identifierService;
  private final Logger logger = LoggerFactory.getLogger(TransactionController.class);


  public TransactionController(TransactionRepository transactionRepository,
      TransactionService transactionService,
      IdentifierService identifierService) {
    this.transactionRepository = transactionRepository;
    this.transactionService = transactionService;
    this.identifierService = identifierService;
  }

  @GetMapping("/uncategorized")
  public List<Transaction> getTransaction() {
    return transactionRepository.findAllByIdentifierIsNull();
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(
      @RequestHeader("Authorization") String bearerRequest,
      @RequestParam("file") MultipartFile csvFile,
      @RequestParam("columnIndex") String columnIndex) {
    try {

      JSONObject json = new JSONObject(columnIndex);
      String userID = getUserId(bearerRequest, issuer);
      InputStream stream = csvFile.getInputStream();

      List<Transaction> transactions = transactionService.parseCsv(stream, userID, json);
      transactionRepository.saveAll(transactions);
      logger.info("FileUpload done");
      return new ResponseEntity<>("File upload successfully", HttpStatus.OK);

    } catch (JSONException | IOException e) {
      logger.error("Cant handle this case");
      return new ResponseEntity<>("Check your File or JSON", HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/categorize")
  public ResponseEntity<String> categorizeTransactions(
      @RequestBody List<Transaction> patchValues) {
    patchValues.forEach(entry -> transactionRepository.findById(entry.getTransactionID())
        .ifPresentOrElse(transaction -> transaction.setIdentifier(
                identifierService.findIdentifierByID(entry.getIdentifier().getIdentifierID())),
            () -> logger.info("Cant find Transaction with ID: {}", entry)));
    transactionRepository.saveAll(patchValues);

    return ResponseEntity.ok("updated values successfully");
  }

}
