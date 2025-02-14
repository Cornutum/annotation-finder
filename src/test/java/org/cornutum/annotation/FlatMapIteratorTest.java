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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link FlatMapIterator}
 */
public class FlatMapIteratorTest
  {
  @Test
  public void whenAcceptAll()
    {
    // Given...
    FlatMapIterator<File,File> iterator = new FilesIterator( getResourceFile( "Files"));
    
    // When...
    List<String> names = fileNames( Iterators.toList( iterator));
    
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
  
  @Test
  public void whenAcceptSome()
    {
    // Given...
    FlatMapIterator<File,File> iterator = new SubdirectoryIterator( getResourceFile( "Files"), "Foxtrot");
    
    // When...
    List<String> names = fileNames( Iterators.toList( iterator));
    
    // Then...
    assertThat(
      "Files",
      names,
      contains(
        "Echo",
        "Juliett"));
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

  public static class FilesIterator extends FlatMapIterator<File,File>
    {
    public FilesIterator( File dir)
      {
      super( append( Files.allFiles( dir), dir));
      }

    protected Iterator<File> map( File element)
      {
      File[] members = element.listFiles();
      
      return
        Arrays.stream( members == null? new File[0] : members)
        .filter( file -> !file.isDirectory())
        .collect( toList())
        .iterator();
      }

    private static Collection<File> append( Collection<File> files, File other)
      {
      ArrayList<File> appended = new ArrayList<File>( files);
      appended.add( other);
      return appended;
      }
    }  

  public static class SubdirectoryIterator extends FlatMapIterator<File,File>
    {
    public SubdirectoryIterator( File dir, String subDir)
      {
      super( Files.allFiles( dir));
      subDir_ = subDir;
      }

    protected boolean accept( File element)
      {
      return element.isDirectory() && element.getName().equals( subDir_);
      }
    
    protected Iterator<File> map( File element)
      {
      return
        Arrays.stream( element.listFiles())
        .filter( file -> !file.isDirectory())
        .collect( toList())
        .iterator();
      }

    private String subDir_;
    }
  
  }
