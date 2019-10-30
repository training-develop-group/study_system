<<<<<<< HEAD
<<<<<<< HEAD
package com.example.study_system.model;

public class QuestionInfoWithBLOBs extends QuestionInfo {
    private String content;

    private String analysis;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis == null ? null : analysis.trim();
    }
=======
package com.example.study_system.model;

public class QuestionInfoWithBLOBs extends QuestionInfo {
	private String content;

	private String analysis;

	private String optionType;

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis == null ? null : analysis.trim();
	}
>>>>>>> remotes/origin/dev-wtq
=======
package com.example.study_system.model;

public class QuestionInfoWithBLOBs extends QuestionInfo {
	private String content;

	private String analysis;

	private String optionType;

	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis == null ? null : analysis.trim();
	}
>>>>>>> remotes/origin/dev-wtq
}