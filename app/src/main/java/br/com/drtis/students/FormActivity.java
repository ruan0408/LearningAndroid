package br.com.drtis.students;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.drtis.students.dao.StudentDAO;
import br.com.drtis.students.model.Student;

public class FormActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private FormHelper helper;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        helper = new FormHelper(this);

        Student student = (Student) getIntent().getSerializableExtra("student");
        if (student != null) {
            helper.fillForm(student);
        }

        Button photoButton = (Button) findViewById(R.id.form_button_photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //TODO fix this to work with API 25, change targetSdk to 25 on app build.gradle

//            System.out.println(getFilesDir().getAbsolutePath());
//            File file = null;
//            try {
//                file = File.createTempFile(System.currentTimeMillis()+"", ".jpg", new File(getFilesDir(), "images"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            File photoPath = new File(getFilesDir(), ".");
//            File photoFile = new File(photoPath, System.currentTimeMillis() + ".jpg");
//            Uri photoUri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", photoFile);


                photoPath = FormActivity.this.getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File photoFile = new File(photoPath);
//            Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//            cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                FormActivity.this.startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO:
                helper.loadImage(photoPath);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                Student student = helper.getStudent();

                StudentDAO studentDAO = new StudentDAO(this);

                if (student.getId() != null){
                    studentDAO.update(student);
                } else {
                    studentDAO.insert(student);
                }
                studentDAO.close();

                Toast.makeText(FormActivity.this, "Student " + student.getName() + " saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
