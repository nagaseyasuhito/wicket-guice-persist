package com.github.nagaseyasuhito.wicket.application;

import org.apache.wicket.Page;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

import com.github.nagaseyasuhito.wicket.TransactionalRequestCycleListener;
import com.github.nagaseyasuhito.wicket.application.page.InitializePage;
import com.google.inject.persist.jpa.JpaPersistModule;

public class TestWebApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return InitializePage.class;
    }

    @Override
    protected void init() {
        super.init();

        this.getComponentInstantiationListeners().add(new GuiceComponentInjector(this, new JpaPersistModule("default")));
        this.getRequestCycleListeners().add(new TransactionalRequestCycleListener());
    }
}
