package iut.lp2017.acpi.photos.models;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marek on 26/01/2017.
 */

public class ImageModel {
    protected Bitmap imageBitmap;
    protected int id;
    protected String nom;
    private String description;
    private String urlLink;
    private String localpath;
    private Context context;
    protected double taille;
    protected List<String> categories;

    public ImageModel()
    {
        imageBitmap = null;
        id = -1;
        nom = null;
        localpath = null;
        urlLink = null;
        context = null;
        taille = -1;
        categories = new ArrayList<String>();
    }

    public ImageModel(Context context, int id, String nom)
    {
            this();
            this.context = context;
            this.id = id;
            this.nom = nom;
    }

    public String getImageLink() {
        return this. urlLink;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Bitmap getImageBitmap() {
        return this.imageBitmap;
    }

    public void setImageBitmap(Bitmap imgBitmap) {
        this.imageBitmap = imgBitmap;
    }

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageLink(String imageLink) {
        this. urlLink = imageLink;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(double taille) {
        this.taille = taille;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getLocalpath() {
        return localpath;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void addCategorie(String categorie) {
        this.categories.add(categorie);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void updateBitmap()
    {
        updateLocalpath();

        if(imageExistsLocally())
        {
            updateBitmapFromLocalStorage();
        }
        else
        {
            updateBitmapFromURL();
            persistBitmapLocally();
        }
    }

    private void updateBitmapFromLocalStorage()
    {
        try
        {
            File f = new File(localpath);
            imageBitmap = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void updateBitmapFromURL()
    {
        try
        {
            URL url = new URL(this.urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            imageBitmap = BitmapFactory.decodeStream(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        String cats = ", catégories: [";
        for(String categ : categories)
            cats+= categ + ", ";
        cats = cats.substring(0,cats.length()-2) + "]";

        return "ImageModel{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", link='" + urlLink + '\'' +
                ", localPath='"+ localpath + '\'' +
                cats + '}';
    }

    private void updateLocalpath()
    {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("miniProjAndro", Context.MODE_PRIVATE);
        localpath = directory.getAbsolutePath() + '/' + nom;
    }

    private boolean imageExistsLocally()
    {
        return new File(localpath).exists();
    }

    private boolean persistBitmapLocally()
    {
        FileOutputStream fos = null;
        try {
            File img_path = new File(localpath);
            fos = new FileOutputStream(img_path);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

}