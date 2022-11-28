package io.github.fvarrui.eclipse.plugin.pasteee.api;

public class PasteSection {

	private String name = "New Paste";
	private String syntax = "autodetect";
	private String contents;

	public PasteSection(String name, String syntax, String contents) {
		super();
		this.name = name;
		this.syntax = syntax;
		this.contents = contents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
