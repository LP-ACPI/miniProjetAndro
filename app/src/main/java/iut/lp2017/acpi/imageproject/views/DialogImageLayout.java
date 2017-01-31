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
 * Created on 27/01/2017.
 */

public class DialogImageLayout extends ScrollView
{
    private Button _dismissButton;
    private Button _fullScreenButton;
    private ImageModel _model;

    public DialogImageLayout(Context context) {
        super(context);
    }

    public DialogImageLayout(Context context, ImageModel model)
    {
        super(context);
        _model = model;
        initDialogView();
    }

    public DialogImageLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initDialogView();
    }

    public DialogImageLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initDialogView();
    }

    public Button getDismissButton(){
        return _dismissButton;
    }

    public ImageModel getModel() {
        return _model;
    }

    public void setModel(ImageModel model) {
        _model = model;
    }

    public Button getFullScreenButton() {
        return _fullScreenButton;
    }

    private void initDialogView()
    {
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

        _fullScreenButton = new Button(getContext());
        _fullScreenButton.setId(ids++);
        RelativeLayout.LayoutParams fulscreenButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        fulscreenButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        fulscreenButtonParams.addRule(RelativeLayout.BELOW, diagCategories.getId());
        _fullScreenButton.setText(getContext().getString(R.string.fullScreen));
        _fullScreenButton.setLayoutParams(fulscreenButtonParams);

        _dismissButton = new Button(getContext());
        RelativeLayout.LayoutParams dialogButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dialogButtonParams.addRule(RelativeLayout.BELOW, _fullScreenButton.getId());
        dialogButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        _dismissButton.setText(getContext().getString(R.string.close));
        _dismissButton.setLayoutParams(dialogButtonParams);

        Bitmap img = BitmapScaler.scaleToFitWidth(_model.getImageBitmap(),(int)BitmapScaler.dpToPx(125,getContext()));
        diagImage.setImageBitmap(img);
        diagNom.setText(_model.getNom());
        diagDescription.setText(_model.getDescription());
        diagSize.setText(Double.toString(_model.getTaille()));
        diagCategories.setText(getContext().getString(R.string.categories) + _model.getCategoriesStringified());

        dialogRelLayout.addView(diagImage);
        dialogRelLayout.addView(diagNom);
        dialogRelLayout.addView(diagDescription);
        dialogRelLayout.addView(diagCategories);
        dialogRelLayout.addView(_dismissButton);
        dialogRelLayout.addView(_fullScreenButton);
        addView(dialogRelLayout);
    }
}
