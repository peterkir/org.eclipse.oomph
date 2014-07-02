/**
 */
package org.eclipse.oomph.setup.launching;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.oomph.setup.launching.LaunchingPackage
 * @generated
 */
public interface LaunchingFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  LaunchingFactory eINSTANCE = org.eclipse.oomph.setup.launching.impl.LaunchingFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Launch Task</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Launch Task</em>'.
   * @generated
   */
  LaunchTask createLaunchTask();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  LaunchingPackage getLaunchingPackage();

} // LaunchingFactory