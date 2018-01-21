package com.rogge.batch.module2;

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
public class DB2CSVApplication {
        public static void main(String[] args) {
            SpringApplication.run(DB2CSVApplication.class, args);
        }
}
