package com.example.Task.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private String itemName;

    private Integer quantity;

    @CreationTimestamp
    private Date addedOn;

    @UpdateTimestamp
    private Date updatedOn;

    private String username;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"shoppingLists"})
    private Users my_user;

    public ShoppingList(int i, String name, int quantity) {
        this.itemId=i;
        this.itemName=name;
        this.quantity=quantity;
    }
}
