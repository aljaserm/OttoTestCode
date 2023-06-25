package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

@RestController
class IPRangeController {

    @GetMapping(value = "/ip-ranges", produces = "text/plain")
    public String getIPRanges(@RequestParam(value = "region", defaultValue = "ALL") String region) {
        String url = "https://ip-ranges.amazonaws.com/ip-ranges.json";
        IPRangeData ipRangeData = fetchIPRangeData(url);
        return filterIPRanges(ipRangeData, region);
    }

    private IPRangeData fetchIPRangeData(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, IPRangeData.class);
    }

    private String filterIPRanges(IPRangeData ipRangeData, String region) {
        StringBuilder filteredRanges = new StringBuilder();
        for (IPRange ipRange : ipRangeData.getPrefixes()) {
            if (isRegionMatch(ipRange, region)) {
                filteredRanges.append(ipRange.toString()).append("\n");
            }
        }
        return filteredRanges.toString();
    }

    private boolean isRegionMatch(IPRange ipRange, String region) {
        return region.equals("ALL")
                || ipRange.getRegion().equalsIgnoreCase(region)
                || region.equalsIgnoreCase("EU") && ipRange.getRegion().startsWith("eu-")
                || region.equalsIgnoreCase("US") && ipRange.getRegion().startsWith("us-")
                || region.equalsIgnoreCase("AP") && ipRange.getRegion().startsWith("ap-")
                || region.equalsIgnoreCase("CN") && ipRange.getRegion().startsWith("cn-")
                || region.equalsIgnoreCase("SA") && ipRange.getRegion().startsWith("sa-")
                || region.equalsIgnoreCase("AF") && ipRange.getRegion().startsWith("af-")
                || region.equalsIgnoreCase("CA") && ipRange.getRegion().startsWith("ca-");
    }

}
