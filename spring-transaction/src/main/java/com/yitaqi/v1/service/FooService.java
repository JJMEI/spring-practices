package com.yitaqi.v1.service;

import com.yitaqi.v1.model.to.Foo;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public interface FooService {

    /**
     *
     * @param fooName
     * @return
     */
    Foo getFoo(String fooName);

    /**
     *
     * @param fooName
     * @param barName
     * @return
     */
    Foo getFoo(String fooName, String barName);

    /**
     *
     * @param foo
     */
    void insertFoo(Foo foo);

    /**
     *
     * @param foo
     */
    void updateFoo(Foo foo);
}
