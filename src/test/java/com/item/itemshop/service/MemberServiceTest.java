package com.item.itemshop.service;

import com.item.itemshop.domain.member.Member;
import com.item.itemshop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void join() throws Exception{
        // Given(준비)
        Member member = new Member();
        member.setName("DongDong");
        // When(실행)
        Long saveId = memberService.join(member);
        // Then(검증)
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void validateDuplicateMember() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("DongDong");

        Member member2 = new Member();
        member2.setName("DongDong");
        // when
        memberService.join(member1);
        // then
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}
