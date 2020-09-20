package com.ebay.candidates.productprocessor.normalizationForms;

import org.springframework.util.StringUtils;

public class CapitalizationForm implements CanonicalForm {
  @Override
  public String getCanonicalForm(String input) {
    return StringUtils.capitalize(input);
  }
}
