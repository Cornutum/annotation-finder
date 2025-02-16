//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.TestFiles.*;

import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Runs tests for {@link Files} methods.
 */
public class FilesTest
  {
  @Test
  public void whenAllFiles()
    {
    // Given...
    Collection<File> files = Files.allFiles( getResourceFile( getClass(), "Files"));
    
    // When...
    List<String> names = fileNames( files);
    
    // Then...
    assertThat(
      "Files",
      names,
      contains(
        "Alpha",
        "Charlie.class",
        "Echo",
        "Hotel.class",
        "India",
        "Juliett",
        "Kilo.class",
        "Lima"));
    }

  @Ignore( "Mystery: Empty directories (sometimes!) not returned (MacOS Monterey, Oracle JDK 8")
  @Test
  public void whenAllDirs()
    {
    // Given...
    Collection<File> files = Files.allDirs( getResourceFile( getClass(), "Files"));
    
    // When...
    List<String> names = fileNames( files);
    
    // Then...
    assertThat(
      "Subdirectories",
      names,
      contains(
        "Bravo",
        "Delta",
        "Foxtrot",
        "Golf"));
    }
  
  @Test
  public void whenClassFiles()
    {
    // Given...
    Collection<File> files = Files.classFiles( getResourceFile( getClass(), "Files"));
    
    // When...
    List<String> names = fileNames( files);
    
    // Then...
    assertThat(
      "Files",
      names,
      contains(
        "Charlie.class",
        "Hotel.class",
        "Kilo.class"));
    }
  }
