package rest;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.acl.ACLMessage;
import services.interfaces.RestMessageLocal;

@Path("/messages")
public class MessagesController {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendMessage(ACLMessage msg) {
		// posalji acl poruku
		//System.out.println(msg.getReceivers());
		//System.out.println(msg.getContent());
		try {
			Context context = new InitialContext();
			RestMessageLocal rml = (RestMessageLocal) context.lookup(RestMessageLocal.LOOKUP);
			rml.postMessages(msg);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerformatives() {
		// dobavi listu performativa
		try {
			Context context = new InitialContext();
			RestMessageLocal rml = (RestMessageLocal) context.lookup(RestMessageLocal.LOOKUP);
			return rml.getMessages();
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

}
