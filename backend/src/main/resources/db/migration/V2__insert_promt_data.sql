insert into prompts (prompt_name, version, prompt_text, is_active)
values (
	'JOB_ANALYSIS',
	'V1',
	'Return ONLY newline-delimited JSON.
	    Do not return markdown.
	    Do not return code fences.
	    Do not return one large JSON object.
	    Return one valid JSON object per line.
		
		Output exactly in this order:
	
	    1. actualRole
	    2. dominantTheme (for each theme, one line per theme)
	    3. strategicInterpretation
	    4. positioningAdvice
	
	    Use these exact shapes:
	
	    {"type":"actualRole","data":{"headline":"string","explanation":"string","candidateRelevanceHint":"string"}}
	
	    {"type":"dominantTheme","index":0,"data":{"themeTitle":"string","keywords":["string"],"meaning":"string"}}
	
	    {"type":"strategicInterpretation","data":{"whatCompanyLikelyValues":["string"],"whatThisUsuallyMeansForCandidates":"string"}}
	
	    {"type":"positioningAdvice","data":{"whatToEmphasize":["string"],"howToThinkAboutMissingSkills":"string"}}
	
		Rules:
	    - each line must be a complete valid JSON object
	    - do not split a JSON object across multiple lines intentionally
	    - do not include commentary outside JSON
	    - keep the order fixed
	    - identify 3 to 5 dominant themes
		
		Instructions:
		- Analyze the job description like an experienced career strategist for software engineers.
		- Explain what the company is actually hiring for, not just the literal title.
		- The actualRole.headline should feel like: "This is not just X. This is really Y."
		- actualRole.explanation should explain the real nature of the role in a practical way.
		- actualRole.candidateRelevanceHint should be a general hint for candidates whose backgrounds may be adjacent rather than identical.
		- candidateRelevanceHint should start with wording similar to:
		  "If your experience includes..."
		- candidateRelevanceHint should mention adjacent experience types that may still make the role relevant.
		- Do not assume a specific candidate background.
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
		%s',
      true
);