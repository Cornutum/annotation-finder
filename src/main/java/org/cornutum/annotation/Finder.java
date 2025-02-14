//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;
import static org.cornutum.annotation.Iterators.*;

/**
 * Finds class elements associated with specified annotations.
 */
public class Finder implements AnnotationFilter
  {
  /**
   * Creates a new Finder instance.
   */
  public Finder()
    {
    }
  
  /**
   * Creates a new Finder instance.
   */
  public Finder( Class<? extends Annotation> annotation)
    {
    this();
    annotation( annotation);
    }

  /**
   * Adds an annotation to find.
   */
  public Finder annotation( Class<? extends Annotation> annotation)
    {
    // Identify the annotation class by its raw type name.
    annotations_.put( String.format( "L%s;", annotation.getName().replace( '.', '/')), annotation);
    return this;
    }

  /**
   * Find annotated classes that belong to one of the given packages
   */
  public Finder inPackage( String... packageNames)
    {
    return inPackage( Arrays.asList( packageNames));
    }

  /**
   * Find annotated classes that belong to one of the given packages
   */
  public Finder inPackage( Collection<String> packageNames)
    {
    packages_.addAll( packageNames);
    return this;
    }

  /**
   * Find annotated classes among one of the given class path elements specified when the JVM was started.
   */
  public Finder inSystemClassPath()
    {
    return
      inClasses(
        Arrays.stream( System.getProperty( "java.class.path").split( System.getProperty( "path.separator")))
        .map( File::new)
        .collect( toList()));
    }

  /**
   * Find annotated classes among one of the given class path elements.
   * Each file must be a *.class file, a directory, or a JAR file.
   */
  public Finder inClasses( File... classPath)
    {
    return inClasses( Arrays.asList( classPath));
    }

  /**
   * Find annotated classes among one of the given class path elements.
   * Each file must be a *.class file, a directory, or a JAR file.  
   */
  public Finder inClasses( Collection<File> classPath)
    {
    for( File file : classPath)
      {
      classPath_.add( file);
      }
    
    return this;
    }

  /**
   * Returns annotated elements found on the current class path.
   */
  public Stream<Annotated> find()
    {
    return toStream( new ClassPathAnnotated( classPath_, this));
    }

  /**
   * If the given class name identifies an accepted {@link Annotation}, returns the annotation class.
   * Otherwise, returns empty.
   */
  public Optional<Class<? extends Annotation>> acceptAnnotation( String className)
    {
    return Optional.ofNullable( annotations_.get( className));
    }

  /**
   * Returns true if the given package is an accepted package.
   */
  public boolean acceptPackage( String packageName)
    {
    return
      Optional.of( packages_)
      .filter( packages -> !packages.isEmpty())
      .map( packages -> packages.contains( packageName))
      .orElse( true);
    }
  
  private LinkedHashMap<String,Class<? extends Annotation>> annotations_;
  private Set<String> packages_ = new LinkedHashSet<String>();
  private Set<File> classPath_ = new LinkedHashSet<File>();
  }
