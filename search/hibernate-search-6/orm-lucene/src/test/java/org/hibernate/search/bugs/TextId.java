package org.hibernate.search.bugs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public class TextId implements Serializable {

	protected TextId() {
	}

	public TextId(final Locale locale, final String[] elements) {
		setElement(locale, elements);
	}

// ------------------------------------------------------------------------

	@Column(name = "textIdMap", length = 1000)
	@JsonProperty("textIdMap")
	@Convert(converter = LocaleStringArrayMapConverter.class, attributeName = "textIdMap")
	private final Map<Locale, String[]> textIdMap = new HashMap<>();

// ------------------------------------------------------------------------

	public Set<Map.Entry<Locale, String[]>> entrySet() {
		return this.textIdMap.entrySet();
	}

	public void setElement(final Locale locale, final String... textIdElements) {
		this.textIdMap.put(locale, textIdElements);
	}

	@Override
	public String toString() {
		return "TextId" + textIdMap;
	}
}
