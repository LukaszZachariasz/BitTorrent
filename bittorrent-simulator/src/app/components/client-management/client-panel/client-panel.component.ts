import {Component, OnInit} from '@angular/core';
import {ClientInterface} from '../../../models/client/client.interface';
import {ModalBuilderService} from '../../../shared/services/modal-builder.service';
import {RegisterFileModalComponent} from '../register-file-modal/register-file-modal.component';
import {filter, mergeMap, tap} from 'rxjs/operators';
import {RegisterFileInterface} from '../../../models/client/register-file.interface';
import {ClientFileService} from '../../../services/client-file.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TorrentFile} from '../../../models/client/torrent-file';
import {DownloadFileModalComponent} from '../download-file/download-file-modal.component';

@Component({
  selector: 'app-client-panel',
  templateUrl: './client-panel.component.html',
  styleUrls: ['./client-panel.component.css']
})
export class ClientPanelComponent implements OnInit {

  selectedClient: ClientInterface;

  constructor(private matSnackBar: MatSnackBar,
              private modalBuilder: ModalBuilderService,
              private clientFileService: ClientFileService) {
  }

  ngOnInit() {
  }

  onLoadSelectedClient(client: ClientInterface) {
    this.selectedClient = client;
  }

  onRegisterFile() {
    this.modalBuilder.build(RegisterFileModalComponent, {
        selectedClient: this.selectedClient
      }
    ).afterClosed()
      .pipe(
        filter((fileData: RegisterFileInterface) => !!fileData),
        mergeMap((fileData: RegisterFileInterface) => {
          return this.clientFileService.register(fileData, this.selectedClient);
        }),
        tap(() => this.matSnackHandler('Torrent registered')),
        tap((torrentFile: TorrentFile) => this.clientFileService.downloadTorrentFile(torrentFile))
      )
      .subscribe(); // TODO: refreshing list of registered files maybe?
  }

  onDownloadByTorrent() {
    this.modalBuilder.build(DownloadFileModalComponent, null)
      .afterClosed()
      .pipe(
        filter((torrentFile: TorrentFile) => !!torrentFile),
        tap((torrentFile: TorrentFile) => {
          this.clientFileService.downloadByTorrentFile(torrentFile, this.selectedClient);
        }),
        tap(() => this.matSnackHandler('Download Started'))
      )
      .subscribe();
  }

  private matSnackHandler(message: string) {
    this.matSnackBar.open(message, 'DISMISS', {duration: 3000});
  }
}
