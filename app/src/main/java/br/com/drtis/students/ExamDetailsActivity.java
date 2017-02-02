package br.com.drtis.students;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

import br.com.drtis.students.model.Exam;

public class ExamDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);
        Intent intent = getIntent();
        Exam exam = (Exam) intent.getSerializableExtra("exam");

        TextView examSubject = (TextView) findViewById(R.id.exam_details_subject);
        TextView examDate = (TextView) findViewById(R.id.exam_details_date);
        ListView examTopics = (ListView) findViewById(R.id.exam_details_topics);

        examSubject.setText(exam.getSubject());
        examDate.setText(exam.getDate());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exam.getTopics());
        examTopics.setAdapter(adapter);
    }
}
