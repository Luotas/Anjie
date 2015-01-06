package com.aleaho.anjie;

import java.io.FileInputStream;
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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Tools {
	static final String TAG = "ANJIE";

	/**
	 * 初始化Spinner
	 * 
	 * @param context
	 *            Spinner所在的上下文即 XXX.class。
	 * @param items
	 *            Spinner加载数据源。
	 * @param spinner
	 *            需要绑定数据的spinner实例。
	 */

	public static void initSpinner(Context context, String[] items,
			Spinner spinner) {
		ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, items);
		_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
		spinner.setAdapter(_Adapter);
	}

	/**
	 * 将字符串日期转换成指定格式日期实例
	 * 
	 * @param style
	 *            指定日期格式 "yyyy-mm"、"yy-mm"等
	 * @param date
	 *            待转换字符串日期
	 * @return 转换后字符日期实例
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
	 * 将日期实例转换成指定格式日期字符串
	 * 
	 * @param style
	 *            指定日期格式 "yyyy-mm"、"yy-mm"等
	 * @param date
	 *            待转换日期实例
	 * @return 转换后字符类型日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateToStr(String style, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(date);
	}

	/**
	 * 将日历类型转换成指定格式日期字符
	 * 
	 * @param calendar
	 *            待转换日历实例
	 * @param style
	 *            指定字符格式 "yyyy-mm"、"yy-mm"等
	 * @return 转换后字符类型日期
	 */
	public static CharSequence clanderToString(Calendar calendar, String style) {

		// SimpleDateFormat sdf = new SimpleDateFormat(style);
		// String dateStr = sdf.format(calendar.getTime());
		CharSequence dateStr = DateFormat.format(style, calendar);
		return dateStr;
	}

	/**
	 * 将日历类型转换成指定格式日期字符
	 * 
	 * @param calendar
	 *            待转换日历实例
	 * @param style
	 *            指定字符格式 "yyyy-mm"、"yy-mm"等
	 * @return 转换后字符类型日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String clanderTodatetime(Calendar calendar, String style) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		String dateStr = sdf.format(calendar.getTime());
		return dateStr;
	}

	/**
	 * 将字符转换成浮点型数字
	 * 
	 * @param text
	 *            待转换字符
	 * @return 转换后数字
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
	 * 将字符转换成整型数字
	 * 
	 * @param text
	 *            待转换字符
	 * @return 转换后数字
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

	public static Properties getProperties(Context context, String file) {
		Properties properties = new Properties();
		try {
			FileInputStream s = context.openFileInput(file);
			// FileInputStream s = new FileInputStream(file);
			properties.load(s);
		} catch (Exception e) {
			e.printStackTrace();

			properties = null;
		}
		return properties;
	}

	/**
	 * 获得系统的配置文件
	 * 
	 * @param c
	 *            应用程序上下文
	 * @return 系统配置文件实例
	 */
	public static Properties getProperties(Context c) {

		Properties props = new Properties();
		try {
			// 方法一：通过activity中的context攻取setting.properties的FileInputStream
			InputStream in = c.getAssets().open("appConfig.properties");
			// 方法二：通过class获取setting.properties的FileInputStream
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
	 * 设置程序配置文件
	 * 
	 * @param c
	 *            应用程序上下文
	 * @param properties
	 *            应用程序配置集合
	 * @return 应用程序配置文件写成功 成功：true，不成功：false
	 */
	public static boolean setProperties(Context c, String file,
			HashMap<String, String> properties) {

		boolean isWrited = false;
		int flag = 0;
		Properties props = new Properties();
		try {

			FileOutputStream out = c.openFileOutput(file,
					Context.MODE_WORLD_READABLE);

			//new FileOutputStream(file, false);
			Iterator<java.util.Map.Entry<String, String>> iterator = properties
					.entrySet().iterator();
			while (iterator.hasNext()) {
				java.util.Map.Entry<String, String> entry = iterator.next();
				props.put(entry.getKey(), entry.getValue());
			}

			props.store(out, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			Log.e(TAG, e1.toString());
			flag = 1;
		}
		if (0 == flag)
			isWrited = true;
		return isWrited;
	}

}
