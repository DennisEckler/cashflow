package dev.eckler.cashflow.transaktion;

import static dev.eckler.cashflow.jwt.CustomJwt.getUserId;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransaktionController {

  @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
  private String issuer;
  private final TransaktionRepository transaktionRepository;
  private final TransaktionService transaktionService;
  private final Logger logger = LoggerFactory.getLogger(TransaktionController.class);


  public TransaktionController(TransaktionRepository transaktionRepository,
      TransaktionService transaktionService) {
    this.transaktionRepository = transaktionRepository;
    this.transaktionService = transaktionService;
  }

//  @GetMapping("/get-empty-category-entries")
//  @PreAuthorize("hasAuthority('ROLE_user')")
//  public Iterable<Transaktion> getTransaktion() {
//    return transaktionRepository.findAllByCategory(LEER);
//  }


  @PostMapping("/file-upload")
  @PreAuthorize("hasAuthority('ROLE_user')")
  public void uploadFile(@RequestHeader("Authorization") String bearerRequest,
      @RequestParam("file") MultipartFile csvFile) throws IOException {
    String userID = getUserId(bearerRequest, issuer);
    InputStream stream = csvFile.getInputStream();
    List<Transaktion> transaktions = new ArrayList<>();
    transaktions.addAll(transaktionService.convertCsvToTransaktionList(stream, userID));
    transaktionRepository.saveAll(transaktions);
    logger.info("FileUpload done");
  }


//  @PostMapping("/file-upload-init")
//  public void uploadInitFile(@RequestParam("file") MultipartFile csvFile) throws IOException {
//    InputStream stream = csvFile.getInputStream();
//    List<Transaktion> transaktions = new ArrayList<>();
////    transaktions.addAll(this.transaktionService.convertCsvToTransaktionListInit(stream));
//    transaktionRepository.saveAll(transaktions);
//  }

  @PatchMapping("/categorize")
  public ResponseEntity<?> categorizeTransaktions(@RequestBody List<Transaktion> patchValues) {

    patchValues.forEach(entry -> {
      Optional<Transaktion> transaktionFromDB = this.transaktionRepository.findById(entry.getId());
      if (transaktionFromDB.isPresent()) {
//        transaktionFromDB.get().setCategory(entry.getCategory());
        transaktionRepository.save(transaktionFromDB.get());
      }

    });
    return ResponseEntity.ok("updated values successfully");
  }

}
