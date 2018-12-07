package com.example.riffdemo;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class RiffDemoApplication {

    public static final int HASH_LENGTH = 8;

    @Bean
    public Function<String, String> shortUrl() {
        return s -> DigestUtils.md5Hex(s).substring(0, HASH_LENGTH);
    }

	public static void main(String[] args) {
		SpringApplication.run(RiffDemoApplication.class, args);
	}
}
