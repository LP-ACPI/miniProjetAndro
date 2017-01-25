package iut.lp2017.acpi.photos;

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

import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by Marek on 24/01/2017.
 */

public class FullScreenView extends View {
    private Bitmap _BMPimage;
    private String _IMGName;
    private Matrix _IMGmatrix;
    private float _IMG_posX;
    private float _IMG_posY;

    private GestureDetector _gestureDetector;
    private ScaleGestureDetector _scaleGestDetector;

    public FullScreenView(Context context) {
        super(context);
        initGests();
    }

    public FullScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGests();
    }

    public FullScreenView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGests();
    }

    public void initImg(){ //CRASH - getMeasuredWidth() -> 0!

        _IMGmatrix = new Matrix();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int center_X = width / 2;
        int center_Y = height / 2;

        _BMPimage = BitmapScaler.scaleToFitWidth(_BMPimage,width);
        int img_height =  _BMPimage.getHeight();
        int img_width  =  _BMPimage.getWidth();

        _IMG_posX = center_X - img_width / 2;
        _IMG_posY = center_Y - img_height / 2;
        _IMGmatrix.postTranslate(_IMG_posX, _IMG_posY);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int center_X = width / 2;
        int center_Y = height / 2;

        Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(BitmapScaler.dpToPx(20, getContext()));

        float textWidth = mTextPaint.measureText(_IMGName);
        float textBottomPadding = BitmapScaler.dpToPx(30, getContext());

        canvas.drawBitmap(_BMPimage, _IMGmatrix, null);
        canvas.drawText(_IMGName, center_X - textWidth / 2, height - textBottomPadding, mTextPaint);
    }

    public void set_BMPimage(Bitmap _BMPimage) {
        this._BMPimage = _BMPimage;
    }

    public void set_IMGName(String _IMGName) {
        this._IMGName = _IMGName;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean toucherBasique = _gestureDetector.onTouchEvent(event);
        boolean toucherZoom = _scaleGestDetector.onTouchEvent(event);

        return toucherBasique || toucherZoom;
    }

    private void initGests(){
        GestControl _gestControl = new GestControl(this);
        _gestureDetector = new GestureDetector(getContext(),_gestControl);
        _gestureDetector.setOnDoubleTapListener(_gestControl);

        _scaleGestDetector = new ScaleGestureDetector(getContext(),_gestControl);
    }

    public void animateMove(float dx, float dy){
        _IMG_posX +=dx;
        _IMG_posY +=dy;
        _IMGmatrix.postTranslate(-dx, -dy);
        invalidate();
    }

}
