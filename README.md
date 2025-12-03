# Account Aggregator (AA) Integration – Digio + Sahamati  

**Live Demo:** http://localhost:4200 (Run locally)  
**Backend:** Spring Boot 3 + Java 21 + PostgreSQL  
**Frontend:** Angular 17+  
**AA Provider:** Digio KSA (Sandbox)  
**FIP Used:** FinShareBank OE UAT  
**Template ID:** `CTMP250926152142855LYQ59RGKJTAGZ`

## Features Implemented
- Consent creation with Digio sandbox (100% working)
- Dynamic reactive form with full client + server validation
- Real-time consent status polling & updates
- FI data fetch simulation (sandbox mode)
- Secure payload handling (no PII logging)
- Responsive Angular dashboard with Material UI
- Proper error handling & toast notifications
- Clean layered architecture (Controller → Service → Util → Entity)

## Screenshots (Live Working Flow)

### 1. Dashboard – Consent Requests List (Real-time Status)
![Dashboard showing consent list with PENDING status](images/Screenshot (156).png)

### 2. Create Consent Request Form (Fully Validated)
![Create Consent Form with all fields and validation](images/create-consent-form.png)

### 3. Consent Successfully Created (Digio Redirect URL + Gateway Token)
![Consent created success with redirect URL](images/consent-created-success.png)

### 4. Consent Approved on Digio (User approves on mobile/email)
![User approves consent on Digio gateway](images/digio-consent-approval.png)

### 5. Status Updated to ACTIVE (Real-time polling)
![Consent status changed to ACTIVE after approval](images/consent-status-active.png)

### 6. FI Data Fetch Simulation (Post consent approval)
![FI data fetched successfully in sandbox mode](images/fi-data-fetched.png)

## Tech Stack
| Layer        | Technology                          |
|--------------|-------------------------------------|
| Backend      | Spring Boot 3.5, Java 21, Lombok    |
| Database     | PostgreSQL 17                       |
| ORM          | Spring Data JPA + Hibernate         |
| Frontend     | Angular 17+, Angular Material, RxJS|
| AA Provider  | Digio KSA (Sandbox)                 |
| Build Tool   | Maven                               |
| Testing      | Postman + Swagger UI                |

## Quick Start

### Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
# Runs on http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
