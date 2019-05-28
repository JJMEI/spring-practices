package com.yitaqi.v1.service.impl;

import com.yitaqi.v1.model.to.Foo;
import com.yitaqi.v1.service.FooService;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public class DefaultFooService implements FooService{
    @Override
    public Foo getFoo(String fooName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Foo getFoo(String fooName, String barName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insertFoo(Foo foo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFoo(Foo foo) {
        throw new UnsupportedOperationException();
    }
}
