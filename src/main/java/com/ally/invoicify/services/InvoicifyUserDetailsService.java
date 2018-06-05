package com.ally.invoicify.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.UserRepository;

@Service
public class InvoicifyUserDetailsService implements UserDetailsService {
	
	private UserRepository usersRepository;
	
	public InvoicifyUserDetailsService(UserRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = usersRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

}















