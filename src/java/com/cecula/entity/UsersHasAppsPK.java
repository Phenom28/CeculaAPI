package com.cecula.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Segun Ogundipe
 */
@Embeddable
public class UsersHasAppsPK implements Serializable {

    private static final long serialVersionUID = -8778229933410757304L;

    @Basic(optional = false)
    @Column(name = "users_table_id")
    private int usersId;
    
    @Basic(optional = false)
    @Column(name = "apps_table_id")
    private int appsId;

    public UsersHasAppsPK() {
    }

    public UsersHasAppsPK(int usersId, int appsId) {
        this.usersId = usersId;
        this.appsId = appsId;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public int getAppsId() {
        return appsId;
    }

    public void setAppsId(int appsId) {
        this.appsId = appsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usersId;
        hash += (int) appsId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsersHasAppsPK)) {
            return false;
        }
        UsersHasAppsPK other = (UsersHasAppsPK) object;
        if (this.usersId != other.usersId) {
            return false;
        }
        if (this.appsId != other.appsId) {
            return false;
        }
        return true;
    }
    
}
