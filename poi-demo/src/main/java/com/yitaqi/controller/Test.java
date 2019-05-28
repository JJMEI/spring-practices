package com.yitaqi.controller;





import com.google.common.collect.Lists;
import com.yitaqi.util.ExcelUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * Created by meijunjie on 2019/5/27.
 */

@RestController
@RequestMapping(value = "/test")
public class Test {

    @RequestMapping(value = "/test.json")
    public void test(HttpServletResponse response){

        List<String>  headers = Lists.newArrayList("支付日期","订单号","订单类型","退款日期","用户ID","收款公司","业务线","支付渠道","SKU编码","商品名称","第三方流水号","支付金额","华视分成金额","商品类型","渠道来源(cpsno)");


        List<List<Object>> data = Lists.newArrayList();
        for(int i=0;i<100000;i++){
            data.add(Lists.newArrayList(
                    new Date().toLocaleString(),"582594057304875037458","收款",new Date().toLocaleString(),
                    "237489238","天津融创","940","25","532093740827308473","超级家庭会员","1286923764723648273832","499.00","38.00","会员","8394298374928"
            ));
        }

        long s = System.currentTimeMillis();
        ExcelUtil.exportExcel(data,headers,"大屏会员",response);

        System.out.println("导出耗时: " + (System.currentTimeMillis()-s));
    }



}
