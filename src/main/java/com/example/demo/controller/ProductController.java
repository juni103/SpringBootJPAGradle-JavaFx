package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.StageManager;
import com.example.demo.controller.form.ProductFormController;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

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
public class ProductController implements ContainerView {

	@Value("${classpath:/com/example/demo/view/ProductView.fxml}")
	private Resource productViewResource;
	
	@Value("${classpath:/com/example/demo/view/form/ProductFormView.fxml}")
	private Resource productFormViewResource;
	
	@FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> categoryColumn;
    @FXML private TableColumn<Product, String> supplierColumn;
    @FXML private TableColumn<Product, String> productColumn;
    @FXML private TableColumn<Product, Float> priceColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;
    @FXML private TableColumn<Product, String> descriptionColumn;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    
    @Autowired ProductService productService;
    @Autowired StageManager stageManager;
    @Autowired ProductFormController productFormController;
    
    private ObservableList<Product> products = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addEventListeners();
		setProductTable();
	}
	
	private void addEventListeners() {
		deleteButton.setOnAction(this::deleteButtonAction);
		editButton.setOnAction(this::editButtonAction);
		addButton.setOnAction(this::addButtonAction);
	}
	
	private void deleteButtonAction(ActionEvent event) {
		Product product = getSelectedProduct();
		productService.deleteProduct(product);
		products.remove(product);
	}
	
	private void editButtonAction(ActionEvent event) {
		showProductForm(event, getSelectedProduct());
	}
	
	private void addButtonAction(ActionEvent event) {
		showProductForm(event, null);
	}

	private Product getSelectedProduct() {
		return productTable.getSelectionModel().getSelectedItem();
	}
	
	private void setProductTable() {
		productTable.setItems(products);
		categoryColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getCategory().getType()));
		supplierColumn.setCellValueFactory(product -> new SimpleStringProperty(product.getValue().getSupplier().getName()));
		productColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));

		Platform.runLater(() -> products.setAll(productService.getAll()));
	}
	
	private void showProductForm(ActionEvent event, Product product) {
		productFormController.setItem(product);
		stageManager.showProductForm(productFormViewResource, productFormController);
		
		if(productFormController.isSaveButtonClicked()) {
			Product updatedPurchase = productFormController.getItem();
			if(null != updatedPurchase) {
				if(null != updatedPurchase.getId()) {
					updateProduct(updatedPurchase);
				} else {
					addProduct(updatedPurchase);
				}
			}
		}
	}
	
	public void updateProduct(Product product) {
		product = productService.updateProduct(product);
		productTable.getItems().set(productTable.getSelectionModel().getSelectedIndex(), product);
	}
	
	public void addProduct(Product product) {
		product = productService.updateProduct(product);
		productTable.getItems().add(product);
	}

	@Override
	public URL getView() throws IOException {
		return productViewResource.getURL();
	}
}
