package com.group9.sdmfas.controller;

import com.group9.sdmfas.dto.LgaAlertSummary;
import com.group9.sdmfas.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final ReportService service;

    public AlertController(ReportService service) {
        this.service = service;
    }

    /** Public flood-status view: one row per LGA, hotspots flagged automatically. */
    @GetMapping
    public List<LgaAlertSummary> getAlerts() {
        return service.getAlertSummary();
    }
}
