package iut.lp2017.acpi.imageproject.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.List;

import iut.lp2017.acpi.R;

/**
 * Created by Marek on 27/01/2017.
 */

public class DialogCategoriesLayout extends RelativeLayout {

    private List<String> categoryList;
    private Button dismissButton;

    public DialogCategoriesLayout(Context context) {
        super(context);
    }
    public DialogCategoriesLayout(Context context, List<String> categoryList) {
        super(context);
        this.categoryList = categoryList;
        initDialogView();
    }
    public DialogCategoriesLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogCategoriesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initDialogView() {

        int ids = 1;

        RelativeLayout.LayoutParams DiagParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        setLayoutParams(DiagParams);

        for(String category: categoryList){
            Button categoryButton = new Button(getContext());
            categoryButton.setId(ids++);
            RelativeLayout.LayoutParams categoryButtonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            categoryButtonParams.setMargins(0,10,0,10);
            categoryButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            categoryButtonParams.addRule(RelativeLayout.BELOW, categoryButton.getId()-1);

            categoryButton.setText(category);

            categoryButton.setPadding(10, 10, 10, 10);
            categoryButton.setLayoutParams(categoryButtonParams);
            addView(categoryButton);
        }

        dismissButton = new Button(getContext());
        RelativeLayout.LayoutParams dialogButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        dialogButtonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dialogButtonParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dismissButton.setText(getContext().getString(R.string.close));
        dismissButton.setLayoutParams(dialogButtonParams);

        addView(dismissButton);
    }

    public Button getDismissButton() {
        return dismissButton;
    }
}
