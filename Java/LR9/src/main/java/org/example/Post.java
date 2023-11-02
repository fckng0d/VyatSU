package org.example;

public enum Post {
    SCHOOL_CHILD("Школьник"), STUDENT("Студент"), WORKER("Рабочий"), RETIREE("Пенсионер");

    private String post;

    Post(String post) {
        this.post = post;
    }

    public String getPost() {
        return post;
    }
}
