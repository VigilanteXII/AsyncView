package com.example.asyncview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SlowView extends ImageView {
	public SlowView(Context context) {
		super(context);
	}

	public SlowView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SlowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try {
			synchronized (this) {
				wait(2000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
