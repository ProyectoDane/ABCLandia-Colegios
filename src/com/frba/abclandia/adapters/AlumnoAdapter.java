package com.frba.abclandia.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frba.abclandia.R;
import com.frba.abclandia.dtos.Alumno;

public class AlumnoAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<Alumno> mAlumnos;
	private int[] colors = new int[] { Color.parseColor("#656D78"), Color.parseColor("#D2E4FC") }; 
	
	public AlumnoAdapter(Context context, List<Alumno> alumnos){
		mContext = context;
		mAlumnos = alumnos;
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
		return alumno.getId();
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
		
//		if (position % 2 == 0)
//			rowView.setBackgroundColor(Color.parseColor("#656D78"));
		
		return rowView;
		
	}


}
