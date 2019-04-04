package com.cecula.web;

import com.cecula.entity.Users;
import com.cecula.web.util.JsfUtil;
import com.cecula.web.util.JsfUtil.PersistAction;
import com.cecula.ejb.UsersBean;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("usersTableController")
@SessionScoped
public class UsersTableController implements Serializable {

    private static final long serialVersionUID = -7544409546888792017L;

    @EJB
    private UsersBean usersBean;
    private List<Users> users = null;
    private Users selected;

    public UsersTableController() {
    }

    public Users getSelected() {
        return selected;
    }

    public void setSelected(Users selected) {
        this.selected = selected;
    }

    private UsersBean getUsersBean() {
        return usersBean;
    }

    public Users prepareCreate() {
        selected = new Users();
        return selected;
    }

    public void create() {
        selected.setPassword(JsfUtil.encryptPassword(selected.getPassword()));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("UsersTableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("UsersTableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("UsersTableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            users = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Users> getUsers() {
        if (users == null) {
            users = getUsersBean().findAll();
        }
        return users;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                switch (persistAction) {
                    case DELETE:
                        getUsersBean().remove(selected);
                        break;
                    case UPDATE:
                        getUsersBean().edit(selected);
                        break;
                    default:
                        getUsersBean().create(selected);
                        break;
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Users getUser(Integer id) {
        return getUsersBean().find(id);
    }

    public List<Users> getUsersAvailableSelectMany() {
        return getUsersBean().findAll();
    }

    public List<Users> getUsersAvailableSelectOne() {
        return getUsersBean().findAll();
    }

    @FacesConverter(forClass = Users.class)
    public static class UsersTableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersTableController controller = (UsersTableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersTableController");
            return controller.getUser(getKey(value));
        }

        Integer getKey(String value) {
            Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Users) {
                Users o = (Users) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Users.class.getName()});
                return null;
            }
        }

    }

}
