package com.item.itemshop.repository;

import com.item.itemshop.domain.item.Item;
import com.item.itemshop.domain.item.QItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;
   private JPAQueryFactory queryFactory;

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
        return new JPAQueryFactory(em)
                .selectFrom(QItem.item)
                .fetch();
    }
}
