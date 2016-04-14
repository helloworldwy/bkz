package com.activenetwork.awo.common;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.WordUtils;

//import com.reserveamerica.commons.Identifiable;
//import com.reserveamerica.commons.encrypt.MD5;
//import com.reserveamerica.framework.persistence.api.PropertyUtils;


/**
 * Class: StrUtil
 * Type: 
 *
 * Common utilities used with Strings.
 *
 * @author      David Ranalli
 * @version     $Header: /cvs/cvsroot/orms2cvs/java/src/com/reserveamerica/common/StrUtil.java,v 1.52 2009-05-13 17:12:54 dave Exp $
 * 
 *
 * Thread Safe: No
 * Reusable:    No
 * Immutable:   No
 */
public final class StrUtil {

  private static final String ACCENTED_UNICODE = "\u00C0\u00E0\u00C8\u00E8\u00CC\u00EC\u00D2\u00F2\u00D9\u00F9" // "AaEeIiOoUu"   grave
      + "\u00C1\u00E1\u00C9\u00E9\u00CD\u00ED\u00D3\u00F3\u00DA\u00FA\u00DD\u00FD" // "AaEeIiOoUuYy" acute
      + "\u00C2\u00E2\u00CA\u00EA\u00CE\u00EE\u00D4\u00F4\u00DB\u00FB\u0176\u0177" // "AaEeIiOoUuYy" circumflex
      + "\u00C3\u00E3\u00D5\u00F5\u00D1\u00F1" // "AaOoNn"       tilde
      + "\u00C4\u00E4\u00CB\u00EB\u00CF\u00EF\u00D6\u00F6\u00DC\u00FC\u0178\u00FF" // "AaEeIiOoUuYy" umlaut
      + "\u00C5\u00E5" // "Aa"           ring                              
      + "\u00C7\u00E7" // "Cc"           cedilla
      + "\u0150\u0151\u0170\u0171" // "OoUu"         double acute
  ;
  public static final String EMPTY = "";
  public static final String TRUE = "true";

  private StrUtil() {
    // prevent construction
  }

  /**
   * Similar to indexOf will null ( empty checks )
   * @param main
   * @param sub
   * @return
   */
  public static boolean contains( String main, String sub ) {
    if ( StrUtil.isEmpty( main ) ) return StrUtil.isEmpty( sub );
    return sub != null && main.indexOf( sub ) > -1;
  }

  /**
   * Returns true if string is null, of zero length or of zero length
   * when trimmed.
   * 
   * @param main String to check
   * @return boolean True if it is empty.
   */
  public static boolean isEmpty( String main ) {
    return main == null || main.trim().length() == 0;
  }

  /**
   * Determines if the given collection of Strings are all empty
   * @param strings Strings
   * @return        TRue if all empty
   */
  public static boolean areAllEmpty( String... strings ) {
    for ( String string : strings ) {
      if ( !isEmpty( string ) ) { return false; }
    }
    return true;
  }

  /**
   * Convience method for full getWord
   * The delimeter is set to a space.
   * 
   * @param source
   * @param wordNum
   * @return String
   */
  public static String getWord( String source, int wordNum ) {
    return getWord( source, wordNum, " " );
  }

  /**
   * Allows retrieval of specific words (sperated by a specific 
   * delimiter, which is regex!!!! becarefull of special characters in the delimeter ( like | ) ).
   * If a = "Hello there world" and called
   * getWord( a, 3, " " )
   * it would return "world"
   *  
   * getWord( a, 4, " " )
   * it would return ""
   * 
   * getWord( a, 1, " " )
   * it would return "Hello"
   *  
   * @param source Source string to search.
   * @param wordNum Word number to return.
   * @param delim Delimeter to split the source by.
   * @return String A portion of the Source as requested.
   */
  public static String getWord( String source, int wordNum, String delim ) {

    if ( isEmpty( source ) ) return source;
    if ( wordNum < 1 ) return "";

    String[] splitWords = source.split( delim );
    int index = wordNum - 1;

    return ( index >= splitWords.length ) ? "" : splitWords[index];
  }

  /**
   *  Encodes a URL.
   *  Note: 
   *    The World Wide Web Consortium Recommendation states that UTF-8 should be used. 
   *    Not doing so may introduce incompatibilities.
   * @param source
   * @return String
   */

  public static String encode( String source ) {
    try {
      return URLEncoder.encode( source, "UTF-8" );
    }
    catch ( UnsupportedEncodingException e ) {
      System.out.println( "error" );
    }

    return null;
  }

  /**
   * Get the toString representation without having to worry if the
   * Object is null. (null objects will return an empty string... not null! )
   * 
   * @param object 
   * @return String (never null unless object.toString returns null)
   */
  public static String toString( Object object ) {
    return ( object == null ) ? "" : object.toString();
  }

  /**
   * Get the toString representation without having to worry if the
   * Object is null. (null objects will return an empty string... not null! )
   * 
   * @param object 
   * @return String (never null unless object.toString returns null)
   */
  public static String toString( Object[] objects ) {
    return join( "," ).add( objects ).toString();
  }

  public static String toUpperCase( String object ) {
    return object == null ? "" : object.toUpperCase();
  }

  public static String toLowerCase( String object ) {
    return object == null ? "" : object.toLowerCase();
  }

  /**
   * converts string to its mix case representation
   * @param s
   * @return String
   */
  public static String mixcase( String object ) {

    if ( object == null ) { return ""; }

    char[] string = object.toLowerCase().toCharArray();
    boolean toUpperCase = true;
    char ch;
    int length = string.length;

    for ( int cnt = 0; cnt < length; cnt++ ) {
      ch = string[cnt];

      if ( toUpperCase ) {
        string[cnt] = Character.toUpperCase( ch );
        toUpperCase = false;
      }

      if ( ch == ' ' ) toUpperCase = true;
      if ( ch == '/' ) toUpperCase = true;
      if ( ch == '-' ) toUpperCase = true;
      if ( ch == '(' ) toUpperCase = true;
      if ( ch == '\'' || ch == '�' ) {
        // keep 's
        if ( ( cnt + 1 ) < length && string[cnt + 1] == 's'
            && ( ( cnt + 2 ) == length || ( ( cnt + 2 ) < length && string[cnt + 2] == ' ' ) ) ) {
          toUpperCase = false;
        }
        else {
          toUpperCase = true;
        }
      }

    }

    String done = metachars( new String( string ) );

    return done;
  }

  /**
   * finds '<' and '>' and changes them to &lt; and &gt; so 
   * they display properly on HTML pages
   * @param s
   * @return String
   */
  public static String metachars( String object ) {

    if ( object == null ) { return ""; }

    StringBuffer sb = new StringBuffer( object );
    StringBuffer out = new StringBuffer();
    int cnt = 0;

    while ( cnt < object.length() ) {
      if ( sb.charAt( cnt ) == '<' ) {
        out.append( "&lt;" );
      }
      else {
        if ( sb.charAt( cnt ) == '>' ) {
          out.append( "&gt;" );
        }
        else {
          out.append( sb.charAt( cnt ) );
        }
      }

      cnt++;
    }

    return ( out.toString() );
  }

  /**
   * Returns the string p_sCheck or an empty
   * string if p_sCheck is null.
   *
   * @param check The string to check if null.
   * @return either p_sCheck or an empty string if null.
   */
  public static String unNull( String check ) {
    return ( check == null ) ? "" : check;
  }

  /**
   * 
   * @param check
   * @param nullResult e.g. "--"
   * @return check or the nullresult
   */
  
  public static String unNull(String check, String nullResult) {
    if (nullResult == null) {
      return unNull(check);
    } else {
      return  ( check == null ) ? nullResult : check;
    }
  }
  /**
   * Converts all '*' to '%'
   * This should be called for prepared statements or QueryExpressions 
   * If you are adding directly into a sql statement (not as a prepared item) call addSqlFilterRaw
   * @param value
   * @return new string 
   */
  public static String addSqlFilter( String value ) {
    String sValue = value;

    if ( !isEmpty( sValue ) ) {
      sValue = sValue.replaceAll( "\\*", "%" );
      if ( !sValue.endsWith( "%" ) ) {
        sValue = sValue + "%";
      }
    }

    return sValue;
  }

  public static String addSqlContainingFilter( String value ) {
    String sValue = addSqlFilter( value );
    if ( !sValue.startsWith( "%" ) ) sValue = "%" + sValue;
    return sValue;
  }

  /**
   * Converts all '*' to '%'
   * This should be called for sql 
   * @param value
   * @return new string 
   */
  public static String addSqlFilterRaw( String value ) {
    String sValue = value;

    if ( !isEmpty( sValue ) ) {
      sValue = sValue.replaceAll( "\\*", "%" );
      sValue = cleanSQL( sValue );
      if ( !sValue.endsWith( "%" ) ) {
        sValue = sValue + "%";
      }
    }

    return sValue;
  }

  /**
   * For the times when it is impossible to use a prepared statement...
   * Remove any interfering characters from a raw sql string. 
   * Example: ( convert O'Brien to O''Brien ) 
   * @param field 
   * @return Cleaned sql
   */
  public static String cleanSQL( String field ) {
    return isEmpty( field ) ? field : field.replace( "'", "''" );
  }

  public static String sqlStr( String s ) {
    return isEmpty( s ) ? "NULL" : "'" + cleanSQL( s ) + "'";
  }


