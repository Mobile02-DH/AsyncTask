package edu.dh.asynctask.controller;

import edu.dh.asynctask.view.interfaces.MainViewInterface;

public class MainController {
	private MainViewInterface listener;

	public MainController(MainViewInterface listener) {
		this.listener = listener;
	}

}
