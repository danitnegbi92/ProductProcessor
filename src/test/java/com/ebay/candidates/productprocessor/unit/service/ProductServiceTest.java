package com.ebay.candidates.productprocessor.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ebay.candidates.productprocessor.comporators.AttributeNameComparator;
import com.ebay.candidates.productprocessor.entity.ProductDo;
import com.ebay.candidates.productprocessor.exception.ProductNotFound;
import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import com.ebay.candidates.productprocessor.normalizationForms.CapitalizationForm;
import com.ebay.candidates.productprocessor.repository.ProductRepository;
import com.ebay.candidates.productprocessor.service.ProductService;
import com.ebay.candidates.productprocessor.utils.ObjectMapperUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

  private ProductService productService;

  @MockBean
  private ProductRepository productRepository;

  private Product product;

  @Before
  public void setUp() {
    productService = new ProductService(productRepository);
    ReflectionTestUtils.setField(productService, "attributesValuesExclusionList",
        Collections.singletonList("Not_Applied"));
    ReflectionTestUtils.setField(productService, "attributesNamesExclusionList",
        Collections.singletonList("color"));


    Long id = 300L;
    product = new Product(id, ImmutableList.<Attribute>builder()
        .add(new Attribute("color", Lists.newArrayList("red", "blue")))
        .add(new Attribute("company", Lists.newArrayList("mont")))
        .add(new Attribute("Size", Lists.newArrayList("s")))
        .add(new Attribute("title", Lists.newArrayList("ralph Lauren Men's Tshirt")))
        .add(new Attribute("attachment", Lists.newArrayList("Not_Applied")))
        .build());

    ProductDo productDo = ObjectMapperUtil.mapToObject(product, ProductDo.class);
    Mockito.when(productRepository.getById(id)).thenReturn(productDo);
    Mockito.when(productRepository.save(productDo)).thenReturn(productDo);
  }

  @Test
  public void sortAttributesByComparator(){
    productService.sortAttributesByComparator(new AttributeNameComparator(), product);
    List<Attribute> attributes = product.getAttributes();
    List<String> attributeNames = attributes.stream().map(Attribute::getName).collect(Collectors.toList());
    assertThat(attributeNames).containsExactly("Size", "attachment", "color", "company", "title");
  }

  @Test
  public void checkNormalizationCapitalize(){
    Product processedProduct = productService.getProcessedProduct(product, new CapitalizationForm(), new AttributeNameComparator());
    List<Attribute> attributes = processedProduct.getAttributes();
    Map<String, List<String>> attributesMap = toAttributesMap(attributes);
    assertThat(attributesMap).containsEntry("title", Lists.newArrayList("Ralph Lauren Men's Tshirt"));
    assertThat(attributesMap).containsEntry("color", Lists.newArrayList("red", "blue"));
    assertThat(attributesMap).containsEntry("Size", Lists.newArrayList("S"));
    assertThat(attributesMap).containsEntry("company", Lists.newArrayList("Mont"));
  }

  @Test
  public void attributeValuesExclusion() {
    Product processedProduct = productService.getProcessedProduct(product, new CapitalizationForm(), new AttributeNameComparator());
    List<Attribute> attributes = processedProduct.getAttributes();
    Map<String, List<String>> attributesMap = toAttributesMap(attributes);
    assertThat(attributesMap).doesNotContainKey("attachment");
  }

  @Test
  public void checkProcessedProduct(){
    Product processedProduct = productService.getProcessedProduct(product, new CapitalizationForm(), new AttributeNameComparator());
    List<Attribute> attributes = processedProduct.getAttributes();
    Map<String, List<String>> attributesMap = toAttributesMap(attributes);
    assertThat(attributesMap).doesNotContainEntry("title", Lists.newArrayList("ralph Lauren"));
    assertThat(attributesMap).doesNotContainEntry("color", Lists.newArrayList("Green"));
    assertThat(attributesMap).doesNotContainKey("size");
  }

  @Test(expected = ProductNotFound.class)
  public void checkNullProcessedProduct() {
    productService.getProcessedProduct(new Product(), null, null);
  }

  @Test(expected = ProductNotFound.class)
  public void checkNullGetProduct() {
    productService.getProductById(200L);
  }

  private Map<String, List<String>> toAttributesMap(List<Attribute> attributes) {
    return attributes.stream()
        .collect(Collectors.toMap(Attribute::getName, Attribute::getValues));
  }
}
