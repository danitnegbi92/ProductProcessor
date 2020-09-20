package com.ebay.candidates.productprocessor.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
  public static String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  public static <T>T mapToObject(Object fromValue, Class<T> toValueType){
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.convertValue(fromValue, toValueType);
  }
}
