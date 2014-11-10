package com.aleaho.anjie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aleaho.anjie.MainActivity.DKLB;
import com.aleaho.anjie.MainActivity.JSFF;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AnJieInfo extends Activity {

	final String TAG = "ANJIE";
	private DKLB daiKuanLeiBei;
	private JSFF jiSuanFangFa;
	private float daiKuanLilv = 0;
	private int daiKuanZongE = 0;
	private int daiKuanQiXian = 0;
	private double yueHuanKuanE = 0;
	private double huanKuanZongE = 0;

	private double daiKuanLiXi = 0;

	private AnJieUity anJieUity = new AnJieUity();

	private List<Map<String, String>> data = null;

	private List<Map<String, String>> hzData = null;
	private ListView lvAnJieInfo = null;
	private ListView lvhuizongxinxi = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anjie_info);

		Log.i(TAG, "Intent��ʼ����......");
		Intent intent = this.getIntent();

		daiKuanLilv = intent.getFloatExtra("DaiKuanLiLv", 0);
		daiKuanZongE = intent.getIntExtra("DaiKuanZongE", 0);
		daiKuanQiXian = intent.getIntExtra("DaiKuanQiXian", 0);
		daiKuanLeiBei = (DKLB) intent.getSerializableExtra("DaiKuanLeiBei");
		jiSuanFangFa = (JSFF) intent.getSerializableExtra("JiSuanFangFa");

		lvAnJieInfo = (ListView) findViewById(R.id.lvAnJieInfo);
		lvhuizongxinxi = (ListView) findViewById(R.id.lvhuizongxinxi);

		Log.i(TAG, "��ʼ���㻹����ϸ------------");
		if (daiKuanLilv != 0 || daiKuanZongE != 0 || daiKuanQiXian != 0) {

			// �ȶϢ
			if (jiSuanFangFa == JSFF.DEBX)
				data = anJieUity.DengEBenXi(daiKuanZongE, daiKuanLilv,
						daiKuanQiXian);
			// �ȶ��
			else if (jiSuanFangFa == JSFF.DEBJ)
				data = anJieUity.DengEBenJin(daiKuanZongE, daiKuanLilv,
						daiKuanQiXian);

			Log.i(TAG, "������ϸ�������------------");

			hzData = anJieUity.getData(jiSuanFangFa);

			
			Log.i(TAG, "hzData������Ϊ��"+hzData.size());
			
			SimpleAdapter adapter = new SimpleAdapter(this, data,
					R.layout.lvinfoitem,
					new String[] { "QiShu", "YueHuanKuanE", "LiXi", "BenJin",
							"ShengYuBenJin" }, new int[] { R.id.itemQiShu,
							R.id.itemyueHuanKuanE, R.id.itemLiXi,
							R.id.itemBenJin, R.id.itemSuoQianBenJin });

			SimpleAdapter adapter1 = new SimpleAdapter(this, hzData,
					R.layout.huzongxinxiitem, new String[] { "MingCheng",
							"NeiRong" }, new int[] { R.id.mingcheng,
							R.id.neirong });

			Log.i(TAG, "�����Ѳ�����ɣ��ȴ���ʾ������");

			try {
				lvhuizongxinxi.setAdapter(adapter1);
				lvAnJieInfo.setAdapter(adapter);
			} catch (Exception e) {
				Log.e(TAG, "��ʾ������:" + e.toString());
			}
			Log.i(TAG, "����������ɣ�����");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
