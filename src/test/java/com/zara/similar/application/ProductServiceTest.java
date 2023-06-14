package com.zara.similar.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockserver.model.HttpRequest.request;

import com.zara.similar.domain.Product;
import com.zara.similar.domain.mapper.ProductMapper;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @Test
  void GivenProductId_WhenGetSimilar_ThenReturnResults() {

    // Given
    final ProductDto productDto = new ProductDto("2", "Product 2", 10.00, true);
    final List<Integer> similarProductIds = List.of(2, 3);
    mockServer.when(
            request()
                .withMethod("GET")
                .withPath("/product/1/similarids")
        )
        .respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody("[2]")
        );
    mockServer.when(
            request()
                .withMethod("GET")
                .withPath("/product/2")
        )
        .respond(
            HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody("{\"id\":2,\"name\":\"Product 2\",\"price\":10.00,\"availability\":true}")
        );
    when(productMapper.toDto(any(Product.class)))
        .thenReturn(productDto);

    // When
    final Flux<ProductDto> similarProducts = productService.getSimilarProducts(1L);

    // Then
    StepVerifier.create(similarProducts)
        .expectNext(productDto)
        .verifyComplete();
    verify(productMapper, times(1)).toDto(any(Product.class));

  }

  @Test
  void GivenNonexistentProductId_WhenGetSimilar_ThenThrowNotFoundException() {
    // Given
    mockServer.when(
            request()
                .withMethod("GET")
                .withPath("/product/1/similarids")
        )
        .respond(
            HttpResponse.response()
                .withStatusCode(404)
        );

    // When
    final Flux<ProductDto> similarProducts = productService.getSimilarProducts(1L);

    // Then
    StepVerifier.create(similarProducts)
        .expectError(WebClientResponseException.NotFound.class)
        .verify();
  }


  @BeforeEach
  public void setupMockServer() {

    mockServer = ClientAndServer.startClientAndServer(2001);
    productService = new ProductService(WebClient.builder()
        .baseUrl("http://localhost:" + mockServer.getLocalPort())
        .build(), productMapper);
  }

  @AfterEach
  public void tearDownServer() {

    mockServer.stop();
  }

  private ClientAndServer mockServer;
  @Mock
  private ProductMapper productMapper;
  @InjectMocks
  private ProductService productService;
}