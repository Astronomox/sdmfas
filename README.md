# SDMFAS: Smart Drainage Monitoring and Flood Alert System
COS202 Group 9

Live app: https://sdmfas.onrender.com

Note: the app runs on a free hosting tier and spins down after periods of inactivity. If it feels slow to load on first visit, give it 30 to 60 seconds to wake up.

## Run it locally

You need Java 17+ and Maven. From this folder:

```
mvn spring-boot:run

```



Then open **http://localhost:8080** in your browser. No database setup, no config needed.

If Maven isn't installed:
- **Windows**: `winget install Apache.Maven` or download from maven.apache.org
- **Mac**: `brew install maven`
- **IntelliJ/Eclipse**: open the folder as a Maven project and run `SdmfasApplication.java` directly, no terminal needed

## What's actually built

The system runs on a four stage pipeline. The first two stages were part of the original proposal. The third, vetting, was added during development once it became clear a public dashboard needs a trust mechanism, not just a counting mechanism.

1. **Report**: a resident submits a location, description, severity, and optional photo through the Report an Incident page. Hits `POST /api/reports`.
2. **Route**: every report is tagged to its LGA at submission time. That tag is the routing decision, there is no separate manual sorting step. See `ReportService.submit()`.
3. **Vet**: an LGA authority reviews the report on the LGA Review page and marks it Verified or Rejected. A report has no effect on the public dashboard until it is verified. See `ReportService.verify()`.
4. **Alert**: the Public Alerts page aggregates only verified reports per LGA and automatically flags any LGA with 3 or more unresolved verified reports as a hotspot. See `ReportService.getAlertSummary()`.

## The six pages

| Page | Audience | Purpose |
|---|---|---|
| Home (`/`) | Everyone | Introduces the project and the pipeline, links into the app |
| Report an Incident (`/report.html`) | Public | Submit a new report |
| Public Alerts (`/alerts.html`) | Public | See which LGAs are currently flagged as hotspots |
| LGA Review (`/verify.html`) | LGA authority | Vet each report, Verify or Reject, track response status |
| About (`/about.html`) | Everyone | Objectives, expected outcomes, SDG alignment |
| Team (`/team.html`) | Everyone | Group members and roles |

## Architecture notes for the defense

- **Storage**: in memory (`ConcurrentHashMap` inside `ReportRepository`), not a real database. This was a deliberate scope decision for the prototype timeline. The repository is already structured so swapping in Spring Data JPA later only touches `ReportRepository`, nothing else.
- **Layering**: standard Spring Boot layering, model (entities/enums) then repository (storage) then service (business logic, routing, vetting, hotspot detection) then controller (REST endpoints) then static HTML pages that call the API with `fetch`.
- **Why the vetting step was added**: the original three stage pipeline would let any single submission, true or false, immediately influence a public safety dashboard. Vetting closes that gap without adding friction for the reporting public, the extra step is entirely on the LGA side, where it belongs.
- **Why hotspot threshold = 3**: arbitrary but defensible, three independent verified reports on the same LGA is treated as confirmed rather than a one off. This is configurable, the `HOTSPOT_THRESHOLD` constant lives in `ReportService`.
- **Photos**: stored as base64 strings in memory for the demo, not on disk or cloud storage. That would be the next real world step (S3 or Cloudinary) but is out of scope for a Java course prototype.
- **No authentication**: any visitor can currently reach the LGA Review page. A production version would restrict this to verified LGA authority accounts.
- **Deployment**: containerised with a two stage Dockerfile (Maven build stage, lightweight JRE run stage) and hosted on Render, which does not offer a native Java runtime and builds Java projects via Docker instead. `application.properties` reads the `PORT` environment variable Render assigns at runtime, falling back to 8080 for local development.

## API endpoints

| Method | Path | Purpose |
|---|---|---|
| GET | `/api/lgas` | List all 20 Lagos LGAs |
| POST | `/api/reports` | Submit a new report |
| GET | `/api/reports` | List all reports |
| GET | `/api/reports/{id}` | Get one report |
| GET | `/api/reports/lga/{lga}` | Reports for one LGA, e.g. `IKEJA` |
| PATCH | `/api/reports/{id}/status` | Update status, e.g. `{"status":"RESOLVED"}` |
| PATCH | `/api/reports/{id}/verify` | Update verification, e.g. `{"verification":"VERIFIED"}` |
| GET | `/api/alerts` | Public hotspot dashboard, verified reports only |

## Demo script

1. Open **Report an Incident**, submit a report for a given LGA.
2. Switch to **Public Alerts**, note that the report does not count yet, nothing has been verified.
3. Switch to **LGA Review**, find the report, click **Verify**.
4. Go back to **Public Alerts**, the counts update. Repeat with 2 more reports on the same LGA to see it flip to a **Hotspot**.
5. Back on **LGA Review**, move a report's status from Pending to Resolved and show the count adjust again.

That sequence demonstrates the full Report, Route, Vet, Alert pipeline in under two minutes, and proves the vetting step actually gates the public dashboard rather than just existing as a button.

## Further documentation

Full project report and infrastructure documentation are included in this repository as Word documents, alongside the original proposal and pitch deck.