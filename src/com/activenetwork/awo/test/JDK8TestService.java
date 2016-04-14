package com.activenetwork.awo.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JDK8TestService {

  private static int outerNum = 3;
  
  public static void main( String[] args ) {
    
//    lambda();
//    predicate();
//    function();
//    supplier();
//    consumer();
//    comparator();
//    optional();
    optionalExt();
//    filter();
//    sort();
//    map();
//    match();
//    count();
//    reduce();
//    sortExt();
//    mapExt();
  }
  
  private static void lambda(){
    Formula f = new FormulaImpl();
    double r = f.calculate( 6 );
    System.out.println( r );
    
//    System.out.println( "----------" );
//    Formula f2 = (baseNum) -> sqrt(baseNum * 20); // disallow to access the non-abstract method in Functional Interface
//    double r2 = f2.calculate( 6 );
//    System.out.println( r2 );
    
    System.out.println( "----------" );
    List<String> countries = Arrays.asList( "China", "France", "New Zealand", "Canada", "German" );
//    Collections.sort( countries ); //ascending

//    Collections.sort( countries, (String a, String b) -> {
//      return b.compareTo(a); //descending
//    } );
    
//    Collections.sort( countries, (String a, String b) -> b.compareTo(a)); //descending
    
    Collections.sort( countries, (a, b) -> b.compareTo( a ) ); //descending
    
    for(String c : countries){
      System.out.println( c );
    }
    
    System.out.println( "----------" );
    Converter<String, Integer> converter = (src) -> Integer.valueOf( src );
    Integer des = converter.convert( "123" );
    System.out.println( des );
    
    System.out.println( "----------" );
    Converter<String, Integer> converter2 = Integer::valueOf;
    Integer des2 = converter2.convert( "234" );
    System.out.println( des2 );
    
    System.out.println( "----------" );
    String srcStr = "Java is a good programm language";
    Converter<String, Boolean> converter3 = srcStr::startsWith;
    Boolean des3 = converter3.convert( "Java" );
    System.out.println( des3 );
    
    System.out.println( "----------" );
    Converter<Integer, String> converter4 = srcStr::substring;
    String des4 = converter4.convert( 8 );
    System.out.println( des4 );
    
    System.out.println( "----------" );
    final int num = 1;
    Converter<Integer, String> converter5 = (src) -> String.valueOf( num + src );
    String des5 = converter5.convert( 2 );
    System.out.println( des5 );
    
    System.out.println( "----------" );
    int num2 = 2; // impact final key, disallow to change this value in Lambda expression
    Converter<Integer, String> converter6 = (from) -> String.valueOf( num2 + from );
    String des6 = converter6.convert( 2 );
//    num2 = 3; // impact final key, disallow to change this value as well
    System.out.println( des6 );
    
    System.out.println( "----------" );
    Converter<Integer, String> converter7 = (from) -> {
      outerNum = outerNum + 23;
      return String.valueOf( outerNum + from );
    };
    String des7 = converter7.convert( 2 );
    System.out.println( des7 );
    
    System.out.println( "----------" );
    PersonFactory<Person> factory = Person::new;
    Person p = factory.create( "Kevin", "Yung" );
    System.out.println( p.toString() );
  }
  
  private static void predicate(){
    Predicate<String> predicate = (s) -> s.length() > 0;
    boolean result = predicate.test( "foo" );
    System.out.println( result );
    
    boolean result2 = predicate.negate().test( "foo" );
    System.out.println( result2 );
    
    Predicate<Boolean> predicate2 = Objects::nonNull;
    boolean result3 = predicate2.test( null );
    System.out.println( result3 );
    
    Predicate<Boolean> predicate3 = Objects::isNull;
    boolean result4 = predicate3.test( true );
    System.out.println( result4 );
    
    Predicate<String> predicate4 = String::isEmpty;
    boolean result5 = predicate4.test( "foo" );
    System.out.println( result5 );
  }
  
  private static void function(){
    Function<String, Integer> toInteger = Integer::valueOf;
    Integer result = toInteger.apply( "123" );
    System.out.println( result );
    
    Function<String, String> toString = toInteger.andThen( String::valueOf );
    String result2 = toString.apply( "123" );
    System.out.println( result2 );
  }
  
  private static void supplier(){
    Supplier<Person> personSupplier = Person::new;
    Person p = personSupplier.get();
    System.out.println( p.toString() );
    
    p.setFirstname( "Kevin" );
    p.setLastname( "Yung" );
    System.out.println( p.toString() );
  }
  
  private static void consumer(){
    Consumer<Person> greeter = (p) -> System.out.println("Hi, " + p.getFirstname() + " " + p.getLastname());
    greeter.accept( new Person("Summer", "Wong") );
  }
  
  private static void comparator(){
    Comparator<Person> comparator = (p1, p2) -> p1.getFirstname().compareToIgnoreCase( p2.getFirstname() );
    Person p1 = new Person("Kevin", "Yung");
    Person p2 = new Person("Summer", "Yung");
    int r1 = comparator.compare( p1, p2 ); //<0
    System.out.println( r1 );
    
    int r2 = comparator.reversed().compare( p1, p2 ); //>0
    System.out.println( r2 );
  }
  
  private static void optional(){
    Optional<String> optional = Optional.of( "foo" );
    boolean r = optional.isPresent();
    System.out.println( r );
    
    String s = optional.get();
    System.out.println( s );
    
    String s2 = optional.orElse( "backup foo" );
    System.out.println( s2 );
    
    optional.ifPresent(( s3 ) -> System.out.println( s3.charAt( 1 ) ));
  }
  
  private static void optionalExt(){
    long id = 9527l;
    
    PersonDao dao = new PersonDaoImpl();
    
    Optional<Person> p = dao.findPersonBy( id );
    if(p.isPresent()){
      System.out.println( p.get().toString() );
    }else{
      System.out.println( "Not Found: " + id );
    }
  }
  
  private static List<String> stream(){
    List<String> c = new ArrayList<String>();
    c.add( "China" );
    c.add( "chengdu" );
    c.add( "xian" );
    c.add( "US" );
    c.add( "co" );
    c.add( "ms" );
    c.add( "France" );
    c.add( "paris" );
    c.add( "lens" );
    c.add( "Britain" );
    c.add( "london" );
    c.add( "liverpool" );
    return c;
  }
  
  private static void filter(){
    List<String> c = stream();
    c.stream().filter( (s) -> s.startsWith( "C" )).forEach( System.out::println );
  }
  
  private static void sort(){
    List<String> c = stream();
    c.stream().sorted().filter( (s) -> s.startsWith( "l" ) ).forEach( System.out::println );
    
    System.out.println( "----------" );
    c.forEach( System.out::println );
  }
  
  private static void sortExt(){
    int max = 100000;
    List<String> values = new ArrayList<String>(max);
    for(int i=0; i<max; i++){
      UUID uuid = UUID.randomUUID();
      values.add( uuid.toString() );
    }
    
    long start = System.nanoTime();
    long count = values.stream().sorted().count();
    System.out.println( count );
    long end = System.nanoTime();
    
    long offset = TimeUnit.NANOSECONDS.toMillis( end - start );
    System.out.println( String.format( "sequential sorting tooks: %d ms", offset ));
    
    start = System.nanoTime();
    count = values.parallelStream().sorted().count();
    System.out.println( count );
    end = System.nanoTime();
    offset = TimeUnit.NANOSECONDS.toMillis( end - start );
    System.out.println( String.format( "parallel sorting tooks: %d ms", offset ));
    
  }
  
  private static void map(){
    List<String> c = stream();
    c.stream().map( String::toUpperCase ).sorted((a, b) -> b.compareTo( a )).forEach( System.out::println );
    
    System.out.println( "----------" );
    c.forEach( System.out::println );
  }
  
  private static void mapExt(){
    Map<Integer, String> m = new HashMap<Integer, String>();
    for(int i=0; i<10; i++){
      m.putIfAbsent( i, "map_" + i );
    }
    
    m.forEach( (key, val) -> System.out.println( key + ": " + val ));
    
    System.out.println( "----------" );
    m.computeIfPresent( 3, (num, val) -> val + num );
    String r = m.get( 3 );
    System.out.println( r );
    
    System.out.println( "----------" );
    m.computeIfPresent( 9, (num, val) -> null );
    boolean r2 = m.containsKey( 9 );
    System.out.println( r2 );
    
    System.out.println( "----------" );
    m.computeIfAbsent( 23, num -> "map_" + num );
    boolean r3 = m.containsKey( 23 );
    System.out.println( r3 );
    
    System.out.println( "----------" );
    m.computeIfAbsent( 3, num -> "foo" );
    String r4 = m.get( 3 );
    System.out.println( r4 );
    
    System.out.println( "----------" );
    m.remove( 3, "val3" ); // not exist
    String r5 = m.get( 3 );
    System.out.println( r5 );
    
    System.out.println( "----------" );
    m.remove( 3, "map_33" );
    String r6 = m.get( 3 );
    System.out.println( r6 );
    
    System.out.println( "----------" );
    String r7 = m.get( 93 );
    System.out.println( r7 );
    r7 = m.getOrDefault( 93, "Not Exist" );
    System.out.println( r7 );
    
    System.out.println( "----------" );
    m.merge( 19, "_kevin", (value, newValue) -> value.concat( newValue ));
    String r8 = m.get( 19 );
    System.out.println( r8 );
    m.merge( 19, "_yung", (value, newValue) -> value.concat( newValue ));
    r8 = m.get( 19 );
    System.out.println( r8 );
  }
  
  private static void match(){
    List<String> c = stream();
    boolean anyStartsWithC = c.stream().anyMatch( (s) -> s.startsWith( "C" ) );
    System.out.println( anyStartsWithC );
    
    boolean allStartsWithC = c.stream().allMatch( (s) -> s.startsWith( "C" ) );
    System.out.println( allStartsWithC );
    
    boolean nonStartsWithC = c.stream().noneMatch( (s) -> s.startsWith( "C" ) );
    System.out.println( nonStartsWithC );
  }
  
  private static void count(){
    List<String> c = stream();
    long count = c.stream().filter( (s) -> s.startsWith( "c" ) ).count();
    System.out.println( count );
  }
  
  private static void reduce(){
    List<String> c = stream();
    Optional<String> reduced = c.stream().sorted().reduce( (s1, s2) -> s1 + "#" + s2 );
    reduced.ifPresent( System.out::println );
  }

}
