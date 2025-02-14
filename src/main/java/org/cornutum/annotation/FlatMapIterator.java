//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import static java.util.Collections.emptyIterator;

/**
 * Performs a "flat map" of a collection of elements of type E to a
 * sequence of objects of type T by applying the {@link #map} method
 * to each element.
 */
public abstract class FlatMapIterator<T,E> implements Iterator<T>
  {
  /**
   * Creates a new FlatMapIterator instance.
   */
  public FlatMapIterator( Collection<E> elements)
    {
    elements_ =
      Optional.ofNullable( elements)
      .map( Collection::iterator)
      .orElse( emptyIterator());
    }

  /**
   * Maps the given element to a sequence of objects of type T.
   */
  protected abstract Iterator<T> map( E element);

  /**
   * Returns true if this element will be included in the mapping.
   */
  protected boolean accept( E element)
    {
    // By default, accept all elements.
    return true;
    }

  public boolean hasNext()
    {
    return
      nextMapping()
      .map( Iterator::hasNext)
      .orElse( false);
    }

  public T next()
    {
    return
      nextMapping()
      .map( Iterator::next)
      .orElseThrow( () -> new NoSuchElementException());
    }

  /**
   * Returns the mapping for the next element.
   */
  private Optional<Iterator<T>> nextMapping()
    {
    for( nextMapping_ = nextMapping_ == null? findNextMapping() : nextMapping_;
         nextMapping_.map( i -> !i.hasNext()).orElse( false);
         nextMapping_ = findNextMapping());      

    return nextMapping_;
    }

  /**
   * Finds the mapping for the next element.
   */
  private Optional<Iterator<T>> findNextMapping()
    {
    E nextElement;
    for( nextElement = null;
         elements_.hasNext() && !accept( nextElement = elements_.next());
         nextElement = null);

    return
      Optional.ofNullable( nextElement)
      .map( e -> map( e));
    }

  private Iterator<E> elements_;
  private Optional<Iterator<T>> nextMapping_;
  }
