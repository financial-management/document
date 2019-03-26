package com.yonyou.util.wsystem.api;

import java.util.Map;

import com.yonyou.util.sql.SqlTableUtil;
import com.yonyou.util.wsystem.util.MSystemMetaUtil;

/**
 * 元数据业务抽象类
 * @author moing_ink
 * <p>创建时间 ： 2016年12月27日
 * @version 1.0
 */
public abstract class IMetaDataEntity<T> extends HTTPApi {
	
	/**
	 * 表名
	 */
	public  String TABLE ="";
	/**
	 * 别名
	 */
    public  String ALIS_TABLE="";
    /**
     * 实现类
     */
	public  T t =null;
	
	protected SqlTableUtil sqlTableUtil =null;
	
	protected boolean isBulidTableUtil =false;
	/**
	 * 初始化
	 */
	public IMetaDataEntity(){
		initTable();
		t=initObj();
		initSqlTableUtil();
		afterTable();
	}
	/**
	 * 抽象方法  初始化子类
	 * @return
	 */
	protected abstract T initObj();
	/**
	 * 抽象方法 初始话 表名 别名
	 */
	protected abstract void initTable();
	
	/**
	 * 对象数据追加处理
	 */
	protected void afterTable(){
		
	}
	
	protected void initSqlTableUtil(){
		//初始化工具类
		sqlTableUtil =new SqlTableUtil(TABLE,ALIS_TABLE);
	}
	
	public void bulidSqlTableUtil(){
		//设置查询项
		sqlTableUtil.appendSelFiledMap(getFiledsForCodeAndCode());
	}
	
	/**
	 * 获取对象Code与Name的Map 信息
	 * @return
	 */
	public  Map<String,String> getFiledsForCodeAndName(){
		return MSystemMetaUtil.getFiledsForCodeAndName(t);
	}
	
	/**
	 * 获取对象Code与Name的Map 信息
	 * @return
	 */
	public  Map<String,String> getFiledsForCodeAndCode(){
		return MSystemMetaUtil.getFiledsForCodeAndCode(t);
	}
	
	/**
	 * 获取SQL工具类   注：为了避免影响原始数据采取COPY方式
	 * @return
	 */
	public SqlTableUtil findSqlTableUtil(){ 
		if(!isBulidTableUtil){
			this.bulidSqlTableUtil();
			isBulidTableUtil=true;
		}
		return sqlTableUtil.copyTable();
	}
	
	
}
