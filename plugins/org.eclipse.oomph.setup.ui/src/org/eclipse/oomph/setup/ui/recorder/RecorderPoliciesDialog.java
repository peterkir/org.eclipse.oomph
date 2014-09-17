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
package org.eclipse.oomph.setup.ui.recorder;

import org.eclipse.oomph.preferences.PreferencesFactory;
import org.eclipse.oomph.setup.ui.AbstractSetupDialog;
import org.eclipse.oomph.setup.ui.SetupUIPlugin;
import org.eclipse.oomph.util.StringUtil;

import org.eclipse.emf.common.util.URI;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.Map;

/**
 * @author Eike Stepper
 */
public class RecorderPoliciesDialog extends AbstractSetupDialog
{
  private final RecorderTransaction transaction;

  private final Map<URI, String> preferences;

  private boolean enablePreferenceRecorder = true;

  private RecorderPoliciesComposite recorderPoliciesComposite;

  private Text valueText;

  public RecorderPoliciesDialog(Shell parentShell, RecorderTransaction transaction, Map<URI, String> preferences)
  {
    super(parentShell, "Preference Recorder", 600, 400, SetupUIPlugin.INSTANCE, null);
    this.transaction = transaction;
    this.preferences = preferences;
  }

  public boolean isEnablePreferenceRecorder()
  {
    return enablePreferenceRecorder;
  }

  public void setEnablePreferenceRecorder(boolean enablePreferenceRecorder)
  {
    this.enablePreferenceRecorder = enablePreferenceRecorder;
  }

  @Override
  protected String getDefaultMessage()
  {
    return "Define whether to record preference tasks for the listed preferences from now on.";
  }

  @Override
  protected void createUI(Composite parent)
  {
    initializeDialogUnits(parent);

    SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
    sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    recorderPoliciesComposite = new RecorderPoliciesComposite(sashForm, SWT.NONE, transaction, false);
    recorderPoliciesComposite.setFocus();
    recorderPoliciesComposite.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent event)
      {
        updateValue((IStructuredSelection)event.getSelection());
      }
    });

    valueText = new Text(sashForm, SWT.READ_ONLY);
    valueText.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));

    sashForm.setWeights(new int[] { 4, 1 });
    Dialog.applyDialogFont(sashForm);
  }

  @Override
  protected void createButtonsForButtonBar(Composite parent)
  {
    final Button enableButton = createCheckbox(parent, "Recorder enabled");
    enableButton.setToolTipText("The enablement can be changed later on the preference page Oomph | Setup | Preference Recorder");
    enableButton.setSelection(enablePreferenceRecorder);
    enableButton.addSelectionListener(new SelectionAdapter()
    {
      @Override
      public void widgetSelected(SelectionEvent e)
      {
        enablePreferenceRecorder = enableButton.getSelection();
        recorderPoliciesComposite.setEnabled(enablePreferenceRecorder);
        valueText.setVisible(enablePreferenceRecorder);
      }
    });

    super.createButtonsForButtonBar(parent);
  }

  private void updateValue(IStructuredSelection selection)
  {
    String path = (String)selection.getFirstElement();
    URI uri = PreferencesFactory.eINSTANCE.createURI(path);
    String value = StringUtil.safe(preferences.get(uri));
    valueText.setText(value);
  }
}