package com.simplon.socwork.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simplon.socwork.dtos.AccountCreate;
import com.simplon.socwork.dtos.AccountLogin;
import com.simplon.socwork.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	//injection de dependances
	private final AccountService service;
	
	public AccountController(AccountService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	void create(@RequestBody AccountCreate inputs) {
		service.create(inputs);
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.CREATED)
	Object login(@RequestBody AccountLogin inputs) {
		return service.login(inputs);
	}
	
	@GetMapping("/toto")
	Object getToto() {
		return "toto";
	}
	
	@GetMapping("/with-role")
	Object withRole() {
		return "with role";
	}
	
	@GetMapping("/account")
	Object getAccount(@RequestBody AccountLogin inputs) {
		return service.getAccount(inputs.username());
	}
}
