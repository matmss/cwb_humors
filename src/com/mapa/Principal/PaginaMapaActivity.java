package com.mapa.Principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapa.Activity.R;
import com.mapa.Objeto.ListaDados;

public class PaginaMapaActivity extends Activity {
	
	ArrayList<Map<String, Object>> recebeLista;
	

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

        LatLng frameworkSystemLocation = null;
        Marker frameworkSystem;
        GoogleMap map = null;
        
        recebeLista = ConsultaBancoDados.listaDeDados.getListaDeDados();
        
        int qtdeLista = recebeLista.size()-1;
        
        for (int i = 0; i <= qtdeLista; i++) {
        	
        	String x = (String) recebeLista.get(i).put("x", recebeLista);
        	String y = (String) recebeLista.get(i).put("y", recebeLista);
        	
        	String desc = (String) recebeLista.get(i).put("descricao", recebeLista);
        	
			double x2 = Double.parseDouble(x);
			double y2 = Double.parseDouble(y);
        	
			//Adiciona um ponto no mapa no local pré determinado 
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			
			//Adiciona Posição de acordo com as coordenadas
			frameworkSystemLocation = new LatLng(x2,y2);
			
			//Adiciona descrição 
        	frameworkSystem = map.addMarker(new MarkerOptions().position(frameworkSystemLocation).title(desc));
        	
        	// Move a câmera para Framework System com zoom 15.
        	map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation , 15));
		}
        
        map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        //Marca sua posição no mapa
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setCompassEnabled(true); 
        map.setMyLocationEnabled(true);      
	}
}
