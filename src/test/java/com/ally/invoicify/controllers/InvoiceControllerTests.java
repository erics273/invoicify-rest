package com.ally.invoicify.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.ModelAndView;

import com.ally.invoicify.controllers.InvoiceController;
import com.ally.invoicify.models.BillingRecord;
import com.ally.invoicify.models.Company;
import com.ally.invoicify.models.FlatFeeBillingRecord;
import com.ally.invoicify.models.Invoice;
import com.ally.invoicify.models.InvoiceLineItem;
import com.ally.invoicify.models.InvoiceView;
import com.ally.invoicify.models.RateBasedBillingRecord;
import com.ally.invoicify.models.User;
import com.ally.invoicify.repositories.BillingRecordRepository;
import com.ally.invoicify.repositories.CompanyRepository;
import com.ally.invoicify.repositories.InvoiceRepository;

public class InvoiceControllerTests {
	
	private Authentication authentication;
	private User user;

	@Mock
	private BillingRecordRepository recordRepository;

	@Mock
	private InvoiceRepository invoiceRepository;

	@Mock
	private CompanyRepository companyRepository;

	@InjectMocks
	private InvoiceController controller;

	@Before
	public void setup() {
	    // Initialize mocks created above
	    MockitoAnnotations.initMocks(this);
		user = new User();
		authentication = mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(user);
	}


	@Test
	public void test_createInvoice_converts_billing_record_ids_to_an_invoice() {
		Company company = new Company();
		long[] recordIds = { 1L, 2L };
		List<BillingRecord> records = Arrays.asList(new BillingRecord[] { new FlatFeeBillingRecord(), new RateBasedBillingRecord() }); 
		when(recordRepository.findByIdIn(recordIds)).thenReturn(records);
		when(companyRepository.findOne(3L)).thenReturn(company);
		
		InvoiceView InvoiceView = new InvoiceView("test invoice", recordIds);
		
		Invoice invoice = controller.createInvoice(InvoiceView, 3L, authentication);
		
		verify(authentication).getPrincipal();
		verify(recordRepository).findByIdIn(recordIds);
		verify(companyRepository).findOne(3L);
		verify(invoiceRepository).save(invoice);
		assertThat(invoice.getCreatedBy()).isSameAs(user);
		assertThat(invoice.getCreatedOn()).isNotNull();
		assertThat(invoice.getCompany()).isSameAs(company);
		List<InvoiceLineItem> lineItems = invoice.getLineItems();
		assertThat(lineItems).isNotNull();
		assertThat(lineItems).hasSize(2);
		for (int i = 0; i < 2; i += 1) {
			assertThat(lineItems.get(i).getBillingRecord()).isSameAs(records.get(i));
			assertThat(lineItems.get(i).getCreatedBy()).isSameAs(user);
			assertThat(lineItems.get(i).getCreatedOn()).isNotNull();
			assertThat(lineItems.get(i).getInvoice()).isSameAs(invoice);
		}
	}
	
}













