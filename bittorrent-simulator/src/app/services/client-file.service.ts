import {Injectable} from '@angular/core';
import {RegisterFileInterface} from '../models/client/register-file.interface';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ClientInterface} from '../models/client/client.interface';
import {TorrentFileInterface} from '../models/client/torrent-file.interface';
import {ClientFilesInfoListInterface} from '../models/client/client-files-info-list.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientFileService {

  constructor(private httpClient: HttpClient) {
  }

  static createTorrentFileName(torrentFile: TorrentFileInterface) {
    return torrentFile.humanName + '_' + torrentFile.fileId + '.torrent';
  }

  register(fileData: RegisterFileInterface, client: ClientInterface): Observable<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post(client.clientIp + '/create', JSON.stringify(fileData), {
      headers,
      responseType: 'json'
    });
  }

  getClientFiles(selectedClient: ClientInterface) {
    return this.httpClient.get(selectedClient.clientIp + '/allPartIdWithStatuses');
  }

  downloadByTorrentFile(torrentFile: TorrentFileInterface, client: ClientInterface) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.post(client.clientIp + '/downloadFile', JSON.stringify(torrentFile), {
      headers,
      responseType: 'text'
    });
  }

  downloadTorrentFile(torrentFile) {
    const blob = new Blob(
      [JSON.stringify(torrentFile)],
      {type: 'application/octet-stream'}
    );
    const fileUrl = window.URL.createObjectURL(blob);
    const anchor = document.createElement('a');
    anchor.download = ClientFileService.createTorrentFileName(torrentFile);
    anchor.href = fileUrl;
    anchor.click();
  }

  removeFileForClient(client: ClientInterface, file: ClientFilesInfoListInterface) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.delete(client.clientIp + '/' + file.torrentId, {
      headers,
      responseType: 'text'
    });
  }

  restoreFileForClient(client: ClientInterface, file: ClientFilesInfoListInterface) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    return this.httpClient.get(client.clientIp + '/restoreFile/' + file.torrentId, {
      headers,
      responseType: 'text'
    });
  }

}