  // public static List splitCode( String sCode, String p_sSeparator ) {
  //
  // List listIDs = new ArrayList();
  // int j = sCode.indexOf( p_sSeparator );
  // int i = 0;
  //
  // // case when code starts and ends with p_sSeparator
  // if( j == 0 ) {
  // i = j + 1;
  // j = sCode.indexOf( p_sSeparator, i );// Skip first substring
  // }
  //
  // while( j >= 0 ) {
  // listIDs.add( sCode.substring( i, j ) );
  // i = j + 1;
  // j = sCode.indexOf( p_sSeparator, i );   // Rest of substrings
  // } 
  //
  // j = sCode.length();
  //
  // // case when code does not start and end with p_sSeparator
  // if( i < j ) {
  // listIDs.add( sCode.substring( i, j ) );
  // }
  //
  // return listIDs;
  // }

  /**
   * Will split a delimeted string into a list.
   * NOTE: This will not add items that are empty, so calling it with (",1,2,3,,,,4,", ",")
   * will return a list containing: 1 2 3 and 4
   * 
   * @param code String to be split
   * @param separator Delimeter to separate string with.
   * @return a list that is never null.
   */
  public final static List<String> splitCode( final String code, final String separator ) {

    List<String> listIDs = new ArrayList<String>();

    // check for valid parameters...
    if ( code == null ) return listIDs;
    if ( separator == null || separator.length() == 0 ) {
      listIDs.add( code );
      return listIDs;
    }
    int len = separator.length();

    int end = code.indexOf( separator );
    int start = 0;

    while ( end > -1 ) {

      if ( start < end ) {
        listIDs.add( code.substring( start, end ) );
      }

      start = end + len;
      end = code.indexOf( separator, start ); // Rest of substrings
    }

    end = code.length();
    // case when code does not start and end with p_sSeparator
    if ( start < end ) {
      listIDs.add( code.substring( start, end ) );
    }

    return listIDs;
  }

  /**
   * Will split a delimeted string into an Array.
   * See the splitCode method for further details
   * 
   * @param code String to be split
   * @param separator Delimeter to separate string with.
   * @return a String Array that is never null.
   */
  public final static String[] split( final String code, final String separator ) {
    return splitCode( code, separator ).toArray( new String[0] );
  }

  /**
   * This will return the left most part of the string (up to p_iCount Characters).
   * left( null, n ) will return null, left( "abc", 0 ) will return "",
   * left( "abc", 2 ) will return "ab" and left( "abc", 8 ) will return "abc"
   *  
   * @param string
   * @param count
   * @return see comments above
   */
  public static String left( String string, int count ) {
    String leftString = null;

    if ( string != null ) {
      if ( string.length() > count ) {
        if ( count > 0 )
          leftString = string.substring( 0, count );
        else
          leftString = "";
      }
      else {
        leftString = string;
      }
    }

    return leftString;
  }

  /**
   * This will return the right most part of the string (up to p_iCount Characters).
   * right( null, n ) will return null, right( "abc", 0 ) will return "",
   * right( "abc", 2 ) will return "bc" and right( "abc", 8 ) will return "abc"
   *  
   * @param string
   * @param count
   * @return see comments above
   */
  public static String right( String string, int count ) {
    String rightString = null;

    if ( string != null ) {
      if ( string.length() >= count && count > 0 )
        rightString = string.substring( string.length() - count, string.length() );
      else
        rightString = string;
    }

    return rightString;
  }

  /**
   * @param string
   */
  public static String varToBeanName( String string ) {
    if ( isEmpty( string ) ) return "";
    if ( !string.startsWith( "m_" ) ) return string;
    int index = 2;

    for ( int i = 2; i < string.length(); i++ ) {
      if ( Character.isUpperCase( string.charAt( i ) ) ) {
        index = i;
        break;
      }
    }

    return string.substring( index );
  }

  /**
   * @param string
   */
  public static String methodToBeanName( String string ) {
    if ( isEmpty( string ) || string.length() < 3 ) return "";
    if ( ! ( string.startsWith( "get" ) || string.startsWith( "is" ) ) ) return string;

    return ( string.startsWith( "get" ) ) ? string.substring( 3 ) : string.substring( 2 );
  }

