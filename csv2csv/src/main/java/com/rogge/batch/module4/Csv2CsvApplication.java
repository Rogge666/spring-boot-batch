package com.rogge.batch.module4;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * [Description]
 * <p>
 * [How to use]
 * <p>
 * [Tips]
 *
 * @author Created by Rogge on 2018-01-20.
 * @since 1.0.0
 */
@EnableBatchProcessing
@SpringBootApplication
public class Csv2CsvApplication {
    public static void main(String[] args) {
        SpringApplication.run(Csv2CsvApplication.class, args);
    }
}
