//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.InputStream;

/**
 * Provides access to the data for a class definition in a JAR file entry.
 */
public class ClassEntryData extends ClassData
  {
  /**
   * Creates a new ClassEntryReader instance.
   */
  public ClassEntryData( JarEntryReader reader)
    {
    reader_ = reader;
    }

  /**
   * Returns the class data input stream.
   */
  protected InputStream getInputStream()
    {
    return reader_.getInputStream();
    }

  private JarEntryReader reader_;
  }
