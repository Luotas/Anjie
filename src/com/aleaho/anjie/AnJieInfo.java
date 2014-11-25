package com.aleaho.anjie;

import java.util.List;
import java.util.Map;

import com.aleaho.anjie.Data.*;

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
	private AnJieUtility anJieUtility = new AnJieUtility();

	private List<Map<String, String>> data = null;

	private List<Map<String, String>> hzData = null;
	private ListView lvAnJieInfo = null;
	private ListView lvhuizongxinxi = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anjie_info);

		Log.i(TAG, "AnJieInfo.Intent初始化中......");
		Intent intent = this.getIntent();

		daiKuanLilv = intent.getFloatExtra("DaiKuanLiLv", 0);
		daiKuanZongE = intent.getIntExtra("DaiKuanZongE", 0);
		daiKuanQiXian = intent.getIntExtra("DaiKuanQiXian", 0);
		daiKuanLeiBei = (DKLB) intent.getSerializableExtra("DaiKuanLeiBei");
		jiSuanFangFa = (JSFF) intent.getSerializableExtra("JiSuanFangFa");

		// 设置标题。
		this.getActionBar().setTitle(
				"你选择的是" + jiSuanFangFa + daiKuanLeiBei + "!");

		lvAnJieInfo = (ListView) findViewById(R.id.lvAnJieInfo);
		lvhuizongxinxi = (ListView) findViewById(R.id.lvhuizongxinxi);

		if (daiKuanLilv != 0 || daiKuanZongE != 0 || daiKuanQiXian != 0) {

			// 等额本息
			if (jiSuanFangFa == JSFF.等额本息) {
				data = anJieUtility.DengEBenXi(daiKuanZongE, daiKuanLilv,
						daiKuanQiXian);
			}
			// 等额本金
			else if (jiSuanFangFa == JSFF.等额本金) {
				data = anJieUtility.DengEBenJin(daiKuanZongE, daiKuanLilv,
						daiKuanQiXian);
			}
			hzData = anJieUtility.getData(jiSuanFangFa);

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

			lvhuizongxinxi.setAdapter(adapter1);
			lvAnJieInfo.setAdapter(adapter);
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
