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
package org.eclipse.oomph.setup.workingsets;

import org.eclipse.oomph.setup.SetupTask;
import org.eclipse.oomph.workingsets.WorkingSet;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Set Working Task</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.oomph.setup.workingsets.WorkingSetTask#getWorkingSets <em>Working Sets</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.oomph.setup.workingsets.SetupWorkingSetsPackage#getWorkingSetTask()
 * @model annotation="http://www.eclipse.org/oomph/setup/ValidTriggers triggers='STARTUP MANUAL'"
 *        annotation="http://www.eclipse.org/oomph/setup/Enablement variableName='setup.workingsets.p2' repository='${oomph.update.url}' installableUnits='org.eclipse.oomph.setup.workingsets.feature.group'"
 * @generated
 */
public interface WorkingSetTask extends SetupTask
{
  /**
   * Returns the value of the '<em><b>Working Sets</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.oomph.workingsets.WorkingSet}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Working Sets</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Working Sets</em>' containment reference list.
   * @see org.eclipse.oomph.setup.workingsets.SetupWorkingSetsPackage#getWorkingSetTask_WorkingSets()
   * @model containment="true"
   * @generated
   */
  EList<WorkingSet> getWorkingSets();

} // SetWorkingTask