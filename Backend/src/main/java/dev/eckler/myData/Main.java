package dev.eckler.myData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import dev.eckler.myData.transaktion.Transaktion;
import dev.eckler.myData.transaktion.TransaktionRepository;
import dev.eckler.myData.transaktion.TransaktionService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import java.io.InputStream;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}
