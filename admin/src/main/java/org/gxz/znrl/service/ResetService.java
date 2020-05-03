package org.gxz.znrl.service;

import org.gxz.znrl.common.mybatis.Page;
import org.gxz.znrl.entity.Reset;

import java.util.List;

/**
 * Created by admin on 2014/8/30.
 */
public interface ResetService {
    void update(Reset reset);
    void save(Reset reset);
    List<Reset> find(Page page, Reset reset);
}
