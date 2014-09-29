package com.example.abclandia.graphics;

import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.abclandia.Card;
import com.example.abclandia.DragShadow;
import com.example.abclandia.DragSource;
import com.example.abclandia.DropTarget;
import com.example.abclandia.MainActivity;
import com.frba.abclandia.R;
import com.frba.abclandia.adapters.CardViewAdapter;







public class CardView extends View implements DragSource, DropTarget {
	
	
	private Card mCard;
	private Renderer mRenderer;
	private Paint mTextPaint, mRectanglePaint;
	private int rectangleWidth, rectangleHeight;
	private Context mContext;
	private boolean mAllowDrag = true;



	private float rectangleSize;
	private int textSize;
	private int widthSize,heightSize;
	 
	 private Bitmap imageBitmap; 
	 
	 public boolean mEmpty = true;
	 public int myCellnumber;
	 public CardViewAdapter mAdapter;
	
	 

	
	


	public CardView(Context context) {
		super(context);
		init(context);
	}
	
	public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

//        
//        TypedArray a = context.getTheme().obtainStyledAttributes(
//                attrs,
//                R.styleable.CardView,
//                0, 0
//        );

        try {
          

            

         
         } finally {
//            a.recycle();
        }

        init(context);
    }
	
	public String getCardId(){
		return mCard.getLetter();
	}
	public boolean isEmptyCard(){
		return mCard.isEmptyCard();
	}
	
	public void setCard(Card card){
		mCard = card;
		imageBitmap = BitmapFactory.decodeFile(mCard.getImagePath());
	}
	public void setRenderer(Renderer renderer){
		mRenderer = renderer;
	}
	public Renderer getRenderer(){
		return mRenderer;
	}
	public void setAllowDrag(boolean allowDrag){
		mAllowDrag = allowDrag;
	}
	
	
	
	private void init(Context context) {
        
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(40);
        mTextPaint.setTextAlign(Align.CENTER);
  
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		   super.onDraw(canvas);
		   
		   rectangleWidth = getMeasuredWidth();
		   rectangleHeight = getMeasuredHeight();
		   mRenderer.Render(canvas, rectangleWidth, rectangleHeight, mRectanglePaint, mTextPaint, mCard, imageBitmap);
		   
		   
	}
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
	   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	   int w = getMeasuredWidth();
	   int h = getMeasuredHeight();
	   setMeasuredDimension(w, h);
	}
 
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
	}
    
   

	


	@Override
	public boolean allowDrag() {
		return mAllowDrag;
	}

	@Override
	public ClipData clipDataForDragDrop() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public View dragDropView() {
		return this;
	}
	/**
	 * This method is called at the start of a drag-drop operation so the object being
	 * dragged knows that it is being dragged.
	 * 
	 */
	@Override
	public void onDragStarted() {
		
		setVisibility(View.GONE);		
	}

	@Override
	public void onDropCompleted(DropTarget target, boolean success) {
		if (!success){
			setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragShadow dragView, Object dragInfo) {
			MainActivity mA = (MainActivity) mContext;
			// Lo comento ahora para que compile, pero sacar
			
//			mRenderer = mA.getDefaultRenderer();
			this.mAllowDrag = false;
			invalidate();
			

	}
	
	

	@Override
	public void onDragEnter(DragSource source, int x, int y, int xOffset,
			int yOffset, DragShadow dragView, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void onDragOver(DragSource source, int x, int y, int xOffset,
			int yOffset, DragShadow dragView, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragExit(DragSource source, int x, int y, int xOffset,
			int yOffset, DragShadow dragView, Object dragInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean acceptDrop(DragSource source, int x, int y, int xOffset,
			int yOffset, DragShadow dragView, Object dragInfo) {
		
		CardView cardViewSource = (CardView) source;
		if (this.getCardId() == cardViewSource.getCardId()){
			return true;
		}else {
			return false;
		}

	}

	@Override
	public Rect estimateDropLocation(DragSource source, int x, int y,
			int xOffset, int yOffset, DragShadow dragView, Object dragInfo,
			Rect recycle) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


	

}
