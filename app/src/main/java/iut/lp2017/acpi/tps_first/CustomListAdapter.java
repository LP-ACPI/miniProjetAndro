package iut.lp2017.acpi.tps_first;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

/**
 * Created by necesanym on 04/01/17.
 */
public class CustomListAdapter extends ArrayAdapter<ListItem> {

    private Context context;

    public CustomListAdapter(Context context, List<ListItem> list) {
        super(context, 0, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,parent, false);
        }

        customListHolder viewHolder = (customListHolder) convertView.getTag();
        ListItem item = getItem(position);

        if(viewHolder == null){
            viewHolder = new customListHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nameListItem);
            viewHolder.prenom = (TextView) convertView.findViewById(R.id.firstNameListItem);
            viewHolder.imgV = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.size = (TextView) convertView.findViewById(R.id.imgSize);
            convertView.setTag(viewHolder);
        }

        viewHolder.nom.setText(item.getNom());
        viewHolder.prenom.setText(item.getPrenom());
        Bitmap img = item.getImageBitmap();
        viewHolder.imgV.setImageBitmap(Bitmap.createScaledBitmap(img, (int)dpToPx(80,context),(int)dpToPx(80,context),false));
        viewHolder.size.setText(Double.toString(item.getTaille()));

        return convertView;
    }

    public static float dpToPx(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    private class customListHolder{
        public ImageView imgV;
        public TextView nom;
        public TextView prenom;
        public TextView size;

    }
}
