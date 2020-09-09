import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-agent-classes',
  templateUrl: './agent-classes.component.html',
  styleUrls: ['./agent-classes.component.css']
})
export class AgentClassesComponent implements OnInit {
  socket: WebSocket = new WebSocket('ws://localhost:8080/WAR2020/ws');
  
  public agents : Set<Agent>;
  public chosenAgent : Agent = {
    name : "Agent",
    module : "111"
  };
  constructor(private http: HttpClient) { 
    this.fetchAgentTypes();
  }

  ngOnInit(): void {
  }

  async fetchAgentTypes() {
    const endpoint = 'http://localhost:8080/WAR2020/rest/agents/classes';
  
    this.http.get(endpoint).subscribe(response =>{
      this.agents = response as Set<Agent>;
      this.chosenAgent = this.agents[0];
    },err => {
      console.log("Unable to fetch agent types");
    });
  
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

export interface Agent{
  name : string,
  module : string;
};

