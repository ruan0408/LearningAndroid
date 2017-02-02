package br.com.drtis.students;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import br.com.drtis.students.model.Exam;

/**
 * Created by webmaster on 02/02/17.
 */

public class ExamListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_list, container, false);

        Exam englishExam = new Exam("English", "01/01/2017", Arrays.asList("To be", "Past perfect", "Nouns"));
        Exam mathExam = new Exam("Mathematics", "01/01/2017", Arrays.asList("Trigonometry", "Complex Hyperboles", "Non-euclidean geometry"));

        ArrayAdapter<Exam> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Arrays.asList(englishExam, mathExam));
        ListView examList = (ListView) view.findViewById(R.id.list_exam);

        examList.setAdapter(adapter);

        examList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam exam = (Exam) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "clicado " + exam, Toast.LENGTH_SHORT).show();

                ExamListActivity examListActivity = (ExamListActivity) getActivity();
                examListActivity.selectExam(exam);
            }
        });

        return view;
    }
}
