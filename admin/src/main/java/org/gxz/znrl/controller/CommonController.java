package org.gxz.znrl.controller;

import org.gxz.znrl.common.baseaction.BaseAction;
import org.gxz.znrl.entity.TableColumnByColumn;
import org.gxz.znrl.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;


/**
 * Created by admin-rubbissh on 2014/12/1.
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/getSequence/{seqName}")
    @ResponseBody
    public long getSequence(@PathVariable String seqName) {
        long retrunStr = 0;
        try {
            retrunStr = commonService.getNextval(seqName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrunStr;
    }

    @RequestMapping(value = "/getTableColumnByColumn/{showCol}/{tableName}/{condCol}/{condValue}/{condOperate}/{condType}/{condCnt}")
    @ResponseBody   //String showCol,String tableName,String condCol,String condValue,String condOperate,String condType,int condCnt
    public String getTableColumnByColumn(@PathVariable String showCol,@PathVariable String tableName,@PathVariable String condCol,@PathVariable String condValue,@PathVariable String condOperate,@PathVariable String condType,@PathVariable int condCnt) {
        String retrunStr = "";
        try {
            TableColumnByColumn tableColumnByColumn = new TableColumnByColumn();
            tableColumnByColumn.setShowCol(showCol);
            tableColumnByColumn.setTableName(tableName);
            tableColumnByColumn.setCondCol(condCol);
            tableColumnByColumn.setCondValue(condValue);
            tableColumnByColumn.setCondOperate(condOperate);
            tableColumnByColumn.setCondType(condType);
            tableColumnByColumn.setCondCnt(condCnt);
            retrunStr = commonService.getTableColumnByColumn(tableColumnByColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrunStr;
    }

    @RequestMapping(value = "/getSysdate")
    @ResponseBody
    public String getSysdate() {
        String retrunStr = "";
        try {
            retrunStr = commonService.getSysdate().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrunStr;
    }
}
