package iut.lp2017.acpi.imageproject.daohandlers;

import android.content.Context;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import iut.lp2017.acpi.imageproject.models.ImageModel;

/**
 * Created on 26/01/2017.
 */

public class ImageHandler extends DefaultHandler
{
    boolean idTag = false;
    boolean nameTag = false;
    boolean descriptTag = false;

    private List<ImageModel> imgList;
    private List<String> distinctCategories;
    private ImageModel img;
    private static Context context;

    public ImageHandler(Context context)
    {
        this.context = context;
    }

    public List<String> getDistinctCategories()
    {
        return distinctCategories;
    }
    public List<ImageModel> getImgList()
    {
        return imgList;
    }

    @Override
    public void startElement(String nsURI, String localName,
                             String rawName, Attributes attributes) throws SAXException
    {
        if (rawName.equalsIgnoreCase("image"))
        {
            img = new ImageModel();
            img.setContext(context);
            if(imgList == null)
            {
                imgList = new ArrayList<ImageModel>();
                distinctCategories = new ArrayList<String>();
            }
        }
        else if (rawName.equalsIgnoreCase("id"))
        {
            idTag = true;
        }
        else if (rawName.equalsIgnoreCase("name"))
        {
            nameTag = true;
        }
        else if (rawName.equalsIgnoreCase("description"))
        {
            descriptTag = true;
        }
        else if (rawName.equalsIgnoreCase("link"))
        {
            img.setImageLink(attributes.getValue("href"));
            img.updateBitmap();
        }
        else if(rawName.equalsIgnoreCase("category"))
        {
            String categ = attributes.getValue("label");
            img.addCategorie(categ);
            if(!distinctCategories.contains(categ))
                distinctCategories.add(categ);
        }
    }

    @Override
    public void endElement(String nsURI, String localName, String rawName) throws SAXException
    {
        if (rawName.equalsIgnoreCase("image"))
        {
            imgList.add(img);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (idTag)
        {
            img.setId(Integer.parseInt(new String(ch, start, length)));
            idTag = false;
        }
        else if (nameTag)
        {
            img.setNom(new String(ch, start, length));
            nameTag = false;
        }
        else if (descriptTag)
        {
            img.setDescription(new String(ch, start, length));
            descriptTag = false;
        }
    }
}

