package com.ebay.candidates.productprocessor.entity;

import com.ebay.candidates.productprocessor.model.Attribute;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class ProductDo {

  @Id
  private Long id;
  private List<Attribute> attributes;

  public ProductDo() {
  }

  public ProductDo(Long id, List<Attribute> attributes) {
    this.id = id;
    this.attributes = attributes;
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
    ProductDo product = (ProductDo) o;
    return Objects.equal(id, product.id) &&
        Objects.equal(attributes, product.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id, attributes);
  }
}
