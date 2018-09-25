package edu.dh.asynctask.model.DAO;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

import edu.dh.asynctask.controller.interfaces.ControllerModelInterface;
import edu.dh.asynctask.model.Noticia;
import edu.dh.asynctask.model.NoticiasResposta;

public class DAOInternet extends AsyncTask<String, Void, List<Noticia>> {

	private ControllerModelInterface listenerController;

	public void setListenerController(ControllerModelInterface listenerController) {
		this.listenerController = listenerController;
	}

	public List<Noticia> obterListaDeNoticiasDaInternet() {

        try {
            URL url = new URL("https://api.myjson.com/bins/y7jl6");
            String json = downloadUrl(url);

            //Cria um objeto Gson para analisar o JSON de forma simples
            Gson gson = new Gson();
            NoticiasResposta noticiasResposta = gson.fromJson(json, NoticiasResposta.class);

            return noticiasResposta.getNoticias();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            listenerController.erro();
        } catch (IOException e) {
            e.printStackTrace();
            listenerController.erro();
        }

        return null;
    }

    /**
     * Given a URL, sets up a connection and gets the HTTP response body from the server.
     * If the network request is successful, it returns the response body in String form. Otherwise,
     * it will throw an IOException.
     */
    private String downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpsURLConnection connection = null;
        String result = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            stream = connection.getInputStream();

            if (stream != null) {
                result = readStream(stream, 4000);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[maxLength];
        int numChars = 0;
        int readSize = 0;

        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }

        if (numChars != -1) {
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }

	@Override
	protected List<Noticia> doInBackground(String... strings) {
		return obterListaDeNoticiasDaInternet();
	}

	@Override
	protected void onPostExecute(List<Noticia> noticias) {
		super.onPostExecute(noticias);
		listenerController.sucesso(noticias);
	}

}
