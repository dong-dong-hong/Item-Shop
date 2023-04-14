package com.item.itemshop.controller;

import com.item.itemshop.domain.member.Member;
import com.item.itemshop.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

   private final MemberService memberService;

   @GetMapping( "/members/join")
    public String createForm(Model model) {
       model.addAttribute("memberForm", new MemberForm());
       return "members/createMemberForm";
   }

   @PostMapping("/members/join")
   public String create(@Valid MemberForm memberForm,BindingResult bindingResult) {

      if(bindingResult.hasErrors()) {
         return "members/createMemberForm";
      }
      Member member = new Member();
      member.setName(memberForm.getName());
      member.setIdname(memberForm.getIdname());
      member.setPw(memberForm.getPw());
      member.setAddress(memberForm.getAddress());
      member.setAddressDetail(memberForm.getAddressDetail());
      memberService.join(member);
      return "redirect:/";
   }

   @GetMapping("/members")
   public String list(Model model) {
      List<Member> members = memberService.findMembers();
      model.addAttribute("members",members);
      return "members/memberList";
   }

   @GetMapping("members/{memberId}/update")
   public String updateForm(@PathVariable("memberId") Long memberId, Model model) {
      model.addAttribute("memberForm",memberService.findOne(memberId));
      return "members/updateMemberForm";
   }

   @PostMapping("members/{memberId}/update")
   public String update(@ModelAttribute("memberForm") MemberForm memberForm, @PathVariable("memberId") Long memberId) {
      memberService.update(memberId, memberForm.getIdname(), memberForm.getPw(), memberForm.getName(), memberForm.getAddress(), memberForm.getAddressDetail());
      return "redirect:/members";
   }
}
