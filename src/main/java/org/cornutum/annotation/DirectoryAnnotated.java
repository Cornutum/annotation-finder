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
 * Returns selected {@link Annotated} instances from class files in the given directory.
 */
public class DirectoryAnnotated extends FlatMapIterator<Annotated,File>
  {
  /**
   * Creates a new DirAnnotated instance.
   */
  public DirectoryAnnotated( File dir, AnnotationFilter filter)
    {
    super( Files.classFiles( dir));
    filter_ = filter;
    }

  /**
   * Maps the given file to a sequence of {@link Annotated} instances.
   */
  protected Iterator<Annotated> map( File file)
    {
    return new ClassFileData( file).getAnnotated( filter_);
    }

  private AnnotationFilter filter_;
  }
