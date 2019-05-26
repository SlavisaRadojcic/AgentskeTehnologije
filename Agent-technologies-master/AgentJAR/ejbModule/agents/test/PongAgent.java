package agents.test;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

@Stateful
public class PongAgent extends AgentClass {

	@Override
	public void handleMessage(ACLMessage poruka) {
		System.out.println("Message received from Ping " + poruka.getSender());
		if (poruka.getPerformative() == Performative.request) {
			ACLMessage response = new ACLMessage();
			response.setReceivers(new AID[] { poruka.getSender() });
			response.setPerformative(Performative.inform);
			response.setContent("Reply to message received from Ping " + poruka.getSender());
			response.setSender(Id);
			MessageBuilder.sendACL(response);
		}

	}

}
