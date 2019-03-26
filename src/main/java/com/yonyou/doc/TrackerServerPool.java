package com.yonyou.doc;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
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
public class TrackerServerPool {

    private static Logger logger = LoggerFactory.getLogger(TrackerServerPool.class);

    /**
     * TrackerServer 配置文件路径(相对根目录的相对路径)
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
    private TrackerServerPool(){};

    private static synchronized GenericObjectPool<TrackerServer> getObjectPool(){
        if(trackerServerPool == null){
            try {
                // 加载配置文件
            	String configPath = Thread.currentThread().getContextClassLoader().getResource(FASTDFS_CONFIG_PATH).getFile();
            	System.out.println(configPath);
                ClientGlobal.init(configPath);
            } catch (Exception e) {
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
