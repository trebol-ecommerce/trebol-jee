package org.trebol.jpa.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.trebol.jpa.GenericEntity;

/**
 *
 * @author Benjamin La Madrid <bg.lamadrid@gmail.com>
 */
@Entity
@Table(name = "salespeople")
@NamedQueries({ @NamedQuery(name = "Salesperson.findAll", query = "SELECT s FROM Salesperson s") })
public class Salesperson
    implements GenericEntity<Integer> {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "salesperson_id")
  private Integer id;
  @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = true, updatable = true)
  @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Person person;

  public Salesperson() {
  }

  public Salesperson(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Salesperson)) {
      return false;
    }
    Salesperson other = (Salesperson)object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "org.trebol.jpa.entities.Salesperson[ id=" + id + " ]";
  }

}
