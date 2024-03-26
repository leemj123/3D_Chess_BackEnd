package com.gamza.chess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import reactor.core.publisher.Hooks;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ChessApplication {

	public static void main(String[] args) {
		Hooks.onOperatorDebug();
		SpringApplication.run(ChessApplication.class, args);
	}

}
