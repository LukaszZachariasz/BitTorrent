import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {ComponentType} from '@angular/cdk/overlay';

@Injectable({
  providedIn: 'root'
})
export class ModalBuilderService {

  private DIALOG_SIZE = '500px';

  constructor(public matDialog: MatDialog) {
  }

  build(modalComponent: ComponentType<any>, data: any) {
    return this.matDialog.open(modalComponent, {
      width: data && data.width ? data.width : this.DIALOG_SIZE,
      data: data ? data : null
    });
  }

}
