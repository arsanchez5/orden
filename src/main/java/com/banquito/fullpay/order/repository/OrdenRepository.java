package com.banquito.fullpay.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banquito.fullpay.order.model.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

}
