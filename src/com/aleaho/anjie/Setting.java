package com.aleaho.anjie;

import java.util.HashMap;
import java.util.Properties;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Setting extends Activity {

	private static final String TAG = "ANJIE";

	/**
	 * 商业贷款利率
	 */
	EditText etSYDKLL = null;
	/**
	 * 公积金贷款利率
	 */
	EditText etGJJDKLL = null;

	Button btnSetting = null;

	/**
	 * 商业贷款利率
	 */
	private String SYSTEMSYDKSYLILV;
	/**
	 * 公积金贷款利率
	 */
	private String SYSTEMGJJLILV;

	Message msg = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		Log.i(TAG, "转到系统设置界面！");
		getLiLv();
		initView();

	}

	private void getLiLv() {
		// TODO Auto-generated method stub

		Log.i(TAG, "获得系统利率");
		Properties props = Tools.getProperties(this.getApplicationContext(),
				Data.CONFIGFILE);

		if (props != null) {
			SYSTEMSYDKSYLILV = props.get("sy").toString();
			SYSTEMGJJLILV = props.get("gjj").toString();
			Log.i(TAG, "从配置文件中获取系统利率");

		} else {
			SYSTEMSYDKSYLILV = this.getResources().getString(R.string.sydklilv);
			SYSTEMGJJLILV = this.getResources().getString(R.string.gjjlilv);
			Log.i(TAG, "从系统设置中获取系统利率");
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		Log.i(TAG, "初始化界面控件");

		etSYDKLL = (EditText) findViewById(R.id.etSYDKLL);
		etGJJDKLL = (EditText) findViewById(R.id.etGJJDKLL);
		btnSetting = (Button) findViewById(R.id.btSetting);

		etSYDKLL.setText(String.valueOf(SYSTEMSYDKSYLILV));
		etGJJDKLL.setText(String.valueOf(SYSTEMGJJLILV));

		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				int flag = 0;
				String sydkll = etSYDKLL.getText().toString();
				String gjjdkll = etGJJDKLL.getText().toString();

				// 判断利率输入是否合法
				if (sydkll == null || sydkll.trim().equals(".")
						|| sydkll.trim().equals("")) {
					etSYDKLL.setError("输入利率！");
					flag = 1;
				}
				if (gjjdkll == null || gjjdkll.trim().equals(".")
						|| gjjdkll.trim().equals("")) {
					etGJJDKLL.setError("输入利率！");
					flag = 1;
				}

				// 利率输入合法，进行保存
				if (0 == flag) {

					msg = m_Handler.obtainMessage();

					HashMap<String, String> properties = new HashMap<String, String>();

					properties.put("sy", etSYDKLL.getText().toString());
					properties.put("gjj", etGJJDKLL.getText().toString());

					boolean result = Tools.setProperties(
							Setting.this.getApplicationContext(),
							Data.CONFIGFILE, properties);

					if (result) {
						Log.i(TAG, "利率设置成功了。。");

						msg.what = 1;
						m_Handler.sendMessage(msg);

					} else {

						Log.i(TAG, "利率设置失败。。");
						msg.what = 0;
						m_Handler.sendMessage(msg);
					}

				}
			}
		});

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
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		if (id == R.id.tiqianhuangkuan) {

			Intent intent = new Intent(this, TiQianHuanKuan.class);
			startActivity(intent);
		}

		if (id == R.id.setting) {

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private Handler m_Handler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// 当提前还款时间长于或短于贷款期限，系统提示。
				Toast.makeText(Setting.this, "利率设置失败！", Toast.LENGTH_SHORT)
						.show();
				break;

			case 1:
				Toast.makeText(Setting.this, "利率设置成功了！", Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}

	};

}
