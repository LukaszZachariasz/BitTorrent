import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ClientInterface} from '../models/client/client.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientControlBehaviourService {

  constructor(private httpClient: HttpClient) {
  }

  setNewDelayValue(newValue: number, client: ClientInterface) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json', 'No-Auth': 'True'});
    return this.httpClient.get(client.clientIp + '/changeSleep/' + newValue.toString(), {
      headers,
      responseType: 'text'
    });
  }

  getDelayValue(client: ClientInterface) {
    const headers: HttpHeaders = new HttpHeaders({'Content-Type': 'application/json', 'No-Auth': 'True'});
    return this.httpClient.get(client.clientIp + '/sleep', {
      headers,
      responseType: 'text'
    });
  }
}
