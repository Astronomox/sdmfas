# SDMFAS — Smart Drainage Monitoring and Flood Alert System
COS202 Group 9

## Run it

You need Java 17+ and Maven. From this folder:

```bash
mvn spring-boot:run
```

Then open **http://localhost:8080** in your browser. That's it — no database setup, no config.

If Maven isn't installed:
- **Windows**: `choco install maven` or download from maven.apache.org
- **Mac**: `brew install maven`
- **IntelliJ/Eclipse**: just open the folder as a Maven project and run `SdmfasApplication.java` directly — no terminal needed.

## What's actually built

This covers the three core features from the proposal, end to end:

1. **Report** — the "Report an Incident" tab. Pick an LGA, describe the issue, optionally attach a photo. Submitting hits `POST /api/reports`.
2. **Route** — every report is tagged to its LGA at submission time. That tag *is* the routing decision (see `ReportService.submit()`).
3. **Alert** — the "Public Alerts" tab (`GET /api/alerts`) aggregates reports per LGA and automatically flags any LGA with 3+ unresolved reports as a **hotspot**. No one has to manually review it — the threshold logic is in `ReportService.getAlertSummary()`.

The "All Reports" tab doubles as the LGA-authority view: an authority would use the status dropdown to move a report from Pending → In Progress → Resolved (`PATCH /api/reports/{id}/status`), which then feeds back into the hotspot calculation live.

## Architecture notes for your writeup / defense

- **Storage**: in-memory (`ConcurrentHashMap` inside `ReportRepository`), not a real database. This was a deliberate scope decision for the prototype timeline — the repository is already structured so swapping in Spring Data JPA later only touches `ReportRepository`, nothing else.
- **Layering**: standard Spring Boot layering — `model` (entities/enums) → `repository` (storage) → `service` (business logic: routing, hotspot detection) → `controller` (REST endpoints) → static `index.html` (frontend, calls the API with `fetch`).
- **Why hotspot threshold = 3**: arbitrary but defensible — three independent reports on the same LGA is treated as confirmed rather than a one-off. Mention this is configurable (`HOTSPOT_THRESHOLD` constant in `ReportService`) if asked — that's a sign you understand it, not just copied it.
- **Photos**: stored as base64 strings in memory for the demo, not on disk/cloud storage — that would be the next real-world step (S3/Cloudinary) but is out of scope for a Java course prototype.

## API endpoints (for testing with Postman if needed)

| Method | Path | Purpose |
|---|---|---|
| GET | `/api/lgas` | List all 20 Lagos LGAs |
| POST | `/api/reports` | Submit a new report |
| GET | `/api/reports` | List all reports |
| GET | `/api/reports/{id}` | Get one report |
| GET | `/api/reports/lga/{lga}` | Reports for one LGA (e.g. `IKEJA`) |
| PATCH | `/api/reports/{id}/status` | Update status: `{"status":"RESOLVED"}` |
| GET | `/api/alerts` | Public hotspot dashboard |

## Demo script (2 minutes)

1. Open "Report an Incident" → submit 3 reports, all for the same LGA (e.g. Ikorodu), varying severity.
2. Switch to "Public Alerts" → point out Ikorodu is now flagged as a **Hotspot** automatically.
3. Switch to "All Reports" → change one report's status to "Resolved" via the dropdown.
4. Go back to "Public Alerts" → show the count updated live, hotspot status recalculated.

That sequence hits Report → Route → Alert in one flow, which is exactly the pipeline in slide 5 of the deck.
