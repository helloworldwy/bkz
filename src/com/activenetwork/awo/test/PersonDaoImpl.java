package com.activenetwork.awo.test;

import java.util.Optional;


public class PersonDaoImpl implements PersonDao {

  @Override
  public Optional<Person> findPersonBy( long id ) {
    Person person = null;
//    person = new Person("Summer", "Wong");
    
    Optional<Person> result = Optional.ofNullable( person );
    return result;
  }

}
