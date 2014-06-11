package com.omahaBot.model;

import lombok.Data;

@Data
public class StringPattern {

	private static final char ANY = 'x';

	private final String pattern;

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringPattern other = (StringPattern) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else {
			if (pattern.contains(String.valueOf(ANY))) {
				for (int i = 0; i < pattern.length(); i++) {
					char c = pattern.charAt(i);

					if (c != ANY && c != other.pattern.charAt(i)) {
						return false;
					}

					i++;
				}

				return true;
			} else {
				return pattern.equals(other.pattern);
			}
		}
		return false;
	}
}
