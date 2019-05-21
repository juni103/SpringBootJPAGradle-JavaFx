package com.example.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public abstract class AbstractSpringBootFx extends Application {

	public ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		ApplicationContextInitializer<GenericApplicationContext> initializer = appContext -> {
					appContext.registerBean(Application.class, 	() -> this);
					appContext.registerBean(Parameters.class, 	() -> getParameters());
					appContext.registerBean(HostServices.class, () -> getHostServices());
				};
				
		this.context = new SpringApplicationBuilder()
				.sources(SpringBootJPAFx.class)
				.initializers(initializer)
				.run(getParameters().getRaw().toArray(new String[0]));
	}
	
	@Override
	public abstract void start(Stage primaryStage) throws Exception;
}