  public static String replaceAll( String word, String replace, String newSeg ) {
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

  public static String replaceFirst( String word, String regex, String replacement ) {
    if ( word != null ) {
      if ( regex == null ) { return word; }

      if ( replacement == null ) {
        replacement = "";
      }

      return word.replaceFirst( regex, replacement );
    }

    return null;
  }

  public static String formatAsTitle( String toBeFormatted ) {
    StringBuilder returnStr = new StringBuilder();
    if ( !isEmpty( toBeFormatted ) ) {
      String[] result = toBeFormatted.split( "\\s+" );
      for ( int i = 0; i < result.length; i++ ) {
        if ( !StrUtil.isEmpty( result[i] ) ) {
          returnStr.append( Character.toUpperCase( result[i].charAt( 0 ) ) + result[i].toLowerCase().substring( 1 )
              + " " );
        }
      }
    }
    return returnStr.toString().trim(); //get rid of the prefix and trailing spaces
  }

  public static String formatFixedLength( String target, int length, char padChar, boolean leftPadding ) {
    target = StrUtil.unNull( target );
    if ( target.length() > length ) {
      target = target.substring( 0, length );
    } else {
      if ( leftPadding ) {
        target = StrUtil.lpad( target, length, padChar );
      } else {
        target = StrUtil.rpad( target, length, padChar );
      }
    }
    return target;
  }
  
  public static String formatFixedLengthUpper( String target, int length, char padChar, boolean leftPadding ) {
    return formatFixedLength( target, length, padChar, leftPadding ).toUpperCase();
  }
  
  public static String resolveKey( String string ) {
    return resolveKey( string, System.getProperties() );
  }

  public static String resolveKey( String string, Properties props ) {
    if ( string == null ) return string;
    String result = string;
    int start = string.indexOf( "${" );
    if ( start > -1 ) {
      int end = string.indexOf( "}", start );
      if ( end > -1 ) {
        String key = string.substring( start + 2, end );
        String value = props.getProperty( key );
        if ( value != null ) {
          result = replaceAll( string, "${" + key + "}", value );
        }
      }

    }
    return result;
  }

  public static void main( String[] args ) {
    long lStart = System.currentTimeMillis();
    //    for ( int i = 0; i < 100000; i++ ) {
    //      /*System.out.println( "m_Abacab " +*///varToBeanName("m_Abacab") ;
    //      /*System.out.println( "m_listAbacab " +*///varToBeanName("m_listAbacab") ;
    //      /*System.out.println( "m_lisTAbacab " +*/varToBeanName( "m_lisTAbacab" );
    //      /*System.out.println( "Abacab " +*///varToBeanName("Abacab") ;
    //      /*System.out.println( "m_iAbacab " +*///varToBeanName("m_iAbacab") ;
    //      /*System.out.println( "Abam_icab " +*///varToBeanName("Abam_icab") ;
    //      /*System.out.println( "m_abacab " +*///varToBeanName("m_abacab") ;
    //      /*System.out.println( "getAbacab " +*/// methodToBeanName("getAbacab") ;
    //      /*System.out.println( "isam_icab " +*///methodToBeanName("isam_icab") ;
    //      /*System.out.println( "asdfasdf " +*///methodToBeanName("asdfasdf") ;
    //    }
    //    System.out.println( "done in " + ( System.currentTimeMillis() - lStart ) );
    //    Properties props = new Properties();
    //    props.setProperty( "dave.test", "it worked!" );
    //    System.out.println( mixcase( "O'BRIEN, JERRY" ));
    //    System.out.println( mixcase( "O'BRIEN'S MUDHOLE"));
    //    System.out.println( mixcase( "O'BRIEN'S"));
    //    System.out.println( mixcase( "O'SULLIVAN"));
    //    System.out.println( mixcase( "O�brien�s List-O-Matic"));
    //    System.out.println( resolveKey( "a${dave.test}a", props ) );
    //    
    //    System.out.println( addSqlFilter( "%O*'Brian*" ));
    //    
    //    System.out.println( addSqlFilter( "%O*''Brian*'" ));

    //    System.out.println(replaceAll("m_listAsvfdgvdfgdgd", "li", "LI"));
    //    String pattern = "aaa\'{0}\'fdsf\'{1}\'fdddddddddd\'{2}\'bbbbbbbbbbbbbbb\'{3}\'ffffff\'{4}\'dfsdfdf";
    //    System.out.println(replaceAll(replaceAll(replaceAll(pattern, "\'", "''"), "{3}", "3333333333"), "dfsdfdf", "ENDDDDDDD"));
    //    System.out.println(replaceAll(replaceAll(replaceAll(pattern, "\'", "''"), "{3}", "3333333333"), "{", "["));

    //    TopicConnectionFactory oFactory;
    //
    //    String sTopicConnectionName = "ConnectionFactory";
    //
    //    try {
    //      Naming oName = Naming.getService();
    //
    //      // This is standard JMS initialization.      
    //      oFactory = (TopicConnectionFactory) oName.get( sTopicConnectionName );
    //      
    //      TopicConnection m_oConnection = oFactory.createTopicConnection();
    //      TopicSession m_oTopicSession =
    //        m_oConnection.createTopicSession( false, Session.AUTO_ACKNOWLEDGE );
    //      
    //      TopicPublisher m_oPublisher = m_oTopicSession.createPublisher( null );
    //      // Start this connection up so events are triggered.
    //      m_oConnection.start();
    //      
    //    } catch( Exception p_oExc ) {
    //      
    //      throw new RuntimeException( "Can not initialize JmsEvent Framework.", p_oExc );
    //      
    //    }
    //    lStart = System.currentTimeMillis();
    //    for ( int i = 0; i < 10000000; i++ ) {
    //      "asdfjASDFASDFjdf".toUpperCase().equals( "asdfjASDFASDFjdF".toUpperCase() );
    //    }
    //    System.out.println( "toUpper done in " + ( System.currentTimeMillis() - lStart ) );
    //    
    //    lStart = System.currentTimeMillis();
    //    for ( int i = 0; i < 10000000; i++ ) {
    //      "asdfjASDFASDFjdf".equals( "asdfjASDFASDFjdF" );
    //    }
    //    System.out.println( "reg. equals done in " + ( System.currentTimeMillis() - lStart ) );
    //    
    //    lStart = System.currentTimeMillis();
    //    for ( int i = 0; i < 10000000; i++ ) {
    //      "asdfjASDFASDFjdf".equalsIgnoreCase( "asdfjASDFASDFjdF" );
    //    }
    //    System.out.println( "ignore case done in " + ( System.currentTimeMillis() - lStart ) );

    //    Map<String, String> stuff = new HashMap<String, String>();
    //    stuff.put( "dave", "value" );
    //    stuff.put( "dave2", null );
    //    stuff.put( "dave", "value" );
    //    
    //    assert combine( null, null, "", null ) == null;
    //    assert combine( null, null, "dave", null, null).equals( "dave" );
    //    assert combine( null, "d", "", "a", null, "v", "", null, "e" ).equals( "dave" );
    //    assert combine( ",", "d", "", "a", null, "v", "", null, "e" ).equals( "d,a,v,e" );
    //    
    //    assert prepend( " ", "dave" ).equals( "dave" );
    //    assert prepend( "d", "ave" ).equals( "dave" );
    //    assert prepend( "", null ) == null;

    
//    String[] test = {
//        "larry",
//        "curly",
//        null,
//        "Special"
//    };
//    
//    List<String> list = Arrays.asList( test );
//    Collections.sort( list, new Comparator<String>() {
//
//      @Override
//      public int compare( String o1, String o2 ) {
//        return doAlphanumericCompare( o1, o2 );
//      }} );
//    
//    System.out.println( "Asc:\t" + list );
//    
//    list = Arrays.asList( test );
//    Collections.sort( list, new Comparator<String>() {
//
//      @Override
//      public int compare( String o1, String o2 ) {
//        return doAlphanumericCompare( o1, o2, false, false );
//      }} );
//    System.out.println( "Desc:\t" + list );
  }

  /**
   * @param source
   * @param strings
   * @return
   */
  public static boolean in( String source, String... strings ) {
    for ( String element : strings ) {
      if ( source.equalsIgnoreCase( element ) ) return true;
    }
    return false;
  }

  public static String toAZaz09String( String wordToEscape, String otherAllowedCharacters, boolean replaceWithSpace ) {
    if ( wordToEscape == null ) return null;
    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numeric = "0123456789";
    if ( otherAllowedCharacters == null ) otherAllowedCharacters = "";
    String specialChar = lowerCase + numeric + upperCase + otherAllowedCharacters
        + "��������������������������������������������������������";
    String convertChar = lowerCase + numeric + upperCase + otherAllowedCharacters
        + "AAAAAACEEEEIIIIDNOOOOOxOUUUUYaaaaaaceeeeiiiinoooooouuuuy";
    StringBuffer buffer = new StringBuffer();
    for ( int i = 0; i < wordToEscape.length(); i++ ) {
      if ( specialChar.indexOf( wordToEscape.charAt( i ) ) > -1 ) {
        buffer.append( convertChar.charAt( specialChar.indexOf( wordToEscape.charAt( i ) ) ) );
      }
      else if ( replaceWithSpace ) buffer.append( ' ' );
    }
    return buffer.toString();
  }

  /**
   * This method will return properly XML encoded string
   * @param wordToEncode
   * @return
   */
  public static String getXmlEncoded( String wordToEncode ) {

    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numeric = "0123456789";
    String special = "!@#$%^*()-_+=[]{}|`~;:,.? ";
    String allowedChars = lowerCase + upperCase + special + numeric;

    if ( wordToEncode == null ) { return null; }
    StringBuffer buffer = new StringBuffer();
    for ( int i = 0; i < wordToEncode.length(); i++ ) {
      switch ( wordToEncode.charAt( i ) ) {
        case '<' :
          //MD - Defect #4604 - replace with the entity reference
          //buffer.append( " " );
          buffer.append( "&#60;" );
          break;
        case '>' :
          //MD - Defect #4604 - replace with the entity reference
          //buffer.append( " " );
          buffer.append( "&#62;" );
          break;
        case '&' :
          buffer.append( ";" );
          break;
        case '"' :
          //MD - Defect #4604 - replace with the entity reference
          //buffer.append( " " );
          buffer.append( "&#34;" );
          break;
        case '\'' :
          //MD - Defect #4604 - replace with the entity reference
          //buffer.append( " " );
          buffer.append( "&#39;" );
          break;
        //MD - seem to need the entity number rather than name for the below:
        case '\u00e9' :
          //oBuffer.append( "&eacute;" );
          buffer.append( " " );
          break;
        case '\u00e8' :
          //oBuffer.append( "&egrave;" );
          buffer.append( " " );
          break;
        case '\u00e7' :
          //oBuffer.append( "&ccedil;");
          buffer.append( " " );
          break;
        case '\u00e2' :
          //oBuffer.append( "&acirc;");
          buffer.append( " " );
          break;
        default :
          //MD - leave out non-standard chars that may not be parsed properly.
          if ( allowedChars.indexOf( wordToEncode.charAt( i ) ) > -1 ) {
            buffer.append( wordToEncode.charAt( i ) );
          }
          else {
            buffer.append( "" );
          }
      }
    }

    return buffer.toString();
  }

  public static String stripSpace( String s ) {

    StringBuilder result = new StringBuilder();
    StringTokenizer tk = new StringTokenizer( s );
    if ( tk.hasMoreElements() ) result.append( tk.nextToken() );

    while ( tk.hasMoreElements() )
      result.append( " " ).append( tk.nextToken() );
    return result.toString();
  }

  //AS - 4Mar10 - added default to fix map issues
  public static String truncateCoord( String c ) {
    return truncateCoord( c, "" );
  }

  //AS - 6Apr06 -  used to truncate map coordinates  
  public static String truncateCoord( String c, String defStr ) {
    String result = defStr;
    if ( c == null ) { return result; }
    double dc = NumUtil.dVal( c );
    result = String.valueOf( NumUtil.round( dc ) );
    return result;
  }

  public static boolean isNum( char c ) {

    String lsNumbers = "0123456789";
    String lsCheck = String.valueOf( c );

    return ( lsNumbers.indexOf( lsCheck ) > -1 );
  }

  /** This method checks to see if a string is made up of all numbers.

  *
  * @param String s -- the string to check
  * @return boolean -- return true if all chars in the string are numeric
  *                 -- return false otherwise
  */

  public static boolean isNum( String s ) {

    boolean lbChecker = true;
    String lsNumbers = "0123456789";

    for ( int i = 0; i < s.length(); i++ ) {
      char lsSub = s.charAt( i );
      if ( lsNumbers.indexOf( lsSub ) == -1 ) return false;
    }

    return lbChecker;
  }

  /**
   * To determine if a string only contains alphanumeric characters and other specified characters
   * @param s -- the string to be checked
   * @param chars -- specified characters
   * @return true if the string only contains alphanumeric characters and other specified characters, false otherwise
   */
  public static boolean isAlphaNumericOrSpecifiedChar( String s, char... chars ) {
    if ( isEmpty( s ) ) return false;

    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( !isAlphaNumeric( c ) && !isOneOfSpecifiedChars( c, chars ) ) return false;
    }

    return true;
  }

  /**
   * To determine if a character is one of the specified characters
   * @param c -- the character to be checked
   * @param chars -- specified characters 
   * @return true if the character is one of the specified characters, false otherwise
   */
  public static boolean isOneOfSpecifiedChars( char c, char... chars ) {
    for ( char item : chars ) {
      if ( c == item ) return true;
    }

    return false;
  }

  public static boolean isAlphaNumeric( String s ) {
    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( !isAlphaNumeric( c ) ) return false;
    }
    return true;
  }

  public static boolean isAlphaNumeric( char c ) {
    boolean isNum = c >= '0' && c <= '9';
    boolean isAlpha = ( c >= 'A' && c <= 'Z' ) || ( c >= 'a' && c <= 'z' );
    if ( !isNum && !isAlpha )
      return false;
    else
      return true;
  }

  /** This method removes the leading characters of a string up until the first digit .
   *  Is intended to extract the number from strings like:  "m_123", "-123", "W123.456", etc.  
   *
   * @param String s -- the string to check
   * @return boolean -- return the string without the leading characters
   */

  public static String lTrimChar( String s ) {

    String lsNumbers = "0123456789";
    int l = s.length();
    for ( int i = 0; i < s.length(); i++ ) {
      String lsSub = String.valueOf( s.charAt( i ) );
      if ( lsNumbers.indexOf( lsSub ) >= 0 ) break;
      l--;
    }
    return right( s, l );
  }

  /**
   * Removes any leading zeros from the input string.
   * Example:   
   * 00000654645 - 654645
   * 00000       - 0
   * 0           - 0
   * 000567000   - 567000
   * 
   * @param string   - input string
   * @return  string without leading zeros   
   */
  public static String trimLeadingZeros( String string ) {
    if ( isEmpty( string ) ) return string;
    return string.replaceAll( "^0+(?!$)", "" );
  }

  /**
   * Limits the length of the given String to the desired max length in chars
   * @param string            String whose length we wish to limit
   * @param   desiredLength   Desired max length  
   * @return                  String whose length has been so limited.
   */
  public static String limitLength( String string, int desiredLength ) {

    if ( null != string && string.length() > desiredLength ) { return string.substring( 0, desiredLength ); }

    return string;

  }

  /**
   * <p> Creates an MD5 has out of string and then converts it into its hex representation</p>
   * Null strings don't get hashed
   *
   * @param string object
   * @return 32-character hex representation of the hash of the object
   */
