console.log("JobLens background loaded");

chrome.runtime.onInstalled.addListener(async () => {
  try {
    await chrome.sidePanel.setPanelBehavior({
      openPanelOnActionClick: true
    });
    console.log("Side panel behavior set");
  } catch (error) {
    console.error("Failed to set side panel behavior:", error);
  }
});