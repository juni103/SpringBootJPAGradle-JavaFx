package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@Component
public class HomeController extends HomeMenuController implements Initializable {

	@Value("${classpath:/com/example/demo/view/HomeView.fxml}")
	private Resource homeViewResource;
	
	@FXML private VBox sideMenu;
    @FXML private Button category;
    @FXML private Button employee;
    @FXML private Button invoice;
    @FXML private Button product;
    @FXML private Button purchase;
    @FXML private Button sale;
    @FXML private Button supplier;
    @FXML private StackPane contentContainer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setControllers();
		showView("category", contentContainer);
	}
    
    @FXML
    void onMenuButtonClicked(ActionEvent event) {
    	showView(event, contentContainer);
    }

	public URL getView() throws IOException {
		return homeViewResource.getURL();
	}
}
