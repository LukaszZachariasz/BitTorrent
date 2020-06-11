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

  constructor(private clientListService: ClientListService) {
  }

  ngOnInit() {
    this.initClientListRefreshing();
  }

  setSelectedClient(client: ClientInterface) {
    this.selectClientEmitterToManagement.emit(client);
  }

  private initClientListRefreshing() {
    setInterval(() => this.connectedClients = this.clientListService.getUpdatedClientList(), 5000);
  }

}
