package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.Initializable;

public interface ContainerView extends Initializable {

	public URL getView() throws IOException;
}
