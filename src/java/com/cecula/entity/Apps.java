package com.cecula.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Segun Ogundipe
 */
@Entity
@Table(name = "apps_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AppsTable.findAll", query = "SELECT a FROM Apps a")
    , @NamedQuery(name = "AppsTable.findById", query = "SELECT a FROM Apps a WHERE a.id = :id")
    , @NamedQuery(name = "AppsTable.findByAppName", query = "SELECT a FROM Apps a WHERE a.appName = :appName")})
public class Apps implements Serializable {

    private static final long serialVersionUID = -5590953455302333423L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "app_name")
    private String appName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apps")
    private List<UsersHasApps> usersHasApps;

    public Apps() {
    }

    public Apps(Integer id) {
        this.id = id;
    }

    public Apps(Integer id, String appName) {
        this.id = id;
        this.appName = appName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @XmlTransient
    public List<UsersHasApps> getUsersHasApps() {
        return usersHasApps;
    }

    public void setUsersHasApps(List<UsersHasApps> usersHasApps) {
        this.usersHasApps = usersHasApps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Apps)) {
            return false;
        }
        Apps other = (Apps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return appName;
    }
    
}
