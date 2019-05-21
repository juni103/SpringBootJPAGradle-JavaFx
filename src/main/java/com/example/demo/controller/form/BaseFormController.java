package com.example.demo.controller.form;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

@Component
public interface BaseFormController<T> extends Initializable {

	void setItem(T item);
	T getItem();
	void updateItemAndCloseForm(ActionEvent event);
	void closFormByEvent(ActionEvent event);
	boolean isSaveButtonClicked();
}
