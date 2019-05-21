package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.example.demo.StageManager;
import com.example.demo.controller.form.EmployeeFormController;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class EmployeeController implements ContainerView {
	
	@Value("${classpath:/com/example/demo/view/EmployeeView.fxml}")
	private Resource employeeViewResource;

	@Value("${classpath:/com/example/demo/view/form/EmployeeFormView.fxml}")
	private Resource employeeFormViewResource;
	
	@FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, String> firstNameColumn;
    @FXML private TableColumn<Employee, String> lastNameColumn;
    @FXML private TableColumn<Employee, String> usernameColumn;
    @FXML private TableColumn<Employee, String> phoneColumn;
    @FXML private TableColumn<Employee, String> addressColumn;
    @FXML private TableColumn<Employee, String> typeColumn;
    
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
	    
    @Autowired StageManager stageManager;
    @Autowired EmployeeService employeeService;
	
	private ObservableList<Employee> employees = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addEventListeners();
		setSuppliersTable();
	}

	private void setSuppliersTable() {
		employeeTable.setItems(employees);
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("username"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("phone"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("address"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("type"));

		Platform.runLater(() -> employees.setAll(employeeService.getAll()));
	}

	private void addEventListeners() {
		deleteButton.setOnAction(this::deleteButtonAction);
		editButton.setOnAction(this::editButtonAction);
		addButton.setOnAction(this::addButtonAction);
	}
	
	private void deleteButtonAction(ActionEvent event) {
		Employee employee = getSelectedEmployee();
		employeeService.deleteEmployee(employee);
		employees.remove(employee);
	}
	
	private void editButtonAction(ActionEvent event) {
		showCategoryForm(event, getSelectedEmployee());
	}
	
	private void addButtonAction(ActionEvent event) {
		showCategoryForm(event, null);
	}
	
	private Employee getSelectedEmployee() {
		return employeeTable.getSelectionModel().getSelectedItem();
	}
	
	private void showCategoryForm(ActionEvent event, Employee employee) {
		EmployeeFormController employeeFormController = new EmployeeFormController();
		employeeFormController.setItem(employee);
		
		stageManager.showEmployeeForm(employeeFormViewResource, employeeFormController);
		if(employeeFormController.isSaveButtonClicked()) {
			Employee updatedEmployee = employeeFormController.getItem();
			if(null != updatedEmployee) {
				if(null != updatedEmployee.getId()) {
					updateEmployee(updatedEmployee);
				} else {
					addEmployee(updatedEmployee);
				}
			}
		}
	}
	
	private void updateEmployee(Employee employee) {
		employee = employeeService.updateEmployee(employee);
		employeeTable.getItems().set(employeeTable.getSelectionModel().getSelectedIndex(), employee);
	}
	
	private void addEmployee(Employee employee) {
		employee = employeeService.updateEmployee(employee);
		employeeTable.getItems().add(employee);
	}

	@Override
	public URL getView() throws IOException {
		return employeeViewResource.getURL();
	}
	
}
