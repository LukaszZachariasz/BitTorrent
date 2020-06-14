import {Component, Pipe, PipeTransform} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {ClientInterface} from '../../../models/client/client.interface';
import {ClientFileService} from '../../../services/client-file.service';
import {filter, tap} from 'rxjs/operators';
import {ClientFilesInfoListInterface} from '../../../models/client/client-files-info-list.interface';
import {PartContentStatusWithSourceClientIp} from '../../../models/client/part-content-status-with-source-client-ip';
import {PartContentStatus} from '../../../constants/part-content-status.enum';
import {ConfigConstants} from '../../../constants/config-constants';
import {FileExistenceStatus} from '../../../constants/file-status.enum';

@Pipe({name: 'keys', pure: false})
export class KeysPipe implements PipeTransform {
  transform(value: any, args?: any[]): any[] {
    if (value) {
      const dataArr = [];
      Object.keys(value).forEach((key: any) => {
        dataArr.push(value[key]);
      });
      return dataArr;
    }
  }
}

@Component({
  selector: 'app-client-file-list',
  templateUrl: './client-file-list.component.html',
  styleUrls: ['./client-file-list.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ClientFileListComponent {

  columnsToDisplay = ['action', 'fileExistenceStatus', 'fileSize', 'humanName', 'torrentId', 'downloadProgress'];
  expandedElement: null;
  selectedClient: ClientInterface;
  clientFilesDataSource: ClientFilesInfoListInterface[] = [];
  refreshListTimer;

  constructor(private clientFileService: ClientFileService) {
  }

  onChangeSelectedClient(client: ClientInterface) {
    this.selectedClient = client;
    this.updateFileList();
    if (this.refreshListTimer) {
      clearInterval(this.refreshListTimer);
    }
    this.refreshListTimer = setInterval(() => {
      this.updateFileList();
    }, ConfigConstants.REFRESH_FILES_INTERVAL);
  }

  getArrayPartWithStatuses(element: any): PartContentStatusWithSourceClientIp[] {
    return [this.clientFilesDataSource.find(el => el === element).partIdToPartContentWithClientSourceIp][0];
  }

  getDownloadFileProgress(file: ClientFilesInfoListInterface): number {
    let existingPartsNumber = 0;
    if (file) {
      const parts: PartContentStatusWithSourceClientIp[] = file.partIdToPartContentWithClientSourceIp;

      for (let i = 0; i < Object.keys(parts).length; i++) {
        if (PartContentStatus.EXISTING === parts[i].partContentStatus) {
          existingPartsNumber++;
        }
      }
      return existingPartsNumber > 0 ? (existingPartsNumber / Object.keys(parts).length) * 100 : existingPartsNumber;
    }
    return existingPartsNumber;
  }

  private updateFileList() {
    this.clientFileService.getClientFiles(this.selectedClient)
      .pipe(
        filter((res: ClientFilesInfoListInterface[]) => !!res),
        tap((res) => console.log(res)),
        tap((res: ClientFilesInfoListInterface[]) => this.clientFilesDataSource = res)
      )
      .subscribe();
  }

  changeFileAccess(event, file: ClientFilesInfoListInterface) {
    event.stopPropagation();
    if (FileExistenceStatus.NON_EXISTING !== file.fileExistenceStatus) {
      this.clientFileService.removeFileForClient(this.selectedClient, file)
        .pipe(
          filter(res => !res),
          tap(res => console.log(res))
        )
        .subscribe();
    } else {
      this.clientFileService.restoreFileForClient(this.selectedClient, file)
        .pipe(
          filter(res => !res),
          tap(res => console.log(res))
        )
        .subscribe();
    }
  }
}
