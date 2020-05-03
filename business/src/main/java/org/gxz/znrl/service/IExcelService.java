package org.gxz.znrl.service;

import java.util.Map;

/**
 * Created by Rubbish on 2015/8/14.
 */
public interface IExcelService {

    public Map<String,Object> exportToExcel(Map<String, Object> map);
}
