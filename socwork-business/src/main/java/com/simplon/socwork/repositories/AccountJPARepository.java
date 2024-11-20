package com.simplon.socwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simplon.socwork.entities.Account;

@Repository
public interface AccountJPARepository extends JpaRepository<Account, Long> {

}
