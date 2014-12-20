package com.aleaho.anjie;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.DropBoxManager.Entry;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Tools {

	/**
	 * ��ʼ��Spinner
	 * 
	 * @param context
	 *            Spinner���ڵ������ļ� XXX.class��
	 * @param items
	 *            Spinner��������Դ��
	 * @param spinner
	 *            ��Ҫ�����ݵ�spinnerʵ����
	 */

	public static void initSpinner(Context context, String[] items,
			Spinner spinner) {
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, items);
		_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spinner.setAdapter(_Adapter);
	}

	/**
	 * ���ַ�������ת����ָ����ʽ����ʵ��
	 * 
	 * @param style
	 *            ָ�����ڸ�ʽ "yyyy-mm"��"yy-mm"��
	 * @param date
	 *            ��ת���ַ�������
	 * @return ת�����ַ�����ʵ��
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date strToDate(String style, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 * ������ʵ��ת����ָ����ʽ�����ַ���
	 * 
	 * @param style
	 *            ָ�����ڸ�ʽ "yyyy-mm"��"yy-mm"��
	 * @param date
	 *            ��ת������ʵ��
	 * @return ת�����ַ���������
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateToStr(String style, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(date);
	}

	/**
	 * ����������ת����ָ����ʽ�����ַ�
	 * 
	 * @param calendar
	 *            ��ת������ʵ��
	 * @param style
	 *            ָ���ַ���ʽ "yyyy-mm"��"yy-mm"��
	 * @return ת�����ַ���������
	 */
	public static CharSequence clanderToString(Calendar calendar, String style) {

		// SimpleDateFormat sdf = new SimpleDateFormat(style);
		// String dateStr = sdf.format(calendar.getTime());
		CharSequence dateStr = DateFormat.format(style, calendar);
		return dateStr;
	}

	/**
	 * ����������ת����ָ����ʽ�����ַ�
	 * 
	 * @param calendar
	 *            ��ת������ʵ��
	 * @param style
	 *            ָ���ַ���ʽ "yyyy-mm"��"yy-mm"��
	 * @return ת�����ַ���������
	 */
	@SuppressLint("SimpleDateFormat")
	public static String clanderTodatetime(Calendar calendar, String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}

	/**
	 * ���ַ�ת���ɸ���������
	 * 
	 * @param text
	 *            ��ת���ַ�
	 * @return ת��������
	 */
	public static float StringToFloat(String text) {

		float result;
		try {
			result = Float.parseFloat(text);
		} catch (NumberFormatException e) {
			result = 0;
		}

		return result;
	}

	/**
	 * ���ַ�ת������������
	 * 
	 * @param text
	 *            ��ת���ַ�
	 * @return ת��������
	 */
	public static int StringToInteger(String text) {

		int result;
		try {
			result = Integer.parseInt(text);
		} catch (NumberFormatException e) {
			result = -1;
		}

		return result;
	}

	/**
	 * ���ϵͳ�������ļ�
	 * 
	 * @param c
	 *            Ӧ�ó���������
	 * @return ϵͳ�����ļ�ʵ��
	 */
	public static Properties getProperties(Context c) {

		Properties props = new Properties();
		try {
			// ����һ��ͨ��activity�е�context��ȡsetting.properties��FileInputStream
			InputStream in = c.getAssets().open("appConfig.properties");
			// ��������ͨ��class��ȡsetting.properties��FileInputStream
			// InputStream in =
			// PropertiesUtill.class.getResourceAsStream("/assets/setting.properties "));
			props.load(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			props = null;
		}
		return props;
	}

	/**
	 * ���ó��������ļ�
	 * 
	 * @param c
	 *            Ӧ�ó���������
	 * @param properties
	 *            Ӧ�ó������ü���
	 * @return Ӧ�ó��������ļ�д�ɹ� �ɹ���true�����ɹ���false
	 */
	public static boolean setProperties(Context c,
			HashMap<String, String> properties) {

		boolean isWrited = false;
		int flag = 0;
		Properties props = new Properties();
		try {
			// InputStream in = c.getAssets().open("appConfig.properties");
			FileOutputStream out = new FileOutputStream(
					"/assests/appConfig.properties");

			Iterator<java.util.Map.Entry<String, String>> iterator = properties
					.entrySet().iterator();
			while (iterator.hasNext()) {
				java.util.Map.Entry<String, String> entry = iterator.next();
				props.put(entry.getKey(), entry.getValue());
			}

			props.store(out, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			flag = 1;
		}
		if (0 == flag)
			isWrited = true;
		return isWrited;
	}

}
