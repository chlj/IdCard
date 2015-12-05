package com.example.idcard;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.trinea.android.common.util.RandomUtils;

/**
 * /ָ�������ء��������ڼ��Ա�Ϳ�����1��999����ʽ��ȷ�����֤���룡�мǲ������ڷǷ�����������Ը���
 * 
 * @author Administrator
 * 
 */
public class IdCardUtils {

	private static final String beginDate="2005-1-1";
	private static final String endDate="2015-12-31";
	
	/**
	 * �õ�ָ�����ڷ�Χ�ڵ����һ������
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private static String getRandomData(String beginDate, String endDate) {

		Date randomDate = randomDate(beginDate, endDate); // 2��29
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(randomDate);
	}

	//ָ�����
	private static String getData(String Date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date start =null;
		try{
			start=format.parse(Date);// ��ʼ����
		}
		catch(Exception e){
			
		}
		return format.format(start);
	}
	
	private static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// ��ʼ����
			Date end = format.parse(endDate);// ��������
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
	 * �õ�����3λ˳����
	 * 
	 * @param flag
	 *            1��,0Ů
	 * @return
	 */
	private static String getThreeCode(int flag) {
		// 0~999 ���������
		if (flag == 1) {
			// ��
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
			// Ů

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
	 * �õ����һλ��
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
	 * ��������
	 * @param sex
	 *  1��,0Ů
	 * @return
	 */
	public static String getIdCard(String Code,int sex){
		String y=Code+getRandomData(beginDate,endDate).replace("-", "")+getThreeCode(sex);
		return getLastNumber(y);
	}
	
	/**
	 * �Ա����
	 * @param Code
	 * @return
	 */
	public static String getIdCard(String Code){
		return getIdCard(Code,RandomUtils.getRandom(0, 1));
	}
	
	
	//ͷ2λΪʡID
	public static String getProviceCode(String LocationCode){
	  return LocationCode;
	}
}
