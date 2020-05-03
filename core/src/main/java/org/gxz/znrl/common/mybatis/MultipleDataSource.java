package org.gxz.znrl.common.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by vincent on 2014/10/13.
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }

}
