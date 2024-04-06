import { Component } from '@angular/core';
import {Container} from "../docker-table/container.model";
import {HttpClient} from "@angular/common/http";
import SockJS from "sockjs-client";
import * as Stomp from "webstomp-client";
import {DiskUsage} from "./diskUsage.model";

@Component({
  selector: 'app-server-overview',
  templateUrl: './server-overview.component.html',
  styleUrl: './server-overview.component.css'
})
export class ServerOverviewComponent {
  diskUsage: DiskUsage[] = [];
  private stompClient: any;

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.fetchData()
    this.connectWebSocket();
  }

  ngOnDestroy() {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  connectWebSocket() {
    const socket = new SockJS('http://localhost:8085/diskInfo');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/disk', (message: { body: string; }) => {
        if (message.body) {
          this.diskUsage = JSON.parse(message.body);
        }
      });
    });
  }

  fetchData() {
    this.http.get<DiskUsage[]>('http://localhost:8085/diskInfo').subscribe(data => {
      this.diskUsage = data;
    });
  }
}
