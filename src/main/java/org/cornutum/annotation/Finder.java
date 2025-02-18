//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;
import static org.cornutum.annotation.Iterators.*;

/**
 * Finds class elements associated with specified annotations.
 */
public class Finder
  {
  /**
   * Creates a new Finder instance.
   */
  public Finder()
    {
    this( null);
    }
  
  /**
   * Creates a new Finder instance.
   */
  public Finder( AnnotationFilter filter)
    {
    filter( filter == null? new SimpleAnnotationFilter() : filter);
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
   * Find annotated class references accepted by the given filter.
   * If <CODE>filter</CODE> is null, accept all references.
   */
  public Finder filter( AnnotationFilter filter)
    {
    filter_ = filter;
    return this;
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
   * Returns annotated elements found among the current class path elements.
   */
  public Stream<Annotated> find()
    {
    return toStream( new ClassPathAnnotated( classPath_, filter_));
    }

  private AnnotationFilter filter_;
  private Set<File> classPath_ = new LinkedHashSet<File>();
  }
