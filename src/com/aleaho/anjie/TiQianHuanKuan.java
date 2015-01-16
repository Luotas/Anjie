package com.aleaho.anjie;

import java.util.Calendar;
import java.util.Date;

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
import com.aleaho.anjie.Data.*;

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

	EarlyPayMent earlyPayMent = new EarlyPayMent();
	Message msg = null;

	private DKLB daiKuanLeiBei;
	private JSFF jiSuanFangFa;
	private JHHKFS jihuahuankuanfangshi;
	private BFHKFS bufenhuankuanfangshi;

	private Date shouCiHuanKuan;
	private Date jiHuaHuanKuan;
	private float daiKuanZongE;
	private int huanKuanZongE;

	private int daiKuanQiXian;
	// 系统初始化利率
	/**
	 * 商业贷款利率
	 */
	private float SYSTEMSYDKSYLILV = 0;
	/**
	 * 公积金贷款利率
	 */
	private float SYSTEMGJJLILV = 0;
	private float systemLiLv = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tqhk);

		SYSTEMSYDKSYLILV = Float.parseFloat(this.getResources().getString(
				R.string.sydklilv));

		SYSTEMGJJLILV = Float.parseFloat(this.getResources().getString(
				R.string.gjjlilv));
		// 设定系统利率
		systemLiLv = SYSTEMSYDKSYLILV;
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
		etJiHuaHuanKuanJinE = (EditText) findViewById(R.id.etJiHuaHuanKuanJinE);
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

		Log.i(TAG, "进入提前还款模式");
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

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {

				boolean result = verifyForm();

				Log.i(TAG, "开始计算了！");

				Log.i(TAG, "部分还款方式：" + jihuahuankuanfangshi);
				// 部分还款比较还款金额与贷款总额关系
				if (jihuahuankuanfangshi == JHHKFS.BFHK) {
					daiKuanZongE = Tools.StringToFloat(etDaiKuanZongE.getText()
							.toString());
					huanKuanZongE = Tools.StringToInteger(etJiHuaHuanKuanJinE
							.getText().toString());
				}

			}

			// 验证界面数据输入是否合法
			private boolean verifyForm() {
				// TODO Auto-generated method stub
				msg = m_Handler.obtainMessage();

				boolean result = true;

				String daiKuanZongE = etDaiKuanZongE.getText().toString();
				String huanKuanZongE = etJiHuaHuanKuanJinE.getText().toString();
				String liLv = etDaiKuanLiLv.getText().toString();

				if (daiKuanZongE == null || daiKuanZongE.trim().equals("")
						|| daiKuanZongE.trim().equals("0")
						|| daiKuanZongE.trim().equals(".")) {
					etDaiKuanZongE.setError("请输入贷款总额！");
					result = false;
				}

				if (jihuahuankuanfangshi == JHHKFS.BFHK) {
					if (huanKuanZongE == null
							|| huanKuanZongE.trim().equals("")
							|| huanKuanZongE.trim().equals("0")) {
						etJiHuaHuanKuanJinE.setError("请输入计划还款金额！");
						result = false;
					}

					if (result) {
						if (Tools.StringToFloat(daiKuanZongE) < Tools
								.StringToFloat(huanKuanZongE)) {
							msg.what = 0x00011;
							result = false;
						}
					}

				}

				if (liLv == null || liLv.trim().equals("")
						|| liLv.trim().equals(".")) {
					etDaiKuanLiLv.setError("请输入贷款利率！");
					result = false;
				}

				shouCiHuanKuan = Tools.strToDate("yyyy-MM",
						etChuCiHuanKuanShiJian.getText().toString());
				jiHuaHuanKuan = Tools.strToDate("yyyy-MM",
						etJiHuaHuanKuanShiJian.getText().toString());
				Log.i(TAG, "首次还款时间" + shouCiHuanKuan + "  计划还款时间："
						+ shouCiHuanKuan);

				Log.i(TAG, "计划划款年份" + jiHuaHuanKuan.getYear());
				Log.i(TAG, "首次还款年份" + shouCiHuanKuan.getYear());

				int tiQianQiShu = (jiHuaHuanKuan.getYear() - shouCiHuanKuan
						.getYear())
						* 12
						+ jiHuaHuanKuan.getMonth()
						- shouCiHuanKuan.getMonth() - 1;

				Log.i(TAG, "计算提前还款结束！" + tiQianQiShu);
				if (tiQianQiShu > daiKuanQiXian || tiQianQiShu <= 0) {
					msg.what = 0x00001;
					result = false;
				}

				if (!result)
					m_Handler.sendMessage(msg);
				return result;
			}
		});
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
		if (id == R.id.setting) {

			Intent intent = new Intent(this, Setting.class);
			startActivity(intent);
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
			switch (parent.getId()) {
			// 贷款类型
			case R.id.SpDaikuanLeibie:
				switch (position) {
				// 设置为商业贷款利率
				case 0:
					systemLiLv = SYSTEMSYDKSYLILV;
					// etDaiKuanLiLv.setText()
					etDaiKuanLiLv.setText(String.valueOf(systemLiLv));
					break;
				case 1:
					// 设置为公积金贷款利率
					systemLiLv = SYSTEMGJJLILV;
					etDaiKuanLiLv.setText(String.valueOf(systemLiLv));
					break;

				}
				// 计算方法
			case R.id.SpJiSuanFangFa:
				switch (position) {
				case 0:
					jiSuanFangFa = JSFF.等额本息;
					break;
				case 1:
					jiSuanFangFa = JSFF.等额本息;
					break;
				}

				// 还款期限
			case R.id.SpDaiKuanQiXian:
				daiKuanQiXian = (position + 1) * 12;
				break;

			// 计划还款方式：一次提前还款，部分提前还款
			case R.id.spJiHuaHuanKuanFangShi:
				switch (position) {
				case 0:
					jihuahuankuanfangshi = JHHKFS.YCHW;
					txtJiHuaHuanKuanJinE.setVisibility(View.GONE);
					etJiHuaHuanKuanJinE.setVisibility(View.GONE);
					txtHuanKuanDanWei.setVisibility(View.GONE);
					txtBuFenHuanKuanFangShi.setVisibility(View.GONE);
					spBuFenHuanKuanFangShi.setVisibility(View.GONE);
					break;
				case 1:
					jihuahuankuanfangshi = JHHKFS.BFHK;
					txtJiHuaHuanKuanJinE.setVisibility(View.VISIBLE);
					etJiHuaHuanKuanJinE.setVisibility(View.VISIBLE);
					txtHuanKuanDanWei.setVisibility(View.VISIBLE);
					txtBuFenHuanKuanFangShi.setVisibility(View.VISIBLE);
					spBuFenHuanKuanFangShi.setVisibility(View.VISIBLE);
					break;

				}

				// 部分还款方式：还款期限不变，缩小还款额;还款额不变，缩短还款期限。
			case R.id.spBuFenHuanKuanFangShi:
				switch (position) {
				case 0:
					bufenhuankuanfangshi = BFHKFS.HKEBB;
					break;
				case 1:
					bufenhuankuanfangshi = BFHKFS.QXBB;
					break;
				}
			}
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
			case 0x00001:// 当提前还款时间长于或短于贷款期限，系统提示。
				Toast.makeText(TiQianHuanKuan.this, "请调整提前还款日期！",
						Toast.LENGTH_SHORT).show();
				break;
			case 0x00011:// 当提前还款时间长于或短于贷款期限，系统提示。
				Toast.makeText(TiQianHuanKuan.this, "提前还款金额大于贷款金额！",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

}
