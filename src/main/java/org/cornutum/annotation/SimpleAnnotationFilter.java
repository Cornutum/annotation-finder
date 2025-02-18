//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;

/**
 * Simple base class for {@link AnnotationFilter} implementations.
 */
public class SimpleAnnotationFilter implements AnnotationFilter
  {
  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  public SimpleAnnotationFilter()
    {
    }

  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  @SafeVarargs
  public SimpleAnnotationFilter( Class<? extends Annotation>... annotations)
    {
    annotation( annotations);
    }

  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  public SimpleAnnotationFilter( String... annotations)
    {
    annotation( annotations);
    }

  /**
   * Adds to the set of accepted annotations.
   */
  @SuppressWarnings("unchecked")
  public SimpleAnnotationFilter annotation( Class<? extends Annotation>... annotations)
    {
    return
      annotation(
        Arrays.stream( annotations)
        .map( Class::getName)
        .toArray( String[]::new));
    }

  /**
   * Adds to the set of accepted annotations.
   */
  public SimpleAnnotationFilter annotation( String... annotations)
    {
    for( String annotation : annotations)
      {
      annotations_.put( ClassData.rawTypeName( annotation), annotation);
      }
    
    return this;
    }

  /**
   * Adds to the set of accepted packages.
   */
  public SimpleAnnotationFilter inPackage( String... packageNames)
    {
    return inPackage( Arrays.asList( packageNames));
    }

  /**
   * Adds to the set of accepted packages.
   */
  public SimpleAnnotationFilter inPackage( Collection<String> packageNames)
    {
    packages_.addAll( packageNames);
    return this;
    }

  /**
   * If the given raw type name identifies an accepted {@link Annotation}, returns the annotation class.
   * Otherwise, returns empty.
   */
  public Optional<String> acceptAnnotation( String rawTypeName)
    {
    return Optional.ofNullable( annotations_.get( rawTypeName));
    }

  /**
   * Returns true if the package belongs to an accepted package. 
   */
  public boolean acceptPackage( String packageName)
    {
    return
      Optional.of( packages_)
      .filter( packages -> !packages.isEmpty())
      .map( packages -> packages.contains( packageName))
      .orElse( true);
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( "annotations", annotations_.values().stream().map( ToString::simpleClassName).collect( toList()))
      .append( "packages", packages_)
      .toString();
    }

  private Map<String,String> annotations_ = new LinkedHashMap<String,String>();
  private Set<String> packages_ = new HashSet<String>();
  }
