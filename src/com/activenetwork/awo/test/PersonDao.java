package com.activenetwork.awo.test;

import java.util.Optional;

public interface PersonDao {

  public Optional<Person> findPersonBy(long id);
  
}
