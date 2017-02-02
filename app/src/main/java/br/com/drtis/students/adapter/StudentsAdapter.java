package br.com.drtis.students.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.drtis.students.R;
import br.com.drtis.students.model.Student;

/**
 * Created by webmaster on 01/02/17.
 */

public class StudentsAdapter extends BaseAdapter {

    private final List<Student> students;
    private final Context context;

    public StudentsAdapter(Context context, List<Student> students) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = students.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View listItem = convertView;
        if (listItem == null)
            listItem = inflater.inflate(R.layout.list_item, parent, false);

        TextView nameField = (TextView) listItem.findViewById(R.id.list_item_name);
        TextView telephoneField = (TextView) listItem.findViewById(R.id.list_item_telephone);
        ImageView photoField = (ImageView) listItem.findViewById(R.id.list_item_photo);

        nameField.setText(student.getName());
        telephoneField.setText(student.getTelephone());

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView addressField = (TextView) listItem.findViewById(R.id.list_item_address);
            TextView siteField = (TextView) listItem.findViewById(R.id.list_item_site);

            addressField.setText(student.getAddress());
            siteField.setText(student.getSite());
        }

        String photoPath = student.getPhotoPath();
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            photoField.setImageBitmap(bitmap);
            photoField.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return listItem;
    }
}
