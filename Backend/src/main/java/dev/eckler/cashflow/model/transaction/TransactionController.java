package dev.eckler.cashflow.model.transaction;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;
  private final TransactionRepository transactionRepository;
  private final TransactionService transaktionService;
  private final Logger logger = LoggerFactory.getLogger(TransactionController.class);


  public TransactionController(TransactionRepository transactionRepository,
      TransactionService transaktionService) {
    this.transactionRepository = transactionRepository;
    this.transaktionService = transaktionService;
  }

  @GetMapping("/uncategorized-transactions")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public List<Transaction> getTransaktion() {
    return transactionRepository.findAllByIdentifierIsNull();
  }


  @PostMapping("/file-upload")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public void uploadFile(@RequestHeader("Authorization") String bearerRequest,
      @RequestParam("file") MultipartFile csvFile) throws IOException {
    String userID = getUserId(bearerRequest, issuer);
    InputStream stream = csvFile.getInputStream();
    List<Transaction> transactions = new ArrayList<>();
    transactions.addAll(transaktionService.convertCsvToTransaktionList(stream, userID));
    transactionRepository.saveAll(transactions);
    logger.info("FileUpload done");
  }

  @PatchMapping("/categorize")
  public ResponseEntity<String> categorizeTransaktions(@RequestBody List<Transaction> patchValues) {

    patchValues.forEach(entry -> {
      Optional<Transaction> transaktionFromDB = this.transactionRepository.findById(entry.getTransactionID());
      if (transaktionFromDB.isPresent()) {
//        transaktionFromDB.get().setCategory(entry.getCategory());
        transactionRepository.save(transaktionFromDB.get());
      }

    });
    return ResponseEntity.ok("updated values successfully");
  }

}
