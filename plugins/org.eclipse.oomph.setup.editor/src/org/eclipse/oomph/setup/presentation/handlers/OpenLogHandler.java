/*
 * Copyright (c) 2014, 2015 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.oomph.setup.presentation.handlers;

import org.eclipse.oomph.setup.internal.core.SetupContext;
import org.eclipse.oomph.setup.presentation.SetupEditorPlugin;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import java.io.File;

/**
 * @author Eike Stepper
 */
public class OpenLogHandler extends AbstractDropdownItemHandler
{
  public OpenLogHandler()
  {
    super("Log", "Open Setup Log");
  }

  public void run()
  {
    try
    {
      URI uri = SetupContext.SETUP_LOG_URI;
      if (!URIConverter.INSTANCE.exists(uri, null))
      {
        URIConverter.INSTANCE.createOutputStream(uri).close();
      }

      IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      File file = new File(uri.toFileString());

      IDE.openEditor(page, file.toURI(), "org.eclipse.ui.DefaultTextEditor", true);
    }
    catch (Exception ex)
    {
      SetupEditorPlugin.INSTANCE.log(ex);
    }
  }
}
