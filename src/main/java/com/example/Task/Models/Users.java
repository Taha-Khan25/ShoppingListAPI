package com.example.Task.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private Integer age;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "my_user")
    @JsonIgnoreProperties({"my_user"})
    private List<ShoppingList> shoppingLists;

    @OneToOne
    @JsonIgnoreProperties({"users"})
    @JoinColumn
    private SecuredUser securedUser;

}
