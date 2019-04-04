package com.cecula.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Segun Ogundipe
 */
@Entity
@Table(name = "users_has_apps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsersHasApps.findAll", query = "SELECT u FROM UsersHasApps u")})
public class UsersHasApps implements Serializable {

    private static final long serialVersionUID = 8086869063640531292L;

    @EmbeddedId
    protected UsersHasAppsPK usersHasAppsPK;
    
    @Column(name = "generated_password")
    private String generatedPassword;
    
    @JoinColumn(name = "apps_table_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Apps apps;
    
    @JoinColumn(name = "users_table_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public UsersHasApps() {
    }

    public UsersHasApps(UsersHasAppsPK usersHasAppsPK) {
        this.usersHasAppsPK = usersHasAppsPK;
    }

    public UsersHasApps(int usersId, int appsId) {
        this.usersHasAppsPK = new UsersHasAppsPK(usersId, appsId);
    }

    public UsersHasAppsPK getUsersHasAppsPK() {
        return usersHasAppsPK;
    }

    public void setUsersHasAppsPK(UsersHasAppsPK usersHasAppsPK) {
        this.usersHasAppsPK = usersHasAppsPK;
    }

    public String getGeneratedPassword() {
        return generatedPassword;
    }

    public void setGeneratedPassword(String generatedPassword) {
        this.generatedPassword = generatedPassword;
    }

    public Apps getApps() {
        return apps;
    }

    public void setApps(Apps apps) {
        this.apps = apps;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersHasAppsPK != null ? usersHasAppsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsersHasApps)) {
            return false;
        }
        UsersHasApps other = (UsersHasApps) object;
        if ((this.usersHasAppsPK == null && other.usersHasAppsPK != null) || (this.usersHasAppsPK != null && !this.usersHasAppsPK.equals(other.usersHasAppsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usersHasAppsPK.toString();
    }
    
}
