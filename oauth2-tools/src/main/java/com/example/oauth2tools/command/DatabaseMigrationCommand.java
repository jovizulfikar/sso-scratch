package com.example.oauth2tools.command;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import liquibase.command.CommandScope;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ShellCommandGroup("Database Migration")
@RequiredArgsConstructor
public class DatabaseMigrationCommand {

    @PersistenceContext
    private final EntityManager entityManager;
    private final Environment environment;
    
    @ShellMethod(key = "migrate", value = "Migrate database")
    @SneakyThrows
    public void migrate() {
        new CommandScope("update")
                .addArgumentValue("url", environment.getProperty("spring.datasource.url"))
                .addArgumentValue("changeLogFile",  environment.getProperty("spring.liquibase.change-log"))
                .addArgumentValue("tag", "123")
                .execute();
    }

    @ShellMethod(key = "migrate:rollback", value = "Rollback database migration")
    @SneakyThrows
    public void rollback() {
        var tag = (String) entityManager.createNativeQuery("SELECT tag FROM databasechangelog WHERE tag is not null ORDER BY dateexecuted desc limit 1")
            .getResultList().get(0);

        new CommandScope("rollback")
                .addArgumentValue("url", environment.getProperty("spring.datasource.url"))
                .addArgumentValue("changeLogFile",  environment.getProperty("spring.liquibase.change-log"))
                .addArgumentValue("tag", tag)
                .execute();
    }

}
