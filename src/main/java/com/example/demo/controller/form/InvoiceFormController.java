package com.example.demo.controller.form;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Employee;
import com.example.demo.model.Invoice;
import com.example.demo.service.EmployeeService;

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
public class InvoiceFormController implements BaseFormController<Invoice> {

	private Invoice invoice;
	private boolean isSaveClicked;
	
	@Autowired EmployeeService employeeService;
	
	@FXML private ComboBox<Employee> employeeCombo;
	@FXML private TextField totalField;
	@FXML private TextField vatField;
	@FXML private TextField discountField;
	@FXML private TextField payableField;
    @FXML private TextField paidField;
    @FXML private TextField returnedField;

    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initComboBoxes();
		setFormData();
		addEventListeners();
	}
	
	private void initComboBoxes() {
		
		employeeCombo.setConverter(new StringConverter<Employee>() {
			
			@Override
			public String toString(Employee employee) {
				return employee.getFullName();
			}
			
			@Override
			public Employee fromString(String string) {
				return null;
			}
		});

		employeeCombo.setCellFactory(comboListView -> new ListCell<Employee>() {
			@Override
		    protected void updateItem(Employee employee, boolean isEmpty) {
		        super.updateItem(employee, isEmpty);
		        setText(isEmpty ? "" : employee.getFullName());
		    }
		});
		
		Platform.runLater(() -> {
			employeeCombo.setItems(employeeService.getAll());
		});
	}
	
	private void addEventListeners() {
		saveButton.setOnAction(event -> updateItemAndCloseForm(event));
		cancelButton.setOnAction(event -> closFormByEvent(event));
	}

	private void setFormData() {
		if(null != invoice && null != invoice.getId()) {
			employeeCombo.getSelectionModel().select(invoice.getEmployee());
			totalField.setText(String.valueOf(invoice.getTotal()));
			vatField.setText(String.valueOf(invoice.getVat()));
			discountField.setText(String.valueOf(invoice.getDiscount()));
			payableField.setText(String.valueOf(invoice.getPayable()));
			paidField.setText(String.valueOf(invoice.getPaid()));
			returnedField.setText(String.valueOf(invoice.getReturned()));
		}
	}

	public Invoice getFormData() {
		if(null == invoice) {
			invoice = new Invoice();
			invoice.setDatetime(LocalDate.now());
		}

		invoice.setEmployee(employeeCombo.getSelectionModel().getSelectedItem());
		invoice.setTotal(Float.parseFloat(totalField.getText()));
		invoice.setVat(Float.parseFloat(vatField.getText()));
		invoice.setDiscount(Float.parseFloat(discountField.getText()));
		invoice.setPayable(Float.parseFloat(payableField.getText()));
		invoice.setPaid(Float.parseFloat(paidField.getText()));
		invoice.setReturned(Float.parseFloat(returnedField.getText()));
		
		return invoice;
	}

	@Override
	public void setItem(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public Invoice getItem() {
		return invoice;
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
