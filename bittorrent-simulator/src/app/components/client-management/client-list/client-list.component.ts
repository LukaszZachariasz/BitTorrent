import {Component, OnInit} from '@angular/core';
import {ClientInterface} from '../../../models/client/client.interface';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  connectedClients: ClientInterface[] = [];

  constructor() {
  }

  ngOnInit() {
  }

}
