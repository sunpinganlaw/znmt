create or replace package BUSINESS_GROUP_INTF_PCK is

  SENDER_CODE   CONSTANT VARCHAR2(40) := '513'; --发送方固定编码
  RECEIVER_CODE CONSTANT VARCHAR2(40) := '513'; --接收方（海关)固定编码

  BUSI_TYPE CONSTANT VARCHAR2(40) := '1'; --该码头固定为1(集装箱）

  BUSI_TYPE_CAR CONSTANT VARCHAR2(2) := '0'; --散货业务代码

  BUSI_TYPE_BOX CONSTANT VARCHAR2(2) := '1'; --集装箱业务代码

  procedure P_PROCESS_0X21(v_dataInfo in varchar2,
                           v_cmdInfo  out varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2);

  procedure P_PROCESS_0X23(v_dataInfo in varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2);
                                     
  procedure P_PROCESS_GET_LMS(v_resType out varchar2,
                                   v_resXml  out varchar2,
                                   v_resCode  out varchar2,
                                   v_resMsg   out varchar2);       
                                                            

  ---和海关交互报文的主入口
  procedure main(v_reportInfo in varchar2,
                 v_reportAttr out varchar2,
                 v_reportXml  out clob,
                 v_resCode    out varchar2,
                 v_resMsg     out varchar2);

  --- 集装箱报文
  procedure P_INTF_STORAGE(v_reportInfo in json,
                           v_reportAttr out json,
                           v_reportXml  out clob,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2);

  procedure append_xml(v_clob in out clob, v_str varchar2);

  procedure add_TransConta(msgString in clob,
                           v_resCode out varchar2,
                           v_resMsg  out varchar2);

  --- 补打汽车过卡记录
  procedure P_PRINT_RECORD(v_reportInfo in varchar2,
                           v_reportJson out varchar2,
                           v_reportXml  out varchar2,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2);

  procedure P_PROCESS_0X21_BOX(v_dataInfo in varchar2,
                               v_cmdInfo  out varchar2,
                               v_resCode  out varchar2,
                               v_resMsg   out varchar2);

  procedure P_PROCESS_0X21_BOX_BAK(v_dataInfo in varchar2,
                                   v_cmdInfo  out varchar2,
                                   v_resCode  out varchar2,
                                   v_resMsg   out varchar2);

                                   
  procedure make_lms(v_messageType in varchar2,
                                   v_business_id  in varchar2,
                                   v_resCode  out varchar2,
                                   v_resMsg   out varchar2);                                                                
                                   
                                   
end BUSINESS_GROUP_INTF_PCK;
/
create or replace package body BUSINESS_GROUP_INTF_PCK is

  procedure P_PROCESS_0X21(v_dataInfo in varchar2,
                           v_cmdInfo  out varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2) is
  
    iv_AREA_ID           varchar2(30); --场站号
    iv_CHNL_NO           varchar2(30); --通道号
    iv_I_E_TYPE          varchar2(30); --进出场标识:I进场，E出场
    iv_SEQ_NO            varchar2(30); --序列号
    iv_DR_IC_NO          varchar2(30); --IC卡号
    iv_GROSS_WT          varchar2(30); --重量
    iv_FILE_TIME         varchar2(30); --时间
    iv_CONTA_ID_F        varchar2(30); --前箱号
    iv_CONTA_ID_B        varchar2(30); --后箱号
    iv_CAR_EC_NO         varchar2(30); --电子车牌
    iv_VE_NAME           varchar2(30); --车牌号
    iv_BAR_CODE          varchar2(300); --条形码值
    iv_PHOTO_GUID        varchar2(3000); --图片相关
    iv_PHOTO_PERSPECTIVE varchar2(3000); --图片相关
  
    iv_CHK_RESULT     varchar2(30); --放行结果。Y放行，N不放行
    iv_LED_INFO       varchar2(30); --放行提示
    iv_BL_NO          varchar2(30); --提单号
    iv_GOODS_PROPERTY varchar2(30); --内外贸标识（NM/WM）
    iv_IM_CUSTOM      varchar2(30); --是否需要发送给海关（外贸Y,内贸N）
    BUSI_TYPE         varchar2(30);
  
    iv_dataInfo_Json json;
  
    iv_rec_status  varchar2(30);
    iv_trade_type  varchar2(30);
    iv_cnt         number(3);
    iv_cmd_jsonObj json;
    iv_recNo       number(12);
  
    iv_cardID     varchar2(30);
    iv_cardTyp    varchar2(30);
    iv_temWeight  number(19, 6);
    iv_carrierNo  varchar2(30);
    iv_recordNo   number(8);
    iv_timeString varchar2(30);
    iv_sampleType varchar2(30);
    iv_shipId     varchar2(30);
    iv_goodNo     varchar2(30);
    iv_netNow     number(19, 6);
    iv_netAll     number(19, 6);
    iv_vendorNmae varchar2(30);
    iv_vendorNo   varchar2(30);
    iv_goodName   varchar2(30);
    iv_stockArea  varchar2(30);
    iv_pzQTY      number(19, 6);
    iv_mzQTY      number(19, 6);
    iv_netQTY     number(19, 6);
    iv_recodNo    number(8);
    iv_std_qty    number(19, 6);
    vrow          rlrecordmstqy%rowtype;
    iv_customerNo varchar2(30);
  
  begin
    v_resCode  := '0';
    v_resMsg   := 'ok';
    iv_cnt     := 0;
    iv_std_qty := 0;
  
    iv_CHK_RESULT     := '';
    iv_LED_INFO       := '';
    iv_BL_NO          := '';
    iv_GOODS_PROPERTY := '';
    iv_IM_CUSTOM      := '';
  
    iv_CONTA_ID_B  := '';
    iv_CONTA_ID_F  := '';
    iv_AREA_ID     := '';
    iv_CHNL_NO     := '';
    iv_trade_type  := 99;
    BUSI_TYPE      := '99';
    iv_cmd_jsonObj := json();
  
    iv_pzQTY  := 0;
    iv_mzQTY  := 0;
    iv_netQTY := 0;
  
    iv_dataInfo_Json := json(v_dataInfo);
  
    -----计入时间进行测试
    select to_char(sysdate, 'yyyy-MM-dd HH24:mi:ss')
      into iv_timeString
      from dual;
  
    --解析公共部分信息
    begin
      iv_AREA_ID           := json_ext.get_string(iv_dataInfo_Json,
                                                  'AREA_ID');
      iv_CHNL_NO           := json_ext.get_string(iv_dataInfo_Json,
                                                  'CHNL_NO');
      iv_I_E_TYPE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'I_E_TYPE');
      iv_SEQ_NO            := json_ext.get_string(iv_dataInfo_Json,
                                                  'SEQ_NO');
      iv_DR_IC_NO          := json_ext.get_string(iv_dataInfo_Json,
                                                  'DR_IC_NO');
      iv_GROSS_WT          := json_ext.get_string(iv_dataInfo_Json,
                                                  'GROSS_WT');
      iv_FILE_TIME         := json_ext.get_string(iv_dataInfo_Json,
                                                  'FILE_TIME');
      iv_CONTA_ID_F        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_F');
      iv_CONTA_ID_B        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_B');
      iv_CAR_EC_NO         := json_ext.get_string(iv_dataInfo_Json,
                                                  'CAR_EC_NO');
      iv_VE_NAME           := json_ext.get_string(iv_dataInfo_Json,
                                                  'VE_NAME');
      iv_BAR_CODE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'BAR_CODE');
      iv_PHOTO_GUID        := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_GUID');
      iv_PHOTO_PERSPECTIVE := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_PERSPECTIVE');
    
    exception
      when others then
        v_resCode := '1';
        v_resMsg  := '解析汽车21报文异常：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
        return;
    end;
  
    ---车牌未识别,禁行
    if length(iv_VE_NAME) = 0 then
      v_resCode     := '1001';
      v_resMsg      := '车牌未识别';
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      return;
    end if;
  
    --根据汽车注册信息表中查询车辆标准载重信息---
    select nvl(max(a.std_qty) * 1000, 99.99)
      into iv_std_qty
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    select count(1)
      into iv_cnt
      from transmst b
     where b.car_id = iv_VE_NAME
       and b.rec_type = '2';
  
    if iv_cnt > 0 then
      ----如果是集装箱业务直接调用老的集装箱业务处理逻辑
    
      P_PROCESS_0X21_BOX(v_dataInfo, v_cmdInfo, v_resCode, v_resMsg);
      RETURN;
    
    end if;
  
    --根据注册表中查找所属的提单信息
    select nvl(max(a.trade_type), '99'),
           nvl(max(a.tick_qty), 0),
           nvl(max(a.BILL_NO), 99) ，nvl(max(a.SAMPLE_TYPE), 99)
      into iv_trade_type, iv_netAll, iv_BL_NO，iv_sampleType
      from ship_cargo_record a, transmst b
     where b.car_id = iv_VE_NAME
       and a.RECORD_NO = to_number(b.batch_no)
       and b.rec_type = '0'
       and a.rec_status in ('1');
  
    if iv_trade_type not in ('0', '1', '2', '3') then
    
      v_resCode := '1002';
      v_resMsg  := '禁行车辆未备案';
    
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      return;
    end if;
  
    select nvl(max(a.org_no), 99)
      into iv_carrierNo
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    if iv_trade_type in ('0', '1') then
      --外贸
      iv_GOODS_PROPERTY := 'WM';
      iv_IM_CUSTOM      := 'Y';
    
    end if;
  
    if iv_trade_type in ('2', '3') then
      --内贸
      iv_GOODS_PROPERTY := 'NM';
      iv_IM_CUSTOM      := 'Y';
    end if;
  
    select nvl(max(b.GOOD_TYPE), 99)
      into BUSI_TYPE
      from ship_cargo_record b
     where b.BILL_NO = iv_BL_NO;
  
    select nvl(max(a.ship_id), 99), nvl(max(b.good_no), 99)
      into iv_shipId, iv_goodNo
      from ship_record a, ship_cargo_record b
     where a.ship_rec_id = b.ship_rec_id
       and b.BILL_NO = iv_BL_NO;
  
    select sum(B.NET_QTY)
      into iv_netNow
      from RLRECORDMSTQY B
     where B.TICK_NO = iv_BL_NO
       and B.CFM_FLG = 'Y'
       and b.leave_flg = 'Y';
  
    ---客户名称-------------
    select nvl(max(a.VEN_NAME), 99), nvl(max(a.VEN_NO), 99)
      into iv_vendorNmae, iv_vendorNo
      from vendor_info a, ship_cargo_record b
     where a.VEN_NO = b.VEN_NO
       and b.BILL_NO = iv_BL_NO;
  
    ---货物名称名称-------------
    select nvl(max(a.GOOD_NAME), 99), nvl(max(b.STOCK_AREA), 99)
      into iv_goodName, iv_stockArea
      from good_info a, ship_cargo_record b
     where a.GOOD_NO = b.GOOD_NO
       and b.BILL_NO = iv_BL_NO;
  
    ---收货单位编号------------
    select nvl(max(b.CUSTOMER_NO), 99)
      into iv_customerNo
      from ship_cargo_record b
     where b.BILL_NO = iv_BL_NO;
  
    --Add by jiaruya:提单公共信息
    iv_cmd_jsonObj.put('OWNER', iv_vendorNmae); --货主
    iv_cmd_jsonObj.put('GOOD_TYPE', iv_goodName); --煤种
    iv_cmd_jsonObj.put('POSITION', iv_stockArea); --堆位
    iv_cmd_jsonObj.put('BL_WEIGHT', to_char(iv_netAll / 1000)); --提单总重量
  
    --End
  
    begin
      ---事务统一管理
    
      if length(iv_DR_IC_NO) > 0 THEN
        iv_cardID  := iv_DR_IC_NO;
        iv_cardTyp := 'IC';
      END IF;
    
      if length(iv_CAR_EC_NO) > 0 THEN
        iv_cardID  := iv_CAR_EC_NO;
        iv_cardTyp := 'IEC';
      END IF;
    
      if length(iv_BAR_CODE) > 0 THEN
        iv_cardID  := iv_BAR_CODE;
        iv_cardTyp := 'BAR';
      END IF;
    
      ---进入散货业务，直接将类型写死为散货业务
      iv_cardTyp := BUSI_TYPE_CAR;
    
      ---进场通道 如果重复的序列号 要求能删除原来车辆信息
      if iv_I_E_TYPE = 'I' then
        select nvl(max(a.record_no), 99)
          into iv_recordNo
          from rlrecordmstqy a
         where a.in_no = iv_SEQ_NO
           and a.INSERT_TIME >= trunc(sysdate - 1);
      
        if iv_recordNo != 99 then
          delete from rlrecordmstqy a where a.record_no = iv_recordNo;
          commit;
        end if;
      end if;
    
      ---如果是散货【直提】业务 宁德码头默认是【出库】业务
      if iv_sampleType = '2' then
      
        iv_sampleType := '0';
        /* iv_cmd_jsonObj.put('DIRECT', '1'); --直提业务*/
      
      else
      
        iv_cardTyp := BUSI_TYPE_CAR;
      end if;
    
      ----进场车辆，并且是卸货类型（重车进入）----------------------------
      if iv_I_E_TYPE = 'I' and iv_sampleType = '1' then
      
        /*---从上传的excel条码信息中找一个可用的条码信息-------------
        select nvl(max(a.point_tag), '99')
          into iv_BAR_CODE
          from point_cfg a, ship_cargo_record b
         where a.point_name = b.RECORD_NO
           and a.STATUS = '0'
           and b.BILL_NO = iv_BL_NO;
        
        if iv_BAR_CODE = '99' then
        
          v_resCode := '1002';
          v_resMsg  := '无可用的条码信息';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;*/
      
        iv_recodNo := SEQ_RLRECORDMSTQY.NEXTVAL;
      
        insert into rlrecordmstqy
          (record_no, --TODO 待确认 入厂流水号哪里输入
           TICK_NO， CARD_TYP,
           CARD_ID,
           car_id,
           VEN_NO,
           CUSTOMER_NO,
           ship_id,
           good_no,
           CARRIER_NO,
           MZ_QTY,
           CZ_BALANCE_NO,
           CZ_DTM,
           IN_DOOR_NO,
           IN_NO,
           STATION_NO,
           CONTA_ID_F,
           CONTA_ID_B,
           leave_flg,
           insert_time,
           RECORD_DTM,
           op_code)
        values
          (iv_recodNo, --TODO 待确认 入厂流水号
           iv_BL_NO,
           iv_cardTyp,
           iv_BAR_CODE,
           iv_VE_NAME,
           iv_vendorNo,
           iv_customerNo,
           iv_shipId,
           iv_goodNo， iv_carrierNo,
           to_number(iv_GROSS_WT),
           iv_CHNL_NO,
           to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
           iv_CHNL_NO,
           iv_SEQ_NO,
           iv_AREA_ID,
           iv_CONTA_ID_F,
           iv_CONTA_ID_B,
           '0',
           sysdate,
           sysdate,
           '1');
      end if;
    
      ----出场车辆，并且是卸货类型（轻车出去）----------------------------
      if iv_I_E_TYPE = 'E' and iv_sampleType = '1' then
      
        select nvl(max(a.record_no), 0)
          into iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
           and a.leave_flg in ('0', 'N');
      
        select nvl(max(a.MZ_QTY), 0), nvl(max(a.CARD_ID), 99)
          into iv_temWeight, iv_BAR_CODE
          from RLRECORDMSTQY a
         where a.record_no = iv_recNo;
      
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := '没有进场记录';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        end if;
      
        if (iv_temWeight - to_number(iv_GROSS_WT)) <= 0 then
          v_resCode := '202';
          v_resMsg  := '重量类型不符，出卡重量大于进卡重量';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        if (iv_netAll - iv_netNow) <
           (iv_temWeight - to_number(iv_GROSS_WT)) then
        
          /*  update ship_cargo_record a
            set REC_STATUS = '3', fact_time = sysdate
          where a.BILL_NO = iv_BL_NO;*/
        
          v_resCode := '203';
          v_resMsg  := '超出提单剩余核销重量';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          commit;
          return;
        end if;
      
        update RLRECORDMSTQY
           set PZ_QTY        = to_number(iv_GROSS_WT),
               NET_QTY       = iv_temWeight - to_number(iv_GROSS_WT),
               JQ_BALANCE_NO = iv_CHNL_NO,
               JQ_DTM        = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
               OUT_DOOR_NO   = iv_CHNL_NO,
               OUT_NO        = iv_SEQ_NO,
               update_code   = '1',
               update_time   = sysdate
         where record_no = iv_recNo;
      
        ----直接结束提单（剩余重量小于100）----
        if (iv_netAll - iv_netNow + to_number(iv_GROSS_WT) - iv_temWeight) = 0 then
          update ship_cargo_record a
             set REC_STATUS = '3', fact_time = sysdate
           where a.BILL_NO = iv_BL_NO;
        
        end if;
      
        iv_recodNo := iv_recNo;
      
      end if;
    
      ----进场车辆，并且是拉类型（轻车进入）----------------------------
    
      if iv_I_E_TYPE = 'I' and iv_sampleType = '0' then
      
        /*  ---从上传的excel条码信息中找一个可用的条码信息-------------
        select nvl(max(a.point_tag), '99')
          into iv_BAR_CODE
          from point_cfg a, ship_cargo_record b
         where a.point_name = b.RECORD_NO
           and a.STATUS = '0'
           and b.BILL_NO = iv_BL_NO;
        
        if iv_BAR_CODE = '99' then
        
          v_resCode := '1002';
          v_resMsg  := '无可用的条码信息';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;*/
      
        iv_recodNo := SEQ_RLRECORDMSTQY.NEXTVAL;
        insert into rlrecordmstqy
          (record_no, --TODO 待确认 入厂流水号哪里输入
           TICK_NO， CARD_TYP,
           CARD_ID,
           car_id,
           VEN_NO,
           CUSTOMER_NO,
           ship_id,
           good_no,
           CARRIER_NO,
           PZ_QTY,
           JQ_BALANCE_NO,
           JQ_DTM,
           IN_DOOR_NO,
           IN_NO,
           STATION_NO,
           CONTA_ID_F,
           CONTA_ID_B,
           leave_flg,
           insert_time,
           RECORD_DTM,
           op_code)
        values
          (iv_recodNo, --TODO 待确认 入厂流水号
           iv_BL_NO,
           iv_cardTyp,
           iv_BAR_CODE,
           iv_VE_NAME,
           iv_vendorNo,
           iv_customerNo,
           iv_shipId,
           iv_goodNo,
           iv_carrierNo,
           to_number(iv_GROSS_WT),
           iv_CHNL_NO,
           to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
           iv_CHNL_NO,
           iv_SEQ_NO,
           iv_AREA_ID,
           iv_CONTA_ID_F,
           iv_CONTA_ID_B,
           '0',
           sysdate,
           sysdate,
           '1');
      
      end if;
    
      ----出场车辆，并且是拉类型（重车出去）----------------------------
      if iv_I_E_TYPE = 'E' and iv_sampleType = '0' then
      
        select nvl(max(a.record_no), 0)
          into iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
           and a.leave_flg in ('0', 'N');
      
        select nvl(max(a.PZ_QTY), 0), nvl(max(a.CARD_ID), 99)
          into iv_temWeight, iv_BAR_CODE
          from RLRECORDMSTQY a
         where a.record_no = iv_recNo;
      
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := '没有进场记录';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        end if;
      
        if (to_number(iv_GROSS_WT) - iv_temWeight) <= 0 then
          v_resCode := '202';
          v_resMsg  := '出卡重量小于进卡重量';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        if (iv_netAll - iv_netNow) <
           (to_number(iv_GROSS_WT) - iv_temWeight) then
        
          v_resCode := '203';
          v_resMsg  := '超出提单剩余核销重量';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
        
          commit;
          return;
        end if;
      
        ----不容许超载拉货出去-------
        if to_number(iv_GROSS_WT) > iv_std_qty then
          v_resCode := '204';
          v_resMsg  := '车辆拉货大于标准载重';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        update RLRECORDMSTQY
           set MZ_QTY        = to_number(iv_GROSS_WT),
               NET_QTY       = to_number(iv_GROSS_WT) - iv_temWeight,
               CZ_BALANCE_NO = iv_CHNL_NO,
               CZ_DTM        = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
               OUT_DOOR_NO   = iv_CHNL_NO,
               OUT_NO        = iv_SEQ_NO,
               STATION_NO    = iv_AREA_ID,
               CONTA_ID_F    = iv_CONTA_ID_F,
               CONTA_ID_B    = iv_CONTA_ID_B,
               CARD_ID       = iv_cardID,
               CARD_TYP      = iv_cardTyp,
               update_code   = '1',
               update_time   = sysdate
         where record_no = iv_recNo;
      
        iv_recodNo := iv_recNo;
      
        ----直接结束提单（剩余重量小于100）----
        if (iv_netAll - iv_netNow - to_number(iv_GROSS_WT) + iv_temWeight) = 0 then
          update ship_cargo_record a
             set REC_STATUS = '3', fact_time = sysdate
           where a.BILL_NO = iv_BL_NO;
        
        end if;
      
      end if;
    
      ---先提交一下重量信息----
      commit;
    
      ---再查一下这次的过磅信息----
      select *
        into vrow
        from rlrecordmstqy i
       where i.RECORD_NO = iv_recodNo;
    
      select sum(B.NET_QTY)
        into iv_netNow
        from RLRECORDMSTQY B
       where B.TICK_NO = iv_BL_NO;
    
      --Add by jiaruya     重量信息
      iv_cmd_jsonObj.put('BARE_WEIGHT', to_char(vrow.pz_qty / 1000));
      iv_cmd_jsonObj.put('GROSS_WEIGHT', to_char(vrow.mz_qty / 1000));
      iv_cmd_jsonObj.put('NET_WEIGHT', to_char(vrow.net_qty / 1000));
    
      iv_cmd_jsonObj.put('LEAVE_WEIGHT',
                         to_char((iv_netAll - iv_netNow) / 1000)); --提单剩余总重量
      iv_cmd_jsonObj.put('BAR_CODE', iv_BAR_CODE); --条码信息      
      --End
    
      iv_cmd_jsonObj.put('CHK_RESULT', 'Y');
      iv_cmd_jsonObj.put('LED_INFO', '正常通行');
      iv_cmd_jsonObj.put('BL_NO', iv_BL_NO);
      iv_cmd_jsonObj.put('GOODS_PROPERTY', iv_GOODS_PROPERTY);
      iv_cmd_jsonObj.put('IM_CUSTOM', iv_IM_CUSTOM);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', 'Y', v_cmdInfo, v_dataInfo);
    
      --将条码信息更新为已使用
      update point_cfg set STATUS = '1' where POINT_TAG = iv_BAR_CODE;
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理21报文失败：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      
        return;
    end;
  
    commit;
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;

  procedure P_PROCESS_0X23(v_dataInfo in varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2) is
    iv_AREA_ID        varchar2(30); --场站号
    iv_CHNL_NO        varchar2(30); --通道号
    iv_I_E_TYPE       varchar2(30); --进出场标识:I进场，E出场
    iv_SEQ_NO         varchar2(30); --序列号
    iv_DR_IC_NO       varchar2(30); --IC卡号
    iv_EXECUTE_RESULT varchar2(30); --前端卡口执行结果
    iv_ERROR_INFO     varchar2(180); --故障信息
    iv_FILE_TIME      varchar2(30); --时间
  
    iv_dataInfo_Json json;
    iv_cnt           number(3);
    
    iv_lms_Code      varchar2(30);
    iv_lms_Msg       varchar2(2048);
  begin
  
    v_resCode        := '0';
    v_resMsg         := 'ok';
    iv_cnt           := 0;
    iv_dataInfo_Json := json(v_dataInfo);
  
    --解析公共部分信息
    begin
    
      iv_AREA_ID        := json_ext.get_string(iv_dataInfo_Json, 'AREA_ID');
      iv_CHNL_NO        := json_ext.get_string(iv_dataInfo_Json, 'CHNL_NO');
      iv_I_E_TYPE       := json_ext.get_string(iv_dataInfo_Json, 'I_E_TYPE');
      iv_SEQ_NO         := json_ext.get_string(iv_dataInfo_Json, 'SEQ_NO');
      iv_DR_IC_NO       := json_ext.get_string(iv_dataInfo_Json, 'DR_IC_NO');
      iv_FILE_TIME      := json_ext.get_string(iv_dataInfo_Json,
                                               'FILE_TIME');
      iv_EXECUTE_RESULT := json_ext.get_string(iv_dataInfo_Json,
                                               'EXECUTE_RESULT');
      iv_ERROR_INFO     := json_ext.get_string(iv_dataInfo_Json,
                                               'ERROR_INFO');
    
    exception
      when others then
        v_resCode := '1';
        v_resMsg  := '解析汽车23报文异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    begin
      ---事务统一管理
    
      if iv_I_E_TYPE = 'I' then
        select count(1)
          into iv_cnt
          from rlrecordmstqy a
         where a.IN_NO = iv_SEQ_NO;
      
        if iv_cnt = 0 then
          v_resCode := '1';
          v_resMsg  := iv_SEQ_NO || '找不到进场记录';
          pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
          return;
        end if;
      
        update RLRECORDMSTQY
           set CFM_FLG         = iv_EXECUTE_RESULT,
               ATTACH_BATCH_NO = iv_ERROR_INFO,
               RECORD_DTM      = to_date(iv_FILE_TIME,
                                         'yyyy-mm-dd hh24:mi:ss')
         where IN_NO = iv_SEQ_NO;
      
      end if;
    
      if iv_I_E_TYPE = 'E' then
        select count(1)
          into iv_cnt
          from rlrecordmstqy a
         where a.OUT_NO = iv_SEQ_NO;
      
        if iv_cnt = 0 then
          v_resCode := '1';
          v_resMsg  := iv_SEQ_NO || '找不到出场记录';
          pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
          return;
        end if;
      
        update RLRECORDMSTQY
           set LEAVE_FLG = iv_EXECUTE_RESULT,
               REMARK    = iv_ERROR_INFO,
               LEAVE_DTM = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss')
         where OUT_NO = iv_SEQ_NO;
         
        --- 调用make_lms存储过程
        pro_exe_log('P_PROCESS_0X23_LMS202', v_resCode, v_resMsg, v_dataInfo);
        make_lms('LMS202',iv_SEQ_NO, iv_lms_Code, iv_lms_Msg);
         
      end if;
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理23报文失败：' || substr(sqlerrm, 1, 100);
        pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
        return;
    end;
    pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
    commit;
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
      pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
  end;
  
  
  
  ---查询堆存报文  
  procedure P_PROCESS_GET_LMS(v_resType out varchar2,
                              v_resXml out varchar2,
                              v_resCode out varchar2,
                              v_resMsg out varchar2) is
    iv_cnt         number(3);
    iv_bussiness_id varchar2(128);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
    iv_cnt    := 0;
    ---查询是否有堆存报文
    select count(1) into iv_cnt from INTF_STORAGE_LMS where UP_STATE = 0;
    if iv_cnt = 0 then
          v_resCode := '1';
          v_resMsg  := '未查询到堆存报文';
          return;
    end if;
    begin
      ---查询堆存报文
      select a.bussiness_id, a.message_type, a.content 
        into iv_bussiness_id, v_resType, v_resXml   
        from INTF_STORAGE_LMS a
      where a.up_state = 0 order by a.insert_time desc;
      ---更新上传状态
      update INTF_STORAGE_LMS a set a.up_state = 1, a.up_time = sysdate where a.bussiness_id = iv_bussiness_id;
      commit;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理堆存报文失败：' || substr(sqlerrm, 1, 100);
        return;
    end;         
  end;
  

  ---和海关交互报文的主入口

  procedure MAIN(v_reportInfo in varchar2,
                 v_reportAttr out varchar2,
                 v_reportXml  out clob,
                 v_resCode    out varchar2,
                 v_resMsg     out varchar2) is
    iv_reportInfoJson  json; --备用
    iv_reportParamJSON json; --实际传参数
    iv_reportAttrJson  json;
    iv_reportCode      varchar2(30);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    iv_reportInfoJson := json(v_reportInfo);
  
    iv_reportCode      := json_ext.get_string(iv_reportInfoJson,
                                              'reportCode');
    iv_reportParamJSON := json_ext.get_json(iv_reportInfoJson,
                                            'reportParam');
  
    if iv_reportCode = 'intf_storage' then
      P_INTF_STORAGE(iv_reportParamJSON,
                     iv_reportAttrJson,
                     v_reportXml,
                     v_resCode,
                     v_resMsg);
      v_reportAttr := iv_reportAttrJson.to_char();
    
    end if;
  
  exception
    when others then
      null;
  end;

  --集装箱报文
  procedure P_INTF_STORAGE(v_reportInfo in json,
                           v_reportAttr out json,
                           v_reportXml  out clob,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2) is
  
    iv_priKeyJsonList json_list;
    v_business_id     varchar2(50);
    vrow              intf_storage%rowtype;
    v_taskTime        varchar2(20);
  begin
    v_resCode         := '0';
    v_resMsg          := 'ok';
    iv_priKeyJsonList := json_list();
    v_reportXml       := ' ';
    v_reportAttr      := json();
    v_taskTime        := to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss');
  
    --到对应表里面取最早生成的数据（同一个BussinessId)
    begin
      select business_id
        into v_business_id
        from (select business_id
                from intf_storage i
               where i.up_state in ('C')
               order by i.gen_time)
       where rownum < 2;
      select *
        into vrow
        from intf_storage i
       where i.business_id = v_business_id
         and rownum < 2;
    exception
      when others then
        v_resCode := 2;
        v_resMsg  := 'nodata';
        return;
    end;
    --构造xml报文，在v_reportXml变量
  
    --可以出来多条数据
    iv_priKeyJsonList.add_elem(v_business_id);
    v_reportAttr.put('reportCode', 'intf_storage');
    v_reportAttr.put('priKeyList', iv_priKeyJsonList);
  
    --XML头部部分
    append_xml(v_reportXml, '<?xml version="1.0" encoding="UTF-8" ?>');
    append_xml(v_reportXml,
               '<Signature xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">');
    append_xml(v_reportXml, '<Object>');
    append_xml(v_reportXml, '<Package>');
    append_xml(v_reportXml, '<EnvelopInfo>');
    append_xml(v_reportXml, '<version>1.0</version>');
    append_xml(v_reportXml,
               '<business_id>' || v_business_id || '</business_id>');
  
    append_xml(v_reportXml,
               '<message_id>' || vrow.message_id || '</message_id>');
    append_xml(v_reportXml,
               '<message_type>' || vrow.message_type || '</message_type>');
    append_xml(v_reportXml,
               '<business_type>' || vrow.bussiness_type ||
               '</business_type>');
    append_xml(v_reportXml, '<sender_id>' || SENDER_CODE || '</sender_id>');
    append_xml(v_reportXml,
               '<receiver_id>' || RECEIVER_CODE || '</receiver_id>');
    append_xml(v_reportXml,
               '<send_time>' || to_char(vrow.gen_time, 'yyyymmddhh24miss') ||
               '</send_time>');
  
    append_xml(v_reportXml, '</EnvelopInfo>');
  
    append_xml(v_reportXml, '<DataInfo>');
  
    --XML内容具体（存在多条记录，但是这些记录对应的v_business_id是同一个，具体的个数在后台生成的时候可以配置）
    for cur in (select *
                  from intf_storage t
                 where t.BUSINESS_ID = v_business_id
                   and t.up_state in ('C')) loop
      append_xml(v_reportXml, '<StorageQuery>');
      append_xml(v_reportXml, '<bol>' || cur.BOL_NO || '</bol>'); ---
      append_xml(v_reportXml, '<turnCus>' || '999' || '</turnCus>'); ---
      append_xml(v_reportXml, '<bolNo>' || cur.BOL_NO || '</bolNo>'); ---
      append_xml(v_reportXml,
                 '<containerNo>' || cur.CONTAINER_NO || '</containerNo>'); ---
      append_xml(v_reportXml, '<etpsNo>' || '999' || '</etpsNo>'); ---
      append_xml(v_reportXml,
                 '<voyNameNo>' || cur.VOY_NAME || '</voyName>');
      append_xml(v_reportXml, '<voyNo>' || cur.VOY_NO || '</voyNo>');
      append_xml(v_reportXml, '<impexp>' || cur.IMPEXP || '</impexp>');
      append_xml(v_reportXml, '<inout>' || cur.INOUT || '</inout>');
      append_xml(v_reportXml, '<EF>' || cur.EF || '</EF>');
      append_xml(v_reportXml,
                 '<storagePosition>' || cur.STORAGE_POSITION ||
                 '</storagePosition>');
      append_xml(v_reportXml,
                 '<approveTime>' || to_char(cur.gen_time, 'yyyy-mm-dd') ||
                 '</approveTime>'); ---
      append_xml(v_reportXml, '<ifAlarmveTime>' || '999' || '</ifAlarm>'); -----
      append_xml(v_reportXml, '<uuid>' || '999' || '</uuid>'); -----
      append_xml(v_reportXml, '<sizeNo>' || cur.SIZE_NO || '</sizeNo>');
      append_xml(v_reportXml,
                 '<containerType>' || cur.CONTAINER_TYPE ||
                 '</containerType>');
      append_xml(v_reportXml,
                 '<containerWeight>' || cur.CONTAINER_WEIGHT ||
                 '</containerWeight>');
      append_xml(v_reportXml, '<slno>' || cur.SLNO || '</slno>');
      append_xml(v_reportXml, '<gdsType>' || cur.GDS_TYPE || '</gdsType>');
      append_xml(v_reportXml, '<sameVoy>' || cur.SAME_VOY || '</sameVoy>');
      append_xml(v_reportXml,
                 '<outLet>' || cur.SUPERVISION_CODE || '</outLet>');
      append_xml(v_reportXml, '<e>' || '999' || '</e>'); ----
      append_xml(v_reportXml, '<f>' || '999' || '</f>'); ----
      append_xml(v_reportXml, '<total>' || '999' || '</total>'); ----
      append_xml(v_reportXml,
                 '<supervisionCode>' || cur.SUPERVISION_CODE ||
                 '</supervisionCode>');
      append_xml(v_reportXml,
                 '<supervisionName>' || cur.SUPERVISION_NAME ||
                 '</supervisionName>');
      append_xml(v_reportXml,
                 '<operationTime>' ||
                 to_char(cur.OPERATION_TIME, 'yyyy-mm-dd hh24:mi:ss') ||
                 '</operationTime>');
      append_xml(v_reportXml,
                 '<messageType>' || cur.MESSAGE_TYPE || '</messageType>');
    
      append_xml(v_reportXml, '</StorageQuery>');
    end loop;
  
    append_xml(v_reportXml, '</DataInfo>');
    append_xml(v_reportXml, '</Package>');
    append_xml(v_reportXml, '</Objet>');
    append_xml(v_reportXml, '</Signature>');
  
    v_reportXml := trim(v_reportXml);
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;
  
      ---堆存组装存储过程
  procedure make_lms(v_messageType in varchar2,
                           v_business_id in varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2) is
    iv_supervisionCode        varchar2(30); --监管场所UUID
    iv_supervisionName        varchar2(100); --监管场所名称
    iv_areaPositionName       varchar2(30); --场位号（SHIP_CARGO_RECORD.STORAGE_NAME）
    iv_name                   varchar2(30); --跺位名称（SHIP_CARGO_RECORD.STOCK_NAME）
    iv_goodsType              varchar2(30); --货种（GOOD_INFO.GOOD_NAME）
    iv_quantity               varchar2(30); --数量，千克（RLRECORDMSTQY.NET_QTY）
    iv_position               varchar2(30); --跺位位置（SHIP_CARGO_RECORD.STORAGE_NAME）
    iv_style                  varchar2(30); --？？？？（可不填）
    iv_property               varchar2(30); --货物属性，内贸外贸(SHIP_CARGO_RECORD.TRADE_TYPE）
    iv_companyId              varchar2(30); --？？？？（可不填）
    iv_bol                    varchar2(30); --？？？？（可不填）
    iv_bolNo                  varchar2(30); --提单号（RLRECORDMSTQY.TICK_NO）
    iv_height                 varchar2(30); --高度（SHIP_CARGO_RECORD.STOCK_HEIGHT）
    iv_voyName                varchar2(30); --船名（SHIP_INFO.SHIP_NAME）
    iv_voyNo                  varchar2(30); --航次（SHIP_RECORD.WAYBILL_NO）
    iv_berthingTime           varchar2(30); --靠泊时间（SHIP_CARGO_RECORD.FACT_TIME）
    iv_consignor              varchar2(30); --？？？？（可不填）
    iv_receicer               varchar2(30); --？？？？（可不填）
    iv_impexp                 varchar2(30); --进出口（SHIP_CARGO_RECORD.TRADE_TYPE）
    iv_inout                  varchar2(30); --内外贸（SHIP_CARGO_RECORD.TRADE_TYPE）
    iv_operationTime          varchar2(30); --操作时间（RLRECORDMSTQY.NET_QTY）
    iv_messageType            varchar2(30); --作业类型
    iv_storageCode            varchar2(30); --堆场代码（SHIP_CARGO_RECORD.STORAGE_CODE）
    
    iv_trade_type             varchar2(30);   --贸易类型
    iv_LMS_XML                varchar2(2048); --堆存报文
    pro_date                  date;           --当前时间
   
    
  begin
  
    v_resCode        := '0';
    v_resMsg         := 'ok';
    iv_LMS_XML       := ' '; 
    
    --公共信息
    iv_supervisionCode  := 'CNZGW350166';
    iv_supervisionName  := '宁德漳湾8.9号码头作业管理系统';
    
    --堆存报文头
    append_xml(iv_LMS_XML, '<?xml version="1.0" encoding="UTF-8" ?>');
    append_xml(iv_LMS_XML, '<Signature xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">');
    append_xml(iv_LMS_XML, '<SignatureValue/>');
    append_xml(iv_LMS_XML, '<KeyInfo>');
    append_xml(iv_LMS_XML, '<KeyName>' || 'aa' || '</KeyName>');
    append_xml(iv_LMS_XML, '</KeyInfo>');
    append_xml(iv_LMS_XML, '<Object>');
    append_xml(iv_LMS_XML, '<Package>');
    append_xml(iv_LMS_XML, '<EnvelopInfo>');
    append_xml(iv_LMS_XML, '<version>1.0</version>');
    append_xml(iv_LMS_XML, '<business_id>' || v_business_id || '</business_id>');
    append_xml(iv_LMS_XML, '<message_id>' || v_business_id || '</message_id>');
    append_xml(iv_LMS_XML, '<message_type>' || v_messageType || '</message_type>');
    append_xml(iv_LMS_XML, '<business_type>' || v_messageType || '</business_type>');
    append_xml(iv_LMS_XML, '<sender_id>' || iv_supervisionCode || '</sender_id>');
    append_xml(iv_LMS_XML, '<receiver_id>' || 'aaaa' || '</receiver_id>');
    begin    
      select sysdate into pro_date from dual;
      append_xml(iv_LMS_XML, '<send_time>' || to_char(pro_date,'yyyymmddhh24miss') || '</send_time>');
    end;
    append_xml(iv_LMS_XML, '</EnvelopInfo>');
    append_xml(iv_LMS_XML, '<DataInfo>');
    
    
   --查找LMS101堆存数据信息 
   
   --查找LMS102堆存数据信息 
   
   --查找LMS201堆存数据信息 
   
   --查找LMS202堆存数据信息 
    begin
      if v_messageType = 'LMS202' then
        select a.tick_no, a.net_qty, a.leave_dtm,
          b.storage_name, b.storage_code, b.stock_name, b.storage_name, b.stock_height,b.trade_type, b.fact_time,
          c.good_name,
          d.waybill_no,
          e.ship_name
        
          into iv_bolNo,iv_quantity,iv_operationTime,
          iv_areaPositionName,iv_storageCode,iv_name,iv_position,iv_height,iv_trade_type,iv_berthingTime,
          iv_goodsType,
          iv_voyNo,
          iv_voyName
  
          from RLRECORDMSTQY a, SHIP_CARGO_RECORD b, GOOD_INFO c, ship_record d, ship_info e
        where a.tick_no = b.bill_no 
          and b.good_no = c.good_no
          and b.ship_rec_id = d.ship_rec_id
          and d.ship_id = e.ship_id
          and a.out_no = v_business_id;
      end if;
      
       if v_messageType = 'LMS201' then
        select b.BILL_NO,  to_char( b.tick_qty), b.insert_time,
          b.storage_name, b.storage_code, b.stock_name, b.storage_name, b.stock_height,b.trade_type, b.fact_time,
          c.good_name,
          d.waybill_no,
          e.ship_name
          into iv_bolNo,iv_quantity,iv_operationTime,
          iv_areaPositionName,iv_storageCode,iv_name,iv_position,iv_height,iv_trade_type,iv_berthingTime,
          iv_goodsType,
          iv_voyNo,
          iv_voyName
          from  SHIP_CARGO_RECORD b, GOOD_INFO c, ship_record d, ship_info e
        where b.good_no = c.good_no
          and b.ship_rec_id = d.ship_rec_id
          and d.ship_id = e.ship_id
          and b.RECORD_NO = v_business_id;
      end if;
      
      
      
      
      
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理堆存报文失败：' || substr(sqlerrm, 1, 100);
        return;
    end;
    --贸易类型匹配
    if iv_trade_type in ('0') then
       iv_property := 'WM';      --外贸
       iv_impexp   := 'I';       --进口
       iv_inout    := 'O';       --外贸
    end if;
    if iv_trade_type in ('1') then
       iv_property := 'WM';      --外贸
       iv_impexp   := 'O';       --出口
       iv_inout    := 'O';       --外贸
    end if;
    if iv_trade_type in ('2') then
       iv_property := 'NM';      --内贸
       iv_impexp   := 'I';       --进口
       iv_inout    := 'I';       --内贸
    end if;
    if iv_trade_type in ('3') then
       iv_property := 'NM';      --内贸
       iv_impexp   := 'O';       --出口
       iv_inout    := 'I';       --内贸
    end if;
    --组建LMS101堆存报文DataInfo
    
    --组建LMS102堆存报文DataInfo
    
    --组建LMS201堆存报文DataInfo
   
    --组建LMS202堆存报文DataInfo
    if v_messageType in( 'LMS201','LMS202') then
      append_xml(iv_LMS_XML, '<AreaStoragePosition>');
      append_xml(iv_LMS_XML, '<supervisionCode>' || iv_supervisionCode || '</supervisionCode>');
      append_xml(iv_LMS_XML, '<supervisionName>' || iv_supervisionName || '</supervisionName>');
      append_xml(iv_LMS_XML, '<areaPositionName>' || iv_areaPositionName || '</areaPositionName>');
      append_xml(iv_LMS_XML, '<name>' || iv_name || '</name>');
      append_xml(iv_LMS_XML, '<goodsType>' || iv_goodsType || '</goodsType>');
      append_xml(iv_LMS_XML, '<quantity>' || iv_quantity || '</quantity>');
      append_xml(iv_LMS_XML, '<position>' || iv_position || '</position>');
      append_xml(iv_LMS_XML, '<style>' || '' || '</style>');
      append_xml(iv_LMS_XML, '<property>' || iv_property || '</property>');
      append_xml(iv_LMS_XML, '<companyId>' || '' || '</companyId>');
      append_xml(iv_LMS_XML, '<bol>' || '' || '</bol>');
      append_xml(iv_LMS_XML, '<bolNo>' || iv_bolNo || '</bolNo>');
      append_xml(iv_LMS_XML, '<height>' || iv_height || '</height>');
      append_xml(iv_LMS_XML, '<voyName>' || iv_voyName || '</voyName>');
      append_xml(iv_LMS_XML, '<voyNo>' || iv_voyNo || '</voyNo>');
      append_xml(iv_LMS_XML, '<berthingTime>' || iv_berthingTime || '</berthingTime>');
      append_xml(iv_LMS_XML, '<consignor>' || '' || '</consignor>');
      append_xml(iv_LMS_XML, '<receicer>' || '' || '</receicer>');
      append_xml(iv_LMS_XML, '<impexp>' || iv_impexp || '</impexp>');
      append_xml(iv_LMS_XML, '<inout>' || iv_inout || '</inout>');
      append_xml(iv_LMS_XML, '<operationTime>' || iv_operationTime || '</operationTime>');
      append_xml(iv_LMS_XML, '<messageType>' || v_messageType || '</messageType>');
      append_xml(iv_LMS_XML, '<storageCode>' || iv_storageCode || '</storageCode>');
      append_xml(iv_LMS_XML, '</AreaStoragePosition>');
    end if;
    
    --堆存报文尾
    append_xml(iv_LMS_XML, '</DataInfo>');
    append_xml(iv_LMS_XML, '</Package>');
    append_xml(iv_LMS_XML, '</Object>');
    append_xml(iv_LMS_XML, '</Signature>');
    --插入堆存报文
    v_resMsg := trim(iv_LMS_XML);
    begin
      insert into intf_storage_lms(id,bussiness_id,message_type, content,up_state,insert_time) 
      values (SEQ_INTF_STORAGE_LMS.nextval,v_business_id, v_messageType, v_resMsg, 0, sysdate);
    end;
    
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;

  
  
  

  procedure append_xml(v_clob in out clob, v_str varchar2) is
  begin
    dbms_lob.writeappend(v_clob, length(v_str), v_str);
  end;

  procedure add_TransConta(msgString in clob,
                           v_resCode out varchar2,
                           v_resMsg  out varchar2) is
  
    iv_update_Json_list json_list;
    iv_tmp_jsonObj      json;
    iv_jv               json_value;
    iv_carId            varchar2(20);
  
    iv_public_Json json;
    iv_recCnt      number(10);
    iv_cnt         number(8);
    iv_recordNo    varchar2(20);
    iv_carType     varchar2(20);
    iv_tradeType   varchar2(20);
    iv_areaCode    varchar2(20);
    iv_fixShipNo   varchar2(20);
    iv_billNo      varchar2(20);
    iv_shipName    varchar2(20);
    iv_batchNo     varchar2(32);
  
  begin
  
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    iv_update_Json_list := json_list(msgString);
  
    iv_recCnt := iv_update_Json_list.count;
  
    begin
      for i in 1 .. iv_recCnt loop
        iv_jv          := iv_update_Json_list.get_elem(i);
        iv_tmp_jsonObj := json(iv_jv);
        iv_carId       := json_ext.get_string(iv_tmp_jsonObj, 'carId');
        iv_carType     := json_ext.get_string(iv_tmp_jsonObj, 'carType');
        iv_tradeType   := json_ext.get_string(iv_tmp_jsonObj, 'tradeType');
        iv_shipName    := json_ext.get_string(iv_tmp_jsonObj, 'shipName');
        iv_billNo      := json_ext.get_string(iv_tmp_jsonObj, 'billNo');
        iv_fixShipNo   := json_ext.get_string(iv_tmp_jsonObj, 'fixShipNo');
        iv_areaCode    := json_ext.get_string(iv_tmp_jsonObj, 'areaCode');
        iv_batchNo     := json_ext.get_string(iv_tmp_jsonObj, 'batchNo');
      
        if i = 1 then
          delete from TRANSMST a
           where a.rec_type = '1'
             and (a.BATCH_NO != iv_batchNo or a.BATCH_NO is null);
        
          commit;
        end if;
        insert into TRANSMST
          (REC_ID,
           batch_no,
           car_id,
           CAR_TYP,
           TRADE_TYPE,
           ship_name,
           bill_no,
           fix_ship_no,
           area_code,
           REG_DTM,
           op_code,
           rec_type)
        values
          (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO 待确认 入厂流水号
           iv_batchNo,
           iv_carID,
           iv_carType,
           iv_tradeType,
           iv_shipName,
           iv_billNo,
           iv_fixShipNo,
           iv_areaCode,
           sysdate,
           '1',
           '1');
      
      end loop;
    exception
      when others then
        rollback;
        v_resCode := '2009';
        v_resMsg  := '添加集装箱信息异常';
        return;
    end;
  
    commit;
  exception
    when others then
      rollback;
      v_resCode := '2009';
      v_resMsg  := '添加集装箱信息异常';
      return;
  end;

  --补打过卡记录
  procedure P_PRINT_RECORD(v_reportInfo in varchar2,
                           v_reportJson out varchar2,
                           v_reportXml  out varchar2,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2) is
  
    v_record_no       number(19);
    vrow              rlrecordmstqy%rowtype;
    iv_reportJson     json;
    iv_in_CHNL_NO     varchar2(30); --通道号
    iv_out_CHNL_NO    varchar2(30); --通道号
    iv_CHNL_NO        varchar2(30); --通道号
    iv_vendorName     varchar2(30); --客户名称
    iv_goodName       varchar2(30); --货物名称
    iv_stockArea      varchar2(30); --堆场区域
    iv_inNo           varchar2(30); --进场序号
    iv_outNo          varchar2(30); --出厂需要
    iv_no             varchar2(30); --序号
    iv_tickQty        number(19, 6);
    iv_netNow         number(19, 6);
    iv_type           varchar2(30); --进出标识
    iv_GOODS_PROPERTY varchar2(30); --序号
    iv_IM_CUSTOM      varchar2(30); --序号
    iv_trade_type     varchar2(30); --序号
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    iv_reportJson := json();
    v_reportXml   := ' ';
  
    --到对应表里面取最早生成的数据（同一个BussinessId)
    begin
    
      select a.record_no
        into v_record_no
        from ticket a
       where a.STATE = '0'
         and rownum < 2;
    
      select *
        into vrow
        from rlrecordmstqy i
       where i.RECORD_NO = v_record_no;
    
      ---获取通道号，以及客户名称信息----
      select nvl(max(a.in_door_no), '99'),
             nvl(max(a.out_door_no), '99'),
             nvl(max(b.VEN_NAME), '99') ， nvl(max(a.in_no), '99'),
             nvl(max(a.out_no), '99')
        into iv_in_CHNL_NO,
             iv_out_CHNL_NO,
             iv_vendorName,
             iv_inNo,
             iv_outNo
        from rlrecordmstqy a, vendor_info b
       where b.VEN_NO = a.VEN_NO
         and a.RECORD_NO = v_record_no;
    
      if iv_in_CHNL_NO not in ('99') then
        iv_CHNL_NO := iv_in_CHNL_NO;
        iv_no      := iv_inNo;
        iv_type    := 'I';
      end if;
    
      if iv_out_CHNL_NO not in ('99') then
        iv_CHNL_NO := iv_out_CHNL_NO;
        iv_no      := iv_outNo;
        iv_type    := 'E';
      end if;
    
      ---获取货物名称---
      select nvl(max(b.good_name), '99')
        into iv_goodName
        from rlrecordmstqy a, good_info b
       where b.good_no = a.good_no
         and a.RECORD_NO = v_record_no;
    
      ---获取货物堆场名称  提单重量---
      select nvl(max(b.STOCK_AREA), '99') ，nvl(max(b.tick_qty), '99'),
             nvl(max(b.trade_type), '99')
        into iv_stockArea, iv_tickQty, iv_trade_type
        from rlrecordmstqy a, ship_cargo_record b
       where b.bill_no = a.tick_no
         and a.RECORD_NO = v_record_no;
    
      ---获取当前提单已经完成的总重量--     
      select sum(B.NET_QTY)
        into iv_netNow
        from RLRECORDMSTQY B
       where B.TICK_NO = vrow.tick_no;
    
      ---获取贸易类型--      
      if iv_trade_type in ('0', '1') then
        --外贸
        iv_GOODS_PROPERTY := 'WM';
        iv_IM_CUSTOM      := 'Y';
      
      end if;
    
      if iv_trade_type in ('2', '3') then
        --内贸
        iv_GOODS_PROPERTY := 'NM';
        iv_IM_CUSTOM      := 'Y';
      end if;
    
      append_xml(v_reportXml, '<?xml version="1.0" encoding="UTF-8" ?>');
      append_xml(v_reportXml, '<MESSAGE_LIST_1.2>');
      append_xml(v_reportXml,
                 '<AREA_ID>' || vrow.station_no || '</AREA_ID>');
    
      append_xml(v_reportXml, '<CHNL_NO>' || iv_CHNL_NO || '</CHNL_NO>');
      append_xml(v_reportXml, '<I_E_TYPE>' || iv_type || '</I_E_TYPE>');
    
      append_xml(v_reportXml, '<SEQ_NO>' || iv_no || '</SEQ_NO>');
    
      append_xml(v_reportXml, '<CHK_RESULT>' || 'Y' || '</CHK_RESULT>');
    
      append_xml(v_reportXml, '<LED_INFO>' || '补打' || '</LED_INFO>');
      append_xml(v_reportXml,
                 '<FILE_TIME>' ||
                 to_char(vrow.Insert_Time, 'YYYY-MM-DD hh24:mi:ss') ||
                 '</FILE_TIME>');
    
      append_xml(v_reportXml,
                 '<DR_IC_NO>' || vrow.card_id || '</DR_IC_NO>');
      append_xml(v_reportXml, '<FORM_ID>' || 'REPRINT' || '</FORM_ID>');
    
      append_xml(v_reportXml, '<VE_NAME>' || vrow.car_id || '</VE_NAME>');
      append_xml(v_reportXml, '<BL_NO>' || vrow.TICK_NO || '</BL_NO>');
      append_xml(v_reportXml,
                 '<BAR_CODE>' || vrow.card_id || '</BAR_CODE>');
      append_xml(v_reportXml,
                 '<GOODS_PROPERTY>' || iv_GOODS_PROPERTY ||
                 '</GOODS_PROPERTY>');
      append_xml(v_reportXml,
                 '<IM_CUSTOM>' || iv_IM_CUSTOM || '</IM_CUSTOM>');
      append_xml(v_reportXml, '<OWNER>' || iv_vendorName || '</OWNER>');
    
      append_xml(v_reportXml,
                 '<GOOD_TYPE>' || iv_goodName || '</GOOD_TYPE>');
    
      append_xml(v_reportXml,
                 '<POSITION>' || iv_stockArea || '</POSITION>');
    
      append_xml(v_reportXml,
                 '<BARE_WEIGHT>' || to_char(vrow.Pz_Qty / 1000) ||
                 '</BARE_WEIGHT>');
    
      append_xml(v_reportXml,
                 '<GROSS_WEIGHT>' || to_char(vrow.Mz_Qty / 1000) ||
                 '</GROSS_WEIGHT>');
      append_xml(v_reportXml,
                 '<NET_WEIGHT>' ||
                 to_char((vrow.Mz_Qty - vrow.Pz_Qty) / 1000) ||
                 '</NET_WEIGHT>');
    
      append_xml(v_reportXml,
                 '<BL_WEIGHT>' || to_char(iv_tickQty) / 1000 ||
                 '</BL_WEIGHT>');
    
      append_xml(v_reportXml,
                 '<LEAVE_WEIGHT>' ||
                 to_char((iv_tickQty - iv_netNow) / 1000) ||
                 '</LEAVE_WEIGHT>');
    
      append_xml(v_reportXml,
                 '<IN_TIME>' ||
                 to_char(vrow.Insert_Time, 'YYYY-MM-DD hh24:mi:ss') ||
                 '</IN_TIME>');
    
      append_xml(v_reportXml,
                 '<OUT_TIME>' ||
                 to_char(vrow.Update_Time, 'YYYY-MM-DD hh24:mi:ss') ||
                 '</OUT_TIME>');
    
      append_xml(v_reportXml, '<OP_CODE>' || vrow.Op_Code || '</OP_CODE>');
    
      append_xml(v_reportXml, '</MESSAGE_LIST_1.2>');
    
      v_reportXml := trim(v_reportXml);
    
      -----组织下发打印的报文信息----     
      iv_reportJson.put('STATION_NO', vrow.station_no);
      iv_reportJson.put('CHNL_NO', iv_CHNL_NO);
      iv_reportJson.put('VEN_NAME', iv_vendorName);
      iv_reportJson.put('GOOD_NAME', iv_goodName);
      iv_reportJson.put('CAR_ID', vrow.car_id);
      iv_reportJson.put('STOCK_AREA', iv_stockArea);
      iv_reportJson.put('BILL_NO', vrow.tick_no);
      iv_reportJson.put('NO', iv_no);
      iv_reportJson.put('PZ', to_char(vrow.Pz_Qty / 1000));
      iv_reportJson.put('MZ', to_char(vrow.Mz_Qty / 1000));
      iv_reportJson.put('TICK_QTY', to_char(iv_tickQty));
      iv_reportJson.put('LEFT_QTY',
                        tO_char((iv_tickQty - iv_netNow) / 1000));
      iv_reportJson.put('IN_TIME',
                        to_char(vrow.Insert_Time, 'YYYY-MM-DD hh24:mi:ss'));
      iv_reportJson.put('OUT_TIME',
                        to_char(vrow.Update_Time, 'YYYY-MM-DD hh24:mi:ss'));
      iv_reportJson.put('OP_CODE', vrow.Op_Code);
    
      v_reportJson := iv_reportJson.to_char();
    
      update ticket
         set state = '1', UPDATE_TIME = sysdate, STR_JSON = v_reportJson
       where record_no = v_record_no;
    
    exception
      when others then
        rollback;
        v_resCode := 2;
        v_resMsg  := 'nodata';
        return;
    end;
  
    COMMIT;
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;

  procedure P_PROCESS_0X21_BOX_BAK(v_dataInfo in varchar2,
                                   v_cmdInfo  out varchar2,
                                   v_resCode  out varchar2,
                                   v_resMsg   out varchar2) is
  
    iv_AREA_ID           varchar2(30); --场站号
    iv_CHNL_NO           varchar2(30); --通道号
    iv_I_E_TYPE          varchar2(30); --进出场标识:I进场，E出场
    iv_SEQ_NO            varchar2(30); --序列号
    iv_DR_IC_NO          varchar2(30); --IC卡号
    iv_GROSS_WT          varchar2(30); --重量
    iv_FILE_TIME         varchar2(30); --时间
    iv_CONTA_ID_F        varchar2(30); --前箱号
    iv_CONTA_ID_B        varchar2(30); --后箱号
    iv_CAR_EC_NO         varchar2(30); --电子车牌
    iv_VE_NAME           varchar2(30); --车牌号
    iv_BAR_CODE          varchar2(300); --条形码值
    iv_PHOTO_GUID        varchar2(3000); --图片相关
    iv_PHOTO_PERSPECTIVE varchar2(3000); --图片相关
  
    iv_CHK_RESULT     varchar2(30); --放行结果。Y放行，N不放行
    iv_LED_INFO       varchar2(30); --放行提示
    iv_BL_NO          varchar2(30); --提单号
    iv_GOODS_PROPERTY varchar2(30); --内外贸标识（NM/WM）
    iv_IM_CUSTOM      varchar2(30); --是否需要发送给海关（外贸Y,内贸N）
  
    iv_dataInfo_Json json;
  
    iv_rec_status  varchar2(30);
    iv_trade_type  varchar2(30);
    iv_cnt         number(3);
    iv_cmd_jsonObj json;
    iv_recNo       number(12);
  
    iv_cardID     varchar2(30);
    iv_cardTyp    varchar2(30);
    iv_temWeight  number(19, 6);
    iv_carrierNo  varchar2(30);
    iv_recordNo   number(8);
    iv_timeString varchar2(30);
    iv_trade_name varchar2(30);
    iv_ship_name  varchar2(30);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
    iv_cnt    := 0;
  
    iv_CHK_RESULT     := '';
    iv_LED_INFO       := '';
    iv_BL_NO          := '';
    iv_GOODS_PROPERTY := '';
    iv_IM_CUSTOM      := '';
  
    iv_CONTA_ID_B := '';
    iv_CONTA_ID_F := '';
    iv_AREA_ID    := '';
    iv_CHNL_NO    := '';
    iv_trade_type := 99;
    iv_trade_name := '';
  
    iv_cmd_jsonObj := json();
  
    iv_dataInfo_Json := json(v_dataInfo);
  
    -----计入时间进行测试
    select to_char(sysdate, 'yyyy-MM-dd HH24:mi:ss')
      into iv_timeString
      from dual;
  
    --解析公共部分信息
    begin
      iv_AREA_ID           := json_ext.get_string(iv_dataInfo_Json,
                                                  'AREA_ID');
      iv_CHNL_NO           := json_ext.get_string(iv_dataInfo_Json,
                                                  'CHNL_NO');
      iv_I_E_TYPE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'I_E_TYPE');
      iv_SEQ_NO            := json_ext.get_string(iv_dataInfo_Json,
                                                  'SEQ_NO');
      iv_DR_IC_NO          := json_ext.get_string(iv_dataInfo_Json,
                                                  'DR_IC_NO');
      iv_GROSS_WT          := json_ext.get_string(iv_dataInfo_Json,
                                                  'GROSS_WT');
      iv_FILE_TIME         := json_ext.get_string(iv_dataInfo_Json,
                                                  'FILE_TIME');
      iv_CONTA_ID_F        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_F');
      iv_CONTA_ID_B        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_B');
      iv_CAR_EC_NO         := json_ext.get_string(iv_dataInfo_Json,
                                                  'CAR_EC_NO');
      iv_VE_NAME           := json_ext.get_string(iv_dataInfo_Json,
                                                  'VE_NAME');
      iv_BAR_CODE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'BAR_CODE');
      iv_PHOTO_GUID        := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_GUID');
      iv_PHOTO_PERSPECTIVE := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_PERSPECTIVE');
    
    exception
      when others then
        v_resCode := '1';
        v_resMsg  := '解析汽车21报文异常：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
        return;
    end;
  
    if trim(iv_CONTA_ID_F) is null and trim(iv_CONTA_ID_B) is null then
      v_resCode := '1001';
      v_resMsg  := '禁行无箱转人工';
    
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      return;
    end if;
  
    ---出厂车辆 如果有车厢号，那么必须是在备案库中的，否则不予放行
    if iv_I_E_TYPE = 'E' or iv_I_E_TYPE = 'I' then
    
      if (length(iv_CONTA_ID_F) > 0 or length(iv_CONTA_ID_B) > 0) then
        select count(1)
          into iv_cnt
          from TRANSMST a
         where a.car_id IN (iv_CONTA_ID_F, iv_CONTA_ID_B);
        /*   AND a.REG_DTM > trunc(sysdate - 2);*/
      
        ---找不到合法车辆
        if iv_cnt = 0 then
          v_resCode := '1002';
          v_resMsg  := '集装箱未登记';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
      end if;
    
    end if;
  
    --根据注册表中查找所属的贸易类型
    if (length(iv_CONTA_ID_F) > 0 or length(iv_CONTA_ID_B) > 0) then
    
      select nvl(max(a.trade_type), '99'),
             nvl(max(a.BILL_NO), 99),
             nvl(max(a.ship_name), '未知')
        into iv_trade_type, iv_BL_NO, iv_ship_name
        from TRANSMST a
       where a.car_id IN (iv_CONTA_ID_F, iv_CONTA_ID_B)
         and rownum = 1;
    
      /* if iv_trade_type not in ('0', '1', '2', '3') then
      
        v_resCode := '1002';
        v_resMsg  := '贸易类型不明确';
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        return;
      end if;*/
    
    end if;
  
    select nvl(max(a.org_no), 99)
      into iv_carrierNo
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    if iv_trade_type = '0' then
      iv_trade_name := '外贸进口';
    end if;
    if iv_trade_type = '1' then
      iv_trade_name := '外贸出口';
    end if;
  
    if iv_trade_type = '2' then
      iv_trade_name := '内贸进口';
    end if;
    if iv_trade_type = '3' then
      iv_trade_name := '内贸出口';
    end if;
  
    if iv_trade_type in ('0', '1') then
      --外贸
      iv_GOODS_PROPERTY := 'WM';
      iv_IM_CUSTOM      := 'Y';
    
    end if;
  
    if iv_trade_type in ('2', '3') then
      --内贸
      iv_GOODS_PROPERTY := 'NM';
      iv_IM_CUSTOM      := 'Y';
    end if;
  
    begin
      ---事务统一管理
    
      if length(iv_DR_IC_NO) > 0 THEN
        iv_cardID  := iv_DR_IC_NO;
        iv_cardTyp := 'IC';
      END IF;
    
      if length(iv_CAR_EC_NO) > 0 THEN
        iv_cardID  := iv_CAR_EC_NO;
        iv_cardTyp := 'IEC';
      END IF;
    
      if length(iv_BAR_CODE) > 0 THEN
        iv_cardID  := iv_BAR_CODE;
        iv_cardTyp := 'BAR';
      END IF;
    
      ---进入集装箱业务，直接将类型写死为集装箱业务
      iv_cardTyp := BUSI_TYPE_BOX;
    
      ---进场通道 如果重复的序列号 要求能删除原来车辆信息
      if iv_I_E_TYPE = 'I' then
        select nvl(max(a.record_no), 99)
          into iv_recordNo
          from rlrecordmstqy a
         where a.in_no = iv_SEQ_NO
           and a.INSERT_TIME >= trunc(sysdate - 1);
      
        if iv_recordNo != 99 then
          delete from rlrecordmstqy a where a.record_no = iv_recordNo;
          commit;
        end if;
      end if;
    
      ----进口货物 进场（重车进入，轻车出场）----------------------------
      if iv_I_E_TYPE = 'I' and iv_trade_type in ('0', '2', '99') then
        ---进口货物 进场（重车进入，轻车出场）
        iv_trade_type := -1;
        insert into rlrecordmstqy
          (record_no, --TODO 待确认 入厂流水号哪里输入
           TICK_NO， CARD_TYP,
           CARD_ID,
           car_id,
           CAR_BATCH_NO,
           CARRIER_NO,
           MZ_QTY,
           CZ_BALANCE_NO,
           CZ_DTM,
           IN_DOOR_NO,
           IN_NO,
           STATION_NO,
           CONTA_ID_F,
           CONTA_ID_B,
           leave_flg,
           insert_time,
           RECORD_DTM,
           op_code,
           good_no)
        values
          (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO 待确认 入厂流水号
           iv_BL_NO,
           iv_cardTyp,
           iv_cardID,
           iv_VE_NAME,
           iv_ship_name,
           iv_carrierNo,
           to_number(iv_GROSS_WT),
           iv_CHNL_NO,
           to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
           iv_CHNL_NO,
           iv_SEQ_NO,
           iv_AREA_ID,
           iv_CONTA_ID_F,
           iv_CONTA_ID_B,
           '0',
           sysdate,
           sysdate,
           '1',
           iv_trade_name);
      end if;
    
      if iv_I_E_TYPE = 'E' and iv_trade_type in ('0', '2', '99') then
        ---进口货物 出场 （重车进入，轻车出场）
        iv_trade_type := -1;
        select nvl(max(a.MZ_QTY), 0), nvl(max(a.record_no), 99)
          into iv_temWeight, iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
           and rownum = 1
         order by a.insert_time desc;
        /*
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := '没有进场记录';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          return;
        end if;*/
      
        update RLRECORDMSTQY
           set PZ_QTY        = to_number(iv_GROSS_WT),
               NET_QTY       = iv_temWeight - to_number(iv_GROSS_WT),
               JQ_BALANCE_NO = iv_CHNL_NO,
               JQ_DTM        = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
               OUT_DOOR_NO   = iv_CHNL_NO,
               OUT_NO        = iv_SEQ_NO,
               update_code   = '1',
               update_time   = sysdate
         where record_no = iv_recNo;
      
      end if;
    
      ---出口货物 进场（轻车进入，重车出场）------------------------
    
      if iv_I_E_TYPE = 'I' and iv_trade_type in ('1', '3', '99') then
        ---出口货物 进场（轻车进入，重车出场）
        iv_trade_type := -1;
        insert into rlrecordmstqy
          (record_no, --TODO 待确认 入厂流水号哪里输入
           TICK_NO， CARD_TYP,
           CARD_ID,
           car_id,
           CAR_BATCH_NO,
           CARRIER_NO,
           PZ_QTY,
           JQ_BALANCE_NO,
           JQ_DTM,
           IN_DOOR_NO,
           IN_NO,
           STATION_NO,
           CONTA_ID_F,
           CONTA_ID_B,
           leave_flg,
           insert_time,
           RECORD_DTM,
           op_code,
           good_no)
        values
          (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO 待确认 入厂流水号
           iv_BL_NO,
           iv_cardTyp,
           iv_cardID,
           iv_VE_NAME,
           iv_ship_name,
           iv_carrierNo,
           to_number(iv_GROSS_WT),
           iv_CHNL_NO,
           to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
           iv_CHNL_NO,
           iv_SEQ_NO,
           iv_AREA_ID,
           iv_CONTA_ID_F,
           iv_CONTA_ID_B,
           '0',
           sysdate,
           sysdate,
           '1',
           iv_trade_name);
      
      end if;
    
      if iv_I_E_TYPE = 'E' and iv_trade_type in ('1', '3', '99') then
        ---出口货物 出场（轻车进入，重车出场）
        iv_trade_type := -1;
        select nvl(max(a.PZ_QTY), 0), nvl(max(a.record_no), 99)
          into iv_temWeight, iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
           and rownum = 1
         order by a.insert_time desc;
        /*  
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := '没有进场记录';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          return;
        end if;*/
      
        update RLRECORDMSTQY
           set MZ_QTY        = to_number(iv_GROSS_WT),
               NET_QTY       = to_number(iv_GROSS_WT) - iv_temWeight,
               CZ_BALANCE_NO = iv_CHNL_NO,
               CZ_DTM        = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
               OUT_DOOR_NO   = iv_CHNL_NO,
               OUT_NO        = iv_SEQ_NO,
               STATION_NO    = iv_AREA_ID,
               CONTA_ID_F    = iv_CONTA_ID_F,
               CONTA_ID_B    = iv_CONTA_ID_B,
               CARD_ID       = iv_cardID,
               CARD_TYP      = iv_cardTyp,
               update_code   = '1',
               update_time   = sysdate
         where record_no = iv_recNo;
      
      end if;
    
      iv_cmd_jsonObj.put('CHK_RESULT', 'Y');
      iv_cmd_jsonObj.put('LED_INFO', '正常通行');
      iv_cmd_jsonObj.put('BL_NO', iv_BL_NO);
      iv_cmd_jsonObj.put('GOODS_PROPERTY', iv_GOODS_PROPERTY);
      iv_cmd_jsonObj.put('IM_CUSTOM', iv_IM_CUSTOM);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', 'Y', v_cmdInfo, v_dataInfo);
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理21报文失败：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      
        return;
    end;
  
    commit;
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;
  procedure P_PROCESS_0X21_BOX(v_dataInfo in varchar2,
                               v_cmdInfo  out varchar2,
                               v_resCode  out varchar2,
                               v_resMsg   out varchar2) is
  
    iv_AREA_ID           varchar2(30); --场站号
    iv_CHNL_NO           varchar2(30); --通道号
    iv_I_E_TYPE          varchar2(30); --进出场标识:I进场，E出场
    iv_SEQ_NO            varchar2(30); --序列号
    iv_DR_IC_NO          varchar2(30); --IC卡号
    iv_GROSS_WT          varchar2(30); --重量
    iv_FILE_TIME         varchar2(30); --时间
    iv_CONTA_ID_F        varchar2(30); --前箱号
    iv_CONTA_ID_B        varchar2(30); --后箱号
    iv_CAR_EC_NO         varchar2(30); --电子车牌
    iv_VE_NAME           varchar2(30); --车牌号
    iv_BAR_CODE          varchar2(300); --条形码值
    iv_PHOTO_GUID        varchar2(3000); --图片相关
    iv_PHOTO_PERSPECTIVE varchar2(3000); --图片相关
  
    iv_CHK_RESULT     varchar2(30); --放行结果。Y放行，N不放行
    iv_LED_INFO       varchar2(30); --放行提示
    iv_BL_NO          varchar2(30); --提单号
    iv_GOODS_PROPERTY varchar2(30); --内外贸标识（NM/WM）
    iv_IM_CUSTOM      varchar2(30); --是否需要发送给海关（外贸Y,内贸N）
  
    iv_dataInfo_Json json;
  
    iv_rec_status  varchar2(30);
    iv_trade_type  varchar2(30);
    iv_cnt         number(3);
    iv_cmd_jsonObj json;
    iv_recNo       number(12);
  
    iv_cardID     varchar2(30);
    iv_cardTyp    varchar2(30);
    iv_temWeight  number(19, 6);
    iv_carrierNo  varchar2(30);
    iv_recordNo   number(8);
    iv_timeString varchar2(30);
    iv_trade_name varchar2(30);
    iv_ship_name  varchar2(30);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
    iv_cnt    := 0;
  
    iv_CHK_RESULT     := '';
    iv_LED_INFO       := '';
    iv_BL_NO          := '';
    iv_GOODS_PROPERTY := '';
    iv_IM_CUSTOM      := '';
  
    iv_CONTA_ID_B := '';
    iv_CONTA_ID_F := '';
    iv_AREA_ID    := '';
    iv_CHNL_NO    := '';
    iv_trade_type := 99;
    iv_trade_name := '';
  
    iv_cmd_jsonObj := json();
  
    iv_dataInfo_Json := json(v_dataInfo);
  
    -----计入时间进行测试
    select to_char(sysdate, 'yyyy-MM-dd HH24:mi:ss')
      into iv_timeString
      from dual;
  
    --解析公共部分信息
    begin
      iv_AREA_ID           := json_ext.get_string(iv_dataInfo_Json,
                                                  'AREA_ID');
      iv_CHNL_NO           := json_ext.get_string(iv_dataInfo_Json,
                                                  'CHNL_NO');
      iv_I_E_TYPE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'I_E_TYPE');
      iv_SEQ_NO            := json_ext.get_string(iv_dataInfo_Json,
                                                  'SEQ_NO');
      iv_DR_IC_NO          := json_ext.get_string(iv_dataInfo_Json,
                                                  'DR_IC_NO');
      iv_GROSS_WT          := json_ext.get_string(iv_dataInfo_Json,
                                                  'GROSS_WT');
      iv_FILE_TIME         := json_ext.get_string(iv_dataInfo_Json,
                                                  'FILE_TIME');
      iv_CONTA_ID_F        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_F');
      iv_CONTA_ID_B        := json_ext.get_string(iv_dataInfo_Json,
                                                  'CONTA_ID_B');
      iv_CAR_EC_NO         := json_ext.get_string(iv_dataInfo_Json,
                                                  'CAR_EC_NO');
      iv_VE_NAME           := json_ext.get_string(iv_dataInfo_Json,
                                                  'VE_NAME');
      iv_BAR_CODE          := json_ext.get_string(iv_dataInfo_Json,
                                                  'BAR_CODE');
      iv_PHOTO_GUID        := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_GUID');
      iv_PHOTO_PERSPECTIVE := json_ext.get_string(iv_dataInfo_Json,
                                                  'PHOTO_PERSPECTIVE');
    
    exception
      when others then
        v_resCode := '1';
        v_resMsg  := '解析汽车21报文异常：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
        return;
    end;
  
    if length(iv_DR_IC_NO) > 0 THEN
      iv_cardID  := iv_DR_IC_NO;
      iv_cardTyp := 'IC';
    END IF;
  
    if length(iv_CAR_EC_NO) > 0 THEN
      iv_cardID  := iv_CAR_EC_NO;
      iv_cardTyp := 'IEC';
    END IF;
  
    if length(iv_BAR_CODE) > 0 THEN
      iv_cardID  := iv_BAR_CODE;
      iv_cardTyp := 'BAR';
    END IF;
    ---进入集装箱业务，直接将类型写死为集装箱业务
    iv_cardTyp := BUSI_TYPE_BOX;
  
    select nvl(max(a.org_no), 99)
      into iv_carrierNo
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    if trim(iv_CONTA_ID_F) is null and trim(iv_CONTA_ID_B) is null then
      v_resCode := '1001';
      v_resMsg  := '禁行无箱转人工';
    
      -----虽然走人工，但是依然记录-----（当做空车进场）--------
    
      iv_BL_NO := '';
    
      insert into rlrecordmstqy
        (record_no, --TODO 待确认 入厂流水号哪里输入
         TICK_NO， CARD_TYP,
         CARD_ID,
         car_id,
         CAR_BATCH_NO,
         CARRIER_NO,
         PZ_QTY,
         JQ_BALANCE_NO,
         JQ_DTM,
         IN_DOOR_NO,
         IN_NO,
         STATION_NO,
         CONTA_ID_F,
         CONTA_ID_B,
         leave_flg,
         insert_time,
         RECORD_DTM,
         op_code,
         good_no)
      values
        (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO 待确认 入厂流水号
         iv_BL_NO,
         iv_cardTyp,
         iv_cardID,
         iv_VE_NAME,
         '',
         iv_carrierNo,
         to_number(iv_GROSS_WT),
         iv_CHNL_NO,
         to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
         iv_CHNL_NO,
         iv_SEQ_NO,
         iv_AREA_ID,
         '',
         '',
         '0',
         sysdate,
         sysdate,
         '1',
         iv_trade_name);
    
      -----虽然走人工，但是依然记录-----（当做空车进场）--------
    
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      commit;
      return;
    end if;
  
    ---出厂车辆 如果有车厢号，那么必须是在备案库中的，否则不予放行
    if iv_I_E_TYPE = 'E' or iv_I_E_TYPE = 'I' then
    
      if (length(iv_CONTA_ID_F) > 0 or length(iv_CONTA_ID_B) > 0) then
        select count(1)
          into iv_cnt
          from TRANSMST a
         where a.car_id IN (iv_CONTA_ID_F, iv_CONTA_ID_B);
        /*   AND a.REG_DTM > trunc(sysdate - 2);*/
      
        ---找不到合法车辆
        if iv_cnt = 0 then
          v_resCode := '1002';
          v_resMsg  := '集装箱未登记';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
      end if;
    
    end if;
  
    --根据注册表中查找所属的贸易类型
    if (length(iv_CONTA_ID_F) > 0 or length(iv_CONTA_ID_B) > 0) then
    
      select nvl(max(a.trade_type), '99'),
             nvl(max(a.BILL_NO), 99),
             nvl(max(a.ship_name), '未知')
        into iv_trade_type, iv_BL_NO, iv_ship_name
        from TRANSMST a
       where a.car_id IN (iv_CONTA_ID_F, iv_CONTA_ID_B)
         and rownum = 1;
    
    end if;
  
    if iv_trade_type = '0' then
      iv_trade_name := '外贸进口';
    end if;
    if iv_trade_type = '1' then
      iv_trade_name := '外贸出口';
    end if;
  
    if iv_trade_type = '2' then
      iv_trade_name := '内贸进口';
    end if;
    if iv_trade_type = '3' then
      iv_trade_name := '内贸出口';
    end if;
  
    if iv_trade_type in ('0', '1') then
      --外贸
      iv_GOODS_PROPERTY := 'WM';
      iv_IM_CUSTOM      := 'Y';
    
    end if;
  
    if iv_trade_type in ('2', '3') then
      --内贸
      iv_GOODS_PROPERTY := 'NM';
      iv_IM_CUSTOM      := 'Y';
    end if;
  
    begin
    
      ---进场通道 如果重复的序列号 要求能删除原来车辆信息
      if iv_I_E_TYPE = 'I' then
        select nvl(max(a.record_no), 99)
          into iv_recordNo
          from rlrecordmstqy a
         where a.in_no = iv_SEQ_NO
           and a.INSERT_TIME >= trunc(sysdate - 1);
      
        if iv_recordNo != 99 then
          delete from rlrecordmstqy a where a.record_no = iv_recordNo;
          commit;
        end if;
      end if;
    
      ---出口货物 进场（轻车进入，重车出场）------------------------
    
      if iv_I_E_TYPE = 'I' then
      
        insert into rlrecordmstqy
          (record_no, --TODO 待确认 入厂流水号哪里输入
           TICK_NO， CARD_TYP,
           CARD_ID,
           car_id,
           CAR_BATCH_NO,
           CARRIER_NO,
           PZ_QTY,
           JQ_BALANCE_NO,
           JQ_DTM,
           IN_DOOR_NO,
           IN_NO,
           STATION_NO,
           CONTA_ID_F,
           CONTA_ID_B,
           leave_flg,
           insert_time,
           RECORD_DTM,
           op_code,
           good_no)
        values
          (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO 待确认 入厂流水号
           iv_BL_NO,
           iv_cardTyp,
           iv_cardID,
           iv_VE_NAME,
           iv_ship_name,
           iv_carrierNo,
           to_number(iv_GROSS_WT),
           iv_CHNL_NO,
           to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
           iv_CHNL_NO,
           iv_SEQ_NO,
           iv_AREA_ID,
           iv_CONTA_ID_F,
           iv_CONTA_ID_B,
           '0',
           sysdate,
           sysdate,
           '1',
           iv_trade_name);
      
      end if;
    
      if iv_I_E_TYPE = 'E' then
        ---出口货物 出场（轻车进入，重车出场）
      
        select nvl(max(a.record_no), 0)
          into iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME;
      
        update RLRECORDMSTQY
           set MZ_QTY        = to_number(iv_GROSS_WT),
               NET_QTY       = to_number(iv_GROSS_WT), ---集装箱净重没有意义
               CZ_BALANCE_NO = iv_CHNL_NO,
               CZ_DTM        = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss'),
               OUT_DOOR_NO   = iv_CHNL_NO,
               OUT_NO        = iv_SEQ_NO,
               STATION_NO    = iv_AREA_ID,
               CONTA_ID_F    = iv_CONTA_ID_F,
               CONTA_ID_B    = iv_CONTA_ID_B,
               CARD_ID       = iv_cardID,
               CARD_TYP      = iv_cardTyp,
               TICK_NO       = iv_BL_NO,
               CAR_BATCH_NO  = iv_ship_name,
               good_no       = iv_trade_name,
               update_code   = '1',
               update_time   = sysdate
         where record_no = iv_recNo;
      
      end if;
    
      iv_cmd_jsonObj.put('CHK_RESULT', 'Y');
      iv_cmd_jsonObj.put('LED_INFO', '正常通行');
      iv_cmd_jsonObj.put('BL_NO', iv_BL_NO);
      iv_cmd_jsonObj.put('GOODS_PROPERTY', iv_GOODS_PROPERTY);
      iv_cmd_jsonObj.put('IM_CUSTOM', iv_IM_CUSTOM);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', 'Y', v_cmdInfo, v_dataInfo);
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '处理21报文失败：' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      
        return;
    end;
  
    commit;
  exception
    when others then
      v_resCode := 1;
      v_resMsg  := substr(sqlerrm, 1, 100);
  end;

end BUSINESS_GROUP_INTF_PCK;
/
