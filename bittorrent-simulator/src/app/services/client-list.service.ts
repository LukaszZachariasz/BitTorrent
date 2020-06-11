import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ClientUrl} from '../constants/client-url.enum';
import {filter} from 'rxjs/operators';
import {ClientInterface} from '../models/client/client.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientListService {

  private clientUrls: ClientUrl;

  constructor(private httpClient: HttpClient) {
  }

  getUpdatedClientList(): ClientInterface[] {
    const connectedClients: ClientInterface[] = [];

    for (const client in ClientUrl) {
      this.httpClient.get(ClientUrl[client] + '/activeClient', {}).pipe(
        filter(response => !!response)
      ).subscribe(() => connectedClients.push({
        clientIp: ClientUrl[client],
        clientName: client
      }));
    }
    return connectedClients;
  }

  private getClientUrl(clientNumber: number) {
    return ClientUrl[clientNumber];
  }

}
