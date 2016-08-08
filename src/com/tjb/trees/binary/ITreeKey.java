package com.tjb.trees.binary;

/**
 * Created by Tim on 31/07/2016.
 */
public interface ITreeKey<V> {

    V getKeyValue();

    boolean lessThan(ITreeKey<V> treeKey);

    boolean greaterThan(ITreeKey<V> treeKey);

    V getMinValue();

    V getMaxValue();

    boolean equals(Object obj);

}
