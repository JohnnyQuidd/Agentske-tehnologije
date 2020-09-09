import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-agent-classes',
  templateUrl: './agent-classes.component.html',
  styleUrls: ['./agent-classes.component.css']
})
export class AgentClassesComponent implements OnInit {
  socket: WebSocket = new WebSocket('ws://localhost:8080/WAR2020/ws');
  
  public agents : Set<AgentType>;
  public chosenAgentType : AgentType = {
    name : "Agent",
    module : "111"
  };
  public name : string;

  constructor(private http: HttpClient) { 
    this.fetchAgentTypes();
  }

  ngOnInit(): void {
  }

  async fetchAgentTypes() {
    const endpoint = 'http://localhost:8080/WAR2020/rest/agents/classes';
  
    this.http.get(endpoint).subscribe(response =>{
      this.agents = response as Set<AgentType>;
      this.chosenAgentType = this.agents[0];
    },err => {
      console.log("Unable to fetch agent types");
    });
  
  }

  async onSubmitNewAgent(){
    let endpoint = "http://localhost:8080/WAR2020/rest/agents/running/" + this.chosenAgentType + "/" + this.name;

    this.http.put(endpoint, null, {responseType: 'text'}).subscribe(response => {
      console.log(response);
    },err => {
      console.log(err);
    });

    console.log("PAZI SE OVOGA"+endpoint);

  }



}




async function  establishConnection(socket) {

  socket.onopen = (evt) => {
    console.log('onopen: Socket Status: ' + socket.readyState + ' (open)');
  }

  socket.onmessage = msg => {
    console.log('Received a message: ' + msg.data);
  }

  socket.onclose = function () {
    console.log("WS closed")
    socket = null;
  }
}

export interface AgentType{
  name : string,
  module : string;
};


