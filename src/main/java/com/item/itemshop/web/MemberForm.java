package com.item.itemshop.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;
    @NotEmpty(message = "회원 아이디는 필수 입니다.")
    private String idname;
    @NotEmpty(message = "회원 비밀번호는 필수 입니다.")
    private String pw;
    private String address;
    private String addressDetail;
}
