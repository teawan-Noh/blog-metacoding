package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //Getter ,Setter 합친거
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
//ORM -> Java(등의 언어) Object -> 테이블로 매핑해주는 기술  : object를 만들면 JPA가 테이블을 만들어 준다.
@Entity //User 클래스가 자동으로 MySQL에 테이블이 생성된다.
//@DynamicInsert // JPA에서 insert 할때 null인 필드 제외 시켜서 default 설정한 값으로 들어가게 함 // ColumnDefault를 세팅할 수도 있지만 이렇게 안쓰고 Enum 사용
public class User {

	@Id //Primarty key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; //시퀀스(Oracle), auto_increment(MySQL)
	
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	
	@Column(nullable = false, length = 100)  // 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	//DB에는 RoleType이라는게 없다 따라서 어노테이션 붙여줌
	@Enumerated(EnumType.STRING)
	private RoleType role; // Enum을 쓰는게 좋다. 데이터의 도메인을 만들어줄 수 있다. // admin, user, manager 중 하나만 들어가게 도메인 설정 가능 (Enum 사용시 장점)
	
	@CreationTimestamp //시간 자동 입력
	private Timestamp createDate;
}
