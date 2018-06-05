package com.ally.invoicify.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ally.invoicify.models.Company;
import com.ally.invoicify.models.RateBasedBillingRecord;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;

@Controller
@RequestMapping("/api/billing-record/rate-based")
public class RateBasedBillingRecordController {

	private BillingRecordRepository recordRepository;
	private CompanyRepository companyRepository;

	public RateBasedBillingRecordController(BillingRecordRepository recordRepository, CompanyRepository companyRepository) {
		this.recordRepository = recordRepository;
		this.companyRepository = companyRepository;
	}

	@PostMapping()
	public RateBasedBillingRecord create(RateBasedBillingRecord record, long clientId, Authentication auth) {
		User user = (User) auth.getPrincipal();
		Company client = companyRepository.findOne(clientId);
		record.setClient(client);
		record.setCreatedBy(user);

		return recordRepository.save(record);
	}

}












