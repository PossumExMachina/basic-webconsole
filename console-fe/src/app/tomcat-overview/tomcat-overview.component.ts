import { Component } from '@angular/core';
import {Container} from "../docker-table/container.model";
import {HttpClient} from "@angular/common/http";
import SockJS from "sockjs-client";
import * as Stomp from "webstomp-client";

@Component({
  selector: 'app-tomcat-overview',
  templateUrl: './tomcat-overview.component.html',
  styleUrl: './tomcat-overview.component.css'
})
export class TomcatOverviewComponent {
  // applications: Container[] = [];
  // private stompClient: any;
  //
  // constructor(private http: HttpClient) {}
  //
  // ngOnInit() {
  //   this.fetchData()
  //   this.connectWebSocket();
  // }
  //
  // ngOnDestroy() {
  //   if (this.stompClient) {
  //     this.stompClient.disconnect();
  //   }
  // }
  //
  // connectWebSocket() {
  //   const socket = new SockJS('http://localhost:8085/dockerUpdates');
  //   this.stompClient = Stomp.over(socket);
  //   this.stompClient.connect({}, () => {
  //     this.stompClient.subscribe('/topic/containers', (message: { body: string; }) => {
  //       if (message.body) {
  //         this.dockerContainers = JSON.parse(message.body);
  //       }
  //     });
  //   });
  // }
  //
  // fetchData() {
  //   this.http.get<Container[]>('http://localhost:8085/dockerInfo').subscribe(data => {
  //     this.dockerContainers = data;
  //   });
  // }
}
