package com.github.nagaseyasuhito.wicket.application.page;

import javax.persistence.EntityManager;

import org.apache.wicket.markup.html.WebPage;

import com.github.nagaseyasuhito.wicket.application.entity.Group;
import com.github.nagaseyasuhito.wicket.application.entity.User;
import com.google.inject.Inject;

public class InitializePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    public InitializePage() {
        Group group = new Group();
        group.setName("group");
        this.entityManager.persist(group);

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setLoginId("loginId" + i);
            user.setPassword("password");
            user.setGroup(group);

            this.entityManager.persist(user);
        }
    }
}
