package iut.lp2017.acpi.photos.controllers;

import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

import iut.lp2017.acpi.photos.views.FullScreenView;

/**
 * Created by Marek on 24/01/2017.
 */

public class GestControl implements OnGestureListener,OnScaleGestureListener,OnDoubleTapListener {

    private FullScreenView view;

    public GestControl(FullScreenView view) {
        this.view = view;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float zoom = detector.getScaleFactor();
        zoom = Math.max(0.1f, Math.min(zoom, 5.0f));
        view.animateScale(zoom);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        float zoom = detector.getScaleFactor();
        zoom = Math.max(0.1f, Math.min(zoom, 5.0f));
        view.updateImgAfterScale(zoom);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        view.animateMove(distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //TODO : animation
        final float coefTemps = 0.4f;
        final float dx = (coefTemps * velocityX / 2);
        final float dy = (coefTemps * velocityY / 2);
//
//        view.animateFlingMove(-dx,-dy);
        Log.i("Fling X", String.valueOf(dx));
        Log.i("Fling Y", String.valueOf(dy));
        return false;
    }
}
