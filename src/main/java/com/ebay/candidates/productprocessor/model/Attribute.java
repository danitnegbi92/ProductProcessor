package com.ebay.candidates.productprocessor.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;

public class Attribute {

  private String name;
  private List<String> values;

  public Attribute() {
  }

  public Attribute(String name, List<String> values) {
    this.name = name;
    this.values = values;
  }

  public String getName() {
    return name;
  }

  public List<String> getValues() {
    return values;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("name", name)
        .add("values", values)
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
    Attribute attribute = (Attribute) o;
    return Objects.equal(name, attribute.name) &&
        Objects.equal(values, attribute.values);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name, values);
  }
}
