import {Component, Pipe, PipeTransform} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {ClientInterface} from '../../../models/client/client.interface';
import {ClientFileService} from '../../../services/client-file.service';
import {filter, tap} from 'rxjs/operators';
import {ClientFilesInfoListInterface} from '../../../models/client/client-files-info-list.interface';
import {PartContentStatusWithSourceClientIp} from '../../../models/client/part-content-status-with-source-client-ip';

@Pipe({name: 'keys', pure: false})
export class KeysPipe implements PipeTransform {
  transform(value: any, args?: any[]): any[] {
    if (value) {
      const keyArr: any[] = Object.keys(value),
        dataArr = [];
      keyArr.forEach((key: any) => {
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

  columnsToDisplay = ['fileExistenceStatus', 'fileSize', 'humanName'];
  expandedElement: null;
  selectedClient: ClientInterface;
  clientFilesDataSource: ClientFilesInfoListInterface[] = [];

  constructor(private clientFileService: ClientFileService) {
  }

  onChangeSelectedClient(client: ClientInterface) {
    this.selectedClient = client;
    this.clientFileService.getClientFiles(this.selectedClient)
      .pipe(
        filter((res: ClientFilesInfoListInterface[]) => !!res),
        tap((res: ClientFilesInfoListInterface[]) => this.clientFilesDataSource = res)
      )
      .subscribe();
  }

  getArrayPartWithStatuses(element: any): PartContentStatusWithSourceClientIp[] {
    return [this.clientFilesDataSource.find(el => el === element).partIdToPartContentWithClientSourceIp][0];
  }
}
