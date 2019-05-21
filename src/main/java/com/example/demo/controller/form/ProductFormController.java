package com.example.demo.controller.form;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.Supplier;
import com.example.demo.service.CategoryService;
import com.example.demo.service.SupplierService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

@Component
public class ProductFormController implements BaseFormController<Product> {

	private Product product;
	private boolean isSaveClicked;
	
	@Autowired CategoryService categoryService;
	@Autowired SupplierService supplierService;
	
	@FXML private ComboBox<Category> categoryCombo;
    @FXML private ComboBox<Supplier> supplierCombo;
    @FXML private TextField productNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField descriptionField;

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxes();
		setFormData();
		addEventListeners();
	}
	
	private void initComboBoxes() {
		categoryCombo.setConverter(new StringConverter<Category>() {
			
			@Override
			public String toString(Category category) {
				return category.getType();
			}
			
			@Override
			public Category fromString(String string) {
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

		categoryCombo.setCellFactory(comboListView -> new ListCell<Category>() {
			@Override
		    protected void updateItem(Category category, boolean isEmpty) {
		        super.updateItem(category, isEmpty);
		        setText(isEmpty ? "" : category.getType());
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
			categoryCombo.setItems(categoryService.getAll());
			supplierCombo.setItems(supplierService.getAllSuppliers());
		});
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateItemAndCloseForm(event));
		cancelButton.setOnAction(event -> closFormByEvent(event));
	}

	private void setFormData() {
		if(null != product && null != product.getId()) {
			categoryCombo.getSelectionModel().select(product.getCategory());
			supplierCombo.getSelectionModel().select(product.getSupplier());
			productNameField.setText(product.getName());
			quantityField.setText(String.valueOf(product.getQuantity()));
			priceField.setText(String.valueOf(product.getPrice()));
			descriptionField.setText(product.getDescription());
		}
	}

	public Product getFormData() {
		if(null == product) {
			product = new Product();
		}

		product.setCategory(categoryCombo.getSelectionModel().getSelectedItem());
		product.setSupplier(supplierCombo.getSelectionModel().getSelectedItem());
		product.setName(productNameField.getText());
		product.setQuantity(Integer.parseInt(quantityField.getText()));
		product.setPrice(Float.parseFloat(priceField.getText()));
		product.setDescription(descriptionField.getText());
		
		return product;
	}

	@Override
	public void setItem(Product product) {
		this.product = product;
	}

	@Override
	public Product getItem() {
		return product;
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
