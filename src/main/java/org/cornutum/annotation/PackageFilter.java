//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.ClassData.classPackage;
import static org.cornutum.annotation.ClassData.rawTypeName;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * An {@link AnnotationFilter} that accepts annotated elements appearing in classes
 * that belong to specific packages.
 */
public class PackageFilter implements AnnotationFilter
  {
  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  public PackageFilter()
    {
    }

  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  @SafeVarargs
  public PackageFilter( Class<? extends Annotation>... annotations)
    {
    annotation( annotations);
    }

  /**
   * Creates a new SimpleAnnotationFilter instance.
   */
  public PackageFilter( String... annotations)
    {
    annotation( annotations);
    }

  /**
   * Adds to the set of accepted annotations.
   */
  @SafeVarargs  
  public final PackageFilter annotation( Class<? extends Annotation>... annotations)
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
  public PackageFilter annotation( String... annotations)
    {
    for( String annotation : annotations)
      {
      annotations_.put( rawTypeName( annotation), annotation);
      }
    
    return this;
    }

  /**
   * Adds to the set of accepted packages.
   */
  public PackageFilter inPackage( String... packageNames)
    {
    return inPackage( Arrays.asList( packageNames));
    }

  /**
   * Adds to the set of accepted packages.
   */
  public PackageFilter inPackage( Collection<String> packageNames)
    {
    packages_.addAll( packageNames);
    return this;
    }

  /**
   * Returns the annotations accepted by this filter.
   */
  public Set<String> getAnnotations()
    {
    return unmodifiableSet( annotations_.values().stream().collect( toSet()));
    }

  /**
   * Returns the packages accepted by this filter.
   */
  public Set<String> getPackages()
    {
    return unmodifiableSet( packages_);
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
   * Returns true if the given class belongs to an accepted package.
   * If no accepted package has been defined, returns true for any <CODE>packageName</CODE>.
   */
  public boolean acceptClass( String className)
    {
    return packages_.isEmpty() || packages_.contains( classPackage( className));
    }

  public String toString()
    {
    return
      ToString.of( this)
      .append( "annotations", getAnnotations().stream().map( ToString::simpleClassName).collect( toList()))
      .append( "packages", getPackages())
      .toString();
    }

  private Map<String,String> annotations_ = new LinkedHashMap<String,String>();
  private Set<String> packages_ = new HashSet<String>();
  }
