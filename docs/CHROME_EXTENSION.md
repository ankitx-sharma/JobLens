# JobLens Chrome Extension – Technical Documentation

## Overview

The JobLens Chrome Extension allows users to analyze job descriptions directly from job pages (e.g., LinkedIn) using an AI-powered backend.

### Key Capabilities

- Extract job description from active tab
- Analyze using backend (Spring Boot + AI)
- Stream structured insights to UI
- Display results in a side panel

---

# Architecture

## Components
Chrome Extension  
│  
├── background.js → Controls side panel behavior  
├── sidepanel.html → UI container  
├── sidepanel.js → Main logic (extraction + API + rendering)  
└── manifest.json → Extension configuration  

---

# Execution Flow

## 1. User Interaction

1. User opens a job description page (e.g., LinkedIn)
2. User clicks extension icon
3. Side panel opens
4. User clicks **"Analyze Job"**

---

## 2. Job Description Extraction

Extraction happens **inside the active tab context** using:

```js
chrome.scripting.executeScript(...)
```

**Why?**
- Side panel cannot access page DOM
- Must execute code inside the page itself

## 3. Extraction Strategy

We use a multi-layer fallback approach:

### Priority 1: Direct Selectors (Fast & Accurate)
```js
#job-details
.jobs-box__html-content
.jobs-description__content
```

### Priority 2: Semantic Extraction (Stable)

Uses heading:
```
"About the job"
```

**Logic:**
Find heading (h1, h2, h3)
Match text → "about the job"
Traverse parent containers
Select container with meaningful content
