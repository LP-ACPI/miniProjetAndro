package iut.lp2017.acpi.histo_tps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import iut.lp2017.acpi.R;
import iut.lp2017.acpi.imageproject.controllers.ImageController;

/**
 * Created by necesanym on 04/01/17.
 */
public class ListActivity extends Activity {

    private final Context context = this;
    private static ListView tpListView;
    private static List<String> nameList;
    private static List<String> firstNameList;
    private static List<String> imgSrcList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tpListView = (ListView) findViewById(R.id.tpListView);

        initInstance();
        initListsItemDialog(tpListView);

//        Button resetButton = (Button) findViewById(R.id.resetButton);
/*        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
//        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveInstance();
    }

    private List generateItems(List<String> noms, List<String> prenoms, List<String> imgs){
        List<ListItem> out = new ArrayList<ListItem>();
        int i = 0;
        for(String prenom: prenoms){
             out.add(new ListItem(context,imgs.get(i), noms.get(i),prenom));
             i++;
        }
        return out;
    }

    private void initListsItemDialog(ListView listV){

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.item_dialog);
                ListItem li = (ListItem) parent.getAdapter().getItem(position);
                dialog.setTitle(li.getPrenom() + " " + li.getNom());

                dialog.setContentView(initDialogView(dialog, li));

                dialog.show();
            }
        });

    }

    private void saveInstance(){
        DataHandler.getInstance().setFirstNameList(firstNameList);
        DataHandler.getInstance().setImageList(imgSrcList);
        DataHandler.getInstance().setNameList(nameList);
    }

    private void initInstance(){

        firstNameList = DataHandler.getInstance().getFirstNameList();
        imgSrcList = DataHandler.getInstance().getImageList();
        nameList = DataHandler.getInstance().getNameList();

        List<ListItem> items = generateItems(nameList,firstNameList,imgSrcList);
        int lastIx = items.size();

        Intent SecondActivityInt = getIntent();
        String nom = SecondActivityInt.getStringExtra(SecondActivity.NOM_LABEL);
        String prenom = SecondActivityInt.getStringExtra(SecondActivity.PRENOM_LABEL);

        nameList.add(nom);
        firstNameList.add(prenom);
        items.add(new ListItem(context,nameList.get(lastIx),firstNameList.get(lastIx)));
        imgSrcList.add(items.get(lastIx).getImageSource());

        final CustomListAdapter listAdapter = new CustomListAdapter(this, items);
        tpListView.setAdapter(listAdapter);
    }


    private void reset(){
        firstNameList.clear();
        nameList.clear();
        imgSrcList.clear();
        final CustomListAdapter listAdapter = new CustomListAdapter(this, new ArrayList<ListItem>());
        tpListView.setAdapter(listAdapter);
    }

    private RelativeLayout initDialogView(final Dialog diag, final ListItem li){

        PhotosLayout diagLayout = new PhotosLayout(context,new PhotoModel(context,li));

        diagLayout.getDismissButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.dismiss();
            }
        });

        diagLayout.getFullScreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageController.getInstance().showFullScreen(context, li.getImageSource(),li.getNom());
            }
        });

        return diagLayout;
    }
}
