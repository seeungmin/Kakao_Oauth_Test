package com.example.demo.service;

import com.example.demo.domain.MemberForm;
import com.example.demo.domain.Member;
import com.example.demo.repository.SpringDataJpaMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MemberService {

    private SpringDataJpaMemberRepository springDataJpaMemberRepository;


    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) {
        this.springDataJpaMemberRepository = springDataJpaMemberRepository;
    }


    /**
     *
     * 회원 가입
     */
    public Long join(Member member){

        long start = System.currentTimeMillis();

        try{
            //같은 이름이 있는 중복 회원 불가
            //Optional을 통해서 여러 메서드 사용 가능
            if (springDataJpaMemberRepository.findByUserId(member.getUserId()) != null)
                return member.getId();
            springDataJpaMemberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
          //  System.out.println("join = " + timeMs + "ms");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){

        long start = System.currentTimeMillis();

        try{
            return springDataJpaMemberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
          //  System.out.println("findMembers = " + timeMs + "ms");
        }
    }

    public Member login(MemberForm memberForm){
        Member byUserId = springDataJpaMemberRepository.findByUserId(memberForm.getUserId());
        if(memberForm.getUserPassword() != byUserId.getUserPassword()){
            return null;
        }

        return byUserId;
    }


}
