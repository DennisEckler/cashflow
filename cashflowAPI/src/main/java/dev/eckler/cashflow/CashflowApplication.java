package dev.eckler.cashflow;

import dev.eckler.cashflow.config.Oauth2Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {Oauth2Properties.class})
@SpringBootApplication
public class CashflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashflowApplication.class, args);
	}

}
