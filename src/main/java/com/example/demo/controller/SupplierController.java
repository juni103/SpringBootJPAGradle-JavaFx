package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.StageManager;
import com.example.demo.model.Supplier;
import com.example.demo.service.SupplierService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class SupplierController implements Initializable, ContainerView {
	
	@Value("${classpath:/com/example/demo/view/SuppliersView.fxml}")
	private Resource supplierViewResource;
	
	@FXML private TableView<Supplier> suppliersTable;
	@FXML private TableColumn<Supplier, String> nameColumn;
    @FXML private TableColumn<Supplier, String> phoneColumn;
    @FXML private TableColumn<Supplier, String> addressColumn;
    
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
	
    @Autowired StageManager stageManager;
    @Autowired SupplierService supplierService;
    
    private ObservableList<Supplier> suppliers = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addEventListeners();
		setSuppliersTable();
	}
	
	private void addEventListeners() {
		deleteButton.setOnAction(this::deleteButtonAction);
		editButton.setOnAction(this::editButtonAction);
		addButton.setOnAction(this::addButtonAction);
	}
	
	private void deleteButtonAction(ActionEvent event) {
		Supplier supplier = getSelectedSupplier();
		supplierService.deleteSupplier(supplier);
		suppliers.remove(supplier);
	}
	
	private void editButtonAction(ActionEvent event) {
		showSupplierDialog(event, getSelectedSupplier());
	}
	
	private void addButtonAction(ActionEvent event) {
		showSupplierDialog(event, null);
	}
	
	private Supplier getSelectedSupplier() {
		return suppliersTable.getSelectionModel().getSelectedItem();
	}
	
	private void setSuppliersTable() {
		Platform.runLater(() -> suppliers.setAll(supplierService.getAllSuppliers()));
		
		suppliersTable.setItems(suppliers);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Supplier, String>("name"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<Supplier, String>("phone"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Supplier, String>("address"));
	}
	
	private void showSupplierDialog(ActionEvent event, Supplier supplier) {
		Supplier updatedSupplier = stageManager.showSupplierForm(supplier);
		if(null != updatedSupplier) {
			if(null != updatedSupplier.getId()) {
				updateSupplier(updatedSupplier);
			} else {
				addSupplier(updatedSupplier);
			}
		}
	}
	
	public void updateSupplier(Supplier supplier) {
		supplier = supplierService.updateSupplier(supplier);
		suppliersTable.getItems().set(suppliersTable.getSelectionModel().getSelectedIndex(), supplier);
	}
	
	public void addSupplier(Supplier supplier) {
		supplier = supplierService.updateSupplier(supplier);
		suppliersTable.getItems().add(supplier);
	}

	@Override
	public URL getView() throws IOException {
		return supplierViewResource.getURL();
	}
}
