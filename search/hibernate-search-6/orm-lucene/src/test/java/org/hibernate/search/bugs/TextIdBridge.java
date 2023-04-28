/*
 * TheVegCat - The Vegan Catalog.
 * Copyright (C) H.Lo
 * mailto:horvoje@gmail.com
 */
package org.hibernate.search.bugs;

import org.hibernate.search.mapper.pojo.bridge.ValueBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.ValueBridgeToIndexedValueContext;

public class TextIdBridge implements ValueBridge<TextId, String> {

	@Override
	public String toIndexedValue(final TextId value, final ValueBridgeToIndexedValueContext context) {
		if (value == null) {
            return "";
        }
		// The exact value is irrelevant
        return value.toString();
	}

}
