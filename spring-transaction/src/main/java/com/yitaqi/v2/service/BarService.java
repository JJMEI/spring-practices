package com.yitaqi.v2.service;

import com.yitaqi.v2.model.to.Foo;

/**
 *
 * @author meijunjie
 * @date 2019/5/28
 */
public interface BarService {

    int updateBar(Foo foo);

    int insertBarWithPropagationMandatory(Foo fo);

    int insertBarWithPropagationRequiresNew(Foo fo);

    int insertBarWithPropagationNotSuuported(Foo fo);

    int insertBarWithPropagationNever(Foo fo);
}
