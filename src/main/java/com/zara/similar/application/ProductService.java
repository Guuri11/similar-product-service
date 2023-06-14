package com.zara.similar.application;

import com.zara.similar.domain.Product;
import com.zara.similar.domain.mapper.ProductMapper;
import java.time.Duration;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductService {

  public final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final WebClient productClient;
  private final ProductMapper productMapper;

  @Cacheable(value = "productCache", key = "#id")
  public Flux<ProductDto> getSimilarProducts(final Long id) {

    return getSimilarProductIds(id)
        .flatMapMany(Flux::fromIterable)
        .flatMap(pid -> getProduct(String.valueOf(pid)))
        .map(productMapper::toDto);
  }

  private Mono<Product> getProduct(final String id) {

    return productClient.get()
        .uri("/product/" + id)
        .retrieve()
        .bodyToMono(Product.class)
        .onErrorResume(WebClientResponseException.class,
            ex -> {
              if (ex.getRawStatusCode() >= 400 && ex.getRawStatusCode() < 500) {
                logger.error("Product not found");
              } else {
                logger.error("Product Mock Service returned a 5xx error");
              }
              return Mono.empty();
            })
        .timeout(Duration.ofSeconds(5), Mono.empty());
  }

  private Mono<List<Integer>> getSimilarProductIds(final Long id) {

    return productClient.get()
        .uri("/product/" + id.toString() + "/similarids")
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Integer>>() {
        })
        .switchIfEmpty(Mono.error(new NotFoundException()));
  }
}
