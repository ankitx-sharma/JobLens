package com.project.joblens.ai.helper;

import java.util.Collections;
import java.util.List;

import com.project.joblens.dto.DominantThemeDto;
import com.project.joblens.dto.StructuredJobAnalysisDto;

public class JobAnalysisDataHelper {
	
	public static String ACTUAL_ROLE_TEXT = "\"actualRole\":{";
	public static String DOMINANT_THEMES_TEXT = "\"dominantThemes\":[";
	public static String STRATEGIC_INTERPRETATION_TEXT = "\"strategicInterpretation\":{";
	public static String POSITIONING_ADVICE_TEXT = "\"positioningAdvice\":{";
	
	public static List<DominantThemeDto> safeThemes(List<DominantThemeDto> themes) {
        return themes != null ? themes : Collections.emptyList();
    }
	
	public static String extractJson(String rawResponse) {
	    if (rawResponse == null || rawResponse.isBlank()) {
	        throw new RuntimeException("AI response is empty.");
	    }

	    int start = rawResponse.indexOf("{");
	    int end = rawResponse.lastIndexOf("}");

	    if (start == -1 || end == -1 || start >= end) {
	        throw new RuntimeException("No valid JSON object found in AI response: " + rawResponse);
	    }

	    return rawResponse.substring(start, end + 1);
	}
	
	public static String toActualRoleText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getActualRole() == null) {
            return "";
        }
        return "Headline: " + nullSafe(parsed.getActualRole().getHeadline())
                + "\nExplanation: " + nullSafe(parsed.getActualRole().getExplanation())
                + "\nCandidate relevance hint: " + nullSafe(parsed.getActualRole().getCandidateRelevanceHint());
    }
	
	public static String toThemesText(List<DominantThemeDto> themes) {
        if (themes == null || themes.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < themes.size(); i++) {
            DominantThemeDto theme = themes.get(i);
            sb.append("Theme ").append(i + 1).append(": ").append(nullSafe(theme.getThemeTitle())).append("\n")
              .append("Keywords: ").append(theme.getKeywords() != null ? theme.getKeywords() : Collections.emptyList()).append("\n")
              .append("Meaning: ").append(nullSafe(theme.getMeaning())).append("\n\n");
        }
        return sb.toString().trim();
    }
	
	public static String toStrategicInterpretationText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getStrategicInterpretation() == null) {
            return "";
        }
        return "What company likely values: "
                + (parsed.getStrategicInterpretation().getWhatCompanyLikelyValues() != null
                ? parsed.getStrategicInterpretation().getWhatCompanyLikelyValues()
                : Collections.emptyList())
                + "\nWhat this usually means for candidates: "
                + nullSafe(parsed.getStrategicInterpretation().getWhatThisUsuallyMeansForCandidates());
    }
	
	public static String toPositioningAdviceText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getPositioningAdvice() == null) {
            return "";
        }
        return "What to emphasize: "
                + (parsed.getPositioningAdvice().getWhatToEmphasize() != null
                ? parsed.getPositioningAdvice().getWhatToEmphasize()
                : Collections.emptyList())
                + "\nHow to think about missing skills: "
                + nullSafe(parsed.getPositioningAdvice().getHowToThinkAboutMissingSkills());
    }
	
	public static String nullSafe(String value) {
        return value != null ? value : "";
    }
	
	public static String buildPreview(String input) {
		String trimmed = input == null ? "" : input.trim();
		return trimmed.length() > 300 ? trimmed.substring(0, 300) + "..." : trimmed;
	}
}
