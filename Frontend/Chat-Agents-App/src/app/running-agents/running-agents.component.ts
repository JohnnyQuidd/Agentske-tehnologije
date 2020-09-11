import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';




@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  socket: WebSocket = new WebSocket('ws://localhost:8080/WAR2020/ws');
  public agents : Set<Agent>;
  public chosenAgent : Agent = {
    name : "Agent",
    module : "111"
  };


  constructor(private http : HttpClient) { 
    this.establishConnection(this.socket);
    this.fetchRunningAgents();
    
  }

  ngOnInit(): void {
    
  }

  async fetchRunningAgents() {
    const endpoint = 'http://localhost:8080/WAR2020/rest/agents/running';
  
    this.http.get(endpoint).subscribe(response =>{
      this.agents = response as Set<Agent>;
    },err => {
      console.log("Unable to fetch agent types");
    });
  
  }

  async stopAgent(){
    let endpoint = 'http://localhost:8080/WAR2020/rest/agents/running/' + this.chosenAgent;

    console.log(endpoint);

    this.http.delete(endpoint,{responseType: 'text'}).subscribe(response => {
      console.log(response);
    },err => {
      console.log(err);
    });

  }

  async establishConnection(socket) {

    socket.onopen = (evt) => {
      //TODO    
    }
  
    socket.onmessage = msg => {
      this.fetchRunningAgents();
    }
  
    socket.onclose = function () {
      // TODO
      socket = null;
    }
  }
  


}





export interface Agent{
  name : string,
  module : string;

}