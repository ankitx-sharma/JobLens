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
