package model.agent;

import javax.ejb.Local;

import model.acl.ACLMessage;

@Local
public interface AgentInterface {

	public void handleMessage(ACLMessage poruka);
	
}
