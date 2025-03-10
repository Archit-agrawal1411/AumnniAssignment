package org.aumni.core;

import javax.persistence.*;
@NamedQueries({
        @NamedQuery(
                name = "org.aumni.core.Employee.findAll",
                query = "SELECT e FROM Employee e"
        )
})
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "mobileNumber", length = 10, nullable = false)
    private String mobileNumber;

    public Employee() {
    }

    public Employee(String name, String city, String mobileNumber) {
        this.name = name;
        this.city = city;
        this.mobileNumber = mobileNumber;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}


