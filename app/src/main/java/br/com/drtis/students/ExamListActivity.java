package br.com.drtis.students;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.drtis.students.model.Exam;

public class ExamListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.main_frame, new ExamListFragment());

        if (isOnLandscapeMode())
            tx.replace(R.id.secondary_frame, new ExamDetailsFragment());

        tx.commit();
    }

    private boolean isOnLandscapeMode() {
        return getResources().getBoolean(R.bool.landscapeMode);
    }

    public void selectExam(Exam exam) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (!isOnLandscapeMode()) {
            FragmentTransaction tx = fragmentManager.beginTransaction();
            ExamDetailsFragment detailsFragment = new ExamDetailsFragment();

            Bundle args = new Bundle();
            args.putSerializable("exam", exam);
            detailsFragment.setArguments(args);

            tx.replace(R.id.main_frame, detailsFragment);
            tx.addToBackStack(null);

            tx.commit();
        } else {
            ExamDetailsFragment examDetailsFragment =
                    (ExamDetailsFragment) fragmentManager.findFragmentById(R.id.secondary_frame);
            examDetailsFragment.fillFieldsWith(exam);

        }
    }
}
