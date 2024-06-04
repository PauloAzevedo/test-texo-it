package com.texoit.test.config;

import com.texoit.test.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    @Autowired
    private MovieService movieService;


    @Bean
    public CommandLineRunner loadData() {
        return args -> {
           movieService.startLoadData();
        };
    }
}
