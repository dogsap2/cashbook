package com.gdu.ditestweb.cashbook1;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@PropertySource("classpath:google.properties")
public class CashbookApplication {// 프로퍼티즈 파일
	@Value("${google.username}")
	private String username;
	@Value("${google.password}")
	private String password;
//컨피규레이트 기능 스프링에 이런이런이런 거 만들어주세요
//@SpringBootApplication == @Configuration + @EnableAutoConfiguration + @ComponentScan

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

	//자바 시작할때 객체필요해서 빈태그를 이용해서 mailSender객체를 만들고 속성기입함
	//캐쉬북 애플리케이션을 실행되면 제일먼저 실행
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com"); //이 메일서버에 접속해서
		javaMailSender.setPort(587);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
		
		Properties prop = new Properties(); // Properties == HashMap<String,String>
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender; 
	}
}
