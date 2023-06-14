package com.zara.similar.infrastructure.web;

import com.zara.similar.application.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @Test
  void getSimilar_shouldCallProductService() {
    // Given

    // When
    productController.getSimilar(1L);

    // Then
    Mockito.verify(productService)
        .getSimilarProducts(1L);

  }

  @Mock
  private ProductService productService;
  @InjectMocks
  private ProductController productController;
}