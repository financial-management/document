package com.yonyou.doc;

import java.io.IOException;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.utils.PropertyUtil;

/**
 * TrackerServer 对象池
* @ClassName TrackerServerPool 
* @author hubc
* @date 2018年5月24日
 */
public class CopyOfTrackerServerPool {

    private static Logger logger = LoggerFactory.getLogger(CopyOfTrackerServerPool.class);

    /**
     * TrackerServer 配置文件路径
     */
    private static final String FASTDFS_CONFIG_PATH = "fdfs_client.conf";

    /**
     * 最大连接数
     */
    private static int maxStorageConnection = Integer.valueOf(PropertyUtil.getPropertyByKey("doc.maxConnection"));

    /**
     * TrackerServer对象池
     */
    private static GenericObjectPool<TrackerServer> trackerServerPool;

    /**
     * 禁止无参构造
     */
    private CopyOfTrackerServerPool(){};

    private static synchronized GenericObjectPool<TrackerServer> getObjectPool(){
        if(trackerServerPool == null){
            try {
                // 加载配置文件
            	String configPath = logger.getClass().getResource("/fdfs_client.conf").getFile();
            	System.out.println(configPath);
                ClientGlobal.init(configPath);
            } catch (IOException | MyException e) {
            	logger.error("FastDFS加载配置文件失败");
                e.printStackTrace();
            }

            //连接池配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            //最小空闲连接
            poolConfig.setMinIdle(2);
            //最大连接
            if(maxStorageConnection > 0){
                poolConfig.setMaxTotal(maxStorageConnection);
            }

            trackerServerPool = new GenericObjectPool<>(new TrackerServerFactory(), poolConfig);
        }
        return trackerServerPool;
    }

    /**
     * 获取 TrackerServer
     * @return TrackerServer
     * @throws FastDFSException
     */
    public static TrackerServer borrowObject(){
        TrackerServer trackerServer = null;
        try {
			trackerServer = getObjectPool().borrowObject();
		} catch (Exception e) {
			logger.error("获取TrackerServer实例失败");
			e.printStackTrace();
		}

        return trackerServer;
    }

    /**
     * 回收 TrackerServer
     * @param trackerServer
     */
    public static void returnObject(TrackerServer trackerServer){
        getObjectPool().returnObject(trackerServer);
    }


}
