/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author bailo
 */
@Entity
@Table(name = "producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByCodiProd", query = "SELECT p FROM Producto p WHERE p.codiProd = :codiProd"),
    @NamedQuery(name = "Producto.findByNombProd", query = "SELECT p FROM Producto p WHERE p.nombProd = :nombProd"),
    @NamedQuery(name = "Producto.findByPrecProd", query = "SELECT p FROM Producto p WHERE p.precProd = :precProd"),
    @NamedQuery(name = "Producto.findByStockProd", query = "SELECT p FROM Producto p WHERE p.stockProd = :stockProd")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiProd")
    private Integer codiProd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nombProd")
    private String nombProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precProd")
    private double precProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stockProd")
    private int stockProd;

    public Producto() {
    }

    public Producto(Integer codiProd) {
        this.codiProd = codiProd;
    }

    public Producto(Integer codiProd, String nombProd, double precProd, int stockProd) {
        this.codiProd = codiProd;
        this.nombProd = nombProd;
        this.precProd = precProd;
        this.stockProd = stockProd;
    }

    public Integer getCodiProd() {
        return codiProd;
    }

    public void setCodiProd(Integer codiProd) {
        this.codiProd = codiProd;
    }

    public String getNombProd() {
        return nombProd;
    }

    public void setNombProd(String nombProd) {
        this.nombProd = nombProd;
    }

    public double getPrecProd() {
        return precProd;
    }

    public void setPrecProd(double precProd) {
        this.precProd = precProd;
    }

    public int getStockProd() {
        return stockProd;
    }

    public void setStockProd(int stockProd) {
        this.stockProd = stockProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiProd != null ? codiProd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.codiProd == null && other.codiProd != null) || (this.codiProd != null && !this.codiProd.equals(other.codiProd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Producto[ codiProd=" + codiProd + " ]";
    }
    
}
