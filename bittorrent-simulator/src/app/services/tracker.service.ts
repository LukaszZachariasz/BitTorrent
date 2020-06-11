import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TrackerService {

  constructor(private httpClient: HttpClient) {

  }

}
