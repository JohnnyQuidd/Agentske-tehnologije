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
  constructor(private http: HttpClient) { 
    this.fetchPerformatives();
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

}

export interface Performative {
  performative: string;
};