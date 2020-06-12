import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-download-file-modal',
  templateUrl: './download-file-modal.component.html',
  styleUrls: ['./download-file-modal.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DownloadFileModalComponent implements OnInit {

  downloadFileForm: FormGroup;
  private fileString: string | ArrayBuffer;

  constructor(public dialogRef: MatDialogRef<DownloadFileModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
    this.downloadFileForm = new FormGroup({
      file: new FormControl({
        value: '',
        disabled: true
      }, {
        validators: Validators.required
      })
    });
  }

  onConfirm() {
    this.dialogRef.close(JSON.parse(this.fileString.toString()));
  }


  onChangeFile(file) {
    let fileContent = file.target.files[0];
    this.downloadFileForm.controls.file.setValue(file.target.files[0].name);
    const fileReader = new FileReader();
    fileReader.readAsText(fileContent);
    fileReader.onloadend = (e) => {
      console.log(fileReader.result);
      this.fileString = fileReader.result;
    };
  }

  isDownloadPossible(): boolean {
    return this.downloadFileForm.getRawValue().file.length > 0;
  }
}
