package com.item.itemshop.repository;

import com.item.itemshop.domain.member.Member;
import com.item.itemshop.domain.member.QMember;
import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
      em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return new JPAQueryFactory(em)
                .selectFrom(QMember.member)
                .fetch();
    }

    public List<Member> findByName(String name) {
        return new JPAQueryFactory(em)
                .selectFrom(QMember.member)
                .where(QMember.member.name.eq(name))
                .fetch();
    }
    public List<Member> findByIdname(String idname) {
        return new JPAQueryFactory(em)
                .selectFrom(QMember.member)
                .where(QMember.member.idname.eq(idname))
                .fetch();
    }
}
