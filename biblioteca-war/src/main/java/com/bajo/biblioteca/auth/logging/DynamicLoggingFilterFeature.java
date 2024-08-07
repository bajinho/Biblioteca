/*
 * Copyright (c) 2011, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.bajo.biblioteca.auth.logging;

import com.bajo.biblioteca.resources.PessoasResource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.container.DynamicFeature;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;

/**
 * Dynamic feature for a enabling a logging request/response post-matching filter that dynamically decides to bind the
 * logging filter only to GET processing resource methods on all subclasses of {@link com.bajo.biblioteca.resources.PessoasResource} and the
 * {@code MyResourceClass} itself.
 *
 * @author Santiago Pericas-Geertsen
 * @author Marek Potociar
 */
@Provider
public final class DynamicLoggingFilterFeature implements DynamicFeature {

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        if (PessoasResource.class.isAssignableFrom(resourceInfo.getResourceClass())
                && resourceInfo.getResourceMethod().isAnnotationPresent(GET.class)) {
            context.register(new LoggingFilter());
        }
    }
}
