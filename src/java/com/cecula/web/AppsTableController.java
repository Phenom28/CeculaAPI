package com.cecula.web;

import com.cecula.entity.Apps;
import com.cecula.web.util.JsfUtil;
import com.cecula.web.util.JsfUtil.PersistAction;
import com.cecula.ejb.AppsBean;

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

@Named("appsTableController")
@SessionScoped
public class AppsTableController implements Serializable {

    @EJB
    private AppsBean appsBean;
    private List<Apps> apps = null;
    private Apps selected;

    public AppsTableController() {
    }

    public Apps getSelected() {
        return selected;
    }

    public void setSelected(Apps selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private AppsBean getAppsBean() {
        return appsBean;
    }

    public Apps prepareCreate() {
        selected = new Apps();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle").getString("AppsTableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            apps = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle").getString("AppsTableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle").getString("AppsTableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            apps = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Apps> getApps() {
        if (apps == null) {
            apps = getAppsBean().findAll();
        }
        return apps;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getAppsBean().edit(selected);
                } else {
                    getAppsBean().remove(selected);
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

    public Apps getAppsTable(Integer id) {
        return getAppsBean().find(id);
    }

    public List<Apps> getAppsAvailableSelectMany() {
        return getAppsBean().findAll();
    }

    public List<Apps> getAppsAvailableSelectOne() {
        return getAppsBean().findAll();
    }

    @FacesConverter(forClass = Apps.class)
    public static class AppsTableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AppsTableController controller = (AppsTableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "appsTableController");
            return controller.getAppsTable(getKey(value));
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
            if (object instanceof Apps) {
                Apps o = (Apps) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Apps.class.getName()});
                return null;
            }
        }

    }

}
