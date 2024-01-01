package dev.eckler.cashflow.init;

import dev.eckler.cashflow.category.Category;
import dev.eckler.cashflow.category.CategoryRepository;
import dev.eckler.cashflow.identifier.Identifier;
import dev.eckler.cashflow.identifier.IdentifierRepository;

import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;

import dev.eckler.cashflow.transaktion.TransaktionRepository;
import dev.eckler.cashflow.transaktion.TransaktionService;

@Configuration
@Profile("Demo")
class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(TransaktionRepository transaktionRepository,
      TransaktionService transaktionService,
      IdentifierRepository identifierRepository,
      CategoryRepository categoryRepository,
      ResourceLoader resourceloader) {

    return args -> {
//      try {
//        Resource resource = resourceloader.getResource("classpath:fakeData.csv");
//        InputStream stream = resource.getInputStream();
//        List<Transaktion> transaktions = new ArrayList<>();
//        transaktions.addAll(transaktionService.convertCsvToTransaktionListInit(stream));
//        transaktionRepository.saveAll(transaktions);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
      Category category1 = new Category("Lebensmittel", "akldsjfkfadsofdasftue");
      Category category2 = new Category("Miete", "akldsjfkfadsofdasftue");
      Category category3 = new Category("Miete", "cvuiocxvue");
      categoryRepository.save(category1);
      categoryRepository.save(category2);
      categoryRepository.save(category3);
      Set<Category> categoriesForUser = categoryRepository.findAllByUserID("akldsjfkfadsofdasftue");
      Identifier identifier1 = new Identifier("Wez", categoriesForUser.stream().filter(e -> e.getLabel().equals("Lebensmittel")).findFirst().orElse(null));
      identifierRepository.save(identifier1);
      categoryRepository.deleteAll();

    };
  }

}
