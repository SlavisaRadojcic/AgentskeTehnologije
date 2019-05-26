import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { AType } from '../interfaces/atype';
import { AID } from '../interfaces/aid';

const port: string = "8080";
const host: string = "localhost";

@Injectable()
export class RestService {

  config = {
    headers: {
      'content-type': 'application/json',
      'accept': 'application/json'
    }
  }
  constructor(private http: HttpClient) { }

  getAgentTypes(): Observable<AType[]> {
    return this.http.get<AType[]>('http://' + host + ':' + port + '/AgentWAR/agent-app/agents/classes');
  }

  getRunningAgents(): Observable<AID[]> {
    return this.http.get<AID[]>('http://' + host + ':' + port + '/AgentWAR/agent-app/agents/running');
  }

  startAgent(agent) {
    return this.http.put('http://' + host + ':' + port + '/AgentWAR/agent-app/agents/running/' + agent, "");
  }

  stopAgent(agent) {
    return this.http.delete('http://' + host + ':' + port + '/AgentWAR/agent-app/agents/running/' + agent);
  }

  sendACLMessage(aclMessage) {
    return this.http.post('http://' + host + ':' + port + '/AgentWAR/agent-app/messages', aclMessage, this.config);
  }

  getPerformative(): Observable<string[]> {
    return this.http.get<string[]>('http://' + host + ':' + port + '/AgentWAR/agent-app/messages');
  }

}
