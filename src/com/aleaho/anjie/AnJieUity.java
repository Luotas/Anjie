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
	 * ���Ϣ�㷨
	 * @param daiKuanZongE		������
	 * @param yueLiLv			������
	 * @param daiKuanQiXian		��������
	 * @return					ÿһ�ڻ����б�
	 * ����������������������������������������������������������������������������������������������
	 * ����		�»����		��Ϣ		����		ʣ�౾��
	 * 
	 * ����������������������������������������������������������������������������������������������
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
		// �ѻ�����
		double sumMoney = 0;
		// ʣ�౾��
		double shengYuBenJi = 0;
		// ���ڻ���Ϣ
		double liXi = 0;
		// ���ڻ�����
		double benJin = 0;

		// ��һ����Ϣ

		for (int i = 1; i <= daiKuanQiXian; i++) {

			shuJu = new HashMap<String, String>();
			shuJu.put("QiShu", "��" + i + "��");
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
	 * ������㷨
	 * @param daiKuanZongE		������
	 * @param yueLiLv			������
	 * @param daiKuanQiXian		��������
	 * @return					ÿһ�ڻ����б�
	 * ����������������������������������������������������������������������������������������������
	 * ����		�»����		��Ϣ		����		ʣ�౾��
	 * 
	 * ����������������������������������������������������������������������������������������������
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
		// �ѻ�����
		double sumMoney = 0;
		// ʣ�౾��
		double shengYuBenJi = 0;
		// ���ڻ���Ϣ
		double liXi = 0;
		shengYuBenJi = daiKuanZongE;
		for (int i = 1; i <= daiKuanQiXian; i++) {

			shuJu = new HashMap<String, String>();
			shuJu.put("QiShu", "��" + i + "��");
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
	 * �ȶϢ��ǰ�����л��������̻������޷�ʽ������ʣ�໹��������
	 * @param shengyudaikuan	ʣ������
	 * @param daikuanzonge		ԭ�����ܽ��
	 * @param yuelilv			������
	 * @param daikuanqishu		ԭ����������
	 * @return					��ǰ�����ʣ���������
	 */
	public int getShengYueQiShu(float shengyudaikuan, int daikuanzonge,
			float yuelilv, int daikuanqishu) {

		double yuehuankuan = getYueHuanKuan(daikuanzonge, yuelilv, daikuanqishu);
		
		return  (int)Math.ceil(Math.log(yuehuankuan)/Math.log(yuehuankuan-shengyudaikuan*yuelilv));
		
	}

	/**
	 *��ô��������
	 * @param daikuanzonge		������
	 * @param yuelilv			������
	 * @param qishu				��������(����)
	 * @return
	 */
	public double getYueHuanKuan(int daikuanzonge, float yuelilv, int qishu) {
		return daikuanzonge * yuelilv * Math.pow(1 + yuelilv, qishu)
				/ (Math.pow(1 + yuelilv, qishu) - 1);
	}

	// �������Ϣ����

	/**
	 * 
	 * @return ������Ϣ���ܣ������������Ϣ���ܻ���������������»����
	 */
	public List<Map<String, String>> getData(JSFF jiSuanFangFa) {
		// TODO Auto-generated method stub

		DecimalFormat df = new DecimalFormat("#.00");
		List<Map<String, String>> huanKuanHuiZong = new ArrayList<Map<String, String>>();

		Map<String, String> shuJu = new HashMap<String, String>();
		Log.i(TAG, "-----------------------------");
		Log.i(TAG, "��ô��������Ϣ��");
		shuJu.put("MingCheng", "�����ܽ��:");
		shuJu.put("NeiRong", String.valueOf(getDaiKuanZongE()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "�����ܽ��:");
		shuJu.put("NeiRong", df.format(getHuanKuanZongE()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "��Ϣ�ܽ��:");
		shuJu.put("NeiRong", df.format(getDaiKuanLiXi()));
		huanKuanHuiZong.add(shuJu);
		shuJu = new HashMap<String, String>();

		shuJu.put("MingCheng", "��������:");
		shuJu.put("NeiRong", String.valueOf(getDaiKuanQiXian()));
		huanKuanHuiZong.add(shuJu);

		if (jiSuanFangFa == JSFF.DEBX) {
			shuJu = new HashMap<String, String>();
			shuJu.put("MingCheng", "�»����:");
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