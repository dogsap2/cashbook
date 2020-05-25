package com.gdu.ditestweb.cashbook1.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.ditestweb.cashbook1.mapper.MemberMapper;
import com.gdu.ditestweb.cashbook1.mapper.MemberidMapper;
import com.gdu.ditestweb.cashbook1.vo.LoginMember;
import com.gdu.ditestweb.cashbook1.vo.Member;
import com.gdu.ditestweb.cashbook1.vo.MemberForm;
import com.gdu.ditestweb.cashbook1.vo.Memberid;

@Service
@Transactional // 클래스에 붙어있을시 클래스안의 메소드를 실행시키다 하나라도 에러가나면 실행취소. (메소드안에 붙어있으면 이 메소드 한정으로 트랜잭션 에러시
				// 롤백)
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	@Autowired
	private JavaMailSender javaMailSender; // @Component와 아이F들....
	@Value("D:\\git-cashbook\\cashbook\\src\\main\\resources\\static\\upload\\")
	private String path;

	//비밀번호 찾기
	public int getMemberPw(Member member) {
		// 임시 pw추가
		UUID uuid = UUID.randomUUID();
		// 랜덤 문자열을 생성한뒤 0부터 8자리 까지 컷해서 memeberPw에 넣어주세요.
		String memberPw = uuid.toString().substring(0, 8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);

		if (row == 1) {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("tiger@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호는 " + memberPw + " 입니다.");
			javaMailSender.send(simpleMailMessage);
			// 메일로 update를 성공한 랜덤 pw를 전송
			// 메일을 보내는 메일객체가 필요 new JavaMailSender();
		}
		return row;
	}
	
	//아이디 잃어버렸을때
	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	

	// 회원 탈퇴
	public int removeMember(LoginMember loginMember) {

		// 1.멤버 이미지 파일 삭제
		// 1_1 파일 이름 select member_pic from member
		String memberPic = memberMapper.selectMemberPic(loginMember.getMemberId());
		// 1_2 파일 삭제
		File file = new File(path + memberPic);
		if (file.exists()) { // 파일이 있으면 삭제해주세요.
			file.delete();
		}
		// 1..멤버아이디 하나 추가
		Memberid memberid = new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		System.out.println(memberid + "<=====memberId");
		int row = memberMapper.deleteMemberOne(loginMember);

		int row1 = 0;
		if (row == 1) {
			row1 = memberidMapper.insertMemberid(memberid);
		}
		return row1;
	}
	
	
	//수정(파일x)
	public int modifyNoPicMember(MemberForm memberForm) {
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		System.out.println(member + "<---memberService.addMember:member");
		
		int row = memberMapper.updatenopicMember(member);
		return row;
	}

	// 수정
	public int modifyMember(MemberForm memberForm) {
		
		MultipartFile mf = memberForm.getMemberPic();
		String originName = mf.getOriginalFilename();
		System.out.println(originName);

		int lastDot = originName.lastIndexOf("."); // 오리진 파일에서 .을 찾아주세요 좌석표.png
		String extension = originName.substring(lastDot); // 점앞에 다자르기 //원래 이름에서(오리진네임) -> 확장자 구현.->
		// 새로운 아름을 생성 : UUID
		String memberPic = memberForm.getMemberId() + extension;
		// 1.db에서 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		System.out.println(member + "<---memberService.addMember:member");
		
		int row = memberMapper.updateMember(member);
		File file = new File(path + memberPic);
		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			// Exception
			// 1. 예외처리를 해야만 문법적으로 이상없는 예외
			// 2. 예외처리를 코드에서 구현하지 않아도 아무문제없는 예외 runtimeException
		}
		return row;
	}
	
	//로그인한 회원의 상세정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}

	//중복확인
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck); // 둘 중에 하나가 리턴됨 결과물 있으면 멤버아이디, 없으면 null
	}
	
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}

	// 회원가입
	// 파일을 넘기기위해서느는 enctype 설정, 그리고 파일이 넘어왔기떄문에 파일을 받아야되기때문에 컨트롤러에서 멀티파트파일을 받아야해서
	// vo에 맴버폼 만들어서 넘김
	public int addMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		// 확장자 필요
		String originName = mf.getOriginalFilename();
		System.out.println(originName);

		int lastDot = originName.lastIndexOf("."); // 오리진 파일에서 .을 찾아주세요 좌석표.png
		String extension = originName.substring(lastDot); // 점앞에 다자르기 //원래 이름에서(오리진네임) -> 확장자 구현.->

		// 새로운 아름을 생성 : UUID
		String memberPic = memberForm.getMemberId() + extension;
		// 1.db에서 저장
		Member member = new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberPic(memberPic);
		System.out.println(member + "<---memberService.addMember:member");
		int row = memberMapper.insertMember(member);

		// 2.파일 저장 //윈도우경로 \ 슬러시 리눅스 / 역슬러시...스프링 안에서 자동으로 바꿔주긴하지만
		String path ="C:\\Users\\tigersvadeva\\git\\cashbook\\cashbook\\src\\main\\resources\\static\\upload\\";
		File file = new File(path + memberPic);//새로운 파일 생성
		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
			// Exception
			// 1. 예외처리를 해야만 문법적으로 이상없는 예외
			// 2. 예외처리를 코드에서 구현하지 않아도 아무문제없는 예외 runtimeException
		}

		return row;
	}
}
