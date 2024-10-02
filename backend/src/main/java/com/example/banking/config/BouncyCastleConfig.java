package com.example.banking.config;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.Security;

@Configuration
public class BouncyCastleConfig {

    @PostConstruct
    public void setup() {
        Security.addProvider(new BouncyCastleProvider());
    }
}