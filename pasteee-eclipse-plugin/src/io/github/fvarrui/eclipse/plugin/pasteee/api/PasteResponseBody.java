package io.github.fvarrui.eclipse.plugin.pasteee.api;

public class PasteResponseBody {

	private String id;
	private String link;
	private Boolean success;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
