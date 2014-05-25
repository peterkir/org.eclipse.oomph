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
package org.eclipse.oomph.p2.internal.core;

import org.eclipse.oomph.p2.P2Exception;
import org.eclipse.oomph.p2.P2Factory;
import org.eclipse.oomph.p2.ProfileDefinition;
import org.eclipse.oomph.p2.Repository;
import org.eclipse.oomph.p2.Requirement;
import org.eclipse.oomph.p2.VersionSegment;
import org.eclipse.oomph.p2.core.Agent;
import org.eclipse.oomph.p2.core.AgentManager;
import org.eclipse.oomph.p2.core.BundlePool;
import org.eclipse.oomph.p2.core.Profile;
import org.eclipse.oomph.p2.core.ProfileTransaction;
import org.eclipse.oomph.util.IOUtil;
import org.eclipse.oomph.util.StringUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.engine.IProfile;
import org.eclipse.equinox.p2.metadata.IInstallableUnit;
import org.eclipse.equinox.p2.metadata.VersionRange;
import org.eclipse.equinox.p2.query.IQuery;
import org.eclipse.equinox.p2.query.IQueryResult;
import org.eclipse.equinox.p2.query.QueryUtil;
import org.eclipse.equinox.p2.repository.IRepositoryManager;
import org.eclipse.equinox.p2.repository.metadata.IMetadataRepositoryManager;

