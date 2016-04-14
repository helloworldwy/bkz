package com.activenetwork.awo.test;

@FunctionalInterface
public interface PersonFactory<P extends Person> {

  P create(String firstname, String lastname);
  
}
