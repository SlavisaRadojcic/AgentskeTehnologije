package services.interfaces;

import java.util.List;

import javax.ejb.Local;

import model.agent.AID;
import model.agent.AgentType;

@Local
public interface RestAgentsLocal {

	List<AgentType> getAgentsClasses();
	List<AID> getAgentsRunning();
	void putAgentsRunning(AgentType type, String name);
	void deleteAgentsRunning(AID aid);
	void addAgentType(AgentType at);
	void removeAgentType(AgentType at);
	void addRunningAgent(AID aid);
	
}
