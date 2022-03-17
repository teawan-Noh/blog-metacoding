package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자의 요청 -> 응답(HTML 파일) : @Controller

// 사용자의 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest";
	
	//application.yml에 경로 설정을 해줬으므로 /blog/http/lombok 으로 접속해야함
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = new Member(1, "ssas", "1234", "acb@gaac.com");
		System.out.println(TAG + "getter :" + m.getId());
		m.setId(5000);
		System.out.println(TAG + "setter :" + m.getId());
		
		return "lombok test 완료";
	}

	//인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다
	//hppt:/localhost:8080/http/get : (select)
	@GetMapping("/http/get")
	public String getTest(Member m) { //개별 변수를 요청하고 싶으면 @RequestParam을 사용 (ex: @RequestParam int id, String username) // MessageConverter(스프링부트)
		
		return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	//hppt:/localhost:8080/http/post : (insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트)가 자동으로 매핑해줌
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	//hppt:/localhost:8080/http/put : (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();


	}
	//hppt:/localhost:8080/http/delete : (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
