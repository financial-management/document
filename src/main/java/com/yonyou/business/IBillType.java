package com.yonyou.business;

/**
 * 单据类型定义
 * 
 * @author lixi
 * 
 */
public interface IBillType {

	/** 对外付款单据类型 */
	public static final String PAY_BILL = "DYFK";
	
	/** 总部代付单据类型 */
	public static final String GROUP_PAY_BILL = "JZFK";
	
	/** 员工支付单据类型 */
	public static final String SALARY_PAY_BILL = "YGZF";
	
	/** 对外付款单据类型 */
	public static final String COUNTER_PAY_BILL = "CPAY";

	/** 支付确认单类型 */
	public static final String PAY_CONFIRM = "ZFQR";
	
	/** 资金支付确认单类型  add by zf 2015/06/03*/
	public static final String PAY_ZJCONFIRM = "ZJZFQR";
	
	/** 总部代付确认单类型 */
	public static final String GROUP_PAY_CONFIRM = "JZQR";
	
	/** 开户申请书类型 
	 * @deprecated
	 * @date 2010-10-13
	 * @author wls
	 * */
	public static final String OPEN_ACCOUNT = "KHSQS";
	
	/** 账户冻结单类型 
	 * @deprecated
	 * @date 2010-10-13
	 * @author wls
	 * */
	public static final String ACCOUNT_FROZEN = "ZHDJ";
	
	/** 账户解冻单类型 
	 * @deprecated
	 * @date 2010-10-13
	 * @author wls
	 * */
	public static final String ACCOUNT_UNFROZEN = "ZHJD";
	
	/** 账户销户单类型 
	 * @deprecated
	 * @date 2010-10-13
	 * @author wls
	 * */
	public static final String ACCOUNT_DESTROY = "ZHXH";
	
	/**	开户申请书-实体帐户*/
	public static final String OPEN_ACTUAL_ACCOUNT = "STKHSQ";

	/**	开户回填-实体帐户*/
	public static final String ACTUAL_ACCOUNT_BACKFILL = "STKHHT";
	
	/**	开户申请书-虚拟帐户*/
	public static final String OPEN_VIRTUAL_ACCOUNT = "XNKHSQ";
	
	/** 虚拟账户开户告知*/
	public static final String VA_OPEN_INFORM = "XNKHGZ"; 

	/** 虚拟账户变更告知*/
	public static final String VA_CHANGE_INFORM = "XNBGGZ";
	
	/** 虚拟账户销户告知*/
	public static final String VA_DESTROY_INFORM = "XNXHGZ";
	
	/**	帐户冻结单-实体帐户*/
	public static final String ACTUAL_ACCOUNT_FROZEN = "STZHDJ";
	
	/**	帐户冻结单-虚拟帐户*/
	public static final String VIRTUAL_ACCOUNT_FROZEN = "XNZHDJ";
	
	/**	帐户解冻单-实体帐户*/
	public static final String ACTUAL_ACCOUNT_UNFROZEN = "STZHJD";
	
	/**	帐户解冻单-虚拟帐户*/
	public static final String VIRTUAL_ACCOUNT_UNFROZEN = "XNZHJD";
	
	/**	帐户销户单-实体帐户*/
	public static final String ACTUAL_ACCOUNT_CLOSED = "STZHXH";
	
	/**	帐户销户单-虚拟帐户*/
	public static final String VIRTUAL_ACCOUNT_CLOSED = "XNZHXH";
	
	/**	帐户变更单-实体帐户*/
	public static final String ACTUAL_ACCOUNT_CHANGE = "STZHBG";
	
	/**	帐户变更单-虚拟帐户*/
	public static final String VIRTUAL_ACCOUNT_CHANGE = "XNZHBG";
	
	/**	资金调度-资金上收*/
	public static final String FTSFUND_GATHER_UP = "SHZIDD";
	
	/**	资金调度-资金下拨*/
	public static final String FTSFUND = "XBZIDD";
	
	/**	资金调度-资金上收规则  */
	public static final String FTS_ACTIVETURNIN = "ZJSSGZ";
	
	/**	资金调度-资金下拨规则  */
	public static final String FTS_AUTOFUND = "ZJXBGZ";
	
	/**	资金调度-资金调拨规则  */
	public static final String FTS_AUTOTRANSFER = "ZJDBGZ";
	
	/**	资金调度-资金调拨   */
	public static final String FTS_TRANSFER = "ZJDB";
	
	/**	资金调度-资金上收下拨   */
	public static final String FTS_FUNDS_TWOLINES = "ZJSSXB";
	
	/**	资金调度-资金上缴下拨申请   */
	public static final String FTS_TWOLINES_APPLY = "SJXBSQ";
	
	/**	账户开户-报备类账户*/
	public static final String OPEN_FILING_ACCOUNT = "BBKH";
	
	/**	账户变更-报备类账户*/
	public static final String CHANGE_FILING_ACCOUNT = "BBBG";
	
	/**	账户销户-报备类账户*/
	public static final String DESTROY_FILING_ACCOUNT = "BBXH";
	
	/**	报备类账户开户告知*/
	public static final String INFORM_OPEN_FILING_ACCNT = "BBKHGZ";
	
	/**	报备类账户变更告知*/
	public static final String INFORM_CHANGE_FILING_ACCNT = "BBBGGZ";
	
	/**	报备类账户销户告知*/
	public static final String INFORM_DESTROY_FILING_ACCNT = "BBXHGZ";
	
	/**	信贷管理-合同申请   */
	public static final String CDM_CONTRACT_APPLICATION = "HTSQ";
	
	/**	信贷管理-提款申请   */
	public static final String CDM_WITHDRAWAL_REQUEST = "TKSQ";
	
	/**	信贷管理-提款申请-总公司审批   */
	public static final String CDM_WITHDRAWAL_REQUEST_ZGS = "TKSQ_ZGS";
	
	/**	信贷管理-还款申请   */
	public static final String CDM_REPAYMENT_APPLICATION = "HKSQ";
	
	/**	营收稽核-纸质凭证交接   */
	public static final String RA_AD_TRANSFER_PAPER = "PZJJ";
	
	/** 稽核督办单*/
	public static final String AUDIT_SUPERVISE = "JHDB";
	
	/**	资金调拨-调拨签发   */
	public static final String FLITTING_BILL_REQUEST = "DBQF";
	
	/** 资金调拨-支付确认单类型 */
	public static final String PAY_CONFIRM_ZJDB = "ZJDBQR";
	/** 资金调拨-柜台支付类型 */
	public static final String PAY_DB_CPAY = "DBCPAY";
	
	/** 资金调拨-调拨支付 */
	public static final String PAY_CONFIRM_DBZF = "DBZF";
	
	/** 资金计划-集团批复 */
	public static final String PLAN_AUDIT = "JHPF";
}
