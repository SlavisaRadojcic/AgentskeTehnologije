package services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;

import agent_manager.AgentManagerLocal;
import model.agent.AgentType;
import model.center.AgentCenter;
import node_manager.NodeManagerLocal;
import services.interfaces.RestNodeLocal;
import utils.RestBuilder;

@Stateless
public class RestNode implements RestNodeLocal {

	@Override
	public void deleteNode(String alias) {
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			nml.deleteSlave(alias);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postNode(AgentCenter ac) {
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			
			if(nml.getMasterNode().getAddress().equals(nml.getThisNode().getAddress())) {
				// ako sam ja master onda mi je stigao poziv od novog cvora da ga ubacim u mrezu
				connectSlave(ac);
			} else {
				// ili sam vec u mrezi pa mi master salje novi cvor
				// ili sam novi cvor pa mi master salje ostale cvorove u mrezi
				nml.addSlave(ac);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void connectSlave(AgentCenter slaveAddr) {
		List<AgentType> slaveTypes = RestBuilder.getSlaveAgentTypes(slaveAddr); // trazimo od slavea sve tipove agenata
		// javljamo ostalim cvorovima da je slave dosao
		try {
			Context context = new InitialContext();
			NodeManagerLocal nml = (NodeManagerLocal) context.lookup(NodeManagerLocal.LOOKUP);
			List<AgentCenter> slaves = nml.getSlaves();

			for(AgentCenter ac : slaves) {
				RestBuilder.sendNewSlave(ac, slaveAddr); // master cvor javlja ostalim cvorovima da je novi cvor dosao u mrezu				
				RestBuilder.sendNewAgentTypes(ac, slaveTypes); 
			}
			RestBuilder.sendNodesToSlave(slaveAddr, slaves); // master cvor dostavlja spisak agenata novom cvoru
			AgentManagerLocal aml = (AgentManagerLocal) context.lookup(AgentManagerLocal.LOOKUP);// master cvor dostavlja spisak tipova agenata novom cvoru
			RestBuilder.sendNewAgentTypes(slaveAddr, aml.getAgentTypes());
			RestBuilder.sendRunningAgentsToSlave(slaveAddr, aml.getRunningAgents());
			nml.addSlave(slaveAddr, slaveTypes); 
		} catch (Exception e) {
			e.printStackTrace();
		} 		
	}	
	
}
