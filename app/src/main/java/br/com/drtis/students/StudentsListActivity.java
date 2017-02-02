package br.com.drtis.students;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.drtis.students.adapter.StudentsAdapter;
import br.com.drtis.students.converter.StudentConverter;
import br.com.drtis.students.model.Student;
import br.com.drtis.students.dao.StudentDAO;


public class StudentsListActivity extends AppCompatActivity {

    private ListView studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        studentList = (ListView) findViewById(R.id.student_list);

        Button newStudent = (Button) findViewById(R.id.button_new_student);
        newStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToForm = new Intent(StudentsListActivity.this, FormActivity.class);
                StudentsListActivity.this.startActivity(goToForm);
            }
        });

        registerForContextMenu(studentList);

        studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {
                Student student = (Student) studentList.getItemAtPosition(position);

                Intent goToForm = new Intent(StudentsListActivity.this, FormActivity.class);
                goToForm.putExtra("student", student);

                startActivity(goToForm);
            }
        });

        if (ActivityCompat.checkSelfPermission(StudentsListActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(StudentsListActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, 112);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudentList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Student student = (Student) studentList.getItemAtPosition(info.position);

        MenuItem callItem = menu.add("Call");
        callItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(StudentsListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StudentsListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 111);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + student.getTelephone()));
                    StudentsListActivity.this.startActivity(callIntent);
                }
                return false;
            }
        });

        MenuItem mapItem = menu.add("See on map");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(Uri.parse("geo:0,0?q="+student.getAddress()));
        mapItem.setIntent(mapIntent);

        MenuItem smsItem = menu.add("Send SMS");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("sms:"+student.getTelephone()));
        smsItem.setIntent(smsIntent);

        MenuItem siteItem = menu.add("Visit site");
        Intent siteIntent = new Intent(Intent.ACTION_VIEW);
        siteIntent.setData(Uri.parse(siteWithHttp(student.getSite())));
        siteItem.setIntent(siteIntent);


        MenuItem removeItem = menu.add("Remove");
        removeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
                studentDAO.remove(student);
                studentDAO.close();

                StudentsListActivity.this.loadStudentList();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_send:
                new SendStudentsTask(this).execute();
                break;
            case R.id.menu_list_exams:
                Intent goToExams = new Intent(this, ExamActivity.class);
                startActivity(goToExams);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String siteWithHttp(String url) {
        if (!url.startsWith("http://"))
            return "http://" + url;
        return url;
    }

    private void loadStudentList() {
        StudentDAO studentDAO = new StudentDAO(this);
        List<Student> students = studentDAO.getAllStudents();
        studentDAO.close();
        StudentsAdapter adapter = new StudentsAdapter(this, students);

        studentList.setAdapter(adapter);
    }
}
