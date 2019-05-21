package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Purchase;
import com.example.demo.repository.PurchaseRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class PurchaseService {

	@Autowired PurchaseRepository purchaseRepository;

	public ObservableList<Purchase> getAll() {
		List<Purchase> purchases = purchaseRepository.findAll();
		return purchases.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	public Purchase updatePurchase(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}

	public void deletePurchase(Purchase purchase) {
		purchaseRepository.delete(purchase);
	}
}
