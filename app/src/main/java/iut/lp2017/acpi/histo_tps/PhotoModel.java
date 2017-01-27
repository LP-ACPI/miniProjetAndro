package iut.lp2017.acpi.histo_tps;

import android.content.Context;

/**
 * Created by necesanym on 18/01/17.
 */
public class PhotoModel extends ListItem {

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
        setTaille(listitem.getImageBitmap().getRowBytes());
    }


}
