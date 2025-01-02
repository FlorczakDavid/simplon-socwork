package com.simplon.socwork.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_roles")
public class Role extends AbstractEntity {
	
	@JsonIgnore
	@ManyToMany(mappedBy = "exercisedRoles", fetch = FetchType.EAGER)
	Set<Account> exercisingAccounts = new HashSet<>();

	@Column(name = "code") // ROLE_ + ...
	private String code;
	
	@Column(name = "is_default")
	private Boolean isDefault;

	public Role() {
		// Required by ORM
	}

	public String getCode() {
		return code;
	}

	public void setcode(String code) {
		this.code = code;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Set<Account> getExercisingAccounts() {
		return exercisingAccounts;
	}

	public void setExercisingAccounts(Set<Account> exercisingAccounts) {
		this.exercisingAccounts = exercisingAccounts;
	}

	//avoid collectons
	@Override
	public String toString() {
		return "Role [exercisingAccounts=LAZY_LOADED, code=" + code + ", isDefault=" + isDefault + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		return Objects.equals(code, other.code);
	}
	
	
}
