/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package com.bajo.biblioteca.auth.compression;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

/**
 * GZIP entity interceptor for compressing and decompressing HTTP entity bodies.
 *
 * This interceptor automatically compresses outgoing responses and decompresses
 * incoming requests using the GZIP algorithm, enhancing network efficiency.
 *
 * The interceptor is applied to both request and response entities by
 * implementing the `ReaderInterceptor` and `WriterInterceptor` interfaces.
 *
 * @author Santiago Pericas-Geertsen
 */
@Provider
@Gzipped
@Priority(Priorities.ENTITY_CODER)
public class GzipEntityInterceptor implements ReaderInterceptor, WriterInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext ctx) throws IOException {
        if (!gzipEncoded(ctx)) {
            return ctx.proceed();
        } else {
            InputStream old = ctx.getInputStream();
            ctx.setInputStream(new GZIPInputStream(old));
            try {
                return ctx.proceed();
            } finally {
                ctx.setInputStream(old);
            }
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext ctx) throws IOException {
        if (!acceptsGzip(ctx)) {
            ctx.proceed();
        } else {
            OutputStream old = ctx.getOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(old);
            ctx.setOutputStream(gzipOutputStream);
            try {
                ctx.proceed();
            } finally {
                gzipOutputStream.finish();
                ctx.setOutputStream(old);
            }
        }
    }

    /**
     * Determines if the client accepts GZIP encoding for the response.
     *
     * This method should be implemented to check the request headers for the
     * `Accept-Encoding` header and its value to determine if GZIP is accepted.
     *
     * @param ctx The interceptor context.
     * @return `true` if the client accepts GZIP encoding, `false` otherwise.
     */
    private boolean acceptsGzip(WriterInterceptorContext ctx) {
        // implementation goes here
        return true;
    }

    /**
     * Determines if the request entity is GZIP encoded.
     *
     * This method should be implemented to check the request headers for the
     * `Content-Encoding` header and its value to determine if the content is
     * GZIP encoded.
     *
     * @param ctx The interceptor context.
     * @return `true` if the request entity is GZIP encoded, `false` otherwise.
     */
    private boolean gzipEncoded(ReaderInterceptorContext ctx) {
        // implementation goes here
        return true;
    }
}
