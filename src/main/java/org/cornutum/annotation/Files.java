//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

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
  }
