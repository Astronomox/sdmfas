package com.group9.sdmfas.dto;

import com.group9.sdmfas.model.Lga;

/**
 * One row of the public flood-status dashboard: how many active/resolved
 * reports exist for a given LGA, and whether it currently counts as a
 * "hotspot" (i.e. enough unresolved reports to warrant a public warning).
 */
public class LgaAlertSummary {

    private Lga lga;
    private long pendingCount;
    private long inProgressCount;
    private long resolvedCount;
    private boolean hotspot;

    public LgaAlertSummary(Lga lga, long pendingCount, long inProgressCount, long resolvedCount, boolean hotspot) {
        this.lga = lga;
        this.pendingCount = pendingCount;
        this.inProgressCount = inProgressCount;
        this.resolvedCount = resolvedCount;
        this.hotspot = hotspot;
    }

    public Lga getLga() {
        return lga;
    }

    public long getPendingCount() {
        return pendingCount;
    }

    public long getInProgressCount() {
        return inProgressCount;
    }

    public long getResolvedCount() {
        return resolvedCount;
    }

    public boolean isHotspot() {
        return hotspot;
    }
}
