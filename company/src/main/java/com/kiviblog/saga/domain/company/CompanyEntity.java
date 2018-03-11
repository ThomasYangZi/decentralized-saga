package com.kiviblog.saga.domain.company;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author yangzifeng
 */
@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 40)
    private String id;

    @Column(name = "name", length = 40, nullable = false)
    private String name;


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

    @Override
    public String toString() {
        return "CompanyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
