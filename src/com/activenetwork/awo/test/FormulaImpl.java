package com.activenetwork.awo.test;


public class FormulaImpl implements Formula {

  @Override
  public double calculate( int a ) {
    return sqrt(a * a);
  }

}
