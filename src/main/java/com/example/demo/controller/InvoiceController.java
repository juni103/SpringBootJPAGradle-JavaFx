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
import com.example.demo.controller.form.InvoiceFormController;
import com.example.demo.model.Invoice;
import com.example.demo.service.InvoiceService;

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
public class InvoiceController implements ContainerView {

	@Value("${classpath:/com/example/demo/view/InvoiceView.fxml}")
	private Resource invloiceViewResource;
	
	@Value("${classpath:/com/example/demo/view/form/InvoiceFormView.fxml}")
	private Resource invoiceFormViewResource;
	
	@FXML private TableView<Invoice> invoiceTable;
    @FXML private TableColumn<Invoice, String> employeeColumn;
    @FXML private TableColumn<Invoice, Float> totalColumn;
    @FXML private TableColumn<Invoice, Float> vatColumn;
    @FXML private TableColumn<Invoice, Float> discountColumn;
    @FXML private TableColumn<Invoice, Float> payableColumn;
    @FXML private TableColumn<Invoice, Float> paidColumn;
    @FXML private TableColumn<Invoice, Float> returnColumn;
    @FXML private TableColumn<Invoice, LocalDate> dateColumn;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    
    @Autowired InvoiceService invoiceService;
    @Autowired StageManager stageManager;
    @Autowired InvoiceFormController invoiceFormController;
    
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();

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
		Invoice invoice = getSelectedInvoice();
		invoiceService.deleteInvoice(invoice);
		invoices.remove(invoice);
	}
	
	private void editButtonAction(ActionEvent event) {
		showInvoiceForm(event, getSelectedInvoice());
	}
	
	private void addButtonAction(ActionEvent event) {
		showInvoiceForm(event, null);
	}

	private Invoice getSelectedInvoice() {
		return invoiceTable.getSelectionModel().getSelectedItem();
	}
	
	private void setSuppliersTable() {
		invoiceTable.setItems(invoices);
		employeeColumn.setCellValueFactory(invoice -> new SimpleStringProperty(
				invoice.getValue().getEmployee().getFirstName().concat(" ").concat(invoice.getValue().getEmployee().getLastName())));
		
		totalColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("total"));
		vatColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("vat"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("discount"));
		payableColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("payable"));
		paidColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("paid"));
		returnColumn.setCellValueFactory(new PropertyValueFactory<Invoice, Float>("returned"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Invoice, LocalDate>("datetime"));

		Platform.runLater(() -> invoices.setAll(invoiceService.getAll()));
	}
	
	private void showInvoiceForm(ActionEvent event, Invoice invoice) {
		invoiceFormController.setItem(invoice);
		stageManager.showForm(invoiceFormViewResource, invoiceFormController);
		
		if(invoiceFormController.isSaveButtonClicked()) {
			Invoice updatedPurchase = invoiceFormController.getItem();
			if(null != updatedPurchase) {
				if(null != updatedPurchase.getId()) {
					updateInvoice(updatedPurchase);
				} else {
					addInvoice(updatedPurchase);
				}
			}
		}
	}
	
	public void updateInvoice(Invoice invoice) {
		invoice = invoiceService.updateInvoice(invoice);
		invoiceTable.getItems().set(invoiceTable.getSelectionModel().getSelectedIndex(), invoice);
	}
	
	public void addInvoice(Invoice invoice) {
		invoice = invoiceService.updateInvoice(invoice);
		invoiceTable.getItems().add(invoice);
	}

	@Override
	public URL getView() throws IOException {
		return invloiceViewResource.getURL();
	}
}
