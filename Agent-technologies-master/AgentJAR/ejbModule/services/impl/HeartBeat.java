package services.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.TimerService;
import javax.naming.Context;
import javax.naming.InitialContext;

import model.center.AgentCenter;
import node_manager.NodeManagerLocal;
import services.interfaces.HeartBeatLocal;
import utils.RestBuilder;

@Singleton
public class HeartBeat implements HeartBeatLocal {

	@Resource
	TimerService ts;

	@Schedules({
		@Schedule(hour = "*", minute = "*", second = "*/30", info = "every 30 seconds")
		})
	private void heartBeatPr() {
		System.out.println("###### Heartbeat ######");
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			List<AgentCenter> nodes = nml.getSlaves();
			
			for(AgentCenter a : nodes) {
				if(RestBuilder.getHeartBeat(a) == false) {
					if(RestBuilder.getHeartBeat(a) == false) {
						// brisemo ovaj cvor
						nml.deleteSlave(a);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}

}
