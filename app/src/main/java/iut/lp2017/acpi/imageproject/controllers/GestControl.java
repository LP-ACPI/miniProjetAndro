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
    private FullScreenView view;
    float coefZoom;
    boolean imgFitToWitdth;
    boolean doubleTapZoomSwitch;
    boolean zoomOut;

    public GestControl(FullScreenView view)
    {
        this.view = view;
        zoomOut = true;
        imgFitToWitdth = true;
        doubleTapZoomSwitch = true;
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

        if(doubleTapZoomSwitch)
        {
            if(!zoomOut)
                view.animateScale(centerX, centerY, 0.4f);
            else
                view.animateScale(centerX, centerY, 2.5f);
        }
        else
        {
            if(zoomOut)
                view.animateScale(centerX, centerY, 0.4f);
            else
                view.animateScale(centerX, centerY, 2.5f);
            zoomOut = !zoomOut;
        }
        doubleTapZoomSwitch = !doubleTapZoomSwitch;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e)
    {
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        coefZoom = detector.getScaleFactor();
        coefZoom = Math.max(0.1f, Math.min(coefZoom, 5.0f));
        float pivotPosX = detector.getFocusX();
        float pivotPosY = detector.getFocusY();
        view.animateScale(pivotPosX,pivotPosY,coefZoom);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector)
    {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        view.updateImageAfterScale(coefZoom);
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
        view.animateMove(distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e)
    {
        imgFitToWitdth = !imgFitToWitdth;
        view.intialiseView(imgFitToWitdth);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        final float coefTemps = 0.2f;
        final float dx = (coefTemps * velocityX / 2);
        final float dy = (coefTemps * velocityY / 2);

        view.animateFlingMove(dx,dy);
        return false;
    }
}
