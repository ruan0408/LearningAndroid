package br.com.drtis.students;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.drtis.students.model.Student;

/**
 * Created by webmaster on 30/01/17.
 */

public class FormHelper {

    private final EditText nameField;
    private final EditText addressField;
    private final EditText telephoneField;
    private final EditText siteField;
    private final RatingBar ratingBar;
    private final ImageView photo;

    private Student student;

    public FormHelper(FormActivity activity) {
        nameField = (EditText) activity.findViewById(R.id.form_name);
        addressField = (EditText) activity.findViewById(R.id.form_address);
        telephoneField = (EditText) activity.findViewById(R.id.form_telephone);
        siteField = (EditText) activity.findViewById(R.id.form_site);
        ratingBar = (RatingBar) activity.findViewById(R.id.form_grade);
        photo = (ImageView) activity.findViewById(R.id.form_photo);
        student = new Student();
    }

    public Student getStudent() {

        student.setName(nameField.getText().toString());
        student.setAddress(addressField.getText().toString());
        student.setTelephone(telephoneField.getText().toString());
        student.setSite(siteField.getText().toString());
        student.setGrade((double) ratingBar.getProgress());
        student.setPhotoPath((String) photo.getTag());

        return student;
    }

    public void fillForm(Student student) {
        nameField.setText(student.getName());
        addressField.setText(student.getAddress());
        telephoneField.setText(student.getTelephone());
        siteField.setText(student.getSite());
        ratingBar.setProgress(student.getGrade().intValue());
        loadImage(student.getPhotoPath());
        this.student = student;
    }

    public void loadImage(String photoPath) {
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            photo.setImageBitmap(bitmap);
            photo.setScaleType(ImageView.ScaleType.FIT_XY);
            photo.setTag(photoPath);
        }
    }
}
