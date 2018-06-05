package com.ally.invoicify.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ally.invoicify.models.Company;
import com.ally.invoicify.models.FlatFeeBillingRecord;
import com.ally.invoicify.models.RateBasedBillingRecord;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;
import com.ally.invoicify.repositories.UserRepository;

@Configuration
@Profile("development")
public class SeedData {
	
	public SeedData(BillingRecordRepository recordRepository, CompanyRepository companyRepository, UserRepository usersRepository, PasswordEncoder encoder) {
		usersRepository.save(new User("curtis", encoder.encode("password")));
		User admin = usersRepository.save(new User("admin", encoder.encode("admin")));
		User clerk = usersRepository.save(new User("clerk", encoder.encode("clerk")));
		usersRepository.save(new User("accountant", encoder.encode("accountant")));
		
		Company ajax = companyRepository.save(new Company("AJAX Ltd."));
		Company lomax = companyRepository.save(new Company("Lomax Brothers, LLC"));
		
		recordRepository.save(new FlatFeeBillingRecord(300, "Faxes", clerk, ajax));
		recordRepository.save(new FlatFeeBillingRecord(1.75, "Socks", clerk, ajax));
		recordRepository.save(new FlatFeeBillingRecord(500, "Paper", clerk, lomax));
		recordRepository.save(new FlatFeeBillingRecord(72.33, "Stockings", clerk, lomax));
		recordRepository.save(new FlatFeeBillingRecord(142.99, "Paint", admin, lomax));
		
		recordRepository.save(new RateBasedBillingRecord(500, 3.5, "Legal services", clerk, ajax));
		recordRepository.save(new RateBasedBillingRecord(150, 2.5, "Painting", clerk, ajax));
		recordRepository.save(new RateBasedBillingRecord(100, 4.25, "House cleaning", clerk, ajax));
		recordRepository.save(new RateBasedBillingRecord(700, 8, "Palm reading", admin, lomax));
		recordRepository.save(new RateBasedBillingRecord(1.57, 25, "Show shining", clerk, lomax));
	}

}


















