package com.activenetwork.awo.test;

import java.util.Arrays;
import java.util.HashSet;

public class JDKTestService {

  private static void parseAttributeValueExpressionToTuple( String attributeValueString ){
      if ( attributeValueString.split( "[=]" ).length < 2 ){
        System.out.println( "wrong format" );
        return;
      }
      String[] idAndVal = attributeValueString.split( "[=]" );
      try{
        long attrId = Long.parseLong( idAndVal[1] );
        System.out.println( idAndVal[0] + ": " + attrId );
      }
      catch ( Exception ex ){
        //  Just let it go and return null.
        System.out.println( "failed to split" );
      }
  }
  
  private static void parse( String attributeValueString ){
    String[] subStrings = attributeValueString.split( "[,]" );
    for(String subS : subStrings){
      parseAttributeValueExpressionToTuple(subS);
    }
}
  
  public static <T> HashSet<T> newHashSet(T... objs) {
    HashSet<T> results = new HashSet<T>();
    results.addAll(Arrays.asList(objs));
    return results;
  }
  
  private static boolean isInSequence(long[] entranceIds, long[] sequenceIds){
    long currentEntranceId = 0l;
    int preMatchedIndex = 0;
    boolean isMatched = false;
    for ( int i=0; i<entranceIds.length; i++ ) {
      currentEntranceId = entranceIds[i];
      
      for(int j=preMatchedIndex; j<sequenceIds.length; j++){
        if(currentEntranceId == sequenceIds[j]){
          isMatched = true;
          preMatchedIndex = j + 1; // move to next order item index
          break;
        }
      }
      
      if(!isMatched){
        return false;
      }else{
        //reset indicator and keep checking next order item
        isMatched = false;
      }
    }
    
    return true;
  }
  
  public static void main( String[] args ) {
    
    if(null instanceof String){
      System.out.println( "--" );
    }else{
      System.out.println( "xx" );
    }
    
//    long[] entranceIds = new long[]{1, 3};
//    long[] sequenceIds = new long[]{1, 2, 3, 4};
//    boolean isInSeq = isInSequence(entranceIds, sequenceIds);
//    System.out.println( isInSeq );
    
    
//    boolean a = false;
//    boolean b = false;
//    boolean s = Boolean.logicalXor( a, b );
//    System.out.println( s );
    
    
//    for(int i=0; i<20; i++){
//      for(int j=0; j<20; j++){
//        if(j == 5){
//          break;
//        }else{
//          System.out.println( i + ": " + j );
//        }
//      }
//    }
    
//    String[] s = new String[]{"123", "123", "456"};
//    HashSet<String> d = newHashSet(s);
//    for(String x : d){
//      System.out.println( x );
//    }
    
////    String n = "Site Reserve Type";
//    String m = "loop"; //"Type of Use";
////    String r = replaceAll(n, "Site", "Area");
//    
//    boolean r = isInValid(m);
    
    
    
//    long d = 123l;
//    Privilege p = new Privilege(123l);
//    if(p.getId() ==  d ){
//      System.out.println( "equals" );
//    }else{
//      System.out.println( "not equals" );
//    }
    
    
//    String d = "testLLLsssee";
//    System.out.println( d.toLowerCase() );
    
//    int currentPageSize = 5;
//    int currentPageNum = 3;
//    int formIndex = 0;
//    int toIndex = currentPageSize;
//    if(currentPageNum > 1){
//      formIndex = currentPageSize * (currentPageNum - 1) + 1;
//      toIndex = currentPageSize * currentPageNum;
//    }else{
//      formIndex = 0;
//      toIndex = currentPageSize;
//    }
//    
//    System.out.println( formIndex + ": " + toIndex );;
    
    
//    boolean a = true;
//    boolean b = true;
//    if(a && b){
//      System.out.println( "a and b" );
//    }else if(b){
//      System.out.println( "b" );
//    }else{
//      System.out.println( "no" );
//    }
    
//    String attributeValueStrings = "(101=-1,102=121,103=131,104=141,105=151),(101=212,102=-1,103=232,104=242,105=252),(101=313,102=323,103=-1,104=343,105=353)";
////    String[] strings = attributeValueStrings.split( "[,]" );
//    String[] strings = attributeValueStrings.split( "[\\(.*\\)]" );
//    for(String s : strings){
////      System.out.println( "s -- " + s );
//      if(s.length() > 1){
//        System.out.println( "s -- " + s );
//        parse(s);
//      }
//    }
    
    
//      Long a = new Long(12);
//      Long b = new Long(12);
//      if(a == b){
//        System.out.println( "==" );
//      }else{
//        System.out.println( "<>" );
//      }
//      
//      if(a.equals( b )){
//        System.out.println( "equals" );
//      }else{
//        System.out.println( "Unequals" );
//      }
    
//    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//    Date d;
//    try {
//      d = f.parse( "2016-01-09 13:30:23:045" );
//      Calendar inventoryDate = Calendar.getInstance(); 
//      inventoryDate.setTime( d );
//      
//      Calendar now = Calendar.getInstance(); 
//      if(now.before( inventoryDate )){
//        System.out.println( "before" );
//      }else{
//        System.out.println( "after" );
//      }
//    }
//    catch ( ParseException e ) {
//      e.printStackTrace();
//    }
  }

  private static boolean isInValid(String m){
    if(m.isEmpty()){
      return true;
    }
    
    for(String invalidLabel : INVALID_HUNTING_LABELS){
      if(invalidLabel.equalsIgnoreCase( m )){
        return true;
      }
    }
    return false;
  }
  
  private static final String[] INVALID_HUNTING_LABELS = {"Type of Use", "Loop"};
  
  private static boolean isEmpty( String main ) {
    return main == null || main.trim().length() == 0;
  }
  
  private static String replaceFirst( String word, String regex, String replacement ) {
    if ( word != null ) {
      if ( regex == null ) { return word; }

      if ( replacement == null ) {
        replacement = "";
      }

      return word.replaceFirst( regex, replacement );
    }

    return null;
  }
  
  private static String replaceAll( String word, String replace, String newSeg ) {
    StringBuilder newStr = new StringBuilder();
    int foundIdx = 0;
    int lastPointerIdx = 0;
    do {
      foundIdx = word.indexOf( replace, lastPointerIdx );
      if ( foundIdx < 0 ) {
        newStr.append( word.substring( lastPointerIdx, word.length() ) );
      }
      else {
        if ( foundIdx > lastPointerIdx ) {
          newStr.append( word.substring( lastPointerIdx, foundIdx ) );
        }
        newStr.append( newSeg );
        lastPointerIdx = foundIdx + replace.length();
      }
    }
    while ( foundIdx > -1 );
    return newStr.toString();
  }
}
