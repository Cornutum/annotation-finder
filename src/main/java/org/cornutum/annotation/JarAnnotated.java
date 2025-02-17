//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.jar.JarFile;

import static java.util.stream.Collectors.toList;

/**
 * Returns selected {@link Annotated} instances from class files in the given JAR file.
 */
public class JarAnnotated extends FlatMapIterator<Annotated, JarEntryReader>
  {
  /**
   * Creates a new DirAnnotated instance.
   */
  public JarAnnotated( File jar, AnnotationFilter filter)
    {
    super( entryReaders( jar));
    jar_ = jar;
    filter_ = filter;
    }

  /**
   * Returns the JAR file for this iterator.
   */
  public File getJar()
    {
    return jar_;
    }

  /**
   * Returns true if this JAR entry will be included in the mapping.
   */
  protected boolean accept( JarEntryReader reader)
    {
    return filter_.acceptPackage( entryPackage( reader.getEntry().getName()));
    }

  /**
   * Maps the given file to a sequence of {@link Annotated} instances.
   */
  protected Iterator<Annotated> map( JarEntryReader reader)
    {
    return new ClassEntryData( reader).getAnnotated( filter_);
    }

  /**
   * Returns the package name for the given JAR class entry.
   */
  private static String entryPackage( String classEntryName)
    {
    return classEntryName.substring( 0, classEntryName.lastIndexOf( '/')).replace( '/', '.');
    }

  /**
   * Returns an {@link JarEntryReader} for each class file in the given JAR file
   */
  private static Collection<JarEntryReader> entryReaders( File jar)
    {
    try
      {
      JarFile jarFile = new JarFile( jar);
      return
        Collections.list( jarFile.entries())
        .stream()
        .filter( entry -> entry.getName().endsWith( ".class"))
        .map( entry -> new JarEntryReader( jarFile, entry))
        .collect( toList());
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't read entries for jar=%s", jar), e);
      }
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( getJar().getName())
      .toString();
    }

  private final File jar_;
  private final AnnotationFilter filter_;
  }
