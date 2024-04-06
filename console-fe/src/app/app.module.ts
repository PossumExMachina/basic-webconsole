import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DockerTableComponent } from './docker-table/docker-table.component';
import { HeaderComponent } from './header/header.component';
import { TomcatOverviewComponent } from './tomcat-overview/tomcat-overview.component';
import { ServerOverviewComponent } from './server-overview/server-overview.component';


@NgModule({
  declarations: [
    AppComponent,
    DockerTableComponent,
    HeaderComponent,
    TomcatOverviewComponent,
    ServerOverviewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
