package com.yonyou.doc;

import java.util.Map;

/**
 * 文档服务bean
* @ClassName DocBean 
* @author hubc
* @date 2018年5月29日
 */
public class DocBean {

	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 上传人id
	 */
	private String userId;
	/**
	 * 上传人
	 */
	private String userName;
	/**
	 * 上传时间
	 */
	private String uploadTime;
	/**
	 * 业务编码
	 */
	private String bsCode;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 可用状态
	 */
	private String usableStatus;
	/**
	 * 只读标志
	 */
	private String readOnly;
	/**
	 * 所在目录id
	 */
	private String directoryId;
	
	//以下为上传文件所需的新增属性
	/**
	 * 公司id
	 */
	private String  companyId;
	/**
	 * 文件内容
	 */
	private byte[] fileContent;
	/**
	 * 所属字段
	 */
	private String fieldCode;
	/**
	 * 追加的描述信息
	 */
	private Map<String, String> description;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getBsCode() {
		return bsCode;
	}
	public void setBsCode(String bsCode) {
		this.bsCode = bsCode;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getUsableStatus() {
		return usableStatus;
	}
	public void setUsableStatus(String usableStatus) {
		this.usableStatus = usableStatus;
	}
	public String getReadOnly() {
		return readOnly;
	}
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public byte[] getFileContent() {
		return fileContent;
	}
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	public Map<String, String> getDescription() {
		return description;
	}
	public void setDescription(Map<String, String> description) {
		this.description = description;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
}
