package com.sonb.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.Torrent;

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

    @PostMapping("register")
    ResponseEntity<?> registerTorrent(@RequestBody Torrent torrent,
                                      @RequestParam String clientIp) {
        trackerService.registerTorrent(torrent, clientIp);
        return ResponseEntity.ok("TEST_OK_REGISTER");
    }

    @GetMapping("clients/{fileId}")
    List<String> getClients(@PathVariable String fileId) {
        return trackerService.getClients(fileId);
    }
}
