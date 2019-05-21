package com.example.demo.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Service
@Transactional
public class CategoryService {

	@Autowired CategoryRepository categoryRepository;

	public ObservableList<Category> getAll() {
		Iterable<Category> categoryIterator = this.categoryRepository.findAll();
		ObservableList<Category> categories = FXCollections.observableArrayList();
		categoryIterator.forEach(category -> categories.add(category));
		
		return categories;
	}

	public void deleteCategory(Category category) {
		categoryRepository.delete(category);
	}

	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}
}
