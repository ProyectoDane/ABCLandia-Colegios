package com.frba.abclandia;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frba.abclandia.dtos.Maestro;

public class MaestroListActivity extends ListActivity {

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_maestro_list);
		//ListView listView = (ListView) findViewById(R.id.list_maestro);
			
			
		//TODO: Buscar maestros en la DB
		//maestroListAdapter = new MaestroListAdapter(this);
		
		setListAdapter(new MaestroListAdapter(this));
		
		// TODO: Buscar todos los maestros
	}

	
	private List<Maestro >getMaestrosData() {
		
		Maestro m1 = new Maestro(1,"Ricco","Gonzalo");
		Maestro m2 = new Maestro(2,"Ramos","Pablo");
		Maestro m3 = new Maestro(3, "Orgizovic","Federico");
		List<Maestro> maestros = new ArrayList<Maestro>();
		maestros.add(m1);
		maestros.add(m2);
		maestros.add(m3);
		return maestros;
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id ) {
		//TODO: Agregar accion cuando se selecciona un profesor.
		
		Maestro maestro =  (Maestro) getListAdapter().getItem(position);
		Toast.makeText(this, maestro.getApellido() + " " +  maestro.getLegajo() + " selected", Toast.LENGTH_LONG).show();
		
		// Tomar el ID del maestro
		// Buscar los alumnos del maestro
		Intent i = new Intent(this, AlumnoListActivity.class);
		startActivity(i);
	
		
	}
	
	private class MaestroListAdapter extends BaseAdapter{
		
		private Context mContext;
		private List<Maestro> mMaestros;
		
		public MaestroListAdapter(Context context){
			mContext = context;
			
			// Tenemos que tomar los maestros de la base de datos y/o actualizarla
			mMaestros = getMaestrosData();
		}

		@Override
		public int getCount() {
			return mMaestros.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mMaestros.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			Maestro maestro = mMaestros.get(position);
			
			return maestro.getLegajo();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			LayoutInflater inflater = (LayoutInflater) mContext
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView;
			if (convertView == null){
			    rowView = inflater.inflate(R.layout.maestro_row, parent, false);
			} else {
				rowView = convertView;
			}
			    TextView lblMaestroApellido = (TextView) rowView.findViewById(R.id.lblMaestroApellido);
			    TextView lblMaestroNombre =  (TextView) rowView.findViewById(R.id.lblMaestroNombre);
			
			    Maestro maestro = mMaestros.get(position);
			    
			    lblMaestroApellido.setText(maestro.getApellido());
			    lblMaestroNombre.setText(maestro.getNombre());
			    
			    return rowView;
		}

		
	}
}
