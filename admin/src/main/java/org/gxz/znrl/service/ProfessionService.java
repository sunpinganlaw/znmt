package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Profession;

import java.util.List;

/**
 * Created by admin-rubbissh on 2014/12/5.
 */
public interface ProfessionService {

    List<Profession> find(Page page, Profession profession);

    void update(Profession profession);

    void delete(Long id);

    void save(Profession profession);

    Page findByPage(Page page, Profession profession);

    List<Profession> findAll();

}
