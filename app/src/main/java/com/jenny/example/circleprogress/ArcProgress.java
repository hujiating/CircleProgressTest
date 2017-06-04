package com.jenny.example.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by hujiating on 2017/6/1.
 */

public class ArcProgress extends View {
    private Paint paint;
    private Paint pointPaint;

    private RectF rectF = new RectF();

    private float strokeWidth;

    private int grade = 0;
    private int maxGrade;
    private int finishedStrokeColor;
    private int unfinishedStrokeColor;
    private float arcStartAngle;
    private float arcEndAngle;
    private float arcSweepAngle;
    float finishedSweepAngle;
    float finishedStartAngle;
    float finishedEndAngle;

    float drawingSweepAngle;
    float drawwingEndAngle;

    private float arcShowWidth;
    private float arcShowHeight;
    private float arcBottomHeight;

    private final int default_finished_color = Color.RED;
    private final int default_unfinished_color = Color.GRAY;

    private final float default_stroke_width;
    private final float default_point_radius;
    private final int default_max_grade = 6;
    private final int min_size;

    float radius;
    float pointRadius;

    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_STROKE_WIDTH = "stroke_width";

    private static final String INSTANCE_PROGRESS = "grade";
    private static final String INSTANCE_MAX = "maxGrade";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_START_ARC_ANGLE = "arc_start_angle";
    private static final String INSTANCE_SWEEP_ARC_ANGLE = "arc_sweep_angle";

    public ArcProgress(Context context) {
        this(context, null);
    }

    public ArcProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        min_size = (int) Utils.dp2px(getResources(), 100);

        default_stroke_width = Utils.dp2px(getResources(), 1);
        default_point_radius = Utils.dp2px(getResources(), 2.5f);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
        setGrade(0,0);
    }

    protected void initByAttributes(TypedArray attributes) {

        finishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.ArcProgress_arc_unfinished_color, default_unfinished_color);

//        arcStartAngle = attributes.getFloat(R.styleable.ArcProgress_arc_start_angle, default_arc_angle);
//        arcSweepAngle = attributes.getFloat(R.styleable.ArcProgress_arc_sweep_angle, default_arc_angle);

        arcShowWidth = attributes.getDimension(R.styleable.ArcProgress_arc_show_width, getWidth());
        arcShowHeight = attributes.getDimension(R.styleable.ArcProgress_arc_show_height, getHeight());

        setMaxGrade(attributes.getInt(R.styleable.ArcProgress_arc_max_grade, default_max_grade));
        setGrade(attributes.getInt(R.styleable.ArcProgress_arc_grade, 0));
        strokeWidth = attributes.getDimension(R.styleable.ArcProgress_arc_stroke_width, default_stroke_width);
        pointRadius = attributes.getDimension(R.styleable.ArcProgress_point_radius, default_point_radius);
    }

    protected void initPainters() {

        paint = new Paint();
        paint.setColor(default_unfinished_color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);

        pointPaint = new Paint();
        pointPaint.setColor(default_finished_color);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }


    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
        if (this.grade > getMaxGrade()) {
            this.grade %= getMaxGrade();
        }
        invalidate();
    }

    public void setGrade(int oldGrade,int grade) {
        this.grade = grade;
        if (this.grade > getMaxGrade()) {
            this.grade %= getMaxGrade();
        }
        float oldFinishedSweepAngle = oldGrade / (float) getMaxGrade() * arcSweepAngle;
        finishedSweepAngle = grade / (float) getMaxGrade() * arcSweepAngle;
        finishedStartAngle = arcStartAngle;
        finishedEndAngle = finishedStartAngle + finishedSweepAngle;

        ValueAnimator anim = ValueAnimator.ofFloat((int)oldFinishedSweepAngle, (int)finishedSweepAngle);
        anim.setDuration(1000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drawingSweepAngle = (float) animation.getAnimatedValue();
                drawwingEndAngle = finishedStartAngle + drawingSweepAngle;
                Log.d("TAG", "cuurent drawingSweepAngle is " + drawingSweepAngle);
                invalidate();
            }
        });
        anim.start();
    }

    public int getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(int maxGrade) {
        if (maxGrade > 0) {
            this.maxGrade = maxGrade;
            invalidate();
        }
    }


    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public float getArcStartAngle() {
        return arcStartAngle;
    }

    public void setArcStartAngle(float arcStartAngle) {
        this.arcStartAngle = arcStartAngle;
        this.invalidate();
    }

    public float getArcSweepAngle() {
        return arcSweepAngle;
    }

    public void setArcSweepAngle(float arcSweepAngle) {
        this.arcSweepAngle = arcSweepAngle;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return min_size;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return min_size;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height =  MeasureSpec.getSize(heightMeasureSpec);
        rectF.set(pointRadius / 2f, pointRadius / 2f, width - pointRadius / 2f, height - pointRadius / 2f);
        radius = width / 2f;
        calculateAngles();
        float angle = (360 - arcStartAngle) / 2f;
        arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
    }

    private void calculateAngles(){
        arcStartAngle = -(float)(Math.asin((arcShowWidth-radius)/radius)*180/Math.PI)-90;
        arcEndAngle = (float)(Math.asin((arcShowHeight-radius)/radius)*180/Math.PI);
        arcSweepAngle = arcEndAngle-arcStartAngle;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(grade == 0)
            finishedStartAngle = 0.01f;
        paint.setColor(unfinishedStrokeColor);
        canvas.drawArc(rectF, arcStartAngle,arcSweepAngle , false, paint);
        paint.setColor(finishedStrokeColor);
        canvas.drawArc(rectF, finishedStartAngle, drawingSweepAngle, false, paint);


        if(arcBottomHeight == 0) {
            float radius = getWidth() / 2f;
            float angle = (360 - arcStartAngle) / 2f;
            arcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
        }

        float pointX;
        float pointY;
        pointX = radius + (float) Math.cos(drawwingEndAngle / 180 * Math.PI) * (radius - pointRadius / 2);
        pointY = radius + (float) Math.sin(drawwingEndAngle / 180 * Math.PI) * (radius - pointRadius / 2);

        canvas.drawCircle(pointX,pointY,pointRadius,pointPaint);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());

        bundle.putInt(INSTANCE_PROGRESS, getGrade());
        bundle.putInt(INSTANCE_MAX, getMaxGrade());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putFloat(INSTANCE_START_ARC_ANGLE, getArcStartAngle());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);


            setMaxGrade(bundle.getInt(INSTANCE_MAX));
            setGrade(bundle.getInt(INSTANCE_PROGRESS));
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);

            initPainters();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
