package org.company.youtube.service.flyway;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@RequiredArgsConstructor
public class FlywayDemoService implements CommandLineRunner {

    private final DataSource dataSource;

//    public static void main(String[] args) {
//        SpringApplication.run(FlywayDemoService.class, args);
//    }

    @Override
    public void run(String... args) throws Exception {
        Flyway.configure().baselineOnMigrate(true).dataSource(dataSource).load().migrate();
    }

}
