package com.ebay.candidates.productprocessor.service;

import com.ebay.candidates.productprocessor.entity.ProductDo;
import com.ebay.candidates.productprocessor.exception.ProductNotFound;
import com.ebay.candidates.productprocessor.normalizationForms.CanonicalForm;
import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import com.ebay.candidates.productprocessor.repository.ProductRepository;
import com.ebay.candidates.productprocessor.utils.ObjectMapperUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ProductService {

  private ProductRepository productRepository;

  @Value("#{'${attribute-values.exclusion-list}'.split(',')}")
  private List<String> attributesValuesExclusionList;

  @Value("#{'${attribute-names.exclusion-list}'.split(',')}")
  private List<String> attributesNamesExclusionList;

  public ProductService() {
  }

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository; }

  public Product getProcessedProduct(Product product, CanonicalForm canonicalForm, Comparator<Attribute> comparatorToSort) {
    Product processedProduct = null;

    if(isValidProduct(product)) {
      processedProduct = new Product(product.getId());

      for (Attribute attribute : product.getAttributes()) {
        addProcessedAttributes(canonicalForm, processedProduct, attribute);
      }
      sortAttributesByComparator(comparatorToSort, processedProduct);
      productRepository.save(ObjectMapperUtil.mapToObject(processedProduct, ProductDo.class));
    }
    return processedProduct;
  }

  private boolean isValidProduct(Product product){
    if(product==null || product.getId() == null){
      throw new ProductNotFound();
    }
    return true;
  }

  private void addProcessedAttributes(CanonicalForm canonicalForm, Product processedProduct,
                                      Attribute attribute) {
    List<String> attributesValues = new ArrayList<>();
    String attributeName = attribute.getName();
    for (String value : attribute.getValues()) {
      if (shouldValueBeExcluded(value)) {
        continue;
      }
      String normalizedValue = getNormalizedValue(canonicalForm, attributeName, value);
      attributesValues.add(normalizedValue);
    }
    if(!CollectionUtils.isEmpty(attributesValues)) {
      processedProduct.addAttribute(new Attribute(attributeName, attributesValues));
    }
  }

  private String getNormalizedValue(CanonicalForm canonicalForm, String attributeName,
                                    String value) {
    return CollectionUtils.containsAny(attributesNamesExclusionList, Collections.singletonList(attributeName))? value : canonicalForm.getCanonicalForm(value);
  }

  private boolean shouldValueBeExcluded(String value) {
    return CollectionUtils.containsAny(attributesValuesExclusionList, Collections.singletonList(value));
  }

  public void sortAttributesByComparator(Comparator<Attribute> comparatorToSort,
                                         Product processedProduct) {
    List<Attribute> attributes = processedProduct.getAttributes().stream().sorted(comparatorToSort).collect(
            Collectors.toList());
    processedProduct.setAttributes(attributes);
  }

  public Product getProductById(Long id){
    Product product = ObjectMapperUtil.mapToObject(productRepository.getById(id), Product.class);
    if(isValidProduct(product)) {
      return product;
    }
    return null;
  }
}
