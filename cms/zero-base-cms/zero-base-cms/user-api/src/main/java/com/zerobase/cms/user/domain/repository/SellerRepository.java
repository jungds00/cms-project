package com.zerobase.cms.user.domain.repository;

import com.zerobase.cms.user.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByIdAndEmail(Long id, String email);
    Optional<Seller> findByEmailAndPasswordAndVerifyIsTrue(String emalil,String password);

    Optional<Seller> findByEmail(String email);
}
