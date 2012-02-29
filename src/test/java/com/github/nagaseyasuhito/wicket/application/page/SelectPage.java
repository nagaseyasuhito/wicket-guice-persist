package com.github.nagaseyasuhito.wicket.application.page;

import javax.persistence.EntityManager;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.github.nagaseyasuhito.wicket.application.entity.User;
import com.google.inject.Inject;

public class SelectPage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    public SelectPage() {
        this.add(new Label("size", Model.of(this.entityManager.createQuery("from User", User.class).getResultList().size())));
    }
}
