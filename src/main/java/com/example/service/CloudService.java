package com.example.service;

import com.example.model.Cloud;
import com.example.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CloudService {

    @Autowired
    private CloudRepository repository;

    public List<Cloud> getAllClouds() {
        return repository.findAll();
    }

    public Optional<Cloud> getCloudById(String id) {
        return repository.findById(id);
    }

    public Cloud addCloud(Cloud cloud) {
        cloud.onCreate();
        return repository.save(cloud);
    }

    public Cloud updateCloud(String id, Cloud newCloud) {
        Optional<Cloud> optional = repository.findById(id);
        if (optional.isPresent()) {
            Cloud cloud = optional.get();

            cloud.setName(newCloud.getName());
            cloud.setProvider(newCloud.getProvider());
            cloud.setVmStatus(newCloud.getVmStatus());
            cloud.setStorageGb(newCloud.getStorageGb());
            cloud.setCost(newCloud.getCost());
            cloud.setRegion(newCloud.getRegion());
            cloud.setInstanceType(newCloud.getInstanceType());
            cloud.setCpuCores(newCloud.getCpuCores());
            cloud.setRamGb(newCloud.getRamGb());
            cloud.setEnvironment(newCloud.getEnvironment());

            cloud.onUpdate();

            return repository.save(cloud);
        }
        return null;
    }

    public Cloud startVM(String id) {
        return updateStatus(id, "RUNNING");
    }

    public Cloud stopVM(String id) {
        return updateStatus(id, "STOPPED");
    }

    private Cloud updateStatus(String id, String status) {
        Optional<Cloud> optional = repository.findById(id);
        if (optional.isPresent()) {
            Cloud cloud = optional.get();
            cloud.setVmStatus(status);
            cloud.onUpdate();
            return repository.save(cloud);
        }
        return null;
    }

    public Cloud addStorage(String id, Integer gb) {
        Optional<Cloud> optional = repository.findById(id);
        if (optional.isPresent()) {
            Cloud cloud = optional.get();
            cloud.setStorageGb(cloud.getStorageGb() + gb);
            cloud.onUpdate();
            return repository.save(cloud);
        }
        return null;
    }

    public Cloud resetCloud(String id) {
        Optional<Cloud> optional = repository.findById(id);
        if (optional.isPresent()) {
            Cloud cloud = optional.get();
            cloud.setVmStatus("STOPPED");
            cloud.setStorageGb(0);
            cloud.onUpdate();
            return repository.save(cloud);
        }
        return null;
    }

    public void deleteCloud(String id) {
        repository.deleteById(id);
    }

    public void deleteAllClouds() {
        repository.deleteAll();
    }

    public Map<String, Object> getAnalytics() {
        List<Cloud> clouds = repository.findAll();

        double totalCost = clouds.stream().mapToDouble(c -> c.getCost()).sum();
        int totalStorage = clouds.stream().mapToInt(c -> c.getStorageGb()).sum();

        Map<String, Object> map = new HashMap<>();
        map.put("totalClouds", clouds.size());
        map.put("totalCost", totalCost);
        map.put("totalStorage", totalStorage);

        return map;
    }

    public String exportToCSV() {
        List<Cloud> clouds = repository.findAll();
        StringBuilder sb = new StringBuilder();

        sb.append("Name,Provider,Status,Cost\n");
        for (Cloud c : clouds) {
            sb.append(c.getName()).append(",")
              .append(c.getProvider()).append(",")
              .append(c.getVmStatus()).append(",")
              .append(c.getCost()).append("\n");
        }

        return sb.toString();
    }

    public Map<String, Long> getStatusCount() {
        List<Cloud> clouds = repository.findAll();

        Map<String, Long> map = new HashMap<>();
        map.put("RUNNING", clouds.stream().filter(c -> "RUNNING".equals(c.getVmStatus())).count());
        map.put("STOPPED", clouds.stream().filter(c -> "STOPPED".equals(c.getVmStatus())).count());

        return map;
    }
}
