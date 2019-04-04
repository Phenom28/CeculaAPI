package com.cecula.web;

import com.cecula.entity.UsersHasApps;
import com.cecula.web.util.JsfUtil;
import com.cecula.web.util.JsfUtil.PersistAction;
import com.cecula.ejb.UsersHasAppsBean;

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

@Named("usersHasAppsController")
@SessionScoped
public class UsersHasAppsController implements Serializable {

    @EJB
    private com.cecula.ejb.UsersHasAppsBean ejbFacade;
    private List<UsersHasApps> items = null;
    private UsersHasApps selected;

    public UsersHasAppsController() {
    }

    public UsersHasApps getSelected() {
        return selected;
    }

    public void setSelected(UsersHasApps selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getUsersHasAppsPK().setAppsId(selected.getApps().getId());
        selected.getUsersHasAppsPK().setUsersId(selected.getUsers().getId());
    }

    protected void initializeEmbeddableKey() {
        selected.setUsersHasAppsPK(new com.cecula.entity.UsersHasAppsPK());
    }

    private UsersHasAppsBean getFacade() {
        return ejbFacade;
    }

    public UsersHasApps prepareCreate() {
        selected = new UsersHasApps();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("UsersHasAppsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("UsersHasAppsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("UsersHasAppsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UsersHasApps> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public UsersHasApps getUsersHasApps(com.cecula.entity.UsersHasAppsPK id) {
        return getFacade().find(id);
    }

    public List<UsersHasApps> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UsersHasApps> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UsersHasApps.class)
    public static class UsersHasAppsControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsersHasAppsController controller = (UsersHasAppsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usersHasAppsController");
            return controller.getUsersHasApps(getKey(value));
        }

        com.cecula.entity.UsersHasAppsPK getKey(String value) {
            com.cecula.entity.UsersHasAppsPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new com.cecula.entity.UsersHasAppsPK();
            key.setUsersId(Integer.parseInt(values[0]));
            key.setAppsId(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(com.cecula.entity.UsersHasAppsPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getUsersId());
            sb.append(SEPARATOR);
            sb.append(value.getAppsId());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UsersHasApps) {
                UsersHasApps o = (UsersHasApps) object;
                return getStringKey(o.getUsersHasAppsPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsersHasApps.class.getName()});
                return null;
            }
        }

    }

}
