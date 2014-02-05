package com.example.demo1_s2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.widget.TextView;

public class CountryDetailActivity extends Activity{
	private String country = "";
	public static final String COUNTRY = "coutry";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country_detail);
		TextView name = (TextView)findViewById(R.id.name);
		TextView horario = (TextView)findViewById(R.id.horario);
		TextView web = (TextView)findViewById(R.id.web);
		TextView telefono = (TextView)findViewById(R.id.telefono);
		TextView direccion = (TextView)findViewById(R.id.direccion);
		TextView email = (TextView)findViewById(R.id.email);
		TextView ema = (TextView)findViewById(R.id.ema);
		
		Intent intent = getIntent();
		country = intent.getStringExtra(COUNTRY);
		name.setText(country);
		if(country.equalsIgnoreCase("Lacoste")){
			horario.setText("Horario: 10:00AM - 8:00pm");
			direccion.setText("Direccion: 21 Ave. 4-32 Z.11, Nivel 1");
			web.setText("www.lacoste.com");
			telefono.setText("24748771");
			email.setText("Email:");
			ema.setText("contacto@lacoste.com");
		}
		if(country.equalsIgnoreCase("Tigo")){
			horario.setText("Horario: 10:00AM - 6:00pm");
			direccion.setText("Direccion: 21 Ave. 4-32 Z.11, Nivel 1");
			web.setText("www.tigo.com");
			telefono.setText("40072030");
			email.setText("Email:");
			ema.setText("contacto@tigo.com");
		}
		if(country.equalsIgnoreCase("Ishop")){
			horario.setText("Horario: 10:00AM - 7:30pm");
			direccion.setText("Direccion: 21 Ave. 4-32 Z.11, Nivel 2");
			web.setText("www.ishop.com");
			telefono.setText("22696111");
			email.setText("Email:");
			ema.setText("contacto@ishop.com");
		}
		if(country.equalsIgnoreCase("SportLine")){
			horario.setText("Horario: 10:00AM - 9:00pm");
			direccion.setText("Direccion: 21 Ave. 4-32 Z.11, Nivel 2");
			web.setText("www.sportline.com");
			telefono.setText("24748759");
			email.setText("Email:");
			ema.setText("contacto@sportline.com");
		}
		
		Linkify.addLinks(telefono, Linkify.ALL);
		Linkify.addLinks(web, Linkify.ALL);
		Linkify.addLinks(ema, Linkify.ALL);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.country_detail, menu);
		return true;
	}

	public String getCountry() {
		return country;
	}


}
