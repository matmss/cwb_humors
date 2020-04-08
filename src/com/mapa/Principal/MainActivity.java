package com.mapa.Principal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.mapa.Activity.R;
import com.mapa.Cadastro.CadastroCoordenadaActivity;
import com.mapa.Cadastro.CoordenadasCadastroActivity;
import com.mapa.Principal.ConsultaBancoDados.ConsultaBancoListener;

public class MainActivity extends Activity implements ConsultaBancoListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_principal);
	}
	
	public void testeCreate(Bundle savedInstanceState, String teste){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

		
	@SuppressLint("NewApi")
	public void principal(View view){
		Toast.makeText(this, "Carregando...", Toast.LENGTH_LONG).show();
		String varView = view.toString();
		
		new ConsultaBancoDados(this).execute(varView);
		
	}
	
	public void onConsultaBancoListener(String coordenadas) {
		
		Intent intent = new Intent(this, PaginaMapaActivity.class);
		
		//Instanciando a classe Bundle e mapeando os dados das coordenadas para passar para a intent
		Bundle parametros = new Bundle();
		parametros.putString("coordenadas", coordenadas);
		
		//mapeando parametros na intent para passar para a classe
		intent.putExtra("coordenadas", parametros);
		
		startActivity(intent);
		
	}
	
	//Chama tela de Cadastro de Coordenadas
	public void cadastroCoordenadas(View view){
		
		Intent intent = new Intent(view.getContext(), CoordenadasCadastroActivity.class);
		
		startActivity(intent);
		
	}

}


