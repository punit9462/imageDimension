package com.example.mehtapunit.imagedimension;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


/**
 * Created by mehta.punit on 13/10/15.
 */
public class MyImageView extends ImageView {

    private Paint mPaint;
    private Path mPath;

    public float getFirstX() {
        return firstX;
    }

    private float firstX;

    public float getFirstY() {
        return firstY;
    }

    private float firstY;

    public float getSecondX() {
        return secondX;
    }

    private float secondX;

    public float getSecondY() {
        return secondY;
    }

    private float secondY;

    public Boolean getIsBaseSet() {
        return isBaseSet;
    }

    public void setIsBaseSet(Boolean isBaseSet) {
        this.isBaseSet = isBaseSet;
    }

    private Boolean isBaseSet;
    private static final float TOUCH_TOLERANCE = 30;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint;
    private int numberOfTouches;

    @Override
    public void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private void drawLine(float x1, float y1, float x2, float y2) {
        mPath.moveTo(x1, y1);
        mPath.lineTo(x2, y2);
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
    }

    private void clearLine() {
        mBitmap.eraseColor(Color.TRANSPARENT);
        mPath.reset();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (!isBaseSet) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                numberOfTouches++;
                if (numberOfTouches == 1) {
                    firstX = x;
                    firstY = y;
                } else if (numberOfTouches == 2) {
                    secondX = x;
                    secondY = y;
                    drawLine(firstX, firstY, secondX, secondY);
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE && numberOfTouches > 2) {
                int whichToMove = whichToMove(x, y);
                //          int whichToMove = 1;
                if (whichToMove != 0) {
                    if (whichToMove == 1) {
                        firstX = x;
                        firstY = y;
                    } else {
                        secondX = x;
                        secondY = y;
                    }
                    clearLine();
                    drawLine(firstX, firstY, secondX, secondY);
                }
            }
        }
        else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                numberOfTouches++;
                if (numberOfTouches == 1) {
                    firstX = x;
                    firstY = y;
                } else if (numberOfTouches == 2) {
                    secondX = x;
                    secondY = y;
                    drawLine(firstX, firstY, secondX, secondY);
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE && numberOfTouches > 2) {
                int whichToMove = whichToMove(x, y);
                //          int whichToMove = 1;
                if (whichToMove != 0) {
                    if (whichToMove == 1) {
                        firstX = x;
                        firstY = y;
                    } else {
                        secondX = x;
                        secondY = y;
                    }
                    clearLine();
                    drawLine(firstX, firstY, secondX, secondY);
                }
            }
        }

        return true;
    }

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (isFirstTime) {
//                isFirstTime = false;
//                firstX = x;
//                firstY = y;
//                mPath.moveTo(firstX,firstY);
//            }
//            else {
//                mPath.lineTo(x, y);
//                mCanvas.drawPath(mPath, mPaint);
//                invalidate();
//                mCanvas
//                mPath.moveTo(firstX,firstY);
//            }
//        }

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                touch_start(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                touch_move(x, y);
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP:
//                touch_up();
//                invalidate();
//                break;
//        }
//        return true;
//    }

//    private void touch_start(float x, float y) {
//        //mPath.reset();
////        mPath.moveTo(x, y);
//        firstX = x;
//        firstY = y;
//    }
//
//    private void touch_move(float x, float y) {
//        float dx = Math.abs(x - mX);
//        float dy = Math.abs(y - mY);
//        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//            mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
////            mX = x;
////            mY = y;
//        }
//    }
//
//    private void touch_up() {
//        mPath.lineTo(mX, mY);
//        // commit the path to our offscreen
//        mCanvas.drawPath(mPath, mPaint);
//        //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
//        // kill this so we don't double draw
//        mPath.reset();
//        // mPath= new Path();
//    }

    private int whichToMove(float newX, float newY) {
        double val1 = calculateCartesianDistance(newX, newY, firstX, firstY);
        double val2 = calculateCartesianDistance(newX, newY, secondX, secondY);
        if (val1 <= TOUCH_TOLERANCE) {
            return 1;
        } else if (val2 <= TOUCH_TOLERANCE) {
            return 2;
        } else {
            return 0;
        }
    }

    public int getNumberOfTouches() {
        return numberOfTouches;
    }

    public void setNumberOfTouches(int numberOfTouches) {
        this.numberOfTouches = numberOfTouches;
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        mBitmapPaint = new Paint();
        mBitmapPaint.setColor(Color.RED);

        mPath = new Path();
        numberOfTouches = 0;
        isBaseSet = false;
    }

    private double calculateCartesianDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
    }

}
