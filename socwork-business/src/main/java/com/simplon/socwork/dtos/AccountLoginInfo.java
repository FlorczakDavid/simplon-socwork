package com.simplon.socwork.dtos;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public record AccountLoginInfo(String token, List<String> roles) {

	@Override
	public String toString() {
		return "AccountLoginInfo [token=[REDACTED], " + "roles=" + roles + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountLoginInfo)) {
			return false;
		}
		AccountLoginInfo other = (AccountLoginInfo) obj;
		return Objects.equals(token, other.token);
	}

	
}
