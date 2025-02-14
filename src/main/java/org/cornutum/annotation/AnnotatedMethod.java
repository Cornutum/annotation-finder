//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;

/**
 * Defines a method that references a specified annotation.
 */
public class AnnotatedMethod extends Annotated
  {
  /**
   * Creates a new AnnotatedMethod instance.
   */
  protected AnnotatedMethod( Class<? extends Annotation> annotation, String className, String method)
    {
    super( annotation, className);
    method_ = method;
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.METHOD;
    }

  /**
   * Returns the method that references the annotation.
   */
  public String getMethod()
    {
    return method_;
    }

  private String method_;
  }
