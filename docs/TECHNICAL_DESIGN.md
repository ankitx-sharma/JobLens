# JobLens Technical Design Document

## 1. Overview
JobLens is a web application that analyzes a pasted job description and returns a structured interpretation of the role.

The MVP focuses on one core workflow:

- user pastes a job description
- system analyzes it using AI
- system returns four structured insight sections

**MVP output sections**

1.  Role Interpretation
2.  Industry / Domain
3.  Product / System Architecture
4.  Team & Work Environment

---

## 2. Application Flow
### 2.1 High-level user flow

1. User opens the homepage
2. User pastes a job description into a text area
3. User clicks Analyze Job Description
4. Backend validates the input
5. Backend sends a structured prompt to the AI service
6. AI service returns structured analysis
7. Backend maps the response into result fields
8. Backend stores request and result in the database
9. User is shown the result page

### 2.2 Detailed backend flow

**Step 1 — Request received**
- Frontend sends a POST /analyze request
- Payload contains the pasted job description text

**Step 2 — Validation**
- Check job description is not empty
- Check minimum length
- Trim whitespace

**Step 3 — Save input**
- Store raw job description in database as JobAnalysisRequest

**Step 4 — Build AI prompt**
- Construct a structured prompt using the predefined analysis template

**Step 5 — AI analysis**
- Send prompt to Ollama via Spring AI
- Receive structured analysis text or JSON-like formatted output

**Step 6 — Parse AI response**
Extract:
- role interpretation
- industry/domain
- product/system type
- team/work environment

**Step 7 — Save result**
- Store parsed result in database as JobAnalysisResult

**Step 8 — Render response**
- Return result page with the structured sections

---

## 3. Backend Components
The MVP uses a small monolithic architecture.

### 3.1 Controller layer
`JobAnalysisController`

Responsibilities:
- render homepage
- handle form submission
- call service layer
- return result page

### 3.2 Service layer
`JobAnalysisService`

Responsibilities:
- validate job description input
- orchestrate request saving
- build analysis flow
- call AI service
- save result
- prepare data for UI

`PromptBuilderService`

Responsibilities:
- generate the final prompt string sent to AI
- enforce the required output structure

### 3.3 AI integration layer
`AiAnalysisService`

Responsibilities:
- send prompt to Ollama
- receive model response
- return raw analysis output

Possible implementation:
- `OllamaAnalysisService`

### 3.4 Persistence layer
`JobAnalysisRequestRepository`

Responsibilities:
- persist raw user input

`JobAnalysisResultRepository`

Responsibilities:
- persist structured analysis result

### 3.5 Model / DTO layer
**DTOs**

Used for request/response handling between controller and UI.

Possible DTOs:
- `JobAnalysisForm`
- `JobAnalysisView`

**Entities**

Used for persistence.

Possible entities:
- `JobAnalysisRequestEntity`
- `JobAnalysisResultEntity`

### 3.6 Exception handling
`GlobalExceptionHandler`

Responsibilities:
- handle validation errors
- handle AI service failures
- return user-friendly error page/message

---

## 4. Data Model
For MVP, we use two main tables.

### 4.1 job_analysis_request
Stores the raw job description submitted by the user.

Fields
- `id` — primary key
- `job_description_text` — text
- `created_at` — timestamp

### 4.2 job_analysis_result
Stores the structured output of the analysis.

Fields
- `id` — primary key
- `request_id` — foreign key to `job_analysis_request`
- `role_interpretation` — text
- `industry_domain` — text
- `product_system_type` — text
- `team_environment` — text
- `raw_ai_response` — text
- `created_at` — timestamp

### 4.3 Entity relationship

- one `job_analysis_request`
- one `job_analysis_result`

Relationship:
```
job_analysis_request (1) ---- (1) job_analysis_result
```

---

## 5. Endpoints

For MVP,  we have kept the endpoints minimal.

### 5.1 Page endpoints
`GET /`

Purpose:
- render homepage

Response:
- input form page

`POST /analyze`

Purpose:
- receive job description form submission
- trigger analysis flow
- return result page

