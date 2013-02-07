package com.example.asyncview;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AsyncView extends ViewGroup {
	private AsyncTask<Void, Void, Void> mMeasureTask;
	private boolean mMeasured = false;

	public AsyncView(Context context) {
		super(context);
	}

	public AsyncView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		for(int i=0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.layout(0, 0, child.getMeasuredWidth(), getMeasuredHeight());
		}
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(mMeasured)
			return;
		if(mMeasureTask == null) {
			mMeasureTask = new AsyncTask<Void, Void, Void>() {
				@Override
				protected Void doInBackground(Void... objects) {
					for(int i=0; i < getChildCount(); i++) {
						measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
					}
					return null;
				}

				@Override
				protected void onPostExecute(Void aVoid) {
					mMeasured = true;
					mMeasureTask = null;
					requestLayout();
				}
			};
			mMeasureTask.execute();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if(mMeasureTask != null) {
			mMeasureTask.cancel(true);
			mMeasureTask = null;
		}
		mMeasured = false;
		super.onSizeChanged(w, h, oldw, oldh);
	}
}
