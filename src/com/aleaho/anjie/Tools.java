package com.aleaho.anjie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
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

	// �ַ�����������ת����date����
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

	@SuppressLint("SimpleDateFormat")
	public static String dateToStr(String style, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(date);
	}

	public static CharSequence clanderToString(Calendar calendar, String style) {

		//SimpleDateFormat sdf = new SimpleDateFormat(style);
		//String dateStr = sdf.format(calendar.getTime());
		CharSequence dateStr= DateFormat.format(style, calendar);
		return dateStr;
	}
	
	@SuppressLint("SimpleDateFormat") 
	public static String clanderTodatetime(Calendar calendar, String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}
}
