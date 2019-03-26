package com.yonyou.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.util.jdbc.BaseDao;
import com.yonyou.util.sql.SQLUtil.WhereEnum;
import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.sql.SqlWhereEntity;

/**
 * 流水号工具类
 * 
 * @ClassName ReceiptcodeUtil
 * @author 博超
 * @date 2017年3月2日
 */
public class ReceiptcodeUtil {

	public static final String RC_TABLE_NAME = "UT_RECEIPT_CODE";// /表名
	public static final String RC_ID = "ID";// /主键
	public static final String RC_TYPE = "RC_TYPE";// 单据类型
	public static final String RC_PARTCODE = "RC_PARTCODE";//单据的单位/部门
	public static final String RC_DATE = "RC_DATE";// /最新单据的产生时间，格式：yyMMdd
	public static final String RC_SERIALNUMBER = "RC_SERIALNUMBER";// /流水号


	//时间格式
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
	
	/**
	 * 获取单个流水号
	* @param receiptType 单据类型
	* @param partCode 部门编码
	* @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public static String getReceiptCode(String receiptType, String partCode) {
		
		String[] receiptCodeArr = batchGetReceiptCode(receiptType,partCode,1);
		
		if (receiptCodeArr != null && receiptCodeArr.length > 0) {
			return receiptCodeArr[0];
		}
		return null;
	}
	
	/**
	 * 批量获取同一类型n个流水号
	* @param receiptType 单据类型
	* @param partCode 部门编码
	* @param n 数量
	* @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public static String[] batchGetReceiptCode(String receiptType, String partCode, int n) {
		
		if (receiptType == null || receiptType.trim().length() == 0) {
			throw new NullPointerException("生成流水号：单据类型不能为空 \nReceiptcodeUtil.getLastReceiptCode.receiptType:" + receiptType);
		}
		if (partCode == null || partCode.trim().length() == 0) {
			throw new NullPointerException("生成流水号：部门代码不能为空 \nReceiptcodeUtil.getLastReceiptCode.partCode:" + partCode);
		}
		String currentDate = dateFormat.format(new Date());
		
		SqlWhereEntity sqlWhere = new SqlWhereEntity();
		sqlWhere.putWhere(RC_DATE, currentDate, WhereEnum.EQUAL_STRING);
		sqlWhere.putWhere(RC_TYPE, receiptType, WhereEnum.EQUAL_STRING);
		sqlWhere.putWhere(RC_PARTCODE, partCode, WhereEnum.EQUAL_STRING);
		SqlTableUtil tableEntity = new SqlTableUtil(RC_TABLE_NAME, "").appendSelFiled("*").appendSqlWhere(sqlWhere);
		Map<String, Object> receiptEntity = BaseDao.getBaseDao().find(tableEntity);
		
		String[] receiptCodeArr = new String[n];
		String[] serialNumberArr = new String[n];
		//按照数据库是否有记录标记是新增还是修改
		boolean isUpdate = true;
		if(receiptEntity.isEmpty()){
			isUpdate = false;
//			receiptEntity = new HashMap<String, Object>();
			receiptEntity.put(RC_TYPE,receiptType);
			receiptEntity.put(RC_PARTCODE,partCode);
			receiptEntity.put(RC_DATE,currentDate);
			receiptEntity.put(RC_SERIALNUMBER,"");
		}else{
			receiptEntity.remove("RMRN");
		}
		
		serialNumberArr[0] = incSerialNumber((String) receiptEntity.get(RC_SERIALNUMBER));
		receiptCodeArr[0] = partCode + receiptType + currentDate + serialNumberArr[0];
		for (int i = 1; i < n; i++) {
			serialNumberArr[i] = incSerialNumber(serialNumberArr[i-1]);
			receiptCodeArr[i] = partCode + receiptType + currentDate + serialNumberArr[i];
		}
		
		receiptEntity.put(RC_SERIALNUMBER, serialNumberArr[n-1]);
		if(isUpdate){
			BaseDao.getBaseDao().update(RC_TABLE_NAME, receiptEntity, new SqlWhereEntity());
		}else{
			BaseDao.getBaseDao().insert(RC_TABLE_NAME, receiptEntity);
		}
		
		return receiptCodeArr;
	}

	/**
	 * 序列号+1
	* @param serialnumber
	* @return
	 */
	private static String incSerialNumber(String serialnumber){
		if(""==serialnumber.trim()){
			return "00000001";
		}
		
		int i = Integer.parseInt(serialnumber) + 1;
		return String.format("%08d", i);
	}
	
}
