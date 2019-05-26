package agent_manager;

import java.util.List;

import javax.ejb.Local;

import model.acl.ACLMessage;
import model.agent.AID;
import model.agent.AgentType;
import model.center.AgentCenter;

@Local
public interface AgentManagerLocal {

	public static final String LOOKUP = "java:global/AgentEAR/AgentJAR/AgentManager!agent_manager.AgentManagerLocal";

	void startInit(AgentCenter center);

	List<AID> getRunningAgents();

	List<AgentType> getAgentTypes();

	AgentType getAgentType(String name, String module);

	boolean msgToAgent(AID agent, ACLMessage msg);

	void startAgent(AID agent);

	void stopAgent(AID agent);

	void addAgentType(AgentType at);

	void deleteAgentType(AgentType at);

	void deleteTypesByNode(AgentCenter center);

}
