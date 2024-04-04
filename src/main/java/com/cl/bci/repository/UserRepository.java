package com.cl.bci.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cl.bci.model.User;

/**
 * The Interface UserRepository.
 */
@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT COUNT(*) =1 FROM USER WHERE EMAIL=:email", nativeQuery = true)
	boolean findByEmail(String email);

	@Query(value = "SELECT UUID, CREATED, EMAIL, LAST_LOGIN, MODIFIED, NAME, PASSWORD FROM USER WHERE EMAIL=:email", nativeQuery = true)
	User UserByEmail(String email);

	@Query(value = "SELECT COUNT(*) =1 FROM USER WHERE EMAIL=:email AND PASSWORD=:password", nativeQuery = true)
	boolean LoginByUser(String email, String password);

	@Modifying
	@Query(value = "UPDATE USER SET LAST_LOGIN=:fecha WHERE UUID=:id", nativeQuery = true)
	void updateLastLogin(String fecha, Long id);
}
