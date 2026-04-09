package com.example.controller;

import com.example.model.Cloud;
import com.example.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clouds")
public class CloudController {

    @Autowired
    private CloudService cloudService;

    @GetMapping
    public List<Cloud> getAllClouds() {
        return cloudService.getAllClouds();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cloud> getCloudById(@PathVariable String id) {
        return cloudService.getCloudById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cloud addCloud(@RequestBody Cloud cloud) {
        return cloudService.addCloud(cloud);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cloud> updateCloud(@PathVariable String id, @RequestBody Cloud cloud) {
        Cloud updated = cloudService.updateCloud(id, cloud);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<Cloud> startVM(@PathVariable String id) {
        Cloud cloud = cloudService.startVM(id);
        return cloud != null ? ResponseEntity.ok(cloud) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/stop")
    public ResponseEntity<Cloud> stopVM(@PathVariable String id) {
        Cloud cloud = cloudService.stopVM(id);
        return cloud != null ? ResponseEntity.ok(cloud) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/storage/{gb}")
    public ResponseEntity<Cloud> addStorage(@PathVariable String id, @PathVariable Integer gb) {
        Cloud cloud = cloudService.addStorage(id, gb);
        return cloud != null ? ResponseEntity.ok(cloud) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/reset")
    public ResponseEntity<Cloud> resetCloud(@PathVariable String id) {
        Cloud cloud = cloudService.resetCloud(id);
        return cloud != null ? ResponseEntity.ok(cloud) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCloud(@PathVariable String id) {
        cloudService.deleteCloud(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllClouds() {
        cloudService.deleteAllClouds();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {
        return cloudService.getAnalytics();
    }

    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() {
        String csv = cloudService.exportToCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cloud_dashboard_export.csv");
        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }

    @GetMapping("/status-count")
    public Map<String, Long> getStatusCount() {
        return cloudService.getStatusCount();
    }
}
