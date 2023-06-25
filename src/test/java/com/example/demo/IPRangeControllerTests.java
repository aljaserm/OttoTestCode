package com.example.demo;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.regex.Pattern;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IPRangeController.class)
public class IPRangeControllerTests {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetIPRanges_AllRegions() throws Exception {
        // Mock the response from the IP ranges API
        IPRangeData mockIPRangeData = new IPRangeData();
        IPRange ipRange1 = new IPRange();
        ipRange1.setIp_prefix("1.2.3.0/24");
        ipRange1.setRegion("af-south-1");
        IPRange ipRange2 = new IPRange();
        ipRange2.setIp_prefix("192.168.0.0/16");
        ipRange2.setRegion("af-south-1");
        IPRange ipRange3 = new IPRange();
        ipRange3.setIp_prefix("10.0.0.0/8");
        ipRange3.setRegion("af-south-1");
        mockIPRangeData.setPrefixes(Arrays.asList(ipRange1, ipRange2, ipRange3));

        when(restTemplate.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IPRangeData.class))
                .thenReturn(mockIPRangeData);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/ip-ranges")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesRegex("1\\.2\\.3\\.\\d{1,3}, 192\\.168\\.\\d{1,3}\\.\\d{1,3}, 10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")));
    }


    @Test
    public void testGetIPRanges_SpecificRegion() throws Exception {
        // Mock the response from the IP ranges API
        IPRangeData mockIPRangeData = new IPRangeData();
        IPRange ipRange1 = new IPRange();
        ipRange1.setIp_prefix("1.2.3.0/24");
        ipRange1.setRegion("US");
        IPRange ipRange2 = new IPRange();
        ipRange2.setIp_prefix("3.2.34.0/26");
        ipRange2.setRegion("af-south-1");
        mockIPRangeData.setPrefixes(Arrays.asList(ipRange1, ipRange2));

        when(restTemplate.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IPRangeData.class))
                .thenReturn(mockIPRangeData);

        // Perform the GET request with a specific region
        mockMvc.perform(MockMvcRequestBuilders.get("/ip-ranges?region=US")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesRegex("1\\.2\\.3\\.\\d{1,3}")));
    }
    
    @Test
    public void testGetIPRanges_NoRegions() throws Exception {
        // Mock the response from the IP ranges API with no regions
        IPRangeData mockIPRangeData = new IPRangeData();
        IPRange ipRange1 = new IPRange();
        ipRange1.setIp_prefix("1.2.3.0/24");
        ipRange1.setRegion(null);
        IPRange ipRange2 = new IPRange();
        ipRange2.setIp_prefix("3.2.34.0/26");
        ipRange2.setRegion(null);
        mockIPRangeData.setPrefixes(Arrays.asList(ipRange1, ipRange2));

        when(restTemplate.getForObject("https://ip-ranges.amazonaws.com/ip-ranges.json", IPRangeData.class))
                .thenReturn(mockIPRangeData);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/ip-ranges")
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(matchesRegex("1\\.2\\.3\\.\\d{1,3}")));
    }

    public static org.hamcrest.Matcher<java.lang.String> matchesRegex(final java.lang.String regex) {
        return new TypeSafeMatcher<java.lang.String>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("matches regex=").appendText(regex);
            }

            @Override
            protected boolean matchesSafely(java.lang.String item) {
                return item.matches(regex);
            }
        };
    }
}
