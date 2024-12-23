package dev.eckler.cashflow.domain.transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.eckler.cashflow.domain.identifier.IdentifierService;
import dev.eckler.cashflow.shared.FileStructure;

@RestController
@RequestMapping(path = "/api/transaction")
public class TransactionController {

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
  public List<Transaction> getTransaction(@AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    return transactionRepository.findAllByIdentifierIsNullAndUserID(userID);
  }

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFile(
      @RequestParam("file") MultipartFile csvFile,
      @RequestParam("fileStructure") String fileStructureJson, @AuthenticationPrincipal Jwt jwt) {
    try {
      String userID = jwt.getSubject();

      InputStream stream = csvFile.getInputStream();
      FileStructure fileStructure = new ObjectMapper().readValue(fileStructureJson, FileStructure.class);

      List<Transaction> transactions = transactionService.parseCsv(stream, userID, fileStructure);
      transactionRepository.saveAll(transactions);
      logger.info("FileUpload done");
      return new ResponseEntity<>("File upload successfully", HttpStatus.OK);

    } catch (IOException e) {
      logger.error("Cant handle this case");
      return new ResponseEntity<>("Check your File or JSON", HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/categorize")
  public ResponseEntity<String> categorizeTransactions(
      @RequestBody List<Transaction> patchValues) {
    patchValues.forEach(entry -> transactionRepository.findById(entry.getId())
        .ifPresentOrElse(transaction -> transaction.setIdentifier(
            identifierService.findIdentifierByID(entry.getIdentifier().getId())),
            () -> logger.info("Cant find Transaction with ID: {}", entry)));
    transactionRepository.saveAll(patchValues);
    return ResponseEntity.ok("updated values successfully");
  }

  @GetMapping("/recategorize")
  public ResponseEntity<String> recategorize(@AuthenticationPrincipal Jwt jwt) {
    String userID = jwt.getSubject();
    transactionService.recategorize(userID);
    return ResponseEntity.accepted().body("recategorize done");
  }

}
