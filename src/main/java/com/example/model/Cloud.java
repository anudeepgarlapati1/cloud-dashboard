package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clouds")
public class Cloud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String provider;
    private String vmStatus;
    private Integer storageGb;
    private Double cost;
    private String region;
    private String instanceType;
    private Integer cpuCores;
    private Integer ramGb;
    private String environment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public Cloud() {}
    
    public Cloud(String name, String provider, String vmStatus, Integer storageGb, Double cost, 
                 String region, String instanceType, Integer cpuCores, Integer ramGb, String environment) {
        this.name = name;
        this.provider = provider;
        this.vmStatus = vmStatus;
        this.storageGb = storageGb;
        this.cost = cost;
        this.region = region;
        this.instanceType = instanceType;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.environment = environment;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getProvider() { return provider; }
    public String getVmStatus() { return vmStatus; }
    public Integer getStorageGb() { return storageGb; }
    public Double getCost() { return cost; }
    public String getRegion() { return region; }
    public String getInstanceType() { return instanceType; }
    public Integer getCpuCores() { return cpuCores; }
    public Integer getRamGb() { return ramGb; }
    public String getEnvironment() { return environment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setProvider(String provider) { this.provider = provider; }
    public void setVmStatus(String vmStatus) { this.vmStatus = vmStatus; }
    public void setStorageGb(Integer storageGb) { this.storageGb = storageGb; }
    public void setCost(Double cost) { this.cost = cost; }
    public void setRegion(String region) { this.region = region; }
    public void setInstanceType(String instanceType) { this.instanceType = instanceType; }
    public void setCpuCores(Integer cpuCores) { this.cpuCores = cpuCores; }
    public void setRamGb(Integer ramGb) { this.ramGb = ramGb; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}