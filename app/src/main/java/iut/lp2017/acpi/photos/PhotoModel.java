package iut.lp2017.acpi.photos;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import iut.lp2017.acpi.tps_first.ListItem;

/**
 * Created by necesanym on 18/01/17.
 */
public class PhotoModel extends ListItem {

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    public PhotoModel(Context context, String imageSource, String nom, String prenom) {
        super(context, imageSource, nom, prenom);
    }

    public PhotoModel(Context context, String nom, String prenom) {
        super(context, nom, prenom);
    }

    public PhotoModel(Context context, ListItem listitem) {
        super();
        setImageBitmap(listitem.getImageBitmap());
        setNom(listitem.getNom());
        setPrenom(listitem.getPrenom());
        setTaille(listitem.getImageBitmap().getByteCount());
    }


}
