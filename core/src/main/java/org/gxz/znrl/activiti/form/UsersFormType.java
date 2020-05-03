package org.gxz.znrl.activiti.form;

import org.activiti.engine.form.AbstractFormType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Created by admin on 2014/7/10.
 */
public class UsersFormType extends AbstractFormType {

    @Override
    public String getName() {
        return "users";
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        String[] split = StringUtils.split(propertyValue, ",");
        return Arrays.asList(split);
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return ObjectUtils.toString(modelValue);
    }

}
