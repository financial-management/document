package com.yonyou.doc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.utils.PropertyUtil;

/**
 * Fastdfs客户端
* @ClassName FastdfsClient 
* @author hubc
* @date 2018年5月23日
 */
public class FastdfsClient {
	
	public static Logger logger = LoggerFactory.getLogger(FastdfsClient.class);
	
	/**
	 * FastDFS元数据默认包含的key
	 */
	public static final String KEY_FILE_NAME = "fileName";//文件名（包含后缀）
	
	/**
	 * 最大文件大小
	 */
	public static int maxFileSize = Integer.valueOf(PropertyUtil.getPropertyByKey("doc.maxFileSize"));

	/**
	 * 通过stream上传文件
	* @param fileInsStream
	* @param fileName
	* @param descriptions
	* @return
	* @throws Exception
	 */
	public static String upload(InputStream fileInsStream, String fileName, Map<String, String> descriptions){
		//.available()不靠谱-阻塞的情况下无法正确获取流的大小
//		int fileSize = fileInsStream.available();
//		if(fileSize > maxFileSize){
//			logger.error("上传文件大小超出限制");
//			throw new Exception();
//		}
		//输入流转字节数组
		byte[] buf = new byte[1024*1024];
		int length = 0;
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		try {
			while((length = fileInsStream.read(buf)) > 0){
				swapStream.write(buf, 0 ,length);
			}
		} catch (IOException e) {
			logger.error("从流中读取文件失败");
			e.printStackTrace();
		}
		byte[] fileContent = swapStream.toByteArray();
		return upload(fileContent, fileName, descriptions);
	}

	/**
	 * 上传文件基础方法
	* @param fileInsStream
	* @param fileName
	* @param descriptions
	* @return
	* @throws Exception
	 */
	public static String upload(byte[] fileContent, String fileName, Map<String, String> descriptions){
//		int fileSize = fileContent.length;
//		if(maxFileSize > 0 && fileSize > maxFileSize){
//			logger.error("上传文件大小超出限制");
//			throw new Exception();
//		}
		Date now = new Date( );
	      SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
	     System.out.println("预防上传文件服务超时Start{}Current Date: " + ft.format(now));
		//文件后缀名
		String extName = null;
		if(fileName.contains(".")){
			extName = fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		// 设置元信息=》默认+追加
		ArrayList<NameValuePair> metaInfo = new ArrayList<>();
		metaInfo.add(new NameValuePair(KEY_FILE_NAME, fileName));
		if (descriptions != null && descriptions.size() > 0) {
			for (Entry<String, String> entry : descriptions.entrySet()) {
				metaInfo.add(new NameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		NameValuePair[] metaList = new NameValuePair[metaInfo.size()];
		metaInfo.toArray(metaList);
		
		TrackerServer trackerServer = TrackerServerPool.borrowObject();
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		String dfsPath = null;
		try {
			dfsPath = storageClient1.upload_file1(fileContent, extName, metaInfo.toArray(metaList));
			System.out.println("预防上传文件服务超时End{}Current Date: " + ft.format(now));
		} catch (IOException | MyException e) {
			logger.error("上传文件失败");
			e.printStackTrace();
		} 
		//返还对象
		TrackerServerPool.returnObject(trackerServer);
		
		return dfsPath;
	}
	
	/**
	 * 下载文件
	* @param dfsPath
	* @return
	* @throws IOException
	* @throws MyException
	 */
	public static byte[] download(String dfsPath){
		TrackerServer trackerServer = TrackerServerPool.borrowObject();
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		byte[] fileContent = null;
		try {
			fileContent = storageClient1.download_file1(dfsPath);
		} catch (IOException | MyException e) {
			logger.error("下载文件失败,dfsPath:"+dfsPath);
			e.printStackTrace();
		}
		//返还对象
		TrackerServerPool.returnObject(trackerServer);
		if(fileContent == null){
			logger.error("下载文件失败,请检查dfs路径是否正确："+dfsPath);
		}
		return fileContent;
	}

	/**
	 * 删除文件
	* @param dfsPath
	* @return
	 */
	public static boolean delete(String dfsPath){
		boolean flag = false;
		TrackerServer trackerServer = TrackerServerPool.borrowObject();
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		try {
			if(0 == storageClient1.delete_file1(dfsPath)){
				flag = true;				
			}else{
				logger.error("删除文件失败,请检查路径：dfsPath:" + dfsPath);
			}
		} catch (IOException |MyException e) {
			logger.error("删除文件失败,dfsPath:" + dfsPath);
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取文件元数据
	* @param dfsPath
	* @return
	* @throws IOException
	* @throws MyException
	 */
	public HashMap<String, String> getMetaInfo(String dfsPath) {
		NameValuePair[] metaInfo = null;
		TrackerServer trackerServer = TrackerServerPool.borrowObject();
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
		try {
			metaInfo = storageClient1.get_metadata1(dfsPath);
		} catch (IOException | MyException e) {
			logger.error("获取元数据失败");
			e.printStackTrace();
		}
		TrackerServerPool.returnObject(trackerServer);
		
		HashMap<String, String> metaInfoMap = new HashMap<>();
		if(metaInfo != null){
			for (NameValuePair nameValuePair : metaInfo) {
				metaInfoMap.put(nameValuePair.getName(), nameValuePair.getValue());
			}
		}
		return metaInfoMap;
	}


	public void getUrl(String dfsPath) {
		
	}
}
