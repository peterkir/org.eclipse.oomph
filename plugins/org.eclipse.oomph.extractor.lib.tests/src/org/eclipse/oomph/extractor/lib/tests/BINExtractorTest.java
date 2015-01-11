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
package org.eclipse.oomph.extractor.lib.tests;

import org.eclipse.oomph.extractor.lib.BINExtractor;

/**
 * @author Eike Stepper
 */
public class BINExtractorTest
{
  private static final String[] ARGS = { "../org.eclipse.oomph.extractor/Debug/extractor.exe", "unzipped", System.getProperty("java.home"),
      "unzipped/launcher.txt" };

  public static void main(String[] args) throws Exception
  {
    BINExtractor.main(ARGS);
  }
}