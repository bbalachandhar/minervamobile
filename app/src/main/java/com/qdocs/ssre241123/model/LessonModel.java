package com.qdocs.ssre241123.model;

public class LessonModel {

    private String lessonTitle;

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    private String quizTitle;

    public String getAssignment_title() {
        return assignment_title;
    }

    public void setAssignment_title(String assignment_title) {
        this.assignment_title = assignment_title;
    }

    public String getCourse_assignment_id() {
        return course_assignment_id;
    }

    public void setCourse_assignment_id(String course_assignment_id) {
        this.course_assignment_id = course_assignment_id;
    }

    public String getCourse_exam_name() {
        return course_exam_name;
    }

    public void setCourse_exam_name(String course_exam_name) {
        this.course_exam_name = course_exam_name;
    }

    public String getCourse_exam_id() {
        return course_exam_id;
    }

    public void setCourse_exam_id(String course_exam_id) {
        this.course_exam_id = course_exam_id;
    }

    private String assignment_title;
    private String course_assignment_id;
    private String course_exam_name;
    private String course_exam_id;

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    private String quiz_id;
    private String duration;

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }
}
