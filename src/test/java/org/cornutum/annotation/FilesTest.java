//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.TestFiles.*;

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
        "Bravo",
        "Charlie.class",
        "Delta",
        "Echo",
        "Foxtrot",
        "Golf",
        "Hotel.class",
        "India",
        "Juliett",
        "Kilo.class",
        "Lima"));
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
