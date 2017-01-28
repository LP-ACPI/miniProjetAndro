package iut.lp2017.acpi.imageproject.views;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.utilitaires.BitmapScaler;

/**
 * Created on 27/01/2017.
 */

public class DialogCategoriesLayout extends ScrollView
{
    private List<String> categoryList;
    private List<Button> categsButtons;
    private Button noCategoryFilterButton;
    private Button dismissButton;

    public DialogCategoriesLayout(Context context) {
        super(context);
    }

    public DialogCategoriesLayout(Context context, List<String> categoryList)
    {
        super(context);
        this.categoryList = categoryList;
        this.categsButtons = new ArrayList<Button>();
        initDialogView();
    }

    public DialogCategoriesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogCategoriesLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private void initDialogView()
    {
        int ids = 1;
        RelativeLayout relativeDialog = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams DiagParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        setLayoutParams(DiagParams);
        relativeDialog.setLayoutParams(DiagParams);

        boolean isPortrait = Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation;
        int buttonOffset;
        if(isPortrait)
            buttonOffset = (int)BitmapScaler.dpToPx(10,getContext());
        else
            buttonOffset = (int)BitmapScaler.dpToPx(5,getContext());

        Button categoryButton = null;
        for(String category: categoryList){
            categoryButton = new Button(getContext());
            categoryButton.setId(ids++);
            RelativeLayout.LayoutParams categoryButtonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            categoryButtonParams.setMargins(0,buttonOffset,0,buttonOffset);
            categoryButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            categoryButtonParams.addRule(RelativeLayout.BELOW, categoryButton.getId()-1);

            categoryButton.setText(category);
            categoryButton.setLayoutParams(categoryButtonParams);
            relativeDialog.addView(categoryButton);
            categsButtons.add(categoryButton);
        }

        noCategoryFilterButton = new Button(getContext());
        noCategoryFilterButton.setId(ids++);
        RelativeLayout.LayoutParams noCategoryFilterButtonParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        noCategoryFilterButtonParams.setMargins(0,buttonOffset*2,0,0);
        noCategoryFilterButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        noCategoryFilterButtonParams.addRule(RelativeLayout.BELOW,categoryButton.getId());
        noCategoryFilterButton.setText(getContext().getString(R.string.allCategories));
        noCategoryFilterButton.setLayoutParams(noCategoryFilterButtonParams);
        relativeDialog.addView(noCategoryFilterButton);

        dismissButton = new Button(getContext());
        RelativeLayout.LayoutParams dialogButtonParams = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        dialogButtonParams.setMargins(5,buttonOffset*2,5,5);
        dialogButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dialogButtonParams.addRule(RelativeLayout.BELOW,noCategoryFilterButton.getId());

        dismissButton.setText(getContext().getString(R.string.close));
        dismissButton.setLayoutParams(dialogButtonParams);

        relativeDialog.addView(dismissButton);
        addView(relativeDialog);
    }

    public Button getDismissButton() {
        return dismissButton;
    }
    public List<Button> getCategoryButtons() {
        return categsButtons;
    }
    public Button getNoCategoryFilterButton() { return noCategoryFilterButton; }
}
