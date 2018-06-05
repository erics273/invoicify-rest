package com.ally.invoicify.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.ally.invoicify.controllers.RateBasedBillingRecordController;
import com.ally.invoicify.models.Company;
import com.ally.invoicify.models.RateBasedBillingRecord;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;

public class RateBasedBillingRecordControllerTests {

	@Test
	public void test_create_uses_the_principal_and_clienId_to_save_a_record() {
		BillingRecordRepository records = mock(BillingRecordRepository.class);
		CompanyRepository companies = mock(CompanyRepository.class);
		Authentication auth = mock(Authentication.class);
		RateBasedBillingRecord record = new RateBasedBillingRecord();
		User user = new User();
		Company company = new Company();
		when(auth.getPrincipal()).thenReturn(user);
		when(companies.findOne(3l)).thenReturn(company);
		RateBasedBillingRecordController controller = new RateBasedBillingRecordController(records, companies);
		
		RateBasedBillingRecord actual = controller.create(record,  3l, auth);
		
		verify(auth).getPrincipal();
		verify(companies).findOne(3l);
		assertThat(record.getClient()).isSameAs(company);
		assertThat(record.getCreatedBy()).isSameAs(user);
		verify(records).save(record);
	}

}
