package com.ebay.candidates.productprocessor.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;

public class Product {

  private Long id;
  private List<Attribute> attributes;

  public Product() {
  }

  public Product(Long id) {
    this.id = id;
    this.attributes = new ArrayList<>();
  }

  public Product(Long id, List<Attribute> attributes) {
    this.id = id;
    this.attributes = attributes;
  }

  public void setAttributes(
      List<Attribute> attributes) {
    this.attributes = attributes;
  }

  public void addAttribute(Attribute attribute){
    attributes.add(attribute);
  }

  public Long getId() {
    return id;
  }

  public List<Attribute> getAttributes() {
    return attributes;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("attributes", attributes)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equal(id, product.id) &&
        Objects.equal(attributes, product.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, attributes);
  }
}
