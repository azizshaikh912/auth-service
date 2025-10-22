package com.aziz.ecommerce.repository;

import com.aziz.ecommerce.entity.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {}

