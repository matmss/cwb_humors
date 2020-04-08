package com.mapa.Cadastro;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class CadastroCoordenadaBancoDeDados extends AsyncTask<String, Void, String> {

	private CoordenadasCadastroActivity listener;
	String retorno;
	String resultadoJson;
	
	private final static String URL_INSERT = "http://www.agenciaodara.com.br/android/insertCoordenadas.php";
	
	CadastroCoordenadaBancoDeDados(CoordenadasCadastroActivity coordenadasCadastroActivity){
		this.listener = coordenadasCadastroActivity;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		String humor = params[0]; 
		String descMapa = params[1];
		
		String lat = params[2];
		String log = params[3];
		
		ArrayList parametrosPost = new ArrayList<NameValuePair>();
		parametrosPost.add(new BasicNameValuePair("humor", humor));
		parametrosPost.add(new BasicNameValuePair("x", lat));
		parametrosPost.add(new BasicNameValuePair("y", log));
		parametrosPost.add(new BasicNameValuePair("descricao", descMapa));
		
		try {
			retorno = insertServidor(parametrosPost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			resultadoJson = interpretaResultado(retorno);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return resultadoJson;
	}
	
	private String interpretaResultado(String resultado) throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject object = new JSONObject(resultado);
		
		JSONArray jsonArray = object.getJSONArray("existente");
		//int numRetorno = jsonArray.length();
		ArrayList<Map<String, Object>> lista = new ArrayList<>();
		
		JSONObject jsonUsuarios = jsonArray.getJSONObject(0);
		
		String retornoJson = jsonUsuarios.getString("resultado");
		String retorno;
		if(retornoJson.equals("1")){
			retorno = "Cadastrado com sucesso";
		}else{
			retorno = "Deu cagada";
		}
		
		//return x +","+ y + ","+ descricao;
		return retorno;
	}

	private String insertServidor(ArrayList<NameValuePair> parametros) throws IOException {
		// TODO Auto-generated method stub
		
		InputStream is;
		
		try {
			
			HttpParams httpParameters = new BasicHttpParams();
			/*HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
			HttpConnectionParams.setSoTimeout(httpParameters, timeout);*/

			HttpPost httppost = new HttpPost(URL_INSERT);
			DefaultHttpClient httpClient = new DefaultHttpClient();
			httppost.setEntity(new UrlEncodedFormEntity(parametros));
			httpClient.setParams(httpParameters); //eu seto os params aqui... tinha me esquecido disso!

			HttpResponse response = httpClient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			if (is == null) {
				return "";
			}

			int i;
			StringBuilder stringBuilder = new StringBuilder();

			while ((i = is.read()) >= 0) {
				stringBuilder.append((char) i);
			}

			return stringBuilder.toString();  
			
			
			/*Reader reader;
			reader = new InputStreamReader(is,"iso-8859-1");
			char buffer[] = new char[2048];
			reader.read(buffer);
			return new String(buffer);*/
			
		} finally{
			/*if(is != null){
				is.close();
			}*/
		}
	}

	protected void onPostExecute(String result) {
		listener.onInsertBancoListener(result);
	}
	
	public interface CadastroCoordenadaListener{
		void onConsultaBancoListener(String listaDados);
	}

}
