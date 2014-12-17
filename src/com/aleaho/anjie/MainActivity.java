package com.aleaho.anjie;

import com.aleaho.anjie.Data.*;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	final String TAG = "ANJIE";
	/**
	 * 贷款类别
	 */
	Spinner spDaikuanleibie = null;
	/**
	 * 贷款计算方法
	 */
	Spinner spJisuanfangfa = null;
	/**
	 * 贷款期限
	 */
	Spinner spDaikuanqixian = null;
	/**
	 * 利率模式
	 */
	Spinner spLilvmoshi = null;
	/**
	 * 利率折扣
	 */
	Spinner spLilvzhekou = null;

	/**
	 * 年利率
	 */
	EditText etNianLiLv = null;
	/**
	 * 贷款总额
	 */
	EditText etDakuanzonge = null;

	Button btJS = null;

	private DKLB daiKuanLeiBei;
	private JSFF jiSuanFangFa;

	private float daiKuanLilv = 0;

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
	private int daiKuanZongE = 0;
	private int daiKuanQiXian = 0;

	private Message m_Message = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SYSTEMSYDKSYLILV = Float.parseFloat(this.getResources().getString(
				R.string.sydklilv));

		SYSTEMGJJLILV = Float.parseFloat(this.getResources().getString(
				R.string.gjjlilv));

		initView();
	}

	/**
	 * 初始化界面控件
	 */
	private void initView() {
		// TODO Auto-generated method stub

		Log.i(TAG, "初始化界面控件");

		spDaikuanleibie = (Spinner) findViewById(R.id.SpDaikuanLeibie);
		spJisuanfangfa = (Spinner) findViewById(R.id.SpJiSuanFangFa);
		spDaikuanqixian = (Spinner) findViewById(R.id.SpDaiKuanQiXian);
		spLilvmoshi = (Spinner) findViewById(R.id.SpLiLvMoShi);
		spLilvzhekou = (Spinner) findViewById(R.id.spLiLvZheKou);
		btJS = (Button) findViewById(R.id.btjisuan);

		etNianLiLv = (EditText) findViewById(R.id.etNianLilv);
		etNianLiLv.setEnabled(false);

		etDakuanzonge = (EditText) findViewById(R.id.etDaiKuanZongE);
		etDakuanzonge.setFocusable(true);

		// 设定系统利率
		systemLiLv = SYSTEMSYDKSYLILV;

		initSpinner();

		spDaikuanleibie
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spJisuanfangfa
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spDaikuanqixian
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spLilvmoshi
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());
		spLilvzhekou
				.setOnItemSelectedListener(new SpinnerOnItemSelectedListener());

		// 设置Spinner初始化选中项目，并触发OnItemSelectedListener事件，
		spDaikuanleibie.setSelection(0, true);
		spJisuanfangfa.setSelection(0, true);
		spDaikuanqixian.setSelection(19, true);
		spLilvmoshi.setSelection(0, true);
		spLilvzhekou.setSelection(4, true);

		btJS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				m_Message = m_Handler.obtainMessage();
				int flag = 0;

				try {

					Log.i(TAG, "获得当前贷款利率！！！！");
					daiKuanLilv = Float.parseFloat(etNianLiLv.getText()
							.toString()) / 100 / 12;

					Log.i(TAG, "当前贷款利率:" + daiKuanLilv);

				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "MainActivity界面年利率未输入!!\n" + e.toString());
					flag = flag + 1;
				}

				try {

					Log.i(TAG, "获得当前贷款总额！！！！");
					daiKuanZongE = (int) (Float.parseFloat(etDakuanzonge
							.getText().toString()) * 10000);
					Log.i(TAG, "获得当前贷款总额:" + daiKuanZongE);

				} catch (Exception e) {
					// TODO: handle exception

					Log.e(TAG, "MainActivity界面贷款总额未输入!!\n" + e.toString());
					flag = flag + 2;
					Log.e(TAG, "MainActivity界面flag=" + flag);
				}

				if (0 == flag) {
					Log.i(TAG, "准备跳转到显示界面了");
					Intent intent = new Intent(MainActivity.this,
							AnJieInfo.class);
					intent.putExtra("DaiKuanLiLv", daiKuanLilv);
					intent.putExtra("DaiKuanZongE", daiKuanZongE);
					intent.putExtra("DaiKuanLeiBei", daiKuanLeiBei);
					intent.putExtra("JiSuanFangFa", jiSuanFangFa);
					intent.putExtra("DaiKuanQiXian", daiKuanQiXian);
					Log.i(TAG, "MA贷款期限：" + daiKuanQiXian);
					startActivity(intent);
				} else {
					m_Message.what = flag;
					m_Handler.sendMessage(m_Message);
				}
			}
		});
	}

	// 初始化各种Spinner
	private void initSpinner() {
		// TODO Auto-generated method stub

		// 建立贷款类别数据源
		String[] daikuanleibieItems = getResources().getStringArray(
				R.array.spdaikuanleibie);
		initSpinner(daikuanleibieItems, spDaikuanleibie);

		// 建立计算方法数据源
		String[] jisuanfangfaItems = getResources().getStringArray(
				R.array.spjisuanfangfa);
		initSpinner(jisuanfangfaItems, spJisuanfangfa);

		// 建立贷款期限数据源
		String[] huankuanqixianItems = getResources().getStringArray(
				R.array.sphuankuanqixian);
		initSpinner(huankuanqixianItems, spDaikuanqixian);

		// 建立利率模式数据源
		String[] lilvmoshiItems = getResources().getStringArray(
				R.array.splilvmoshi);
		initSpinner(lilvmoshiItems, spLilvmoshi);

		// 建立商业贷款利率折扣数据源
		String[] shydlilvzhekouItems = getResources().getStringArray(
				R.array.spshydlilvzhekou);
		initSpinner(shydlilvzhekouItems, spLilvzhekou);
	}

	/**
	 * 初始化Spinner,由initSpinner调用
	 * 
	 * @param items
	 *            Spinner数据源
	 * @param spinner
	 *            需要初始化的Spinner的实例对象
	 */
	private void initSpinner(String[] items, Spinner spinner) {
		Tools.initSpinner(MainActivity.this, items, spinner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.daikuanjisuan) {
			return true;
		}
		if (id == R.id.tiqianhuangkuan) {

			Intent intent = new Intent(this, TiQianHuanKuan.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private class SpinnerOnItemSelectedListener implements
			OnItemSelectedListener {

		// 当前利率折扣
		int _positon = 4;

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			switch (parent.getId()) {
			// 根据贷款类别设置贷款折扣以及贷款利息信息
			case R.id.SpDaikuanLeibie:
				switch (position) {
				case 0:
					daiKuanLeiBei = DKLB.商业贷款;
					changeDaiKuanLeibie(position);
					break;
				case 1:
					daiKuanLeiBei = DKLB.公积金贷款;
					changeDaiKuanLeibie(position);
					break;
				}

				break;
			case R.id.SpJiSuanFangFa:
				switch (position) {
				case 0:
					jiSuanFangFa = JSFF.等额本息;
					Log.i(TAG, "计算方法为：等额本息" + " postion=" + position);
					break;
				default:
					jiSuanFangFa = JSFF.等额本金;
					Log.i(TAG, "计算方法为：等额本金" + " postion=" + position);
					break;
				}
				break;

			case R.id.SpDaiKuanQiXian:

				daiKuanQiXian = (position + 1) * 12;
				Log.i(TAG, "贷款期限为：" + daiKuanQiXian + " postion=" + position);
				break;

			case R.id.SpLiLvMoShi:
				switch (position) {
				// 系统利率
				case 0:
					etNianLiLv.setEnabled(false);
					spLilvzhekou.setEnabled(true);
					etNianLiLv.clearFocus();
					setLiLvZheKou(_positon, daiKuanLeiBei);
					break;

				// 自定义利率
				default:
					etNianLiLv.setEnabled(true);
					spLilvzhekou.setEnabled(false);
					etNianLiLv.requestFocus();
					_positon = spLilvzhekou.getSelectedItemPosition();
					break;
				}
				break;

			/**
			 * <item >7折利率</item> <item >8折利率</item> <item >85折利率</item> <item
			 * >9折利率</item> <item >基准利率(1.0)</item> <item >第二套房(1.1)</item>
			 * <item >其他上浮(1.05)</item> <item >其他上浮(1.15)</item> <item
			 * >其他上浮(1.2)</item>
			 */
			case R.id.spLiLvZheKou:
				setLiLvZheKou(position, daiKuanLeiBei);
				break;
			}
		}

		// 根据利率折扣以及贷款类别设置利率
		private void setLiLvZheKou(int position, DKLB daiKuanLeibie) {

			// 商业贷款类别，根据利率折扣计算利率
			if (daiKuanLeibie == DKLB.商业贷款)
				switch (position) {
				case 0:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 0.7));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 0.7 + " postion="
							+ position);
					break;
				case 1:
					etNianLiLv.setText(String.format("%.2f", systemLiLv * 0.8));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 0.8 + " postion="
							+ position);
					break;
				case 2:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 0.85));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 0.85 + " postion="
							+ position);
					break;
				case 3:
					etNianLiLv.setText(String.format("%.4f", systemLiLv * 0.9));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 0.9 + " postion="
							+ position);
					break;
				case 4:
					etNianLiLv.setText(String.valueOf(systemLiLv));
					Log.i(TAG, "贷款利率为：" + systemLiLv + " postion=" + position);
					break;
				case 5:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 1.1));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 1.1 + " postion="
							+ position);
					break;
				case 6:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 1.05));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 1.05 + " postion="
							+ position);
					break;
				case 7:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 1.15));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 1.15 + " postion="
							+ position);
					break;
				case 8:
					etNianLiLv.setText(String.format("%.2f", systemLiLv * 1.2));
					// Log.i(TAG, "贷款利率为："+systemLiLv*1.2+" postion="+position);
					break;
				}
			else
				// 公积金贷款类别，根据利率折扣计算利率
				switch (position) {
				case 0:
					etNianLiLv.setText(String.valueOf(systemLiLv));
					Log.i(TAG, "贷款利率为：" + systemLiLv + " postion=" + position);
					break;
				case 1:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 1.1));
					Log.i(TAG, "贷款利率为：" + systemLiLv * 1.1 + " postion="
							+ position);
					break;
				}
		}

		private void changeDaiKuanLeibie(int position) {
			// 商业贷款
			if (0 == position) {
				spLilvmoshi.setSelection(0, false);
				Log.i(TAG, "商业贷款利率：" + SYSTEMSYDKSYLILV);
				systemLiLv = SYSTEMSYDKSYLILV;

				// 建立数据源
				String[] mItems = getResources().getStringArray(
						R.array.spshydlilvzhekou);
				// 建立Adapter并且绑定数据源
				ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, mItems);
				_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spLilvzhekou.setAdapter(_Adapter);
				spLilvzhekou.setSelection(4, false);

			}
			// 公积金贷款
			else if (1 == position) {
				spLilvmoshi.setSelection(0, false);
				systemLiLv = SYSTEMGJJLILV;
				Log.i(TAG, "获得系统公积金利率为：" + systemLiLv);
				// 建立数据源
				String[] mItems = getResources().getStringArray(
						R.array.spgjjlilvzhekou);
				// 建立Adapter并且绑定数据源
				ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, mItems);
				_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spLilvzhekou.setAdapter(_Adapter);
				spLilvzhekou.setSelection(0, false);
			}

			etNianLiLv.setText(String.valueOf(systemLiLv));
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}

	// 通过handler传递信息，在界面显示提示信息。
	private Handler m_Handler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), "请输入年利率!",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "请输入贷款总额!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "请输入贷款总额及年利率!",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
}
