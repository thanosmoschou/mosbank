package com.thanos.mosbank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanos.mosbank.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

}
