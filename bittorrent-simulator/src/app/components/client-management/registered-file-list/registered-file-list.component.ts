import {Component, Input, OnInit} from '@angular/core';
import {TrackerService} from '../../../services/tracker.service';
import {ClientInterface} from '../../../models/client/client.interface';
import {filter, tap} from 'rxjs/operators';

@Component({
  selector: 'app-registered-file-list',
  templateUrl: './registered-file-list.component.html',
  styleUrls: ['./registered-file-list.component.css']
})
export class RegisteredFileListComponent implements OnInit {

  @Input() selectedClient: ClientInterface;

  registeredFileList;

  constructor(private trackerService: TrackerService) {
  }

  ngOnInit() {
    // this.initClientFilesRefreshing();
  }

  private initClientFilesRefreshing() {
    setInterval(() => this.fetchRegisteredFileList(), 5000);
  }

  private fetchRegisteredFileList() {
    this.trackerService.getAllRegisteredFiles(this.selectedClient)
      .pipe(
        filter(fileInfo => !!fileInfo),
        tap(res => console.log(res, this.selectedClient)),
      )
      .subscribe();
  }
}
