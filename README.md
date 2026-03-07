# JobLens
JobLens is a tool that helps job seekers quickly understand what a job description is actually asking for.

Instead of reading long and often vague job descriptions, users can paste the text and receive a structured interpretation of the role, including the engineering focus, industry domain, system architecture, and team environment.

The goal is to help candidates understand the true nature of a role within seconds.

## Problem
Job descriptions are often:
- long
- vague
- marketing-heavy
- difficult to interpret quickly

For software engineering roles, it is often unclear:
- what type of engineer they are hiring
- what system or product the team is building
- what industry domain the company operates in
- what the engineering environment looks like

Candidates frequently read an entire job description and still struggle to answer:
> "What is this role actually about?"
JobLens solves this by interpreting job descriptions and presenting structured insights.

---

## Target Users
Primary users:
- Software engineers
- Developers actively applying for jobs
- Technical professionals reviewing multiple job postings

User characteristics:
- reviewing many job descriptions daily
- trying to understand whether a role fits their experience
- wants quick insights instead of reading large blocks of text

## Example scenarios:
### Scenario 1
A developer opens a LinkedIn job posting and wants to quickly understand:
- what type of role this is
- whether it fits their experience

### Scenario 2
A candidate comparing multiple job descriptions wants to quickly identify:
- industry
- system architecture
- engineering expectations

---

## Product Goal
The goal of JobLens is simple:
> Paste a job description and instantly understand what the role actually requires.

The product focuses on interpretation, not ATS optimization or resume rewriting.


### MVP Scope (Version 1)
The first version of JobLens focuses only on job description analysis.
Users paste a job description and receive structured insights in four sections.

#### Features included in V1
- paste job description text
- analyze job description
- generate structured insights

#### Output sections
1. Role Interpretation
2. Industry / Domain
3. Product / System Architecture
4. Team & Work Environment

### Out of Scope (V1)
To keep the first version simple and focused, the following features are not included:

- resume upload
- resume tailoring
- ATS keyword analysis
- cover letter generation
- job tracking
- user accounts
- saving history
- browser extension

These features may be considered in future versions.

---

## User Flow
The application is intentionally simple.

### Step 1
User opens the application homepage.

### Step 2
User pastes a job description into a text input field.

#### Example input:

```
Python Software Developer
Industry: FinTech / Digital Assets / Crypto

Are you looking for a fast-paced, innovative environment...
```

### Step 3
User clicks Analyze Job Description.

### Step 4
The backend analyzes the job description.

### Step 5
The application displays structured insights.

---

## Output Structure
Each analysis returns four structured sections.
The format is consistent for every job description.

### 1. Role Interpretation
Explains **what type of role the company is hiring for**.
Information extracted:

- role type
- seniority level
- primary technologies
- engineering focus

Example output:
```
ROLE INTERPRETATION

Role: Backend Java Developer

Seniority: Mid–Senior level

Primary technologies:
• Java
• Spring Boot
• REST APIs

Engineering focus:
• backend service development
• scalable API design
• microservice architecture
```

### 2. Industry / Domain
Identifies the industry context of the role.
Information extracted:

- industry
- domain signals
- implications

Example output:
```
INDUSTRY / DOMAIN

Domain: Financial Technology

Signals detected:
• digital assets
• trading platform
• financial markets

Implications:
Experience in regulated systems or financial data processing may be valuable.
```

### 3. Product / System Architecture
Explains what type of system or product the team is building.
Information extracted:

- architecture style
- system type
- technology signals
- system characteristics

Example output:
```
PRODUCT / SYSTEM TYPE

Architecture signals:
• microservices
• event-driven systems

Technologies detected:
• Kafka
• WebSocket
• Redis

System characteristics:
• real-time data processing
• high-performance backend systems
```

### 4. Team & Work Environment
Describes how the team operates.
Information extracted:

- team structure
- collaboration style
- language expectations
- working model

Example output:
```
TEAM & WORK ENVIRONMENT

Team characteristics:
• cross-functional engineering team
• collaboration with product teams

Language expectations:
• English-speaking environment

Working style:
• agile development
• ownership of features from design to production
```

## Technology Stack
Planned stack for the MVP:

**Backend:**
Java 21
Spring Boot
Spring AI

**Frontend:**
- Thymeleaf

**Database:**
- PostgreSQL

**AI integration:**
- Local LLM via Ollama
- Prompt-based job description analysis

**Architecture:**
- Single monolithic backend
- Simple two-page web interface

---

## Future Vision
After the initial release, JobLens may expand to include:

- resume-to-job matching
- experience framing suggestions
- resume tailoring guidance
- browser extension for LinkedIn job pages

The current focus is to **launch a working product quickly and iterate based on user feedback**.

---

## Project Status
Version 1 is currently under development.
