package com.group9.sdmfas.controller;

import com.group9.sdmfas.dto.ReportRequest;
import com.group9.sdmfas.dto.StatusUpdateRequest;
import com.group9.sdmfas.model.Lga;
import com.group9.sdmfas.model.Report;
import com.group9.sdmfas.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Report submitReport(@Valid @RequestBody ReportRequest request) {
        return service.submit(request);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Report getReport(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/lga/{lga}")
    public List<Report> getReportsByLga(@PathVariable Lga lga) {
        return service.getByLga(lga);
    }

    @PatchMapping("/{id}/status")
    public Report updateStatus(@PathVariable String id, @Valid @RequestBody StatusUpdateRequest request) {
        return service.updateStatus(id, request);
    }
}
