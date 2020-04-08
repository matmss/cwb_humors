package com.mapa.Cadastro;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.mapa.Activity.R;
import com.mapa.Cadastro.CadastroCoordenadaBancoDeDados.CadastroCoordenadaListener;
import com.mapa.Principal.PaginaMapaActivity;

public class CadastroCoordenadaActivity extends Activity implements CadastroCoordenadaListener, LocationListener {
	
	Button botao = (Button) findViewById(R.id.btnEnviar);
	private Spinner spn1;
	private List<String> nomes = new ArrayList<String>();
	private String nome;
	
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
 
		//Adicionando Nomes no ArrayList
		nomes.add("Feliz");
		nomes.add("triste");
		nomes.add("Ativo");
		nomes.add("Sem dinheiro");
		nomes.add("Ressaca");
 
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
				Toast.makeText(CadastroCoordenadaActivity.this, "Nome Selecionado: " + nome, Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
 
		
		botao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	
	
	}
	
		public void onInsertBancoListener(String coordenadas) {
			
			Intent intent = new Intent(this, PaginaMapaActivity.class);
			
			//Instanciando a classe Bundle e mapeando os dados das coordenadas para passar para a intent
			Bundle parametros = new Bundle();
			parametros.putString("coordenadas", coordenadas);
			
			//mapeando parametros na intent para passar para a classe
			intent.putExtra("coordenadas", parametros);
			
			//String botao = findViewById();
			startActivity(intent);
			
		}
		
		public void principal(View view){
			
			//Recuperando humor da tela
			spn1 = (Spinner) findViewById(R.id.spinner1);
			String humor = spn1.getSelectedItem().toString();
			
			//Recuperando Descrição do mapa na tela 
			String descMapa = findViewById(R.id.textDescricao).toString();
			
			LocationManager LM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			String bestProvider = LM.getBestProvider(new Criteria(),true);
			
			//Pegar os dados de localização
			Location l = LM.getLastKnownLocation(bestProvider);
			
			String latitude = String.valueOf(l.getLatitude());
			String longitude = String.valueOf(l.getLongitude());
			
			
			
			//new CadastroCoordenadaBancoDeDados(this).execute(humor, descMapa, latitude, longitude);
			
		}

		@Override
		public void onConsultaBancoListener(String listaDados) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			//this.location  = location;
			
		}
	

}
