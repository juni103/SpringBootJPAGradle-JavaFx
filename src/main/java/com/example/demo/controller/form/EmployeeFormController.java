package com.example.demo.controller.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.example.demo.model.Employee;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeFormController implements BaseFormController<Employee> {

	private boolean isSaveClicked;
	private Employee employee;

	@FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField addressField;
    @FXML private TextField employeeTypeField;
    @FXML private TextField phoneField;

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
		if(null != employee && null != employee.getId()) {
			firstNameField.setText(employee.getFirstName());
			lastNameField.setText(employee.getLastName());
			usernameField.setText(employee.getUsername());
			passwordField.setText(employee.getPassword());
			addressField.setText(employee.getAddress());
			phoneField.setText(employee.getPhone());
			employeeTypeField.setText(employee.getType());
		}
	}

	public Employee getFormData() {
		if(null == employee) {
			employee = new Employee();
		}

		employee.setFirstName(firstNameField.getText());
		employee.setLastName(lastNameField.getText());
		employee.setUsername(usernameField.getText());
		employee.setPassword(passwordField.getText());
		employee.setAddress(addressField.getText());
		employee.setPhone(phoneField.getText());
		employee.setType(employeeTypeField.getText());
		
		return employee;
	}

	@Override
	public void setItem(Employee employee) {
		this.employee = employee;
	}

	@Override
	public Employee getItem() {
		return employee;
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
