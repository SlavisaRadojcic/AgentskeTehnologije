import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery/dist/jquery.min.js';
import { WebsocketService } from './services/websocket.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'app';

  constructor(private ws:WebsocketService){ }
  ngOnInit(){
		this.ws.connect();
	}
}
