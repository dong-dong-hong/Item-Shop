package com.item.itemshop.service;

import com.item.itemshop.domain.item.Book;
import com.item.itemshop.domain.item.Clean;
import com.item.itemshop.domain.item.Item;
import com.item.itemshop.domain.member.Member;
import com.item.itemshop.domain.order.Order;
import com.item.itemshop.domain.order.OrderStatus;
import com.item.itemshop.exception.NotEnoughStockException;
import com.item.itemshop.repository.OrderRepository;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


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

        //When
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Long orderId2 = orderService.order(member.getId(), item2.getId(), orderCount2);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        Order getOrder2 = orderRepository.findOne(orderId2);

       assertEquals(OrderStatus.ORDER, getOrder.getOrderStatus()); // 상품 주문 시 상태는 ORDER
       assertEquals(1, getOrder.getOrderItems().size()); // 주문한 상품 종류 수가 정확
       assertEquals(50000 * 100, getOrder.getTotalPrice()); // 주문 가격은 가격 * 수량
       assertEquals(100, item.getStockQuantity()); // 주문 수량만큼 재고가 다운

       assertEquals(OrderStatus.ORDER, getOrder2.getOrderStatus());
       assertEquals(1, getOrder2.getOrderItems().size());
       assertEquals(150000 * 30, getOrder2.getTotalPrice());
       assertEquals(70, item2.getStockQuantity());

        System.out.println("getOrder = " + getOrder.getTotalPrice());
        System.out.println("getOrder2 = " + getOrder2.getTotalPrice());
    }

    @Test
    @DisplayName("상품주문 재고 수량 초과")
    public void productOrderInventoryQuantityExceeded() throws Exception {
        // given
        Member member = createMember();

        Book book= createBook("백엔드 개발자", 20000,50);

        int orderCount = 100; // 재고보다 많은 수량
        // when
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
        Book item = createBook("DongDongJPA", 50000, 200);

        int orderCount = 100;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);
        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getOrderStatus()); // 주문 취소 시 상태는 CANCEL
        assertEquals(200, item.getStockQuantity()); // 주문이 취소된 상품은 그만큼 재고가 증가
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
}