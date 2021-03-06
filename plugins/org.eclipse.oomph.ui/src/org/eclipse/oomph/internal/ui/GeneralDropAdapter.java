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
package org.eclipse.oomph.internal.ui;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Widget;

import java.util.HashMap;

/**
 * @author Eike Stepper
 */
public final class GeneralDropAdapter extends OomphDropAdapter
{
  private final EObject containerObject;

  public GeneralDropAdapter(Viewer viewer, EObject containerObject, EReference containmentFeature, DroppedObjectHandler handler)
  {
    super(createEditingDomain(containerObject, containmentFeature, handler), viewer, OomphTransferDelegate.DELEGATES);
    this.containerObject = containerObject;
  }

  @Override
  protected Object extractDropTarget(Widget item)
  {
    return containerObject;
  }

  private static EditingDomain createEditingDomain(final EObject containerObject, final EReference containmentFeature, final DroppedObjectHandler handler)
  {
    if (!containmentFeature.isMany())
    {
      throw new IllegalArgumentException("Not many-valued: " + containmentFeature);
    }

    AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
    EditingDomain editingDomain = new OomphEditingDomain(adapterFactory, new BasicCommandStack(), new HashMap<Resource, Boolean>(),
        OomphTransferDelegate.DELEGATES);

    containerObject.eAdapters().add(new AdapterImpl()
    {
      @Override
      public void notifyChanged(Notification msg)
      {
        if (msg.isTouch())
        {
          return;
        }

        if (msg.getFeature() == containmentFeature && msg.getEventType() == Notification.ADD)
        {
          EList<?> list = (EList<?>)containerObject.eGet(containmentFeature);
          Object droppedObject = list.get(0);
          list.clear();

          try
          {
            handler.handleDroppedObject(droppedObject);
          }
          catch (Exception ex)
          {
            ex.printStackTrace();
          }
        }
      }
    });

    ResourceSet resourceSet = editingDomain.getResourceSet();
    resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new ResourceFactoryImpl());

    Resource resource = resourceSet.createResource(URI.createURI("container.list"));
    resource.getContents().add(containerObject);

    return editingDomain;
  }

  /**
   * @author Eike Stepper
   */
  public interface DroppedObjectHandler
  {
    public void handleDroppedObject(Object object) throws Exception;
  }
}
