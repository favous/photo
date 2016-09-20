package com.cloudsea.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalHelper {

	public static int compare(double d1 , double d2){
	   if (round(d1)>round(d2)){
		   return 1;
	   }else if (round(d1)==round(d2)){
		   return 0;
	   }else{
		   return -1; 
	   }
	}
	public static double getDoubleValue(BigDecimal value){
	   if (value==null){
		   return 0.0;
	   }else {
		   return value.doubleValue();
	   }
   }
   public static int getIntValue(BigDecimal value){
	   if (value==null){
		   return 0;
	   }else {
		   return value.intValue();
	   }
   }

   public static long getLongValue(BigDecimal value){
	   if (value==null){
		   return 0l;
	   }else {
		   return value.longValue();
	   }
   }
   
   /**
    * 两数相加
    * @param orignValue
    * @param addValue
    * @return
    */
   public static BigDecimal addValue(BigDecimal orignValue,BigDecimal addValue){
	   if(orignValue==null)
		   orignValue=new BigDecimal(0);
	   if (addValue==null)
		   addValue=new BigDecimal(0);

//	   if(orignValue==null||addValue==null) return null;
//	  else return orignValue.add(addValue);
	   return new BigDecimal(addValue(orignValue.doubleValue(),addValue.doubleValue()));
   }
   
   /**
    * 两数相减
    * @param orignValue
    * @param substractValue
    * @return
    */
   public static BigDecimal substractValue(BigDecimal orignValue,BigDecimal substractValue){
	   if(orignValue==null)
		   orignValue=new BigDecimal(0);
	   if (substractValue==null)
		   substractValue=new BigDecimal(0);
//
//	   if(orignValue==null||substractValue==null) return null;
//	  else return orignValue.subtract(substractValue);
	   return new BigDecimal(substractValue(orignValue.doubleValue(),substractValue.doubleValue()));

   }
   
   /**
    * 两数相乘
    * @param orignValue
    * @param multiplyValue
    * @return
    */
   public static BigDecimal multiplyValue(BigDecimal orignValue,BigDecimal multiplyValue){
	   if(orignValue==null)
		   orignValue=new BigDecimal(0);
	   if (multiplyValue==null)
		   multiplyValue=new BigDecimal(0);
//	  if(orignValue==null||multiplyValue==null) return null;
//	  else return orignValue.multiply(multiplyValue);
	   return new BigDecimal(multiplyValue(orignValue.doubleValue(),multiplyValue.doubleValue()));
   }
   
   /**
    * 两数相除
    * @param orignValue
    * @param multiplyValue
    * @return
 * @throws Exception 
    */
   public static BigDecimal divideValue(BigDecimal orignValue,BigDecimal divideValue) throws Exception{
	   if(orignValue==null)
		   orignValue=new BigDecimal(0);
	   if (divideValue==null)
     	   throw new Exception("除数不能为空");
//	  if(orignValue==null||divideValue==null||divideValue.compareTo(new BigDecimal(0))==0) return null;
//	  else return orignValue.divide(divideValue);
	   return new BigDecimal(divideValue(orignValue.doubleValue(),divideValue.doubleValue()));
   }
   
public static double round(double value, int scale) {   
       BigDecimal bd = new BigDecimal(value);   
       bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);   
       double d = bd.doubleValue();   
       bd = null;   
       return d;   
}   

public static double round(double value) {   
       BigDecimal bd = new BigDecimal(value);   
       bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);   
       double d = bd.doubleValue();   
       bd = null;   
       return d;   
}

public static double addValue(double d1,double d2){
	BigDecimal bd1=new BigDecimal(Double.toString(d1));
	BigDecimal bd2=new BigDecimal(Double.toString(d2));
	return bd1.add(bd2).doubleValue();
}

public static double substractValue(double d1,double d2){
	BigDecimal bd1=new BigDecimal(Double.toString(d1));
	BigDecimal bd2=new BigDecimal(Double.toString(d2));
	return bd1.subtract(bd2).doubleValue();
}

public static double multiplyValue(double d1,double d2){
	BigDecimal bd1=new BigDecimal(Double.toString(d1));
	BigDecimal bd2=new BigDecimal(Double.toString(d2));
	return bd1.multiply(bd2).doubleValue();
}

public static double divideValue(double d1,double d2){
	BigDecimal bd1=new BigDecimal(Double.toString(d1));
	BigDecimal bd2=new BigDecimal(Double.toString(d2));
	
	return bd1.divide(bd2,3,BigDecimal.ROUND_HALF_EVEN).doubleValue();
}

public static String getUpdateValue(double value){
	DecimalFormat   df1   =   new   DecimalFormat( "#0.###"); 
	return df1.format(value);
}

public static String getUpdateValue(BigDecimal value){
	if (value==null) value=new BigDecimal(0);
	return getUpdateValue(value.doubleValue());
}
   
   public static void main(String[] args){
   }
   
   
}
