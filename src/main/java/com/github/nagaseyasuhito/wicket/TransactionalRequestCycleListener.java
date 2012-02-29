package com.github.nagaseyasuhito.wicket;

import javax.persistence.EntityManager;

import org.apache.wicket.Application;
import org.apache.wicket.IApplicationListener;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;

public class TransactionalRequestCycleListener extends AbstractRequestCycleListener implements IApplicationListener {

    // EntityManager must wrap with Provider interface.
    @Inject
    private Provider<EntityManager> entityManager;

    // UnitOfWork must wrap with Provider interface.
    @Inject
    private Provider<UnitOfWork> workManager;

    @Inject
    private PersistService persistService;

    public TransactionalRequestCycleListener() {
        Injector.get().inject(this);

        // add this to applicationListeners for persistService start/stop.
        Application.get().getApplicationListeners().add(this);
    }

    @Override
    public void onBeginRequest(RequestCycle cycle) {
        this.workManager.get().begin();
        this.entityManager.get().getTransaction().begin();
    }

    @Override
    public void onEndRequest(RequestCycle cycle) {
        try {
            if (this.entityManager.get().getTransaction().isActive()) {
                if (this.entityManager.get().getTransaction().getRollbackOnly()) {
                    this.entityManager.get().getTransaction().rollback();
                } else {
                    this.entityManager.get().getTransaction().commit();
                }
            }
        } finally {
            this.workManager.get().end();
        }
    }

    @Override
    public IRequestHandler onException(RequestCycle cycle, Exception ex) {
        this.entityManager.get().getTransaction().rollback();

        return super.onException(cycle, ex);
    }

    @Override
    public void onAfterInitialized(Application application) {
        this.persistService.start();
    }

    @Override
    public void onBeforeDestroyed(Application application) {
        this.persistService.stop();
    }
}
