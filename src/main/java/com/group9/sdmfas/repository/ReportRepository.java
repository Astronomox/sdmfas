package com.group9.sdmfas.repository;

import com.group9.sdmfas.model.Lga;
import com.group9.sdmfas.model.Report;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory store standing in for a database. Good enough for the demo;
 * swap for a JPA repository later without touching the service/controller
 * layers if the project needs to grow past the prototype stage.
 */
@Repository
public class ReportRepository {

    private final Map<String, Report> store = new ConcurrentHashMap<>();

    public Report save(Report report) {
        store.put(report.getId(), report);
        return report;
    }

    public Optional<Report> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Report> findAll() {
        return List.copyOf(store.values());
    }

    public List<Report> findByLga(Lga lga) {
        return store.values().stream()
                .filter(r -> r.getLga() == lga)
                .collect(Collectors.toList());
    }

    public Collection<Report> allValues() {
        return store.values();
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public long count() {
        return store.size();
    }
}
