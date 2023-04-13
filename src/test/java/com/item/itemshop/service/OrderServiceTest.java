package com.item.itemshop.service;

import com.item.itemshop.domain.item.*;
import com.item.itemshop.domain.member.Member;
import com.item.itemshop.domain.member.QMember;
import com.item.itemshop.domain.order.Order;
import com.item.itemshop.domain.order.OrderSearch;
import com.item.itemshop.domain.order.OrderStatus;
import com.item.itemshop.domain.order.QOrder;
import com.item.itemshop.exception.NotEnoughStockException;
import com.item.itemshop.repository.OrderRepository;
import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("상품주문")
    public void itemOrder() throws IllegalStateException {
        //Given
        Member member = createMember();

        Item item = createBook("DongDongJPA", 50000, 200); //이름, 가격, 재고
        int orderCount = 100;

        Item item2 = createClean("로봇청소기", 150000,100);
        int orderCount2 = 30;

        Item item3 = createClean("야구공", 10000,1000);
        int orderCount3 = 100;

        Item item4 = createClean("60계치킨", 20000,60);
        int orderCount4 = 10;

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Long orderId2 = orderService.order(member.getId(), item2.getId(), orderCount2);
        Long orderId3 = orderService.order(member.getId(), item3.getId(), orderCount3);
        Long orderId4 = orderService.order(member.getId(), item4.getId(), orderCount4);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        Order getOrder2 = orderRepository.findOne(orderId2);
        Order getOrder3 = orderRepository.findOne(orderId3);
        Order getOrder4 = orderRepository.findOne(orderId4);

       assertEquals(OrderStatus.ORDER, getOrder.getOrderStatus()); // 상품 주문 시 상태는 ORDER
       assertEquals(1, getOrder.getOrderItems().size()); // 주문한 상품 종류 수가 정확
       assertEquals(50000 * 100, getOrder.getTotalPrice()); // 주문 가격은 가격 * 수량
       assertEquals(100, item.getStockQuantity()); // 주문 수량만큼 재고가 다운

       assertEquals(OrderStatus.ORDER, getOrder2.getOrderStatus());
       assertEquals(1, getOrder2.getOrderItems().size());
       assertEquals(150000 * 30, getOrder2.getTotalPrice());
       assertEquals(70, item2.getStockQuantity());

        assertEquals(OrderStatus.ORDER, getOrder3.getOrderStatus());
        assertEquals(1, getOrder3.getOrderItems().size());
        assertEquals(10000 * 100, getOrder3.getTotalPrice());
        assertEquals(900, item3.getStockQuantity());

        assertEquals(OrderStatus.ORDER, getOrder4.getOrderStatus());
        assertEquals(1, getOrder4.getOrderItems().size());
        assertEquals(20000 * 10, getOrder4.getTotalPrice());
        assertEquals(50, item4.getStockQuantity());
    }

    @Test
    @DisplayName("상품주문 재고 수량 초과")
    public void productOrderInventoryQuantityExceeded() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("백엔드 개발자", 20000,50);
        int orderCount = 100; // 재고보다 많은 수량
        // then
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), book.getId(), orderCount);
        }, "재고 수량 예외가 발생해야 한다.");
    }

    @Test
    @DisplayName("주문취소")
    public void orderCancel() throws Exception {
        // given
        Member member = createMember();

        Book book = createBook("DongDongJPA", 50000, 200);
        int orderCount = 100;
        Clean clean = createClean("물걸레 청소기", 10000, 100);
        int orderCount2 = 50;
        Sport sport = createSport("야구방망이", 200000, 200);
        int orderCount3 = 100;
        Food food = createFood("59쌀피자", 5900, 59);
        int orderCount4 = 9;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Long orderId2 = orderService.order(member.getId(), clean.getId(), orderCount2);
        Long orderId3 = orderService.order(member.getId(), sport.getId(), orderCount3);
        Long orderId4 = orderService.order(member.getId(), food.getId(), orderCount4);

        // when
        orderService.cancelOrder(orderId);
        orderService.cancelOrder(orderId2);
        orderService.cancelOrder(orderId3);
        orderService.cancelOrder(orderId4);
        // then
        Order getOrder = orderRepository.findOne(orderId);
        Order getOrder2 = orderRepository.findOne(orderId2);
        Order getOrder3 = orderRepository.findOne(orderId3);
        Order getOrder4 = orderRepository.findOne(orderId4);

        assertEquals(OrderStatus.CANCEL, getOrder.getOrderStatus()); // 주문 취소 시 상태는 CANCEL
        assertEquals(200, book.getStockQuantity()); // 주문이 취소된 상품은 그만큼 재고가 증가

        assertEquals(OrderStatus.CANCEL, getOrder2.getOrderStatus());
        assertEquals(100, clean.getStockQuantity());

        assertEquals(OrderStatus.CANCEL, getOrder3.getOrderStatus());
        assertEquals(200, sport.getStockQuantity());

        assertEquals(OrderStatus.CANCEL, getOrder4.getOrderStatus());
        assertEquals(59, food.getStockQuantity());
    }


    @Test
    @Transactional
    public void testFindAll() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("DongDong");
        member1.setAddress("서울시 마포구");
        member1.setAddressDetail("공덕동 123-456");
        em.persist(member1);
        em.flush();

        Member findMember = em.find(Member.class, 1L);

        Order order1 = new Order();
        order1.setMember(findMember);
        order1.setOrderDate(LocalDateTime.now());
        order1.setOrderStatus(OrderStatus.ORDER);
        em.persist(order1);
        em.flush();

        OrderSearch orderSearch = new OrderSearch();
        orderSearch.setMemberName("DongDong");
        orderSearch.setOrderStatus(OrderStatus.ORDER);

        // when
        List<Order> orders = orderRepository.findAll(orderSearch);

        // then
        Assertions.assertThat(orders).hasSize(1);
        Assertions.assertThat(orders.get(0).getMember().getName()).isEqualTo(orderSearch.getMemberName());
        Assertions.assertThat(orders.get(0).getOrderStatus()).isEqualTo(OrderStatus.ORDER);
    }

    // queryDSL을 이용하려고 했으나 일단 보류..
    private BooleanBuilder buildOrderPredicate(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            builder.and(member.name.eq(orderSearch.getMemberName()));
        }

        if (orderSearch.getOrderStatus() != null) {
            builder.and(order.orderStatus.eq(orderSearch.getOrderStatus()));
        }

        return builder;
    }


    private Member createMember() {
        Member member = new Member();
        member.setName("DongDong");
        member.setAddress("서울시 마포구 공덕동 77-77");
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }
    private Clean createClean(String name, int price, int stockQuantity) {
        Clean clean = new Clean();
        clean.setName(name);
        clean.setStockQuantity(stockQuantity);
        clean.setPrice(price);
        em.persist(clean);
        return clean;
    }

    private Sport createSport(String name, int price, int stockQuantity) {
        Sport sport = new Sport();
        sport.setName(name);
        sport.setStockQuantity(stockQuantity);
        sport.setPrice(price);
        em.persist(sport);
        return sport;
    }

    private Food createFood(String name, int price, int stockQuantity) {
        Food food = new Food();
        food.setName(name);
        food.setStockQuantity(stockQuantity);
        food.setPrice(price);
        em.persist(food);
        return food;
    }
}