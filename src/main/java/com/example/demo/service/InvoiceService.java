package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Invoice;
import com.example.demo.repository.InvoiceRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class InvoiceService {

	@Autowired InvoiceRepository invoiceRepository;

	public ObservableList<Invoice> getAll() {
		List<Invoice> invoices = invoiceRepository.findAll();
		return invoices.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	public Invoice updateInvoice(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}

	public void deleteInvoice(Invoice invoice) {
		invoiceRepository.delete(invoice);
	}
}
