//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Provides methods to handle iterators.
 */
public final class Iterators
  {
  /**
   * Creates a new Iterators instance.
   */
  private Iterators()
    {
    // Static methods only
    }

  /**
   * Returns a stream that produces the sequence defined by the given Iterator.
   */
  public static <T> Stream<T> toStream( Iterator<T> iterator)
    {
    return
      Optional.ofNullable( iterator)
      .map( i -> {
        Iterable<T> iterable = () -> i;
        return toStream( iterable);
        })
      .orElse( null);
    }

  /**
   * Returns a stream that produces the sequence defined by the given Iterable.
   */
  public static <T> Stream<T> toStream( Iterable<T> iterable)
    {
    return
      Optional.ofNullable( iterable)
      .map( i -> StreamSupport.stream( i.spliterator(), false))
      .orElse( null);
    }

  /**
   * Returns a list that contains the sequence defined by the given Iterator.
   */
  public static <T> List<T> toList( Iterator<T> iterator)
    {
    return toStream( iterator).collect( Collectors.toList());
    }

  }
