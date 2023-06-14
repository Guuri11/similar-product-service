package com.zara.similar.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class ProductServiceIT {

  private final ProductService underTest;

  @Autowired
  public ProductServiceIT(final ProductService underTest) {

    this.underTest = underTest;
  }

  @Test
  void testSimilarProductsFromProductOne() {

    final List<ProductDto> result = underTest.getSimilarProducts(1L)
        .collectList()
        .block();
    assertThat(result).hasSize(3);
    assertThat(result)
        .extracting(ProductDto::getId)
        .containsOnly("2", "3", "4");
  }
}