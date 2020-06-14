import {Component, OnInit} from '@angular/core';
import {ClientInterface} from '../../../models/client/client.interface';
import {ModalBuilderService} from '../../../shared/services/modal-builder.service';
import {RegisterFileModalComponent} from '../register-file-modal/register-file-modal.component';
import {filter, mergeMap, tap} from 'rxjs/operators';
import {RegisterFileInterface} from '../../../models/client/register-file.interface';
import {ClientFileService} from '../../../services/client-file.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {TorrentFileInterface} from '../../../models/client/torrent-file.interface';
import {DownloadFileModalComponent} from '../download-file/download-file-modal.component';
import {ConfigConstants} from '../../../constants/config-constants';
import {MatSliderChange} from '@angular/material/slider';
import {ClientControlBehaviourService} from '../../../services/client-control-behaviour.service';

@Component({
  selector: 'app-client-panel',
  templateUrl: './client-panel.component.html',
  styleUrls: ['./client-panel.component.css']
})
export class ClientPanelComponent implements OnInit {

  selectedClient: ClientInterface;
  simulatedDownloadPartDelay: number;

  constructor(private matSnackBar: MatSnackBar,
              private modalBuilder: ModalBuilderService,
              private clientFileService: ClientFileService,
              private clientControlBehaviourService: ClientControlBehaviourService) {
  }

  ngOnInit() {
  }

  onChangeSelectedClient(client: ClientInterface) {
    this.selectedClient = client;
    this.getDelayValue();
  }

  formatLabel(value: number) {
    return value + 's';
  }

  onRegisterFile() {
    this.modalBuilder.build(RegisterFileModalComponent, {
      selectedClient: this.selectedClient
    }).afterClosed()
      .pipe(
        filter((fileData: RegisterFileInterface) => !!fileData),
        mergeMap((fileData: RegisterFileInterface) => {
          return this.clientFileService.register(fileData, this.selectedClient);
        }),
        tap(() => this.matSnackHandler('Torrent registered')),
        tap((torrentFile: TorrentFileInterface) => this.clientFileService.downloadTorrentFile(torrentFile))
      )
      .subscribe();
  }

  onDownloadByTorrent() {
    this.modalBuilder.build(DownloadFileModalComponent, null)
      .afterClosed()
      .pipe(
        filter((torrentFile: TorrentFileInterface) => !!torrentFile),
        mergeMap((torrentFile: TorrentFileInterface) => {
          return this.clientFileService.downloadByTorrentFile(torrentFile, this.selectedClient)
            .pipe(
              tap(() => this.matSnackHandler('Download Started'), () => this.matSnackHandler('Torrent Error'))
            );
        })
      )
      .subscribe();
  }

  setNewDelayValue(event: MatSliderChange) {
    this.clientControlBehaviourService
      .setNewDelayValue(event.value * 1000, this.selectedClient)
      .subscribe();
  }

  private getDelayValue() {
    this.clientControlBehaviourService.getDelayValue(this.selectedClient)
      .pipe(
        tap((res: any) => this.simulatedDownloadPartDelay = (res / 1000))
      )
      .subscribe();
  }

  private matSnackHandler(message: string) {
    this.matSnackBar.open(message, 'DISMISS', {duration: ConfigConstants.TIME_OF_POPUP});
  }
}
