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
  public InputStream getInputStream()
    {
    return reader_.getInputStream();
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( reader_)
      .toString();
    }

  private JarEntryReader reader_;
  }
