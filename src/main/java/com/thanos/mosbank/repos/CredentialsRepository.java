package com.thanos.mosbank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanos.mosbank.model.Credentials;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, String> 
{

}
