package dev.eckler.myData.transaktion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransaktionController {

  private final TransaktionRepository transaktionRepository;
  private final TransaktionService transaktionService;

  public TransaktionController(TransaktionRepository transaktionRepository, TransaktionService transaktionService) {
    this.transaktionRepository = transaktionRepository;
    this.transaktionService = transaktionService;
  }

  @GetMapping("/update-list")
  public Iterable<Transaktion> getTransaktion() {
    System.out.println(transaktionRepository.findAllByCategoryIsNull());
    return transaktionRepository.findAllByCategoryIsNull();
  }

  @GetMapping("/show-transaktions")
  public Iterable<Transaktion> getTransaktions() {
    return transaktionRepository.findAll();
  }

  @PostMapping("/file-upload")
  public void uploadFile(@RequestParam("file") MultipartFile csvFile) throws IOException {
    InputStream stream = csvFile.getInputStream();
    List<Transaktion> transaktions = new ArrayList<>();
    transaktions.addAll(this.transaktionService.convertCsvToTransaktionList(stream));
    transaktionRepository.saveAll(transaktions);
  }

}
