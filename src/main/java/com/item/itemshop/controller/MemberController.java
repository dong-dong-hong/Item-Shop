package com.item.itemshop.controller;

import com.item.itemshop.controller.form.LoginForm;
import com.item.itemshop.controller.form.MemberForm;
import com.item.itemshop.domain.member.Member;
import com.item.itemshop.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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



   @GetMapping(value = "members/{memberId}")
   public String deleteMember(@PathVariable("memberId") Long memberId) {
      memberService.deleteMember(memberId);
      return "redirect:/members";
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
   // 로그인 페이지로 이동
   @GetMapping("members/login")
   public String loginGet(Model model) {
      model.addAttribute("loginForm", new LoginForm());
      return "members/login";
   }
   // 로그인 처리
   @PostMapping("members/login")
   public String loginPost(@Valid @ModelAttribute LoginForm loginForm, HttpSession session, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
      Member member = memberService.login(loginForm.getIdname(), loginForm.getPw());
      if(bindingResult.hasErrors()) {
         return "members/login";
      }
      if(member != null) {
         session.setAttribute("loginForm",member);  // 로그인 정보를 세션에 저장
         return "redirect:/";
      }else{
         redirectAttributes.addFlashAttribute("message","아이디 또는 비밀번호가 일치하지 않습니다.");
         return "redirect:/login";
      }
   }
   // 로그아웃 처리
   @GetMapping("/logout")
   public String logout(HttpSession session) {
      session.removeAttribute("loginForm"); // 세션에서 로그인 정보 제거
      return "redirect:/";
   }
}
