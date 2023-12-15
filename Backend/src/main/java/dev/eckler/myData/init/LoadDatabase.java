package dev.eckler.myData.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.eckler.myData.transaktion.Transaktion;
import dev.eckler.myData.transaktion.TransaktionRepository;
import dev.eckler.myData.transaktion.TransaktionService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(TransaktionRepository transaktionRepository, TransaktionService transaktionService,
      ResourceLoader resourceloader) {

    return args -> {
      try {
        Resource resource = resourceloader.getResource("classpath:fakeDate.csv");
        InputStream stream = resource.getInputStream();
        List<Transaktion> transaktions = new ArrayList<>();
        transaktions.addAll(transaktionService.convertCsvToTransaktionListInit(stream));
        transaktionRepository.saveAll(transaktions);
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
  }

}
