import {Injectable} from '@angular/core';
import {RegisterFileInterface} from '../models/client/register-file.interface';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ClientInterface} from '../models/client/client.interface';
import {TorrentFile} from '../models/client/torrent-file';

@Injectable({
  providedIn: 'root'
})
export class ClientFileService {

  constructor(private httpClient: HttpClient) {
  }

  register(fileData: RegisterFileInterface, client: ClientInterface): Observable<any> {
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
    };
    return this.httpClient.post(client.clientIp + '/create', JSON.stringify(fileData), options);
  }


  downloadByTorrentFile(torrentFile: TorrentFile, client: ClientInterface) {
    const options = {
      headers: new HttpHeaders()
        .set('Content-Type', 'application/json')
    };
    return this.httpClient.post(client.clientIp + '/downloadFile', JSON.stringify(torrentFile), options);
  }

  downloadTorrentFile(torrentFile) {
    const blob = new Blob(
      [JSON.stringify(torrentFile)],
      {type: 'application/octet-stream'}
    );
    const fileUrl = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');
    anchor.download = this.createTorrentFileName(torrentFile);
    anchor.href = fileUrl;
    anchor.click();
  }

  private createTorrentFileName(torrentFile: TorrentFile) {
    return torrentFile.humanName + '_' + torrentFile.fileId + '.torrent';
  }


}
