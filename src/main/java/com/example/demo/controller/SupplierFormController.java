package com.example.demo.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.model.Supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SupplierFormController implements Initializable {

	private Supplier 	supplier;
	private boolean 	isSaveClicked = false;
	
	@FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setDialogData();
		addEventListeners();
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateSupplierAndCloseDialog(event));
		cancelButton.setOnAction(event -> closeEventStage(event));
	}
	
	private void setDialogData() {
		if(null != supplier && null != supplier.getId()) {
			nameField.setText(supplier.getName());
			phoneField.setText(supplier.getPhone());
			addressField.setText(supplier.getAddress());
		}
	}
	
	private Supplier getDialogData() {
		if(null == supplier) {
			supplier = new Supplier();
		}

		supplier.setName(nameField.getText());
		supplier.setPhone(phoneField.getText());
		supplier.setAddress(addressField.getText());
		
		return supplier;
	}
	
	public void updateSupplierAndCloseDialog(ActionEvent event) {
		isSaveClicked = true;
		setSupplier(getDialogData());
		closeEventStage(event);
	}
	
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public Supplier getSupplier() {
		return supplier;
	}

	public boolean isSaveClicked() {
		return isSaveClicked;
	}
	
	public void closeEventStage(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}
	
}
