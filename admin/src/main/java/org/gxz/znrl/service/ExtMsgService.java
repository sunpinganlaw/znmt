package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.ExtMsg;

import java.util.List;

/**
 * Created by admin on 2014/7/11.
 */
public interface ExtMsgService {

    Page find(Page page, ExtMsg extMsg);

    List<ExtMsg> findList(Page page, ExtMsg extMsg);

    List<ExtMsg> findAll();

    void save(ExtMsg extMsg);

    ExtMsg get(int id);

    void update(ExtMsg extMsg);

    void delete(int id);

}
