package com.mapa.Cadastro;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mapa.Activity.R;

public class CoordenadasCadastroActivity extends Activity {
	
	Button botao;// = (Button) findViewById(R.id.btnEnviar);
	private Spinner spn1;
	private List<String> nomes = new ArrayList<String>();
	private String nome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
 
		//Adicionando Nomes no ArrayList
		nomes.add("Feliz");
		nomes.add("triste");
		nomes.add("Energetico");
		nomes.add("Semdinheiro");
		nomes.add("nervoso");
		nomes.add("Romantico");
		nomes.add("doente");
		nomes.add("animado");
		nomes.add("beber");
 
		//Identifica o Spinner no layout
		spn1 = (Spinner) findViewById(R.id.spinner1);
		
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nomes);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spn1.setAdapter(spinnerArrayAdapter);
 
		//Método do Spinner para capturar o item selecionado
		spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posição
				nome = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o nome que foi selecionado
				Toast.makeText(CoordenadasCadastroActivity.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
 
		
		/*botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});*/
	
	
	}
	
		public void onInsertBancoListener(String retorno) {
			
			
			Toast.makeText(CoordenadasCadastroActivity.this, "Nome Selecionado: " + retorno, Toast.LENGTH_LONG).show();
			/*//Instanciando a classe Bundle e mapeando os dados das coordenadas para passar para a intent
			Bundle parametros = new Bundle();
			parametros.putString("coordenadas", coordenadas);
			
			//mapeando parametros na intent para passar para a classe
			intent.putExtra("coordenadas", parametros);*/
			
			//Intent intent = new Intent(this, CoordenadasCadastroActivity.class);
			//String botao = findViewById();
			//startActivity(intent);
			
		}
		
		@SuppressLint("NewApi")
		public void principal(View view){
			
			((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
			
			//Recuperando humor da tela
			spn1 = (Spinner) findViewById(R.id.spinner1);
			String humor = spn1.getSelectedItem().toString();
			
			EditText texto = (EditText) findViewById(R.id.textDescricao);
			String descMapa = texto.getText().toString();
			
			//Recuperando Descrição do mapa na tela 
			//String descMapa = "casa do chupeta";
			
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		    Criteria criteria = new Criteria();
		    criteria.setAccuracy(Criteria.ACCURACY_FINE);
		    String provider = locationManager.getBestProvider(criteria, true);
		    Location location = locationManager.getLastKnownLocation(provider);
		    
		    String latitude = String.valueOf(location.getLatitude());
		    String longitude = String.valueOf(location.getLongitude());
			
			new CadastroCoordenadaBancoDeDados(this).execute(humor, descMapa, latitude, longitude);
			
		}
		
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		    switch (item.getItemId()) {
		    // Respond to the action bar's Up/Home button
		    case android.R.id.home:
		        NavUtils.navigateUpFromSameTask(this);
		        return true;
		    }
		    return super.onOptionsItemSelected(item);
		}

}
