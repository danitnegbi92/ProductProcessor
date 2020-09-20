package com.ebay.candidates.productprocessor.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ebay.candidates.productprocessor.model.Attribute;
import com.ebay.candidates.productprocessor.model.Product;
import com.ebay.candidates.productprocessor.normalizationForms.CanonicalForm;
import com.ebay.candidates.productprocessor.service.ProductService;
import com.ebay.candidates.productprocessor.utils.ObjectMapperUtil;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Comparator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@WebMvcTest(com.ebay.candidates.productprocessor.controller.ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  ProductService productService;

  @Test
  public void createProduct() throws Exception {
    Product product = new com.ebay.candidates.productprocessor.model.Product(100L, ImmutableList.<Attribute>builder()
        .add(new Attribute("title", Lists.newArrayList("ralph Lauren Men's Tshirt")))
        .add(new Attribute("color", Lists.newArrayList("red", "blue")))
        .build());

    String uri = "/product-processor/product";

    Mockito.doReturn(product).when(productService).getProcessedProduct(Mockito.any(
        com.ebay.candidates.productprocessor.model.Product.class), Mockito.any(
        CanonicalForm.class), ArgumentMatchers.<Comparator<Attribute>>any());

    String inputJson = ObjectMapperUtil.mapToJson(product);
    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

    assertEquals(HttpStatus.OK.value(),  mvcResult.getResponse().getStatus());
  }
}
