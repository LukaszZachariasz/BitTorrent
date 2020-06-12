export interface TorrentFile {
  fileId: string;
  humanName: string;
  pieceNumbers: number;
  trackerIps: string[];
}
