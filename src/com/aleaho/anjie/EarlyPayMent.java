package com.aleaho.anjie;

import java.util.Date;

import com.aleaho.anjie.Data.*;

public class EarlyPayMent {

	/**
	 * �ƻ����ʽ
	 */
	private JSFF jsff;
	/**
	 * ԭ��������
	 */
	private int qiXian;
	/**
	 * ԭ������
	 */
	private float daiKuanJinE;
	/**
	 * ԭ��������
	 */
	private float lilv;
	/**
	 * �״λ���ʱ��
	 */
	private Date shouCiHuanKuan;
	/**
	 * �ƻ�����ʱ��
	 */
	private Date jiHuaHuanKuan;
	/**
	 * �ƻ����ʽ
	 */
	private JHHKFS jhhkfs;
	/**
	 * ���ֻ��ʽ
	 */
	private BFHKFS bfhkfs;

	public JSFF getJsff() {
		return jsff;
	}

	public void setJsff(JSFF jsff) {
		this.jsff = jsff;
	}

	public int getQiXian() {
		return qiXian;
	}

	public void setQiXian(int qiXian) {
		this.qiXian = qiXian;
	}

	public float getDaiKuanJinE() {
		return daiKuanJinE;
	}

	public void setDaiKuanJinE(float daiKuanJinE) {
		this.daiKuanJinE = daiKuanJinE;
	}

	public float getLilv() {
		return lilv;
	}

	public void setLilv(float lilv) {
		this.lilv = lilv;
	}

	public Date getShouCiHuanKuan() {
		return shouCiHuanKuan;
	}

	public void setShouCiHuanKuan(Date shouCiHuanKuan) {
		this.shouCiHuanKuan = shouCiHuanKuan;
	}

	public Date getJiHuaHuanKuan() {
		return jiHuaHuanKuan;
	}

	public void setJiHuaHuanKuan(Date jiHuaHuanKuan) {
		this.jiHuaHuanKuan = jiHuaHuanKuan;
	}

	public JHHKFS getJhhkfs() {
		return jhhkfs;
	}

	public void setJhhkfs(JHHKFS jhhkfs) {
		this.jhhkfs = jhhkfs;
	}

	public BFHKFS getBfhkfs() {
		return bfhkfs;
	}

	public void setBfhkfs(BFHKFS bfhkfs) {
		this.bfhkfs = bfhkfs;
	}

}
