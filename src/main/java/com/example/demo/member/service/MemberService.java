package com.example.demo.member.service;

import com.example.demo.member.domain.Member;
import com.example.demo.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public void create(Member member) throws SQLException {
        memberRepository.save(member);
    }

    public List<Member> findAll() {
        List<Member> members = memberRepository.findAll();
        return members;
    }

}
