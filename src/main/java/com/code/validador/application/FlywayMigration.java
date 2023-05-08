package com.code.validador.application;

import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FlywayMigration {
    // You can Inject the object if you want to use it manually
    @Inject
    Flyway flyway;

    public void checkMigration() {
        // This will print 1.0.0
        System.out.println(flyway.info().current().getVersion().toString());
    }
}