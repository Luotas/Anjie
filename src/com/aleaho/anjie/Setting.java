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
	 * ��ҵ��������
	 */
	EditText etSYDKLL = null;
	/**
	 * �������������
	 */
	EditText etGJJDKLL = null;

	Button btnSetting = null;

	/**
	 * ��ҵ��������
	 */
	private String SYSTEMSYDKSYLILV;
	/**
	 * �������������
	 */
	private String SYSTEMGJJLILV;

	Message msg = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		Log.i(TAG,"ת��ϵͳ���ý��棡");
		getLiLv();
		initView();

	}

	private void getLiLv() {
		// TODO Auto-generated method stub
		
		Log.i(TAG,"���ϵͳ����");
		Properties props = Tools.getProperties(this.getApplicationContext());

		if (props != null) {

			SYSTEMSYDKSYLILV = props.get("sy").toString();
			SYSTEMGJJLILV = props.get("gjj").toString();
			Log.i(TAG,"�������ļ��л�ȡϵͳ����");

		} else {
			SYSTEMSYDKSYLILV = this.getResources().getString(R.string.sydklilv);
			SYSTEMGJJLILV = this.getResources().getString(R.string.gjjlilv);
			Log.i(TAG,"��ϵͳ�����л�ȡϵͳ����");
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		Log.i(TAG, "��ʼ������ؼ�");

		etSYDKLL = (EditText) findViewById(R.id.etSYDKLL);
		etGJJDKLL = (EditText) findViewById(R.id.etGJJDKLL);
		btnSetting = (Button) findViewById(R.id.btSetting);

		etSYDKLL.setText(String.valueOf(SYSTEMSYDKSYLILV));
		etGJJDKLL.setText(String.valueOf(SYSTEMGJJLILV));

		btnSetting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				msg = m_Handler.obtainMessage();

				HashMap<String, String> properties = new HashMap<String, String>();

				properties.put("sy", etSYDKLL.getText().toString());
				properties.put("gjj", etGJJDKLL.getText().toString());

				boolean result = Tools.setProperties(
						Setting.this.getApplicationContext(), properties);

				Log.i(TAG, "�������óɹ��ˡ���");
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
			case 1:// ����ǰ����ʱ�䳤�ڻ���ڴ������ޣ�ϵͳ��ʾ��
				Toast.makeText(Setting.this, "�������ǰ�������ڣ�", Toast.LENGTH_SHORT)
						.show();
				break;

			case 2:
				Toast.makeText(Setting.this, "��������ܶ��Լ���ǰ�����",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};

}
