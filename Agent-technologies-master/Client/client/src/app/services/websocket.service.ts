import { Injectable } from '@angular/core';
import { AID } from '../interfaces/aid';
import { AType } from '../interfaces/atype';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';



const url = 'ws://localhost:9090/AgentWAR/websocket';

@Injectable()
export class WebsocketService {

  ws: WebSocket;
  agentTypes: AType[] = [];
  performatives: string[] = [];
  runningAgents: AID[] = [];
  messages: string[] = [];

  constructor() {
  
  }

  public connect(): void {
    this.ws = new WebSocket(url);
    var w = this;

    this.ws.onmessage = function (message) {
      console.log("Stigla poruka na websocket:  " + message.data);
      var json = JSON.parse(message.data);
      var type = json.type;
      var data = json.data;
      let date = new Date();

      let now = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " - ";
      w.messages.push(now + message.data);

      if (type == "start_agent") {
        let aType = { name: data[0].type.name, module: data[0].type.module };
        let aHost = { alias: data[0].host.alias, address: data[0].host.address };
        let aid = { name: data[0].name, host: aHost, type: aType };
        w.runningAgents.push(aid);
      } else if (type == "stop_agent") {
        for (var i = 0; i < w.runningAgents.length; i++) {
          if (w.runningAgents[i].name == data[0].name && w.runningAgents[i].host.alias == data[0].host.alias
            && w.runningAgents[i].host.address == data[0].host.address && w.runningAgents[i].type.name == data[0].type.name
            && w.runningAgents[i].type.module == data[0].type.module) {
            w.runningAgents.splice(i, 1);
          }
        }
      } else if (type == "add_agent_type") {
        let aType = { name: data[0].type.name, module: data[0].type.module };
         w.agentTypes.push(aType);
      } else if (type == "remove_agent_type") {

      } else if (type == "acl_message") {
        console.log("sender name: " + data[0].sender.name);
        console.log("performative: " + data[0].performative);
        console.log("content: " + data[0].content);

        
      }
    };

  }

}
