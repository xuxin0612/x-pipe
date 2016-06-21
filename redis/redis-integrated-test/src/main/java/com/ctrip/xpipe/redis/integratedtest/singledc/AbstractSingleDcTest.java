package com.ctrip.xpipe.redis.integratedtest.singledc;


import java.awt.IllegalComponentStateException;
import java.util.List;
import java.util.Map;

import org.junit.Before;

import com.ctrip.xpipe.redis.core.entity.DcMeta;
import com.ctrip.xpipe.redis.core.entity.RedisMeta;
import com.ctrip.xpipe.redis.integratedtest.AbstractIntegratedTest;
import com.ctrip.xpipe.redis.keeper.RedisKeeperServer;



/**
 * @author wenchao.meng
 *
 * Jun 15, 2016
 */
public class AbstractSingleDcTest extends AbstractIntegratedTest{
	
	private DcMeta dcMeta;
	
	@Before
	public void beforeAbstractSingleDcTest() throws Exception{


		this.dcMeta = activeDc();
		
		if(dcMeta == null){
			throw new IllegalComponentStateException("can not find dc with a active redis master");
		}
		
		startDc(dcMeta.getId());
		sleep(1000);
	}
	
	public DcMeta getDcMeta() {
		return dcMeta;
	}
	
	protected List<RedisMeta> getRedises(){
		
		return getRedises(dcMeta.getId());
	}
	
	protected List<RedisMeta> getRedisSlaves(){
		return getRedisSlaves(dcMeta.getId());
	}
	
	public RedisKeeperServer getRedisKeeperServerActive(){
		
		Map<String, RedisKeeperServer> redisKeeperServers = getRegistry().getComponents(RedisKeeperServer.class);
		
		for(RedisKeeperServer server : redisKeeperServers.values()){
			if(server.getRedisKeeperServerState().isActive()){
				return server;
			}
		}
		return null;
	}
	

}
