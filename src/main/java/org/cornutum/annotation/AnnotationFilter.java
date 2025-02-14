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
 * Defines criteria for finding an {@link Annotation}.
 */
public interface AnnotationFilter
  {
  /**
   * If the given class name identifies an accepted {@link Annotation}, returns the annotation class.
   * Otherwise, returns empty.
   */
  Optional<Class<? extends Annotation>> acceptAnnotation( String className);

  /**
   * Returns true if the package belongs to an accepted package.
   */
  boolean acceptPackage( String packageName);
  }
