package com.activenetwork.awo.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class TokenizedStringBuilder {
  private enum IgnoreType { NONE, NULLS, EMPTY, CONVERTNULL }
  
  /** Indicates to ignore nulls, empty strings or convert nulls to empty strings */
  private IgnoreType ignoreType = IgnoreType.NONE;
  
  /**
   * The String
   */
  private StringBuilder data = new StringBuilder();

  /**
   * String-based representation of the default token.  We use a String here in case the token is more than 
   * one character long.
   */
  private String delimiter;

  /** Should a delimiter lead the string */
  private boolean prefix = false;
  
  /** Should a delimiter end the string (only when something is appended) */
  private boolean suffix = false;
  
  /** Indicates if any data has been appended. (Just in case empty data is appended) */
  private boolean appended = false;
  
  /** A list of formatters that can be used to transform the objects being passed in */
  private List<Formatter> formatters = new ArrayList<Formatter>();

  /**
   * Creates a new {@link TokenizedStringBuilder} whose content data is a new empty String
   * @param delim   Delimiting String ( should prefix with delimiter...default false )
   */
  public TokenizedStringBuilder( String delim ) {
    this.delimiter = delim;
  }

  /**
   * Constructs a new {@link TokenizedStringBuilder} whose token is a tab
   */
  public TokenizedStringBuilder() {
    this( "\t" );
  }
  
  public TokenizedStringBuilder ignoreNull() {
    this.ignoreType = IgnoreType.NULLS;
    return this;
  }
  
  public TokenizedStringBuilder convertNullsToEmptyString() {
    this.ignoreType = IgnoreType.CONVERTNULL;
    return this;
  }
  
  public TokenizedStringBuilder ignoreEmptyStrings() {
    this.ignoreType = IgnoreType.EMPTY;
    return this;
  }

  /**
   * Appends the tokens to the tokenized String
   * @param token The tokens to be appended
   */
  public TokenizedStringBuilder add( String... tokens ) {
    return add( (Object[])tokens );
  }
  
  /**
   * Prefix the string with the delimiter ( if something is appended )
   * @return
   */
  public TokenizedStringBuilder prefix() {
    prefix = true;
    return this;
  }
  
  /**
   * Suffix the string with the delimiter ( if something is appended )
   * 
   * @return
   */
  public TokenizedStringBuilder suffix() {
    suffix = true;
    return this;
  }
  
  /**
   * Prefix and suffix this string with the delimiter ( if anything is appended )
   * @return
   */
  public TokenizedStringBuilder surround() {
    prefix = true;
    suffix = true;
    return this;
  }
  
  /**
   * Appends the tokens to the tokenized String
   * @param token The tokens to be appended
   */
  public TokenizedStringBuilder add( Object... tokens ) {
    if ( tokens == null ) return this;
    
    for ( Object token : tokens ) {
      
      for( Formatter formatter : formatters ) 
        token = formatter.format( token );
      
      String value = token == null ? null : token.toString();
      
      if ( value == null && ignoreType == IgnoreType.CONVERTNULL ) value = "";
      
      if ( !( (value == null && ignoreType == IgnoreType.NULLS ) ||  (StrUtil.isEmpty( value ) && ignoreType == IgnoreType.EMPTY ) ) ) {
        if ( prefix || appended ) data.append( delimiter );
        appended = true;
        data.append( value );
      }
      
    }
    return this;
  }
  
  /**
   * Appends the tokens to the tokenized String
   * @param token The tokens to be appended
   */
  public TokenizedStringBuilder add( Collection<?> tokens ) {
    if ( tokens == null ) return this;
    
    return add( tokens.toArray() );
  }
  
  public TokenizedStringBuilder add( long number ) {
    return add( "" + number );
  }

  /**
   * Appends the key values to this using the seperator between key and value.
   * For example: calling with a map  key is a and value is b and key is c and value is d like this:
   * TokenizedStringBuilder( "," ).add( map, "=" ).toString();
   * would produce a=b,c=d
   * 
   * @param token The tokens to be appended
   * @param keyValueDelimeter the delimiter between key and value
   */
  public <K,V> TokenizedStringBuilder add( Map<K,V> tokens, String keyValueDelimeter ) {
    if ( tokens == null ) return this;
    
    for ( Map.Entry<K, V> entry : tokens.entrySet() ) 
      add( entry.getKey() + keyValueDelimeter + entry.getValue() );
    
    return this;
  }

  /**
   * @return    String-based representation using the data
   */
  @Override
  public String toString() {
    // Suffix is added just in time...
    return data.toString() + ( suffix && appended ? delimiter : "" );
  }

  /**
   * 
   * @return    A StringTokenizer for retrieving the data that were inserted into this String
   */
  public StringTokenizer getTokenizer() {
    return new StringTokenizer( toString(), delimiter );
  }
  
  public static TokenizedStringBuilder getIgnoreNullInstance( String delim ) {
    return new TokenizedStringBuilder( delim ).ignoreNull();
  }
  
  public static TokenizedStringBuilder getIgnoreEmptyInstance( String delim ) {
    return new TokenizedStringBuilder( delim ).ignoreEmptyStrings();
  }
  
  /**
   * For testing the class
   */
  public static void main( String[] args ) {

    TokenizedStringBuilder tb = new TokenizedStringBuilder( "|" );

    tb.add( "Fark.com" ).add( "Hobbe.com" ).add( "No throwing coconuts" );

    System.out.println( tb.toString() );
    StringTokenizer st = tb.getTokenizer();
    while ( st.hasMoreTokens() ) {
      System.out.println( st.nextToken() );
    }
    String aNull = null;
    show( new TokenizedStringBuilder( ", " ).add( "test" ) );
    show( new TokenizedStringBuilder( ", " ).add( "test", 245 ) );
    show( TokenizedStringBuilder.getIgnoreNullInstance( ", " ).add( "test" ) );
    show( TokenizedStringBuilder.getIgnoreNullInstance( ", " ).add( "test", aNull, "this" ) );

    show( new TokenizedStringBuilder( ", " ).ignoreNull().add( "test" ) );
    show( new TokenizedStringBuilder( ", " ).ignoreNull().add( "test" ).add( aNull ).add( "this", null, "that", null ) );
    show( new TokenizedStringBuilder( ", " ).ignoreEmptyStrings().add( "test" ) );
    show( new TokenizedStringBuilder( ", " ).ignoreEmptyStrings().add( "test" ).add( aNull ).add( "this", "", "that", null, "abc", "" ) );
    show( new TokenizedStringBuilder( ", " ).add( "test" ).add( aNull ).add( "this", "", "that", null, "abc", "" ) );
    show( new TokenizedStringBuilder( ", " ).convertNullsToEmptyString().add( "test", aNull, "this", "", "that", null, "abc", "" ) );
    show( new TokenizedStringBuilder( ", " )
      .addFormatter( new StringFormatter() {
          @Override public String formatString( String string ) { 
            return StrUtil.truncate( StrUtil.mixcase( string ), 3 ); } }
       ).add( "test", aNull, "this", "", "that", null, "abc", "" ) );
  }
  
  private static void show( Object str ) {
    System.out.println( ">" + str + "<" );
  }
  
  public TokenizedStringBuilder addFormatter( Formatter formatter ) {
    formatters.add( formatter );
    return this;
  }
  
  public interface Formatter {
    public Object format( Object o );
  }
  
  public abstract static class StringFormatter implements Formatter {
    /** {@inheritDoc} */
    @Override
    public Object format( Object o ) {
      return formatString( o == null ? null : o.toString() );
    }

    public abstract String formatString( String string );
  }

}
