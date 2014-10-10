package com.frba.abclandia.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.abclandia.AbcPlayerActivity;
import com.example.abclandia.Card;
import com.example.abclandia.GameActivity;
import com.example.abclandia.GameSixActivity;
import com.example.abclandia.graphics.CardView;
import com.example.abclandia.graphics.EmptyRenderer;
import com.example.abclandia.graphics.Renderer;

public class Adapterprueba extends BaseAdapter {
	 private List<Card> mData = new ArrayList<Card>();
	 private Renderer mRenderer;
	 private Renderer mEmptyRenderer;
	 private int mLayoutCardView;
	 int j =1;
	 
	 
	 
	public List<Card> getmData() {
		return mData;
	}
	public void setmData(List<Card> mData) {
		this.mData = mData;
	}

	private AbcPlayerActivity mContext;
	private View.OnDragListener mDragListener;

	public Adapterprueba(List<Card> data, AbcPlayerActivity context, Renderer renderer, int layoutCardView) {
		
		
		mContext = context;
		adaptDataToScreen(data);
		mRenderer = renderer;
		mEmptyRenderer = new EmptyRenderer(mContext);
		mLayoutCardView = layoutCardView;
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
			  		view = inflater.inflate(mLayoutCardView, parent, false);
		} else {
			view = convertView;
		}
		
		CardView cardView = (CardView) view;
		
//		if (mContext.getClass() == GameSixActivity.class){
//			cardView.setDoubleMatching(true);
//			
//		}
		
//		mContext.prueba();
		cardView.myCellnumber = position;
//		cardView.mAdapter = this;
		cardView.setCard(dataCard);
		
		if (dataCard.isEmptyCard()) {
			cardView.setRenderer(mEmptyRenderer);
			cardView.setAllowDrag(false);
		} else {
			cardView.setRenderer(mRenderer);
		}
		
		

		cardView.setOnTouchListener ((View.OnTouchListener) mContext);
		
	
		  
		  
		  

		   
		return view;
		
	}
	private void adaptDataToScreen(List<Card> data){
		
		if (mContext.getClass().getSuperclass() == GameActivity.class) {
		Collections.shuffle(data);
		}
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