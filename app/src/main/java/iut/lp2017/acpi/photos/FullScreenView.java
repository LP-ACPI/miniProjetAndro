package iut.lp2017.acpi.photos;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by Marek on 24/01/2017.
 */

public class FullScreenView extends View {

    private GestureDetector _gestureDetector;
    private ScaleGestureDetector _scaleGestDetector;

    public FullScreenView(Context context) {
        super(context);
    }

    public FullScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return true;
    }

    private void initGests(){
        GestControl _gestControl = new GestControl(this);
        _gestureDetector = new GestureDetector(getContext(),_gestControl);
        _gestureDetector.setOnDoubleTapListener(_gestControl);

        _scaleGestDetector = new ScaleGestureDetector(getContext(),_gestControl);
    }

}
