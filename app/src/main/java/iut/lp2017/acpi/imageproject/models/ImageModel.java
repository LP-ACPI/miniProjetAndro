package iut.lp2017.acpi.imageproject.models;

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
 * Created on 26/01/2017.
 */

public class ImageModel {
    private static Context _sContext;
    private List<String> _categories;
    private Bitmap _imageBitmap;
    private int _id;
    private String _nom;
    private String _description;
    private String _urlLink;
    private String _localpath;
    private double _taille;

    public ImageModel()
    {
        _id = -1;
        _nom = null;
        _taille = -1;
        _urlLink = null;
        _sContext = null;
        _localpath = null;
        _imageBitmap = null;
        _categories = new ArrayList<String>();
    }

    public ImageModel(Context context, int id, String nom)
    {
        this();
        _id = id;
        _nom = nom;
        _sContext = context;
    }

    public String getImageLink()
    {
        return _urlLink;
    }

    public String getNom()
    {
        return _nom;
    }

    public void setNom(String nom)
    {
        _nom = nom;
    }

    public Bitmap getImageBitmap()
    {
        return _imageBitmap;
    }

    public void setImageBitmap(Bitmap imgBitmap)
    {
        _imageBitmap = imgBitmap;
    }

     public int getId()
     {
        return _id;
    }

    public void setId(int id)
    {
        _id = id;
    }

    public void setImageLink(String imageLink)
    {
        _urlLink = imageLink;
    }

    public double getTaille()
    {
        return _taille;
    }

    public void setTaille(double taille)
    {
        _taille = taille;
    }

    public String getUrlLink()
    {
        return _urlLink;
    }

    public void setUrlLink(String urlLink)
    {
        _urlLink = urlLink;
    }

    public String getLocalpath()
    {
        return _localpath;
    }

    public void setLocalpath(String localpath) { _localpath = localpath; }

    public Context getContext() {
        return _sContext;
    }

    public void setContext(Context context) {
        _sContext = context;
    }

    public List<String> getCategories() {
        return _categories;
    }

    public void setCategories(List<String> categories) { _categories = categories; }

    public void addCategorie(String categorie) {
        _categories.add(categorie);
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        _description = description;
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

    @Override
    public String toString()
    {
        return "ImageModel{" +
                "id=" + _id +
                ", nom='" + _nom + '\'' +
                ", description='" + _description + '\'' +
                ", link='" + _urlLink + '\'' +
                ", localPath='"+ _localpath + '\'' +
                ", catégories: [" + getCategoriesStringified() + "]}";
    }

    public String getCategoriesStringified()
    {
        String categs = "";
        for(String categ : _categories)
            categs += categ + ", ";
        return categs.length() > 0 ? categs.substring(0,categs.length()-2) : "";
    }

    private void updateBitmapFromLocalStorage()
    {
        try
        {
            File f = new File(_localpath);
            _imageBitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            _taille = _imageBitmap.getRowBytes();
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
            URL url = new URL(_urlLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            _imageBitmap = BitmapFactory.decodeStream(input);
            _taille = _imageBitmap.getRowBytes();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void updateLocalpath()
    {
        _localpath = getLocalSaveDirectory() + '/' + _nom;
    }

    private static String getLocalSaveDirectory()
    {
        ContextWrapper cw = new ContextWrapper(_sContext);
        File directory = cw.getDir("miniProjAndro", Context.MODE_PRIVATE);
        return directory.getAbsolutePath();
    }

    private boolean imageExistsLocally()
    {
        return new File(_localpath).exists();
    }

    private boolean persistBitmapLocally()
    {
        FileOutputStream fos = null;
        try {
            File img_path = new File(_localpath);
            fos = new FileOutputStream(img_path);
            _imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
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