package com.thanos.mosbank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanos.mosbank.model.Iban;

@Repository
public interface IbanRepository extends JpaRepository<Iban, String>
{

}