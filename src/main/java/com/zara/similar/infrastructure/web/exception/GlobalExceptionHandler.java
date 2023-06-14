package com.zara.similar.infrastructure.web.exception;

import com.zara.similar.infrastructure.web.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

  @ExceptionHandler(WebClientResponseException.class)
  public Mono<ResponseEntity<String>> handleWebClientResponseException(final WebClientResponseException ex) {

    final HttpStatus status = ex.getStatusCode();
    final String message = ex.getResponseBodyAsString();
    logger.error(ex.getMessage());
    return Mono.just(ResponseEntity.status(status)
        .body(message));
  }

  @ExceptionHandler(Throwable.class)
  public Mono<ResponseEntity<String>> handleThrowable(final Throwable ex) {

    logger.error(ex.getMessage());
    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ex.getMessage()));
  }
}
