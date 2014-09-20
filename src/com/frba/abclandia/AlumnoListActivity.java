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

import com.frba.abclandia.dtos.Alumno;

public class AlumnoListActivity extends ListActivity {
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_alumno_list);
		
		setListAdapter(new AlumnoListAdapter(this));
	}
	
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		//TODO: Inicia la siguiente actividad con los ejercicios y demas....
		
		Alumno alumno =  (Alumno) getListAdapter().getItem(position);
		Toast.makeText(this,  alumno.getNombre() + " " + alumno.getApellido() + " Seleccionado", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, ActividadesActivity.class);
		startActivity(i);
	}
	
	private List<Alumno> getAlumnosData(){
		Alumno a1 = new Alumno(1,"Lento", "Pedro",1);
		Alumno a2 = new Alumno(2,"Lenteja","Maria",1);
		Alumno a3 = new Alumno(3,"Gomez","Luis",2);
		
		List<Alumno> alumnos =  new ArrayList<Alumno>();
		alumnos.add(a1);
		alumnos.add(a2);
		alumnos.add(a3);
		
		return alumnos;
	}
	
	private class AlumnoListAdapter extends BaseAdapter{
		private Context mContext;
		private List<Alumno> mAlumnos;
		
		public AlumnoListAdapter(Context context){
			mContext = context;
			mAlumnos = getAlumnosData();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mAlumnos.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mAlumnos.get(position);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			Alumno alumno = mAlumnos.get(arg0);
			return alumno.getLegajo();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView;
			if (convertView == null){
				rowView = inflater.inflate(R.layout.alumno_row, parent,false);
			} else {
				rowView = convertView;
			}
			
			TextView lblAlumnoApellido = (TextView)  rowView.findViewById(R.id.lblAlumnoApellido);
			TextView lblAlumnoNombre = (TextView) rowView.findViewById(R.id.lblAlumnoNombre);
			
			Alumno alumno = mAlumnos.get(position);
			
			lblAlumnoApellido.setText(alumno.getApellido());
			lblAlumnoNombre.setText(alumno.getNombre());
			
			return rowView;
			
		}
	}

}
