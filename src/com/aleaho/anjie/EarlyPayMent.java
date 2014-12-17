package com.aleaho.anjie;

import java.util.Date;

import com.aleaho.anjie.Data.*;

public class EarlyPayMent {

	/**
	 * 计划还款方式
	 */
	private JSFF jsff;
	/**
	 * 原贷款期限
	 */
	private int qiXian;
	/**
	 * 原贷款金额
	 */
	private float daiKuanJinE;
	/**
	 * 原贷款利率
	 */
	private float lilv;
	/**
	 * 首次还款时间
	 */
	private Date shouCiHuanKuan;
	/**
	 * 计划还款时间
	 */
	private Date jiHuaHuanKuan;
	/**
	 * 计划还款方式
	 */
	private JHHKFS jhhkfs;
	/**
	 * 部分还款方式
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
