package iut.lp2017.acpi.imageproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import iut.lp2017.acpi.imageproject.controllers.GestControl;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created on 24/01/2017.
 */

public class FullScreenView extends View {
    private Bitmap _bmpImage;
    private Matrix _imgMatrix;
    private int _viewHeightV;
    private int _viewWidth;

    private GestureDetector _gestureDetector;
    private ScaleGestureDetector _scaleGestDetector;
    private int _imgPosX;
    private int _imgPosY;
    private boolean _initialised = false;

    public FullScreenView(Context context)
    {
        super(context);
        initGests();
    }

    public FullScreenView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initGests();
    }

    public FullScreenView(Context context,  AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initGests();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        if(!_initialised)
        {
            _imgMatrix = new Matrix();
            _viewWidth = getMeasuredWidth();
            _viewHeightV = getMeasuredHeight();
            _imgPosX = _viewWidth /2 - _bmpImage.getWidth()/2;
            _imgPosY = _viewHeightV /2 - _bmpImage.getHeight()/2;
            _imgMatrix.setTranslate(_imgPosX, _imgPosY);
            _initialised = true;
        }
        canvas.drawBitmap(_bmpImage, _imgMatrix, null);
    }

    public void set_bmpImage(Bitmap BMPimage)
    {
        this._bmpImage = BMPimage;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        boolean toucherBasique = _gestureDetector.onTouchEvent(event);
        boolean toucherZoom  = _scaleGestDetector.onTouchEvent(event);

        return toucherBasique || toucherZoom;
    }

    private void initGests()
    {
        GestControl _gestControl = new GestControl(this);
        _gestureDetector = new GestureDetector(getContext(),_gestControl);
        _gestureDetector.setOnDoubleTapListener(_gestControl);

        _scaleGestDetector = new ScaleGestureDetector(getContext(),_gestControl);
    }

    public void animateMove(float dx, float dy)
    {
        _imgPosX -= dx;
        _imgPosY -= dy;
        _imgMatrix.postTranslate(-dx, -dy);
        //au lieu de 'invalidate()' -> évite le clipping de draw+animation dans animateFlingMove
        postInvalidateDelayed(0);
    }

    public void animateScale(float pivotX,float pivotY,float coefZoom)
    {
        _imgMatrix.postScale(coefZoom,coefZoom,pivotX,pivotY);
        invalidate();
    }

    public void updateImageAfterScale(float coefZoom)
    {
        float newWidth = _bmpImage.getWidth()*coefZoom;
        float newHeight = _bmpImage.getHeight()*coefZoom;
        _imgPosX -= _imgPosX *coefZoom;
        _imgPosY -= _imgPosY *coefZoom;
        set_bmpImage(Bitmap.createScaledBitmap(_bmpImage,(int)newWidth,(int)newHeight, false));
    }

    public void animateFlingMove(final float endFlingX,final float endFlingY)
    {
        //TODO: pb - image découpée si précédemment en dehors de l'écran -> image en entier
        Animation.AnimationListener aL = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation)
            {
                animateMove(-endFlingX, -endFlingY);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        };

        Animation translate = new TranslateAnimation(0,endFlingX,0,endFlingY);
        translate.setDuration(750);
        translate.setInterpolator(new LinearOutSlowInInterpolator());
        translate.setAnimationListener(aL);
        startAnimation(translate);
    }

    public void intialiseView(boolean fitImageToWidth)
    {
        if(fitImageToWidth)
            set_bmpImage(BitmapScaler.scaleToFitWidth(_bmpImage, _viewWidth));
        else
            set_bmpImage(BitmapScaler.scaleToFitHeight(_bmpImage, _viewHeightV));
        _initialised = false;
        invalidate();
    }
}
