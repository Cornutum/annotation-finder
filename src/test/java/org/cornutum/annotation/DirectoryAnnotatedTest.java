//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.Iterators.*;
import static org.cornutum.annotation.TestFiles.*;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link DirectoryAnnotated}.
 */
public class DirectoryAnnotatedTest
  {
  @Test
  public void whenAnnotatedField()
    {
    // Given...
    File dir = getTargetDir( getClass());
    AnnotationFilter filter = new PackageFilter( Deprecated.class);
    DirectoryAnnotated iterator = new DirectoryAnnotated( dir, filter);
    
    // When...
    List<Annotated> annotated =
      toStream( iterator)
      .collect( toList());
    
    // Then...
    assertThat(
      "Annotated",
      annotated,
      contains( new AnnotatedField( Deprecated.class, "org.cornutum.annotation.ClassDataTest", "stringField", true)));
    }
  }
