# JobLens UI Design Document
## 1. Overview

This document defines the user interface for the JobLens MVP.

The goal of the UI is to keep the experience:

- simple
- fast
- readable
- focused on one main action

The MVP is a server-rendered web application using Thymeleaf and consists of 2 pages:
1. Home page
2. Result page

The UI should help users quickly paste a job description, analyze it, and view the results in a structured format.

---

## 2. UI Design Principles

The UI for JobLens should follow these principles:

**Simplicity**

The interface should have minimal distractions and only the elements needed for the core workflow.

**Readability**

Results must be easy to scan. Long paragraphs should be avoided. Structured sections and bullet points should be preferred.

**Speed**

The user should understand what to do immediately without instructions that are too long.

**Clarity**

Labels, headings, buttons, and messages should be explicit and unambiguous.

**Consistency**

The same spacing, typography hierarchy, and card structure should be used across pages.

---

## 3. Pages
The MVP has 2 pages:

### 1. Home Page
Route: 
- `GET /`

Purpose:
- allow the user to paste a job description
- submit the analysis request

### 2. Result Page
Route:
- returned after `POST /analyze`

Purpose:
- show the analysis results in 4 structured sections

---

## 4. Home Page Design
### 4.1 Purpose
The home page is the entry point of the application.

Its role is to make the primary user action extremely clear:

> Paste a job description and analyze it. 

### 4.2 Main Layout

The home page should contain the following sections, top to bottom:

#### Header

Contains:
- product name
- short product subtitle

#### Main Input Section

Contains:
- section title
- short instruction text
- large textarea
- analyze button

#### Optional Helper Text

Contains:
- examples of what the tool does
- note about supported input type

### 4.3 Home Page Structure
#### A. Top Navigation / Minimal Header

Content:
- JobLens logo or text wordmark
- optional small tagline

Example:
```
JobLens
Understand what a job description is actually asking for
```
This should be minimal, not a large complex navbar.

#### B. Hero / Intro Section

This is the main product explanation.

Content:
- page headline
- one-sentence product summary

Example:
```
Decode job descriptions in seconds

Paste a job description and get a structured explanation of the role,
industry, system architecture, and team environment.
```

#### C. Input Form Section

This is the most important part of the home page.

Elements:

**1. Label**

Example:
```
Paste job description
```

**2. Textarea**

Properties:
- large
- multi-line
- comfortable for long text
- placeholder text visible

Example placeholder:
```
Paste the full job description here...
```

**3. Validation error area**

If the user submits invalid input, show an inline message below the textarea.

Example messages:
- Job description cannot be empty
- Please paste a longer job description

**4. Analyze button**

Primary CTA button.

Example label:
```
Analyze Job Description
```

#### D. Optional Tips Section

This section can appear below the form.

Purpose:
- reassure the user
- clarify what the tool extracts

Example content:
```
JobLens analyzes:
• Role and seniority
• Industry / domain
• Product / system type
• Team and work environment
```
This should stay short.

### 4.4 Home Page Wireframe
```
--------------------------------------------------
JobLens

Decode job descriptions in seconds
Paste a job description and get a structured explanation
of the role, domain, system type, and team environment.

[ Paste job description ]
--------------------------------------------------
|                                                |
|  Paste the full job description here...        |
|                                                |
|                                                |
|                                                |
--------------------------------------------------

[ Analyze Job Description ]

JobLens analyzes:
• Role and seniority
• Industry / domain
• Product / system type
• Team environment
--------------------------------------------------
```

### 4.5 Home Page States
#### Default state
- empty textarea
- analyze button enabled

#### Validation error state
- textarea highlighted
- inline error text visible

#### Loading state

After submission:
- button disabled
- button text changes to something like:
  - Analyzing...
- optional small loading indicator

The loading state should prevent repeated submissions.

---

## 5. Result Page Design
### 5.1 Purpose

The result page displays the structured analysis of the submitted job description.

This page should prioritize:
- scan-ability
- section clarity
- visual separation between the 4 outputs

### 5.2 Main Layout

The result page should contain the following sections, top to bottom:

**Header**

Contains:
- JobLens title
- optional back action

**Submitted Input Preview**

Contains:
- short preview of the pasted job description

**Results Section**

Contains the 4 output cards

**Action Section**

Contains:
- analyze another job description button

### 5.3 Result Page Structure
#### A. Header

Content:
- JobLens logo/text
- optional back link

Example:
```
JobLens
[ Analyze another job description ]
```

#### B. Result Page Title

Main heading:
```
Job Description Analysis
```

Optional subtitle:
```
Here is a structured interpretation of the submitted role.
```

#### C. Submitted Job Description Preview

This section shows a preview of the original input so the user remembers what was analyzed.

Content:
- section title
- small preview card

Example:
```
Submitted Job Description
[ first few lines of JD text... ]
```
Important:
- do not show the entire very long JD by default
- show preview with optional expand later if needed
- for MVP, preview can be truncated

#### D. Analysis Result Cards

The result page should display 4 cards or panels, one for each analysis section.

Each card should have:
- section title
- clear spacing
- readable content
- consistent formatting

### 5.4 Result Section 1 — Role Interpretation

Title:
```
Role Interpretation
```

Content may include:
- role type
- seniority
- primary stack
- engineering focus

