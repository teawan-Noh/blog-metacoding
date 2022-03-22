package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired
	private UserRepository userRepository;

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { //Exception 으로 걸어도 되지만 다른 Exception이 걸렸을 수도 있으니 정확하게 하기위해 정확한거 걸어줌
			return "삭제에 실패하였습니다. 없는 ID입니다.";
		}
		return "삭제되었습니다. id:" + id;
	}
	
	// save함수는 id를 전달하지 않으면 insert를 해주고
	// save함수는 id를 전달하면 db에 해당 id 데이터가 있으면 update를 해주고 데이터가 없으면 insert를 해요
	// but, 우리는 save함수를 쓰지않고 @Transactional 어노테이션을 사용할겁니다.
	// email, password
	
	@Transactional //함수 종료시에 자동 commit이 됨
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 요청 -> Java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아줘요)
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);  //@Transactional 어노테이션을 붙이지 않았을 경우
		
		//@Transactional 어노테이션을 붙였을 경우
		//더티 체킹을 이용한 업데이트 : 함수 종료시에 영속화 시킨 데이터와 함수안에 불려진 데이터가 다를경우 Commit 하면서 변경감지 -> DB에 수정
		return user;
	}
	
	//전체 리스트 받기
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll(); 
	}
	
	// 페이징 : 한페이지당 2건의 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable);
		
		if(pagingUsers.isFirst()) {
			
		}
		List<User> users = pagingUsers.getContent();
	
		return users;
	}
	
	//{id} 주소로 파라미터를 전달 받을 수 있음.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4 을 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐?
		// 그럼 리턴할 때 null 이 리턴이 되자나 그럼 프로그램에 문제가 있지 않겠니?
		// 그러므로 Optional로 너의 User 객체를 감싸서 가져올테니 null인지 아난지 판단해서 return 해!
		
//		람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//		return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
//		});
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("없는 ID 입니다");
			}
		});
		//요청: 웹브라우저
		//user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> Json 
		// 스프링부트는 MessageConverter가 응답시에 자동 작동
		// 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에 던져줍니다.
		return user;
	}
	
	
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		
		user.setRole(RoleType.USER);
		userRepository.save(user);  
		return "회원가입이 완료되었습니다.";
	}
}
