package com.example.demo.controller.form;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Product;
import com.example.demo.model.Purchase;
import com.example.demo.model.Supplier;
import com.example.demo.service.ProductService;
import com.example.demo.service.SupplierService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Component
public class PurchaseFormController implements BaseFormController<Purchase> {

	private Purchase purchase;
	private boolean isSaveClicked;
	
	@Autowired ProductService productService;
	@Autowired SupplierService supplierService;
	
	@FXML private ComboBox<Product> productCombo;
    @FXML private ComboBox<Supplier> supplierCombo;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField totalField;
    @FXML private DatePicker purchaseDateDatePicker;

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxes();
		setFormData();
		addEventListeners();
	}
	
	private void initComboBoxes() {
		
		productCombo.setConverter(new StringConverter<Product>() {
			
			@Override
			public String toString(Product product) {
				return product.getName();
			}
			
			@Override
			public Product fromString(String string) {
				return null;
			}
		});
		
		supplierCombo.setConverter(new StringConverter<Supplier>() {
			
			@Override
			public String toString(Supplier supplier) {
				return supplier.getName();
			}
			
			@Override
			public Supplier fromString(String string) {
				return null;
			}
		});

		productCombo.setCellFactory(comboListView -> new ListCell<Product>() {
			@Override
		    protected void updateItem(Product product, boolean isEmpty) {
		        super.updateItem(product, isEmpty);
		        setText(isEmpty ? "" : product.getName());
		    }
		});
		
		supplierCombo.setCellFactory(item -> new ListCell<Supplier>() {
			@Override
		    protected void updateItem(Supplier supplier, boolean isEmpty) {
		        super.updateItem(supplier, isEmpty);
		        setText(isEmpty ? "" : supplier.getName());
		    }
		});
		
		Platform.runLater(() -> {
			productCombo.setItems(productService.getAll());
			supplierCombo.setItems(supplierService.getAllSuppliers());
		});
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateItemAndCloseForm(event));
		cancelButton.setOnAction(event -> closFormByEvent(event));
	}

	private void setFormData() {
		if(null != purchase && null != purchase.getId()) {
			productCombo.getSelectionModel().select(purchase.getProduct());
			supplierCombo.getSelectionModel().select(purchase.getSupplier());
			quantityField.setText(String.valueOf(purchase.getQuantity()));
			priceField.setText(String.valueOf(purchase.getPrice()));
			totalField.setText(String.valueOf(purchase.getTotal()));
			purchaseDateDatePicker.setValue(purchase.getDatetime());
		}
	}

	public Purchase getFormData() {
		if(null == purchase) {
			purchase = new Purchase();
		}

		purchase.setProduct(productCombo.getSelectionModel().getSelectedItem());
		purchase.setSupplier(supplierCombo.getSelectionModel().getSelectedItem());
		purchase.setQuantity(Integer.parseInt(quantityField.getText()));
		purchase.setPrice(Float.parseFloat(priceField.getText()));
		purchase.setTotal(Float.parseFloat(totalField.getText()));
		purchase.setDatetime(purchaseDateDatePicker.getValue());
		
		return purchase;
	}

	@Override
	public void setItem(Purchase purchase) {
		this.purchase = purchase;
	}

	@Override
	public Purchase getItem() {
		return purchase;
	}

	@Override
	public void updateItemAndCloseForm(ActionEvent event) {
		isSaveClicked = true;
		setItem(getFormData());
		closFormByEvent(event);
	}

	@Override
	public void closFormByEvent(ActionEvent event) {
		((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
	}

	@Override
	public boolean isSaveButtonClicked() {
		return isSaveClicked;
	}

}
