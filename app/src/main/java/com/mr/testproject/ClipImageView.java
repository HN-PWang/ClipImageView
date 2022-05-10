package com.mr.testproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;

/**
 * @auther: pengwang
 * @date: 2022/2/28
 * @description:
 */
public class ClipImageView extends View {

    /**
     * 视图宽
     */
    private int mViewWidth = 0;

    /**
     * 视图高
     */
    private int mViewHeight = 0;

    /**
     * 真实的宽度
     */
    private int mRealWidth = 0;

    /**
     * 真实的高度
     */
    private int mRealHeight = 0;

    /**
     * 内容宽度
     */
    private float mCenterWidth = 0;

    /**
     * 内容高度
     */
    private float mCenterHeight = 0;

    /**
     * 图片宽度
     */
    private int mImgWidth = 0;

    /**
     * 图片高度
     */
    private int mImgHeight = 0;

    /**
     * 图片宽高比
     */
    private float mImgScale = 0;

    /**
     * 视图宽高比
     */
    private float mViewScale = 0;

    /**
     * 剪裁款
     */
    private int mClipStrokeColor;

    /**
     * 边框画笔
     */
    private Paint borderPaint;

    /**
     * 图片画笔
     */
    private Paint mImagePaint;

    /**
     * 边框的测量仪
     */
    private RectF borderRectF;

    /**
     * 图片的测量仪
     */
    private RectF mImageRectF;

    /**
     * 图片
     */
    private Bitmap mClipBitmap;

    public ClipImageView(Context context) {
        super(context);
        initialize(context, null, 0);
    }

    public ClipImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initialize(context, attrs, 0);
    }

    public ClipImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr);
    }

    private void initialize(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClipImageView
                , defStyleAttr, 0);

        mCenterWidth = ta.getDimensionPixelSize(R.styleable.ClipImageView_cvClipCenterWidth, 400);
        mCenterHeight = ta.getDimensionPixelSize(R.styleable.ClipImageView_cvClipCenterHeight, 400);
        mClipStrokeColor = ta.getColor(R.styleable.ClipImageView_cvClipStrokeColor, Color.WHITE);

        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(mClipStrokeColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(10);

        mImagePaint = new Paint();
        mImagePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        mRealWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mRealHeight = mViewHeight - getPaddingTop() - getPaddingBottom();

        mViewScale = ((float) mRealWidth) / mRealHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawImage(canvas);
        drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        borderRectF = getBorderRectF(borderRectF);

        canvas.drawRect(borderRectF, borderPaint);
    }

    private void drawImage(Canvas canvas) {
        mImageRectF = getImageRectF(mImageRectF);

        canvas.drawBitmap(mClipBitmap, null, mImageRectF, mImagePaint);
    }

    private RectF getImageRectF(RectF rectF) {
        rectF = rectF == null ? new RectF() : rectF;

        float showImgWidth;
        float showImgHeight;

        if (mImgWidth > mRealWidth || mImgHeight > mRealHeight) {
            float realImgWidth = mRealHeight * mImgScale;
            float realImgHeight = ((float) mRealWidth) / mImgScale;

            if (realImgWidth > mRealWidth && realImgHeight <= mRealHeight) {
                //图片宽超出
                showImgWidth = mRealWidth;
                showImgHeight = realImgHeight;
            } else if (realImgWidth <= mRealWidth && realImgHeight > mRealHeight) {
                //图片高超出
                showImgWidth = realImgWidth;
                showImgHeight = mRealHeight;
            } else {
                //宽高都超出
                if (mImgScale >= mViewScale) {
                    //图片宽占比较大,已宽为基准
                    showImgWidth = mRealWidth;
                    showImgHeight = realImgHeight;
                } else {
                    showImgWidth = realImgWidth;
                    showImgHeight = mRealHeight;
                }
            }
        } else {
            showImgWidth = mImgWidth;
            showImgHeight = mImgHeight;
        }

        int paddingWidth = (int) ((mRealWidth - showImgWidth) / 2);
        int paddingHeight = (int) ((mRealHeight - showImgHeight) / 2);

        rectF.left = getPaddingLeft() + paddingWidth;
        rectF.top = getPaddingTop() + paddingHeight;
        rectF.right = getPaddingLeft() + paddingWidth + showImgWidth;
        rectF.bottom = getPaddingTop() + paddingHeight + showImgHeight;

        return rectF;
    }

    private RectF getBorderRectF(RectF rectF) {
        rectF = rectF == null ? new RectF() : rectF;

        mCenterWidth = Math.min(mCenterWidth, mRealWidth);
        mCenterHeight = Math.min(mCenterHeight, mRealHeight);

        int paddingWidth = (int) ((mRealWidth - mCenterWidth) / 2);
        int paddingHeight = (int) ((mRealHeight - mCenterHeight) / 2);

        rectF.left = getPaddingLeft() + paddingWidth;
        rectF.top = getPaddingTop() + paddingHeight;
        rectF.right = getPaddingLeft() + paddingWidth + mCenterWidth;
        rectF.bottom = getPaddingTop() + paddingHeight + mCenterHeight;

        return rectF;
    }

    public void setImage(Object object) {
        if (object instanceof File) {
            setImageFile((File) object);
        } else if (object instanceof Integer) {
            setImageRes((Integer) object);
        } else if (object instanceof String) {
            setImagePath((String) object);
        }
    }

    private void setImagePath(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            setImageFile(file);
        }
    }

    private void setImageFile(File file) {
        if (file.exists() && file.isFile()) {
            conversionBitmap(file.getPath());
            postInvalidate();
        }
    }

    private void setImageRes(int resId) {
        conversionBitmap(resId);
        postInvalidate();
    }

    /**
     * 转化为bitmap
     */
    private void conversionBitmap(Object imgOb) {
        if (imgOb instanceof String) {
            String filePath = (String) imgOb;
            mClipBitmap = BitmapFactory.decodeFile(filePath);
        } else if (imgOb instanceof Integer) {
            int resId = (int) imgOb;
            mClipBitmap = BitmapFactory.decodeResource(getResources(), resId);
        }

        if (mClipBitmap != null) {
            mImgHeight = mClipBitmap.getHeight();
            mImgWidth = mClipBitmap.getWidth();
            mImgScale = ((float) mImgWidth) / mImgHeight;
        }
    }

    /**
     * 剪裁图片
     */
    private void doClipImage(int l, int t, int r, int b) {

    }

}
