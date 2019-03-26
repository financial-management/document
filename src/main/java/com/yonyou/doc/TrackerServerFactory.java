package com.yonyou.doc;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * TrackerServer 工厂类
* @ClassName TrackerServerFactory 
* @author hubc
* @date 2018年5月24日
 */
public class TrackerServerFactory extends BasePooledObjectFactory<TrackerServer> {

    @Override
    public TrackerServer create() throws Exception {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    @Override
    public PooledObject<TrackerServer> wrap(TrackerServer trackerServer) {
        return new DefaultPooledObject<TrackerServer>(trackerServer);
    }
}
