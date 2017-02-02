package br.com.drtis.students.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.drtis.students.model.Student;

/**
 * Created by webmaster on 30/01/17.
 */

public class StudentDAO extends SQLiteOpenHelper {

    public StudentDAO(Context context) {
        super(context, "test", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE student (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "address TEXT, " +
                "telephone TEXT, " +
                "site TEXT, " +
                "grade REAL, " +
                "photoPath TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE test;");
//        switch (oldVersion) {
//            case 1:
//            case 2:
//                String sql = "ALTER TABLE student ADD COLUMN photoPath TEXT";
//                db.execSQL(sql);
//        }
    }

    public void insert(Student student) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = getContentValuesFromStudent(student);

        db.insert("student", null, contentValues);
    }

    public void remove(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {student.getId().toString()};
        db.delete("student","id = ?", params);
    }

    public  void update(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = getContentValuesFromStudent(student);

        String[] params = {student.getId().toString()};
        db.update("student", contentValues, "id = ?", params);
    }

    public List<Student> getAllStudents() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM student";
        Cursor cursor = db.rawQuery(sql, null);

        List<Student> students = new ArrayList<>();

        while(cursor.moveToNext()) {
            Student student = new Student();

            student.setId(cursor.getLong(cursor.getColumnIndex("id")));
            student.setName(cursor.getString(cursor.getColumnIndex("name")));
            student.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            student.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
            student.setSite(cursor.getString(cursor.getColumnIndex("site")));
            student.setGrade(cursor.getDouble(cursor.getColumnIndex("grade")));
            student.setPhotoPath(cursor.getString(cursor.getColumnIndex("photoPath")));
            students.add(student);
        }
        cursor.close();
        return students;
    }

    public boolean isStudent(String phoneNumber) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student WHERE telephone = ?", new String[]{phoneNumber});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    @NonNull
    private ContentValues getContentValuesFromStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        contentValues.put("telephone", student.getTelephone());
        contentValues.put("site", student.getSite());
        contentValues.put("grade", student.getGrade());
        contentValues.put("photoPath", student.getPhotoPath());

        return contentValues;
    }
}
