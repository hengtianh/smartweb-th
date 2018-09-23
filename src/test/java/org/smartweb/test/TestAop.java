package org.smartweb.test;

import org.junit.Before;
import org.junit.Test;
import org.smartweb.container.BeanContainer;
import org.smartweb.container.BeanContainerBuilder;
import org.smartweb.service.TestService;

public class TestAop {

    @Before
    public void init() {
        BeanContainerBuilder.buildWebContainer();
    }

    @Test
    public void testAop() {
        TestService test = BeanContainer.getBean(TestService.class);
        test.say();
    }
}
