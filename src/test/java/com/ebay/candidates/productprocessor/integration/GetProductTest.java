package com.ebay.candidates.productprocessor.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetProductTest extends BaseTest {

  private static final String UPSERT_PRODUCT_URL = "/product-processor/product";
  private static final String GET_PRODUCT_URL = "/product-processor/product/%s";

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void getProduct() {
    Product expectedProduct = createProduct(100L);
    ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity(String.format(GET_PRODUCT_URL, "100"), Product.class);
    assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    Product actualProduct = productResponseEntity.getBody();
    assertThat(actualProduct).isEqualTo(expectedProduct);
  }

  private Product createProduct(Long id) {
    Product product = new Product(id, ImmutableList.<Attribute>builder()
        .add(new Attribute("color", Lists.newArrayList("Red", "Blue")))
        .add(new Attribute("size", Lists.newArrayList("Xl")))
        .add(new Attribute("title", Lists.newArrayList("ralph Lauren Men's Tshirt")))
        .build());
    ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(UPSERT_PRODUCT_URL, product, Product.class);
    assertThat(productResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    return product;
  }
}
