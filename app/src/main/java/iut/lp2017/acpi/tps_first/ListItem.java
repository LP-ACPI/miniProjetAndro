package iut.lp2017.acpi.tps_first;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by necesanym on 04/01/17.
 */
public class ListItem {

    protected Bitmap imageBitmap;
    private String imageSource;
    protected String nom;
    protected String prenom;
    protected double taille;

    public ListItem(){}

    public ListItem(Context context, String nom, String prenom) {
        try {
            String imgs[] = {"chats-web.png","gg.jpeg","images.jpeg", "imagesdf.jpeg", "test.jpg"};
            Random r = new Random();
            int ixImg = r.nextInt(5);
            this.imageSource = imgs[ixImg];
            InputStream stream = context.getAssets().open(imageSource);
            this.imageBitmap = BitmapFactory.decodeStream(stream);
            this.taille = imageBitmap.getRowBytes();
            this.nom = nom;
            this.prenom = prenom;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ListItem(Context context, String imageSource, String nom, String prenom) {
        this(context,nom,prenom);
        try {
            this.imageSource = imageSource;
            InputStream stream = context.getAssets().open(imageSource);
            setImageBitmap(BitmapFactory.decodeStream(stream));
            setTaille(getImageBitmap().getRowBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getImageSource() {
        return this.imageSource;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Bitmap getImageBitmap() {
        return this.imageBitmap;
    }

    public void setImageBitmap(Bitmap imgBitmap) {
        this.imageBitmap = imgBitmap;
    }
    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }


}
