package com.example.oauth2service.clirunner.db;

import com.example.oauth2service.adapter.jpa.repository.JpaQueryBuilderClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class DbSeedRunner implements CommandLineRunner {

    private final JpaQueryBuilderClientRepository clientRepository;

    private static final String COMMAND = "db:seed";

    @Autowired
    public DbSeedRunner(ApplicationContext applicationContext, JpaQueryBuilderClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!Arrays.asList(args).contains(COMMAND)) {
            return;
        }
    }
}
