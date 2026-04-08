package com.example.controller;

import com.example.model.Cloud;
import com.example.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/clouds")
public class CloudController {
    
    @Autowired
    private CloudService cloudService;
    
    // Get all clouds
    @GetMapping
    public List<Cloud> getAllClouds() {
        return cloudService.getAllClouds();
    }
    
    // Get cloud by ID
    @GetMapping("/{id}")
    public ResponseEntity<Cloud> getCloudById(@PathVariable Long id) {
        return cloudService.getCloudById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Add new cloud
    @PostMapping
    public Cloud addCloud(@RequestBody Cloud cloud) {
        return cloudService.addCloud(cloud);
    }
    
    // Update cloud (EDIT feature)
    @PutMapping("/{id}")
    public ResponseEntity<Cloud> updateCloud(@PathVariable Long id, @RequestBody Cloud cloud) {
        Cloud updatedCloud = cloudService.updateCloud(id, cloud);
        if (updatedCloud != null) {
            return ResponseEntity.ok(updatedCloud);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Start VM
    @PutMapping("/{id}/start")
    public ResponseEntity<Cloud> startVM(@PathVariable Long id) {
        Cloud cloud = cloudService.startVM(id);
        if (cloud != null) {
            return ResponseEntity.ok(cloud);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Stop VM
    @PutMapping("/{id}/stop")
    public ResponseEntity<Cloud> stopVM(@PathVariable Long id) {
        Cloud cloud = cloudService.stopVM(id);
        if (cloud != null) {
            return ResponseEntity.ok(cloud);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Add storage
    @PutMapping("/{id}/storage/{gb}")
    public ResponseEntity<Cloud> addStorage(@PathVariable Long id, @PathVariable Integer gb) {
        Cloud cloud = cloudService.addStorage(id, gb);
        if (cloud != null) {
            return ResponseEntity.ok(cloud);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Reset cloud
    @PutMapping("/{id}/reset")
    public ResponseEntity<Cloud> resetCloud(@PathVariable Long id) {
        Cloud cloud = cloudService.resetCloud(id);
        if (cloud != null) {
            return ResponseEntity.ok(cloud);
        }
        return ResponseEntity.notFound().build();
    }
    
    // Delete cloud
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCloud(@PathVariable Long id) {
        cloudService.deleteCloud(id);
        return ResponseEntity.noContent().build();
    }
    
    // Delete all clouds
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllClouds() {
        cloudService.deleteAllClouds();
        return ResponseEntity.noContent().build();
    }
    
    // Get analytics
    @GetMapping("/analytics")
    public Map<String, Object> getAnalytics() {
        return cloudService.getAnalytics();
    }
    
    // Export to CSV
    @GetMapping("/export")
    public ResponseEntity<String> exportToCSV() {
        String csv = cloudService.exportToCSV();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cloud_dashboard_export.csv");
        return new ResponseEntity<>(csv, headers, HttpStatus.OK);
    }
    
    // Get status counts
    @GetMapping("/status-count")
    public Map<String, Long> getStatusCount() {
        return cloudService.getStatusCount();
    }
}