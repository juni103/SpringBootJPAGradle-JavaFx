package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.StageManager;
import com.example.demo.controller.form.PurchaseFormController;
import com.example.demo.model.Purchase;
import com.example.demo.service.PurchaseService;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class PurchaseController implements ContainerView {

	@Value("${classpath:/com/example/demo/view/PurchaseView.fxml}")
	private Resource purchaseViewResource;
	
	@Value("${classpath:/com/example/demo/view/form/PurchaseFormView.fxml}")
	private Resource purchaseFormViewResource;

	@FXML private TableView<Purchase> productTable;
    @FXML private TableColumn<Purchase, String> productColumn;
    @FXML private TableColumn<Purchase, String> supplierColumn;
    @FXML private TableColumn<Purchase, Integer> quantityColumn;
    @FXML private TableColumn<Purchase, Float> priceColumn;
    @FXML private TableColumn<Purchase, Float> totalColumn;
    @FXML private TableColumn<Purchase, LocalDate> datetimeColumn;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    
    @Autowired StageManager stageManager;
    @Autowired PurchaseService purchaseService;
    @Autowired PurchaseFormController purchaseFormController;
    
    private ObservableList<Purchase> purchases = FXCollections.observableArrayList();

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
		Purchase purchase = getSelectedPurchase();
		purchaseService.deletePurchase(purchase);
		purchases.remove(purchase);
	}
	
	private void editButtonAction(ActionEvent event) {
		showPurchaseForm(event, getSelectedPurchase());
	}
	
	private void addButtonAction(ActionEvent event) {
		showPurchaseForm(event, null);
	}

	private Purchase getSelectedPurchase() {
		return productTable.getSelectionModel().getSelectedItem();
	}
	
	private void setSuppliersTable() {
		productTable.setItems(purchases);
		
		productColumn.setCellValueFactory(purchase -> new SimpleStringProperty(purchase.getValue().getProduct().getName()));
		supplierColumn.setCellValueFactory(purchase -> new SimpleStringProperty(purchase.getValue().getSupplier().getName()));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("quantity"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("total"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("price"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Float>("total"));
		datetimeColumn.setCellValueFactory(new PropertyValueFactory<Purchase, LocalDate>("datetime"));

		Platform.runLater(() -> purchases.setAll(purchaseService.getAll()));
	}
	
	private void showPurchaseForm(ActionEvent event, Purchase purchase) {
		purchaseFormController.setItem(purchase);
		stageManager.showPurchaseForm(purchaseFormViewResource, purchaseFormController);
		
		if(purchaseFormController.isSaveButtonClicked()) {
			Purchase updatedPurchase = purchaseFormController.getItem();
			if(null != updatedPurchase) {
				if(null != updatedPurchase.getId()) {
					updatePurchase(updatedPurchase);
				} else {
					addPurchase(updatedPurchase);
				}
			}
		}
	}
	
	public void updatePurchase(Purchase purchase) {
		purchase = purchaseService.updatePurchase(purchase);
		productTable.getItems().set(productTable.getSelectionModel().getSelectedIndex(), purchase);
	}
	
	public void addPurchase(Purchase purchase) {
		purchase = purchaseService.updatePurchase(purchase);
		productTable.getItems().add(purchase);
	}

	@Override
	public URL getView() throws IOException {
		return purchaseViewResource.getURL();
	}
	
}
