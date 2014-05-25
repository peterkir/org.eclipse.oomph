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

import org.eclipse.oomph.setup.ui.wizards.SetupWizard;
import org.eclipse.oomph.setup.ui.wizards.SetupWizard.Updater;
import org.eclipse.oomph.ui.UIUtil;

/**
 * @author Eike Stepper
 */
public class PerformHandler extends AbstractDropdownItemHandler
{
  public PerformHandler()
  {
    super("update", "Perform Setup Tasks...");
  }

  public void run()
  {
    Updater updater = new SetupWizard.Updater(true);
    updater.openDialog(UIUtil.getShell());
  }
}