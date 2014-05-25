/*
 * Copyright (c) 2014 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.oomph.internal.setup.core.util;

import org.eclipse.oomph.internal.setup.core.SetupContext;
import org.eclipse.oomph.internal.setup.core.bundle.SetupCorePlugin;
import org.eclipse.oomph.setup.Project;
import org.eclipse.oomph.setup.SetupFactory;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.URIHandlerImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Eike Stepper
 */
public class UserURIHandlerImpl extends URIHandlerImpl
{
  private static final Pattern NAME_PATTERN = Pattern.compile("name='([^']*)'");

  private static final Pattern LABEL_PATTERN = Pattern.compile("label='([^']*)'");

  private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("description='([^']*)'");

  @Override
  public boolean canHandle(URI uri)
  {
    return "user".equals(uri.scheme());
  }

  private void create(URI uri, URI normalizedURI)
  {
    Project project = SetupFactory.eINSTANCE.createProject();
    String query = URI.decode(uri.query());
    Matcher nameMatcher = NAME_PATTERN.matcher(query);
    nameMatcher.find();
    project.setName(nameMatcher.group(1));
    Matcher labelMatcher = LABEL_PATTERN.matcher(query);
    labelMatcher.find();
    project.setLabel(labelMatcher.group(1));
    Matcher descriptionMatcher = DESCRIPTION_PATTERN.matcher(query);
    descriptionMatcher.find();
    project.setDescription(descriptionMatcher.group(1));

    Resource resource = EMFUtil.createResourceSet().createResource(normalizedURI);
    resource.getContents().add(project);
    try
    {
      resource.save(null);
    }
    catch (IOException ex)
    {
      SetupCorePlugin.INSTANCE.log(ex);
    }
  }

  private URI normalize(URI uri)
  {
    return SetupContext.GLOBAL_SETUPS_LOCATION_URI.appendSegments(uri.segments());
  }

  @Override
  public InputStream createInputStream(URI uri, Map<?, ?> options) throws IOException
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    if (!uriConverter.exists(normalizedURI, options))
    {
      create(uri, normalizedURI);
    }

    return uriConverter.createInputStream(normalizedURI, options);
  }

  @Override
  public OutputStream createOutputStream(URI uri, Map<?, ?> options) throws IOException
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    return uriConverter.createOutputStream(normalizedURI, options);
  }

  @Override
  public void delete(URI uri, Map<?, ?> options) throws IOException
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    uriConverter.delete(normalizedURI, options);
  }

  @Override
  public Map<String, ?> contentDescription(URI uri, Map<?, ?> options) throws IOException
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    if (!uriConverter.exists(normalizedURI, options))
    {
      create(uri, normalizedURI);
    }

    return uriConverter.contentDescription(normalizedURI, options);
  }

  @Override
  public boolean exists(URI uri, Map<?, ?> options)
  {
    return true;
  }

  @Override
  public Map<String, ?> getAttributes(URI uri, Map<?, ?> options)
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    if (!uriConverter.exists(normalizedURI, options))
    {
      create(uri, normalizedURI);
    }

    return uriConverter.getAttributes(normalizedURI, options);
  }

  @Override
  public void setAttributes(URI uri, Map<String, ?> attributes, Map<?, ?> options) throws IOException
  {
    URI normalizedURI = normalize(uri);
    URIConverter uriConverter = getURIConverter(options);
    if (!uriConverter.exists(normalizedURI, options))
    {
      create(uri, normalizedURI);
    }

    uriConverter.setAttributes(normalizedURI, attributes, options);
  }
}