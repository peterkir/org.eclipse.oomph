/**
 */
package org.eclipse.oomph.setup.impl;

import org.eclipse.oomph.setup.ResourceExtractTask;
import org.eclipse.oomph.setup.SetupPackage;
import org.eclipse.oomph.setup.SetupTaskContext;
import org.eclipse.oomph.util.IOUtil;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.URIConverter;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipInputStream;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Extract Task</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.oomph.setup.impl.ResourceExtractTaskImpl#getSourceURL <em>Source URL</em>}</li>
 *   <li>{@link org.eclipse.oomph.setup.impl.ResourceExtractTaskImpl#getTargetURL <em>Target URL</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ResourceExtractTaskImpl extends SetupTaskImpl implements ResourceExtractTask
{
  /**
   * The default value of the '{@link #getSourceURL() <em>Source URL</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceURL()
   * @generated
   * @ordered
   */
  protected static final String SOURCE_URL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getSourceURL() <em>Source URL</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSourceURL()
   * @generated
   * @ordered
   */
  protected String sourceURL = SOURCE_URL_EDEFAULT;

  /**
   * The default value of the '{@link #getTargetURL() <em>Target URL</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetURL()
   * @generated
   * @ordered
   */
  protected static final String TARGET_URL_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getTargetURL() <em>Target URL</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTargetURL()
   * @generated
   * @ordered
   */
  protected String targetURL = TARGET_URL_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ResourceExtractTaskImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return SetupPackage.Literals.RESOURCE_EXTRACT_TASK;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getSourceURL()
  {
    return sourceURL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSourceURL(String newSourceURL)
  {
    String oldSourceURL = sourceURL;
    sourceURL = newSourceURL;
    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL, oldSourceURL, sourceURL));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTargetURL()
  {
    return targetURL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTargetURL(String newTargetURL)
  {
    String oldTargetURL = targetURL;
    targetURL = newTargetURL;
    if (eNotificationRequired())
    {
      eNotify(new ENotificationImpl(this, Notification.SET, SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL, oldTargetURL, targetURL));
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL:
        return getSourceURL();
      case SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL:
        return getTargetURL();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL:
        setSourceURL((String)newValue);
        return;
      case SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL:
        setTargetURL((String)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL:
        setSourceURL(SOURCE_URL_EDEFAULT);
        return;
      case SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL:
        setTargetURL(TARGET_URL_EDEFAULT);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL:
        return SOURCE_URL_EDEFAULT == null ? sourceURL != null : !SOURCE_URL_EDEFAULT.equals(sourceURL);
      case SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL:
        return TARGET_URL_EDEFAULT == null ? targetURL != null : !TARGET_URL_EDEFAULT.equals(targetURL);
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy())
    {
      return super.toString();
    }

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (sourceURL: ");
    result.append(sourceURL);
    result.append(", targetURL: ");
    result.append(targetURL);
    result.append(')');
    return result.toString();
  }

  public boolean isNeeded(SetupTaskContext context) throws Exception
  {
    return false;
  }

  public void perform(SetupTaskContext context) throws Exception
  {
    String sourceURL = getSourceURL();
    URI sourceURI = createResolvedURI(sourceURL);
    URI targetURI = createResolvedURI(getTargetURL());
    URIConverter uriConverter = context.getURIConverter();

    if (targetURI.hasTrailingPathSeparator())
    {
      if (sourceURI.hasTrailingPathSeparator())
      {
        context.log("Unsupported copying folder " + uriConverter.normalize(sourceURI) + " to " + uriConverter.normalize(targetURI));
      }
      else if (uriConverter.exists(sourceURI, null))
      {
        URI targetResourceURI = targetURI;
        Path targetResourcePath = Paths.get(targetResourceURI.toFileString());
        File targetFolder = targetResourcePath.toFile();
        targetFolder.mkdirs();

        InputStream input = null;
        ZipInputStream zis = null;

        try
        {
          input = uriConverter.createInputStream(sourceURI);
          zis = new ZipInputStream(input);
          IOUtil.extract(zis, targetResourceURI);
        }
        finally
        {
          IOUtil.closeSilent(zis);
          IOUtil.closeSilent(input);
        }
      }
    }
    else if (uriConverter.exists(sourceURI, null))
    {
      context.log("Extracting resource failed cause target " + uriConverter.normalize(targetURI) + " is no directory");
    }
    else
    {
      context.log("Cannot copy non-existing " + uriConverter.normalize(sourceURI) + " to " + uriConverter.normalize(targetURI));
    }
  }

} // ResourceExtractTaskImpl