//  public static String getMD5Hash( String s, boolean caseSensitive ) {
//    if ( s == null ) return "";
//
//    MD5 md5 = new MD5();
//
//    md5.Init();
//    md5.Update( caseSensitive ? s : s.toLowerCase() );
//
//    return md5.asHex();
//  }

//  public static String getMD5Hash( String s ) {
//    return getMD5Hash( s, false );
//  }


  /**
    * Replaces all single apostrophes with double-apostrophes, making
    * the return value suitable for raw SQL strings. <br> 
    * E.g., If string = "foo's bar's" then <br>
    * String sql = "select * from table where name = '" + StrUtil.escapeApostrophes( string ) + "'"; <br>
    * becomes <br>
    * select * from table where name = 'foo''s bar''s'
    * @param string
    * @return new String object or null if original string == null
    */
  public static String escapeApostrophes( String string ) {
    if ( string == null ) { return string; }
    String result = string;

    // Escape any apostrophes 
    int apos = result.indexOf( "'", 0 );
    int doubleApos = result.indexOf( "''", 0 );
    while ( apos > -1 && apos != doubleApos ) {
      result = result.substring( 0, apos ) + "''" + result.substring( apos + 1 );

      apos = result.indexOf( "'", apos + 2 );
      doubleApos = result.indexOf( "''", apos );
    }
    return result;
  }

  /**
   * Formats 10-digit phone or fax number to have dashes
   * e.g. 9995555555 to 999-555-5555
   * 
   * @param number
   * @return String
   */
  public static String formatPhoneNumber( String number ) {
    String result = "";
    if ( number != null ) {
      if ( number.length() == 10 ) {
        result = number.substring( 0, 3 ) + "-" + number.substring( 3, 6 ) + "-" + number.substring( 6, 10 );
      }
      else {
        //TODO: add other checks
        result = number;
      }
    }
    return result;
  }

  /**
   * Formats 10-digit phone or 7-digit phone
   * e.g. 9995555555 to (999) 555-5555
   *       1234567 to 123-4567
   * @param number
   * @return String
   */
  public static String convertPhoneNumber( String number ) {
    String result = "";
    if ( number != null ) {
      if ( number.length() == 10 ) {
        result = "(" + number.substring( 0, 3 ) + ")" + " " + number.substring( 3, 6 ) + "-" + number.substring( 6, 10 );
      }
      else if ( number.length() == 7 ) {
        result = number.substring( 0, 3 ) + "-" + number.substring( 3, 7 );
      }
      else if ( number.length() == 11 ) {
        result = number.substring( 0, 3 ) + "-" + number.substring( 3, 7 ) + "x" + number.substring( 7, 11 );
      }
      else if ( number.length() == 14 ) {
        result = "(" + number.substring( 0, 3 ) + ")" + " " + number.substring( 3, 6 ) + "-" + number.substring( 6, 10 )
            + "x" + number.substring( 10, 14 );
      }
      else {
        //TODO: add other checks
        result = number;
      }

    }
    return result;
  }

  /**
   * Formats 10-digit phone or fax number to have parenthesis
   * e.g. 9058661234 to (905)866-1234
   * @param number - phone number to be formatted
   * @return formatted telephone number
   */
  //assume all non-numeric characters have been stripped out
  public static String formatPhoneNumberWithParenthesis( String number ) {
    StringBuilder sb = new StringBuilder();
    if ( !isEmpty( number ) ) {
      if ( number.length() == 10 ) {
        sb.append( "(" ).append( number.substring( 0, 3 ) ).append( ") " )
          .append( number.substring( 3, 6 ) ).append( "-" ).append( number.substring( 6 ) );
      }
      else if ( number.length() == 7 ) {
        sb.append( number.substring( 0, 3 ) ).append( "-" ).append( number.substring( 3 ) );
      }
      else {
        sb.append( number );
      }
    }
    return sb.toString();
  }

  //assume param number already passed validation "isValidPhoneOrFaxNumber(String s)"
//  public static String formatedPhoneNumber( String number ) {
//
//    if ( number == null ) return "";
//    if ( PropertyUtils.propertyIsTrue( "NeedPhoneNumberFormat" ) ) {
//      String extNumber = null;
//      number = unformatedPhoneNumber( number );
//      if ( number.contains( "x" ) ) {
//        extNumber = number.substring( number.indexOf( "x" ) + 1 );
//        number = number.substring( 0, number.indexOf( "x" ) );
//      }
//
//      if ( number.length() == 10 )
//        number = "(" + number.substring( 0, 3 ) + ")" + " " + number.substring( 3, 6 ) + "-" + number.substring( 6, 10 );
//
//      if ( extNumber != null ) number += " x " + extNumber;
//
//      return number;
//    }
//    else {
//      return getFilteredPhoneNumber( number );
//    }
//  }

  //assume param number already passed validation "isValidPhoneOrFaxNumber(String s)"
//  public static String unformatedPhoneNumber( String phone ) {
//    if ( PropertyUtils.propertyIsTrue( "NeedPhoneNumberFormat" ) ) {
//      StringBuilder sResult = new StringBuilder();
//      if ( !StrUtil.isEmpty( phone ) ) {
//        char[] chars = phone.toCharArray();
//        for ( int i = 0; i < chars.length; i++ ) {
//          if ( ( chars[i] >= '0' && chars[i] <= '9' ) || chars[i] == '+' ) {
//            sResult.append( chars[i] );
//          }
//          else if ( chars[i] == 'X' || chars[i] == 'x' ) sResult.append( 'x' );
//        }
//      }
//      String s = sResult.toString();
//      if ( s.length() >= 11 && s.charAt( 0 ) == '1' && ( s.length() == 11 || s.charAt( 11 ) == 'x' ) )
//        s = s.substring( 1 );
//
//      return s;
//    }
//    else {
//      return getFilteredPhoneNumber( phone );
//    }
//  }

  public static String formatPhoneNumberFormattedOrNot( String phone ) {
    StringBuilder sb = new StringBuilder();
    if ( !StrUtil.isEmpty( phone ) ) {
      String countryCode = null;
      String extension = null;
      if ( phone.indexOf( "+" ) > -1 || phone.indexOf( "x" ) > -1 ) {
        if ( phone.indexOf( "+" ) > -1 ) {
          countryCode = phone.substring( 0, phone.indexOf( "+" ) ).trim();
          phone = phone.substring( phone.indexOf( "+" ) + 1 ).trim();
        }
        if ( phone.indexOf( "x" ) > -1 ) {
          extension = phone.substring( phone.indexOf( "x" ) + 1 ).trim();
          phone = phone.substring( 0, phone.indexOf( "x" ) ).trim();
        }
        phone = getFilteredPhoneNumber( phone );
      } 
      else {
        phone = getFilteredPhoneNumber( phone );
        if ( phone.length() == 11 ) {
          countryCode = phone.substring( 0, 1 ).trim();
          phone = phone.substring( 1 ).trim();
        }
        else if ( phone.length() == 14 ) {
          extension = phone.substring( phone.indexOf( "x" ) + 1 ).trim();
          phone = phone.substring( 0, phone.indexOf( "x" ) ).trim();
        }
        else if ( phone.length() > 14 ) {
          countryCode = phone.substring( 0, phone.length() - 14 ).trim();
          extension = phone.substring( phone.length() - 4 ).trim();
          phone = phone.substring( phone.length() - 14, phone.length() - 4 ).trim();
        }
      }
      if ( !StrUtil.isEmpty( countryCode ) ) {
        sb.append( "+" ).append( countryCode ).append( " " );
      }
      sb.append( formatPhoneNumberWithParenthesis( phone ) );
      if ( !StrUtil.isEmpty( extension ) ) {
        sb.append( "x" ).append( extension );
      }
    }
    return sb.toString();
  }
  
  /**
   * Determines if the two {@link String}s are the same by comparing the {@link #unNull(String) un-nulled} versions of each with eachother
   * @param str1        First string
   * @param str2        Second string
   * @param bIgnorecase If this is true then comparison is done ignorant of case
   * @return            True if the two un-nulled versions of str1 and str2 respectively are the same
   */
  public static boolean isSame( String str1, String str2, boolean bIgnorecase ) {
    if ( bIgnorecase ) return StrUtil.unNull( str1 ).equalsIgnoreCase( StrUtil.unNull( str2 ) );

    return StrUtil.unNull( str1 ).equals( StrUtil.unNull( str2 ) );
  }

  /**
   * Determines if the two {@link String}s are the same by comparing the {@link #unNull(String) un-nulled} versions of each with eachother
   * @param str1        First string
   * @param str2        Second string
   * @return            True if the two un-nulled versions of str1 and str2 respectively are the same
   */
  public static boolean isSame( String str1, String str2 ) {
    return StrUtil.unNull( str1 ).equals( StrUtil.unNull( str2 ) );
  }

  /**
   * Process a block of text that may contain html tags. For the most part, this will strip out html tags with the
   * following exceptions:
   *   <a href>...</a> tags will be retained
   *   <br>, <br/>, <p> and <p/> will be converted to newlines.
   *   
   * The rules for HTML processing were specified in PCR 903  
   *     
   * @param content
   * @return the processd content with html tags removed
   */
  public static String stripHtmlTags( String content ) {

    if ( StrUtil.isEmpty( content ) ) return content;

    // First we'll process the elements that we don't want completely stripped from the output.
    // <br>, <br/>, <p> and <p/> should be converted to newlines.
    String processedString = content.replaceAll( "(?i)<(?:(?:br)||(?:p))\\s*/?>", "\n" );

    // For any anchors (<a ...> elements), change the angle brackets(<>) to carets (^)
    // Note that we need to use the "non-greedy" (?) operator following the * and + so that
    // the expression doesn't try to take up as many characters as possible. Otherwise, the > from 
    // some other tag further ahead in the document will be used to complete the expression.
    // For example, in the text: <a href="abc" >link</a> and some <b>bold</b> text
    // "href="abc" >link</a> and some <b>bold</b" would be a greedy match for the following expression.
    // Adding the non-greedy operator (?) makes the expression take as few characters as required to match
    processedString = processedString.replaceAll( "(?i)<a( .*?)?>", "^a$1^" ).replaceAll( "(?i)</a\\s*>", "^/a^" );

    // Now get rid of any existing html tags. This is a straight removal. There is a danger that
    // any < or > characters in the text (not intended as tags) will be misinterpreted as tags and the content
    // between them will be removed. This was understood and accepted by the BA as the risk is small.
    // We'll make sure there is at least 1 non-whitespace character at the beginning of the potential tag.
    processedString = processedString.replaceAll( "<\\S+?\\s*>", "" ).replaceAll( "<\\S+?\\s*\\S+?>", "" );

    // Now we want to put the anchor tags back the way they were (replace the carets with angle brackets)
    // Note that caret is a special regex character and needs to be escaped
    processedString = processedString.replaceAll( "\\^a( .*?)?\\^", "<a$1>" ).replaceAll( "\\^/a\\^", "</a>" );
    return processedString;
  }

  public static String convertListToString( List<String> l ) {
    return convertCollectionToString( l, false, ", " );
  }

  public static String convertListToString( List<String> l, boolean forSQL ) {
    return convertCollectionToString( l, forSQL, ", " );
  }

  public static String convertLongListToString( List<Long> l ) {
    List<String> s = new ArrayList<String>( l.size() );
    for ( Long id : l ) {
      s.add( id.toString() );
    }
    return convertCollectionToString( s, false, ", " );
  }

