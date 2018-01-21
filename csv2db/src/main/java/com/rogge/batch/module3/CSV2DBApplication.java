package com.rogge.batch.module3;

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
public class CSV2DBApplication {
    public static void main(String[] args) {
        SpringApplication.run(CSV2DBApplication.class, args);
    }
}
