const BACKEND_URL = "http://localhost:8080/analyze/extension";

const analyzeBtn = document.getElementById("analyzeBtn");
const statusText = document.getElementById("statusText");
const statusDetail = document.getElementById("statusDetail");

const actualRoleHeadline = document.getElementById("actualRoleHeadline");
const actualRoleExplanation = document.getElementById("actualRoleExplanation");
const actualRoleHint = document.getElementById("actualRoleHint");

const dominantThemesContainer = document.getElementById("dominantThemesContainer");
const dominantThemesPlaceholder = document.getElementById("dominantThemesPlaceholder");

const strategicValues = document.getElementById("strategicValues");
const strategicMeaning = document.getElementById("strategicMeaning");

const positioningEmphasis = document.getElementById("positioningEmphasis");
const positioningMissingSkills = document.getElementById("positioningMissingSkills");

function escapeHtml(text) {
  const div = document.createElement("div");
  div.textContent = text || "";
  return div.innerHTML;
}

function renderList(container, items) {
  container.innerHTML = "";
  (items || []).forEach(item => {
    const li = document.createElement("li");
    li.textContent = item;
    container.appendChild(li);
  });
}

function renderDominantTheme(index, theme) {
  if (dominantThemesPlaceholder) {
    dominantThemesPlaceholder.remove();
  }

  let block = document.getElementById("theme-" + index);
  if (!block) {
    block = document.createElement("div");
    block.className = "theme-block";
    block.id = "theme-" + index;
    dominantThemesContainer.appendChild(block);
  }

  block.innerHTML = `
    <h3>${index + 1}. ${escapeHtml(theme.themeTitle || "")}</h3>
    <p><strong>Keywords / Signals:</strong></p>
    <ul>${(theme.keywords || []).map(k => `<li>${escapeHtml(k)}</li>`).join("")}</ul>
    <p><strong>What this means:</strong></p>
    <p>${escapeHtml(theme.meaning || "")}</p>
  `;
}

async function getActiveTab() {
  const tabs = await chrome.tabs.query({ active: true, currentWindow: true });
  return tabs[0];
}

async function extractJobDescriptionFromActiveTab() {
  const tab = await getActiveTab();

  if (!tab?.id) {
    throw new Error("No active tab found.");
  }

  const results = await chrome.scripting.executeScript({
    target: { tabId: tab.id, allFrames: true },
    func: async () => {
      function getJobDescriptionElement() {
        const selectors = [
          "#job-details",
          ".jobs-description__content",
          ".jobs-box__html-content",
          ".jobs-description-content__text",
          "[class*='jobs-description']"
        ];

        for (const selector of selectors) {
          const elements = document.querySelectorAll(selector);

          for (const el of elements) {
            if (el && el.innerText && el.innerText.trim().length > 100) {
              return el;
            }
          }
        }

        return null;
      }

      async function waitForJobDescription(maxAttempts = 20, delayMs = 500) {
        for (let attempt = 1; attempt <= maxAttempts; attempt++) {
          const element = getJobDescriptionElement();
          if (element) {
            return element.innerText.trim();
          }
          await new Promise(resolve => setTimeout(resolve, delayMs));
        }
        return "";
      }

      return await waitForJobDescription();
    }
  });

  for (const res of results) {
    if (res?.result && res.result.trim().length > 100) {
      return res.result.trim();
    }
  }
  return "";
}

async function startStreamingAnalysis(jobDescriptionText) {
  statusText.textContent = "Starting analysis...";
  statusDetail.textContent = "Connecting to backend stream, it might take a moment...";

  const response = await fetch(BACKEND_URL, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
    },
    body: new URLSearchParams({ jobDescriptionText }).toString()
  });

  const contentType = response.headers.get("content-type") || "";

  if (!response.ok || !response.body) {
    throw new Error("Failed to start streaming analysis.");
  }

  if (!contentType.includes("text/event-stream")) {
    throw new Error("Backend did not return an SSE stream.");
  }

  const reader = response.body.getReader();
  const decoder = new TextDecoder();
  let buffer = "";

  statusText.textContent = "Analyzing job description...";
  statusDetail.textContent = "Sections will appear below as they become ready.";

  while (true) {
    const { value, done } = await reader.read();

    if (done) {
      if (buffer.trim()) {
        handleEventBlock(buffer);
      }
      break;
    }

    buffer += decoder.decode(value, { stream: true });
    const events = buffer.split(/\r?\n\r?\n/);
    buffer = events.pop() || "";
    events.forEach(handleEventBlock);
  }
}

function handleEventBlock(eventBlock) {
  const lines = eventBlock.split(/\r?\n/);
  let eventName = "message";
  const dataLines = [];

  lines.forEach(line => {
    if (line.startsWith("event:")) {
      eventName = line.slice(6).trim();
    } else if (line.startsWith("data:")) {
      dataLines.push(line.startsWith("data: ") ? line.slice(6) : line.slice(5));
    }
  });

  const eventData = dataLines.join("\n");
  if (!eventData) return;

  try {
    if (eventName === "actualRole") {
      const data = JSON.parse(eventData);
      actualRoleHeadline.textContent = data.headline || "";
      actualRoleExplanation.textContent = data.explanation || "";
      actualRoleHint.textContent = data.candidateRelevanceHint || "";
      return;
    }

    if (eventName === "dominantTheme") {
      const payload = JSON.parse(eventData);
      renderDominantTheme(payload.index, payload.data);
      return;
    }

    if (eventName === "strategicInterpretation") {
      const data = JSON.parse(eventData);
      renderList(strategicValues, data.whatCompanyLikelyValues || []);
      strategicMeaning.textContent = data.whatThisUsuallyMeansForCandidates || "";
      return;
    }

    if (eventName === "positioningAdvice") {
      const data = JSON.parse(eventData);
      renderList(positioningEmphasis, data.whatToEmphasize || []);
      positioningMissingSkills.textContent = data.howToThinkAboutMissingSkills || "";
      return;
    }

    if (eventName === "error") {
      statusText.textContent = "Analysis failed.";
      statusDetail.textContent = eventData;
      return;
    }

    if (eventName === "done") {
      statusText.textContent = "Analysis complete.";
      statusDetail.textContent = "All sections have been generated.";
    }
  } catch (e) {
    console.error("Failed handling SSE event:", eventName, e, eventData);
    statusText.textContent = "Rendering error.";
    statusDetail.textContent = "A streamed section could not be processed.";
  }
}

async function analyzeCurrentJob() {
  try {
    statusText.textContent = "Reading current page...";
    statusDetail.textContent = "Extracting job description from the active tab.";

    const jobDescriptionText = await extractJobDescriptionFromActiveTab();

    if (!jobDescriptionText || !jobDescriptionText.trim()) {
      statusText.textContent = "No job description found.";
      statusDetail.textContent = "Please open a LinkedIn job details page and try again.";
      return;
    }

    await startStreamingAnalysis(jobDescriptionText);
  } catch (error) {
    console.error(error);
    statusText.textContent = "Failed to analyze the job description.";
    statusDetail.textContent = error.message;
  }
}

analyzeBtn.addEventListener("click", analyzeCurrentJob);

document.addEventListener("DOMContentLoaded", () => {
  statusText.textContent = "Ready to analyze.";
  statusDetail.textContent = "Open a LinkedIn job page and click Analyze Job.";
});