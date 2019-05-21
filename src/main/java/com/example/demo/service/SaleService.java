package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Sale;
import com.example.demo.repository.SaleRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class SaleService {

	@Autowired SaleRepository saleRepository;

	public ObservableList<Sale> getAll() {
		List<Sale> sales = saleRepository.findAll();
		return sales.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	public void deleteSale(Sale sale) {
		saleRepository.delete(sale);
	}

	public Sale updateSale(Sale sale) {
		return saleRepository.save(sale);
	}
}
