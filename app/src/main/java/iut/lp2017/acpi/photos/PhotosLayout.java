package iut.lp2017.acpi.photos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by necesanym on 18/01/17.
 */
public class PhotosLayout extends RelativeLayout {

    private Button dismissButton;
    private Button fullScreenButton;
    private PhotoModel model;

    public PhotosLayout(Context context) {
        super(context);
    }
    public PhotosLayout(Context context,PhotoModel model) {
        super(context);
        initDialogView(model);
    }

    public PhotosLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialogView(model);
    }

    public PhotosLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDialogView(model);
    }

    private void initDialogView(PhotoModel model){

        setModel(model);
        int ids = 1;

        RelativeLayout.LayoutParams DiagParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        setLayoutParams(DiagParams);

        ImageView diagImage = new ImageView(getContext());
        diagImage.setId(ids++);
        RelativeLayout.LayoutParams diagImageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagImage.setLayoutParams(diagImageParams);
        diagImage.setPadding(20, 10, 20, 10);

        TextView diagNom = new TextView(getContext());
        diagNom.setId(ids++);
        RelativeLayout.LayoutParams diagNomParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagNomParams.addRule(RelativeLayout.RIGHT_OF,diagImage.getId());
        diagNom.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
        diagNom.setLayoutParams(diagNomParams);


        TextView diagPrenom = new TextView(getContext());
        diagPrenom.setId(ids++);
        RelativeLayout.LayoutParams diagPrenomParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagPrenomParams.addRule(RelativeLayout.RIGHT_OF,diagImage.getId());
        diagPrenomParams.addRule(RelativeLayout.BELOW, diagNom.getId());
        diagPrenom.setLayoutParams(diagPrenomParams);

        TextView diagSize = new TextView(getContext());
        diagSize.setId(ids++);
        RelativeLayout.LayoutParams diagSizeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagSizeParams.addRule(RelativeLayout.RIGHT_OF,diagImage.getId());
        diagSizeParams.addRule(RelativeLayout.BELOW, diagPrenom.getId());
        diagSize.setLayoutParams(diagSizeParams);

        fullScreenButton = new Button(getContext());
        fullScreenButton.setId(ids++);
        RelativeLayout.LayoutParams fulscreenButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        fulscreenButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        fulscreenButtonParams.addRule(RelativeLayout.BELOW, diagImage.getId());
        fullScreenButton.setText(getContext().getString(R.string.fullScreen));
        fullScreenButton.setLayoutParams(fulscreenButtonParams);

        dismissButton = new Button(getContext());
        RelativeLayout.LayoutParams dialogButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dialogButtonParams.addRule(RelativeLayout.BELOW, fullScreenButton.getId());
        dialogButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dismissButton.setText(getContext().getString(R.string.close));
        dismissButton.setLayoutParams(dialogButtonParams);


        Bitmap img = model.getImageBitmap();
        int imgDims = 100;
        diagImage.setImageBitmap(Bitmap.createScaledBitmap(img, (int)dpToPx(imgDims,getContext()), (int)dpToPx(imgDims,getContext()), false));
        diagNom.setText(model.getNom());
        diagPrenom.setText(model.getPrenom());
        diagSize.setText(Double.toString(model.getTaille()));

        addView(diagImage);
        addView(diagNom);
        addView(diagPrenom);
        addView(diagSize);
        addView(dismissButton);
        addView(fullScreenButton);
    }


    private void initFullScreenView(String imageSource){}



    public Button getDismissButton(){
        return dismissButton;
    }


    public PhotoModel getModel() {
        return model;
    }

    public void setModel(PhotoModel model) {
        this.model = model;
    }

    public Button getFullScreenButton() {
        return fullScreenButton;
    }

    public static float dpToPx(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }
}
