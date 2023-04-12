package com.item.itemshop.domain.item.category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class CategoryItem {

    @Id @GeneratedValue
    @Column(name = "category_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CategoryItem parent;

    @OneToMany(mappedBy = "parent")
    private List<CategoryItem> child = new ArrayList<>();

    // 연관관계 메서드
    public void addChildCategoryItem(CategoryItem child) {
        this.child.add(child);
        child.setParent(this);
    }
}
