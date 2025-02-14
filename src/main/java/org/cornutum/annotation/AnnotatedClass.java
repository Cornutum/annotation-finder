//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;

/**
 * Defines a class that references a specified annotation.
 */
public class AnnotatedClass extends Annotated
  {
  /**
   * Creates a new AnnotatedClass instance.
   */
  protected AnnotatedClass( Class<? extends Annotation> annotation, String className)
    {
    super( annotation, className);
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.CLASS;
    }
  }
