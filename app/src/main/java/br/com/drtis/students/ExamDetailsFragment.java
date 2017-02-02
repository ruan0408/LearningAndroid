package br.com.drtis.students;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.drtis.students.model.Exam;


public class ExamDetailsFragment extends Fragment {

    private ListView topicsView;
    private TextView subjectView;
    private TextView dateView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_details, container, false);

        subjectView = (TextView) view.findViewById(R.id.exam_details_subject);
        dateView = (TextView) view.findViewById(R.id.exam_details_date);
        topicsView = (ListView) view.findViewById(R.id.exam_details_topics);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Exam exam = (Exam) bundle.getSerializable("exam");
            fillFieldsWith(exam);
        }

        return view;
    }

    public void fillFieldsWith(Exam exam) {
        subjectView.setText(exam.getSubject());
        dateView.setText(exam.getDate());

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, exam.getTopics());

        topicsView.setAdapter(adapter);
    }

}
