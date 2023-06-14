package com.zara.similar.infrastructure.web;

import com.zara.similar.application.ProductDto;
import com.zara.similar.application.ProductService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController extends BaseController {

  private final ProductService productService;

  @GetMapping(path = "/{id}/similar", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "404", description = "Item not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  public Flux<ProductDto> getSimilar(
      @PathVariable("id") final long id
  ) {

    this.logger.info("GET /products/{}/similar", id);
    return productService.getSimilarProducts(id);
  }
}
