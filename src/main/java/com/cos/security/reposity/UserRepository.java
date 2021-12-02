package com.cos.security.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security.model.User;

//CRUE 함수를 JPAReposity가 들고 있음
//@Repository라는 어노테이션이 없어도 IOC가 
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
