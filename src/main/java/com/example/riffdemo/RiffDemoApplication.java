package com.example.riffdemo;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.function.Function;

@SpringBootApplication
public class RiffDemoApplication {

    private static final int HASH_LENGTH = 8;
    private static final String DOMAIN_NAME = "http://r.url/";

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public Function<String, String> shortUrl() {
        return s -> {
            String hash = DigestUtils.md5Hex(s).substring(0, HASH_LENGTH);
            writeToChannel(hash, s);
            return DOMAIN_NAME + hash;
        };
    }

    private void writeToChannel(String hash, String longUrl) {
        // TODO parameterize this
        HttpEntity<String> kvPair = new HttpEntity<>(hash+":"+longUrl);
        restTemplate.postForLocation("http://url-channel.default.svc.cluster.local", kvPair);
    }

	public static void main(String[] args) {
		SpringApplication.run(RiffDemoApplication.class, args);
	}
}
