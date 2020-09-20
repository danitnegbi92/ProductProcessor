package com.ebay.candidates.productprocessor.controller;

import com.ebay.candidates.productprocessor.comporators.AttributeNameComparator;
import com.ebay.candidates.productprocessor.normalizationForms.CapitalizationForm;
import com.ebay.candidates.productprocessor.model.Product;
import com.ebay.candidates.productprocessor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product-processor")
public class ProductController {

  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @RequestMapping(path = "/product", method = RequestMethod.POST)
  public ResponseEntity<Product> upsert(@RequestBody Product product) {
    Product processedProduct = productService.getProcessedProduct(product, new CapitalizationForm(), new AttributeNameComparator());
    return ResponseEntity.ok(processedProduct);
  }

  @RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
  public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
    Product productsList = productService.getProductById(id);
    return ResponseEntity.ok(productsList);
  }
}
