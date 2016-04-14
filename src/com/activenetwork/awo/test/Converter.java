package com.activenetwork.awo.test;

@FunctionalInterface
public interface Converter<F, T> {

  T convert(F src);
  
}
