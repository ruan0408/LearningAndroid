package br.com.drtis.students;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.drtis.students.converter.StudentConverter;
import br.com.drtis.students.dao.StudentDAO;
import br.com.drtis.students.model.Student;

/**
 * Created by webmaster on 01/02/17.
 */

public class SendStudentsTask extends AsyncTask<Object, Object, String> {

    private Context context;
    private ProgressDialog dialog;

    public SendStudentsTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Loading", "Loading...", true, true);
    }

    @Override
    protected String doInBackground(Object... params) {
        StudentDAO dao = new StudentDAO(context);
        List<Student> students = dao.getAllStudents();
        dao.close();
        StudentConverter converter = new StudentConverter();
        String json = converter.toJSON(students);
        WebClient webClient = new WebClient();
        return webClient.post(json);
    }

    @Override
    protected void onPostExecute(String response) {
        dialog.dismiss();
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    }
}
