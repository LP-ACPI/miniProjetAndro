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
    private static Context _sContext;
    private boolean _idTag = false;
    private boolean _nameTag = false;

    private boolean _descriptTag = false;
    private List<String> _distinctCategories;
    private List<ImageModel> _imgList;
    private ImageModel _img;

    public ImageHandler(Context context)
    {
        _sContext = context;
    }

    public List<String> getDistinctCategories()
    {
        return _distinctCategories;
    }

    public List<ImageModel> getImgList()
    {
        return _imgList;
    }

    @Override
    public void startElement(String nsURI, String localName,
                             String rawName, Attributes attributes) throws SAXException
    {
        if (rawName.equalsIgnoreCase("image"))
        {
            _img = new ImageModel();
            _img.setContext(_sContext);
            if(_imgList == null)
            {
                _imgList = new ArrayList<ImageModel>();
                _distinctCategories = new ArrayList<String>();
            }
        }
        else if (rawName.equalsIgnoreCase("id"))
        {
            _idTag = true;
        }
        else if (rawName.equalsIgnoreCase("name"))
        {
            _nameTag = true;
        }
        else if (rawName.equalsIgnoreCase("description"))
        {
            _descriptTag = true;
        }
        else if (rawName.equalsIgnoreCase("link"))
        {
            _img.setImageLink(attributes.getValue("href"));
            _img.updateBitmap();
        }
        else if(rawName.equalsIgnoreCase("category"))
        {
            String categ = attributes.getValue("label");
            _img.addCategorie(categ);
            if(!_distinctCategories.contains(categ))
                _distinctCategories.add(categ);
        }
    }

    @Override
    public void endElement(String nsURI, String localName, String rawName) throws SAXException
    {
        if (rawName.equalsIgnoreCase("image"))
        {
            _imgList.add(_img);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        if (_idTag)
        {
            _img.setId(Integer.parseInt(new String(ch, start, length)));
            _idTag = false;
        }
        else if (_nameTag)
        {
            _img.setNom(new String(ch, start, length));
            _nameTag = false;
        }
        else if (_descriptTag)
        {
            _img.setDescription(new String(ch, start, length));
            _descriptTag = false;
        }
    }
}

