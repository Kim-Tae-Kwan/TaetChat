package com.ktk.taekChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TaekChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaekChatApplication.class, args);
	}

}
