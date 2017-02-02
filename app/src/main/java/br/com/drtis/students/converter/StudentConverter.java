package br.com.drtis.students.converter;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.drtis.students.model.Student;

/**
 * Created by webmaster on 01/02/17.
 */

public class StudentConverter {

    public String toJSON(List<Student> students) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("students").array();
            for (Student student : students) {
                js.object();
                js.key("name").value(student.getName());
                js.key("grade").value(student.getGrade());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
}
