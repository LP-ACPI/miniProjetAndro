package iut.lp2017.acpi.imageproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.models.ImageModel;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created by Marek on 27/01/2017.
 */

public class DialogImageLayout extends ScrollView
{
    private Button dismissButton;
    private Button fullScreenButton;
    private ImageModel model;

    public DialogImageLayout(Context context) {
        super(context);
    }

    public DialogImageLayout(Context context, ImageModel model) {
        super(context);
        this.model = model;
        initDialogView();
    }

    public DialogImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDialogView();
    }

    public DialogImageLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDialogView();
    }

    public Button getDismissButton(){
        return dismissButton;
    }

    public ImageModel getModel() {
        return model;
    }

    public void setModel(ImageModel model) {
        this.model = model;
    }

    public Button getFullScreenButton() {
        return fullScreenButton;
    }

    private void initDialogView(){

        int ids = 1;

        RelativeLayout dialogRelLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams DiagParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        dialogRelLayout.setLayoutParams(DiagParams);
        setLayoutParams(DiagParams);

        int Offset10dp = (int)BitmapScaler.dpToPx(10,getContext());
        ImageView diagImage = new ImageView(getContext());
        diagImage.setId(ids++);
        RelativeLayout.LayoutParams diagImageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagImage.setLayoutParams(diagImageParams);
        diagImage.setPadding(Offset10dp*2, Offset10dp, Offset10dp*2, Offset10dp);

        TextView diagNom = new TextView(getContext());
        diagNom.setId(ids++);
        RelativeLayout.LayoutParams diagNomParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagNomParams.addRule(RelativeLayout.RIGHT_OF, diagImage.getId());
        diagNom.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
        diagNom.setPadding(0, Offset10dp, Offset10dp/2, Offset10dp);
        diagNom.setLayoutParams(diagNomParams);

        TextView diagDescription = new TextView(getContext());
        diagDescription.setId(ids++);
        RelativeLayout.LayoutParams diagDescParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagDescParams.addRule(RelativeLayout.RIGHT_OF,diagImage.getId());
        diagDescParams.addRule(RelativeLayout.BELOW, diagNom.getId());
        diagDescription.setPadding(0, 0, Offset10dp/2, 0);
        diagDescription.setLayoutParams(diagDescParams);

        TextView diagSize = new TextView(getContext());
        diagSize.setId(ids++);
        RelativeLayout.LayoutParams diagSizeParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagSizeParams.addRule(RelativeLayout.RIGHT_OF,diagImage.getId());
        diagSizeParams.addRule(RelativeLayout.BELOW, diagDescription.getId());
        diagSize.setLayoutParams(diagSizeParams);

        TextView diagCategories = new TextView(getContext());
        diagCategories.setId(ids++);
        RelativeLayout.LayoutParams diagCatsParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        diagCatsParams.addRule(RelativeLayout.BELOW, diagImage.getId());
        diagCatsParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        diagCategories.setLayoutParams(diagCatsParams);
        diagCategories.setTextAppearance(getContext(), android.R.style.TextAppearance_DeviceDefault_Medium);
        diagCategories.setPadding(0, Offset10dp, 0, Offset10dp);

        fullScreenButton = new Button(getContext());
        fullScreenButton.setId(ids++);
        RelativeLayout.LayoutParams fulscreenButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        fulscreenButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        fulscreenButtonParams.addRule(RelativeLayout.BELOW, diagCategories.getId());
        fullScreenButton.setText(getContext().getString(R.string.fullScreen));
        fullScreenButton.setLayoutParams(fulscreenButtonParams);

        dismissButton = new Button(getContext());
        RelativeLayout.LayoutParams dialogButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dialogButtonParams.addRule(RelativeLayout.BELOW, fullScreenButton.getId());
        dialogButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dismissButton.setText(getContext().getString(R.string.close));
        dismissButton.setLayoutParams(dialogButtonParams);

        Bitmap img = BitmapScaler.scaleToFitWidth(model.getImageBitmap(),(int)BitmapScaler.dpToPx(125,getContext()));
        diagImage.setImageBitmap(img);
        diagNom.setText(model.getNom());
        diagDescription.setText(model.getDescription());
        diagSize.setText(Double.toString(model.getTaille()));
        diagCategories.setText(getContext().getString(R.string.categories) + model.getCategoriesStringified());

        dialogRelLayout.addView(diagImage);
        dialogRelLayout.addView(diagNom);
        dialogRelLayout.addView(diagDescription);
        dialogRelLayout.addView(diagCategories);
        dialogRelLayout.addView(dismissButton);
        dialogRelLayout.addView(fullScreenButton);
        addView(dialogRelLayout);
    }
}
