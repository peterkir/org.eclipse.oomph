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
package org.eclipse.oomph.setup.presentation.handlers;

import org.eclipse.oomph.internal.setup.core.SetupContext;
import org.eclipse.oomph.setup.presentation.SetupEditorPlugin;

import org.eclipse.emf.ecore.resource.URIConverter;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import java.net.URI;

/**
 * @author Eike Stepper
 */
public class OpenLogHandler extends AbstractDropdownItemHandler
{
  public OpenLogHandler()
  {
    super("Log", "Open Log File");
  }

  public void run()
  {
    try
    {
      if (!URIConverter.INSTANCE.exists(SetupContext.SETUP_LOG_URI, null))
      {
        URIConverter.INSTANCE.createOutputStream(SetupContext.SETUP_LOG_URI).close();
      }

      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      IDE.openEditor(page, new URI(SetupContext.SETUP_LOG_URI.toString()), "org.eclipse.ui.DefaultTextEditor", true);
    }
    catch (Exception ex)
    {
      SetupEditorPlugin.INSTANCE.log(ex);
    }
  }
}