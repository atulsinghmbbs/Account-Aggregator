# Account-Aggregator

# Account Aggregator (AA) Integration – Digio + Sahamati

**Live Demo:** http://localhost:4200 (Run locally)  
**Backend:** Spring Boot + PostgreSQL  
**Frontend:** Angular 17+  
**AA Provider:** Digio (Sandbox)  
**FIP Used:** FinShareBank OE UAT  
**Template ID:** `CTMP250926152142855LYQ59RGKJTAGZ`

## Features Implemented

- Consent creation with Digio sandbox
- Dynamic form with full validation
- Real-time consent status tracking
- FI data fetch simulation (sandbox mode)
- Secure payload handling (no PII logging)
- Responsive Angular dashboard
- Proper error handling & user feedback
- Clean layered architecture

## Tech Stack

| Layer       | Technology                  |
|-------------|-----------------------------|
| Backend     | Spring Boot 3, Java 17      |
| Database    | PostgreSQL                  |
| Frontend    | Angular 17+, TypeScript     |
| AA Provider | Digio KSA (Sandbox)         |
| Build Tool  | Maven / npm                 |

## Quick Start

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
# Runs on http://localhost:8080
