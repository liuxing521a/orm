///*
// * Copyright 2014 xcnet
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.itas.common.collection;
//
//import java.util.Enumeration;
//import java.util.NoSuchElementException;
//
///**
// * 迭代器<p>
// * An iterator over a collection.  {@code Iterator} takes the place of
// * {@link Enumeration} in the Java Collections Framework.  Iterators
// * differ from enumerations in two ways:
// *
// * <ul>
// *      <li> Iterators allow the caller to remove elements from the
// *           underlying collection during the iteration with well-defined
// *           semantics.
// *      <li> Method names have been improved.
// * </ul>
// *
// * @author liuzhen<liuxing521a@163.com>
// * @calendar 2015年5月29日
// */
//public interface Iterator<E> extends java.util.Iterator<E> {
//
//	/**
//     * Returns {@code true} if the iteration has more elements.
//     * (In other words, returns {@code true} if {@link #next} would
//     * return an element rather than throwing an exception.)
//     *
//     * @return {@code true} if the iteration has more elements
//     */
//    boolean hasNext();
//
//    /**
//     * Returns the next element in the iteration.
//     *
//     * @return the next element in the iteration
//     * @throws NoSuchElementException if the iteration has no more elements
//     */
//    E next();
//}
