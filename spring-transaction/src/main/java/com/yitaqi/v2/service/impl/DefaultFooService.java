package com.yitaqi.v2.service.impl;

import com.yitaqi.v2.model.to.Foo;
import com.yitaqi.v2.service.BarService;
import com.yitaqi.v2.service.FooService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public class DefaultFooService implements FooService {



    private BarService barService;

    public void setBarService(BarService barService) {
        this.barService = barService;
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public Foo getFoo(String fooName) {
        barService.insertBarWithPropagationRequiresNew(new Foo());
        throw new UnsupportedOperationException();
    }


    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public Foo getFoo(String fooName, String barName) {

        barService.insertBarWithPropagationNotSuuported(new Foo());
        throw new UnsupportedOperationException();
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public void insertFoo(Foo foo) {
        barService.updateBar(foo);
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateFoo(Foo foo) {
        barService.insertBarWithPropagationMandatory(foo);
        throw new UnsupportedOperationException();
    }




}
