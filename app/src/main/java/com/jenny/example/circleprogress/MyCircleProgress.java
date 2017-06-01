package com.jenny.example.circleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hujiating on 2017/6/1.
 */

public class MyCircleProgress  extends View {

    public MyCircleProgress(Context context) {
        super(context);
    }

    public MyCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawArc(this.mCircleAttribute.mRoundOval, 0.0F, 360.0F, this.mCircleAttribute.mBRoundPaintsFill, this.mCircleAttribute.mBottomPaint);
//        canvas.drawArc(this.mCircleAttribute.mRoundOval, this.mCircleAttribute.mDrawPos, f1, this.mCircleAttribute.mBRoundPaintsFill, this.mCircleAttribute.mSubPaint);
//        canvas.drawArc(this.mCircleAttribute.inRoundOval, 0.0F, 360.0F, this.mCircleAttribute.mBRoundPaintsFill, this.mCircleAttribute.mMainPaints);
    }
}
