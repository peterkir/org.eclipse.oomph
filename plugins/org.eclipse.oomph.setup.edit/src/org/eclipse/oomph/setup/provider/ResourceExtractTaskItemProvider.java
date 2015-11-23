/**
 */
package org.eclipse.oomph.setup.provider;

import org.eclipse.oomph.setup.ResourceExtractTask;
import org.eclipse.oomph.setup.SetupPackage;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import java.util.Collection;
import java.util.List;

/**
 * This is the item provider adapter for a {@link org.eclipse.oomph.setup.ResourceExtractTask} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class ResourceExtractTaskItemProvider extends SetupTaskItemProvider
{
  /**
   * This constructs an instance from a factory and a notifier.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ResourceExtractTaskItemProvider(AdapterFactory adapterFactory)
  {
    super(adapterFactory);
  }

  /**
   * This returns the property descriptors for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object)
  {
    if (itemPropertyDescriptors == null)
    {
      super.getPropertyDescriptors(object);

      addSourceURLPropertyDescriptor(object);
      addTargetURLPropertyDescriptor(object);
    }
    return itemPropertyDescriptors;
  }

  /**
   * This adds a property descriptor for the Source URL feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addSourceURLPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
        getString("_UI_ResourceExtractTask_sourceURL_feature"),
        getString("_UI_PropertyDescriptor_description", "_UI_ResourceExtractTask_sourceURL_feature", "_UI_ResourceExtractTask_type"),
        SetupPackage.Literals.RESOURCE_EXTRACT_TASK__SOURCE_URL, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This adds a property descriptor for the Target URL feature.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected void addTargetURLPropertyDescriptor(Object object)
  {
    itemPropertyDescriptors.add(createItemPropertyDescriptor(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(), getResourceLocator(),
        getString("_UI_ResourceExtractTask_targetURL_feature"),
        getString("_UI_PropertyDescriptor_description", "_UI_ResourceExtractTask_targetURL_feature", "_UI_ResourceExtractTask_type"),
        SetupPackage.Literals.RESOURCE_EXTRACT_TASK__TARGET_URL, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
  }

  /**
   * This returns ResourceExtractTask.gif.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object getImage(Object object)
  {
    return overlayImage(object, getResourceLocator().getImage("full/obj16/ResourceExtractTask"));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected boolean shouldComposeCreationImage()
  {
    return true;
  }

  /**
   * This returns the label text for the adapted class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getText(Object object)
  {
    String label = ((ResourceExtractTask)object).getID();
    return label == null || label.length() == 0 ? getString("_UI_ResourceExtractTask_type") : getString("_UI_ResourceExtractTask_type") + " " + label;
  }

  /**
   * This handles model notifications by calling {@link #updateChildren} to update any cached
   * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void notifyChanged(Notification notification)
  {
    updateChildren(notification);

    switch (notification.getFeatureID(ResourceExtractTask.class))
    {
      case SetupPackage.RESOURCE_EXTRACT_TASK__SOURCE_URL:
      case SetupPackage.RESOURCE_EXTRACT_TASK__TARGET_URL:
        fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
        return;
    }
    super.notifyChanged(notification);
  }

  /**
   * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
   * that can be created under this object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object)
  {
    super.collectNewChildDescriptors(newChildDescriptors, object);
  }

}
