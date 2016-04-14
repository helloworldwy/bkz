package com.activenetwork.awo.common;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class NumUtil {  

  public static final int LAT_N = 1;
  public static final int LAT_S = -1;

  public static final int LONG_E = 1;
  public static final int LONG_W = -1;

  public static final int DIR_N  = 0;
  public static final int DIR_S  = 1;
  public static final int DIR_E  = 2;
  public static final int DIR_W  = 3;
  
  public static final int COORD_DEGREES = 0;
  public static final int COORD_MIN = 1;
  public static final int COORD_SEC = 2;
  public static final int COORD_DIR = 3;
  
  public static final String[] DIRECTIONS = new String[] { "N", "S", "E", "W" };
  
  public static final String TYPE_LAT = "LAT";
  public static final String TYPE_LONG = "LONG";
    
  private NumUtil() {
    // prevent instantiation.
  }

  /**
   * Attempts to obtain an integer value based on the given string input.  If
   * this is not possible or the given String is null then returns 0.
   * @param psNum   String representing a number
   * @return        The number as an int, or 0 if parse fails.
   */
  public static int iVal( String psNum ) {
    return iVal( psNum, 0 );
  }
  
  public static int iVal( String psNum, int defaultValue ) {
    if ( psNum == null ) return defaultValue;
    int liVal;

    try {
      liVal = Integer.valueOf( psNum ).intValue();
    } catch( Exception e ) {
      liVal = defaultValue;
    }

    return liVal;
  }
  
  public static Integer IVal( String psNum ) {
    return IVal( psNum, null );
  }
  
  /**
   * @param value
   * @param defaultValue
   * @return
   */
  public static Integer IVal( String value, Integer defaultValue ) {
    if ( value == null ) return defaultValue;
    try {
      return Integer.valueOf( value );
    }
    catch ( Exception e ) {
      return defaultValue;
    }
  }
  
  public static Long LVal( String psNum ) {
    if ( psNum == null ) return null;
    try {
      return Long.valueOf( psNum );
    }
    catch ( Exception e ) {
      return null;
    }
  }
  


  /**
   * Obtains the long value equivilent of the given string
   * @param psNum   String to parse as a long
   * @return        Long value of the string or 0 if parsing failed.
   */
  public static long lVal( String psNum ) {
    return lVal( psNum, 0 );
  }
  
  /**
   * Parse the given string into a long
   * @param psNum           String to parse
   * @param defaultValue    Default value to return if parsing fails
   * @return                The long equivilent of the String or the defaultValue if parsing fails.
   */
  public static long lVal( String psNum, long defaultValue ) {
    if ( psNum == null ) return defaultValue;
    long llVal;

    try {
      llVal = Long.valueOf( psNum ).longValue();
    } catch( Exception e ) {
      llVal = defaultValue;
    }

    return llVal;
  }
  
  // DOUBLES!!!!
  public static double dVal( String psNum ) {
    return dVal( psNum, 0 );
  }
  public static double dVal( String value, double defaultVal ) {
    return DVal( value, Double.valueOf( defaultVal ) ).doubleValue();
  }

  
  /**
   * Formats the given String as a double
   * @param psNum   String to format
   * @return        Double value or null if parse was not successful.
   */
  public static Double DVal( String psNum ) {
    return DVal( psNum, null );
  }
  public static Double DVal( String psNum, Double defaultVal ) {
    if ( StrUtil.isEmpty( psNum ) ) return defaultVal;
    Double DVal = defaultVal;

    try {
      DVal = Double.valueOf( psNum );
    } catch( Exception e ) {
      // ignore error
    }

    return DVal;
  }

  public static Double DVal( String psNum, Double defaultVal, Locale locale ) {
    if ( StrUtil.isEmpty( psNum ) ) return defaultVal;
    Double DVal = defaultVal;
    NumberFormat nf = DecimalFormat.getInstance( locale );

    try {
      DVal = nf.parse(psNum).doubleValue(); 
    } catch( Exception e ) {
      // ignore error
    }

    return DVal;
  }
  
  /**
   * Method to parse strings formatted by {@link ConfLetterBean#formatDecimallForPOSQuantityUnitPrice}
   * 
   * @param psNum
   * 
   * @return double value of psNum or defaultVal if psNum is not a valid double.
   */
  public static double dValCurrentLocale(String psNum) {
    return dValCurrentLocale(psNum,0);
  }  

  /**
   * Method to parse a double from a string based on current locale.
   * 
   * @param psNum
   * @param defaultVal
   * 
   * @return double value of psNum or defaultVal if psNum is not a valid double.
   */
  public static double dValCurrentLocale(String psNum, double defaultVal) {
    return DValCurrentLocale( psNum, Double.valueOf( defaultVal ) ).doubleValue();
  }
  
  /**
   * Method to parse strings formatted by {@link ConfLetterBean#formatDecimallForPOSQuantityUnitPrice}
   * 
   * @param psNum
   * 
   * @return Double value of psNum or null if psNum is not a valid double.
   */
  public static Double DValCurrentLocale( String psNum ) {
    return DValCurrentLocale( psNum, null );
  }

  /**
   * Method to parse strings formatted by {@link ConfLetterBean#formatDecimallForPOSQuantityUnitPrice}
   * 
   * @param psNum
   * @param defaultVal
   * 
   * @return Double value of psNum or defaultVal if psNum is not a valid Double.
   */
  public static Double DValCurrentLocale( String psNum, Double defaultVal ) {
    NumberFormat nf = DecimalFormat.getInstance();
    if ( StrUtil.isEmpty( psNum ) ) return defaultVal;
    Double DVal = defaultVal;

    try {
      DVal = nf.parse(psNum).doubleValue(); 
    } catch( Exception e ) {
      // ignore error
    }

    return DVal;
  }

  public static boolean isEmpty( Float floatN ){
    return fVal( floatN ) == 0f;
  }
  
  /**
   * Extract float value of number or 0 if the number is null
   * @param n
   * @return
   */
  public static float fVal( Number n ){
    if ( n == null )
      return 0f;
    return n.floatValue();
  }
  
  public static float fVal( String psNum ) {
    if ( psNum == null ) return 0;
    float lfVal;

    try {
      lfVal = Float.valueOf( psNum ).floatValue();
    } catch( Exception e ) {
      lfVal = 0;
    }

    return lfVal;
  }

  public static short sVal( String psNum ) {
    if ( psNum == null ) return 0;
    short lshVal;

    try {
      lshVal = Short.valueOf( psNum ).shortValue();
    } catch( Exception e ) {
      lshVal = 0;
    }

    return lshVal;
  }

  public static float fMoney( float pfNum ) {
    float lfNum;

    if( pfNum >= 0f )
      lfNum = pfNum + .0025f;
    else
      lfNum = pfNum - .0025f;

    return lfNum;
  }

  public static double fMoney( double pfNum ) {
    double lfNum;

    // lfNum = (float)( ( 100 * pfNum ) + .5 ) /100 ;
    // lfNum = Math.rint(.5 + pfNum * 100.0)/100.0;
    if( pfNum >= 0f )
      lfNum = pfNum + .0025f;
    else
      lfNum = pfNum - .0025f;

    return lfNum;
  }

  public static float fCurrency( float pfNum ) {
    return fMoney( pfNum );
  }

  public static String time( int piSec ) {
    int liSecs = piSec % 60;
    int liMinutes = ( piSec / 60 ) % 60;
    int liHours = ( piSec / 3600 ) % 24;
    int liDays = ( piSec / 3600 ) / 24;
    boolean showDays = liDays > 0;
    boolean showHours = liHours > 0 || showDays;

    return( ( !showDays ) ? "" : liDays + "d " ) +
      ( ( !showHours ) ? "" : liHours + "h " ) +
      ( ( !showHours && liMinutes == 0 ) ? "" : liMinutes + "m " ) +
      liSecs + "s";
  }

  public static String time( long plMSec ) {
    return time( (int) ( plMSec / 1000 ) );
  }
  /**
   * Get the formated elapsed time with milliseconds.
   * @param p_oL
   * @return
   */
  public static String timem( long p_lMillis ) {
    String sTime = "";
    if ( p_lMillis > 999 ) sTime =time( p_lMillis );
    sTime += " " + (p_lMillis % 1000 ) + "ms";    
    return sTime;
  }

  public static String bytes( long pBytes, int decimalplaces ) {
    double KBASE = 1024;
    double MBASE = KBASE * KBASE;
    double GBASE = MBASE * KBASE;
    double TBASE = GBASE * KBASE;
    double PBASE = TBASE * KBASE;  // Peta
    double EBASE = PBASE * KBASE;  // Exa
    double ZBASE = EBASE * KBASE;  // Zetta
    double YBASE = ZBASE * KBASE;  // Yotta
    double BBASE = YBASE * KBASE;  // Bronto

    NumberFormat lNf = NumberFormat.getInstance();

    lNf.setMaximumFractionDigits( decimalplaces );
    lNf.setMinimumFractionDigits( decimalplaces );
    lNf.setMinimumIntegerDigits( 3 );
    
    if( pBytes > BBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Bb";
    if( pBytes > YBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Yb";
    if( pBytes > ZBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Zb";
    if( pBytes > EBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Eb";
    if( pBytes > PBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Pb";
    if( pBytes > TBASE - 1 )
      return lNf.format( pBytes / TBASE ) + " Tb";
    if( pBytes > GBASE - 1 )
      return lNf.format( pBytes / GBASE ) + " Gb";
    if( pBytes > MBASE - 1 )
      return lNf.format( pBytes / MBASE ) + " Mb";
    if( pBytes > KBASE - 1 )
      return lNf.format( pBytes / KBASE ) + " Kb";
    return lNf.format( pBytes ) + " b";
  }
  
  public static String bytes( long pBytes ) {
    return bytes( pBytes, 2 );
  }
  
  public static String bytesw( long pBytes ) {
    return bytes( pBytes, 0 );
  }

  public static double round( double amt ) {
    return ((double)Math.round( 100 * amt )  /  100 );
  }
  
  public static double round( double amt , int digits) {
    return( Math.round( Math.pow(10 , digits) * amt ) ) /  Math.pow(10 , digits);
  }
  
  public static double roundDown(double amt, double digits){
    double powValue = Math.pow( 10, digits );
    amt = amt*powValue;
    amt = ((int) amt);
    amt = amt /powValue;
    
    return amt;
  }
  
  public static double least( double amt1,double amt2 ) {
	    if(amt1>=amt2)
	    	return amt2;
	    else 
	    	return amt1;
  }
  
//  public static String format2DecimalPoint(double amt){
//    StringBuffer ret = new StringBuffer();
//    
//    String tmp = Double.toString(NumUtil.round(amt));
//    int index = tmp.indexOf(".");
//    if(index == -1){
//      ret.append(tmp);
//      ret.append(".00"); 
//    }else{
//      ret.append(tmp);
//      if(tmp.substring(index+1).length() == 0){
//        ret.append("00");
//      }
//      if(tmp.substring(index+1).length() == 1){
//        ret.append("0");
//      }
//    }
//    return ret.toString();
//  }
  
  public static String format2DecimalPoint(double amt){
	    StringBuffer ret = new StringBuffer();
	    
	    String tmp = "";
	    try{
	    	tmp = new Long(new BigDecimal(NumUtil.round(amt*100)).longValue()).toString();
	    }catch(NumberFormatException e){
	    	return "";
	    }
	    
	    String sign="";
	    if(tmp.startsWith("-")){
	    	sign="-";
	    	tmp=tmp.substring(1);
	    }
	    
	    	
	    if(tmp.length()>2){
	    	ret.append(tmp.substring(0, tmp.length()-2));
	    	ret.append(".");
	    	ret.append(tmp.substring(tmp.length()-2));
	    }else if (tmp.length()==2){
	    	ret.append("0.");
	    	ret.append(tmp);
	    }else if (tmp.length()==1){
	    	ret.append("0.0");
	    	ret.append(tmp);
	    	
	    }
	    ret.insert(0,sign);
	    return ret.toString();
	  }
  
  public static String format2DecimalPointMoney(double amt){
    boolean isNegativ = (amt<0);
    
    String tmp = format2DecimalPoint(Math.abs(amt));
    if(isNegativ)
      tmp = "("+tmp+")";
    return tmp;
  }
  
  public static String format2Currency(double amt){
    return "$"+format2DecimalPointMoney(amt);
  }
  
  /**
   * @param amt
   * @return    The return result will be formatted as (###,###,###.##) 
   */
  public static String format2DecimalPointMoneyWithComma(double amt){
    boolean isNegativ = (amt<0);
    
    String tmp = formatDouble(Math.abs(amt));
    if(isNegativ)
      return "("+tmp+")";
    else
      return tmp;
  }
  
  /**
   * @param amt     
   * @return    The return result will be formatted as ($###,###,###.##)
   */
  public static String format2CurrencyWithComma( double amt ) {
    String formattedAmount = format2DecimalPointMoneyWithComma( amt );
    
    if ( formattedAmount.startsWith( "(" ) ){
      return formattedAmount.replace( "(", "($" );
    }else {
      return "$" + formattedAmount;
    }
  }
  
  public static String format4DecimalPoint(double amt){
    StringBuffer ret = new StringBuffer();
    NumberFormat formatter = new DecimalFormat("#.#####");
    String tmp = formatter.format(NumUtil.round(amt,4));
    int index = tmp.indexOf(".");
    if(index == -1){
      ret.append(tmp);
      ret.append(".0000"); 
    }else{
      ret.append(tmp);
      if(tmp.substring(index+1).length() == 0){
        ret.append("0000");
      }
      if(tmp.substring(index+1).length() == 1){
        ret.append("000");
      }
      if(tmp.substring(index+1).length() == 2){
        ret.append("00");
      }
      if(tmp.substring(index+1).length() == 3){
        ret.append("0");
      }
    }
    return ret.toString();
  }
  
//  public static String formatNoDecimalPoint(double amt){
//      StringBuffer ret = new StringBuffer();
//      
//      String tmp = Double.toString(NumUtil.round(amt));
//      int index = tmp.indexOf(".");
//      if(index == -1){
//        ret.append(tmp);
//      }else{
//        tmp = tmp.substring(0, index);
//        ret.append(tmp);
//      }
//      return ret.toString();
//  }
  
  public static String formatNoDecimalPoint(double amt){
      StringBuffer ret = new StringBuffer();
      
      String tmp = "";
      try{
    	  tmp = new Long( new BigDecimal(amt).longValue() ).toString();
      }catch(NumberFormatException e){
    	  return"";
      }
      
      int index = tmp.indexOf(".");
      if(index == -1){
        ret.append(tmp);
      }else{
        tmp = tmp.substring(0, index);
        ret.append(tmp);
      }
      return ret.toString();
  }
  
  public static String formatDoubleLable(double amt){
    String strRate = "";
    
    if (NumUtil.hasDecimal( amt  ))
      strRate = NumUtil.format2DecimalPoint( amt );
    else
      strRate = NumUtil.formatNoDecimalPoint( amt );
    
    return strRate;
  }
  


  /**
   * Determine integer value of the given {@link Number} or return the 
   * default value of the given {@link Number} is null
   * @param number          Number
   * @param defaultValue    Default of number is null
   * @return                Integer value
   */
  public static int iVal( Number number, int defaultValue ) {
    if ( number == null ) return defaultValue;
    return number.intValue();
  }
  
  /**
   * Convert the given {@link Number} to an int
   * @param number  
   * @return        int value of the {@link Number} or 0 if the given number is null.
   */
  public static int iVal( Number number ) {
    return iVal( number, 0 );
  }

  public static long lVal( Number number ) {
    if ( number == null ) return 0;
    return number.longValue();
  }

  public static boolean isWholeNumber( String word ) {
    return word.matches( "[0-9]+" );
  }
  
  public static boolean isWholeNumberInRange( String word, int lower, int upper ) {
    if ( !isWholeNumber(word) ) return false;
    
    return ( lower <= iVal( word ) ) && ( iVal( word ) <= upper );
  }
  
  //check to see if the number has commas in the correct place or no commas
  public static boolean isCommaCorrect( String number ) {
    //a string is comma correct if it is not null and any commas are 
    //positioned in a position that is a multiple of 4 from the end
    
    if ( number == null ) return false;
   
    boolean valid = true;
    int currPos = 0;
    while ( valid && currPos != - 1 ) {
      
      currPos = number.indexOf( ",", currPos );
      	
      if ( currPos != -1 ) {
        valid = ( number.length() - currPos ) % 4 == 0;
        currPos++;
      }
      
    }
    return valid;
  }
  
  public static void addMapInt(Map map, Object key, int newValue) {
    map.put( key, iVal( (Integer)map.get( key ) ) + newValue );
  }
  
  public static String convertListToString(Iterable<Long> l) {
    return convertListToString( l,  ", " );
  }

  public static String convertListToString(Iterable<Long> l, String separator) {
    if ( separator == null ) separator = ", ";

    StringBuilder buff = new StringBuilder();
    for ( Iterator<Long> it = l.iterator(); it.hasNext(); ) {
      buff.append( it.next() );
      if (it.hasNext()) {
        buff.append( separator );
      }
    }

    return buff.toString();
  }

  /**
   * I know it's not a number... but Deals with the exact same issue...
   * Conversion from Object type to primitive...
   * @return
   */
  public static boolean bVal( Boolean value ) {
    return value != null && value; 
  }
  
  /**
   * from: http://en.wikipedia.org/wiki/Geographic_coordinate_conversion
   *   
   * @param degree
   * @param min
   * @param sec
   * @return coord
   */  
  public static double convertCoordinateFromDMSToDecimalDegree(int dir, int degree, int min, double sec ) {
    int secsInHour = 3600;
    
    double seconds = min * 60 + sec;
    double fraction = (degree * secsInHour + seconds) / secsInHour;
    double coord = fraction * dir;
    
    //round to 7 decimal places
    coord = round(coord, 7);
    
    return coord;
  }
  

  /**
   * Converts a location in degrees, minutes, seconds into a decimal.
   * 
   * @see http://en.wikipedia.org/wiki/Geographic_coordinate_conversion
   * 
   * @param direction name of the direction, starting with N, E, W, or S for north, east, west or south.
   * @param degree
   * @param min
   * @param sec
   * @return
   */
  public static double convertCoordinateFromDMSToDecimalDegree( String direction, int degree, int min, double sec ) {
    int dir = 0;

    if ( direction != null ) {
      if ( direction.startsWith( "N" ) || direction.startsWith( "E" ) ) {
        dir = 1;
      }

      if ( direction.startsWith( "S" ) || direction.startsWith( "W" ) ) {
        dir = -1;
      }
    }

    return convertCoordinateFromDMSToDecimalDegree( dir, degree, min, sec );
  }
  
  /**
   * from: http://en.wikipedia.org/wiki/Geographic_coordinate_conversion
   * AS: TODO - change to allow decimals for seconds
   * 
   * @param decDegree
   * @return coord (format --> deg:min:secdir 
   */
  public static int[] convertCoordinateFromDecimalDegreeToDMS(double decDegree, String type) {
    
    //Input must be non-negative
    double absVal = Math.abs( decDegree );
    int deg = (int)absVal;
    double minVal = (absVal - deg) * 60;
    int min = (int)minVal;
    double secVal = (minVal - min) * 60;
    int sec = (int)Math.round(secVal);
    
    //After rounding, the seconds might become 60. 
    if ( sec == 60 ) {
      min = min + 1;
      sec = 0;
    }

    if (min == 60) {
       deg = deg + 1;
       min = 0;
    }
    
    int dir = 0;
    if ( type.equals( TYPE_LAT )) {
      dir = decDegree > 0 ? DIR_N : DIR_S;
    } else if (type.equals( TYPE_LONG )) {
      dir = decDegree > 0 ? DIR_E : DIR_W;
    }
    
    return new int[] { deg, min, sec, dir };
  } 
  
  public static boolean isValidInteger(String value){
    try{
      Integer.parseInt( value );
      return true;
    }catch (NumberFormatException e) {
      return false;
    }
  }
  
  public static boolean isValidLong(String value) {
    try  {
      Long.parseLong( value );
      return true;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  public static boolean isValidDouble(String value) {
    try{
      Double.valueOf( value );
      return true;
    }catch (NumberFormatException e) {
      return false;
    }
  }  
  
  public static String formatLong( long value ){
    NumberFormat formatter = new DecimalFormat( "#,###,###" );
    return formatter.format( value );
  }
  
  public static String formatDouble(double value){
    NumberFormat formatter = new DecimalFormat("#,###,##0.00;(#,###,##0.00)");
    return formatter.format(value); 
  }
  
  /**
   * Formats the given double as a dollar amount, with negative amounts in parentheses.
   * @param amount  Amount to render as formatted String
   * @return        Formatted String
   */
  public static String formatDoubleAmount(double amount){
    NumberFormat formatter = new DecimalFormat("$#,###,##0.00;($#,###,##0.00)");
    return formatter.format(amount); 
  }

  public static boolean bVal( String value ) {
    return value != null && ( value.toLowerCase().startsWith( "t" ) || value.toLowerCase().startsWith( "y" ) );
  }

  public static String timeNanos( long nano ) {
    long millis = nano / 1000000;
    long leftOver = nano % 1000000;
    return timem( millis ) + " " + leftOver + " ns";
  }

  public static boolean isNumber(String str) {
      if(str == null || str.length() == 0) return false;
      
      char chars[] = str.toCharArray();
      int sz = chars.length;
      boolean hasExp = false;
      boolean hasDecPoint = false;
      boolean allowSigns = false;
      boolean foundDigit = false;
      int start = chars[0] != '-' ? 0 : 1;
      int i;
      
    if ( sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x' ) {
      i = start + 2;
      if ( i == sz ) return false;
      for ( ; i < chars.length; i++ )
        if ( ( chars[i] < '0' || chars[i] > '9' ) && ( chars[i] < 'a' || chars[i] > 'f' )
            && ( chars[i] < 'A' || chars[i] > 'F' ) ) return false;

      return true;
    }
    sz--;
    for ( i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; i++ ) {
      if ( chars[i] >= '0' && chars[i] <= '9' ) {
        foundDigit = true;
        allowSigns = false;
        continue;
      }
      if ( chars[i] == '.' ) {
        if ( hasDecPoint || hasExp ) return false;
        hasDecPoint = true;
        continue;
      }
      if ( chars[i] == 'e' || chars[i] == 'E' ) {
        if ( hasExp ) return false;
        if ( !foundDigit ) return false;
        hasExp = true;
        allowSigns = true;
        continue;
      }
      if ( chars[i] == '+' || chars[i] == '-' ) {
        if ( !allowSigns ) return false;
        allowSigns = false;
        foundDigit = false;
      }
      else {
        return false;
      }
    }

    if ( i < chars.length ) {
      if ( chars[i] >= '0' && chars[i] <= '9' ) return true;
      if ( chars[i] == 'e' || chars[i] == 'E' ) return false;
      if ( !allowSigns && ( chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F' ) )
        return foundDigit;
      if ( chars[i] == 'l' || chars[i] == 'L' )
        return foundDigit && !hasExp;
      else
        return false;
    }
    else {
      return !allowSigns && foundDigit;
    }
  }
  
  /**
   * Determines if a string contains only numbers.  
   * {@link Character#isDigit(char)}
   * 
   * @param value
   * @return
   */
  public static boolean onlyNumbers( String value ) {
    for ( char c : value.toCharArray() ) 
      if ( !Character.isDigit( c ) )
        return false;
    return value.length() > 0;
  }

  /**
   * Determines if the given double is equivalent to an int
   * 
   * @param value   Number to check
   * @return        True if the number's value is an integer.
   */
  public static boolean isIntEquivalent( double value ){
    return Math.floor( value ) == value;
  }
  
  /**
   * Determines if the given {@link Integer} is a non-zero non-null number
   * 
   * @param value   Number to check
   * @return        True if the number's value is not 0. and the {@link Integer} itself
   * is not null
   */
  public static boolean isNotEmpty( Integer value ){
    return iVal( value ) != 0;
  }
  /**
   * Determine if the given {@link Integer} boxed value is either null or 0.
   * @param value
   * @return
   */
  public static boolean isEmpty( Integer value ){
    return iVal( value ) == 0;
  }
    
  /**
   * Determines if the given {@link Long} is a non-zero non-null number
   * @param value   Number to check
   * @return        True if the number's value is not 0. and the {@link Long} itself
   * is not null
   */
  public static boolean isNotEmpty( Long value ){
    return lVal( value ) != 0;
  }
  
  public static boolean isEmpty( Long value ){
    return lVal( value ) == 0;
  }
  
  /**
   * 
   * @param value  -- string of a double value
   * @param maxDecimal 
   * @return if the double value passed (in string format) has decimal places less than max decimal places specified
   */
  public static boolean hasValidDecimal(String value, int maxDecimal) {
    if(! isValidDouble( value ))
      return false;
    
    int decimal = 0;
    if(value.indexOf( "." ) != -1) {
      String sDecimal = value.substring( value.indexOf( "." ) + 1 );
      decimal =  sDecimal.length();
    }
    
    return decimal <= maxDecimal;
  }
  
  /**
   * 
   * @param value  -- a double value
   * @return if the double value has any decimal 
   */
  public static boolean hasDecimal(double value) {
    
    if (value % 1.0 > 0 ) {
      return true;
    }
    
    return false;
  }
  
  /**
   * @return the argument as a String with the fractional part removed if removing said yields the same
   * number, logically (.0* removed).
   */
  public static String stripInconsequentialFractionPart(double amt) {
    if ((int)amt == amt) {
      return String.valueOf( (int)amt );
    }
    
    return String.valueOf( amt );
  }

  /**
   * Will format the currency according to the current users locale.
   * 
   * @param amt
   * @return
   */
  public static String formatCurrency( double amt ) {
    return formatCurrency( amt, 2 );
  }
  
  public static String formatCurrency( double amt, int fractionDigits ) {
    return getCurrencyFormat(fractionDigits).format( amt );
  }
  
  public static NumberFormat getCurrencyFormat() {
    return getCurrencyFormat(2);
  }

  public static NumberFormat getCurrencyFormat(int fractionDigits) {
    NumberFormat nf = NumberFormat.getCurrencyInstance( Locale.US );
    
    if ( fractionDigits !=  2 ) {
      nf.setMinimumFractionDigits( fractionDigits );
      nf.setMaximumFractionDigits( fractionDigits );
    }
    
    return nf;
  }

  
  public static String formatCurrencyWithCurrencyUnit( double amt, int fractionDigits ) {
    NumberFormat nf = getCurrencyFormat(fractionDigits);
    String currAmt=nf.format( amt );
    String symbol = (nf instanceof DecimalFormat) ?  ((DecimalFormat)nf).getDecimalFormatSymbols().getCurrencySymbol() : "" ;
    
	if (symbol.length()==1) {
		currAmt = currAmt + " " + nf.getCurrency();
	}    
    
    return currAmt;
  }
  

  

  /**
   * Compares its two arguments.  Returns a -1, 0, +1 integer if the first argument is less than, equal
   * to, or greater than the second.  Useful for implementing comparators that use a long value to sort.
   * 
   * @param v1
   * @param v2
   * @return -1, 0 or 1 is v1 is less than, equal to or greater than v2 respectively
   */
  public static int compare(long v1, long v2) {
    return ( v1 - v2 ) > 0 ? 1 : ( ( v1 - v2 ) == 0 ? 0 : -1 );
  }

  /**
   * Compares its two arguments.  Returns a -1, 0, +1 integer if the first argument is less than, equal
   * to, or greater than the second.  Useful for implementing comparators that use a double value to sort.
   * 
   * @param v1
   * @param v2
   * @return -1, 0 or 1 is v1 is less than, equal to or greater than v2 respectively
   */
  public static int compare(double v1, double v2) {
    return ( v1 - v2 ) > 0 ? 1 : ( ( v1 - v2 ) == 0 ? 0 : -1 );
  }

  /**
   * Format a number as a percent in the current locale.
   * The expected number is a decimal value where 1 = 100%, 0.5 = 50%, 1.5 = 150% etc...
   *
   * 
   * @param percent as a decimal value where 1 is 100%
   * @return A string representing the percentage in the current locale
   */
  public static String formatPercent( double percent ) {
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    
    return percentFormat.format( percent );
  }
  
  public static String formatPercent( double percent, int maximumFractionDigits ) {
    NumberFormat percentFormat = NumberFormat.getPercentInstance();
    percentFormat.setMaximumFractionDigits( maximumFractionDigits );
    return percentFormat.format( percent );
  }
  
  
  // FIXME add to unit test
  public static void main( String[] args ) {
    System.out.println(NumUtil.format4DecimalPoint(1));
    System.out.println(NumUtil.format4DecimalPoint(1.1));
    System.out.println(NumUtil.format4DecimalPoint(1.12));
    System.out.println(NumUtil.format4DecimalPoint(1.123));
    System.out.println(NumUtil.format4DecimalPoint(1.1234));
    System.out.println(NumUtil.format4DecimalPoint(1.12345));
    System.out.println(NumUtil.format4DecimalPoint(1.123456));
    System.out.println(NumUtil.format4DecimalPoint(12.123456));
    
    double a = 0.09d;
    double b = 0.01d;
    double c = 0.1d;
    
    System.out.println( ( a + b ) );
    System.out.println( c - ( a + b ) == 0 );
    
    //Currency Testing
    Locale canadianEnglish = new Locale( "en","CA" );
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance( Locale.CANADA_FRENCH );
    currencyFormat.setCurrency( Currency.getInstance( Locale.CANADA ) );
    currencyFormat.setMinimumFractionDigits( 2 );
    currencyFormat.setMaximumFractionDigits( 4 );
    double amt = -1.21234;
    System.out.println(amt + " formats as "  + currencyFormat.format( amt ));
    System.out.println( formatPercent( 2.34d ));
    System.out.println( formatPercent( 0.54d ));
    System.out.println( formatPercent( 99.99999d ));
    System.out.println( formatPercent( 99d ));
    System.out.println( formatPercent( 100d ));
    System.out.println( formatPercent( 150d ));
    System.out.println( formatPercent( 150.1d ));
    System.out.println("\n\n\n\n\n\n\n\n");
    
    System.out.println("formatNoDecimalPoint");
    
    System.out.println("0.000 -> "+NumUtil.formatNoDecimalPoint(0.000));
    System.out.println("0.0005 -> "+NumUtil.formatNoDecimalPoint(0.0005));
	System.out.println("0.05 -> "+NumUtil.formatNoDecimalPoint(0.05));
	System.out.println("0.5 -> "+NumUtil.formatNoDecimalPoint(0.5));
	System.out.println("1 -> "+NumUtil.formatNoDecimalPoint(1));
	System.out.println("1.1 -> "+NumUtil.formatNoDecimalPoint(1.1));
	System.out.println("1.12 -> "+NumUtil.formatNoDecimalPoint(1.12));
	System.out.println("1.123 -> "+NumUtil.formatNoDecimalPoint(1.123));
	System.out.println("1.1234 -> "+NumUtil.formatNoDecimalPoint(1.1234));
	System.out.println("1.12345 -> "+NumUtil.formatNoDecimalPoint(1.12345));
	System.out.println("1.123456 -> "+NumUtil.formatNoDecimalPoint(1.123456));
	System.out.println("12.12345699999999999999999999999999999999 -> "+NumUtil.formatNoDecimalPoint(12.12345699999999999999999999999999999999));
	System.out.println("566762.42 -> "+formatNoDecimalPoint(566762.42));
	System.out.println("56676242.00 -> "+formatNoDecimalPoint(56676242.00));
	System.out.println("2342342342.00 -> "+formatNoDecimalPoint(2342342342.00));
	System.out.println("12345678901234.12345678901234567890 -> "+formatNoDecimalPoint(12345678901234.12345678901234567890));
    
	
	System.out.println("NumUtil.format2DecimalPoint");
    
	System.out.println("0.0005 -> "+NumUtil.format2DecimalPoint(0.0005));
	System.out.println("0.05 -> "+NumUtil.format2DecimalPoint(0.05));
	System.out.println("0.5 -> "+NumUtil.format2DecimalPoint(0.5));
	System.out.println("1 -> "+NumUtil.format2DecimalPoint(1));
	System.out.println("1.1 -> "+NumUtil.format2DecimalPoint(1.1));
	System.out.println("1.12 -> "+NumUtil.format2DecimalPoint(1.12));
	System.out.println("1.123 -> "+NumUtil.format2DecimalPoint(1.123));
	System.out.println("1.1234 -> "+NumUtil.format2DecimalPoint(1.1234));
	System.out.println("1.12345 -> "+NumUtil.format2DecimalPoint(1.12345));
	System.out.println("1.123456 -> "+NumUtil.format2DecimalPoint(1.123456));
	System.out.println("12.12345699999999999999999999999999999999 -> "+NumUtil.format2DecimalPoint(12.12345699999999999999999999999999999999));
	System.out.println("566762.42 -> "+format2DecimalPoint(566762.42));
	System.out.println("56676242.00 -> "+format2DecimalPoint(56676242.00));
	System.out.println("2342342342.00 -> "+format2DecimalPoint(2342342342.00));
	System.out.println("12345678901234.12345678901234567890 -> "+format2DecimalPoint(12345678901234.12345678901234567890));
	
	
    
////////////
	
System.out.println("formatNoDecimalPoint");
    
    System.out.println("0.000 -> "+NumUtil.formatNoDecimalPoint(0.00));
    System.out.println("-0.0005 -> "+NumUtil.formatNoDecimalPoint(-0.0005));
	System.out.println("-0.05 -> "+NumUtil.formatNoDecimalPoint(-0.05));
	System.out.println("-0.5 -> "+NumUtil.formatNoDecimalPoint(-0.5));
	System.out.println("-1 -> "+NumUtil.formatNoDecimalPoint(-1));
	System.out.println("-1.1 -> "+NumUtil.formatNoDecimalPoint(-1.1));
	System.out.println("-1.12 -> "+NumUtil.formatNoDecimalPoint(-1.12));
	System.out.println("-1.123 -> "+NumUtil.formatNoDecimalPoint(-1.123));
	System.out.println("-1.1234 -> "+NumUtil.formatNoDecimalPoint(-1.1234));
	System.out.println("-1.12345 -> "+NumUtil.formatNoDecimalPoint(-1.12345));
	System.out.println("-1.123456 -> "+NumUtil.formatNoDecimalPoint(-1.123456));
	System.out.println("-12.12345699999999999999999999999999999999 -> "+NumUtil.formatNoDecimalPoint(-12.12345699999999999999999999999999999999));
	System.out.println("-566762.42 -> "+formatNoDecimalPoint(-566762.42));
	System.out.println("-56676242.00 -> "+formatNoDecimalPoint(-56676242.00));
	System.out.println("-2342342342.00 -> "+formatNoDecimalPoint(-2342342342.00));
	System.out.println("-12345678901234.12345678901234567890 -> "+formatNoDecimalPoint(-12345678901234.12345678901234567890));
    
	
	System.out.println("NumUtil.format2DecimalPoint");
    
	System.out.println("0.000 -> "+NumUtil.format2DecimalPoint(0.00));
	System.out.println("-0.0005 -> "+NumUtil.format2DecimalPoint(-0.0005));
	System.out.println("-0.05 -> "+NumUtil.format2DecimalPoint(-0.05));
	System.out.println("-0.5 -> "+NumUtil.format2DecimalPoint(-0.5));
	System.out.println("-1 -> "+NumUtil.format2DecimalPoint(-1));
	System.out.println("-1.1 -> "+NumUtil.format2DecimalPoint(-1.1));
	System.out.println("-1.12 -> "+NumUtil.format2DecimalPoint(-1.12));
	System.out.println("-1.123 -> "+NumUtil.format2DecimalPoint(-1.123));
	System.out.println("-1.1234 -> "+NumUtil.format2DecimalPoint(-1.1234));
	System.out.println("-1.12345 -> "+NumUtil.format2DecimalPoint(-1.12345));
	System.out.println("-1.123456 -> "+NumUtil.format2DecimalPoint(-1.123456));
	System.out.println("-12.12345699999999999999999999999999999999 -> "+NumUtil.format2DecimalPoint(-12.12345699999999999999999999999999999999));
	System.out.println("-566762.42 -> "+format2DecimalPoint(-566762.42));
	System.out.println("-56676242.00 -> "+format2DecimalPoint(-56676242.00));
	System.out.println("-2342342342.00 -> "+format2DecimalPoint(-2342342342.00));
	System.out.println("-12345678901234.12345678901234567890 -> "+format2DecimalPoint(-12345678901234.12345678901234567890));
	
	
	
	System.out.println("8865450.54 -> "+NumUtil.format2DecimalPoint(8865450.54));
	
	
	
	System.out.println( ratio( 7, 14 ) );
	
    System.out.println( ratio( 10293, 1234 ) );
	
    System.out.println( ratio( 50 * 23, 20 * 23 ) );
  }

  /**
   * @param long1
   * @param defaultValue
   * @return
   */
  public static long lVal( Number number, long defaultValue ) {
    if ( number == null ) return defaultValue;
    return number.longValue();
  }

  public static double dVal( Number number, double defaultValue ) {
    if ( number == null ) return defaultValue;
    return number.doubleValue();
  }

  public static double dVal( Number number ) {
    return dVal( number, 0.0 );
  }

  public static boolean isNullOrZero( Long number ) {
    return number == null || number.longValue() == 0;
  }

  public static boolean isNullOrZero( Integer number ) {
    return number == null || number.intValue() == 0;
  }
  
  //Return a string with leading zero to a number
  public static String addLeadingZerosToNumber (long number, int length){
    //the digits in the "number" is greater than or equal to the "length"
    if (length<= ((int) Math.log10(number) + 1)) return String.valueOf( number );
    
    return String.format("%0"+ length +"d", number) ;
  }
  
  //Return a string with leading zero to a string
  public static String addLeadingZerosToString (String number, int length){
    //the length of the "number" is greater than or equal to the "length"
    if ( StrUtil.isEmpty( number ) || length <=number.length()) return number;
    
    return (addLeadingZerosToNumber(0, length) + number).substring(number.length());
  }
  
  private static long gcd( long x, long y ) {
    return BigInteger.valueOf( x ).gcd( BigInteger.valueOf( y ) ).longValue();
  }
  
  public static String ratio( long x, long y ) {
    long lcd = gcd( x, y );
    if (lcd == 0) {
      return "0:0";
    }
    return (x/lcd) + ":" + (y/lcd);
  }

  /**
   * Checks if value is (inclusively) between min and max.
   * 
   * @param value
   * @param min
   * @param max
   * @return
   */
  public static boolean between( int value, int min, int max ) {
    return value >= min && value <= max;
  }
  
  /**
   * @param value
   * @param min
   * @param max
   */
  public static boolean between( double value, double min, double max ) {
    return value >= min && value <= max;
  }
}

