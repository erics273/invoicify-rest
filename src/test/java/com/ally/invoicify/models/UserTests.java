package com.ally.invoicify.models;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.security.core.GrantedAuthority;

import com.ally.invoicify.models.User;

public class UserTests {

	private User user;

	@Before
	public void setup() {
		user = new User("curtis", "password", "ADMIN");
	}
	
	@Test
	public void test_getAuthorities_returns_proper_authorities() {
		List<? extends GrantedAuthority> actual = user.getAuthorities().stream().collect(Collectors.toList());
		
		assertThat(actual).hasSize(1);
		assertThat(actual.get(0).getAuthority()).isEqualTo("ROLE_ADMIN");
	}

	@Test
	public void test_getters_and_setters() {
		BeanTester tester = new BeanTester();
		tester.testBean(User.class);
	}
	
	@Test
	public void test_default_constructor() {
		User user = new User();
		assertThat(user.getId()).isNull();
		assertThat(user.getPassword()).isNull();
		assertThat(user.getUsername()).isNull();
	}
	
	@Test
	public void test_parameterized_constructor() {
		assertThat(user.getId()).isNull();
		assertThat(user.getPassword()).isEqualTo("password");
		assertThat(user.getUsername()).isEqualTo("curtis");
	}
	
	@Test
	public void test_isAccountNonExpired_returns_true() {
		assertThat(user.isAccountNonExpired()).isTrue();
	}
	
	@Test
	public void test_isAccountNonLocked_returns_true() {
		assertThat(user.isAccountNonLocked()).isTrue();
	}
	
	@Test
	public void test_isCredentialNonExpired_returns_true() {
		assertThat(user.isCredentialsNonExpired()).isTrue();
	}
	
	@Test
	public void test_isEnabled_returns_true() {
		assertThat(user.isEnabled()).isTrue();
	}

}
