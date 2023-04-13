package com.item.itemshop.repository;

import com.item.itemshop.domain.member.QMember;
import com.item.itemshop.domain.order.Order;
import com.item.itemshop.domain.order.OrderSearch;
import com.item.itemshop.domain.order.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    private QueryFactory queryFactory;


    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

     public List<Order> findAll(OrderSearch orderSearch) {

         QOrder order = QOrder.order;
         QMember member = QMember.member;

         BooleanBuilder builder = new BooleanBuilder();

         // 주문 상태 검색
         if (orderSearch.getOrderStatus() != null) {
             builder.and(order.orderStatus.eq(orderSearch.getOrderStatus()));
         }

         // 회원 이름 검색
         if (StringUtils.hasText(orderSearch.getMemberName())) {
             builder.and(member.name.like("%" + orderSearch.getMemberName() + "%"));
         }
         return new JPAQueryFactory(em)
                 .selectFrom(order)
                 .leftJoin(order.member, member)
                 .where(builder)
                 .limit(1000)
                 .fetch();
     }
}

