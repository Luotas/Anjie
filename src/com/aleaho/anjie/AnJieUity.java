package com.aleaho.anjie;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.aleaho.anjie.MainActivity.DKLB;
import com.aleaho.anjie.MainActivity.JSFF;

public class AnJieUity {

	final String TAG = "ANJIE";
	private int daiKuanZongE = 0;
	private int daiKuanQiXian = 0;
	private double yueHuanKuanE = 0;
	private double huanKuanZongE = 0;
	private double daiKuanLiXi = 0;

	/**
	 * 贷款本息算法
	 * @param daiKuanZongE		贷款金额
	 * @param yueLiLv			月利率
	 * @param daiKuanQiXian		贷款期数
	 * @return					每一期还款列表
	 * ×××××××××××××××××××××××××××××××××××××××××××××××
	 * 期数		月还款额		利息		本金		剩余本金
	 * 
	 * ×××××××××××××××××××××××××××××××××××××××××××××××
	 */
	public List<Map<String, String>> DengEBenXi(int daiKuanZongE,
			double yueLiLv, int daiKuanQiXian) {

		setDaiKuanZongE(daiKuanZongE);
		setDaiKuanQiXian(daiKuanQiXian);

		DecimalFormat df = new DecimalFormat("#.00");
		double zongLiXi = 0;

		double yueHuanKuan = daiKuanZongE * yueLiLv
				* Math.pow(1 + yueLiLv, daiKuanQiXian)
				/ (Math.pow(1 + yueLiLv, daiKuanQiXian) - 1);

		setYueHuanKuanE(yueHuanKuan);
		setHuanKuanZongE(yueHuanKuan * daiKuanQiXian);

		List<Map<String, String>> huanKuanShuJu = new ArrayList<Map<String, String>>();

		Map<String, String> shuJu = null;
		// 已还本金
		double sumMoney = 0;
		// 剩余本金
		double shengYuBenJi = 0;
		// 当期还利息
		double liXi = 0;
		// 当期还本金
		double benJin = 0;

		// 第一期利息

		for (int i = 1; i <= daiKuanQiXian; i++) {

			shuJu = new HashMap<String, String>();
			shuJu.put("QiShu", "第" + i + "期");
			if (i == 1)
				liXi = daiKuanZongE * yueLiLv;
			else
				liXi = shengYuBenJi * yueLiLv;
			zongLiXi += liXi;
			benJin = yueHuanKuan - liXi;
			sumMoney += benJin;
			shengYuBenJi = daiKuanZongE - sumMoney;
			shuJu.put("YueHuanKuanE", df.format(yueHuanKuan));
			shuJu.put("LiXi", df.format(liXi));
			shuJu.put("BenJin", df.format(benJin));
			shuJu.put("ShengYuBenJin", df.format(shengYuBenJi));
			huanKuanShuJu.add(shuJu);
		}
		setDaiKuanLiXi(zongLiXi);

		return huanKuanShuJu;
	}

	/**
	 * 贷款本金算法
	 * @param daiKuanZongE		贷款金额
	 * @param yueLiLv			月利率
	 * @param daiKuanQiXian		贷款期数
	 * @return					每一期还款列表
	 * ×××××××××××××××××××××××××××××××××××××××××××××××
	 * 期数		月还款额		利息		本金		剩余本金
	 * 
	 * ×××××××××××××××××××××××××××××××××××××××××××××××
	 */
	public List<Map<String, String>> DengEBenJin(int daiKuanZongE,
			double yueLiLv, int daiKuanQiXian) {

		setDaiKuanZongE(daiKuanZongE);
		setDaiKuanQiXian(daiKuanQiXian);

		DecimalFormat df = new DecimalFormat("#.00");
		double zongLiXi = 0;

		double yueHuanBenJin = (double) daiKuanZongE / daiKuanQiXian;
		double yueHuanKuanE = 0;
		// setHuanKuanZongE(yueHuanKuan * daiKuanQiXian);

		List<Map<String, String>> huanKuanShuJu = new ArrayList<Map<String, String>>();

		Map<String, String> shuJu = null;
		// 已还本金
		double sumMoney = 0;
		// 剩余本金
		double shengYuBenJi = 0;
		// 当期还利息
		double liXi = 0;
		shengYuBenJi = daiKuanZongE;
		for (int i = 1; i <= daiKuanQiXian; i++) {

			shuJu = new HashMap<String, String>();
			shuJu.put("QiShu", "第" + i + "期");
			if (i == 1)
				liXi = daiKuanZongE * yueLiLv;
			else
				liXi = shengYuBenJi * yueLiLv;
			shengYuBenJi -= yueHuanBenJin;
			yueHuanKuanE = liXi + yueHuanBenJin;
			zongLiXi += liXi;

			shuJu.put("YueHuanKuanE", df.format(yueHuanKuanE));
			shuJu.put("LiXi", df.format(liXi));
			shuJu.put("BenJin", df.format(yueHuanBenJin));
			shuJu.put("ShengYuBenJin", df.format(shengYuBenJi));
			huanKuanShuJu.add(shuJu);
		}

		setDaiKuanLiXi(zongLiXi);
		setHuanKuanZongE(daiKuanZongE + zongLiXi);

		return huanKuanShuJu;

	}

