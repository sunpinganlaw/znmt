package org.gxz.znrl.service;

import org.gxz.znrl.entity.Msg;
import org.gxz.znrl.common.mybatis.Page;

import java.util.List;

/**
 * Created by Alex on 2014/9/9.
 */
public interface MsgService {
    Page find(Page page, Msg msg);

    int countByExample(Page page, Msg msg);

    List<Msg> verfyList(Msg msg);

    List<Msg> findList(Page page, Msg msg);

    List<Msg> findAll();

    void save(Msg msg);

    Msg get(Long id);

    void update(Msg msg);

    void delete(Long id);
}
