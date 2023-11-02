package org.example;

@Table(title = "Kangaroo")
public class Kangaroo {
    @Column
    private String name;

    @Column
    private int age;

    @Column
    private int maxRunDist;

    @Column
    private int maxSwimDist;

    @Column
    private String diet;

    @Column
    private Type type;

    public Kangaroo(String name, int age, int maxRunDist, int maxSwimDist, String diet, Type type) {
        this.name = name;
        this.age = age;
        this.maxRunDist = maxRunDist;
        this.maxSwimDist = maxSwimDist;
        this.diet = diet;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMaxRunDist() {
        return maxRunDist;
    }

    public void setMaxRunDist(int maxRunDist) {
        this.maxRunDist = maxRunDist;
    }

    public int getMaxSwimDist() {
        return maxSwimDist;
    }

    public void setMaxSwimDist(int maxSwimDist) {
        this.maxSwimDist = maxSwimDist;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

}
