package com.banquito.fullpay.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.fullpay.order.model.Cobro;
import com.banquito.fullpay.order.model.Orden;

public interface CobroRepository extends JpaRepository<Cobro, Integer>{


}