	/**
	 * 等额本息提前还款中还款额不变缩短还款期限方式，计算剩余还款期数。
	 * @param shengyudaikuan	剩余贷款本金
	 * @param daikuanzonge		原贷款总金额
	 * @param yuelilv			月利率
	 * @param daikuanqishu		原贷款总期数
	 * @return					提前还款后，剩余贷款期数
	 */
	public int getShengYueQiShu(float shengyudaikuan, int daikuanzonge,
			float yuelilv, int daikuanqishu) {

		double yuehuankuan = getYueHuanKuan(daikuanzonge, yuelilv, daikuanqishu);
		
		return  (int)Math.ceil(Math.log(yuehuankuan)/Math.log(yuehuankuan-shengyudaikuan*yuelilv));
		
	}

	/**
	 *获得贷款余额还款额
	 * @param daikuanzonge		贷款金额
	 * @param yuelilv			月利率
	 * @param qishu				贷款期数(月数)
	 * @return
	 */
	public double getYueHuanKuan(int daikuanzonge, float yuelilv, int qishu) {
		return daikuanzonge * yuelilv * Math.pow(1 + yuelilv, qishu)
				/ (Math.pow(1 + yuelilv, qishu) - 1);
	}

	// 贷款还款信息汇总

	/**
	 * 
	 * @return 贷款信息汇总，贷款金额，还款利息，总还款金额，还款期数，月还款额
	 */
	public List<Map<String, String>> getData(JSFF jiSuanFangFa) {
		// TODO Auto-generated method stub

		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, String>> huanKuanHuiZong = new ArrayList<Map<String, String>>();

		Map<String, String> shuJu = new HashMap<String, String>();
		Log.i(TAG, "-----------------------------");
		Log.i(TAG, "获得贷款汇总信息：");
		shuJu.put("MingCheng", "贷款总金额:");
		shuJu.put("NeiRong", String.valueOf(getDaiKuanZongE()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "还款总金额:");
		shuJu.put("NeiRong", df.format(getHuanKuanZongE()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "利息总金额:");
		shuJu.put("NeiRong", df.format(getDaiKuanLiXi()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "贷款期限:");
		shuJu.put("NeiRong", String.valueOf(getDaiKuanQiXian()));
		huanKuanHuiZong.add(shuJu);

		if (jiSuanFangFa == JSFF.DEBX) {
			shuJu = new HashMap<String, String>();
			shuJu.put("MingCheng", "月还款额:");
			shuJu.put("NeiRong", df.format(getYueHuanKuanE()));
			huanKuanHuiZong.add(shuJu);
		}
		Log.i(TAG, "-----------------------------");
		return huanKuanHuiZong;
	}

	public double getYueHuanKuanE() {
		return yueHuanKuanE;
	}

	public void setYueHuanKuanE(double yueHuanKuanE) {
		this.yueHuanKuanE = yueHuanKuanE;
	}

	public double getHuanKuanZongE() {
		return huanKuanZongE;
	}

	public void setHuanKuanZongE(double huanKuanZongE) {
		this.huanKuanZongE = huanKuanZongE;
	}

	public int getDaiKuanZongE() {
		return daiKuanZongE;
	}

	public void setDaiKuanZongE(int daiKuanZongE) {
		this.daiKuanZongE = daiKuanZongE;
	}

	public int getDaiKuanQiXian() {
		return daiKuanQiXian;
	}

	public void setDaiKuanQiXian(int daiKuanQiXian) {
		this.daiKuanQiXian = daiKuanQiXian;
	}

	public double getDaiKuanLiXi() {
		return daiKuanLiXi;
	}

	public void setDaiKuanLiXi(double daiKuanLiXi) {
		this.daiKuanLiXi = daiKuanLiXi;
	}

}