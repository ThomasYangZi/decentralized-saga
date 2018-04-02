package com.kiviblog.saga.domain.menu;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangzifeng
 */
@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 40)
    private String id;

    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @Column(name = "company", length = 40, nullable = false)
    private String company;

    @Column(name = "user", length = 40, nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private MenuEntity parent;

    @OneToMany(targetEntity = MenuEntity.class, mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<MenuEntity> child = new ArrayList<>(0);

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MenuEntity getParent() {
        return parent;
    }

    public void setParent(MenuEntity parent) {
        this.parent = parent;
    }

    public List<MenuEntity> getChild() {
        return child;
    }

    public void setChild(List<MenuEntity> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", username='" + username + '\'' +
                ", parent=" + parent +
                ", child=" + child.size() +
                '}';
    }
}

