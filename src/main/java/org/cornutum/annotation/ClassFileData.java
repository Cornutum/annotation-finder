//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Provides access to the data for a class definition in a file.
 */
public class ClassFileData extends AbstractClassFileData
  {
  /**
   * Creates a new ClassFileData instance.
   */
  public ClassFileData( File file)
    {
    file_ = file;
    }

  /**
   * Returns the class data input stream.
   */
  public InputStream getInputStream()
    {
    try
      {
      return new FileInputStream( file_);
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't open file=%s", file_), e);
      }
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
      .append( file_)
      .toString();
    }

  private final File file_;
  }
