package com.project.joblens.service;

import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

	public String buildStructuredJsonPrompt(String jobDescriptionText) {
		return """
	            Return ONLY a valid JSON object.
                No markdown.
                No code fences.
                No explanation outside JSON.

                Use exactly this JSON structure:

                {
                  "actualRole": {
                    "headline": "string",
                    "explanation": "string"
                  },
                  "dominantThemes": [
                    {
                      "themeTitle": "string",
                      "keywords": ["string"],
                      "meaning": "string"
                    }
                  ],
                  "strategicInterpretation": {
                    "whatCompanyLikelyValues": ["string"],
                    "whatThisUsuallyMeansForCandidates": "string"
                  },
                  "positioningAdvice": {
                    "whatToEmphasize": ["string"],
                    "howToThinkAboutMissingSkills": "string"
                  }
                }

                Instructions:
                - Analyze the job description like an experienced career strategist for software engineers.
                - Explain what the company is actually hiring for, not just the literal title.
                - The actualRole.headline should feel like: "This is not just X. This is really Y."
                - actualRole.explanation should explain the real nature of the role in a practical way.
                - Identify 3 to 5 dominant themes in the job description.
                - For each dominant theme:
                  - give a clear theme title
                  - include keywords or signals directly grounded in the JD
                  - explain what that theme implies technically or strategically
                - strategicInterpretation.whatCompanyLikelyValues should list the core qualities, priorities, or strengths the company seems to care about.
                - strategicInterpretation.whatThisUsuallyMeansForCandidates should explain how a candidate should interpret the role strategically.
                - positioningAdvice.whatToEmphasize should list the kinds of experiences, strengths, or themes a candidate should highlight.
                - positioningAdvice.howToThinkAboutMissingSkills should explain how to handle missing tools or technologies in a smart way.
                - Be insightful, practical, and moderately detailed.
                - Do not be overly brief.
                - Do not invent unsupported details.
                - Keep the answer grounded in the job description.

                Job description:
                %s
	            """.formatted(jobDescriptionText);
	}
}
