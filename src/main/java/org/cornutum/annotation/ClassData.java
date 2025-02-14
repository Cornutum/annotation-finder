//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An interface to the data for a class definition.
 */
public abstract class ClassData
  {
  /**
   * Returns the annotated elements found for this class.
   */
  public Iterator<Annotated> getAnnotated( AnnotationFilter filter)
    {
    try( InputStream classData = getInputStream())
      {
      return annotated_.iterator();
      }
    catch( Exception e)
      {
      throw new IllegalStateException( String.format( "Can't read class data for %s", this), e);
      }
    }

  /**
   * Returns the class data input stream.
   */
  protected abstract InputStream getInputStream();

  private List<Annotated> annotated_ = new ArrayList<Annotated>();
  }
