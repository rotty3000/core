/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.tests.injectionPoint.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Extension;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.BeanArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.weld.test.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CustomBeanInjectionPointMetadataTest {

    @Inject
    Bar bar;

    @Deployment
    public static Archive<?> getDeployment() {
        return ShrinkWrap.create(BeanArchive.class, Utils.getDeploymentNameAsHash(CustomBeanInjectionPointMetadataTest.class))
                .addPackage(CustomBeanInjectionPointMetadataTest.class.getPackage()).addAsServiceProvider(Extension.class, BarExtension.class);
    }

    @Test
    public void testInjectionPointMetadata() {
        assertNotNull(bar);
        // The test class instance is non-contextual object
        assertNull(bar.getInjetionPointMetadata().getBean());
        // Verify metadata
        assertEquals(Bar.class, bar.getInjetionPointMetadata().getType());
        assertEquals(1, bar.getInjetionPointMetadata().getQualifiers().size());
        assertEquals(Default.Literal.INSTANCE, bar.getInjetionPointMetadata().getQualifiers().iterator().next());
    }
}