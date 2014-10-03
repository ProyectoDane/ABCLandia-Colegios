package com.frba.abclandia.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.frba.abclandia.R;
import com.frba.abclandia.dtos.Maestro;

public class MaestroListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Maestro> maestros;
	
	public MaestroListAdapter(Context context){
		
		this.context = context;

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		    View rowView = inflater.inflate(R.layout.maestro_row, parent, false);
		    TextView lblMaestroApellido = (TextView) rowView.findViewById(R.id.lblMaestroApellido);
		    TextView lblMaestroNombre =  (TextView) rowView.findViewById(R.id.lblMaestroNombre);
		
		    Maestro maestro = maestros.get(position);
		    
		    lblMaestroApellido.setText(maestro.getApellido());
		    lblMaestroNombre.setText(maestro.getNombre());
		
		return rowView;
	}

}
