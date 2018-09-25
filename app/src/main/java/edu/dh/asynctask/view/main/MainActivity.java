package edu.dh.asynctask.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import edu.dh.asynctask.R;
import edu.dh.asynctask.controller.MainController;
import edu.dh.asynctask.controller.interfaces.ControllerModelInterface;
import edu.dh.asynctask.model.Noticia;
import edu.dh.asynctask.view.interfaces.MainViewInterface;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

	MainController mainController;
	TextView txtTitulo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtTitulo = findViewById(R.id.txtTitulo);

		mainController = new MainController(this);

		mainController.getDAOInternet();
	}

	@Override
	public void success(Object obj) {
		List<Noticia> noticias = (List<Noticia>) obj;
		txtTitulo.setText(noticias.get(0).getDescription());
	}

	@Override
	public void error(Throwable throwable) {

	}
}
