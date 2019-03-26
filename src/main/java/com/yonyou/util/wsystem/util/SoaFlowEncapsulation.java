package com.yonyou.util.wsystem.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class SoaFlowEncapsulation {
		
	 public static String  decode(String flowString){
		 return URLDecoder.decode(flowString);
	 }
	 
	 public static String  encode(String flowString){
		 return URLEncoder.encode(flowString);
	 }
}
