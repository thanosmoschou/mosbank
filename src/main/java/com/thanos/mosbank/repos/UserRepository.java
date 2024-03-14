/*
 * Author: Thanos Moschou
 * Description: This is a banking system using Spring Boot.
 * 
 * 
 * Spring Data Jpa uses underscore notation if an attribute
 * is bigger than 1 word. So in my classes' attributes I use this notation.
 * Keep in mind that in my method parameters and local variables I use lowerCamelcase.
 * 
 * 
 * Last Modification Date: 8/3/2024
 */

package com.thanos.mosbank.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thanos.mosbank.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{

}
