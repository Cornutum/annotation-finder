//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.io.File;
import java.util.Iterator;

/**
 * Base class for {@link ClassData} implementations that read a class definition from a file.
 */
public abstract class AbstractClassFileData extends ClassData
  {
  /**
   * Returns the file containing the class definition.
   */
  protected abstract File getFile();

  /**
   * Returns the annotated elements found for this class.
   */
  public Iterator<Annotated> getAnnotated( AnnotationFilter filter)
    {
    return
      new MapIterator<Annotated,Annotated>(
        super.getAnnotated( filter),
        (annotated) -> { annotated.setFile( getFile()); return annotated; });
    }
  }
