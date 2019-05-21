package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class SpringBootJPAFx extends AbstractSpringBootFx {
	
	@Autowired StageManager stageManager;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stageManager.setPrimaryStage(primaryStage);
		stageManager.showMain();
	}

	public static void main(String[] args) {
		Application.launch(SpringBootJPAFx.class, args);
	}

}
