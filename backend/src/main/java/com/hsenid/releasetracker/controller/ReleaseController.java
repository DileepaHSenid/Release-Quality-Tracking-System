package com.hsenid.releasetracker.controller;

import com.hsenid.releasetracker.model.Release;
import com.hsenid.releasetracker.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/releases")
public class ReleaseController {

    @Autowired
    private ReleaseService releaseService;

    @GetMapping
    public ResponseEntity<List<Release>> getReleases() {
        List<Release> releases = releaseService.fetchAllReleases();
        return ResponseEntity.ok(releases);
    }

    @PostMapping("/add")
    public ResponseEntity<Release> addRelease(@RequestParam String productId, @RequestBody Release release) {
        Release createdRelease = releaseService.addReleaseDetails(release, productId);
        return new ResponseEntity<>(createdRelease, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Release> getReleaseById(@PathVariable String id) {
        Release release = releaseService.fetchUsingId(id);
        return ResponseEntity.ok(release);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRelease(@PathVariable String id) {
        releaseService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
