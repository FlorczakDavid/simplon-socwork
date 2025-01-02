package com.simplon.socwork.dtos;

import java.util.Objects;

public record AccountLogin(String username, String password) {

	@Override
	public String toString() {
		//password is protected
		return "AccountCreate [username=" + username + ", password=[REDACTED]]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountLogin)) {
			return false;
		}
		AccountLogin other = (AccountLogin) obj;
		return Objects.equals(username, other.username);
	}

}
