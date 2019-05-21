package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class ProductService {

	@Autowired ProductRepository productRepository;

	public ObservableList<Product> getAll() {
		List<Product> products = productRepository.findAll();
		return products.stream().collect(Collectors.toCollection(FXCollections::observableArrayList));
	}

	public Product updateProduct(Product product) {
		return productRepository.save(product);
	}

	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}
}
