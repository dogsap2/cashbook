package com.gdu.ditestweb.cashbook1.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.ditestweb.cashbook1.mapper.MemberMapper;
import com.gdu.ditestweb.cashbook1.mapper.MemberidMapper;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;
import com.gdu.ditestweb.cashbook1.vo.Memberid;

@Service 
@Transactional //클래스에 붙어있을시 클래스안의 메소드를 실행시키다 하나라도 에러가나면 실행취소. (메소드안에 붙어있으면 이 메소드 한정으로 트랜잭션 에러시 롤백)
public class MemberService {
	@Autowired 
	private MemberMapper memberMapper;
	@Autowired 
	private MemberidMapper memberidMapper;
	@Autowired
	private JavaMailSender javaMailSender; //@Component와 아이들....
	
	
	public int getMemberPw(Member member) {
		//임시 pw추가
		UUID uuid = UUID.randomUUID();
		//랜덤 문자열을 생성한뒤 0부터 8자리 까지 컷해서 memeberPw에 넣어주세요.
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		
		if(row == 1) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("tiger@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호는 "+memberPw+" 입니다.");
			javaMailSender.send(simpleMailMessage);	
			//메일로 update를 성공한 랜덤 pw를 전송
			//메일을 보내는 메일객체가 필요 new JavaMailSender();
		}
		return row;
	}
	
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	
	//삭제
	public void removeMember(LoginMember loginMember) {
	  //1..멤버아이디 하나 추가
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
      //2. 삭제  예외발생시 전부롤백
		memberMapper.deleteMemberOne(loginMember);
	}
	
	public int modifyMember(Member member) {
		return memberMapper.updateMember(member);
	}
	
	//public int removeMember(LoginMember loginMember) {
		//return memberMapper.deleteMemberOne(loginMember);
	//}
	
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); //둘 중에 하나가 리턴됨 결과물 있으면 멤버아이디, 없으면 null
	}
	
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	public int addMember(Member member) {
		return memberMapper.insertMember(member);	
	}
}