import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class ProfileImpl extends AgentManagerElementImpl implements Profile, PersistentMap.ExtraInfoProvider, IProfile
{
  private static final Map<Object, Object> XML_OPTIONS;

  static
  {
    Map<Object, Object> options = new HashMap<Object, Object>();
    options.put(XMLResource.OPTION_DECLARE_XML, Boolean.FALSE);
    options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
    XML_OPTIONS = Collections.unmodifiableMap(options);
  }

  private final Agent agent;

  private final BundlePool bundlePool;

  private final String id;

  private final String type;

  private final File installFolder;

  private final File referencer;

  private IProfile delegate;

  private ProfileDefinition definition;

  public ProfileImpl(Agent agent, BundlePool bundlePool, String id, String type, File installFolder, File referencer)
  {
    this.agent = agent;
    this.bundlePool = bundlePool;
    this.id = id;
    this.type = type;
    this.installFolder = installFolder;
    this.referencer = referencer;
  }

  @Override
  public String getElementType()
  {
    return "profile";
  }

  public AgentManager getAgentManager()
  {
    return agent.getAgentManager();
  }

  public Agent getAgent()
  {
    return agent;
  }

  public BundlePool getBundlePool()
  {
    return bundlePool;
  }

  public String getProfileId()
  {
    return id;
  }

  public String getType()
  {
    return type;
  }

  public File getLocation()
  {
    if (bundlePool != null)
    {
      return bundlePool.getLocation();
    }

    return getInstallFolder();
  }

  public File getInstallFolder()
  {
    return installFolder;
  }

  public File getReferencer()
  {
    return referencer;
  }

  public String getExtraInfo()
  {
    List<String> tokens = new ArrayList<String>();
    tokens.add(type);
    tokens.add(bundlePool != null ? bundlePool.getLocation().getAbsolutePath() : "");
    tokens.add(installFolder != null ? installFolder.getAbsolutePath() : "");
    tokens.add(referencer != null ? referencer.getAbsolutePath() : "");
    return StringUtil.implode(tokens, '|');
  }

  public synchronized IProfile getDelegate()
  {
    return getDelegate(true);
  }

  public synchronized IProfile getDelegate(boolean loadOnDemand)
  {
    if (delegate == null)
    {
      if (loadOnDemand)
      {
        delegate = getAgent().getProfileRegistry().getProfile(id);
        if (delegate == null)
        {
          throw new P2Exception("Profile does not exist: " + id);
        }
      }
    }

    return delegate;
  }

  public void setDelegate(IProfile delegate)
  {
    this.delegate = delegate;
  }

  public synchronized ProfileDefinition getDefinition()
  {
    if (definition == null)
    {
      String xml = getDelegate().getProperty(PROP_PROFILE_DEFINITION);
      if (xml == null)
      {
        definition = definitionFromRootIUs(this, VersionSegment.MINOR);
      }
      else
      {
        definition = definitionFromXML(xml);
      }
    }

    return definition;
  }

  public void setDefinition(ProfileDefinition definition)
  {
    this.definition = definition;
  }

  public boolean isValid()
  {
    File engineLocation = new File(agent.getLocation(), AgentImpl.ENGINE_PATH);
    File registryLocation = new File(engineLocation, "profileRegistry");
    File profileLocation = new File(registryLocation, id + ".profile");
    return profileLocation.isDirectory();
  }

  public boolean isCurrent()
  {
    return agent.getCurrentProfile() == this;
  }

  public boolean isUsed()
  {
    if (referencer != null)
    {
      if (!referencer.exists())
      {
        return false;
      }

      if (referencer.isFile())
      {
        List<String> lines = IOUtil.readLines(referencer);
        if (!lines.contains(id))
        {
          return false;
        }
      }

      return true;
    }

    return installFolder == null || installFolder.isDirectory();
  }

  @Override
  protected void doDelete()
  {
    ((AgentImpl)agent).deleteProfile(this);
  }

  public ProfileTransaction change()
  {
    return new ProfileTransactionImpl(this);
  }

  public IQueryResult<IInstallableUnit> query(IQuery<IInstallableUnit> query, IProgressMonitor monitor)
  {
    return getDelegate().query(query, monitor);
  }

  public IProvisioningAgent getProvisioningAgent()
  {
    return getDelegate().getProvisioningAgent();
  }

  public String getProperty(String key)
  {
    return getDelegate().getProperty(key);
  }

  public String getInstallableUnitProperty(IInstallableUnit iu, String key)
  {
    return getDelegate().getInstallableUnitProperty(iu, key);
  }

  public Map<String, String> getProperties()
  {
    return getDelegate().getProperties();
  }

  public Map<String, String> getInstallableUnitProperties(IInstallableUnit iu)
  {
    return getDelegate().getInstallableUnitProperties(iu);
  }

  public long getTimestamp()
  {
    return getDelegate().getTimestamp();
  }

  public IQueryResult<IInstallableUnit> available(IQuery<IInstallableUnit> query, IProgressMonitor monitor)
  {
    return getDelegate().available(query, monitor);
  }

  @Override
  public String toString()
  {
    return id;
  }

  public static String definitionToXML(ProfileDefinition definition)
  {
    try
    {
      StringWriter writer = new StringWriter();
      XMLResource resource = new XMLResourceImpl();
      resource.getContents().add(definition);
      resource.save(writer, XML_OPTIONS);
      return writer.toString();
    }
    catch (IOException ex)
    {
      throw new P2Exception(ex);
    }
  }

  public static ProfileDefinition definitionFromXML(String xml)
  {
    try
    {
      XMLResource resource = new XMLResourceImpl();
      resource.load(new InputSource(new StringReader(xml)), XML_OPTIONS);
      return (ProfileDefinition)resource.getContents().get(0);
    }
    catch (IOException ex)
    {
      throw new P2Exception(ex);
    }
  }

  public static ProfileDefinition definitionFromRootIUs(Profile profile, VersionSegment compatibility)
  {
    ProfileDefinition definition = P2Factory.eINSTANCE.createProfileDefinition();

    EList<Requirement> requirements = definition.getRequirements();
    IQueryResult<IInstallableUnit> query = profile.query(QueryUtil.createIUAnyQuery(), null);
    for (IInstallableUnit iu : query)
    {
      if ("true".equals(profile.getInstallableUnitProperty(iu, Profile.PROP_PROFILE_ROOT_IU)))
      {
        VersionRange versionRange = P2Factory.eINSTANCE.createVersionRange(iu.getVersion(), compatibility);

        Requirement requirement = P2Factory.eINSTANCE.createRequirement(iu.getId());
        requirement.setVersionRange(versionRange);
        requirements.add(requirement);
      }
    }

    IMetadataRepositoryManager metadataRepositoryManager = profile.getAgent().getMetadataRepositoryManager();
    java.net.URI[] knownRepositories = metadataRepositoryManager.getKnownRepositories(IRepositoryManager.REPOSITORIES_NON_SYSTEM);
    if (knownRepositories.length > 0)
    {
      EList<Repository> repositories = definition.getRepositories();
      for (java.net.URI knownRepository : knownRepositories)
      {
        Repository repository = P2Factory.eINSTANCE.createRepository(knownRepository.toString());
        repositories.add(repository);
      }
    }

    return definition;
  }
}