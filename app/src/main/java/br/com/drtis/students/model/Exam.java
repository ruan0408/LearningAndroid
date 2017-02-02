package br.com.drtis.students.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by webmaster on 01/02/17.
 */

public class Exam implements Serializable{

    private String subject;
    private String date;
    private List<String> topics;

    public Exam(String subject, String date, List<String> topics) {
        this.subject = subject;
        this.date = date;
        this.topics = topics;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return subject;
    }
}
