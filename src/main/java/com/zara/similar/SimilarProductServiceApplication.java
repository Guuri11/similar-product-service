package com.zara.similar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@OpenAPIDefinition(info = @Info(title = "Similar Product Backend service", version = "1.0", description = "Documentation Similar Product Management APIs v1.0"))
@SpringBootApplication
public class SimilarProductServiceApplication {

  public static void main(final String[] args) {

    SpringApplication.run(SimilarProductServiceApplication.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {

    return new ModelMapper();
  }

}
