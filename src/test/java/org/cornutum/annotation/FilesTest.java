//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link Files} methods.
 */
public class FilesTest
  {
  @Test
  public void whenAllFiles()
    {
    // Given...
    Collection<File> files = Files.allFiles( getResourceFile( "Files"));
    
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
    Collection<File> files = Files.classFiles( getResourceFile( "Files"));
    
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

  private List<String> fileNames( Collection<File> files)
    {
    return
      files
      .stream()
      .map( File::getName)
      .sorted()
      .collect( toList());
    }
  
  private File getResourceFile( String name)
    {
    try
      {
      URL resource = Optional.ofNullable( getClass().getResource( name)).orElseThrow( () -> new IllegalArgumentException( "Resource not found")); 
      return new File( resource.toURI().getPath());
      }
    catch( Exception e)
      {
      throw new IllegalArgumentException( String.format( "Can't get resource file=%s", name), e);
      }
    }
  
  }
