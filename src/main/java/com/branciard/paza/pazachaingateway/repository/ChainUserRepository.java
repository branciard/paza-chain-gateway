package com.branciard.paza.pazachaingateway.repository;

import com.branciard.paza.pazachaingateway.domain.ChainUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChainUser entity.
 */
@SuppressWarnings("unused")
public interface ChainUserRepository extends JpaRepository<ChainUser,Long> {

}
