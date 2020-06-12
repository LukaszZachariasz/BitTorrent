import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ClientUrl} from '../constants/urls/client-url.enum';
import {catchError, filter, tap} from 'rxjs/operators';
import {ClientInterface} from '../models/client/client.interface';
import {of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientListService {

  constructor(private httpClient: HttpClient) {
  }

  getUpdatedClientList(): ClientInterface[] {
    const connectedClients: ClientInterface[] = [];

    for (const client in ClientUrl) {
      this.httpClient.get(ClientUrl[client] + '/activeClient', {})
        .pipe(
          filter(response => !!response),
          tap(() => connectedClients.push({
            clientIp: ClientUrl[client],
            clientName: client
          })),
          catchError(_ => of('Error'))
        ).subscribe();
    }
    return connectedClients;
  }

  private getClientUrl(clientNumber: number) {
    return ClientUrl[clientNumber];
  }

}
