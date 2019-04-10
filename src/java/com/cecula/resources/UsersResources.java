package com.cecula.resources;

import com.cecula.ejb.UsersBean;
import com.cecula.entity.Users;
import com.cecula.jsonwrapper.LoginWrapper;
import com.cecula.jsonwrapper.ResponseWrapper;
import com.cecula.util.AuthenticationUtility;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Segun Ogundipe <segun.ogundipe at cecula.com>
 */
@Stateless
@Path("user")
public class UsersResources {

    @EJB
    UsersBean usersBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(Users entity) {

        if (entity != null) {
            Users user = new Users();
            user.setEmail(entity.getEmail());
            user.setFirstname(entity.getFirstname());
            user.setLastname(entity.getLastname());
            user.setPassword(AuthenticationUtility.encryptPassword(entity.getPassword()));
            System.out.println(user.getPassword());
            user.setUsername(entity.getUsername());
            usersBean.create(user);

            String token = getToken(user.getEmail());
            String message = "User has been registered successfully";

            ResponseWrapper responseWrapper = new ResponseWrapper(message, token);

            return Response.ok(responseWrapper).build();

        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginWrapper wrapper) {
        if (verifyUSer(wrapper)) {

            String token = getToken(wrapper.getEmail());
            String message = "User has been logged in";

            ResponseWrapper responseWrapper = new ResponseWrapper(message, token);

            return Response.ok(responseWrapper).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response generatePassword(@QueryParam("q") String q){
//        
//    }
    

    private boolean verifyUSer(LoginWrapper wrapper) {
        Users user;
        if (wrapper.getEmail() != null && wrapper.getPassword() != null) {
            user = usersBean.findByEmail(wrapper.getEmail());

            return AuthenticationUtility.checkPassword(wrapper.getPassword(), user.getPassword());
        }

        return false;
    }

    private String getToken(String email) {
        Users user = usersBean.findByEmail(email);

        return AuthenticationUtility.issueToken(user);
    }

}
