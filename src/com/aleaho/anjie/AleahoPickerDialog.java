package com.aleaho.anjie;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * 重写DatePickerDialog 只显示年月
 * 
 * @author Aleaho Yang
 * 
 */
@SuppressLint("SimpleDateFormat")
public class AleahoPickerDialog extends DatePickerDialog {

	public AleahoPickerDialog(Context context, OnDateSetListener onDateSetListener,
			int year, int monthOfYear, int dayOfMonth) {
		super(context, onDateSetListener, year, monthOfYear, dayOfMonth);
		this.setTitle(year + "年" + (monthOfYear + 1) + "月");
		((ViewGroup) ((ViewGroup) this.getDatePicker().getChildAt(0))
				.getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		this.setTitle(year + "年" + (month + 1) + "月");
	}
	

}
