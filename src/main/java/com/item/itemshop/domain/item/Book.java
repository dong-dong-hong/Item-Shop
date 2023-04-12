package com.item.itemshop.domain.item;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("Book")
@Getter @Setter
public class Book extends Item{

    private String author;
    private String isbn;
}
