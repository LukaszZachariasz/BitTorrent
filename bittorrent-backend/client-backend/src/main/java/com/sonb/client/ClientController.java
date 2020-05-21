package com.sonb.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.PartIdWithPartStatus;
import util.Torrent;

import java.util.List;

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

    @DeleteMapping("{fileId}/{trackerId}")
    ResponseEntity<?> removeFileFromClient(@PathVariable String fileId,
                                           @PathVariable String trackerId) {
        clientService.removeFileFromClient(fileId, trackerId);
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

}

