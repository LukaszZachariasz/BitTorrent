import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ClientInterface} from '../../../models/client/client.interface';
import {ClientListService} from '../../../services/client-list.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  @Output() selectClientEmitterToManagement: EventEmitter<ClientInterface> = new EventEmitter<ClientInterface>();

  connectedClients: ClientInterface[] = [];

  refreshListBlock = true;

  constructor(private clientListService: ClientListService) {
  }

  ngOnInit() {
    this.refreshList();
  }

  setSelectedClient(client: ClientInterface) {
    this.selectClientEmitterToManagement.emit(client);
  }

  refreshList() {
    if (this.refreshListBlock) {
      this.refreshListBlock = !this.refreshListBlock;
      this.connectedClients = this.clientListService.getUpdatedClientList();
    }
  }

  unlockRefresh() {
    this.refreshListBlock = !this.refreshListBlock;
  }

}
