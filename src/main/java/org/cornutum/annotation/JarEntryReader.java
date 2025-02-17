//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Provides access to the data for a JAR file entry.
 */
public class JarEntryReader
  {
  /**
   * Creates a new JarEntryReader instance.
   */
  public JarEntryReader( JarFile file, JarEntry entry)
    {
    file_ = file;
    entry_ = entry;
    }

  /**
   * Returns the JAR file for this reader.
   */
  public JarFile getFile()
    {
    return file_;
    }

  /**
   * Returns the JAR entry for this reader.
   */
  public JarEntry getEntry()
    {
    return entry_;
    }

  /**
   * Returns the entry data input stream.
   */
  protected InputStream getInputStream()
    {
    try
      {
      return getFile().getInputStream( getEntry());
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "JAR=%s: Can't open entry=%s", getFile(), getEntry().getName()), e);
      }
    }

  /**
   * Returns the simple name of the JAR file for this entry.
   */
  private String getJarName()
    {
    return new File( getFile().getName()).getName();
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( "jar", getJarName())
      .append( "entry", getEntry().getName())
      .toString();
    }
  
  private JarFile file_;
  private JarEntry entry_;
  }
