package com.github.nagaseyasuhito.wicket;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.nagaseyasuhito.wicket.application.TestWebApplication;
import com.github.nagaseyasuhito.wicket.application.page.InitializePage;
import com.github.nagaseyasuhito.wicket.application.page.SelectPage;

public class TransactionRequestCycleListenerTest {

    private WicketTester tester;

    @Before
    public void before() {
        this.tester = new WicketTester(new TestWebApplication());
    }

    @After
    public void after() {
        this.tester.destroy();
    }

    @Test
    public void test() {
        this.tester.startPage(InitializePage.class);
        this.tester.assertRenderedPage(InitializePage.class);

        this.tester.startPage(SelectPage.class);
        this.tester.assertRenderedPage(SelectPage.class);
        this.tester.assertLabel("size", "5");

        // raise unique key violation
        // this.tester.startPage(InitializePage.class);
        // this.tester.assertRenderedPage(InitializePage.class);
    }
}
