package com.item.itemshop.domain.item;

import com.item.itemshop.domain.item.category.Category;
import com.item.itemshop.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter @ToString
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany
    private List<Category> categoryItems = new ArrayList<>();

    // 비즈니스 로직
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity; // 재고 증가 및 상품 취소 시에 적용
    }

    public void removeStock(int stockQuantity) {
        nonMinus(stockQuantity); // 재고가 음수일 경우
        int restStock = this.stockQuantity - stockQuantity;
        if(restStock < 0) { // 재고가 부족한 경우
            throw new NotEnoughStockException("더 많은 재고가 필요합니다.");
        }
        this.stockQuantity = restStock;
    }

    private void nonMinus(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("재고는 음수가 되어서는 안됩니다.");
        }
    }
}