Request fields:
- `jobDescriptionText`

Response:
- rendered result page

---

## 6. Pages
For MVP, there are 2 pages.

### 6.1 Home page

Route:
- `GET /`

Purpose:
- let user paste a job description
- submit analysis request

Main elements:
- application title
- short product description
- text area for job description
- analyze button

### 6.2 Result page

Route:
- returned after `POST /analyze`

Purpose:
- display the four structured analysis sections

Main elements:
- submitted job description preview
- Role Interpretation
- Industry / Domain
- Product / System Architecture
- Team & Work Environment
- back button / analyze another job description button

---

## 7. File Structure

Project structure:
```
joblens/
├── src/
│   ├── main/
│   │   ├── java/com/joblens/
│   │   │   ├── JoblensApplication.java
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   └── JobAnalysisController.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── JobAnalysisService.java
│   │   │   │   └── PromptBuilderService.java
│   │   │   │
│   │   │   ├── ai/
│   │   │   │   ├── AiAnalysisService.java
│   │   │   │   └── OllamaAnalysisService.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── JobAnalysisForm.java
│   │   │   │   └── JobAnalysisView.java
│   │   │   │
│   │   │   ├── entity/
│   │   │   │   ├── JobAnalysisRequestEntity.java
│   │   │   │   └── JobAnalysisResultEntity.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── JobAnalysisRequestRepository.java
│   │   │   │   └── JobAnalysisResultRepository.java
│   │   │   │
│   │   │   ├── config/
│   │   │   │   └── AiConfig.java
│   │   │   │
│   │   │   └── exception/
│   │   │       └── GlobalExceptionHandler.java
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── home.html
│   │       │   └── result.html
│   │       │
│   │       ├── static/
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   └── js/
│   │       │
│   │       └── application.yml
│   │
│   └── test/
│       └── java/com/joblens/
│
├── pom.xml
├── README.md
└── TECHNICAL_DESIGN.md
```

---

## 8. Request / Response Design
### 8.1 Form request object
`JobAnalysisForm`

Fields:
- `jobDescriptionText`

Validation:
- required
- minimum length, for example 100 characters

### 8.2 View response object
`JobAnalysisView`

Fields:
- `id`
- `jobDescriptionText`
- `roleInterpretation`
- `industryDomain`
- `productSystemType`
- `teamEnvironment`

This object is passed from controller to Thymeleaf template.

---

## 9. AI Response Strategy
For MVP, the AI prompt will request output in a stable structure.

Preferred format:
- sectioned text with fixed headings

Example headings:
- ROLE INTERPRETATION
- INDUSTRY / DOMAIN
- PRODUCT / SYSTEM TYPE
- TEAM & WORK ENVIRONMENT

Alternative later:
- JSON structured response

For V1, fixed-section text is preferred.

---

## 10. Validation Rules

**Rules**
- job description must not be blank
- job description should have a minimum length
- trim leading/trailing whitespace

**Failure behavior**
- return user to home page
- show validation error message

---

## 11. Error Handling

The system should handle these cases:

**Validation errors**

Example:
- empty job description
- very short input

**AI service errors**

Example:
- Ollama unavailable
- timeout
- malformed response

**Parsing errors**

Example:
- AI response missing expected sections

**Fallback strategy:**
- save raw response if available
- show partial result if possible
- otherwise display friendly error message

---

## 12. Non-Functional Requirements
**Performance**
- response should feel reasonably fast for one JD submission
- target: a few seconds, depending on model speed

**Availability**
- simple monolith deployment
- no distributed architecture required for MVP

**Reliability**
- input should always be saved before AI processing
- errors should not crash the app

**Maintainability**
- clean package structure
- AI logic isolated in separate service
- prompt creation isolated in dedicated service

---

## 13. MVP Technical Decisions Summary
**Architecture**
- monolithic Spring Boot application

**Frontend**
- Thymeleaf
- 2 pages only

**Database**
- PostgreSQL

**AI integration**
- Ollama via Spring AI

**Core flow**
- form submit → service → AI → save result → render page
