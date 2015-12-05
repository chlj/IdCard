package com.example.idcard;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.trinea.android.common.util.RandomUtils;

/**
 * /指定出生地、出生日期及性别就可生成1到999个格式正确的身份证号码！切记不可用于非法活动，否则后果自负！
 * 
 * @author Administrator
 * 
 */
public class IdCardUtils {

	private static final String beginDate="2005-1-1";
	private static final String endDate="2015-12-31";
	
	/**
	 * 得到指定日期范围内的随机一个日期
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static String getRandomData(String beginDate, String endDate) {

		Date randomDate = randomDate(beginDate, endDate); // 2月29
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(randomDate);
	}

	//指定年份
	private static String getData(String Date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start =null;
		try{
			start=format.parse(Date);// 开始日期
		}
		catch(Exception e){
			
		}
		return format.format(start);
	}
	
	private static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 开始日期
			Date end = format.parse(endDate);// 结束日期
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		if (rtnn == begin || rtnn == end) {
			return random(begin, end);
		}
		return rtnn;
	}

	/**
	 * 得到最后的3位顺序码
	 * 
	 * @param flag
	 *            1男,0女
	 * @return
	 */
	private static String getThreeCode(int flag) {
		// 0~999 随机的奇数
		if (flag == 1) {
			// 男
			int m = RandomUtils.getRandom(999);
			if (m % 2 == 1) {
				if (String.valueOf(m).length() == 1) {
					return "00" + m;
				} else if (String.valueOf(m).length() == 2) {
					return "0" + m;
				} else {
					return String.valueOf(m);
				}
			} else {
				return getThreeCode(flag);
			}
		} else {
			// 女

			int m = RandomUtils.getRandom(999);
			if (m % 2 == 0) {
				if (String.valueOf(m).length() == 1) {
					return "00" + m;
				} else if (String.valueOf(m).length() == 2) {
					return "0" + m;
				} else {
					return String.valueOf(m);
				}
			} else {
				return getThreeCode(flag);
			}
		}

	}
	
	/**
	 * 得到最后一位数
	 * @param y
	 * @return
	 */
	private static String getLastNumber(String y){
		int str[] ={7 ,9 ,10, 5, 8, 4, 2 ,1, 6, 3, 7, 9 ,10 ,5 ,8 ,4 ,2};
	    char[] c=y.toCharArray();
	    int sum=0;
	    for(int i=0;i<c.length;i++){
	    	sum=sum + (str[i] *  Integer.parseInt(String.valueOf(c[i])) );
	    }
	    int last_index=sum%11;
	    String ym[]={"1","0", "X","9", "8", "7", "6", "5", "4", "3", "2"};
	    String last_str=ym[last_index];
	    System.out.println("sum="+sum+",last_index="+sum%11+",last_str="+last_str);
	    System.out.println("y=" +y+last_str);
	    return y+last_str;
	}
	/**
	 * 
	 * @param Code
	 * 行政编码
	 * @param sex
	 *  1男,0女
	 * @return
	 */
	public static String getIdCard(String Code,int sex){
		String y=Code+getRandomData(beginDate,endDate).replace("-", "")+getThreeCode(sex);
		return getLastNumber(y);
	}
	
	/**
	 * 性别随机
	 * @param Code
	 * @return
	 */
	public static String getIdCard(String Code){
		return getIdCard(Code,RandomUtils.getRandom(0, 1));
	}
	
	
	//头2位为省ID
	public static String getProviceCode(String LocationCode){
	  return LocationCode;
	}
}
