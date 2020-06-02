package com.sonb.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.RegisterTorrentRq;

import java.util.List;

/**
 * @author Łukasz Zachariasz
 */

@RestController
@RequestMapping
public class TrackerController {

    private final TrackerService trackerService;

    @Autowired
    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    @PostMapping("registerTorrent")
    ResponseEntity<?> registerTorrent(@RequestBody RegisterTorrentRq registerTorrentRq) {
        trackerService.registerTorrent(registerTorrentRq);
        return ResponseEntity.ok("TEST_OK_REGISTER");
    }

    @DeleteMapping("remove/{fileId}")
    ResponseEntity<?> removeFileFromClient(@PathVariable String fileId, @RequestParam String clientIp) {
        trackerService.removeFileFromClient(fileId, clientIp);

        return ResponseEntity.ok().build();
    }

    @GetMapping("clients/{fileId}")
    List<String> getClients(@PathVariable String fileId) {
        return trackerService.getClients(fileId);
    }

    @GetMapping("registerFileOwner/{fileId}")
    public ResponseEntity<?> registerFileOwnerClient(@PathVariable String fileId, @RequestParam String clientIp) {
        trackerService.registerFileOwnerClient(fileId, clientIp);
        return ResponseEntity.ok("Registered");
    }
}
