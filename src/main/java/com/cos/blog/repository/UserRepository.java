package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

// DAO 
// 자동으로 Bean으로 등록이 된다.
// @Repository 생략 가능하다
// extens 안에 모든 함수가 다 들어있다.
public interface UserRepository extends JpaRepository<User, Integer> {

}
