package com.example.service;

import com.example.model.Cloud;
import com.example.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CloudService {
    
    @Autowired
    private CloudRepository cloudRepository;
    
    public List<Cloud> getAllClouds() {
        return cloudRepository.findAll();
    }
    
    public Optional<Cloud> getCloudById(Long id) {
        return cloudRepository.findById(id);
    }
    
    public Cloud addCloud(Cloud cloud) {
        return cloudRepository.save(cloud);
    }
    
    public Cloud updateCloud(Long id, Cloud cloudDetails) {
        Cloud cloud = cloudRepository.findById(id).orElse(null);
        if (cloud != null) {
            cloud.setName(cloudDetails.getName());
            cloud.setProvider(cloudDetails.getProvider());
            cloud.setVmStatus(cloudDetails.getVmStatus());
            cloud.setStorageGb(cloudDetails.getStorageGb());
            cloud.setCost(cloudDetails.getCost());
            cloud.setRegion(cloudDetails.getRegion());
            cloud.setInstanceType(cloudDetails.getInstanceType());
            cloud.setCpuCores(cloudDetails.getCpuCores());
            cloud.setRamGb(cloudDetails.getRamGb());
            cloud.setEnvironment(cloudDetails.getEnvironment());
            return cloudRepository.save(cloud);
        }
        return null;
    }
    
    public Cloud startVM(Long id) {
        Cloud cloud = cloudRepository.findById(id).orElse(null);
        if (cloud != null) {
            cloud.setVmStatus("running");
            return cloudRepository.save(cloud);
        }
        return null;
    }
    
    public Cloud stopVM(Long id) {
        Cloud cloud = cloudRepository.findById(id).orElse(null);
        if (cloud != null) {
            cloud.setVmStatus("stopped");
            return cloudRepository.save(cloud);
        }
        return null;
    }
    
    public Cloud addStorage(Long id, Integer additionalGb) {
        Cloud cloud = cloudRepository.findById(id).orElse(null);
        if (cloud != null) {
            cloud.setStorageGb(cloud.getStorageGb() + additionalGb);
            cloud.setCost(cloud.getCost() + (additionalGb * 0.10));
            return cloudRepository.save(cloud);
        }
        return null;
    }
    
    public Cloud resetCloud(Long id) {
        Cloud cloud = cloudRepository.findById(id).orElse(null);
        if (cloud != null) {
            cloud.setStorageGb(0);
            cloud.setCost(0.0);
            cloud.setVmStatus("stopped");
            return cloudRepository.save(cloud);
        }
        return null;
    }
    
    public void deleteCloud(Long id) {
        cloudRepository.deleteById(id);
    }
    
    // ========== ADVANCED ANALYTICS ==========
    
    public Map<String, Object> getAnalytics() {
        List<Cloud> clouds = cloudRepository.findAll();
        
        if (clouds.isEmpty()) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("totalCost", 0);
            empty.put("totalStorage", 0);
            empty.put("totalClouds", 0);
            return empty;
        }
        
        double totalCost = clouds.stream().mapToDouble(Cloud::getCost).sum();
        int totalStorage = clouds.stream().mapToInt(Cloud::getStorageGb).sum();
        int totalCpu = clouds.stream().mapToInt(c -> c.getCpuCores() != null ? c.getCpuCores() : 0).sum();
        int totalRam = clouds.stream().mapToInt(c -> c.getRamGb() != null ? c.getRamGb() : 0).sum();
        
        Cloud highestCostCloud = clouds.stream()
            .max(Comparator.comparing(Cloud::getCost))
            .orElse(null);
        
        Cloud highestStorageCloud = clouds.stream()
            .max(Comparator.comparing(Cloud::getStorageGb))
            .orElse(null);
        
        double averageCost = totalCost / clouds.size();
        double averageStorage = totalStorage / (double) clouds.size();
        
        // Cost by Provider
        Map<String, Double> costByProvider = clouds.stream()
            .collect(Collectors.groupingBy(Cloud::getProvider, Collectors.summingDouble(Cloud::getCost)));
        
        // Storage by Provider
        Map<String, Integer> storageByProvider = clouds.stream()
            .collect(Collectors.groupingBy(Cloud::getProvider, Collectors.summingInt(Cloud::getStorageGb)));
        
        // Environment Distribution
        Map<String, Long> envDistribution = clouds.stream()
            .filter(c -> c.getEnvironment() != null)
            .collect(Collectors.groupingBy(Cloud::getEnvironment, Collectors.counting()));
        
        // AI Prediction: Predicted Cost = Current Cost + (Storage × 0.05) + (CpuCores × 10)
        List<Map<String, Object>> predictions = clouds.stream().map(cloud -> {
            Map<String, Object> pred = new HashMap<>();
            pred.put("name", cloud.getName());
            pred.put("currentCost", cloud.getCost());
            double predicted = cloud.getCost() + (cloud.getStorageGb() * 0.05);
            if (cloud.getCpuCores() != null) predicted += cloud.getCpuCores() * 10;
            pred.put("predictedCost", Math.round(predicted * 100.0) / 100.0);
            pred.put("savings", Math.round((predicted - cloud.getCost()) * 100.0) / 100.0);
            return pred;
        }).collect(Collectors.toList());
        
        // Cost Efficiency Score (Storage per Dollar)
        List<Map<String, Object>> efficiency = clouds.stream().map(cloud -> {
            Map<String, Object> eff = new HashMap<>();
            eff.put("name", cloud.getName());
            eff.put("efficiency", cloud.getCost() > 0 ? 
                Math.round((cloud.getStorageGb() / cloud.getCost()) * 100.0) / 100.0 : 0);
            eff.put("costPerGb", cloud.getCost() > 0 ? 
                Math.round((cloud.getCost() / cloud.getStorageGb()) * 100.0) / 100.0 : 0);
            return eff;
        }).collect(Collectors.toList());
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalCost", totalCost);
        analytics.put("totalStorage", totalStorage);
        analytics.put("totalCpu", totalCpu);
        analytics.put("totalRam", totalRam);
        analytics.put("highestCostCloud", highestCostCloud != null ? highestCostCloud.getName() : "N/A");
        analytics.put("highestCostValue", highestCostCloud != null ? highestCostCloud.getCost() : 0);
        analytics.put("highestStorageCloud", highestStorageCloud != null ? highestStorageCloud.getName() : "N/A");
        analytics.put("highestStorageValue", highestStorageCloud != null ? highestStorageCloud.getStorageGb() : 0);
        analytics.put("averageCost", averageCost);
        analytics.put("averageStorage", averageStorage);
        analytics.put("totalClouds", clouds.size());
        analytics.put("costByProvider", costByProvider);
        analytics.put("storageByProvider", storageByProvider);
        analytics.put("envDistribution", envDistribution);
        analytics.put("predictions", predictions);
        analytics.put("efficiency", efficiency);
        
        return analytics;
    }
    
    // Export data as CSV
    public String exportToCSV() {
        List<Cloud> clouds = cloudRepository.findAll();
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Name,Provider,Status,Storage(GB),Cost($),Region,InstanceType,CPU,RAM,Environment,Created,Updated\n");
        for (Cloud c : clouds) {
            csv.append(c.getId()).append(",");
            csv.append(c.getName()).append(",");
            csv.append(c.getProvider()).append(",");
            csv.append(c.getVmStatus()).append(",");
            csv.append(c.getStorageGb()).append(",");
            csv.append(c.getCost()).append(",");
            csv.append(c.getRegion() != null ? c.getRegion() : "").append(",");
            csv.append(c.getInstanceType() != null ? c.getInstanceType() : "").append(",");
            csv.append(c.getCpuCores() != null ? c.getCpuCores() : 0).append(",");
            csv.append(c.getRamGb() != null ? c.getRamGb() : 0).append(",");
            csv.append(c.getEnvironment() != null ? c.getEnvironment() : "").append(",");
            csv.append(c.getCreatedAt()).append(",");
            csv.append(c.getUpdatedAt()).append("\n");
        }
        return csv.toString();
    }
    
    // Bulk delete all clouds
    public void deleteAllClouds() {
        cloudRepository.deleteAll();
    }
    
    // Get running vs stopped count
    public Map<String, Long> getStatusCount() {
        List<Cloud> clouds = cloudRepository.findAll();
        return clouds.stream().collect(Collectors.groupingBy(Cloud::getVmStatus, Collectors.counting()));
    }
}