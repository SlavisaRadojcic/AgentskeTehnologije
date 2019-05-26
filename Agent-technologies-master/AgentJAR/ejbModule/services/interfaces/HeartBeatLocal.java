package services.interfaces;

import javax.ejb.Local;

@Local
public interface HeartBeatLocal {
	
	public static String LOOKUP = "java:app/AgentJAR/HeartBeat!services.interfaces.HeartBeatLocal";
	
}
