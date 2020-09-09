import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.css']
})
export class ActionsComponent implements OnInit {
  public performatives : Set<Performative>;
  public chosenPerformative : Performative = {
    performative: 'Performative'
  };

  public agents : Set<Agent>;
  public content : string;
  public message : ACLMessage = {
    performative : "",
    reciever : "" ,
    content : "",
    sender : ""
  };

  public chosenReciever : Agent ={
    name : "temp",
    module : "temp"
  };

  public chosenSender : Agent ={
    name : "temp",
    module : "temp"
  };

  constructor(private http: HttpClient) { 
    this.fetchPerformatives();
    this.fetchRunningAgents();
  }

  ngOnInit(): void {
  }

  async fetchPerformatives() {
    const apiEndpoint = 'http://localhost:8080/WAR2020/rest/messages';

    this.http.get(apiEndpoint).subscribe(response => {
      this.performatives = response as Set<Performative>;
      console.log(this.performatives);
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
    this.message.reciever =  this.chosenReciever as string;
    this.message.sender = this.chosenSender as string;
    this.message.performative = this.chosenPerformative as string;

    this.http.post(endpoint,this.message,{responseType : "text"}).subscribe(response => {
      console.log(response);
    },err => {
      console.log(err); 
    })

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
  reciever : string;
  content : string;
  sender : string;
}