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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

/**
 * Provides methods to handle files.
 */
public final class Files
  {
  /**
   * Creates a new Files instance.
   */
  private Files()
    {
    // Static methods only
    }

  /**
   * Returns all (non-directory) files found in the given directory and its descendants.
   */
  public static Collection<File> allFiles( File dir)
    {
    return
      addMembers(
        new ArrayList<File>(),
        dir.isDirectory()? dir.listFiles() : new File[0]);
    }

  /**
   * Returns all directories found in the given directory and its descendants.
   */
  public static Collection<File> allDirs( File dir)
    {
    return
      addSubdirs(
        new ArrayList<File>(),
        dir.isDirectory()? dir.listFiles() : new File[0]);
    }

  /**
   * Returns all class files found in the given directory and its descendants..
   */
  public static Collection<File> classFiles( File dir)
    {
    return
      allFiles( dir)
      .stream()
      .filter( file -> !file.isDirectory() && file.getName().endsWith( ".class"))
      .collect( toList());
    }

  /**
   * Adds all of the given directory members and their descendants.
   */
  private static Collection<File> addMembers( Collection<File> files, File[] members)
    {
    for( File member : members)
      {
      if( member.isDirectory())
        {
        addMembers( files, member.listFiles());
        }
      else
        {
        files.add( member);
        }
      }
    
    return files;
    }

  /**
   * For each subdirectory among the given directory members, adds the subdirectory and its descendant directories.
   */
  private static Collection<File> addSubdirs( Collection<File> files, File[] members)
    {
    for( File member : members)
      {
      if( member.isDirectory())
        {
        files.add( member);
        addSubdirs( files, member.listFiles());
        }
      }
    
    return files;
    }

  /**
   * Returns the files on the current class path that contain the given packages.
   */
  public static Set<File> classPathFor( String... packageNames)
    {
    return classPathFor( Arrays.asList( packageNames));
    }

  /**
   * Returns the files on the current class path that contain the given packages.
   */
  public static Set<File> classPathFor( Collection<String> packageNames)
    {
    return
      packageNames
      .stream()

      .flatMap( packageName -> {
        try
          {
          return
            Collections.list(
              Thread.currentThread().getContextClassLoader().getResources(
                Arrays.asList( packageName.split( "\\."))
                .stream()
                .collect( joining( "/", "", "/"))))
            .stream();
          }
        catch( Exception e)
          {
          throw new IllegalArgumentException( String.format( "Can't get resources for package=%s", packageName), e);
          }
        })

      .map( url -> {
        Optional<File> classPathFile;
        classPathFile =
          "file".equals( url.getProtocol())?
          toClassFile( url) :
          
          "jar".equals( url.getProtocol())?
          toJarFile( url) :

          Optional.empty();

        return classPathFile;
        })

      .filter( Optional::isPresent)
      .map( Optional::get)

      .collect( toCollection( LinkedHashSet::new));
    }

  /**
   * If the given URL represents a class file or directory, returns the file path.
   */
  public static Optional<File> toClassFile( URL url)
    {
    try
      {
      return
        Optional.of( toFile( url))
        .filter( file -> file.isDirectory() || file.getPath().endsWith( ".class"));
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't get file from url=%s", url), e);
      }
    }

  /**
   * If the given URL represents a JAR file, returns the file path.
   */
  public static Optional<File> toJarFile( URL url)
    {
    try
      {
      JarURLConnection jar = (JarURLConnection)url.openConnection();
      return
        Optional.of( jar.getJarFileURL())
        .filter( jarUrl -> "file".equals( jarUrl.getProtocol()))
        .map( jarUrl -> toFile( jarUrl));
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't get JAR file from url=%s", url), e);
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
  }
