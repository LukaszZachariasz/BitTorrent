import {Component, OnInit, ViewChild} from '@angular/core';
import {ClientInterface} from '../../models/client/client.interface';
import {ClientComponent} from './client/client.component';

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrls: ['./client-management.component.css']
})
export class ClientManagementComponent implements OnInit {

  @ViewChild(ClientComponent) clientPanelComponent: ClientComponent;

  private selectedClient: ClientInterface;

  constructor() {
  }

  ngOnInit() {
  }

  selectedClientEvent(client: ClientInterface) {
    this.selectedClient = client;
    this.clientPanelComponent.onLoadSelectedClient(client);
  }

}
