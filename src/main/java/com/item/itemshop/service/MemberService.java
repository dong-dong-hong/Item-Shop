package com.item.itemshop.service;

import com.item.itemshop.domain.member.Member;
import com.item.itemshop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional(readOnly = false)
    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByName(member.getName());
        if(!findMember.isEmpty()) {
            throw new IllegalStateException("존재하는 회원입니다.");
        }
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

//    public Member update(String idname, String pw, String name,String address, String addressDetail) {
//        Member member = new Member();
//        member.setIdname(idname);
//        member.setPw(pw);
//        member.setName(name);
//        member.setAddress(address);
//        member.setAddressDetail(addressDetail);
//        return member;
//    }
    public Member login(String idname, String pw) {
        List<Member> findMember = memberRepository.findByIdname(idname);
        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 아이디입니다.");
        }
        Member member = findMember.get(0);
        if (!member.getPw().equals(pw)) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return member;
    }
}
