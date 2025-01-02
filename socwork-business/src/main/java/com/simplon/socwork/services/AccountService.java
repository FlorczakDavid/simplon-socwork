package com.simplon.socwork.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.simplon.socwork.config.JwtProvider;
import com.simplon.socwork.dtos.AccountCreate;
import com.simplon.socwork.dtos.AccountLogin;
import com.simplon.socwork.dtos.AccountLoginInfo;
import com.simplon.socwork.entities.Account;
import com.simplon.socwork.entities.Role;
import com.simplon.socwork.repositories.AccountJPARepository;
import com.simplon.socwork.repositories.RoleJPARepository;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountService {
	
	private final AccountJPARepository accounts;
	
	private final RoleJPARepository roles;
	
	private final PasswordEncoder encoder;
	
	private final JwtProvider provider;
	
//	@Value("${co.simplon.socwork.BCrypt.rounds}")
//	private int rounds;
	
	//injection de dependances
	public AccountService(AccountJPARepository accounts, PasswordEncoder encoder, JwtProvider provider, RoleJPARepository roles) {
		this.accounts = accounts;
		this.roles = roles;
		this.encoder = encoder;
		this.provider = provider;
	}

	@Transactional
	public void create(AccountCreate inputs) {
		Account account = new Account();
		account.setUsername(inputs.username());
//		String salt = BCrypt.gensalt(rounds);
//		String hashedPassword = BCrypt.hashpw(inputs.password(), salt);
		String hashedPassword = encoder.encode(inputs.password());
		account.setPassword(hashedPassword);
		Role defaultRole = roles.findByIsDefaultTrue();
		account.getExercisedRoles().add(defaultRole);
		accounts.save(account);
	}

	public AccountLoginInfo login(AccountLogin inputs) {
		String username = inputs.username();
		String password = inputs.password();
		Account fetchedAccount = accounts.getByUsernameIgnoreCase(username)
									.orElseThrow(() -> new BadCredentialsException(username));
		
		if(!encoder.matches(password, fetchedAccount.getPassword())) {
			throw new BadCredentialsException(password);
		}

		Set<Role> sentRoles = roles.findAllByExercisingAccountsUsername(username); 

		return new AccountLoginInfo(provider.create(username, sentRoles), sentRoles.stream().map(role -> role.getCode()).toList());
	}

	public Object getAccount(String name) {
		// TODO Auto-generated method stub
		return accounts.getByUsernameIgnoreCase(name);
	}

}
