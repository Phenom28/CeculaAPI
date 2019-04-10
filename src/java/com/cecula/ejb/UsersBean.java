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
    
//    @Override
//    public void create(Users user){
//        Groups group = em.find(Groups.class, 1);
//        user.addToGroup(group);
//        group.addUser(user);
//        em.persist(user);
//        em.merge(group);
//    }
    
//    @Override
//    public void remove(Users user) {
//        Groups group = em.find(Groups.class, 1);
//        group.removeUser(user);
//        em.remove(em.merge(user));
//        em.merge(group);
//    }
    
    public Users findByEmail(String email){
        Users user = (Users) em.createNamedQuery("UsersTable.findByEmail").setParameter("email", email)
                .getSingleResult();
        return user;
    }
    
}
