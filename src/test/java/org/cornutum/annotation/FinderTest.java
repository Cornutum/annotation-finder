//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import static org.cornutum.annotation.TestFiles.*;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link Finder}.
 */
public class FinderTest
  {
  @Test
  public void whenInClassesAll()
    {
    // Given...
    File dir = getTargetDir( getClass());
    File jar = getClassPathJar( "org.hamcrest.collection");
    File file = getResourceFile( getClass(), String.format( "%s.class", getClass().getSimpleName()));
    Finder finder = new Finder().filter( new SimpleAnnotationFilter( Deprecated.class)).inClasses( dir, jar, file);

    // When...
    List<Annotated> annotated = finder.find().collect( toList());

    // Then...
    assertThat(
      "Annotated",
      annotated,
      contains(
        new AnnotatedField( Deprecated.class, "org.cornutum.annotation.ClassDataTest", "stringField", true),
        new AnnotatedClass( Deprecated.class, "org.hamcrest.core.IsCollectionContaining", true),
        new AnnotatedClass( Deprecated.class, "org.hamcrest.collection.IsArrayContainingInAnyOrder", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.collection.IsIn", "isIn", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.collection.IsIn", "isIn", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.collection.IsIn", "isOneOf", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.BaseMatcher", "_dont_implement_Matcher___instead_extend_BaseMatcher_", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.Matcher", "_dont_implement_Matcher___instead_extend_BaseMatcher_", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.text.IsEmptyString", "isEmptyString", true),
        new AnnotatedMethod( Deprecated.class, "org.hamcrest.text.IsEmptyString", "isEmptyOrNullString", true)
        ));
    }
  }