Example:
```
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

### 5.5 Result Section 2 — Industry / Domain

Title:
```
Industry / Domain
```
Content may include:
- detected domain
- supporting signals
- implications

Example:
```
Domain: Financial Technology

Signals detected:
• digital assets
• trading platform
• financial markets

Implications:
Experience in regulated systems or financial data processing may be valuable.
```

### 5.6 Result Section 3 — Product / System Type

Title:
```
Product / System Architecture
```

Content may include:
- architecture type
- technology clues
- system characteristics

Example:
```
Architecture signals:
• event-driven architecture
• distributed systems

Technologies detected:
• Kafka
• WebSocket
• Redis

System characteristics:
• real-time data processing
• high-performance backend systems
```

### 5.7 Result Section 4 — Team & Work Environment

Title:
```
Team & Work Environment
```

Content may include:
- collaboration style
- team characteristics
- language/work mode

Example:
```
Team characteristics:
• cross-functional engineering team
• collaboration with business or product teams

Language expectations:
• English-speaking environment

Working style:
• agile development
• ownership of features from design to production
```

### 5.8 Action Section

At the bottom of the result page, include a clear next action.

Primary action:
```
Analyze Another Job Description
```

This should take the user back to the home page.

Optional later:
- copy results
- save result
- export result

Not required in MVP.

### 5.9 Result Page Wireframe
```
--------------------------------------------------
JobLens                            [ Analyze Another ]

Job Description Analysis
Here is a structured interpretation of the submitted role.

Submitted Job Description
--------------------------------------------------
| Python Software Developer ...                  |
| FinTech / Digital Assets / Crypto ...          |
--------------------------------------------------

Role Interpretation
--------------------------------------------------
| Role: Backend / Trading Systems Engineer       |
| Seniority: Mid-Senior                          |
| Primary stack: ...                             |
| Engineering focus: ...                         |
--------------------------------------------------

Industry / Domain
--------------------------------------------------
| Domain: Financial Technology                   |
| Signals detected: ...                          |
| Implications: ...                              |
--------------------------------------------------

Product / System Architecture
--------------------------------------------------
| Architecture signals: ...                      |
| Technologies: ...                              |
| System characteristics: ...                    |
--------------------------------------------------

Team & Work Environment
--------------------------------------------------
| Team characteristics: ...                      |
| Language expectations: ...                     |
| Working style: ...                             |
--------------------------------------------------

[ Analyze Another Job Description ]
--------------------------------------------------
```

---

## 6. UI Components

The MVP can be built using a small set of reusable UI components.

### 6.1 Components List
**Header**

Used on both pages.

**Textarea Input**

Used on home page.

**Primary Button**

Used for:
- analyze action
- analyze another action

**Result Card**

Used for each of the 4 output sections.

**Error Message**

Used for validation and failure states.

**Job Description Preview Card**

Used on result page.

---

## 7. Styling Guidelines
### 7.1 General Look and Feel

The UI should feel:
- professional
- clean
- minimal
- modern
- easy to scan

Avoid:
- heavy gradients
- excessive animations
- cluttered dashboard-like layouts

### 7.2 Layout Width

**Recommended:**
- centered main content container
- comfortable reading width
- not too wide on desktop

**Suggested structure:**
- max-width container for content
- consistent vertical spacing between sections

### 7.3 Typography

Use clear hierarchy:

**Large heading**

For page title / hero title

**Medium heading**

For section titles

**Normal body text**

For descriptions and insights

**Muted small text**

For helper text and subtitles

Results should avoid dense paragraph walls.

### 7.4 Spacing

Use generous spacing between:
- page sections
- cards
- headings and content

This is especially important because the value of the app comes from readability.

### 7.5 Buttons

Primary button should be visually clear and prominent.

Examples:
- Analyze Job Description
- Analyze Another Job Description

Buttons should have:
- hover state
- disabled state
- loading state

---

## 8. UI States
### 8.1 Empty State

Home page before anything is submitted.

### 8.2 Validation Error State

Shown when input is empty or too short.

Example:
```
Please paste a full job description before analyzing.
```

### 8.3 Loading State

Shown after clicking analyze.

Examples:
- button disabled
- text changed to `Analyzing...`

### 8.4 Success State

Result page rendered with all 4 analysis cards.

### 8.5 Failure State

If AI or server processing fails.

Error message example:
```
We could not analyze this job description right now. Please try again.
```
In this state, provide:
- return to home button
- retry option later if desired

---

## 9. Accessibility and Usability Considerations

The MVP should still follow basic usability principles.

### Form usability
- label should be visible
- textarea should be large enough
- button should be clearly discoverable

### Readability
- avoid tiny text
- use strong contrast
- use enough line spacing

### Keyboard support
- textarea should be usable with keyboard only
- button should be focusable
- basic form submission should work reliably

---

## 10. Page Summary
### Home Page

Purpose:
- accept job description input

Main elements:
- title
- subtitle
- textarea
- analyze button
- helper text

### Result Page

Purpose:
- display structured analysis

Main elements:
- title
- job description preview
- 4 result cards
- analyze another button

---

## 11. Final UI Scope for MVP

The UI for MVP includes only:
- 2 pages
- 1 input form
- 4 result sections
- basic validation state
- loading state
- failure state

Anything beyond this is optional and should not block launch.
