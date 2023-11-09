package com.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
    private Locale currentLocale;

    @PostConstruct
    public void init(){
        currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }
    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }

    public void changeLocale(String newLocaleValue) {
        for (Iterator<Locale> it = FacesContext.getCurrentInstance().getApplication().getSupportedLocales(); it.hasNext(); ) {
            Locale locale = it.next();
            if (locale.toString().equals(newLocaleValue)) {
                currentLocale = locale;
                break;
            }
        }
        FacesContext.getCurrentInstance().getViewRoot().setLocale(currentLocale);
    }
}
