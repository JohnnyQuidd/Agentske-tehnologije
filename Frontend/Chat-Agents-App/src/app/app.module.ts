import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AgentClassesComponent } from './agent-classes/agent-classes.component';
import { ActionsComponent } from './actions/actions.component';
import { RunningAgentsComponent } from './running-agents/running-agents.component';

@NgModule({
  declarations: [
    AppComponent,
    AgentClassesComponent,
    ActionsComponent,
    RunningAgentsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
