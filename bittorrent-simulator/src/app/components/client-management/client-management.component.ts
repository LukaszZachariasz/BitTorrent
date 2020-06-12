import {Component, OnInit, ViewChild} from '@angular/core';
import {ClientInterface} from '../../models/client/client.interface';
import {ClientPanelComponent} from './client-panel/client-panel.component';
import {ClientFileListComponent} from './client-file-list/client-file-list.component';

@Component({
  selector: 'app-client-management',
  templateUrl: './client-management.component.html',
  styleUrls: ['./client-management.component.css']
})
export class ClientManagementComponent implements OnInit {

  @ViewChild(ClientPanelComponent) clientPanelComponent: ClientPanelComponent;
  @ViewChild(ClientFileListComponent) clientFileListComponent: ClientFileListComponent;

  private selectedClient: ClientInterface;

  constructor() {
  }

  ngOnInit() {
  }

  selectedClientEvent(client: ClientInterface) {
    this.selectedClient = client;
    this.clientPanelComponent.onChangeSelectedClient(client);
    this.clientFileListComponent.onChangeSelectedClient(client);
  }
}
