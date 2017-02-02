package br.com.drtis.students;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import br.com.drtis.students.model.Exam;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Exam englishExam = new Exam("English", "01/01/2017", Arrays.asList("To be", "Past perfect", "Nouns"));
        Exam mathExam = new Exam("Mathematics", "01/01/2017", Arrays.asList("Trigonometry", "Complex Hyperboles", "Non-euclidean geometry"));

        ArrayAdapter<Exam> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(englishExam, mathExam));
        ListView examList = (ListView) findViewById(R.id.list_exam);

        examList.setAdapter(adapter);

        examList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam exam = (Exam) parent.getItemAtPosition(position);
                Toast.makeText(ExamActivity.this, "clicado " + exam, Toast.LENGTH_SHORT).show();
                Intent goToExamDetails = new Intent(ExamActivity.this, ExamDetailsActivity.class);
                goToExamDetails.putExtra("exam", exam);
                startActivity(goToExamDetails);
            }
        });
    }
}
