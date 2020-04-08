package com.mapa.Principal;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapa.Activity.R;
import com.mapa.Objeto.ListaDados;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class ConsultaBancoDados extends AsyncTask<String, Void, String> {
	
	private ConsultaBancoListener listener;
	
	private final static String URL_CONSULTA = "http://www.agenciaodara.com.br/android/consulta.php?humor=";
	
	public static ListaDados listaDeDados;
	
	
	ConsultaBancoDados(ConsultaBancoListener listener){
		this.listener = listener;
	}

	public String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		ArrayList<Map<String, Object>> lista = null;
		
		listaDeDados = new ListaDados();
		
		try {
			
			String resultado = consultaServidor(params[0]);
			
					
			lista = interpretaResultado(resultado);
			
			listaDeDados.setListaDeDados(lista);  
			
		} catch (IOException e) {
			e.printStackTrace();
			//return null;
		} catch (JSONException e) {
			e.printStackTrace();
			//return null;
		}
		
		return "teste";
		
	}
	
	private ArrayList<Map<String, Object>> interpretaResultado(String resultado) throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject object = new JSONObject(resultado);
		
		JSONArray jsonArray = object.getJSONArray("coordenadas");
		int numRetorno = jsonArray.length();
		ArrayList<Map<String, Object>> lista = new ArrayList<>();
		
		for (int i = 0; i < numRetorno; i++) {
			
			Map<String, Object> map = new HashMap<String,Object>();
			JSONObject jsonUsuarios = jsonArray.getJSONObject(i);
			
			String x = 			jsonUsuarios.getString("x");
			String y = 			jsonUsuarios.getString("y");
			String descricao = 	jsonUsuarios.getString("descricao");
			
			map.put("x", x);
			map.put("y", y);
			map.put("descricao", descricao);

			lista.add(map);
		}
		
		
		//return x +","+ y + ","+ descricao;
		return lista;
	}

	private String consultaServidor(String stringView) throws IOException {
		// TODO Auto-generated method stub
		
		InputStream is = null;
		try {
			
			String[] varHumor = stringView.split("app:");
			String humor = varHumor[1].substring(3, varHumor[1].length()-1);
			
			URL url = new URL(URL_CONSULTA+humor);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			
			is = conn.getInputStream();
			
			Reader reader = null;
			reader = new InputStreamReader(is,"iso-8859-1");
			char buffer[] = new char[2048];
			reader.read(buffer);
			return new String(buffer);
			
		} finally{
			if(is != null){
				is.close();
			}
		}
	}

	protected void onPostExecute(String result) {
		listener.onConsultaBancoListener(result);
	}
	
	public interface ConsultaBancoListener{
		void onConsultaBancoListener(String listaDados);
	}

	

}
