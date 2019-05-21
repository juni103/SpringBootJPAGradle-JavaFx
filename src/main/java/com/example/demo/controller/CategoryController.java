package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.StageManager;
import com.example.demo.controller.form.CategoryFormController;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class CategoryController implements ContainerView {
	
	@Value("${classpath:/com/example/demo/view/CategoryView.fxml}")
	private Resource categoryViewResource;

	@Value("${classpath:/com/example/demo/view/form/CategoryFormView.fxml}")
	private Resource categoryFormViewResource;

	@FXML private TableView<Category> categoryTable;
    @FXML private TableColumn<Category, String> typeColumn;
    @FXML private TableColumn<Category, String> descriptionColumn;

    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    
    @Autowired StageManager stageManager;
    @Autowired CategoryService categoryService;
    
    private ObservableList<Category> categories = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addEventListeners();
		setCategoryTable();
	}
	
	private void addEventListeners() {
		deleteButton.setOnAction(this::deleteButtonAction);
		editButton.setOnAction(this::editButtonAction);
		addButton.setOnAction(this::addButtonAction);
	}
	
	private void deleteButtonAction(ActionEvent event) {
		Category category = getSelectedCategory();
		categoryService.deleteCategory(category);
		categories.remove(category);
	}
	
	private void editButtonAction(ActionEvent event) {
		showCategoryForm(event, getSelectedCategory());
	}
	
	private void addButtonAction(ActionEvent event) {
		showCategoryForm(event, null);
	}
	
	private Category getSelectedCategory() {
		return categoryTable.getSelectionModel().getSelectedItem();
	}
	
	private void setCategoryTable() {
		categoryTable.setItems(categories);
		typeColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("type"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Category, String>("description"));

		Platform.runLater(() -> categories.setAll(categoryService.getAll()));
	}
	
	private void showCategoryForm(ActionEvent event, Category category) {
		CategoryFormController categoryFormController = new CategoryFormController();
		categoryFormController.setItem(category);
		
		stageManager.showCategoryForm(categoryFormViewResource, categoryFormController);
		if(categoryFormController.isSaveButtonClicked()) {
			Category updatedcategory = categoryFormController.getItem();
			if(null != updatedcategory) {
				if(null != updatedcategory.getId()) {
					updateCategory(updatedcategory);
				} else {
					addCategory(updatedcategory);
				}
			}
		}
	}
	
	private void updateCategory(Category category) {
		category = categoryService.updateCategory(category);
		categoryTable.getItems().set(categoryTable.getSelectionModel().getSelectedIndex(), category);
	}
	
	private void addCategory(Category category) {
		category = categoryService.updateCategory(category);
		categoryTable.getItems().add(category);
	}
	
	@Override
	public URL getView() throws IOException {
		return categoryViewResource.getURL();
	}
	
}
