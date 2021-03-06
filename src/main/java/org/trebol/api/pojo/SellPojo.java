package org.trebol.api.pojo;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Benjamin La Madrid <bg.lamadrid at gmail.com>
 */
public class SellPojo {
  @JsonInclude
  private Integer id;
  @JsonInclude
  @NotNull
  private Date date;
  @JsonInclude
  @NotNull
  private int subtotal;
  @JsonInclude
  @NotNull
  private SellTypePojo sellType;
  @JsonInclude
  @NotNull
  private CustomerPojo customer;
  @JsonInclude
  @Nullable
  private SalespersonPojo salesperson;
  @JsonInclude(value = Include.NON_EMPTY)
  @NotEmpty
  @Valid
  private Collection<SellDetailPojo> sellDetails;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getSubtotal() {
    return subtotal;
  }

  public void setSubtotal(int subtotal) {
    this.subtotal = subtotal;
  }

  public SellTypePojo getSellType() {
    return sellType;
  }

  public void setSellType(SellTypePojo sellType) {
    this.sellType = sellType;
  }

  public CustomerPojo getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerPojo customer) {
    this.customer = customer;
  }

  public SalespersonPojo getSalesperson() {
    return salesperson;
  }

  public void setSalesperson(SalespersonPojo salesperson) {
    this.salesperson = salesperson;
  }

  public Collection<SellDetailPojo> getSellDetails() {
    return sellDetails;
  }

  public void setSellDetails(Collection<SellDetailPojo> sellDetails) {
    this.sellDetails = sellDetails;
  }

}
