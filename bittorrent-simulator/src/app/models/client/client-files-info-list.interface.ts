import {FileExistenceStatus} from '../../constants/file-status.enum';
import {PartContentStatusWithSourceClientIp} from './part-content-status-with-source-client-ip';

export interface ClientFilesInfoListInterface {
  fileExistenceStatus: FileExistenceStatus;
  fileSize: number;
  humanName: string;
  partIdToPartContentWithClientSourceIp: PartContentStatusWithSourceClientIp[];
}
