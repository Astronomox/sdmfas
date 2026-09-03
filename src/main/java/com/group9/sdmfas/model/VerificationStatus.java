package com.group9.sdmfas.model;

/**
 * Whether an LGA authority has reviewed a report for legitimacy.
 * Separate from ReportStatus, which tracks response progress —
 * a report can be RESOLVED but still UNREVIEWED if no one has vetted it,
 * and a report must be VERIFIED before it counts toward the public
 * hotspot dashboard.
 */
public enum VerificationStatus {
    UNREVIEWED,
    VERIFIED,
    REJECTED
}
