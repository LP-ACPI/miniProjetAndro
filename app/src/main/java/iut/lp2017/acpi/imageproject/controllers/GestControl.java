package iut.lp2017.acpi.imageproject.controllers;

import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

import iut.lp2017.acpi.imageproject.views.FullScreenView;

/**
 * Created on 24/01/2017.
 */

public class GestControl implements OnGestureListener,OnScaleGestureListener,OnDoubleTapListener
{
    private boolean _doubleTapZoomSwitch;
    private boolean _imgFitToWitdth;
    private boolean _zoomOut;
    private float _coefZoom;
    private FullScreenView _view;

    public GestControl(FullScreenView view)
    {
        _view = view;
        _zoomOut = true;
        _imgFitToWitdth = true;
        _doubleTapZoomSwitch = true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        float centerX = e.getX();
        float centerY = e.getY();

        if(_doubleTapZoomSwitch)
        {
            if(!_zoomOut)
                _view.animateScale(centerX, centerY, 0.4f);
            else
                _view.animateScale(centerX, centerY, 2.5f);
        }
        else
        {
            if(_zoomOut)
                _view.animateScale(centerX, centerY, 0.4f);
            else
                _view.animateScale(centerX, centerY, 2.5f);
            _zoomOut = !_zoomOut;
        }
        _doubleTapZoomSwitch = !_doubleTapZoomSwitch;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector)
    {
        _coefZoom = detector.getScaleFactor();
        _coefZoom = Math.max(0.1f, Math.min(_coefZoom, 5.0f));
        float pivotPosX = detector.getFocusX();
        float pivotPosY = detector.getFocusY();
        _view.animateScale(pivotPosX,pivotPosY, _coefZoom);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        _view.updateImageAfterScale(_coefZoom);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) { }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
    {
        _view.animateMove(distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        _imgFitToWitdth = !_imgFitToWitdth;
        _view.intialiseView(_imgFitToWitdth);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
    {
        final float coefTemps = 0.2f;
        final float dx = (coefTemps * velocityX / 2);
        final float dy = (coefTemps * velocityY / 2);

        _view.animateFlingMove(dx,dy);
        return false;
    }
}
