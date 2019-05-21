package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Supplier;
import com.example.demo.repository.SupplierRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class SupplierService {
	
	@Autowired SupplierRepository supplierRepository;
	
	public ObservableList<Supplier> getAllSuppliers() {
		Iterable<Supplier> suppliersIt = this.supplierRepository.findAll();
		ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
		suppliersIt.forEach(supplier -> suppliers.add(supplier));
		
		return suppliers;
	}

	public Supplier updateSupplier(Supplier supplier) {
		return supplierRepository.save(supplier);
	}

	public void deleteSupplier(Supplier supplier) {
		supplierRepository.delete(supplier);
	}
}
