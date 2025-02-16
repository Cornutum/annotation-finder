//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;

/**
 * Defines a field that references a specified annotation.
 */
public class AnnotatedField extends Annotated
  {
  /**
   * Creates a new AnnotatedField instance.
   */
  protected AnnotatedField( Class<? extends Annotation> annotation, String className, String field, boolean isRuntime)
    {
    super( annotation, className, isRuntime);
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

  public int hashCode()
    {
    return
      Objects.hashCode( getClass())
      ^ Objects.hashCode( getAnnotation())
      ^ Objects.hashCode( getClassName())
      ^ Objects.hashCode( getField())
      ^ Objects.hashCode( isRuntime())
      ;
    }

  public boolean equals( Object object)
    {
    return
      Optional.ofNullable( object)
      .filter( other -> getClass().equals( other.getClass()))
      .map( other -> (AnnotatedField) other)
      .map( other ->
            Objects.equals( other.getAnnotation(), getAnnotation())
            && Objects.equals( other.getClassName(), getClassName())
            && Objects.equals( other.getField(), getField())
            && Objects.equals( other.isRuntime(), isRuntime()))
      .orElse( false);
    }

  private String field_;
  }
