package services.interfaces;

import javax.ejb.Local;

import model.center.AgentCenter;

@Local
public interface RestNodeLocal {

	public static String LOOKUP = "java:app/RestNode!services.interfaces.RestNodeLocal";
	
	void postNode(AgentCenter ac);
		
	void deleteNode(String alias);
	
}
