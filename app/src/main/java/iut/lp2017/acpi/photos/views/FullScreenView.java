package iut.lp2017.acpi.photos.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import iut.lp2017.acpi.photos.controllers.GestControl;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by Marek on 24/01/2017.
 */

public class FullScreenView extends View {
    private Bitmap _BMPimage;
    private String _IMGName;
    private Matrix _IMGmatrix;
    private int heightView;
    private int widthView;

    private GestureDetector _gestureDetector;
    private ScaleGestureDetector _scaleGestDetector;
    private int _IMGposX;
    private int _IMGposY;
    private boolean initialised = false;;

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

    public void initImg(){
        _IMGmatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        if(!initialised)
        {
            widthView = getMeasuredWidth();
            heightView = getMeasuredHeight();
            _IMGposX = widthView/2 - _BMPimage.getWidth()/2;
            _IMGposY = heightView/2 - _BMPimage.getHeight()/2;
            _IMGmatrix.setTranslate(_IMGposX, _IMGposY);
            initialised = true;
        }
        Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(BitmapScaler.dpToPx(20, getContext()));

        float textWidth = mTextPaint.measureText(_IMGName);
        float textBottomPadding = BitmapScaler.dpToPx(30, getContext());

        canvas.drawBitmap(_BMPimage, _IMGmatrix, null);
        canvas.drawText(_IMGName, widthView/2 - textWidth/2, heightView - textBottomPadding, mTextPaint);
    }

    public void set_BMPimage(Bitmap BMPimage)
    {
        this._BMPimage = BMPimage;
        initImg();
    }

    public void set_IMGName(String _IMGName) {
        this._IMGName = _IMGName;
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

    public void animateMove(float dx, float dy){
        //TODO : revoir les limites de positionnement & màj position
        float currentX = _IMGposX - dx;
        float currentY = _IMGposY - dy;
        boolean gaucheDansCadre = currentX >= -_BMPimage.getWidth()/2;
        boolean droiteDansCadre = currentX <= widthView -_BMPimage.getWidth()/2;
        boolean hautDansCadre   = currentY >= -_BMPimage.getHeight()/2;
        boolean basDansCadre    = currentY <= heightView -_BMPimage.getHeight()/2;

        if(!gaucheDansCadre || !droiteDansCadre)
            dx = 0;
        else _IMGposX -= dx;

        if(!hautDansCadre || !basDansCadre)
            dy = 0;
        else _IMGposY -= dy;

        _IMGmatrix.postTranslate(-dx, -dy);

        invalidate();
    }

    public void animateScale(float coefZoom){
        //TODO : à revoir - décalage en position x,y
        float tx = (widthView  - _BMPimage.getWidth()/2) * coefZoom;
        float ty = (heightView - _BMPimage.getHeight()/2) * coefZoom;
        _IMGmatrix.postScale(coefZoom, coefZoom, tx, ty);
        invalidate();
    }

    public void updateImgAfterScale(float coefZoom)
    {
        //TODO : actuellement buggué
//        int newWidth = _BMPimage.getWidth() * (int)coefZoom;
//        int newHeight = _BMPimage.getHeight() * (int)coefZoom;
//        _BMPimage = BitmapScaler.scaleToFill(_BMPimage,newWidth,newHeight);
    }

    public void animateFlingMove(float dx, float dy, long velocity)
    {
        //TODO : animation

        _IMGmatrix.postTranslate(-dx, -dy);

        invalidate();
    }
}
