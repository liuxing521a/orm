package org.itas.util.cache;


/**
 * <p>计算对象占用内存</p>
 * 一个类实现了Cacheable接口  指示给{@link org.itas.util.cache.Cacheable#getCachedSize()}
 * 的方法,来计算在cache中所占内存（字节数量）
 * @author liuzhen <liuxing521a@gmail.com>
 */
public interface Cacheable {

    /**
     * <p>对象占用的内存空间（以字节为单位）</p>
     *	动态估算的对象占用了多少内存
     * @return 对象占用的内存空间（以字节为单位）
     */
    public int getCachedSize() throws CalculateSizeException;
}
