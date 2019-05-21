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
import com.example.demo.controller.form.SaleFormController;
import com.example.demo.model.Sale;
import com.example.demo.service.SaleService;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@Component
public class SaleController implements ContainerView {

	@Value("${classpath:/com/example/demo/view/SaleView.fxml}")
	private Resource saleViewResource;
	
	@Value("${classpath:/com/example/demo/view/form/SaleFormView.fxml}")
	private Resource saleFormViewResource;
	
	@FXML private TableView<Sale> saleTable;
    @FXML private TableColumn<Sale, String> invoiceColumn;
    @FXML private TableColumn<Sale, String> productColumn;
    @FXML private TableColumn<Sale, Integer> quantityColumn;
    @FXML private TableColumn<Sale, Float> priceColumn;
    @FXML private TableColumn<Sale, Float> totalColumn;
    @FXML private TableColumn<Sale, LocalDate> datetimeColumn;
    @FXML private Button deleteButton;
    @FXML private Button editButton;
    @FXML private Button addButton;
    
    @Autowired SaleService saleService;
    @Autowired StageManager stageManager;
    @Autowired SaleFormController saleFormController;
    
    private ObservableList<Sale> sales = FXCollections.observableArrayList();
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addEventListeners();
		setSalesTable();
	}
	
	private void addEventListeners() {
		deleteButton.setOnAction(this::deleteButtonAction);
		editButton.setOnAction(this::editButtonAction);
		addButton.setOnAction(this::addButtonAction);
	}
	
	private void deleteButtonAction(ActionEvent event) {
		Sale sale = getSelectedSale();
		saleService.deleteSale(sale);
		sales.remove(sale);
	}
	
	private void editButtonAction(ActionEvent event) {
		showSaleForm(event, getSelectedSale());
	}
	
	private void addButtonAction(ActionEvent event) {
		showSaleForm(event, null);
	}

	private Sale getSelectedSale() {
		return saleTable.getSelectionModel().getSelectedItem();
	}
	
	private void setSalesTable() {
		saleTable.setItems(sales);
		invoiceColumn.setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getInvoice().getId().toString()));
		productColumn.setCellValueFactory(sale -> new SimpleStringProperty(sale.getValue().getProduct().getName()));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("quantity"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Sale, Float>("total"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Sale, Float>("price"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<Sale, Float>("total"));
		datetimeColumn.setCellValueFactory(new PropertyValueFactory<Sale, LocalDate>("datetime"));

		Platform.runLater(() -> sales.setAll(saleService.getAll()));
	}
	
	private void loadAllSales() {
		Service<ObservableList<Sale>> service = new Service<ObservableList<Sale>>() {
            @Override
            protected Task<ObservableList<Sale>> createTask() {
                return new Task<ObservableList<Sale>>() {
        			@Override
        			public ObservableList<Sale> call() throws Exception {
        				sales.clear();
        				ObservableList<Sale> innerSales = saleService.getAll();;
        				int count = 0;
        				int max = innerSales.size();
        				updateProgress(count, max);
        				for(count = 0; count < max; count++) {
        					Thread.sleep(10);
        					sales.add(innerSales.get(count));
        					updateProgress(count, max);
        				}
        				return innerSales;
        			}
        		};
            }
        };
//        service.start();
        
//        progressBar.progressProperty().bind(service.progressProperty());
//        service.setOnRunning((WorkerStateEvent event) -> {
//            imgLoad.setVisible(true);
//        });
//        service.setOnSucceeded((WorkerStateEvent event) -> {
//            imgLoad.setVisible(false);
////            new FadeInUpTransition(paneTabel).play();
//        });
	}
	
	private void showSaleForm(ActionEvent event, Sale Sale) {
		saleFormController.setItem(Sale);
		stageManager.showForm(saleFormViewResource, saleFormController);
		
		if(saleFormController.isSaveButtonClicked()) {
			Sale updatedSale = saleFormController.getItem();
			if(null != updatedSale) {
				if(null != updatedSale.getId()) {
					updateSale(updatedSale);
				} else {
					addSale(updatedSale);
				}
			}
		}
	}
	
	public void updateSale(Sale sale) {
		sale = saleService.updateSale(sale);
		saleTable.getItems().set(saleTable.getSelectionModel().getSelectedIndex(), sale);
	}
	
	public void addSale(Sale sale) {
		sale = saleService.updateSale(sale);
		saleTable.getItems().add(sale);
	}

	@Override
	public URL getView() throws IOException {
		return saleViewResource.getURL();
	}
	
}
