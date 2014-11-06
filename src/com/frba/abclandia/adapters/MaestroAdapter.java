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

public class MaestroAdapter extends BaseAdapter {
	
	
	private Context mContext;
	private List<Maestro> mData;
	
	public MaestroAdapter(Context context, List<Maestro> maestros){
		mContext = context;
		mData = maestros;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Maestro maestro = mData.get(position);
		
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
		
		    Maestro maestro = mData.get(position);
		    
		    lblMaestroApellido.setText(maestro.getApellido());
		    lblMaestroNombre.setText(maestro.getNombre());
		    
		    return rowView;
	}

}
