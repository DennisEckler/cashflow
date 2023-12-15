package dev.eckler.myData.transaktion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import dev.eckler.myData.shared.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransaktionController {

  private final TransaktionRepository transaktionRepository;
  private final TransaktionService transaktionService;
  private static final Category LEER = Category.LEER;

  public TransaktionController(TransaktionRepository transaktionRepository, TransaktionService transaktionService) {
    this.transaktionRepository = transaktionRepository;
    this.transaktionService = transaktionService;
  }

  @GetMapping("/get-empty-category-entries")
  public Iterable<Transaktion> getTransaktion() {
    return transaktionRepository.findAllByCategory(LEER);
  }

  @GetMapping("/show-transaktions")
  public Iterable<Transaktion> getTransaktions() {
    return transaktionRepository.findAllByCategoryNot(LEER);
  }

  @PostMapping("/file-upload")
  public void uploadFile(@RequestParam("file") MultipartFile csvFile) throws IOException {
    InputStream stream = csvFile.getInputStream();
    List<Transaktion> transaktions = new ArrayList<>();
    transaktions.addAll(this.transaktionService.convertCsvToTransaktionList(stream));
    transaktionRepository.saveAll(transaktions);
  }

  @PostMapping("/file-upload-init")
  public void uploadInitFile(@RequestParam("file") MultipartFile csvFile) throws IOException {
    InputStream stream = csvFile.getInputStream();
    List<Transaktion> transaktions = new ArrayList<>();
    transaktions.addAll(this.transaktionService.convertCsvToTransaktionListInit(stream));
    transaktionRepository.saveAll(transaktions);
  }

  @PatchMapping("/categorize")
  public ResponseEntity<?> categorizeTransaktions(@RequestBody List<TransaktionDTO> patchValues) {

    patchValues.forEach(entry -> {
      Optional<Transaktion> transaktionFromDB = this.transaktionRepository.findById(entry.getId());
      if (transaktionFromDB.isPresent()) {
        transaktionFromDB.get().setCategory(entry.getCategory());
        transaktionRepository.save(transaktionFromDB.get());
      }

    });
    return ResponseEntity.ok("updated values successfully");
  }

}
