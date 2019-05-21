package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

@Component
public class HomeMenuController {
	
	@Autowired CategoryController 	categoryController;
	@Autowired EmployeeController 	employeeController;
	@Autowired InvoiceController 	invoiceController;
	@Autowired ProductController 	productController;
	@Autowired PurchaseController 	purchaseController;
	@Autowired SaleController 		saleController;
	@Autowired SupplierController 	supplierController;
	
	private Map<String, ContainerView> controllersMap = new HashMap<String, ContainerView>();
	
	public void setControllers() {
		controllersMap.put("category", categoryController);
		controllersMap.put("employee", employeeController);
		controllersMap.put("invoice", invoiceController);
		controllersMap.put("product", productController);
		controllersMap.put("purchase", purchaseController);
		controllersMap.put("sale", saleController);
		controllersMap.put("supplier", supplierController);
	}
	
	public void showView(ActionEvent event, StackPane contentContainer) {
		showView(getEventButtonId(event), contentContainer);
	}
	
	public void showView(String viewId, StackPane contentContainer) {
		ContainerView view = controllersMap.get(viewId);
		setContainerView(contentContainer, view);
	}
	
	public void setContainerView(StackPane contentContainer, ContainerView view) {
		contentContainer.getChildren().clear();
		contentContainer.getChildren().add(getContainerVeiwPan(view));
	}
	
	public Pane getContainerVeiwPan(ContainerView view) {
		try {
			FXMLLoader loader = new FXMLLoader(view.getView());
			loader.setController(view);
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getEventButtonId(ActionEvent event) {
		return ((Button) event.getSource()).getId();
	}
	
}
