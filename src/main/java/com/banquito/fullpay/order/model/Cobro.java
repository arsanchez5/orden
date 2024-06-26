package com.banquito.fullpay.order.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "COR_COBRO", schema = "cobrosrecaudos")
public class Cobro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_COBRO")
    private Long codCobro;

    @Column(name = "COD_CUENTA", nullable = false)
    private int corCodCuenta;

    @Column(name = "COD_CONTRATO", nullable = false)
    private int corCodContrato;

    @Column(name = "ASUNTO", length = 100, nullable = false)
    private String asunto;

    @Column(name = "MONTO_COBRADO", nullable = false)
    private BigDecimal montoCobrado;

    @Column(name = "ESTADO", length = 3, nullable = false)
    private String estado;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codCobro == null) ? 0 : codCobro.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cobro other = (Cobro) obj;
        if (codCobro == null) {
            if (other.codCobro != null)
                return false;
        } else if (!codCobro.equals(other.codCobro))
            return false;
        return true;
    }

    
    

}
