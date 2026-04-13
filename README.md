# JobLens

JobLens is a **self-hosted tool** that helps users analyze job descriptions and compare them against their profile or resume.

It is designed for **local use**, giving you full control over your data while leveraging local AI models.

---

## Overview

JobLens helps you:

* Extract key requirements from job descriptions
* Compare job postings with your profile or resume
* Identify skill gaps
* Generate structured insights for better job targeting

---

## Project Scope

This repository contains:

* **Backend** (Spring Boot)
* **Web App**
* **Chrome Extension**

The system is designed to run **locally**, with the Chrome extension interacting with a locally running backend.

---

## Status

**Feature-complete personal project**

* Fully functional for local usage
* Public for **personal use, learning, and experimentation**
* Not deployed as a hosted service
* Not published on the Chrome Web Store

---

## How It Works

1. You open a job posting (e.g., on LinkedIn)
2. The Chrome extension extracts job data
3. The data is sent to the local backend
4. The backend processes it using a local AI model (via Ollama)
5. Results are returned and displayed to you

---

## Architecture

* **Backend:** Java (Spring Boot)
* **Database:** PostgreSQL
* **AI Runtime:** Ollama (local LLM)
* **Frontend:** Web UI + Chrome Extension

---

## Quick Start (Local Setup)

### Prerequisites

Make sure you have installed:

* Java 21
* PostgreSQL
* Ollama → https://ollama.com
* Node.js (if frontend requires it)
* Google Chrome

---

### High-Level Setup

1. **Start PostgreSQL**

   * Create a database (e.g., `joblens`)
   * Update credentials if needed

2. **Start Ollama**

   ```bash
   ollama pull llama3
   ollama serve
   ```

3. **Run the Backend**

   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

4. **Load Chrome Extension**

   * Open Chrome → `chrome://extensions/`
   * Enable **Developer Mode**
   * Click **Load unpacked**
   * Select the `extension/` folder

5. **Use the App**

   * Open a LinkedIn job page
   * Activate the extension
   * View analysis results

---

## Configuration

The backend is configured via:

* `backend/src/main/resources/application.yml`

Default setup assumes:

* PostgreSQL running on `localhost:5432`
* Ollama running on `http://localhost:11434`

You can modify these values as needed.

---

## Important Notes

* This is a **local-first project** — no cloud services required
* Your data stays on your machine
* The Chrome extension depends on a **running local backend**
* Not production-hardened for multi-user or public deployment

---

## Security

* Do **not** expose your backend publicly without proper security measures
* Do **not** commit real credentials to the repository
* Review configuration before deploying outside localhost

---

## Contributing

This is primarily a personal project.

* Issues can be opened for discussion
* Contributions are not actively managed but may be reviewed

---

## License

This project is licensed under the **Apache License 2.0**.

---

## Future Improvements (Optional)

* Improved UI/UX
* Better model customization
* Deployment options (Docker)
* Chrome Web Store packaging

---

## Repository

GitHub: https://github.com/ankitx-sharma/JobLens

---
