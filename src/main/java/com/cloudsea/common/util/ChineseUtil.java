package com.cloudsea.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * @author zhangxiaorong
 * 2014-1-26
 */
public class ChineseUtil {
	
	
	public static String toPinyin(String str) throws BadHanyuPinyinOutputFormatCombination {
		String string = "";
		
		if (str == null || str.equals("")){
			return string;
		}
		
		for (char c : str.toCharArray()){
			String s = String.valueOf(c);
			if (isChineseChar(s)){
				HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
				format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
				format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
				String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
				string += pinyin[0];
			} else {
				string += s;
			}
		}
		
		return string;
	}
	
	public static boolean isChineseChar(String str) {
		boolean temp = false;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			temp = true;
		}
		return temp;
	}

}
