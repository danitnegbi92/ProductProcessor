package com.ebay.candidates.productprocessor.exception;

public class ProductNotFound extends RuntimeException {
  public ProductNotFound() {
    super("Product Not Found Exception");
  }
}
