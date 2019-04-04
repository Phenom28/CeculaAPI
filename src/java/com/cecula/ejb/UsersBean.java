package com.cecula.ejb;

import com.cecula.entity.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
@Stateless
public class UsersBean extends AbstractBean<Users> {

    @PersistenceContext(unitName = "CeculaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersBean() {
        super(Users.class);
    }
    
}
