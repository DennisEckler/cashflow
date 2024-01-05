package dev.eckler.cashflow.init;

import dev.eckler.cashflow.model.category.Category;
import dev.eckler.cashflow.model.category.CategoryRepository;
import dev.eckler.cashflow.model.identifier.Identifier;
import dev.eckler.cashflow.model.identifier.IdentifierRepository;

import dev.eckler.cashflow.model.transaktion.Transaktion;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import dev.eckler.cashflow.model.transaktion.TransaktionRepository;
import dev.eckler.cashflow.model.transaktion.TransaktionService;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("Demo")
class LoadDatabase {

  static final String USERID = "5276b7e0-4c55-49d7-bf52-46e34ab673cb";

  @Bean
  @Transactional
  CommandLineRunner initDatabase(TransaktionRepository transaktionRepository,
      TransaktionService transaktionService,
      IdentifierRepository identifierRepository,
      CategoryRepository categoryRepository,
      ResourceLoader resourceloader) {

    return args -> {
      categoryRepository.saveAll(getCategories());
      initIdentifier(identifierRepository, categoryRepository);
      try {
        Resource resource = resourceloader.getResource("classpath:fakeData.csv");
        InputStream stream = resource.getInputStream();
        List<Transaktion> transaktions = new ArrayList<>();
        transaktions.addAll(transaktionService.convertCsvToTransaktionListInit(stream, USERID));
        transaktionRepository.saveAll(transaktions);
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
  }

  private List<Category> getCategories() {

    return Arrays.asList(
        new Category("dennis",
            USERID),
        new Category("sveti",
            USERID),
        new Category("gutschrift",
            USERID),
        new Category("miete",
            USERID),
        new Category("strom",
            USERID),
        new Category("internet",
            USERID),
        new Category("handy",
            USERID),
        new Category("versicherung",
            USERID),
        new Category("gez",
            USERID),
        new Category("abonnement",
            USERID),
        new Category("lebensmittel",
            USERID),
        new Category("haushaltsmittel",
            USERID),
        new Category("kleidung",
            USERID),
        new Category("mobilitaet",
            USERID),
        new Category("geschenke",
            USERID),
        new Category("ausgehen",
            USERID),
        new Category("sonstiges",
            USERID)
    );
  }

  private void initIdentifier(IdentifierRepository identifierRepository,
      CategoryRepository categoryRepository) {
    identifierRepository.saveAll(Arrays.asList(
        new Identifier("bertelsmann", categoryRepository.findByLabel("dennis")),
        new Identifier("abas", categoryRepository.findByLabel("dennis")),
        new Identifier("neschen", categoryRepository.findByLabel("dennis")),
        new Identifier("mait", categoryRepository.findByLabel("dennis")),
        new Identifier("arvato", categoryRepository.findByLabel("dennis")),
        new Identifier("kammann", categoryRepository.findByLabel("sveti")),
        new Identifier("rainer klenke", categoryRepository.findByLabel("miete")),
        new Identifier("logemann vermoegensverwaltung", categoryRepository.findByLabel("miete")),
        new Identifier("e.on energie", categoryRepository.findByLabel("strom")),
        new Identifier("stromio gmbh", categoryRepository.findByLabel("strom")),
        new Identifier("vodafone", categoryRepository.findByLabel("internet")),
        new Identifier("telefonica", categoryRepository.findByLabel("handy")),
        new Identifier("drillisch online gmbh", categoryRepository.findByLabel("handy")),
        new Identifier("lvm landw.versicherungsverein", categoryRepository.findByLabel("versicherung")),
        new Identifier("beitragsservice von ard", categoryRepository.findByLabel("gez")),
        new Identifier("spotify", categoryRepository.findByLabel("abonnement")),
        new Identifier("igm herford", categoryRepository.findByLabel("abonnement")),
        new Identifier("netflix", categoryRepository.findByLabel("abonnement")),
        new Identifier("BoRa Sports GmbH", categoryRepository.findByLabel("abonnement")),
        new Identifier("FitX Deutschland GmbH", categoryRepository.findByLabel("abonnement")),
        new Identifier("wez", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("LIDL", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("E-CENTER", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("KAUFLAND", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("ALDI", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("Combi Verbrauchermarkt", categoryRepository.findByLabel("lebensmittel")),
        new Identifier("rossmann", categoryRepository.findByLabel("haushaltsmittel")),
        new Identifier("DM DROGERIEMARKT", categoryRepository.findByLabel("haushaltsmittel")),
        new Identifier("c+a", categoryRepository.findByLabel("kleidung")),
        new Identifier("zalando", categoryRepository.findByLabel("kleidung")),
        new Identifier("hauptzollamt bielefeld", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("unicredit", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("aral ag", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("jet dankt", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("Deutsche Tamoil GmbH", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("TAS Minden", categoryRepository.findByLabel("mobilitaet")),
        new Identifier("landbaeckerei niemeyer", categoryRepository.findByLabel("ausgehen")),
        new Identifier("ing", categoryRepository.findByLabel("sonstiges")),
        new Identifier("elsner catering", categoryRepository.findByLabel("sonstiges"))
    ));
  }

}
