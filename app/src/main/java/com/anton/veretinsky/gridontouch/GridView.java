package com.anton.veretinsky.gridontouch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GridView extends View {
    private static final float GRID_WIDTH = 8f;

    private RectF gridBounds = new RectF();
    private Paint gridPaint = new Paint();

    private float gridWidthDp;

    public GridView(Context context) {
        super(context);
        setupGridPaint();
        setupGridWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float linePosY = gridWidthDp;
        while (linePosY <= gridBounds.bottom) {
            canvas.drawLine(0, linePosY, gridBounds.right, linePosY, gridPaint);
            linePosY += gridWidthDp;
        }

        float linePosX = gridWidthDp;
        while (linePosX <= gridBounds.right) {
            canvas.drawLine(linePosX, 0, linePosX, gridBounds.bottom, gridPaint);
            linePosX += gridWidthDp;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight();
        int desiredHeight = getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom();

        int measuredWidth = measureDimension(desiredWidth, widthMeasureSpec);
        int measuredHeight = measureDimension(desiredHeight, heightMeasureSpec);

        gridBounds.set(0, 0, measuredWidth, measuredHeight);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    private void setupGridPaint() {
        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setColor(Color.RED);
    }

    private void setupGridWidth() {
        gridWidthDp = GRID_WIDTH * getResources().getDisplayMetrics().density;
    }
}
