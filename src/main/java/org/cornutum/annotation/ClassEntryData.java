//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.io.InputStream;

/**
 * Provides access to the data for a class definition in a JAR file entry.
 */
public class ClassEntryData extends AbstractClassFileData
  {
  /**
   * Creates a new ClassEntryReader instance.
   */
  public ClassEntryData( JarEntryReader reader)
    {
    reader_ = reader;
    file_ = new File( reader.getFile().getName());
    }

  /**
   * Returns the class data input stream.
   */
  public InputStream getInputStream()
    {
    return reader_.getInputStream();
    }

  /**
   * Returns the file containing the class definition.
   */
  protected File getFile()
    {
    return file_;
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( reader_)
      .toString();
    }

  private final JarEntryReader reader_;
  private final File file_;
  }
