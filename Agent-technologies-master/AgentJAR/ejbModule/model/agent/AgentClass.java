package model.agent;

import javax.ejb.Singleton;

/**
 * @author Nikola
 *
 */

@Singleton
public abstract class AgentClass implements AgentInterface {

	protected AID Id;

	public AID getId() {
		return Id;
	}

	public void setId(AID id) {
		Id = id;
	}

}
