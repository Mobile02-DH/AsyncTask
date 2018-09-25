package edu.dh.asynctask.controller;

import java.util.List;

import edu.dh.asynctask.controller.interfaces.ControllerModelInterface;
import edu.dh.asynctask.model.DAO.DAOInternet;
import edu.dh.asynctask.model.Noticia;
import edu.dh.asynctask.view.interfaces.MainViewInterface;

public class MainController implements ControllerModelInterface {
	private MainViewInterface listener;
	private DAOInternet daoInternet = new DAOInternet();

	public MainController(MainViewInterface listener) {
		this.listener = listener;
		daoInternet.setListenerController(this);
	}

	public void getDAOInternet(){
		daoInternet.execute();
	}

	@Override
	public void sucesso(Object obj) {
		listener.success(obj);
	}

	@Override
	public Throwable erro() {
		return new Exception();
	}
}
