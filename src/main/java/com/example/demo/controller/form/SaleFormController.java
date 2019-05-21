package com.example.demo.controller.form;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Invoice;
import com.example.demo.model.Product;
import com.example.demo.model.Sale;
import com.example.demo.service.InvoiceService;
import com.example.demo.service.ProductService;

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
public class SaleFormController implements BaseFormController<Sale> {

	private Sale sale;
	private boolean isSaveClicked;
	
	@Autowired ProductService productService;
	@Autowired InvoiceService invoiceService;
	
	@FXML private ComboBox<Invoice> invoiceCombo;
	@FXML private ComboBox<Product> productCombo;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField totalField;

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
		
		invoiceCombo.setConverter(new StringConverter<Invoice>() {
			
			@Override
			public String toString(Invoice invoice) {
				return String.valueOf(invoice.getId());
			}
			
			@Override
			public Invoice fromString(String string) {
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
		
		invoiceCombo.setCellFactory(item -> new ListCell<Invoice>() {
			@Override
		    protected void updateItem(Invoice invoice, boolean isEmpty) {
		        super.updateItem(invoice, isEmpty);
		        setText(isEmpty ? "" : String.valueOf(invoice.getId()));
		    }
		});
		
		Platform.runLater(() -> {
			productCombo.setItems(productService.getAll());
			invoiceCombo.setItems(invoiceService.getAll());
		});
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateItemAndCloseForm(event));
		cancelButton.setOnAction(event -> closFormByEvent(event));
	}

	private void setFormData() {
		if(null != sale && null != sale.getId()) {
			productCombo.getSelectionModel().select(sale.getProduct());
			invoiceCombo.getSelectionModel().select(sale.getInvoice());
			quantityField.setText(String.valueOf(sale.getQuantity()));
			priceField.setText(String.valueOf(sale.getPrice()));
			totalField.setText(String.valueOf(sale.getTotal()));
		}
	}

	public Sale getFormData() {
		if(null == sale) {
			sale = new Sale();
			sale.setDatetime(LocalDate.now());
		}

		sale.setProduct(productCombo.getSelectionModel().getSelectedItem());
		sale.setInvoice(invoiceCombo.getSelectionModel().getSelectedItem());
		sale.setQuantity(Integer.parseInt(quantityField.getText()));
		sale.setPrice(Float.parseFloat(priceField.getText()));
		sale.setTotal(Float.parseFloat(totalField.getText()));
		
		return sale;
	}

	@Override
	public void setItem(Sale sale) {
		this.sale = sale;
	}

	@Override
	public Sale getItem() {
		return sale;
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
