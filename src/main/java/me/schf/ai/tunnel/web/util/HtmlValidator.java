package me.schf.ai.tunnel.web.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public class HtmlValidator {

	private Document doc;
	private boolean valid = true;

	public HtmlValidator(String html) {
		try {
			this.doc = Jsoup.parse(html, "", Parser.htmlParser());
			if (doc.body().text().isEmpty()) {
			    valid = false; // no visible content
			}
		} catch (Exception e) {
			valid = false;
		}
	}

	public HtmlValidator doesNotContainExternalLinks() {
		if (!valid) {
			return this;

		}
		if (hasExternalLinks()) {
			valid = false;
		}
		return this;
	}

	public boolean hasExternalLinks() {
		var links = doc.select("a[href]");
		for (var link : links) {
			String href = link.attr("href").toLowerCase();
			if (href.startsWith("http://") || href.startsWith("https://")) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isValid() {
		return valid;
	}

}
