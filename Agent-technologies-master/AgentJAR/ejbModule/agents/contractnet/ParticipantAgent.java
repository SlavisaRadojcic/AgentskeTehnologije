package agents.contractnet;

import javax.ejb.Stateful;

import model.acl.ACLMessage;
import model.acl.Performative;
import model.agent.AID;
import model.agent.AgentClass;
import utils.MessageBuilder;

/**
 * @author Nikola
 *
 */
@Stateful
public class ParticipantAgent extends AgentClass {

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.agent.AgentInterface#handleMessage(model.acl.ACLMessage)
	 */
	@Override
	public void handleMessage(ACLMessage poruka) {
		switch (poruka.getPerformative()) {
		case cfp:
			handleCFP(poruka);
			break;
		case reject_proposal:
			handleRejectProposal(poruka);
			break;
		case accept_proposal:
			handleAcceptProposal(poruka);
			break;
		default:
			System.out.println("Unexpected message!");
		}
	}

	private void handleCFP(ACLMessage msg) {
		ACLMessage reply = new ACLMessage();
		reply.setSender(Id);
		if (Math.random() < 0.4) {
			System.out.println(Id + ": refusing to participate.");
			reply.setPerformative(Performative.refuse);
			reply.setReceivers(new AID[] { msg.getSender() });
		} else {
			System.out.println(Id + ": accepting.");
			reply.setPerformative(Performative.propose);
			reply.setReceivers(new AID[] { msg.getSender() });
		}

		if (msg.getReplyBy() < System.currentTimeMillis()) {
			System.out.println(Id + ": failed to reply by deadline. Discarding reply.");
		} else {
			MessageBuilder.sendACL(reply);
		}
	}

	private void handleRejectProposal(ACLMessage msg) {
		System.out.println(Id + ": rejected by initiator");
	}

	private void handleAcceptProposal(ACLMessage msg) {
		ACLMessage reply = new ACLMessage();
		reply.setSender(Id);
		reply.setReceivers(new AID[] { msg.getSender() });

		try {
			// simulacija nekakvog rada
			Thread.sleep(2500);

			if (Math.random() > 0.4) {
				reply.setPerformative(Performative.failure);
				System.out.println(Id + ": work failed, sending information to initiator.");
				reply.setContent("Work failed.");
			} else {
				reply.setPerformative(Performative.inform);
				System.out.println(Id + ": work succeeded, sending information to initiator.");
				reply.setContent("Work succeeded.");
			}
			MessageBuilder.sendACL(reply);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
