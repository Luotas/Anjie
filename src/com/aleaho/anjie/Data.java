package com.aleaho.anjie;

public class Data {

	/**
	 * 贷款类别
	 * 
	 * @author lenovo SYDK:商业贷款 GJJDK：公积金贷款
	 */
	public enum DKLB {
		/**
		 * 商业贷款
		 */
		商业贷款,
		/**
		 * 公积金贷款
		 */
		公积金贷款
	}

	/**
	 * 计算方式
	 * 
	 * @author lenovo
	 * @DEBX:等额本息
	 * @DEBJ:等额本金
	 */
	public enum JSFF {

		/**
		 * 等额本息
		 */
		等额本息,

		/**
		 * 等额本金
		 */
		等额本金
	}
	
	/**
	 * 计划还款访方式
	 * 一次提前还款，部分提前还款
	 * @author lenovo
	 *
	 */
	public enum JHHKFS{
		
		/**
		 * 一次提前还款
		 */
		YCHW,
		/**
		 * 部分提前还款
		 */
		BFHK
		
	}
	
	/**
	 * 部分还款方式：还款期限不变，缩小还款额;还款额不变，缩短还款期限。
	 * @author lenovo
	 *
	 */
	public enum BFHKFS{
		/**
		 * 还款期限不变，缩小还款额;
		 */
		QXBB,
		
		/**
		 * 还款额不变，缩短还款期限
		 */
		HKEBB
		
	}
	

}
