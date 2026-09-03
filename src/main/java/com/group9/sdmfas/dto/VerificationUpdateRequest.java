package com.group9.sdmfas.dto;

import com.group9.sdmfas.model.VerificationStatus;
import jakarta.validation.constraints.NotNull;

public class VerificationUpdateRequest {

    @NotNull(message = "verification is required")
    private VerificationStatus verification;

    public VerificationStatus getVerification() {
        return verification;
    }

    public void setVerification(VerificationStatus verification) {
        this.verification = verification;
    }
}
