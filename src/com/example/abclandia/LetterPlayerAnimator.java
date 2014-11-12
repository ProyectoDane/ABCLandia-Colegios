package com.example.abclandia;

import com.example.abclandia.graphics.CardView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.GridView;
import android.widget.ImageView;

public class LetterPlayerAnimator {

	private final static int ANIMATION_FORWARD_DURATION = 800;
	private final static int ANIMATION_REVERSE_DURATION = 800;
	private final static int ANIMATION_MIDDLE_DELAY = 3000;
	private final static float SCALE_FACTOR = 1.5f;

	private Context mContext;
	private WindowManager mWindowManager;
	private AnimatorForwardListener mAnimationListner;
	private AnimatorReverseListener mAnimatorReverseListener;
	private ImageView mViewToAnimate;
	private CardView mCardView;
	private AnimatorSet animatorSetReverse, animatorSetForward, as;
	
	

	public LetterPlayerAnimator(Context context) {
		mContext = context;
		mWindowManager = (WindowManager) mContext.getSystemService("window");
		mAnimationListner = new AnimatorForwardListener();
		mAnimatorReverseListener = new AnimatorReverseListener();
	}

	private void updateViewLayout(ImageView view, Integer x, Integer y,
			Integer w, Integer h) {
		if (view != null) {
			WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view
					.getLayoutParams();

			if (x != null)
				lp.x = x;
			if (y != null)
				lp.y = y;
			if (w != null && w > 0)
				lp.width = w;
			if (h != null && h > 0)
				lp.height = h;
			mWindowManager.updateViewLayout(view, lp);
		}
	}

	void animate(final ImageView viewToAnimate,GridView gv,
			CardView cardView, int viewX, int endX, int viewY,
			int endY, int viewWidth, int viewHeight) {
		mViewToAnimate = viewToAnimate;

		
		
		mCardView= cardView;
		mCardView.setAlpha(0);
		ValueAnimator translateXForward = ValueAnimator.ofInt(viewX, endX);
		ValueAnimator translateXReverse = ValueAnimator.ofInt(endX, viewX);
		ValueAnimator translateYForward = ValueAnimator.ofInt(viewY, endY);
		ValueAnimator translateYReverse = ValueAnimator.ofInt(endY, viewY);
		ValueAnimator scaleXOut = ValueAnimator.ofInt(viewWidth, viewWidth * 3);
		ValueAnimator scaleYOut = ValueAnimator.ofInt(viewHeight, viewHeight * 3);
		
		ObjectAnimator fadeIn = ObjectAnimator.ofFloat(gv, "alpha",0f, 1f);
		ObjectAnimator fadeOut = ObjectAnimator.ofFloat(gv, "alpha",1f, 0f);



		translateYForward
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator valueAnimator) {
						int val = (Integer) valueAnimator.getAnimatedValue();
						updateViewLayout(viewToAnimate, null, val, null, null);

					}
				});
		translateYReverse
		.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				updateViewLayout(viewToAnimate, null, val, null, null);

			}
		});

		translateXForward
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					@Override
					public void onAnimationUpdate(ValueAnimator valueAnimator) {
						int val = (Integer) valueAnimator.getAnimatedValue();
						updateViewLayout(viewToAnimate, val, null, null, null);

					}
				});
		
		translateXReverse
		.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				updateViewLayout(viewToAnimate, val, null, null, null);

			}
		});
		scaleXOut
		.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				updateViewLayout(viewToAnimate, null, null, val, null);

			}
		});
		scaleYOut
		.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				updateViewLayout(viewToAnimate, null, null, null, val);

			}
		});

		animatorSetForward = new AnimatorSet();
		 animatorSetReverse = new AnimatorSet();
		 
		
		animatorSetForward.addListener(mAnimationListner);
		animatorSetForward.playTogether( translateXForward,translateYForward, fadeOut);
		
		 as = new AnimatorSet();
		as.playTogether(scaleXOut,scaleYOut);
		as.setDuration(ANIMATION_FORWARD_DURATION);
		
		animatorSetForward.setDuration(ANIMATION_FORWARD_DURATION);
		
		animatorSetReverse.playTogether(translateXReverse, translateYReverse, fadeIn);
		animatorSetReverse.addListener(mAnimatorReverseListener);
		animatorSetReverse.setDuration(ANIMATION_REVERSE_DURATION);
		animatorSetReverse.setStartDelay(ANIMATION_MIDDLE_DELAY);
		animatorSetForward.start();
		

	}

	private class AnimatorForwardListener extends AnimatorListenerAdapter {
		@Override
		public void onAnimationEnd(Animator animation) {
			CardLetterPlayerActivity activity = (CardLetterPlayerActivity) mContext;

			activity.reproduceSoundCard(mCardView);
			animatorSetReverse.start();
			
			
//			

		}

	}
	private class AnimatorReverseListener extends AnimatorListenerAdapter {
		@Override
		public void onAnimationEnd(Animator animation) {
			 if (mViewToAnimate != null)
			  mWindowManager.removeView(mViewToAnimate);
			  mCardView.setAlpha(1);
			 
			
			

			

		}

	}


}
