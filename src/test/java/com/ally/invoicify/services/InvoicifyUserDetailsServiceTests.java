package com.ally.invoicify.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.UserRepository;
import com.ally.invoicify.services.InvoicifyUserDetailsService;

public class InvoicifyUserDetailsServiceTests {
	
	@Test
	public void test_loadByUsername_calls_the_repo() {
		User user = new User();
		UserRepository repo = mock(UserRepository.class);
		when(repo.findByUsername("curtis")).thenReturn(user);
		InvoicifyUserDetailsService service = new InvoicifyUserDetailsService(repo);
		
		UserDetails actual = service.loadUserByUsername("curtis");
		
		assertThat(actual).isSameAs(user);
	}
	
	@Test
	public void test_loadByUsername_throws_error_when_username_not_found() {
		UserRepository repo = mock(UserRepository.class);
		when(repo.findByUsername("curtis")).thenReturn(null);
		InvoicifyUserDetailsService service = new InvoicifyUserDetailsService(repo);
		
		try {
			service.loadUserByUsername("curtis");
			fail("Did not throw exception when no user found.");
		} catch (UsernameNotFoundException unfe) {}
	}
	
}
