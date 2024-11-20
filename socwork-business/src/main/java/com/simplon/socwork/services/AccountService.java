package com.simplon.socwork.services;

import org.springframework.stereotype.Service;

import com.simplon.socwork.dtos.AccountCreate;
import com.simplon.socwork.entities.Account;
import com.simplon.socwork.repositories.AccountJPARepository;

@Service
public class AccountService {
	
	private final AccountJPARepository accounts;
	
	public AccountService(AccountJPARepository accounts) {
		this.accounts = accounts;
	}

	public void create(AccountCreate inputs) {
		Account account = new Account();
		account.setUsername(inputs.username());
		account.setPassword(inputs.password());
		accounts.save(account);
	}

}
