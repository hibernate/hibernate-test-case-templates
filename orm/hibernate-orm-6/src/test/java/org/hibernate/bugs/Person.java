package org.hibernate.bugs;

public class Person {   
    public Long id;
    public String name;
    public String email;
    public String phone;
    public String address;
    public String city;
    public String state;

    public Person() {
    }

    public Person(Long id, String name, String email, String phone, String address, String city, String state) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}