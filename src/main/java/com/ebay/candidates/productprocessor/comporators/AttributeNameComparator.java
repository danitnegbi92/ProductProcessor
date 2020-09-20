package com.ebay.candidates.productprocessor.comporators;

import com.ebay.candidates.productprocessor.model.Attribute;
import java.util.Comparator;

public class AttributeNameComparator implements Comparator<Attribute> {
  @Override
  public int compare(Attribute object1, Attribute object2) {
    return object1.getName().compareTo(object2.getName());
  }
}
