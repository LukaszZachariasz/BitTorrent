import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ClientInterface} from '../models/client/client.interface';
import {TrackerUrl} from '../constants/tracker-url.enum';

@Injectable({
  providedIn: 'root'
})
export class TrackerService {

  constructor(private httpClient: HttpClient) {
  }

  getAllRegisteredFiles(client: ClientInterface) {
    const httpParams = new HttpParams();
    httpParams.append('clientId', client.clientIp);
    return this.httpClient.get(TrackerUrl.Tracker1 + '/allRegisteredFiles', {params: httpParams});
  }

}
