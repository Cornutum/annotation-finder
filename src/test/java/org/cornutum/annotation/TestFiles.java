//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 * Provides methods to handle test files.
 */
public final class TestFiles
  {
  private TestFiles()
    {
    // Static methods only
    }

  public static List<String> fileNames( Collection<File> files)
    {
    return
      files
      .stream()
      .map( File::getName)
      .sorted()
      .collect( toList());
    }
  
  public static File getResourceFile( Class<?> parent, String name)
    {
    try
      {
      URL resource = Optional.ofNullable( parent.getResource( name)).orElseThrow( () -> new IllegalArgumentException( "Resource not found")); 
      return new File( resource.toURI().getPath());
      }
    catch( Exception e)
      {
      throw new IllegalArgumentException( String.format( "Can't get resource file=%s in package=%s", name, parent.getPackage().getName()), e);
      }
    }
  
  public static File getTargetDir( Class<?> testClass)
    {
    try
      {
      URL resource = Optional.ofNullable( testClass.getResource( ".")).orElseThrow( () -> new IllegalArgumentException( "Resource not found")); 
      File testClassDir = new File( resource.toURI().getPath());

      File targetDir;
      for( targetDir = testClassDir;
           targetDir != null && !targetDir.getName().equals( "target");
           targetDir = targetDir.getParentFile());

      return targetDir;
      }
    catch( Exception e)
      {
      throw new IllegalArgumentException( String.format( "Can't get target directory containing class=%s", testClass.getSimpleName()), e);
      }
    }
  }
