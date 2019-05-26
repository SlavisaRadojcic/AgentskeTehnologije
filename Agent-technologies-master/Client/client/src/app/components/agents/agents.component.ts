import { Component, OnInit } from '@angular/core';
import { AID } from '../../interfaces/aid';
import { AType } from '../../interfaces/atype';
import { RestService } from '../../services/rest.service';
import * as $ from 'jquery/dist/jquery.min.js';
import { WebsocketService } from '../../services/websocket.service';

@Component({
  selector: 'app-agents',
  templateUrl: './agents.component.html',
  styleUrls: ['./agents.component.css']
})
export class AgentsComponent implements OnInit {

  constructor(private restService: RestService, private ws: WebsocketService) { }

  ngOnInit() {
    this.restService.getAgentTypes().subscribe(res => {
      console.log("tipovi " + res);
      this.ws.agentTypes = res;
    });
    this.restService.getPerformative().subscribe(res => {
      console.log("performative " + res);
      this.ws.performatives = res;
    });
    this.restService.getRunningAgents().subscribe(res => {
      console.log("running agents: " + res);
      this.ws.runningAgents = res;
    });
  }

  startAgent(name, type) {
    let agent = type.module + "$" + type.name + "/" + name;
    this.restService.startAgent(agent).subscribe(res => console.log(res));
  }

  stopAgent(aid) {
    let agent = aid.name + "$" + aid.host.alias + "$" + aid.host.address + "$" + aid.type.name + "$" + aid.type.module;
    console.log(aid);
    this.restService.stopAgent(agent).subscribe(res => console.log(res));
  }

  sendMessage(message) {
    if (message.sender == undefined) {
      message.sender = null;
    }
    if (message.replyto == undefined) {
      message.replyto = null;
    }
    if (message.content == undefined) {
      message.content = "";
    }
    if (message.language == undefined) {
      message.language = "";
    }
    if (message.encoding == undefined) {
      message.encoding = "";
    }
    if (message.ontology == undefined) {
      message.ontology = "";
    }
    if (message.protocol == undefined) {
      message.protocol = "";
    }
    if (message.conversationid == undefined) {
      message.conversationid = "";
    }
    if (message.replywith == undefined) {
      message.replywith = "";
    }
    if (message.inreplyto == undefined) {
      message.inreplyto = "";
    }
    if (message.replyby == undefined) {
      message.replyby = "";
    }

    if (message.sender != undefined) {
      var sender = message.sender.split("$");
      var senderType = "{\"name\":\"" + sender[1] + "\",\"module\":\"" + sender[2] + "\"}";
      var senderHost = "{\"alias\":\"" + sender[3] + "\",\"address\":\"" + sender[4] + "\"}";
      message.sender = "{\"name\":\"" + sender[0] + "\","
      + " \"host\":" + senderHost + ","
      + " \"type\":" + senderType + "}"
    }
    if (message.replyto != undefined) {
      var replyTo = message.replyto.split("$");
      var replyToType = "{\"name\":\"" + replyTo[1] + "\",\"module\":\"" + replyTo[2] + "\"}";
      var replyToHost = "{\"alias\":\"" + replyTo[3] + "\",\"address\":\"" + replyTo[4] + "\"}";
      message.replyto = "{\"name\":\"" + replyTo[0] + "\","
      + " \"host\":" + replyToHost + ","
      + " \"type\":" + replyToType + "}"
    }
    var receivers = "";

    for (var i = 0; i < message.receivers.length; i++) {
      let receiver = message.receivers[i].split("$");
      let receiverName = "{\"name\":\"" + receiver[0] + "\","
      let receiverType = "\"type\":{\"name\":\"" + receiver[1] + "\",\"module\":\"" + receiver[2] + "\"},";
      let receiverHost = "\"host\":{\"alias\":\"" + receiver[3] + "\",\"address\":\"" + receiver[4] + "\"}}";
      receivers += receiverName;
      receivers += receiverType;
      receivers += receiverHost;
      if (i < message.receivers.length - 1) {
        receivers += ",";
      }
    }

    let aclMsg = "{\"performative\":\"" + message.performative + "\","
      + " \"sender\":"+message.sender+","
      + " \"receivers\":[" + receivers + "],"
      + " \"replyTo\":"+message.replyto+","
      + " \"content\":\"" + message.content + "\","
      + " \"language\":\"" + message.language + "\","
      + " \"encoding\":\"" + message.encoding + "\","
      + " \"ontology\":\"" + message.ontology + "\","
      + " \"protocol\":\"" + message.protocol + "\","
      + " \"conversationId\":\"" + message.conversationid + "\","
      + " \"replyWith\":\"" + message.replywith + "\","
      + " \"inReplyTo\":\"" + message.inreplyto + "\","
      + " \"replyBy\":\"" + message.replyby + "\"}";

    console.log(aclMsg);
    this.restService.sendACLMessage(aclMsg).subscribe(res => {
      console.log(res);
      message.sender = null;
      message.replyto = null;
    
    });
  }

  clearConsole() {
    this.ws.messages = [];
  }


}
