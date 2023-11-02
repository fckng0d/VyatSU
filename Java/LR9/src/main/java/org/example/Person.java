package org.example;

@Table(title = "People")
public class Person {
    private static int autoincrementId = 1;

    @Column
    private final int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private int age;

    @Column
    private String post;

    public Person(String firstName, String lastName, int age, Post post) {
        this.id = autoincrementId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.post = post.getPost();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
