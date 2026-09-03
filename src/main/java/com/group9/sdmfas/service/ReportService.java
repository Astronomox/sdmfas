package com.group9.sdmfas.service;

import com.group9.sdmfas.dto.ReportRequest;
import com.group9.sdmfas.dto.StatusUpdateRequest;
import com.group9.sdmfas.model.Lga;
import com.group9.sdmfas.model.Report;
import com.group9.sdmfas.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    /** Handles the REPORT step: a new incident comes in and gets stored, already tagged to its LGA. */
    public Report submit(ReportRequest request) {
        Report report = new Report();
        report.setReporterName(request.getReporterName());
        report.setPhoneNumber(request.getPhoneNumber());
        report.setLga(request.getLga());
        report.setStreetAddress(request.getStreetAddress());
        report.setDescription(request.getDescription());
        report.setSeverity(request.getSeverity());
        report.setPhotoBase64(request.getPhotoBase64());
        // ROUTE step: routing is implicit here — the LGA field on the report
        // *is* the routing decision. In a fuller build this is where a
        // notification would fire to that LGA's authority account.
        return repository.save(report);
    }

    public List<Report> getAll() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Report::getCreatedAt).reversed())
                .toList();
    }

    public Report getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No report found with id " + id));
    }

    public List<Report> getByLga(Lga lga) {
        return repository.findByLga(lga).stream()
                .sorted(Comparator.comparing(Report::getCreatedAt).reversed())
                .toList();
    }

    public Report updateStatus(String id, StatusUpdateRequest request) {
        Report report = getById(id);
        report.setStatus(request.getStatus());
        repository.save(report);
        return report;
    }
}
