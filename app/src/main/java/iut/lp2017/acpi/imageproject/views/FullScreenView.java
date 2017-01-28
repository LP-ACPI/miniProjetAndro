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
    private Bitmap _BMPimage;
    private Matrix _IMGmatrix;
    private int _viewHeightV;
    private int _viewWidth;

    private GestureDetector _gestureDetector;
    private ScaleGestureDetector _scaleGestDetector;
    private int _IMGposX;
    private int _IMGposY;
    private boolean initialised = false;

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
        if(!initialised)
        {
            _IMGmatrix = new Matrix();
            _viewWidth = getMeasuredWidth();
            _viewHeightV = getMeasuredHeight();
            _IMGposX = _viewWidth /2 - _BMPimage.getWidth()/2;
            _IMGposY = _viewHeightV /2 - _BMPimage.getHeight()/2;
            _IMGmatrix.setTranslate(_IMGposX, _IMGposY);
            initialised = true;
        }
        canvas.drawBitmap(_BMPimage, _IMGmatrix, null);
    }

    public void set_BMPimage(Bitmap BMPimage)
    {
        this._BMPimage = BMPimage;
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
        //TODO : revoir les limites de positionnement & màj position
/*      float currentX = _IMGposX - dx;
        float currentY = _IMGposY - dy;
        int dpOffset = (int)BitmapScaler.dpToPx(25,getContext());
        boolean gaucheDansCadre = currentX > -_BMPimage.getWidth()+dpOffset;
        boolean droiteDansCadre = currentX < _viewWidth - dpOffset;
        boolean hautDansCadre   = currentY > -_BMPimage.getHeight()+dpOffset;
        boolean basDansCadre    = currentY < _viewHeightV - dpOffset;

        if(!gaucheDansCadre || !droiteDansCadre)
            dx = 0;
        else _IMGposX -= dx;

        if(!hautDansCadre || !basDansCadre)
            dy = 0;
        else _IMGposY -= dy;
*/
        _IMGposX -= dx;
        _IMGposY -= dy;
        _IMGmatrix.postTranslate(-dx, -dy);
        //au lieu de 'invalidate()' -> évite le clipping de draw+animation dans animateFlingMove
        postInvalidateDelayed(0);
    }

    public void animateScale(float pivotX,float pivotY,float coefZoom)
    {
        _IMGmatrix.postScale(coefZoom,coefZoom,pivotX,pivotY);
        invalidate();
    }

    public void updateImageAfterScale(float coefZoom)
    {
        float newWidth = _BMPimage.getWidth()*coefZoom;
        float newHeight = _BMPimage.getHeight()*coefZoom;
        _IMGposX -= _IMGposX*coefZoom;
        _IMGposY -= _IMGposY*coefZoom;
        set_BMPimage(Bitmap.createScaledBitmap(_BMPimage,(int)newWidth,(int)newHeight, false));
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
        translate.setDuration(500);
        translate.setInterpolator(new LinearOutSlowInInterpolator());
        translate.setAnimationListener(aL);
        startAnimation(translate);
    }

    public void intialiseView(boolean fitImageToWidth)
    {
        if(fitImageToWidth)
            set_BMPimage(BitmapScaler.scaleToFitWidth(_BMPimage, _viewWidth));
        else
            set_BMPimage(BitmapScaler.scaleToFitHeight(_BMPimage, _viewHeightV));
        initialised = false;
        invalidate();
    }
}
