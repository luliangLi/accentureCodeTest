package com.accenture.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.test.domain.AccentureUser;

@Repository
public interface UserH2Repository extends JpaRepository<AccentureUser, Long> {
	Optional<AccentureUser> findById(Long id);
}
