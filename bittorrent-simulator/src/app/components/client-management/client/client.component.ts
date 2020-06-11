import {Component, OnInit} from '@angular/core';
import {ClientInterface} from '../../../models/client/client.interface';

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {

  selectedClient: ClientInterface;

  constructor() {

  }

  ngOnInit() {
  }

  onLoadSelectedClient(client: ClientInterface) {
    this.selectedClient = client;
  }
}
