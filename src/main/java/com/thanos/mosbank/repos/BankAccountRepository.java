package com.thanos.mosbank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanos.mosbank.model.BankAccount;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer>
{

}
