//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;

/**
 * Defines a field that references a specified annotation.
 */
public class AnnotatedField extends Annotated
  {
  /**
   * Creates a new AnnotatedField instance.
   */
  protected AnnotatedField( Class<? extends Annotation> annotation, String className, String field)
    {
    super( annotation, className);
    field_ = field;
    }

  /**
   * Return the class element type.
   */
  public Type getType()
    {
    return Type.FIELD;
    }

  /**
   * Returns the field that references the annotation.
   */
  public String getField()
    {
    return field_;
    }

  private String field_;
  }
