package me.schf.ai.tunnel.web.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HtmlValidatorTests {
	
	@Test
	void test_htmlValidator_valid() {
		String valid = "<p>minecraft</p>";
		assertThat(new HtmlValidator(valid).isValid()).isTrue();
	}
	
	@Test
	void test_htmlValidator_valid_containsExternalLinks() {
		String invalid = "<p>https://google.com</p>";
		assertThat(new HtmlValidator(invalid).doesNotContainExternalLinks().isValid()).isTrue();
	}
	
	@Test
	void test_htmlValidator_valid_doesNotContainExternalLinks() {
		String valid = "<p>minecraft</p>";
		assertThat(new HtmlValidator(valid).doesNotContainExternalLinks().isValid()).isTrue();
	}

}
