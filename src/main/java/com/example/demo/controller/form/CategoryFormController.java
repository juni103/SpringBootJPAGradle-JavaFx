package com.example.demo.controller.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.model.Category;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryFormController implements Initializable, BaseFormController<Category> {
	
	private boolean isSaveClicked;
	private Category category;

	@FXML private TextField typeField;
    @FXML private TextField descriptionField;
	@FXML private Button cancelButton;
    @FXML private Button saveButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFormData();
		addEventListeners();
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateItemAndCloseForm(event));
		cancelButton.setOnAction(event -> closFormByEvent(event));
	}

	private void setFormData() {
		if(null != category && null != category.getId()) {
			typeField.setText(category.getType());
			descriptionField.setText(category.getDescription());
		}
	}

	public Category getFormData() {
		if(null == category) {
			category = new Category();
		}

		category.setType(typeField.getText());
		category.setDescription(descriptionField.getText());
		
		return category;
	}
	
	@Override
	public void updateItemAndCloseForm(ActionEvent event) {
		isSaveClicked = true;
		setItem(getFormData());
		closFormByEvent(event);
	}

	@Override
	public void setItem(Category category) {
		this.category = category;
	}

	@Override
	public Category getItem() {
		return category;
	}

	@Override
	public boolean isSaveButtonClicked() {
		return isSaveClicked;
	}
	
	@Override
	public void closFormByEvent(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
	
}
