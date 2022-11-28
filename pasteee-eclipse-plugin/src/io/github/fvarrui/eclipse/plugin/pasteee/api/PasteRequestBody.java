package io.github.fvarrui.eclipse.plugin.pasteee.api;

import java.util.ArrayList;
import java.util.List;

public class PasteRequestBody {

	private Boolean encrypted = false;
	private String description;
	private List<PasteSection> sections = new ArrayList<>();

	public Boolean getEncrypted() {
		return encrypted;
	}

	public void setEncrypted(Boolean encrypted) {
		this.encrypted = encrypted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PasteSection> getSections() {
		return sections;
	}

	public void setSections(List<PasteSection> sections) {
		this.sections = sections;
	}

}
