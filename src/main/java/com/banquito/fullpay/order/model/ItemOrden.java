package com.banquito.fullpay.order.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "COR_ITEM_ORDEN", schema = "cobrosrecaudos")
public class ItemOrden implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COD_ITEM_ORDEN", nullable = false)
    private Long id;
    @Column(name = "NUMERO_CONTRAPARTIDA", length = 20, nullable = false)
    private String numeroContrapartida;
    @Column(name = "CEDULA", length = 10, nullable = false)
    private String cedula;

    @Column(name = "NOMBRES", length = 50, nullable = false)
    private String nombres;

    @Column(name = "APELLIDOS", length = 50, nullable = false)
    private String apellidos;

    @Column(name = "MONTO", nullable = false)
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "COD_ORDEN", referencedColumnName = "COD_ORDEN")
    private Orden orden;

    public ItemOrden(Long id) {
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
        ItemOrden other = (ItemOrden) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
