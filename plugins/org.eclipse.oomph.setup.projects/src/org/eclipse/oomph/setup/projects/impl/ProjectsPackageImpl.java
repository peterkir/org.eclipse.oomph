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
package org.eclipse.oomph.setup.projects.impl;

import org.eclipse.oomph.resources.ResourcesPackage;
import org.eclipse.oomph.setup.SetupPackage;
import org.eclipse.oomph.setup.projects.ProjectsFactory;
import org.eclipse.oomph.setup.projects.ProjectsImportTask;
import org.eclipse.oomph.setup.projects.ProjectsPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProjectsPackageImpl extends EPackageImpl implements ProjectsPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass projectsImportTaskEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see org.eclipse.oomph.setup.projects.ProjectsPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private ProjectsPackageImpl()
  {
    super(eNS_URI, ProjectsFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
   *
   * <p>This method is used to initialize {@link ProjectsPackage#eINSTANCE} when that field is accessed.
   * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static ProjectsPackage init()
  {
    if (isInited)
    {
      return (ProjectsPackage)EPackage.Registry.INSTANCE.getEPackage(ProjectsPackage.eNS_URI);
    }

    // Obtain or create and register package
    ProjectsPackageImpl theProjectsPackage = (ProjectsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProjectsPackageImpl ? EPackage.Registry.INSTANCE
        .get(eNS_URI) : new ProjectsPackageImpl());

    isInited = true;

    // Initialize simple dependencies
    ResourcesPackage.eINSTANCE.eClass();
    SetupPackage.eINSTANCE.eClass();

    // Create package meta-data objects
    theProjectsPackage.createPackageContents();

    // Initialize created meta-data
    theProjectsPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theProjectsPackage.freeze();

    // Update the registry and return the package
    EPackage.Registry.INSTANCE.put(ProjectsPackage.eNS_URI, theProjectsPackage);
    return theProjectsPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getProjectsImportTask()
  {
    return projectsImportTaskEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getProjectsImportTask_SourceLocators()
  {
    return (EReference)projectsImportTaskEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ProjectsFactory getProjectsFactory()
  {
    return (ProjectsFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated)
    {
      return;
    }
    isCreated = true;

    // Create classes and their features
    projectsImportTaskEClass = createEClass(PROJECTS_IMPORT_TASK);
    createEReference(projectsImportTaskEClass, PROJECTS_IMPORT_TASK__SOURCE_LOCATORS);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized)
    {
      return;
    }
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Obtain other dependent packages
    SetupPackage theSetupPackage = (SetupPackage)EPackage.Registry.INSTANCE.getEPackage(SetupPackage.eNS_URI);
    ResourcesPackage theResourcesPackage = (ResourcesPackage)EPackage.Registry.INSTANCE.getEPackage(ResourcesPackage.eNS_URI);

    // Create type parameters

    // Set bounds for type parameters

    // Add supertypes to classes
    projectsImportTaskEClass.getESuperTypes().add(theSetupPackage.getSetupTask());

    // Initialize classes and features; add operations and parameters
    initEClass(projectsImportTaskEClass, ProjectsImportTask.class, "ProjectsImportTask", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getProjectsImportTask_SourceLocators(), theResourcesPackage.getSourceLocator(), null, "sourceLocators", null, 1, -1,
        ProjectsImportTask.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
        IS_ORDERED);

    // Create resource
    createResource("http://git.eclipse.org/c/oomph/org.eclipse.oomph.git/plain/setups/models/Projects.ecore");

    // Create annotations
    // http://www.eclipse.org/emf/2002/Ecore
    createEcoreAnnotations();
    // http://www.eclipse.org/oomph/setup/Enablement
    createEnablementAnnotations();
    // http://www.eclipse.org/oomph/setup/ValidTriggers
    createValidTriggersAnnotations();
  }

  /**
   * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createEcoreAnnotations()
  {
    String source = "http://www.eclipse.org/emf/2002/Ecore";
    addAnnotation(this, source, new String[] { "schemaLocation", "http://git.eclipse.org/c/oomph/org.eclipse.oomph.git/plain/setups/models/Projects.ecore" });
  }

  /**
   * Initializes the annotations for <b>http://www.eclipse.org/oomph/setup/Enablement</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createEnablementAnnotations()
  {
    String source = "http://www.eclipse.org/oomph/setup/Enablement";
    addAnnotation(projectsImportTaskEClass, source, new String[] { "variableName", "setup.projects.p2", "repository", "${oomph.update.url}", "installableUnits",
        "org.eclipse.oomph.setup.projects.feature.group" });
  }

  /**
   * Initializes the annotations for <b>http://www.eclipse.org/oomph/setup/ValidTriggers</b>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void createValidTriggersAnnotations()
  {
    String source = "http://www.eclipse.org/oomph/setup/ValidTriggers";
    addAnnotation(projectsImportTaskEClass, source, new String[] { "triggers", "STARTUP MANUAL" });
  }

} // ProjectsPackageImpl