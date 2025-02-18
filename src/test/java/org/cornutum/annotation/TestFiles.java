//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.net.JarURLConnection;
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
  
  public static File getClassPathJar( String packageName)
    {
    try
      {
      String resourceName = String.format( "%s/", packageName.replace( '.', '/'));
      
      URL resource =
        Optional.ofNullable( Thread.currentThread().getContextClassLoader().getResource( resourceName))
        .orElseThrow( () -> new IllegalArgumentException( String.format( "Package=%s not found on class path", packageName)));

      
      return
        Optional.of( resource)
        .filter( url -> "jar".equals( url.getProtocol()))
        .map( url -> jarFileUrl( url))
        .filter( jarUrl -> "file".equals( jarUrl.getProtocol()))
        .map( TestFiles::toFile)
        .orElseThrow( () -> new IllegalArgumentException( String.format( "Package resource=%s is not a JAR file", resource)));
      }
    catch( Exception e)
      {
      throw new IllegalArgumentException( String.format( "Can't get JAR file containing package=%s", packageName), e);
      }
    }

  /**
   * Returns the file represented by the given URL:
   */
  public static File toFile( URL url)
    {
    try
      {
      return new File( url.toURI().getPath());
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't get file from url=%s", url), e);
      }
    }

  /**
   * Returns the JAR file represented by the given URL:
   */
  public static URL jarFileUrl( URL jar)
    {
    try
      {
      return ((JarURLConnection)jar.openConnection()).getJarFileURL();
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't get JAR file URL from url=%s", jar), e);
      }
    }
  }
