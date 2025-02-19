//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.util.Optional;

/**
 * An {@link AnnotationFilter} that accepts all annotated class elements.
 */
public class AllAnnotated implements AnnotationFilter
  {
  public Optional<String> acceptAnnotation( String rawTypeName)
    {
    return Optional.of( ClassData.toClassName( rawTypeName));
    }

  public boolean acceptPackage( String packageName)
    {
    return true;
    }

  public static final AllAnnotated INSTANCE = new AllAnnotated();
  }
