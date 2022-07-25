package com.example.monitoring.controller;

import com.example.monitoring.dto.MonitoringDto;
import com.example.monitoring.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MonitoringController {

    private final MonitoringService monitoringService;

    @Operation(summary = "Start monitoring on url")
    @PostMapping(value = "/start/{url}")
    public void startMonitoring(@PathVariable("url") final String url) {
        monitoringService.startMonitoring(url);
    }

    @Operation(summary = "Stop monitoring on url")
    @PostMapping(value = "/stop/{url}")
    public void stopMonitoring(@PathVariable("url") final String url) {
        monitoringService.stopMonitoring(url);
    }

    @Operation(summary = "Get lst result monitoring")
    @GetMapping(value = "/last-result/{url}")
    public MonitoringDto getLastResult(@PathVariable("url") final String url) {
        return monitoringService.getLastMonitoringResult(url);
    }
}
