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
 * Runs tests for {@link JarAnnotated}.
 */
public class JarAnnotatedTest
  {
  @Test
  public void whenEntriesInPackage()
    {
    // Given...
    File jar = getClassPathJar( "org.hamcrest.collection");
    AnnotationFilter filter = new SimpleAnnotationFilter( Deprecated.class).inPackage( "org.hamcrest.text");
    JarAnnotated iterator = new JarAnnotated( jar, filter);
    
    // When...
    List<Annotated> annotated =
      toStream( iterator)
      .collect( toList());
    
    // Then...
    assertThat(
      "Annotated",
      annotated,
      contains(
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.text.IsEmptyString", "isEmptyString", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.text.IsEmptyString", "isEmptyOrNullString", true)));
    }

  }
