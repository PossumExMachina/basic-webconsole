
import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Container } from './container.model';
import SockJS from 'sockjs-client';
import * as Stomp from 'webstomp-client';

@Component({
  selector: 'app-docker-table',
  templateUrl: './docker-table.component.html',
  styleUrls: ['./docker-table.component.css']
})
export class DockerTableComponent implements OnInit, OnDestroy {
  dockerContainers: Container[] = [];
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
    const socket = new SockJS('http://localhost:8085/dockerUpdates');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe('/topic/containers', (message: { body: string; }) => {
        if (message.body) {
          this.dockerContainers = JSON.parse(message.body);
        }
      });
    });
  }

  fetchData() {
    this.http.get<Container[]>('http://localhost:8085/dockerInfo').subscribe(data => {
      this.dockerContainers = data;
    });
  }
}
