package com.group9.sdmfas.dto;

import com.group9.sdmfas.model.ReportStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequest {

    @NotNull(message = "status is required")
    private ReportStatus status;

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }
}
