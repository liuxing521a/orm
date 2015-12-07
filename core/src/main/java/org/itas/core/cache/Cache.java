package org.itas.core.cache;

/**
 * <p>cache功能定义</p>
 * 规范高速缓存
 * @author liuzhen <liuxing521a@gmail.com>
 */
public interface Cache<K, V> extends java.util.Map<K, V> {

    /**
     * <p>返回cache名称</p>
     * @return cache名称
     */
    String getName();

    /**
     * <p>给cache设置指定名称</p>
     * @param name 要设置的cache名称
     */
    void setName(String name);

    /**
     * <p>cache可用最大内存(以字节为单位)</p>
     * 如果当前cache [使用内存] > [可用内存]，最不常用的项将被删除。如果[最大内存]设置为-1，则没有大小限制
     * @return 缓存的最大容量（以字节为单位）.
     */
    long getMaxCacheSize();

    /**
     * <p>设置的cache最大可用容量（以字节为单位）</p> 
     * 如果当前cache [使用内存] > [可用内存]，最不常用的项将被删除。如果[最大内存]设置为-1，则没有大小限制
     * @param maxSize 占用内存大小（以字节为单位）.
     */
    void setMaxCacheSize(int maxSize);

    /**
     * <p>cache中项的最大保存时间</p>
     * 一旦超过指定的毫秒数，该对象会被cache自动逐出。如果设置的最大寿命为-1，则对象永远不会过期
     * @return 最大存活时间(毫秒)
     */
    long getMaxLifetime();

    /**
     * <p>设置最大存活时间</p>
     * 一旦超过指定的毫秒数，该对象会被cache自动逐出。如果设置的最大寿命为-1，则对象永远不会过期。
     * @param maxLifetime 要设置cache中项最大寿命(毫秒)
     */
    void setMaxLifetime(long maxLifetime);

    /**
     * <p>cache当前容量(以字节为单位)</p>
     * 粗略估计cache当前的大小
     * @return 当前cache大小(字节数).
     */
    int getCacheSize();

    /**
     * <p>返回cache命中次数</p>
     * cache命中次数：每次请求cache包含请求的对象
     * 跟踪的cache命中率和失误，可制定措施如何使cache的命中率更高，更有效
     * @return 命中次数
     */
    long getCacheHits();

    /**
     * <p>返回cache未命中次数</p>
     * cache未命中次数：每次请求cache不包含请求的对象
     *
     * 跟踪的cache命中率和失误，可制定措施如何使cache的命中率更高，更有效
     * @return 未命中次数
     */
    long getCacheMisses();
    
    /**
     * 添加cache监听器
     * @param listener
     */
    void addListenr(DataChangeListener listener);
    
    /**
     * 移除cache监听器
     * @param listener
     */
    void removeLister(DataChangeListener listener);

}
