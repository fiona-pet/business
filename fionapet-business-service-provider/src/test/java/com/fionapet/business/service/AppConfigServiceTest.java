package com.fionapet.business.service;

import cn.fiona.pet.account.entity.User;
import com.fionapet.business.entity.AppConfig;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dubbo.x.service.CURDService;
import org.dubbo.x.service.CURDServiceBase;
import org.junit.Test;
import org.reflections.ReflectionUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTransactionalTestCase;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * Created by tom on 16/11/1.
 */
public class AppConfigServiceTest{
    @Test
    public void testProties(){
        Long toDatatime = 3*30*24*60*60*1000l;
        System.out.println(DateFormatUtils.format(System.currentTimeMillis()+toDatatime,"yyyy-MM-dd"));

        System.out.println(System.currentTimeMillis()+toDatatime);

//        AppConfigService appConfigService = new AppConfigServiceImpl();
//
//
//        Class c = appConfigService.getClass();
//        Set<Field> fs = ReflectionUtils.getAllFields(c);
//        for (Field f :fs){
//            if (f.getType().getInterfaces().length>0) {
////                System.out.println(f.getType().getInterfaces()[0]);
//                if (f.getType().getInterfaces()[0].isAssignableFrom(CURDService.class)) {
//                    User currentUser = new User();
//                    currentUser.setName("abc");
//                    try {
//                        f.setAccessible(true);
//                        BeanUtilsBean.getInstance().setProperty(f,"setCurrentUser", currentUser);
//                        System.out.println(BeanUtilsBean.getInstance().getProperty(appConfigService, "serialNumberService"));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    System.out.println("f=" + f);
//                }
//            }
        }

//        System.out.println(appConfigService.getClass());
//        try {
//            System.out.println(PropertyUtils.describe(appConfigService));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//
//        for (PropertyDescriptor propertyDescriptor: PropertyUtils.getPropertyDescriptors(appConfigService.getClass())) {
//            System.out.println(propertyDescriptor.getPropertyType());
//        }
//    }
}
