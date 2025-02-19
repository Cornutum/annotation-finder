//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Defines criteria for finding an annotated class element.
 */
public interface AnnotationFilter
  {
  /**
   * If the given raw type name identifies an accepted {@link Annotation}, returns the annotation class name.
   * Otherwise, returns empty.
   */
  Optional<String> acceptAnnotation( String rawTypeName);

  /**
   * Returns true if annotated elements of the given class are accepted.
   */
  boolean acceptClass( String className);
  }
