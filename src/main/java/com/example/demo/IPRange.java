package com.example.demo;

public class IPRange {
    private String ip_prefix;
    private String region;

    public String getIp_prefix() {
        return ip_prefix;
    }

    public void setIp_prefix(String ip_prefix) {
        this.ip_prefix = ip_prefix;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "IP Range: " + ip_prefix + ", Region: " + region;
    }
}
