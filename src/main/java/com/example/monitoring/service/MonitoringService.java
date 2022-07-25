package com.example.monitoring.service;

import com.example.monitoring.dto.MonitoringDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MonitoringService {

    private final Map<String, Boolean> urls = new ConcurrentHashMap<>();

    private final Map<String, MonitoringDto> lastResult = new HashMap<>();

    private final EmailService emailService;

    @Value("${url.port}")
    private int port;

    @Value("${url.timeout}")
    private int timeout;

    @Value("${test-email}")
    private String testEmail;

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringService.class);

    public void startMonitoring(final String url) {
        LOGGER.info("Start monitoring");
        urls.put(url, true);
    }

    public void stopMonitoring(final String url) {
        LOGGER.info("Stop monitoring");
        urls.remove(url);
    }

    public MonitoringDto getLastMonitoringResult(final String url) {
        LOGGER.info("Get last result monitoring");
        return lastResult.get(url);
    }

    @Scheduled(cron = "${cron.expression.value}")
    public void run() {
        urls.forEach((url, value) -> {
            if (Boolean.TRUE.equals(value)) {
                try (Socket socket = new Socket()) {
                    InetAddress ip = InetAddress.getByName(url);
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    lastResult.put(url, MonitoringDto.builder()
                            .available("Available")
                            .url(url)
                            .build()
                    );
                    emailService.sendEmail(testEmail,
                            "Test subject",
                            String.format("The url %s is available", url));
                } catch (IOException e) {
                    lastResult.put(url, MonitoringDto.builder()
                            .available("Available")
                            .url(url)
                            .build()
                    );
                    emailService.sendEmail(testEmail,
                            "Test subject",
                            String.format("The url %s is not available", url));
                }
            }
        });
    }
}
