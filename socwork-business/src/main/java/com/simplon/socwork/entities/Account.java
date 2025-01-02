package com.simplon.socwork.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_accounts")
public class Account extends AbstractEntity {
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name="t_exercise",
		joinColumns = @JoinColumn(name = "account_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> exercisedRoles = new HashSet<>();
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	public  Account() {
		// Required by ORM
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getExercisedRoles() {
		return exercisedRoles;
	}

	public void setExercisedRoles(Set<Role> exercisedRoles) {
		this.exercisedRoles = exercisedRoles;
	}

	@Override
	public String toString() {
		return "Account [exercisedRoles=LAZY_LOADED, username=" + username + ", password=[REDACTED]" + "]";
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
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return Objects.equals(username, other.username);
	}
	
	
}
