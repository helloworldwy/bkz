package com.activenetwork.awo.test;

@FunctionalInterface
public interface Formula {

  double calculate(int a);
  
  default double sqrt(int a){
    return Math.sqrt( a );
  }
}
