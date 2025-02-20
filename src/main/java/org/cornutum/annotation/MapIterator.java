//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.annotation;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Maps a sequence of elements of type E to a sequence of objects of type T by applying
 * a specified mapping function.
 */
public class MapIterator<T,E> implements Iterator<T>
  {
  /**
   * Creates a new MapIterator instance.
   */
  public MapIterator( Iterator<E> elements, Function<E,T> mapping)
    {
    elements_ = elements;
    mapping_ = mapping;
    }

  public boolean hasNext()
    {
    return elements_.hasNext();
    }

  public T next()
    {
    return mapping_.apply( elements_.next());
    }
  
  private final Iterator<E> elements_;
  private final Function<E,T> mapping_;
  }
