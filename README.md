# Wicket and guice-persist integration

Almost Open Session In View pattern was implemented by `Filter`, so cannot catch some commit-time Exceptions.

This integration manage transaction in `IRequestCycleListener` like as Open Session In View pattern, and it can catch all commit-time exceptions and forwarding to wicket managed error page.

## Usage

Add following codes to WebApplication.init() method.

    public class SomeWebApplication extends WebApplication {

        @Override
        protected void init() {
            super.init();

            this.getComponentInstantiationListeners().add(new GuiceComponentInjector(this, new JpaPersistModule("default")));
            this.getRequestCycleListeners().add(new TransactionalRequestCycleListener());
        }
    }
