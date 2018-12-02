package com.dawid.sternik.server.loaders;

import com.dawid.sternik.server.entity.Recipe;
import com.dawid.sternik.server.entity.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(RecipeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Recipe("recipe 1", "rosół")));
            log.info("Preloading " + repository.save(new Recipe("recipe 2", "schabowy")));
        };
    }
}
