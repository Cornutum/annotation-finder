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
public class ClassFileData extends ClassData
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
  protected InputStream getInputStream()
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

  public String toString()
    {
    return
      ToString.of( this)
      .append( file_)
      .toString();
    }

  private File file_;
  }
