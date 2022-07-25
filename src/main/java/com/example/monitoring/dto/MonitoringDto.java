package com.example.monitoring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MonitoringDto {
    private String url;

    private String available;
}
