package com.mapa.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mapa.Activity.R;
import com.mapa.Principal.MainActivity;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	
	public void loginPrincipal(View view){
		
		EditText loginEdit = (EditText) findViewById(R.id.login);
		String login = loginEdit.getText().toString();
		
		EditText senhaEdit = (EditText) findViewById(R.id.senha);
		String senha = senhaEdit.getText().toString();
		
		if(login.equals("admin") && senha.equals("admin")){
			Toast.makeText(this, "Carregando página Principal", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		} else {
			
			Toast.makeText(this, "Usuario e/ou Senha inválido", Toast.LENGTH_LONG).show();
			
		}
		
	}

}
