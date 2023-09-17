package com.example.Task.Dto;

import com.example.Task.Models.SecuredUser;
import com.example.Task.Models.ShoppingList;
import com.example.Task.Models.Users;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateListRequest {

    @NotBlank
    private String itemName;

    @Min(1)
    private Integer quantity;


    public ShoppingList listBuilder()
    {
        return ShoppingList.builder()
                .itemName(this.itemName)
                .quantity(this.quantity)
                .build();
    }
}
