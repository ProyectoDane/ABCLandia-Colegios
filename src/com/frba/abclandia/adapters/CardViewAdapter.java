package com.frba.abclandia.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.abclandia.Card;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.EmptyRenderer;
import com.example.abclandia.graphics.Renderer;
import com.frba.abclandia.R;



public class CardViewAdapter extends BaseAdapter {
	 private List<Card> mData = new ArrayList<Card>();
	 private Renderer mRenderer;
	 private Renderer mEmptyRenderer;
	 int j =1;
	 
	 
	 
	public List<Card> getmData() {
		return mData;
	}
	public void setmData(List<Card> mData) {
		this.mData = mData;
	}

	private Context mContext;
	private View.OnDragListener mDragListener;

	public CardViewAdapter(List<Card> data, Context context, Renderer renderer) {
		
		adaptDataToScreen(data);
		mContext = context;
		mRenderer = renderer;
		mEmptyRenderer = new EmptyRenderer();
	}


	@Override
	public int getCount() {
		return mData.size();
//		return 9;
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		
		Card dataCard = mData.get(position);
	
		if (convertView == null){
			LayoutInflater inflater = (LayoutInflater) mContext
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  		view = inflater.inflate(R.layout.grid_row, parent, false);
		} else {
			view = convertView;
		}
		
		CardView cardView = (CardView) view;
		
		
		cardView.myCellnumber = position;
		cardView.mAdapter = this;
		cardView.setCard(dataCard);
		
		if (dataCard.isEmptyCard()) {
			cardView.setRenderer(mEmptyRenderer);
			cardView.setAllowDrag(false);
		} else {
			cardView.setRenderer(mRenderer);
		}
		
		

		cardView.setOnTouchListener ((View.OnTouchListener) mContext);
		cardView.setOnClickListener ((View.OnClickListener) mContext);
		cardView.setOnLongClickListener ((View.OnLongClickListener) mContext);
	
		  
		  
		  

		   
		return view;
		
	}
	private void adaptDataToScreen(List<Card> data){
		Collections.shuffle(data);
		if (data.size() == 5){
			int j = 0;
			for (int i=0;i <9; i++){
				if (i % 2 == 0){
					mData.add(data.get(j));
					j++;
				} else {
					mData.add(new Card());
				}
			}
		} else { {
			mData = data;
		}
	}

}
}
