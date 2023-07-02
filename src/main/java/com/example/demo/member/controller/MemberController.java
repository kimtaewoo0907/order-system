package com.example.demo.member.controller;

import com.example.demo.member.domain.Address;
import com.example.demo.member.domain.Member;
import com.example.demo.member.service.MemberService;
import com.example.demo.order_item.domain.OrderItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("members/new")
    public String memberCreateForm(Model model) {
        // createMemberForm에서 memberForm이라는 dto객체를 필요로 하므로, dto객체를 만들어서 model을 통해 전달
        // Dto에서 NotNull, NotEmpty등 validation처리를 할 수 있고,
        // 해당 객체를 createMemberForm화면에 전달함으로서 화면에서 validation체크를 할 수가 있다
        model.addAttribute("memberForm", new MemberRequestDto());
        return "members/createMemberForm";
    }

    @PostMapping("members/new")
    public String memberCreate(MemberRequestDto memberRequestDto) throws SQLException {
        Address address = new Address(memberRequestDto.getCity(), memberRequestDto.getStreet(), memberRequestDto.getZipcode());
        Member member1 = Member.builder()
                        .name(memberRequestDto.getName())
                        .email(memberRequestDto.getEmail())
                        .password(memberRequestDto.getPassword())
                        .address(address)
                        .build();
        memberService.create(member1);
        return "redirect:/members";
    }

    @GetMapping("members")
    public String memberFindAll(Model model) {
        List<Member> member = memberService.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member m1 : member) {
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setId(String.valueOf(m1.getId()));
            memberResponseDto.setName(m1.getName());
            memberResponseDto.setEmail(m1.getEmail());
            memberResponseDto.setCity(m1.getAddress().getCity());
            memberResponseDto.setStreet(m1.getAddress().getStreet());
            memberResponseDto.setZipcode(m1.getAddress().getZipcode());
            memberResponseDto.setOrderCount(m1.getOrders().size());
            memberResponseDtos.add(memberResponseDto);
        }
        model.addAttribute("members", memberResponseDtos);
        return "members/memberList";
    }
}


