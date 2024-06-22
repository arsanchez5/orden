package com.banquito.fullpay.order.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "COR_ORDEN")
public class Orden implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_ORDEN", nullable = false)
    private Long id;
    @Column(name = "COD_COBRO_RECAUDO", nullable = false)
    private Long codCobroRecaudo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDateTime fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_FIN")
    private LocalDateTime fechaFin;
    @Column(name = "MONTO_TOTAL", precision = 17, scale = 2, nullable = false)
    private BigDecimal montoTotal;
    @Column(name = "RECAUDO_TOTAL", precision = 17, scale = 2, nullable = false)
    private BigDecimal recaudoTotal;
    @Column(name = "CUENTA_DESTINO", length = 10, nullable = false)
    private String cuentaDestino;

    public Orden(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Orden other = (Orden) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
