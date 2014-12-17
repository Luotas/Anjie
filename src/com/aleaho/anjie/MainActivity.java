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
	 * �������
	 */
	Spinner spDaikuanleibie = null;
	/**
	 * ������㷽��
	 */
	Spinner spJisuanfangfa = null;
	/**
	 * ��������
	 */
	Spinner spDaikuanqixian = null;
	/**
	 * ����ģʽ
	 */
	Spinner spLilvmoshi = null;
	/**
	 * �����ۿ�
	 */
	Spinner spLilvzhekou = null;

	/**
	 * ������
	 */
	EditText etNianLiLv = null;
	/**
	 * �����ܶ�
	 */
	EditText etDakuanzonge = null;

	Button btJS = null;

	private DKLB daiKuanLeiBei;
	private JSFF jiSuanFangFa;

	private float daiKuanLilv = 0;

	// ϵͳ��ʼ������
	/**
	 * ��ҵ��������
	 */
	private float SYSTEMSYDKSYLILV = 0;
	/**
	 * �������������
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
	 * ��ʼ������ؼ�
	 */
	private void initView() {
		// TODO Auto-generated method stub

		Log.i(TAG, "��ʼ������ؼ�");

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

		// �趨ϵͳ����
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

		// ����Spinner��ʼ��ѡ����Ŀ��������OnItemSelectedListener�¼���
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

					Log.i(TAG, "��õ�ǰ�������ʣ�������");
					daiKuanLilv = Float.parseFloat(etNianLiLv.getText()
							.toString()) / 100 / 12;

					Log.i(TAG, "��ǰ��������:" + daiKuanLilv);

				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "MainActivity����������δ����!!\n" + e.toString());
					flag = flag + 1;
				}

				try {

					Log.i(TAG, "��õ�ǰ�����ܶ������");
					daiKuanZongE = (int) (Float.parseFloat(etDakuanzonge
							.getText().toString()) * 10000);
					Log.i(TAG, "��õ�ǰ�����ܶ�:" + daiKuanZongE);

				} catch (Exception e) {
					// TODO: handle exception

					Log.e(TAG, "MainActivity��������ܶ�δ����!!\n" + e.toString());
					flag = flag + 2;
					Log.e(TAG, "MainActivity����flag=" + flag);
				}

				if (0 == flag) {
					Log.i(TAG, "׼����ת����ʾ������");
					Intent intent = new Intent(MainActivity.this,
							AnJieInfo.class);
					intent.putExtra("DaiKuanLiLv", daiKuanLilv);
					intent.putExtra("DaiKuanZongE", daiKuanZongE);
					intent.putExtra("DaiKuanLeiBei", daiKuanLeiBei);
					intent.putExtra("JiSuanFangFa", jiSuanFangFa);
					intent.putExtra("DaiKuanQiXian", daiKuanQiXian);
					Log.i(TAG, "MA�������ޣ�" + daiKuanQiXian);
					startActivity(intent);
				} else {
					m_Message.what = flag;
					m_Handler.sendMessage(m_Message);
				}
			}
		});
	}

	// ��ʼ������Spinner
	private void initSpinner() {
		// TODO Auto-generated method stub

		// ���������������Դ
		String[] daikuanleibieItems = getResources().getStringArray(
				R.array.spdaikuanleibie);
		initSpinner(daikuanleibieItems, spDaikuanleibie);

		// �������㷽������Դ
		String[] jisuanfangfaItems = getResources().getStringArray(
				R.array.spjisuanfangfa);
		initSpinner(jisuanfangfaItems, spJisuanfangfa);

		// ����������������Դ
		String[] huankuanqixianItems = getResources().getStringArray(
				R.array.sphuankuanqixian);
		initSpinner(huankuanqixianItems, spDaikuanqixian);

		// ��������ģʽ����Դ
		String[] lilvmoshiItems = getResources().getStringArray(
				R.array.splilvmoshi);
		initSpinner(lilvmoshiItems, spLilvmoshi);

		// ������ҵ���������ۿ�����Դ
		String[] shydlilvzhekouItems = getResources().getStringArray(
				R.array.spshydlilvzhekou);
		initSpinner(shydlilvzhekouItems, spLilvzhekou);
	}

	/**
	 * ��ʼ��Spinner,��initSpinner����
	 * 
	 * @param items
	 *            Spinner����Դ
	 * @param spinner
	 *            ��Ҫ��ʼ����Spinner��ʵ������
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

		// ��ǰ�����ۿ�
		int _positon = 4;

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {

			switch (parent.getId()) {
			// ���ݴ���������ô����ۿ��Լ�������Ϣ��Ϣ
			case R.id.SpDaikuanLeibie:
				switch (position) {
				case 0:
					daiKuanLeiBei = DKLB.��ҵ����;
					changeDaiKuanLeibie(position);
					break;
				case 1:
					daiKuanLeiBei = DKLB.���������;
					changeDaiKuanLeibie(position);
					break;
				}

				break;
			case R.id.SpJiSuanFangFa:
				switch (position) {
				case 0:
					jiSuanFangFa = JSFF.�ȶϢ;
					Log.i(TAG, "���㷽��Ϊ���ȶϢ" + " postion=" + position);
					break;
				default:
					jiSuanFangFa = JSFF.�ȶ��;
					Log.i(TAG, "���㷽��Ϊ���ȶ��" + " postion=" + position);
					break;
				}
				break;

			case R.id.SpDaiKuanQiXian:

				daiKuanQiXian = (position + 1) * 12;
				Log.i(TAG, "��������Ϊ��" + daiKuanQiXian + " postion=" + position);
				break;

			case R.id.SpLiLvMoShi:
				switch (position) {
				// ϵͳ����
				case 0:
					etNianLiLv.setEnabled(false);
					spLilvzhekou.setEnabled(true);
					etNianLiLv.clearFocus();
					setLiLvZheKou(_positon, daiKuanLeiBei);
					break;

				// �Զ�������
				default:
					etNianLiLv.setEnabled(true);
					spLilvzhekou.setEnabled(false);
					etNianLiLv.requestFocus();
					_positon = spLilvzhekou.getSelectedItemPosition();
					break;
				}
				break;

			/**
			 * <item >7������</item> <item >8������</item> <item >85������</item> <item
			 * >9������</item> <item >��׼����(1.0)</item> <item >�ڶ��׷�(1.1)</item>
			 * <item >�����ϸ�(1.05)</item> <item >�����ϸ�(1.15)</item> <item
			 * >�����ϸ�(1.2)</item>
			 */
			case R.id.spLiLvZheKou:
				setLiLvZheKou(position, daiKuanLeiBei);
				break;
			}
		}

		// ���������ۿ��Լ����������������
		private void setLiLvZheKou(int position, DKLB daiKuanLeibie) {

			// ��ҵ������𣬸��������ۿۼ�������
			if (daiKuanLeibie == DKLB.��ҵ����)
				switch (position) {
				case 0:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 0.7));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 0.7 + " postion="
							+ position);
					break;
				case 1:
					etNianLiLv.setText(String.format("%.2f", systemLiLv * 0.8));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 0.8 + " postion="
							+ position);
					break;
				case 2:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 0.85));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 0.85 + " postion="
							+ position);
					break;
				case 3:
					etNianLiLv.setText(String.format("%.4f", systemLiLv * 0.9));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 0.9 + " postion="
							+ position);
					break;
				case 4:
					etNianLiLv.setText(String.valueOf(systemLiLv));
					Log.i(TAG, "��������Ϊ��" + systemLiLv + " postion=" + position);
					break;
				case 5:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 1.1));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 1.1 + " postion="
							+ position);
					break;
				case 6:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 1.05));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 1.05 + " postion="
							+ position);
					break;
				case 7:
					etNianLiLv
							.setText(String.format("%.4f", systemLiLv * 1.15));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 1.15 + " postion="
							+ position);
					break;
				case 8:
					etNianLiLv.setText(String.format("%.2f", systemLiLv * 1.2));
					// Log.i(TAG, "��������Ϊ��"+systemLiLv*1.2+" postion="+position);
					break;
				}
			else
				// �����������𣬸��������ۿۼ�������
				switch (position) {
				case 0:
					etNianLiLv.setText(String.valueOf(systemLiLv));
					Log.i(TAG, "��������Ϊ��" + systemLiLv + " postion=" + position);
					break;
				case 1:
					etNianLiLv.setText(String.format("%.3f", systemLiLv * 1.1));
					Log.i(TAG, "��������Ϊ��" + systemLiLv * 1.1 + " postion="
							+ position);
					break;
				}
		}

		private void changeDaiKuanLeibie(int position) {
			// ��ҵ����
			if (0 == position) {
				spLilvmoshi.setSelection(0, false);
				Log.i(TAG, "��ҵ�������ʣ�" + SYSTEMSYDKSYLILV);
				systemLiLv = SYSTEMSYDKSYLILV;

				// ��������Դ
				String[] mItems = getResources().getStringArray(
						R.array.spshydlilvzhekou);
				// ����Adapter���Ұ�����Դ
				ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_spinner_item, mItems);
				_Adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
				spLilvzhekou.setAdapter(_Adapter);
				spLilvzhekou.setSelection(4, false);

			}
			// ���������
			else if (1 == position) {
				spLilvmoshi.setSelection(0, false);
				systemLiLv = SYSTEMGJJLILV;
				Log.i(TAG, "���ϵͳ����������Ϊ��" + systemLiLv);
				// ��������Դ
				String[] mItems = getResources().getStringArray(
						R.array.spgjjlilvzhekou);
				// ����Adapter���Ұ�����Դ
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

	// ͨ��handler������Ϣ���ڽ�����ʾ��ʾ��Ϣ��
	private Handler m_Handler = new Handler() {

		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), "������������!",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "����������ܶ�!",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "����������ܶ������!",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}

	};
}
