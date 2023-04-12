package com.item.itemshop.repository;

import com.item.itemshop.domain.item.Item;
import com.item.itemshop.domain.item.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;
   JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class,id);
    }

    public List<Item> findAll() {
        return queryFactory.selectFrom(QItem.item)
                .fetch();
    }
}
