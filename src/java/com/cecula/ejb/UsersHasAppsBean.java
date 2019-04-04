package com.cecula.ejb;

import com.cecula.entity.UsersHasApps;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
@Stateless
public class UsersHasAppsBean extends AbstractBean<UsersHasApps> {

    @PersistenceContext(unitName = "CeculaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersHasAppsBean() {
        super(UsersHasApps.class);
    }
    
}
