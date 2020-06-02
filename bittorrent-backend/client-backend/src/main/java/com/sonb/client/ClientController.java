package com.sonb.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.PartIdWithPartStatus;
import util.Torrent;

import java.util.List;

/**
 * @DocumentationNote: Some important information:
 * 1. About Re-downloading:
 * Make loop (for example 10 times) when flag is set, where new list of clients is updated from tackers
 * 2. Try download again status to file when for example: Client1 is Owner of file, Client2, Client3 was started
 * downloading of this file, but during transfer file Client1 was removed from owner list of file at tracker.
 * When Client1 will appear again the Client2 and Client3 will be continue of downloading
 * 3. When Client will be trying of download file from other Client and will get response that this client is no owner
 * of file, then client will be removed from tracker list as owner.
 */

/**
 * @author Łukasz Zachariasz
 */

@RestController
@RequestMapping
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("create")
    ResponseEntity<Torrent> createTorrent(@RequestBody ClientFileRequest clientFileRequest) {
        Torrent torrent = clientService.createTorrent(clientFileRequest);
        return ResponseEntity.ok(torrent);
    }

    @DeleteMapping("{fileId}")
    ResponseEntity<?> removeFileFromClient(@PathVariable String fileId,
                                           @RequestParam Integer trackerIp) {
        clientService.removeFileFromClient(fileId, trackerIp);
        return ResponseEntity.ok("Removed");
    }

    @GetMapping("{fileId}/{partId}")
    String downloadPart(@PathVariable String fileId,
                        @PathVariable int partId) {
        return clientService.downloadPart(fileId, partId);
    }

    @GetMapping("partIdWithStatuses/{fileId}")
    List<PartIdWithPartStatus> partIdWithStatuses(@PathVariable String fileId) {
        return clientService.partIdWithStatuses(fileId);
    }

    @GetMapping("sleep")
    Integer getSleepValue() {
        return clientService.getSimulatedDownloadDelay();
    }

    @GetMapping("changeSleep/{sleepValue}")
    ResponseEntity<?> postSleepValue(@PathVariable Integer sleepValue) {
        clientService.setSimulatedDownloadDelay(sleepValue);
        return ResponseEntity.ok("Changed sleep value to:" + sleepValue);
    }

    @PostMapping("downloadFile")
    ResponseEntity<?> downloadFile(@RequestBody Torrent torrent) {
        clientService.downloadFile(torrent);
        return ResponseEntity.ok("Download started");
    }

    @GetMapping("allPartIdWithStatuses")
    List<?> allPartIdWithStatuses() {
        return clientService.allPartIdWithStatuses();
    }

    @GetMapping("allClientHaveFullFileToTrue")
    ResponseEntity<?> allClientHaveFullFileToTrue() {
        clientService.setAllClientHaveFullFile(true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("allClientHaveFullFileToFalse")
    ResponseEntity<?> allClientHaveFullFileToFalse() {
        clientService.setAllClientHaveFullFile(false);
        return ResponseEntity.ok().build();
    }
}

