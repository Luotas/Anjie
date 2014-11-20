package com.aleaho.anjie;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TiQianHuanKuan extends Activity {

	final String TAG = "ANJIE";
	private Spinner SpDaikuanLeibie = null;
	private Spinner SpJiSuanFangFa = null;
	private Spinner SpDaiKuanQiXian = null;
	private Spinner spJiHuaHuanKuanFangShi = null;
	private Spinner spBuFenHuanKuanFangShi = null;

	private EditText etDaiKuanZongE = null;
	private EditText etDaiKuanLiLv = null;
	private EditText etJiHuaHuanKuanJinE = null;

	private EditText etChuCiHuanKuanShiJian = null;
	private EditText etJiHuaHuanKuanShiJian = null;

	private TextView txtJiHuaHuanKuanJinE = null;
	private TextView txtBuFenHuanKuanFangShi = null;
	private TextView txtHuanKuanDanWei = null;

	private Calendar clChuCiHuanKuan = null;
	private Calendar clJiHuaHuanKuan = null;

	private Button btjisuan = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tqhk);

		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub

		SpDaikuanLeibie = (Spinner) findViewById(R.id.SpDaikuanLeibie);
		SpJiSuanFangFa = (Spinner) findViewById(R.id.SpJiSuanFangFa);
		SpDaiKuanQiXian = (Spinner) findViewById(R.id.SpDaiKuanQiXian);
		spJiHuaHuanKuanFangShi = (Spinner) findViewById(R.id.spJiHuaHuanKuanFangShi);
		spBuFenHuanKuanFangShi = (Spinner) findViewById(R.id.spBuFenHuanKuanFangShi);

		etDaiKuanLiLv = (EditText) findViewById(R.id.etDaiKuanLiLv);
		etDaiKuanZongE = (EditText) findViewById(R.id.etDaiKuanZongE);

		etChuCiHuanKuanShiJian = (EditText) findViewById(R.id.etChuCiHuanKuanShiJian);
		etJiHuaHuanKuanShiJian = (EditText) findViewById(R.id.etJiHuaHuanKuanShiJian);

		// etChuCiHuanKuanShiJian.setEnabled(false);
		// etJiHuaHuanKuanShiJian.setEnabled(false);

		txtJiHuaHuanKuanJinE = (TextView) findViewById(R.id.txtJiHuaHuanKuanJinE);
		txtBuFenHuanKuanFangShi = (TextView) findViewById(R.id.txtBuFenHuanKuanFangShi);
		txtHuanKuanDanWei = (TextView) findViewById(R.id.txtHuanKuanDanWei);

		btjisuan = (Button) findViewById(R.id.btjisuan);

		initSpinner();
		// getHuanKuanShiJian();

		SpDaikuanLeibie
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		SpJiSuanFangFa
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		SpJiSuanFangFa
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		SpDaiKuanQiXian
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spJiHuaHuanKuanFangShi
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spBuFenHuanKuanFangShi
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());

		etJiHuaHuanKuanShiJian.setOnClickListener(etClickListener);
		etChuCiHuanKuanShiJian.setOnClickListener(etClickListener);

		// 设置Spinner初始化选中项目，并触发OnItemSelectedListener事件，
		SpDaikuanLeibie.setSelection(0, true);
		SpJiSuanFangFa.setSelection(0, true);
		SpDaiKuanQiXian.setSelection(19, true);
		spJiHuaHuanKuanFangShi.setSelection(0, true);

		btjisuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

	private void getHuanKuanShiJian() {
		// TODO Auto-generated method stub

		Log.i(TAG, "初始化还款时间。");
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(Tools.strToDate("yyyy-MM", etChuCiHuanKuanShiJian
				.getText().toString()));
		clChuCiHuanKuan = localCalendar;
		localCalendar.setTime(Tools.strToDate("yyyy-MM", etJiHuaHuanKuanShiJian
				.getText().toString()));
		clJiHuaHuanKuan = localCalendar;

		Log.i(TAG,
				"初次还款时间：" + Tools.clanderTodatetime(clChuCiHuanKuan, "YYYY-MM")
						+ "小于计划还款时间:"
						+ Tools.clanderTodatetime(clJiHuaHuanKuan, "YYYY-MM"));
	}

	private void initSpinner() {
		// TODO Auto-generated method stub

		// 建立贷款类别数据源
		String[] daikuanleibieItems = getResources().getStringArray(
				R.array.spdaikuanleibie);
		initSpinner(daikuanleibieItems, SpDaikuanLeibie);

		// 建立计算方法数据源
		String[] jisuanfangfaItems = getResources().getStringArray(
				R.array.spjisuanfangfa);
		initSpinner(jisuanfangfaItems, SpJiSuanFangFa);

		// 建立贷款期限数据源
		String[] huankuanqixianItems = getResources().getStringArray(
				R.array.sphuankuanqixian);
		initSpinner(huankuanqixianItems, SpDaiKuanQiXian);

		// 建立计划还款数据源
		String[] jihuahuankuanItems = getResources().getStringArray(
				R.array.spjihuankuanfangshi);
		initSpinner(jihuahuankuanItems, spJiHuaHuanKuanFangShi);

		// 建立部分还款乱数据源
		String[] bufenhuankuanItems = getResources().getStringArray(
				R.array.spbufenhuankuanfangshi);
		initSpinner(bufenhuankuanItems, spBuFenHuanKuanFangShi);
	}

	private OnClickListener etClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			EditText txt = null;
			int flag = 0;
			switch (v.getId()) {
			case R.id.etChuCiHuanKuanShiJian:
				flag = 1;
				txt = (EditText) v;
				showAleahoPicker(TiQianHuanKuan.this, txt, flag);
				break;

			case R.id.etJiHuaHuanKuanShiJian:
				flag = 2;
				txt = (EditText) v;
				showAleahoPicker(TiQianHuanKuan.this, txt, flag);
				break;
			}
		}

		private void showAleahoPicker(Context context, final EditText textView,
				int flag) {
			final Calendar localCalendar = Calendar.getInstance();
			if (1 == flag)
				clChuCiHuanKuan = localCalendar;
			else if (2 == flag)
				clJiHuaHuanKuan = localCalendar;
			localCalendar.setTime(Tools.strToDate("yyyy-MM", textView.getText()
					.toString()));
			new AleahoPickerDialog(context,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							localCalendar.set(1, year);
							localCalendar.set(2, monthOfYear);
							Log.i(TAG, "开始设置文本框内容了");
							textView.setText(Tools.clanderTodatetime(
									localCalendar, "yyyy-MM"));

						}
					}, localCalendar.get(1), localCalendar.get(2),
					localCalendar.get(5)).show();
		}
	};

	/**
	 * 初始化Spinner,由initSpinner调用
	 * 
	 * @param items
	 *            Spinner数据源
	 * @param spinner
	 *            需要初始化的Spinner的实例对象
	 */
	private void initSpinner(String[] items, Spinner spinner) {
		Tools.initSpinner(TiQianHuanKuan.this, items, spinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.daikuanjisuan) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		if (id == R.id.tiqianhuangkuan) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private class SpinnerOnItemSelectedListener implements
			OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}

	private Handler m_Handler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:

				break;

			case 1:

				break;
			}
		}

	};

}
