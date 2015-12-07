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
//import java.util.Iterator;
//
//public interface Collection<E> extends Iterable<E> {
//
//    /**
//     * 元素数量<p>
//     *
//     * @return 元素数量
//     */
//    int size();
//
//    /**
//     * 是否空集合<p>
//     *
//     * @return true[空集合] | false[非空集合]
//     */
//    boolean isEmpty();
//
//    /**
//     * 是否包含元素<p>
//     *
//     * @param o 需要检测的元素
//     * @return true[包含指定元素] | false[不包含指定元素]
//     */
//    boolean contains(Object o);
//
//    /**
//     * 元素迭代器<p>
//     *
//     * @return 持有这个集合元素的迭代器
//     */
//    Iterator<E> iterator();
//
//    /**
//     * 返回元素Object数组<p>
//     *
//     * @return 包含这个集合类所有元素的Object数组
//     */
//    Object[] toArray();
//
//    /**
//     * 返回元素指定类型数组<p>
//     * 
//     * @return 包含这个集合类所有元素的指定类型数组
//     */
//    <T> T[] toArray(T[] a);
//
//    // Modification Operations
//
//    /**
//     * 添加元素</p>
//     * 
//     * @param e 要添加的元素
//     * 
//     * @return true[添加成功] | false[添加失败]
//     */
//    boolean add(E e);
//
//    /**
//     * 移除元素</p>
//     *
//     * @param o 要移除的元素
//     * @return true[移除成功] | false[移除失败] 
//     */
//    boolean remove(Object o);
//
//
//    /**
//     * 集合中是否包含另一个集合元素<p>
//     * 
//     * true[包含指定集合中所有元素] | false[不包含指定集合中所有元素]
//     */
//    boolean containsAll(Collection<?> c);
//
//    /**
//     * 添加一个集合中所有元素
//     *
//     * @param c 要添加的集合
//     * @return true[添加成功] | false[添加失败]
//     */
//    boolean addAll(Collection<? extends E> c);
//
//    /**
//     * 移除一个集合中所有元素</p>
//     *
//     * @param c 要移除的集合
//     * @return true[移除成功] | false[移除失败] 
//     */
//    boolean removeAll(Collection<?> c);
//
//
//    /**
//     * 保留指定元素集合
//     *
//     * @param c 需要保留元素集合
//     * @return true[有移除操作] | false[无移除操作] 
//     */
//    boolean retainAll(Collection<?> c);
//
//    /**
//     * 清空元素
//     */
//    void clear();
//
//
//    /**
//     * @see java.lang.Object#equals(Object o)
//     */
//    boolean equals(Object o);
//
//    /**
//     * @see java.lang.Object#hashCode()
//     */
//    int hashCode();
//
//}
