package com.item.itemshop.controller;

import com.item.itemshop.domain.member.Member;
import com.item.itemshop.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
   public String list(Model model, HttpServletResponse response) {
      List<Member> members = memberService.findMembers();
      model.addAttribute("members",members);
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      return "members/memberList";
   }




   @GetMapping("members/{memberId}/update")
   public String updateForm(@PathVariable("memberId") Long memberId, Model model) {
      Member member = memberService.findOne(memberId);

      MemberForm memberForm = new MemberForm();
      memberForm.setId(member.getId());
      memberForm.setName(member.getName());
      memberForm.setIdname(member.getIdname());
      memberForm.setPw(member.getPw());
      memberForm.setAddress(member.getAddress());
      memberForm.setAddressDetail(member.getAddressDetail());

      model.addAttribute("form", memberForm);
      return "members/updateMemberForm";
   }

   @PostMapping("members/{memberId}/update")
   public String update(@Valid @ModelAttribute("form") MemberForm memberForm, @PathVariable Long memberId) {
//      memberService.update(member.getIdname(), member.gememberFormtPw(), member.getName(), member.getAddress(), member.getAddressDetail());
      Member member = new Member();
      member.setId(memberId);
      member.setName(memberForm.getName());
      member.setIdname(memberForm.getIdname());
      member.setPw(memberForm.getPw());
      member.setAddress(memberForm.getAddress());
      member.setAddressDetail(memberForm.getAddressDetail());
      memberService.saveMember(member);
      return "redirect:/members";
   }
}
