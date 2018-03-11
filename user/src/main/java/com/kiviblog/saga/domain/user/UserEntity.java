package com.kiviblog.saga.domain.user;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author yangzifeng
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 40)
    private String id;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "company", length = 40, nullable = false)
    private String company;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}
