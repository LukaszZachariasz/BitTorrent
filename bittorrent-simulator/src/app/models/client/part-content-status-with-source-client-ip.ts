import {PartContentStatus} from '../../constants/part-content-status.enum';
import {ClientUrl} from '../../constants/urls/client-url.enum';

export interface PartContentStatusWithSourceClientIp {
  partContentStatus: PartContentStatus;
  sourceClientIp: ClientUrl;
}
