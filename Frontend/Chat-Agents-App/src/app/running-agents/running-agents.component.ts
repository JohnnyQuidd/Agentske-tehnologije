import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';




@Component({
  selector: 'app-running-agents',
  templateUrl: './running-agents.component.html',
  styleUrls: ['./running-agents.component.css']
})
export class RunningAgentsComponent implements OnInit {

  socket: WebSocket = new WebSocket('ws://localhost:8080/WAR2020/ws');
  public agents : Agent[];
  public chosenAgent : Agent = {
    name : "Agent",
    module : "111"
  };


  constructor(private http : HttpClient) { 
    establishConnection(this.socket);
    
  }

  ngOnInit(): void {
    
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

}