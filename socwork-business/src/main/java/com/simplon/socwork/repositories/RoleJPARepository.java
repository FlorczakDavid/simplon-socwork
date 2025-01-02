package com.simplon.socwork.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplon.socwork.entities.Role;

@Repository
public interface RoleJPARepository extends JpaRepository<Role, Long>{

//	boolean existsByName(String role);

	Role findByIsDefaultTrue();
	
	Set<Role> findAllByExercisingAccountsUsername(String unsername);

}
