import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-register-file-modal',
  templateUrl: './register-file-modal.component.html',
  styleUrls: ['./register-file-modal.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterFileModalComponent implements OnInit {

  registerFileForm: FormGroup;

  constructor(public dialogRef: MatDialogRef<RegisterFileModalComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit(): void {
    this.registerFileForm = new FormGroup({
      humanFileName: new FormControl(
        '',
        Validators.required
      ),
      content: new FormControl(
        '',
        Validators.required
      )
    });
  }

  onConfirm() {
    console.log(this.registerFileForm.getRawValue().humanFileName, this.registerFileForm.getRawValue().content);
    this.dialogRef.close({
      humanName: this.registerFileForm.getRawValue().humanFileName,
      value: this.registerFileForm.getRawValue().content.split(',')
    });
  }
}
