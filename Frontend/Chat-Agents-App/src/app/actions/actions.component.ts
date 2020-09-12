import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.css']
})
export class ActionsComponent implements OnInit {
  public messages = "";
  socket: WebSocket = new WebSocket('ws://localhost:8080/WAR2020/ws');
  public performatives : Set<Performative>;
  public chosenPerformative : Performative = {
    performative: 'Performative'
  };

  public agents : Set<Agent>;
  public content : string;
  public message : ACLMessage = {
    performative : "",
    receiver : "" ,
    content : "",
    sender : ""
  };

  public chosenReceiver : Agent ={
    name : "temp",
    module : "temp"
  };

  public chosenSender : Agent ={
    name : "temp",
    module : "temp"
  };

  constructor(private http: HttpClient) { 
    this.establishConnection(this.socket);
    this.fetchPerformatives();
    this.fetchRunningAgents();
  }

  ngOnInit(): void {
  }

  async fetchPerformatives() {
    const apiEndpoint = 'http://localhost:8080/WAR2020/rest/messages';

    this.http.get(apiEndpoint).subscribe(response => {
      this.performatives = response as Set<Performative>;
    });
  }

  async fetchRunningAgents() {
    const endpoint = 'http://localhost:8080/WAR2020/rest/agents/running';
  
    this.http.get(endpoint).subscribe(response =>{
      this.agents = response as Set<Agent>;
    },err => {
      console.log("Unable to fetch agent types");
    });
  
  }

  async sendMessage(){
    let endpoint = 'http://localhost:8080/WAR2020/rest/messages';

    this.message.content = this.content;
    this.message.receiver =  this.chosenReceiver.name;
    this.message.sender = this.chosenSender.name;
    this.message.performative = this.chosenPerformative.performative;
    let payload = JSON.stringify(this.message);
    
    let header = new HttpHeaders();
    let hdrs= header.append('content-type', 'application/json');
    this.http.post(endpoint, payload, {responseType: 'text', headers: hdrs}).subscribe(response => {
      console.log(response);
    },err => {
      console.log(err); 
    });

  }

  async establishConnection(socket) {

    socket.onopen = (evt) => {
      // TODO
    }
  
    socket.onmessage = msg => {
      console.log(msg.data);
      this.appendField(msg.data);
      this.fetchRunningAgents();

    }
  
    socket.onclose = function () {
      // TODO
      socket = null;
    }
  }

  async appendField(string : string) {
    this.messages += string += '\n';
  }

}



export interface Performative {
  performative: string;
};

export interface Agent{
  name : string,
  module : string;

};

export interface ACLMessage {
  performative : string;
  receiver : string;
  content : string;
  sender : string;
}