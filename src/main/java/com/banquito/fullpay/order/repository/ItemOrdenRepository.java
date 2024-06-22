package com.banquito.fullpay.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banquito.fullpay.order.model.ItemOrden;

public interface ItemOrdenRepository extends JpaRepository<ItemOrden, Long> {

}
