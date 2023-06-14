package com.zara.similar.application;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {

    super("Could not find item");
  }
}