//  public static String convertIdentifiableCollectionToString( Collection<? extends Identifiable> c, boolean forSQL, String delimiter ) {
//    if ( ColUtil.isEmpty( c ) ) {
//      return "";
//    }
//    delimiter = delimiter == null ? "," : delimiter;
//    StringBuilder builder = new StringBuilder();
//    for ( Identifiable id: c ) {
//      if ( builder.length() != 0 ) {
//        builder.append( delimiter );
//      }
//      String val = id.getId().toString();
//      if ( forSQL ) {
//        builder.append( "'" ).append( cleanSQL( val ) ).append( "'" );
//      }
//      else {
//        builder.append( val );        
//      }
//    }
//    return builder.toString();
//  }
  
  public static String convertCollectionToString( Collection<String> l, boolean forSQL, final String delimiter ) {

    String delim = ( delimiter == null ? ", " : delimiter );
    StringBuffer buff = new StringBuffer();
    for ( String str : l ) {
      buff.append( forSQL ? "'" : "" ).append( str ).append( forSQL ? "'" : "" ).append( delim );
    }
    String rtn = buff.toString();
    if ( !StrUtil.isEmpty( rtn ) && rtn.length() > delim.length() ) {
      rtn = rtn.substring( 0, rtn.length() - delim.length() );
    }

    return rtn;
  }

  /**
   * Catenates Strings, placing the delimiter (if not null) in between. Null or empty Strings are ignored.<br>
   * E.g., catenate( ";", "abc", "", "def" ) returns "abc;def"
   * @param delimiter - can be null
   * @param str - if empty (null or "" ), it will be ignored
   * @return catenated String
   */
  public static String catenate( final String delimiter, final String... str ) {
    if ( str == null ) { return null; }
    List<String> strList = new ArrayList<String>();
    for ( String text : str ) {
      if ( !isEmpty( text ) ) {
        strList.add( text );
      }
    }
    return convertCollectionToString( strList, false, StrUtil.unNull( delimiter ) );
  }

  public static String truncateTo( String s, int maxLen ) {
    if ( s != null && s.length() > maxLen && maxLen > 5 ) return s.substring( 0, maxLen - 5 ) + "...";

    return s;
  }

  /**
   * Returns a new truncated string if the length of this string is greater than the specified max. length, 
   * otherwise, returns this string
   * @param source
   * @param maxLen
   * @return
   */
  public static String truncate( String source, int maxLen ) {
    return ( source != null && source.length() > maxLen ) ? source.substring( 0, maxLen ) : source;
  }

  public static String getStackTraceString( Throwable t ) {
    StringBuffer buffer = new StringBuffer();
    getStackTraceString( buffer, t, true );
    return buffer.toString();
  }

  private static void getStackTraceString( StringBuffer buffer, Throwable t, boolean firstException ) {
    buffer.append( ( firstException ? "Error:" : "Caused By:" ) + t.getMessage() );
    for ( int i = 0; i < t.getStackTrace().length; i++ ) {
      buffer.append( "\n... " + t.getStackTrace()[i].toString() );
    }
    if ( t.getCause() != null ) {
      getStackTraceString( buffer, t.getCause(), false );
    }
  }

  public static long getLengthInBytes( String characterSetName, String value ) {
    try {
      return value.getBytes( characterSetName ).length;
    }
    catch ( Exception e ) {
      return 0;
    }
  }

  public static long getLengthInBytes( String characterSetName, StringReader reader ) {
    StringBuilder builder = new StringBuilder();
    int c;
    try {
      reader.mark( 0 );
      while ( ( c = reader.read() ) != -1 )
        builder.append( (char)c );
    }
    catch ( Exception e ) {}
    finally {
      try {
        reader.reset();
      }
      catch ( IOException e ) {}
    }
    return getLengthInBytes( characterSetName, builder.toString() );
  }

  /**
   * Check size without worrying about the string being null.
   * ( null strings are 0 length ).
   * @param strToCheck
   * @return size of strToCheck or 0 if null.
   */
  public static int length( String password ) {
    return toString( password ).length();
  }

  public static int indexOf( String string, String[] strs ) {

    if ( strs != null ) for ( int i = 0; i < strs.length; i++ )
      if ( string.equalsIgnoreCase( strs[i] ) ) return i;

    return -1;
  }

  /**
   * Parses text into a series of lines with length <= width.  Lines will be split 
   * where spaces exist in the text.  If text contains a single word of length > width, 
   * then one line will contain that word.
   * <br><br>
   * If text is empty, or width <= 0, the result will be a single-element array containing 
   * the empty string, "". 
   * <br>
   * For example:
   * <pre>String[] line = parseIntoLines( "The next implementation will be in July", 10 );</pre> 
   * returns:
   * <pre>
   * line[0] = "The next"
   * line[1] = "implementation"
   * line[2] = "will be in"
   * line[3] = "July"
   * </pre>
   * @param text - the text to be parsed
   * @param width - desired maximum line width
   * @return an array containing separated lines of the given text
   */
  public static String[] parseIntoLines( final String text, final int width ) {

    if ( isEmpty( text ) ) { return new String[]{ text }; }
    if ( width <= 0 ) { return new String[]{ "" }; }

    List<String> result = new ArrayList<String>();
    StringBuffer line = new StringBuffer();

    // Get each word, delimited by space
    String[] words = text.split( "\\s" );

    for ( String word : words ) {

      if ( line.length() + word.length() < width ) {
        // have room to add word to the current line
        if ( line.length() > 0 ) {
          line.append( ' ' );
        }
      }
      else if ( line.length() > 0 ) {
        // reached maximum width; save line and start a new one
        result.add( line.toString() );
        line = new StringBuffer();
      }

      line.append( word );
      //System.out.println( "line: " + line.toString() );
    }

    // save the last line
    if ( line.length() > 0 ) {
      result.add( line.toString() );
    }

    return result.toArray( new String[result.size()] );
  }


  /**
   * Wraps text into lines of length <= width, inserting html
   * line-break tags where spaces exist.
   * @see parseIntoLines( text, width )
   * @param text - text to wrap
   * @param width - maximum width of each line
   * @return String containing text with html line-breaks inserted
   */
  public static String htmlWrap( final String text, final int width ) {
    StringBuffer wrapped = new StringBuffer();
    String[] lines = parseIntoLines( text, width );
    for ( String line : lines ) {
      if ( wrapped.length() > 0 ) {
        wrapped.append( "<br/>" );
      }
      wrapped.append( line );
    }
    return wrapped.toString();
  }

  /**
   * Strips out non-numeric characters from a String
   * @param phone   String to be treated as a phone number
   * @return        Phone number without non-numeric characters in it.
   */
  public static String getFilteredPhoneNumber( String phone ) {
    StringBuilder sResult = new StringBuilder();
    if ( !StrUtil.isEmpty( phone ) ) {
      char[] chars = phone.toCharArray();
      for ( int i = 0; i < chars.length; i++ ) {
        if ( chars[i] >= '0' && chars[i] <= '9' ) {
          sResult.append( chars[i] );
        }
      }
    }
    return sResult.toString();
  }

  public static String validateMapValues( String value ) {
    if ( NumUtil.dVal( value ) < 0 ) {
      value = "0";
    }
    if ( !value.equals( "0" ) ) {
      value = StrUtil.truncateCoord( value );
    }
    return value;

  }

  public static String getFileSuffix( String fileName ) {
    String retStr = null;
    StringTokenizer token = new StringTokenizer( fileName, "." );
    while ( token.hasMoreElements() ) {
      retStr = token.nextToken();
    }

    return retStr;
  }

  /**
   * @see StrUtil#replaceVariables(String, Map)
   * @param source
   * @param variables
   * @return
   */
  @SuppressWarnings("unchecked")
  public static String replaceVariables( String source, Properties variables ) {
    return replaceVariables( source, (Map)variables );
  }

  /**
   * Takes a string ( source ) and replaces all variables marked as <code>${variableName}</code> and
   * replaces them with the matching value in the map.
   * Will not replace variables that are not contained in the map.
   * ( Use {@link #replaceVariables(String, Map, boolean)} to remove variables not found in map )
   * 
   * @param source
   * @param variables
   * @return
   */
  public static String replaceVariablesOrig( String source, Map<String, String> variables ) {
    if ( source == null || source.length() < 3 || ColUtil.isEmpty( variables ) || source.indexOf( "${" ) < 0 )
      return source;

    String result = source;

    for ( Map.Entry<String, String> entry : variables.entrySet() )
      result = result.replaceAll( Pattern.quote( "${" + entry.getKey() + "}" ), entry.getValue() );

    return result;
  }

  public static String replaceVariables( String source, String... sets ) {
    if ( source == null || source.length() < 3 || sets.length < 2 || source.indexOf( "${" ) < 0 ) return source;

    String result = source;

    for ( int i = 0; i < sets.length; i += 2 )
      result = result.replaceAll( Pattern.quote( "${" + sets[i] + "}" ), sets[i + 1] );

    return result;
  }

  private static final String REGEX = "\\Q${\\E([a-zA-Z0-9_\\.])*\\Q}\\E";
  private static final Pattern pattern = Pattern.compile( REGEX );

  public static String replaceVariables( String source, Map<String, String> sets ) {
    return replaceVariables( source, sets, false );
  }

  public static String replaceVariables( String source, Map<String, String> sets, boolean removeUnMatchedTags ) {
    if ( source == null || source.length() < 3 || sets == null || sets.isEmpty() || source.indexOf( "${" ) < 0 )
      return source;

    /** The replace method can be up to 4 times faster with a small set of variables */
    if ( sets.size() < 10 && !removeUnMatchedTags ) return replaceVariablesOrig( source, sets );

    StringBuffer sb = new StringBuffer();
    Matcher matcher = pattern.matcher( source );
    while ( matcher.find() ) {
      String token = matcher.group();
      String name = token.substring( 2, token.length() - 1 );

      String replacement = removeUnMatchedTags || sets.containsKey( name ) ? StrUtil.unNull( sets.get( name ) ) : token;
      matcher.appendReplacement( sb, Matcher.quoteReplacement( replacement ) );
    }

    matcher.appendTail( sb );

    return sb.toString();
  }

  public static boolean isValidPhoneOrFaxNumber( String s ) {
    char temp1 = '`';
    char temp2 = '`';
    if ( StrUtil.isEmpty( s ) ) return true;
    s = s.trim();
    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( c <= '9' && c >= '0' ) continue;
      if ( c == ' ' || c == '(' || c == ')' || c == '-' || c == 'x' || c == 'X' || c == '+' ) {
        if ( ( c == 'x' || c == 'X' ) ) {
          if ( temp1 == '`' ) {
            temp1 = c;
          }
          else {
            return false;
          }
        }
        if ( c == '+' ) {
          if ( temp2 == '`' ) {
            temp2 = c;
          }
          else {
            return false;
          }
        }
        continue;
      }

      return false;
    }
    return true;

  }

  /**
   * Shortcut to avoid using TokenizedStringBuilders long name...
   * Join strings together with a large number of options.
   * 
   * @param delim
   * @return
   */
  public static TokenizedStringBuilder join( String delim ) {
    return new TokenizedStringBuilder( delim );
  }

  /**
   * Only includes the separator between two non-empty parts. Supports a null separator.
   * 
   * @return Will return null if the result is empty.
   */
  public static String combine( String separator, String... strings ) {
    String result = join( unNull( separator ) ).ignoreEmptyStrings().add( strings ).toString();
    return isEmpty( result ) ? null : result;
  }

  /**
   * Only includes the separator between two non-empty parts. Supports a null separator.
   * 
   * @return Will return null if the result is empty.
   */
  public static String prepend( String prefix, String orig, String separator ) {
    return combine( separator, prefix, orig );
  }

  /**
   * Appends two non-empty strings.
   * 
   * @return Will return null if the result is empty.
   */
  public static String prepend( String prefix, String orig ) {
    return combine( "", prefix, orig );
  }

  /**
   * Appends two non-empty strings based on condition.
   * 
   * @return Will return null if the result is empty.
   */
  public static String prepend( boolean condition, String prefix, String orig ) {
    return condition ? prepend( prefix, orig ) : orig;
  }

  /**
   * @todo Move to attribute code: Does not look reusable, ( specific to attributes??? - then should be moved to attribute code. )
   * 
   * Returns the list of strings formed by splitting {@code toSplit} by its first non-whitespace character.
   * Empty strings are not included in the returned list.
   * @return List of strings or the empty list (null is never returned).
   */
  public static List<String> split( String toSplit ) {
    List<String> forNull = Collections.emptyList();

    if ( toSplit == null ) { return forNull; }

    toSplit = toSplit.replaceFirst( "\\s*", "" );

    if ( toSplit.isEmpty() ) { return forNull; }

    String replacementSeperator = "~";

    toSplit = toSplit.replace( toSplit.charAt( 0 ), replacementSeperator.charAt( 0 ) );

    List<String> results = new ArrayList<String>();
    for ( String s : toSplit.substring( 1 ).split( replacementSeperator ) ) {
      if ( !s.trim().isEmpty() ) {
        results.add( s.trim() );
      }
    }

    if ( results.isEmpty() ) { return forNull; }

    return results;
  }

  /**
   * @return String formed by replacing in {@code s} all single quotes with two single quotes iff the single
   * quote is not adjacent to another single quote. But don't quote me on that. 
   */
  public static String doubleUpLoneApostrophes( String s ) {
    return s != null ? s.replaceAll( "(?<!')'(?!')", "''" ) : null;
  }


  /**
   * 
   * @param s
   * @return String escaping all special characters accorsing to Javascript rules
   */
  public static String escapeJavascript( String s ) {
    return s != null ? StringEscapeUtils.escapeJavaScript( s ) : null;
  }


  public static String escapeHtml( String html ) {
    //return html!=null?html.replaceAll("\n", "\\\\n").replaceAll("\r", "\\\\r").replaceAll("\"", "\\\\\""):null;
    return html != null ? html.replaceAll( "\n", "" ).replaceAll( "\r", "" ) : null;
  }

  /**
   * Will trim a string of preceding and trailing spaces. see {@link String#trim()}
   * for details of trim.
   * 
   * If the value is null, it will return null.
   * 
   * @param value
   * @return
   */
  public static String trim( String value ) {
    return value == null ? null : value.trim();
  }


  /**
   * Will trim a string of preceding and trailing spaces, returning a default value if the string is empty. See
   * {@link String#trim()} for details of trim.
   * 
   * If the value is null, it will return null.
   * 
   * @param value
   * @return
   */
  public static String trim( String value, String valueIfEmpty ) {
    String trimmed = trim( value );

    return ( trimmed == null || trimmed.length() == 0 ) ? valueIfEmpty : trimmed;
  }


  public static String getHtmlEncoded( String wordToEncode, boolean escapeHTMLOutput ) {
    String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String numeric = "0123456789";
    String special = "/!@#&$%^*()-_+=[]{}|`~;:,.?\" ";
    String allowedChars = lowerCase + upperCase + special + numeric;

    if ( wordToEncode == null ) { return null; }
    StringBuffer buffer = new StringBuffer();
    for ( int i = 0; i < wordToEncode.length(); i++ ) {
      switch ( wordToEncode.charAt( i ) ) {
        case '<' :
          buffer.append( escapeHTMLOutput ? "&#60;" : "<" );
          break;
        case '>' :
          buffer.append( escapeHTMLOutput ? "&#62;" : ">" );
          break;
        case '\'' :
          buffer.append( "&#39;" );
          break;
        case '\u2014' :
          buffer.append( "&#151;" );
          break;
        case '\u201C' :
          buffer.append( "&ldquo;" );
          break;
        case '\u201D' :
          buffer.append( "&rdquo;" );
          break;
        case '\r' :
          buffer.append( " " );
          break;
        case '\n' :
          buffer.append( " " );
          break;
        default :
          if ( allowedChars.indexOf( wordToEncode.charAt( i ) ) > -1 || isAccentedChar( wordToEncode.charAt( i ) )) {
            buffer.append( wordToEncode.charAt( i ) );
          }
          else {
            buffer.append( "" );
          }
      }
    }

    return buffer.toString();
  }

  /**
   * Strip out characters other than alphats
   * @param source - source string 
   * @return string with characters other than alphats being stripped out
   */
  public static String stripCharactersOtherThanAlphabets( String source ) {
    if ( StrUtil.isEmpty( source ) ) { return ""; }

    StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < source.length(); i++ ) {

      char c = source.charAt( i );
      if ( ( c >= 'A' && c <= 'Z' ) || ( c >= 'a' && c <= 'z' ) ) {
        sb.append( c );
      }
    }

    return sb.toString();
  }
  
  /**
   * Strip out characters other than alphabet or number
   * 
   * @param source source string
   * @return string with characters other than alphats being stripped out
   */
  public static String stripCharactersOtherThanAlphaNumeric( String source ) {
    if ( StrUtil.isEmpty( source ) ) {
      return EMPTY;
    }

    StringBuilder sb = new StringBuilder();
    
    for ( int i = 0; i < source.length(); i++ ) {
      char c = source.charAt( i );
      if ( isAlphaNumeric( c ) ) {
        sb.append( c );
      }
    }

    return sb.toString();
  }

  /*
   * filter out the chars which listed in filteringChars from orgString, returns the filtered out string
   */
  public static String filterChars( String orgString, char[] filteringChars ) {
    if ( orgString == null || orgString.isEmpty() || filteringChars == null || filteringChars.length == 0 )
      return orgString;

    char[] chars = orgString.toCharArray();
    int validIndex = 0;
    for ( int index = 0; index < chars.length; ++index ) {
      char theChar = chars[index];
      boolean filterOut = false;
      for ( char filteringChar : filteringChars ) {
        if ( theChar == filteringChar ) {
          //this char need to filter out
          filterOut = true;
          break;
        }
      }
      if ( !filterOut ) chars[validIndex++] = theChar;
    }
    return new String( chars, 0, validIndex );
  }


  public static Comparator<String> stringNonCaseSensitiveComparator = new Comparator<String>() {

    @Override
    public int compare( String o1, String o2 ) {
      return o1.compareToIgnoreCase( o2 );
    }
  };


  public static Comparator<String> stringComparator = new Comparator<String>() {

    @Override
    public int compare( String o1, String o2 ) {
      return o1.compareTo( o2 );
    }
  };

  public static String repeat( String str, int times ) {
    
    if ( str == null || str.length() == 0 || times <= 0 ) 
      return "";

    StringBuilder sb = new StringBuilder();
    for ( int i = 0; i < times; i++ ) 
      sb.append( str );
    
    return sb.toString();
  }

  /**
   * Return the first unempty string in the list of strings, or empty string if all are empty.
   * 
   * @param strings
   * @return
   */
  public static String firstUnempty( String... strings ) {
    for ( String string : strings )
      if ( !StrUtil.isEmpty( string ) ) return string;

    return "";
  }

  /**
   * To determine if the string contains one of the specified characters
   * @param s -- the string to be checked
   * @param chars -- specified characters 
   * @return true if the string contains one of the specified characters, false otherwise
   */
  public static boolean containsAnyOfSpecifiedChars( String s, char... chars ) {
    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( isOneOfSpecifiedChars( c, chars ) ) return true;
    }
    return false;
  }

  /**
   * To escape characters used in element ids which cannot be programatically accessed in Javascript.
   * @param input -- the string to be checked
   */
  public static String escape( String input ) {
    return escapeDash( escapeRight( escapeLeft( escapeColon( escapePeriods( input ) ) ) ) );
  }

  public static String escapeDash( String input ) {
    return StrUtil.replaceAll( input, "-", "\\\\-" );
  }

  public static String escapeLeft( String input ) {
    return StrUtil.replaceAll( input, "[", "\\\\[" );
  }

  public static String escapeRight( String input ) {
    return StrUtil.replaceAll( input, "]", "\\\\]" );
  }

  public static String escapePeriods( String input ) {
    return StrUtil.replaceAll( input, ".", "\\\\." );
  }

  public static String escapeColon( String input ) {
    return StrUtil.replaceAll( input, ":", "\\\\:" );
  }

  public static String formatPrice( double price ) {
    String priceString = Double.toString( price );
    int pad = 3 - ( priceString.length() - priceString.indexOf( "." ) );

    for ( int i = 0; i < pad; i++ )
      priceString += "0";
    return priceString;
  }

  public static Boolean isAccentedChar( Character ch ) {
    return ACCENTED_UNICODE.indexOf( ch ) > -1;
  }

  /**
   * remove accented chars from a string and replace with ascii equivalent
   * @param s String
   * @return ascii equivalent string of non ascii chars.
   */
  public static String replaceAccentedChars( String s ) {
    if ( isEmpty( s ) ) return s;
    String temp = Normalizer.normalize( s, Normalizer.Form.NFD );
    Pattern pattern = Pattern.compile( "\\p{InCombiningDiacriticalMarks}+" );
    return pattern.matcher( temp ).replaceAll( "" );
  }

  /**
   * 
   * @param cardHolderName
   * @return String[2] : [0] is first name, [1] is last name
   */
  public static String[] parseCardHolderName( String cardHolderName ) {
    cardHolderName = cardHolderName.trim();
    String[] names = new String[2];
    int commaIndex = cardHolderName.indexOf( ',' );
    int commaIndex2 = cardHolderName.indexOf( '/' );
    if ( commaIndex >= 0 ) {
      names[1] = cardHolderName.substring( 0, commaIndex ).trim();
      if ( commaIndex + 1 < cardHolderName.length() )
        names[0] = cardHolderName.substring( commaIndex + 1 ).trim();
      else
        names[0] = "";
    }
    else if ( commaIndex2 >= 0 ) {
      names[1] = cardHolderName.substring( 0, commaIndex2 ).trim();
      if ( commaIndex2 + 1 < cardHolderName.length() )
        names[0] = cardHolderName.substring( commaIndex2 + 1 ).trim();
      else
        names[0] = "";
    }
    else {
      String[] lastFirstNames = cardHolderName.trim().split( " " );
      if ( lastFirstNames.length > 0 ) {
        if ( lastFirstNames.length == 1 ) {
          names[0] = lastFirstNames[0].trim();
          names[1] = "";
        }
        else if ( lastFirstNames.length == 2 ) {
          names[0] = lastFirstNames[0].trim();
          names[1] = lastFirstNames[1].trim();
        }
        else {
          StringBuffer firstNameStringBuffer = new StringBuffer( lastFirstNames[0] );
          for ( int i = 1; i < lastFirstNames.length - 1; i++ ) {
            if ( !StrUtil.isEmpty( lastFirstNames[i] ) ) {
              firstNameStringBuffer.append( " " + lastFirstNames[i] );
            }
          }
          names[0] = firstNameStringBuffer.toString();
          names[1] = lastFirstNames[lastFirstNames.length - 1];
        }
      }
    }
    return names;
  }

  public static boolean isCardHolderNameValid( String cardHolderName ) {
    if ( isEmpty( cardHolderName ) ) return false;
    String[] names = parseCardHolderName( cardHolderName );
    // for now, both first name and last name are required to be a valid card holder name
    if ( names == null || names.length != 2 || isEmpty( names[0] ) || isEmpty( names[1] ) ) return false;
    return true;
  }

  /**
   * <p>Capitalizes a String changing the first letter to title case as
   * per {@link Character#toTitleCase(char)}. No other letters are changed.</p>
   *
   * <p>For a word based algorithm, see {@link WordUtils#capitalize(String)}.
   * A <code>null</code> input String returns <code>null</code>.</p>
   *
   * <pre>
   * StringUtils.capitalize(null)  = null
   * StringUtils.capitalize("")    = ""
   * StringUtils.capitalize("cat") = "Cat"
   * StringUtils.capitalize("cAt") = "CAt"
   * </pre>
   *
   * @param str  the String to capitalize, may be null
   * @return the capitalized String, <code>null</code> if null String input
   */
  public static String capitalize( String str ) {
    int strLen;
    if ( str == null || ( strLen = str.length() ) == 0 ) { return str; }
    return new StringBuilder( strLen ).append( Character.toTitleCase( str.charAt( 0 ) ) ).append( str.substring( 1 ) )
        .toString();
  }

  public static int parseCallingCode( String phone ) {
    if ( phone == null || isEmpty( phone ) ) { return 0; }
    phone = phone.trim();
    int callingCDIndex = phone.indexOf( '+' );
    if ( callingCDIndex > 0 ) {
      String callingCodeStr = phone.substring( 0, callingCDIndex ).trim();
      if ( isNum( callingCodeStr ) ) { return Integer.valueOf( callingCodeStr ); }
    }

    return 0;
  }

  /**
   * 
   * @param phone
   * @param areaLen
   * @param phoneLen
   * @return String[4] : [0] is calling code, [1] is area number, [2] is phone number, [3] is ext number
   */
  public static String[] parsePhoneNumber( String phone, int areaLen, int phoneLen ) {
    if ( phone == null ) {
      phone = EMPTY;
    }
    phone = phone.replaceAll( "\\(", "" ).replaceAll( "\\) ", "" ).replaceAll( "-", "" ).trim();
    String[] numbers = new String[4];
    numbers[0] = EMPTY;
    numbers[1] = EMPTY;
    numbers[2] = EMPTY;
    numbers[3] = EMPTY;
    if ( isEmpty( phone ) ) { return numbers; }
    int callingCDIndex = phone.indexOf( '+' );
    if ( callingCDIndex >= 0 ) {
      if ( callingCDIndex > 0 ) {
        numbers[0] = phone.substring( 0, callingCDIndex ).trim();
      }
      phone = phone.substring( callingCDIndex + 1 ).trim();
      if ( isEmpty( phone ) ) { return numbers; }
    }
    int extIndex = phone.indexOf( 'x' );
    if ( extIndex >= 0 ) {
      if ( phone.length() <= 1 ) { return numbers; }
      numbers[3] = phone.substring( extIndex + 1, phone.length() ).trim();
      if ( extIndex == 0 ) { return numbers; }
      phone = phone.substring( 0, extIndex ).trim();
      if ( isEmpty( phone ) ) { return numbers; }
    }
    if ( !isNum( phone ) ) { return numbers; }
    if ( areaLen > 0 ) {
      numbers[1] = phone.substring( 0, Math.min( areaLen, phone.length() ) );
    }
    if ( phone.length() > areaLen ) {
      numbers[2] = phone.substring( areaLen, Math.min( phoneLen + areaLen, phone.length() ) );
    }
    return numbers;
  }
  
  /**
   * Return the line of text based on the specified line number.
   */
  public static String getLine( String contents, int lineNumber ) {
    String[] lines = contents.split( "\\r?\\n|\\r" );
    if ( lines.length < lineNumber )
      return null;
    return lines[lineNumber - 1];
    
  }
  
  /**
   * Romove all space in the target String
   */
  public static String removeSpacesInString( String s ) {
    if ( s == null )
      return s;
    s = s.trim();
    StringBuffer sb = new StringBuffer( "" );
    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( c == ' ' || c == '\t' )
        continue;
      else
        sb.append( c );
    }
    return sb.toString();
  }

  public static String removeCharInString( String s,  char charToBeRemoved ) {
    if ( s == null )
      return s;
    s = s.trim();
    StringBuffer sb = new StringBuffer( "" );
    for ( int i = 0; i < s.length(); i++ ) {
      char c = s.charAt( i );
      if ( c == charToBeRemoved )
        continue;
      else
        sb.append( c );
    }
    return sb.toString();
  }
  
  public static String doubleToSingleQuote( String s ) {
    if ( s == null ) return null;

    return s.replaceAll( "\\\"", "\'" );
  }
  
  public static String singleToDoubleQuote( String s ) {
    if ( s == null ) return null;

    return s.replaceAll( "\'", "\\\"" );
  }
  
  /**
   *  shorten the lengthy string upto maxLength parameter followed by ...;
   *   
   * @param targetString
   * @param maxLength
   * @return
   */
  public static String reduceStringLength(String targetString, int maxLength) {
    if(!isEmpty( targetString ) && targetString.length() > maxLength)
      return  "\"" + targetString.substring( 0, maxLength ) + "..." + "\"";
    else if(!isEmpty( targetString ) && targetString.length() < maxLength)
      return "\"" + targetString + "\"";
    else 
      return targetString;
  }
  
  /**
   * get first not empty string.
   * 
   * @param strs
   * @return
   */
  public static String getFirstNonEmptyStr( String ...strs ) {
    if ( strs != null && strs.length > 0 ) {
      for ( String str : strs ) {
        if ( !isEmpty( str ) ) {
          return str;
        }
      }
    }
    return "";
  }
  
  /**
   * to convert a list of IDs into (12,2345,3456)
   *  mainly for direct SQL
   * @param list of Ids
   * @return string if IDs separated by comma inside brackets.
   */
  public static String convertToBracketIDList(List<Long> ids) {
    String retValue = "";
    if (ColUtil.areEmpty( ids )) {
      return "";
    }
    
    StringBuilder sb = new StringBuilder();
    for (Long id : ids) {
      if (id != null) {
        sb.append( id  ).append( "," );
      }
    }
    
    if (sb.length() > 0) {
      retValue = " (" + sb.toString().substring( 0, sb.length() - 2 ) + ")" ;
    }
    
    return retValue;
  }
  
  public static String chop( String str ) {
    if (isEmpty(str)) return str;
    
    return str.substring( 0, str.length() - 1 );
  }

  /**
   * Evaluates if the source string ends with any of the suffixes.
   *
   * null or empty source always returns false.
   * 
   * @param attr
   * @param string
   * @return
   */
  public static boolean endsIn( String source, String... suffixes ) {
    return endsInWhich(source, suffixes ) != null;
  }
  
  /**
   * Returns the matched suffix if the source string ends with any of the suffixes.
   * null or empty source always returns false.
   * null if no match.
   * 
   * @param attr
   * @param string
   * @return
   */
  public static String endsInWhich( String source, String... suffixes ) {
    if ( source == null || suffixes == null || source.length() == 0  ) return null;
    
    for ( String suffix : suffixes ) 
      if ( source.endsWith( suffix ) )
        return suffix;
    
    return null;
  }
  
  public static String intern( String stringToCache ) {
    if ( stringToCache == null ) return null;
    return stringToCache.intern();
  }
  
  /**
   * Returns a count of the number of occurrences of a char/string in a another String
   * 
   * @param content
   * @param chars (note: don't support regular expression at this monment)
   * @return
   */
  public static int countNumberOfOccurrence( String content, String chars) {
    // don't use isEmpty() to check
    if ( content == null || chars == null ||
         0 ==content.length() || 0 ==chars.length()) return 0;
    return (content.length() - content.replace(chars, "").length()) / chars.length() ;
  }
  
  /**
   * Performs alpha-numeric compare between the two strings.  Compare is case-insensitive and ascending.
   * @param str1
   * @param str2
   * @return
   */
  public static int doAlphanumericCompare( String str1, String str2 ){
    return doAlphanumericCompare( str1, str2, true, false );
  }
  
  /**
   * 
   * @param str1
   * @param str2
   * @param asc
   * @param Whether comparison should be case sensitive
   * @return
   */
  public static int doAlphanumericCompare( String str1, String str2, boolean asc, boolean caseSens ){
    if ( !isEmpty( str1 ) && isEmpty( str2 ) ){
      if ( asc )
        return 1;
      return -1;
    }
    if ( isEmpty( str1 ) && !isEmpty( str2 ) ){
      if ( asc )
        return -1;
      return 1;
    }
    
    if ( caseSens ){
      if ( !asc )
        return unNull( str2 ).compareTo( unNull( str1 ) );
        
      return unNull( str1 ).compareTo( unNull( str2 ) );
    }
    else {
      if ( !asc )
        return unNull( str2 ).compareToIgnoreCase( unNull( str1 ) );
        
      return unNull( str1 ).compareToIgnoreCase( unNull( str2 ) );
    }
  }
  
  public static String textWindow( int positionX, String... values ) {
    String pf = StrUtil.repeat( " ", positionX );
    if ( values == null || values.length == 0 ) return "";
    
    // calc;
    int maxLength = 0 + 2;
    for( String v : values )
      maxLength = Math.max( maxLength, v.length() ); 
    maxLength += 2;
    
    String value = "";
    value =  pf + " " + StrUtil.repeat( "_", maxLength ) + "\n";
    for ( int i = 0; i < values.length; i++ ) 
      value += pf + "| " + values[i] + StrUtil.repeat( " ", ( maxLength - 2 ) - values[i].length() ) + " |" + (i == 0 ? "_" : " |") + "\n";
    
    value += pf + "|" + StrUtil.repeat( "_", maxLength ) + "| |\n";
    value += pf + "  |" + StrUtil.repeat( "_", maxLength ) + "|\n";

    return value;
  }
  
  public static String lpad( String target, int length, char padChar ) {
    if ( unNull( target ).length() >= length ) {
      return target;
    }
    StringBuilder padding = new StringBuilder();
    for ( int i = 0; i < length - target.length(); i++ ) {
      padding.append( padChar );
    }
    return padding.append( target ).toString();
  }
  
  public static String rpad( String target, int length, char padChar ) {
    if ( unNull( target ).length() >= length ) {
      return target;
    }
    StringBuilder padding = new StringBuilder( target );
    for ( int i = 0; i < length - target.length(); i++ ) {
      padding.append( padChar );
    }
    return padding.toString();
  }

  /**
   * Converts a dot notation name to camel case for example:
   * abc.def.efg -> AbcDefEfg
   * 
   * @param dot notation string
   * @return Camel case notation.
   */
  public static String dotToCamelCase( String string, boolean capitalizeFirstLetter ) {
    if ( string == null ) return null;
    if ( isEmpty( string ) ) return string;
    String result = mixcase( string.replace( '.', ' ' ) );
    if ( !capitalizeFirstLetter )
      result = result.substring( 0, 1 ).toLowerCase() + result.substring( 1 );
    return result.replace( " ", "" );
  }
  
  /**
   * Returns true if the given number is represented in the given comma-delimited string
   * @param delimitedSequence   String representation of a list of comma-separated numbers
   * @param number              Number you want to find
   * @return                    True if the number is represented in the sequence
   */
  //    Kevin V.  PCR-4592 marks the 3rd time I've had to solve a problem like this (find APP id in comma-separated string).  
  //    Thus I think we can profit by making this solution common.
  public static boolean containsNumber( String delimitedSequence, long number ){
    
    if ( isEmpty( delimitedSequence ) )
      return false;
    
    String[] parts = delimitedSequence.split( "," );
    String currentAppIDStr = number + "";
    for ( String v : parts ){
      if ( StrUtil.trim( v ).equals( currentAppIDStr ) )
        return true;
    }
    
    return false;
  }

  /*****
   * 
   * remove text between enclose start and enclose end
   * 
   * @param textToProcess
   * @param encloseStart
   * @param encloseEnd
   * @return
   */
  public static String removeEnclosedText( String textToProcess, String encloseStart, String encloseEnd ) {
    StringBuilder sb = new StringBuilder( textToProcess );
    int start = sb.indexOf( encloseStart );
    int end = 0;
    while ( start >= 0 ) {
      end = sb.indexOf( encloseEnd, start + encloseStart.length() );
      sb.delete( start, end + encloseEnd.length() );
      start = sb.indexOf( encloseStart );
    }
    return sb.toString();
  }
  
  public static String mask( String str, int numberOfWordsToMask, String mask ) {
    String[] s = str.split( " " );
    StringBuilder sb = new StringBuilder();
    if (s.length > 0) {
      for ( int i = 0; i < s.length; i++ ) {
        String word = s[i];
        if (i < numberOfWordsToMask)
          word = replaceByRegExpPattern( word, ".", mask );
        sb.append( word );
        sb.append( " " );
      }
    }
    return sb.substring( 0, sb.length() -1 );
  }
  
  private static String replaceByRegExpPattern(String original, String pattern, String replacement) {
    return Pattern.compile( pattern ).matcher( original ).replaceAll( replacement );
  }
  
  public static String trimEndWith( String content, char deleteChar ) {
    if ( StrUtil.isEmpty( content ) ) return "";
    String result = content.trim();
    if ( result.lastIndexOf( deleteChar ) == result.length() - 1 ) { return result.substring( 0, result.length() - 1 ); }
    return result;
  }
  
}
