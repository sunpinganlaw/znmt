create or replace package pk_ship is

  -- Author  : xieyt
  -- Created : 2015-10-10 10:07:02
  -- Purpose : �ִ������¼��Ϣ

  --�ִ�������Ϣ�༭
  procedure edit_ship_info(v_jsonStr   in varchar2,
                           v_actionTag in varchar2,
                           v_opCode    in varchar2,
                           v_resCode   out varchar2,
                           v_resMsg    out varchar2);

  --�ִ�������Ϣ�༭
  procedure edit_ship_rec(v_jsonStr   in varchar2,
                          v_actionTag in varchar2,
                          v_opCode    in varchar2,
                          v_resCode   out varchar2,
                          v_resMsg    out varchar2);

  --�ִ�ú��Ϣ�༭
  procedure edit_ship_cargo_rec(v_jsonStr   in varchar2,
                                v_actionTag in varchar2,
                                v_opCode    in varchar2,
                                v_resCode   out varchar2,
                                v_resMsg    out varchar2);

  function get_man_sample_code return varchar2;

  --�ж��Ƿ��Ѿ������� Y�Ѿ�����  Nδ����
  function isBatch(v_shipRecId in varchar2) return varchar2;

  --����ú�������豸��������
  procedure ship_schedule(v_weightData in varchar2,
                          v_sampleCode out varchar2,
                          v_resCode    out varchar2,
                          v_resMsg     out varchar2);

  --ִ�е���
  procedure doSchedule(v_deviceNo   in varchar2, --1��1�Ų�����  2��2�Ų�����
                       v_beltNo     in varchar2,
                       v_cumuQty    in number,
                       v_sampleCode in varchar2,
                       v_resCode    out varchar2,
                       v_resMsg     out varchar2);

  --��ȡ��ǰ��������Ϣ
  procedure get_current_batch(v_batchNo     out varchar2,
                              v_sampleCode  out varchar2,
                              v_batchStatus out varchar2);

  --��ȡ���κ�
  function get_current_batchNo return varchar2;

  --��ȡ���κ�
  function get_current_sampleCode return varchar2;

  --��ȡ���κ�
  function get_current_samplingCode return varchar2;

  --��ȡ��ǰ����״̬
  function get_batch_status(v_sampleCode in varchar2) return varchar2;

  --�������μ�����
  procedure update_batch_info(v_sampleCode in varchar2,
                              v_beltNo     in varchar2,
                              v_cumuQty    in number,
                              v_resCode    out varchar2,
                              v_resMsg     out varchar2);
  --�ύж��Ԥ��������
  procedure commit_xy_Pre_Command(v_sampleCode in varchar2,
                                  v_resCode    out varchar2,
                                  v_resMsg     out varchar2);

  --��job�����Ĵ���ж��������������Ԥ��������
  --����豸�ڹ涨ʱ���ھ������ʹ�Ԥ�����ת����ʽ����д���
  procedure cmd_process_job;

  --��¼�澯��Ϣ
  procedure record_warning(v_machinCode in varchar2,
                           v_flowId     in varchar2,
                           v_errorCode  in varchar2,
                           v_errorDesc  in varchar2);

  function translate_cmd(v_machineType in varchar2,
                         v_cmdCode     in varchar2,
                         v_sampleCode  in varchar2) return varchar2;

  --���õ�ǰжú��
  procedure setCurrentShip(v_shipRecID in varchar2,
                           v_opCode    in varchar2,
                           v_resCode   out varchar2,
                           v_resMsg    out varchar2);

  --��������ú������Ϣ
  procedure operate_Batch_Status(v_jsonStr in varchar2,
                                 v_opCode  in varchar2,
                                 v_resCode out varchar2,
                                 v_resMsg  out varchar2);

  procedure test;

  procedure ship_schedule_job;

end pk_ship;
/
create or replace package body pk_ship is

  -- Author  : xieyt
  -- Created : 2015-10-10 10:07:02
  -- Purpose : �ִ������¼��Ϣ

  --�ִ�������Ϣ�༭
  procedure edit_ship_info(v_jsonStr   in varchar2,
                           v_actionTag in varchar2,
                           v_opCode    in varchar2,
                           v_resCode   out varchar2,
                           v_resMsg    out varchar2) is
  
    iv_Json json;
    iv_cnt  number(5);
  
    iv_shipId      varchar2(30);
    iv_shipName    varchar2(60);
    iv_shipEngName varchar2(60);
    iv_shipCode    varchar2(60);
    iv_fixShipNo   varchar2(60);
    iv_cabinCnt    varchar2(30);
    iv_loadTun     varchar2(30);
    iv_totalTun    varchar2(30);
    iv_width       varchar2(30);
    iv_length      varchar2(30);
    iv_nationCd    varchar2(30);
    iv_companyNo   varchar2(30);
    iv_remark      varchar2(500);
    iv_status      varchar2(6);
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --��������������Ϣ
    begin
    
      iv_Json := json(v_jsonStr);
    
      iv_shipId      := json_ext.get_string(iv_Json, 'shipId');
      iv_shipName    := json_ext.get_string(iv_Json, 'shipName');
      iv_shipEngName := json_ext.get_string(iv_Json, 'shipEngName');
      iv_shipCode    := json_ext.get_string(iv_Json, 'shipCode');
      iv_fixShipNo   := json_ext.get_string(iv_Json, 'fixShipNo');
      iv_cabinCnt    := json_ext.get_string(iv_Json, 'cabinCnt');
      iv_loadTun     := json_ext.get_string(iv_Json, 'loadTun');
      iv_totalTun    := json_ext.get_string(iv_Json, 'totalTun');
      iv_width       := json_ext.get_string(iv_Json, 'width');
      iv_length      := json_ext.get_string(iv_Json, 'length');
      iv_nationCd    := json_ext.get_string(iv_Json, 'nationCd');
      iv_companyNo   := json_ext.get_string(iv_Json, 'companyNo');
      iv_remark      := json_ext.get_string(iv_Json, 'remark');
      iv_status      := json_ext.get_string(iv_Json, 'status');
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '��������ú��Ϣ�쳣��' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    if v_actionTag in ('ADD') then
      select count(1)
        into iv_cnt
        from ship_info a
       where 1 = 1
         and a.ship_name = iv_shipName
         and a.status = '1';
    
      if iv_cnt > 0 then
        v_resCode := '1';
        v_resMsg  := '�ô����򴬺��Ѿ����ڣ������ظ�����';
        return;
      end if;
    end if;
  
    if v_actionTag = 'ADD' then
      begin
        insert into SHIP_INFO
          (SHIP_ID,
           SHIP_NAME,
           SHIP_ENG_NAME,
           SHIP_CODE,
           FIX_SHIP_NO,
           CABIN_CNT,
           LOAD_TUN,
           TOTAL_TUN,
           WIDTH,
           LENGTH,
           NATION_CD,
           COMPANY_NO,
           REMARK,
           STATUS)
        values
          (SEQ_SHIP_INFO.Nextval,
           iv_shipName,
           iv_shipEngName,
           iv_shipCode,
           iv_fixShipNo,
           iv_cabinCnt,
           iv_loadTun,
           iv_totalTun,
           iv_width,
           iv_length,
           iv_nationCd,
           iv_companyNo,
           iv_remark,
           iv_status);
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '������������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    
    elsif v_actionTag = 'MOD' then
      begin
        update ship_info
           set SHIP_NAME     = iv_shipName,
               SHIP_ENG_NAME = iv_shipEngName,
               SHIP_CODE     = iv_shipCode,
               FIX_SHIP_NO   = iv_fixShipNo,
               CABIN_CNT     = iv_cabinCnt,
               LOAD_TUN      = iv_loadTun,
               TOTAL_TUN     = iv_totalTun,
               WIDTH         = iv_width,
               LENGTH        = iv_length,
               NATION_CD     = iv_nationCd,
               COMPANY_NO    = iv_companyNo,
               REMARK        = iv_remark,
               STATUS        = iv_status
         where ship_id = iv_shipId;
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�޸Ĵ�������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    elsif v_actionTag = 'DEL' then
      update ship_info a
         set a.status = '2',
             a.remark = a.remark || ' ǰ̨ɾ��' ||
                        to_char(sysdate, 'yyyymmddhh24miss') || ',' ||
                        v_opCode
       where a.ship_id = iv_shipId;
    end if;
  
    --�ύ����
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := 'ά����������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
      return;
  end;

  --�ִ�������Ϣ�༭
  procedure edit_ship_rec(v_jsonStr   in varchar2,
                          v_actionTag in varchar2,
                          v_opCode    in varchar2,
                          v_resCode   out varchar2,
                          v_resMsg    out varchar2) is
  
    iv_Json json;
  
    iv_shipRecId    varchar2(30);
    iv_shipId       varchar2(30);
    iv_carrierNo    varchar2(30);
    iv_receiverNo   varchar2(30);
    iv_waybillNo    varchar2(30);
    iv_status       varchar2(30);
    iv_waterTun     varchar2(30);
    iv_loadHours    varchar2(30);
    iv_unloadHours  varchar2(30);
    iv_startPortNo  varchar2(30);
    iv_finalPortNo  varchar2(30);
    iv_startTime    varchar2(30);
    iv_estimateTime varchar2(30);
    iv_factTime     varchar2(30);
    iv_arrangeTime  varchar2(30);
    iv_unloadTime   varchar2(30);
    iv_departTime   varchar2(30);
    iv_berthNo      varchar2(30);
    iv_remark       varchar2(300);
    iv_shipType     varchar2(300);
  
    iv_transNo varchar2(30);
  
    iv_SHIP_RECORD SHIP_RECORD.Ship_Rec_Id%type;
  
    iv_recordNo varchar2(30);
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --��������������Ϣ
    begin
    
      iv_Json := json(v_jsonStr);
    
      iv_shipId       := json_ext.get_string(iv_Json, 'shipId');
      iv_shipRecId    := json_ext.get_string(iv_Json, 'shipRecID');
      iv_carrierNo    := json_ext.get_string(iv_Json, 'carrierNo');
      iv_receiverNo   := json_ext.get_string(iv_Json, 'receiverNo');
      iv_waybillNo    := json_ext.get_string(iv_Json, 'waybillNo');
      iv_status       := json_ext.get_string(iv_Json, 'status');
      iv_waterTun     := json_ext.get_string(iv_Json, 'waterTun');
      iv_loadHours    := json_ext.get_string(iv_Json, 'loadHours');
      iv_unloadHours  := json_ext.get_string(iv_Json, 'unloadHours');
      iv_startPortNo  := json_ext.get_string(iv_Json, 'startPortNo');
      iv_finalPortNo  := json_ext.get_string(iv_Json, 'finalPortNo');
      iv_startTime    := json_ext.get_string(iv_Json, 'startTime');
      iv_factTime     := json_ext.get_string(iv_Json, 'factTime');
      iv_estimateTime := json_ext.get_string(iv_Json, 'estimateTime');
      iv_arrangeTime  := json_ext.get_string(iv_Json, 'arrangeTime');
      iv_unloadTime   := json_ext.get_string(iv_Json, 'unloadTime');
      iv_departTime   := json_ext.get_string(iv_Json, 'departTime');
      iv_berthNo      := json_ext.get_string(iv_Json, 'berthNo');
      iv_remark       := json_ext.get_string(iv_Json, 'remark');
      iv_shipType     := json_ext.get_string(iv_Json, 'shipType');
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '����������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    if v_actionTag = 'ADD' then
      begin
        iv_transNo := getNewTrainNo();
        select SEQ_SHIP_RECORD.Nextval into iv_SHIP_RECORD from dual;
        insert into SHIP_RECORD
          (SHIP_REC_ID, --
           SHIP_TRANS_NO, --
           SHIP_ID, --
           CARRIER_NO, --
           WAYBILL_NO, --
           WATER_TUN, --
           RECEIVER_NO, --
           STATUS, --
           LOAD_HOURS, --
           UNLOAD_HOURS, --
           START_PORT_NO, --
           FINAL_PORT_NO, --
           START_TIME, --
           ESTIMATE_TIME, --
           FACT_TIME, --
           ARRANGE_TIME, --
           UNLOAD_TIME, --
           DEPART_TIME, --
           BERTH_NO, --
           INSERT_TIME,
           OP_CODE,
           REMARK,
           REC_STATUS)
        values
          (iv_SHIP_RECORD, --SEQ_SHIP_RECORD.Nextval,
           iv_transNo,
           to_number(iv_shipId),
           iv_carrierNo,
           iv_waybillNo,
           to_number(iv_waterTun),
           iv_receiverNo,
           iv_status,
           iv_loadHours,
           iv_unloadHours,
           iv_startPortNo,
           iv_finalPortNo,
           to_date(iv_startTime, 'yyyy-mm-dd hh24:mi:ss'),
           to_date(iv_estimateTime, 'yyyy-mm-dd hh24:mi:ss'),
           to_date(iv_factTime, 'yyyy-mm-dd hh24:mi:ss'),
           to_date(iv_arrangeTime, 'yyyy-mm-dd hh24:mi:ss'),
           to_date(iv_unloadTime, 'yyyy-mm-dd hh24:mi:ss'),
           to_date(iv_departTime, 'yyyy-mm-dd hh24:mi:ss'),
           iv_berthNo,
           sysdate,
           v_opCode,
           iv_remark,
           '1');
      
        --��¼��־
        log_mod_infos(v_opCode,
                      'edit_ship_rec_add',
                      iv_shipRecId,
                      null,
                      null,
                      null,
                      '�ִ�����-���Ӵ��˼�¼',
                      'C');
      
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '����������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    elsif v_actionTag = 'MOD' then
      --�޸�
      begin
        --��¼��־
        log_mod_infos(v_opCode,
                      'edit_ship_rec_mod',
                      iv_shipRecId,
                      null,
                      null,
                      null,
                      '�ִ�����-�޸Ĵ��˼�¼',
                      'C');
      
        update SHIP_RECORD
           set SHIP_ID       = to_number(iv_shipId),
               CARRIER_NO    = iv_carrierNo,
               WAYBILL_NO    = iv_waybillNo,
               WATER_TUN     = to_number(iv_waterTun),
               RECEIVER_NO   = iv_receiverNo,
               STATUS        = iv_status,
               LOAD_HOURS    = iv_loadHours, --
               UNLOAD_HOURS  = iv_unloadHours, --
               START_PORT_NO = iv_startPortNo, --
               FINAL_PORT_NO = iv_finalPortNo, --
               START_TIME    = to_date(iv_startTime, 'yyyy-mm-dd hh24:mi:ss'), --
               ESTIMATE_TIME = to_date(iv_estimateTime,
                                       'yyyy-mm-dd hh24:mi:ss'), --
               FACT_TIME     = to_date(iv_factTime, 'yyyy-mm-dd hh24:mi:ss'), --
               ARRANGE_TIME  = to_date(iv_arrangeTime,
                                       'yyyy-mm-dd hh24:mi:ss'), --
               UNLOAD_TIME   = to_date(iv_unloadTime,
                                       'yyyy-mm-dd hh24:mi:ss'), --
               DEPART_TIME   = to_date(iv_departTime,
                                       'yyyy-mm-dd hh24:mi:ss'), --
               BERTH_NO      = iv_berthNo, --
               UPDATE_TIME   = sysdate, --
               UPDATE_CODE   = v_opCode,
               REMARK        = iv_remark,
               ship_type     = iv_shipType
         where SHIP_REC_ID = iv_shipRecId;
      
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�޸Ĵ�����Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    elsif v_actionTag = 'DEL' then
      --ɾ��
    
      if isBatch(iv_shipRecId) = 'Y' then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�ô��Ѿ��г���������¼��������ɾ��';
        return;
      end if;
    
      --��¼��־
      log_mod_infos(v_opCode,
                    'edit_ship_rec_del',
                    iv_shipRecId,
                    null,
                    null,
                    null,
                    '�ִ�����-ɾ�����˼�¼',
                    'C');
    
      update SHIP_CARGO_RECORD a
         set a.rec_status  = '5',
             a.update_time = sysdate,
             a.update_code = v_opCode,
             a.remark      = a.remark || ' ǰ̨ɾ��'
       where a.ship_rec_id = iv_shipRecId;
    
      update SHIP_RECORD a
         set a.rec_status  = '5',
             a.update_time = sysdate,
             a.update_code = v_opCode,
             a.remark      = a.remark || ' ǰ̨ɾ��'
       where a.ship_rec_id = iv_shipRecId;
    
      ---ֱ��ɾ����ӵĳ�����¼
      for cur in (select a.record_no
                    from SHIP_CARGO_RECORD a
                   where a.ship_rec_id = iv_shipRecId) loop
      
        delete from rlrecordmstqy b where b.car_batch_no = cur.record_no;
      end loop;
    
    elsif v_actionTag = 'BAT' then
      --����
      /*  processShipBatch(iv_shipRecId, v_opCode, v_resCode, v_resMsg);*/
    
      if v_resCode <> '0' then
        rollback;
        return;
      end if;
    end if;
  
    --�ύ����
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '��������Ϣ�쳣��' || substr(sqlerrm, 1, 100);
      return;
  end;

  --�ִ�ú��Ϣ�༭
  procedure edit_ship_cargo_rec(v_jsonStr   in varchar2,
                                v_actionTag in varchar2,
                                v_opCode    in varchar2,
                                v_resCode   out varchar2,
                                v_resMsg    out varchar2) is
  
    iv_Json             json;
    iv_shipRecId        varchar2(64);
    iv_recordNo         varchar2(30);
    iv_venNo            varchar2(30);
    iv_mineNo           varchar2(30);
    iv_coalNo           varchar2(30);
    iv_carrierNo        varchar2(30);
    iv_cabinNos         varchar2(500);
    iv_tickQty          varchar2(30);
    iv_lossType         varchar2(30);
    iv_kdQty            varchar2(30);
    iv_departQty        varchar2(30);
    iv_receiveQty       varchar2(30);
    iv_sampleType       varchar2(30);
    iv_remark           varchar2(1000);
    iv_shipRecIDWayTag  varchar2(5);
    iv_cnt              number(5);
    iv_factTime         varchar2(50);
    iv_transForCode     varchar2(100);
    iv_randomValue      number(5);
    iv_tradeType        varchar2(30);
    iv_goodNo           varchar2(30);
    iv_billNo           varchar2(30);
    iv_areaName         varchar2(30);
    iv_areaPosition     varchar2(30);
    iv_areaHeiht        varchar2(30);
    iv_storageCode      varchar2(30);
    iv_storageName      varchar2(30);
    iv_areaNo           varchar2(30);
    iv_goodType         varchar2(30);
    iv_count            number(8);
    iv_customerNo       varchar2(30);
    iv_sequencerecordNo number(12);
    iv_lms_Code         varchar2(30);
    iv_lms_Msg          varchar2(2048);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
    iv_count  := 0;
    --��������������Ϣ
    begin
    
      iv_Json := json(v_jsonStr);
    
      iv_recordNo   := json_ext.get_string(iv_Json, 'recordNo');
      iv_venNo      := json_ext.get_string(iv_Json, 'venNo');
      iv_tickQty    := json_ext.get_string(iv_Json, 'tickQty');
      iv_tradeType  := json_ext.get_string(iv_Json, 'tradeType');
      iv_goodNo     := json_ext.get_string(iv_Json, 'goodNo');
      iv_goodType   := json_ext.get_string(iv_Json, 'goodType');
      iv_billNo     := json_ext.get_string(iv_Json, 'billNo');
      iv_factTime   := json_ext.get_string(iv_Json, 'factTime'); --����ʱ��
      iv_sampleType := json_ext.get_string(iv_Json, 'sampleType');
      /* iv_areaName  := json_ext.get_string(iv_Json, 'areaName'); 
      iv_areaPosition  := json_ext.get_string(iv_Json, 'areaPosition'); */
      iv_areaHeiht := json_ext.get_string(iv_Json, 'areaHeight');
      iv_areaNo    := json_ext.get_string(iv_Json, 'areaNo');
      /*iv_storageCode  := json_ext.get_string(iv_Json, 'storageCode'); */
      iv_storageName := json_ext.get_string(iv_Json, 'storageName');
      iv_customerNo  := json_ext.get_string(iv_Json, 'customerNo');
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�����ᵥ��Ϣ�쳣��' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    if v_actionTag = 'ADD' or v_actionTag = 'MOD' then
      select stock_name, stock_area
        into iv_areaName, iv_areaPosition
        from stock_info a
       where a.stock_no = iv_areaNo;
    end if;
  
    if v_actionTag = 'ADD' then
    
      iv_sequencerecordNo := seq_ship_cargo_record.nextval;
     /* select count(1)
        into iv_count
        from ship_cargo_record a
       where a.bill_no = iv_billNo
         and a.rec_status not in ('5');
    
      if iv_count > 0 then
        v_resCode := '2';
        v_resMsg  := 'ϵͳ���Ѿ�����ͬ���ᵥ��';
        return;
      end if;*/
      iv_billNo:=iv_billNo||'-'||TO_CHAR(SEQ_TICK_NO.NEXTVAL);
    
      begin
        insert into ship_cargo_record
          (RECORD_NO,
           SHIP_REC_ID,
           VEN_NO, --
           CUSTOMER_NO,
           TICK_QTY, --
           TRADE_TYPE,
           GOOD_NO,
           INSERT_TIME,
           OP_CODE,
           REC_STATUS,
           BILL_NO,
           fact_time,
           SAMPLE_TYPE,
           STOCK_NAME,
           STOCK_AREA,
           STOCK_HEIGHT,
           STOCK_NO,
           STORAGE_CODE,
           STORAGE_NAME,
           GOOD_TYPE)
        values
          (iv_sequencerecordNo,
           iv_recordNo,
           iv_venNo,
           iv_customerNo,
           to_number(iv_tickQty) * 1000,
           iv_tradeType,
           iv_goodNo,
           sysdate,
           v_opCode,
           '0',
           iv_billNo,
           to_date(iv_factTime, 'yyyy-mm-dd hh24:mi:ss'),
           iv_sampleType,
           iv_areaName,
           iv_areaPosition,
           iv_areaHeiht,
           iv_areaNo,
           iv_storageCode,
           iv_storageName,
           iv_goodType);
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�����ᵥ��Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    
    elsif v_actionTag = 'MOD' then
      begin
        update ship_cargo_record
           set VEN_NO       = iv_venno,
               CUSTOMER_NO  = iv_customerNo,
               TICK_QTY     = to_number(iv_tickQty) * 1000,
               FACT_TIME    = TO_DATE(iv_factTime, 'YYYY/MM/DD HH24:MI:SS'),
               TRADE_TYPE   = iv_tradeType,
               GOOD_NO      = iv_goodNo,
               BILL_NO      = iv_billNo,
               UPDATE_TIME  = sysdate,
               UPDATE_CODE  = v_opCode,
               SAMPLE_TYPE  = iv_sampleType,
               STOCK_NAME   = iv_areaName,
               STOCK_AREA   = iv_areaPosition,
               STOCK_HEIGHT = iv_areaHeiht,
               STOCK_NO     = iv_areaNo,
               STORAGE_CODE = iv_storageCode,
               STORAGE_NAME = iv_storageName,
               GOOD_TYPE    = iv_goodType
         where record_No = iv_recordNo;
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�޸��ᵥ��Ϣ�쳣��' || substr(sqlerrm, 1, 100);
          return;
      end;
    elsif v_actionTag = 'DEL' then
    
      select count(*)
        into iv_cnt
        from ship_cargo_record a, rlrecordmstqy b
       where (b.cfm_flg = '1' OR b.leave_flg = '1')
         and a.record_no = to_number(b.car_batch_no)
         and a.record_no = iv_recordNo;
    
      if iv_cnt > 0 then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�ô��Ѿ��г���������¼��������ɾ��';
        return;
      end if;
    
      --������ʷ���飬Ҫɾ���Ķ��Ƿ����ݣ�ֱ��ɾ����
    
      update SHIP_CARGO_RECORD a
         set a.rec_status  = '5',
             a.update_time = sysdate,
             a.update_code = v_opCode,
             a.remark      = a.remark || ' ǰ̨ɾ��'
       where a.record_no = iv_recordNo;
    
    elsif v_actionTag = 'AddShipPlan' then
    
      select lpad(dbms_random.value(100, 999), 3, '0')
        into iv_randomValue
        from dual;
    
      iv_transForCode := 'CY-' || to_char(sysdate, 'YYYYMMDD') || '-' ||
                         to_char(iv_randomValue);
    
    end if;
  
    --�ύ����
    commit;
  
    if v_actionTag = 'ADD' then
    
      pro_exe_log('P_PROCESS_PK_SHIP_LMS201',
                  v_resCode,
                  v_resMsg,
                  'P_PROCESS_PK_SHIP_LMS201');
      BUSINESS_GROUP_INTF_PCK.make_lms('LMS201',
                                       iv_sequencerecordNo,
                                       iv_lms_Code,
                                       iv_lms_Msg);
    
    end if;
  
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '�����ᵥ��Ϣ�쳣��' || substr(sqlerrm, 1, 100);
      return;
  end;

  --��ȡ�˹����ñ��������ʶ
  function get_man_sample_code return varchar2 is
    v_result     varchar2(10);
    iv_charOrder number(5);
  
  begin
    select trunc(dbms_random.value(1, 27)) into iv_charOrder from dual;
  
    select substr('ABCDEFGHIJKLMNOPQRSTUVWXYZA', iv_charOrder, 1) ||
           to_char(mod(trunc(dbms_random.value(1, 100)), 2) + 1)
      into v_result
      from dual;
  
    return v_result;
  end get_man_sample_code;

  --�жϸô�����Ч��¼�����Ƿ����Ѿ������ļ�¼
  function isBatch(v_shipRecId in varchar2) return varchar2 is
    iv_cnt number(6);
  begin
  
    select count(*)
      into iv_cnt
      from ship_cargo_record a, rlrecordmstqy b
     where (b.cfm_flg = '1' OR b.leave_flg = '1')
       and b.tick_no = a.bill_no
       and a.record_no = to_number(b.car_batch_no)
       and a.ship_rec_id = v_shipRecId;
  
    if iv_cnt > 0 then
      return 'Y';
    else
      return 'N';
    end if;
  end;

  /*  ����ú�������豸�������� ��������������
  0.ѡ��ָ����Ƥ����������Ϊ����ֵ������¼����
  1.���Ƿ��н����е����Σ��еĻ������������ͣ�ˣ�������������ͣ���˹�ͣ��
  2.���Ƿ���������쵽�ˣ��쵽�˸澯�������������澯
  3.ǰ̨�Ѿ������ˣ������ʼ�����ҽ���һ�����λ��ڲ���������
  */
  procedure ship_schedule(v_weightData in varchar2,
                          v_sampleCode out varchar2,
                          v_resCode    out varchar2,
                          v_resMsg     out varchar2) is
  
    iv_json_list       json_list;
    iv_weightData_Json json;
    iv_tmp_jsonObj     json;
    iv_jv              json_value;
    iv_sampleCode      varchar2(30);
    iv_pcState         varchar2(5);
    iv_beltNo          varchar2(10);
    iv_flashQty        number(19, 8);
    iv_cumuQty         number(19, 8);
  
    CONF_BELT_NO varchar2(10);
    iv_bootLine  number(19, 8);
    iv_stopLine  number(19, 8);
  
  begin
    v_resCode    := '0';
    v_resMsg     := 'ok';
    v_sampleCode := '';
  
    --��ʱ�����ε��ã���ʽ�õ�ʱ��Ҫ�ſ�
    --return;
  
    --TEST
    /*    insert into pro_log
      (log_id, exe_msg, log_time)
    values
      (seq_pro_log.nextval, v_weightData, sysdate);
    commit;*/
  
    --��ȡ��ǰ�Ĳ�����
    iv_sampleCode := get_current_sampleCode();
  
    --���û����Ч�Ĳ����룬�����κδ���
    if nvl(length(iv_sampleCode), 0) = 0 then
      return;
    end if;
  
    --��ȡ����Ƥ����
    CONF_BELT_NO := get_work_mode('SHIP_PDC');
    if nvl(length(CONF_BELT_NO), 0) = 0 then
      v_resCode := '1';
      v_resMsg  := 'δ��ȷ���ö���Ƥ����';
      return;
    end if;
  
    --��ȡƤ����������������������ֵ
    iv_bootLine := to_number(nvl(get_work_mode('PDC_BOOT_FLASH_QTY'), '300'));
    iv_stopLine := to_number(nvl(get_work_mode('PDC_STOP_FLASH_QTY'), '20'));
  
    --��ȡָ��Ƥ���ӵ���ֵ
    iv_weightData_Json := json(v_weightData);
    iv_json_list       := json_ext.get_json_list(iv_weightData_Json,
                                                 'VALUE');
  
    for i in 1 .. iv_json_list.count loop
      --ȡjson�������һ��Ԫ��
      iv_jv          := iv_json_list.get_elem(i);
      iv_tmp_jsonObj := json(iv_jv);
    
      iv_beltNo := json_ext.get_string(iv_tmp_jsonObj, 'BELT_NO');
    
      --����ǵ�ǰѡ����Ƥ��
      if iv_beltNo = CONF_BELT_NO then
        iv_flashQty := to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                     'FLASH_QTY'));
        iv_cumuQty  := to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                     'CUMU_QTY'));
      
        --С��Ƥ����˲ʱС��ָ��ֵ���ܣ�����������Լ�ͣ����ͷ
        --������С����˲ʱֵ�������������ͣ�ˣ����Ͳ���������������
        if iv_flashQty >= iv_bootLine then
          select state_value
            into iv_pcState
            from device_current_status a
           where a.device_code = 'PC03';
        
          --����״̬��Ҳ����ͣ�ţ���Ҫ�Զ�����������
          if iv_pcState = '3' then
            insert into machin_cmd_info
              (CMD_REC_ID,
               MACHIN_CODE,
               MACHIN_TYPE,
               COMMAND_CODE,
               SAMPLE_CODE,
               DATA_STATUS,
               INSERT_TIME,
               OP_CODE)
            values
              (seq_machin_cmd_id.nextval,
               'PC03',
               '7', --7Ƥ��
               '1', --1����
               iv_sampleCode,
               '0',
               sysdate,
               '1');
          end if;
        elsif iv_flashQty < iv_stopLine then
          null;
        end if;
      
        --��¼���������鿴�Ƿ����ο쵽�ˣ����˾�Ҫ����
        update_batch_info(iv_sampleCode,
                          iv_beltNo,
                          iv_cumuQty,
                          v_resCode,
                          v_resMsg);
      
      end if;
    end loop;
  
    --�ύ
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := 'ˮ����ú���������ȳ������쳣��' ||
                   substr(sqlerrm, 1, 100);
  end;

  --ִ�е���
  procedure doSchedule(v_deviceNo   in varchar2, --1��1�Ų�����  2��2�Ų�����   3: 3�Ų�����  order by huangyp 2016-1-11
                       v_beltNo     in varchar2,
                       v_cumuQty    in number,
                       v_sampleCode in varchar2,
                       v_resCode    out varchar2,
                       v_resMsg     out varchar2) is
  
    iv_batchOver boolean;
    iv_cnt       number(5);
  begin
    v_resCode    := '0';
    v_resMsg     := 'ok';
    iv_batchOver := false;
  
    --�������μ�����,������鿴�Ƿ��Ѿ��������ν���
    update_batch_info(v_sampleCode,
                      v_beltNo,
                      v_cumuQty,
                      --iv_batchOver,
                      v_resCode,
                      v_resMsg);
  
    if v_resCode <> '0' then
      rollback;
      return;
    else
      --����������Ѿ����������ύԤ���������job������ʽ��
      if iv_batchOver then
        commit_xy_Pre_Command(v_sampleCode, v_resCode, v_resMsg);
      else
        --���������Ƿ�������״̬�����������״̬���򲻹ܣ���������״̬������Ҫȥ����
        select count(1)
          into iv_cnt
          from device_current_status a
         where a.device_code = 'PC0' || v_deviceNo
           and a.state_value = '1';
      
        --������״̬,����Բ�����
        if iv_cnt > 0 then
          return;
        end if;
      
        --�ж�Ԥ�������Ƿ��д������������ڣ��򲻷�����������
        --ȷ���������ִ�������ٳ�����������
        select count(1)
          into iv_cnt
          from pre_machine_cmd_info a
         where sysdate between a.eff_time and a.exp_time
           and a.send_status = '0'
           and a.try_cnt > 0;
      
        if iv_cnt = 0 then
          /*   --���Ͳ�������������
                    pk_register.commitCtrlCmdPro('PC0' || v_deviceNo,
                                                 '7',--Ƥ��
                                                 '1',--����
                                                 v_sampleCode,
                                                 '1',
                                                 v_resCode,
                                                 v_resMsg);
          */
          if v_resCode <> '0' then
            --����ʧ��Ҫ����
            record_warning('PC0' || v_deviceNo,
                           'CY' || v_deviceNo,
                           '1074',
                           '����Ƥ���������Զ�����������ʧ�ܣ�' || v_resMsg);
            return;
          end if;
        end if;
      end if;
    end if;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '����ú���������ȳ������쳣��' ||
                   substr(sqlerrm, 1, 100);
  end;

  --��ȡ��ǰ�Ĳ���������Ϣ
  --v_batchStatus ����״̬ 0������  1���  2ж���� 3��ͣ��
  procedure get_current_batch(v_batchNo     out varchar2,
                              v_sampleCode  out varchar2,
                              v_batchStatus out varchar2) is
    iv_cnt number(5);
  begin
  
    select count(1)
      into iv_cnt
      from batch_no_info a
     where a.batch_no_type = '2'
       and a.is_valid in ('0', '2')
       and a.insert_time > trunc(sysdate - 10);
  
    ----û�����ݣ����Ƕ������
    if iv_cnt = 0 then
      v_batchNo     := null;
      v_sampleCode  := null;
      v_batchStatus := '1';
      return;
    end if;
  
    --ȫ����������Σ���ʼȡ��һ���������Σ�����ȡ�����ĵ�һ������״̬=2��
    select t.batch_no, t.sample_code, t.is_valid
      into v_batchNo, v_sampleCode, v_batchStatus
      from (select a.batch_no,
                   a.sample_code,
                   a.is_valid,
                   row_number() over(order by a.batch_no) ordr
              from batch_no_info a, ship_record b
             where a.batch_no_type = '2'
               and a.train_no = b.ship_trans_no
               and b.status = '5' --������жú�Ĵ�
               and a.is_valid in ('0', '2')
               and a.insert_time > trunc(sysdate - 10)) t
     where t.ordr = 1;
  exception
    when others then
      v_batchNo     := null;
      v_sampleCode  := null;
      v_batchStatus := '1';
  end;

  --��ȡ���κ�
  function get_current_batchNo return varchar2 is
    iv_batchNo varchar2(30);
  begin
  
    --ȫ����������Σ���ʼȡ��һ���������Σ�����ȡ�����ĵ�һ������״̬=2��
    select t.batch_no
      into iv_batchNo
      from (select a.batch_no,
                   a.sample_code,
                   a.is_valid,
                   row_number() over(order by a.batch_no) ordr
              from batch_no_info a, ship_record b
             where a.batch_no_type = '2'
               and a.train_no = b.ship_trans_no
               and b.status = '5' --������жú�Ĵ�
               and a.is_valid in ('0', '2')
               and a.insert_time > trunc(sysdate - 10)) t
     where t.ordr = 1;
  
    return iv_batchNo;
  exception
    when others then
      dbms_output.put_line(sqlerrm);
      return '';
  end;

  --��ȡ���κ�
  function get_current_sampleCode return varchar2 is
    iv_sampleCode varchar2(30);
  begin
  
    select nvl(max(a.sample_code), '')
      into iv_sampleCode
      from batch_no_info a
     where a.batch_no_type = '2'
       and a.is_valid = '2'; --���ڴ��������;
  
    return iv_sampleCode;
  exception
    when others then
      return '';
  end;

  --��ȡ���κ�
  function get_current_samplingCode return varchar2 is
    iv_samplingCode varchar2(30);
  begin
  
    --ȫ����������Σ���ʼȡ��һ���������Σ�����ȡ�����ĵ�һ������״̬=2��
    select t.sampling_code
      into iv_samplingCode
      from (select a.batch_no,
                   a.sample_code,
                   a.sampling_code,
                   a.is_valid,
                   row_number() over(order by a.batch_no) ordr
              from batch_no_info a, ship_record b
             where a.batch_no_type = '2'
               and a.train_no = b.ship_trans_no
               and b.status = '5' --������жú�Ĵ�
               and a.is_valid in ('0', '2')
               and a.insert_time > trunc(sysdate - 10)) t
     where t.ordr = 1;
  
    return iv_samplingCode;
  exception
    when others then
      return '';
  end;

  --��ȡ��ǰ����״̬  0���ڲ���������    1���    2ж���У�δ��ȫ���� ��3 ������ͣ
  function get_batch_status(v_sampleCode in varchar2) return varchar2 is
    iv_status varchar2(2);
  begin
    select a.is_valid
      into iv_status
      from batch_no_info a
     where a.sample_code = v_sampleCode;
  
    return iv_status;
  exception
    when others then
      return '1';
  end;

  --�������μ�����
  procedure update_batch_info(v_sampleCode in varchar2,
                              v_beltNo     in varchar2,
                              v_cumuQty    in number,
                              v_resCode    out varchar2,
                              v_resMsg     out varchar2) is
  
    Pragma Autonomous_Transaction;
  
    iv_cnt          number(5);
    iv_totalQty     number(19, 6);
    iv_batchTickQty number(19, 6);
    iv_isValid      varchar2(2);
    iv_batchNo      varchar2(30);
    WARN_LINE       number(10, 2);
  begin
    --v_batchOver := false;--false��ʾ����û��������ʱ��
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --��ȡ������Ϣ
    begin
      select batch_no, nvl(a.all_tick_qty, 0), is_valid
        into iv_batchNo, iv_batchTickQty, iv_isValid
        from batch_no_info a
       where a.sample_code = v_sampleCode;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '��ȡ���������쳣��' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    --�����������Ƿ��Ѿ�����
    select count(*)
      into iv_cnt
      from ship_batch_qty a
     where a.sample_code = v_sampleCode
       and a.belt_no = v_beltNo
       and a.status = '2';
  
    --��δ��¼��������Ϣ
    if iv_cnt = 0 then
      insert into ship_batch_qty
        (weight_rec_id,
         batch_no,
         sample_code,
         plan_weight,
         belt_no,
         begin_weight,
         begin_time,
         end_weight,
         end_time,
         status)
      values
        (seq_ship_batch_qty.nextval,
         iv_batchNo,
         v_sampleCode,
         iv_batchTickQty,
         v_beltNo,
         v_cumuQty,
         sysdate,
         v_cumuQty,
         sysdate,
         '2');
    else
    
      --�ȼ�¼����
      update ship_batch_qty a
         set a.end_weight = v_cumuQty, a.end_time = sysdate
       where a.sample_code = v_sampleCode
         and a.belt_no = v_beltNo
         and a.status = '2'; --��ǰ����ļ�¼��ֻ����״̬Ϊ2����
    
      --�ۼ�������
      select NVL(SUM(end_weight), 0) - NVL(SUM(begin_weight), 0)
        into iv_totalQty
        from ship_batch_qty a
       where a.sample_code = v_sampleCode;
    
      --��ȡ��ʼ�澯��λ
      WARN_LINE := to_number(get_work_mode('FINISH_DISTANCE_VAL'));
    
      --����200��Ҫ������ʱ��ʼ����
      if iv_batchTickQty - iv_totalQty > 0 and
         iv_batchTickQty - iv_totalQty <= WARN_LINE then
        select count(*)
          into iv_cnt
          from device_error_info a
         where a.error_code = '7087' --7087 ���ο������ʱ��
           and insert_time > sysdate - 30 / 24 / 60;
        -- and error_confirm = '0';
      
        --��ʮ����֮��û�и澯�����ٴ�Ԥ��
        if iv_cnt = 0 then
          record_warning('PC03',
                         'CY1',
                         '7087',
                         '������λ������ɣ���׼������ɲ���');
        end if;
      elsif iv_batchTickQty - iv_totalQty <= 0 then
      
        select count(*)
          into iv_cnt
          from device_error_info a
         where a.error_code = '7087' --7087 ���ο������ʱ��
           and insert_time > sysdate - 30 / 24 / 60;
        --and error_confirm = '0';
      
        --��ʮ����֮��û�и澯�����ٴ�Ԥ��
        if iv_cnt = 0 then
          record_warning('PC03',
                         'CY1',
                         '7087',
                         '������λ�Ѿ���ɣ�������ɲ���');
        end if;
      end if;
    end if;
  
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '����Ƥ�����ۼ����ݷ����쳣��' ||
                   substr(sqlerrm, 1, 100);
      return;
  end;

  --�ύԤ�������ͣ��������ж�ϣ���������
  procedure commit_xy_Pre_Command(v_sampleCode in varchar2,
                                  v_resCode    out varchar2,
                                  v_resMsg     out varchar2) is
  
    --�ֲ�����
    --Pragma Autonomous_Transaction;
    iv_groupId      varchar2(30);
    iv_currTime     date;
    iv_samplingCode varchar2(30);
  
  begin
    v_resCode   := '0';
    v_resMsg    := 'ok';
    iv_currTime := sysdate;
  
    --���������룬����ʱ����
    iv_groupId := to_char(sysdate, 'yyyymmddhh24miss') ||
                  SEQ_SHORT_CYCLE.NEXTVAL;
  
    --����������ֹͣ����
    begin
      --ֹͣ����ֱ�ӷ�
      insert into machin_cmd_info
        (CMD_REC_ID,
         MACHIN_CODE,
         MACHIN_TYPE,
         COMMAND_CODE,
         SAMPLE_CODE,
         DATA_STATUS,
         INSERT_TIME,
         OP_CODE)
      values
        (seq_machin_cmd_id.nextval,
         'PC03',
         '7', --7Ƥ��������
         '2', --1����  2ֹͣ 3��ͣ 4ж��
         v_sampleCode,
         '0', --0δ����
         sysdate,
         '1');
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�ύֹͣ������Ԥ����������쳣:' ||
                     substr(sqlerrm, 1, 100);
        record_warning('CY01',
                       'CY1',
                       '900',
                       '�ύֹͣ������Ԥ����������쳣:' ||
                       substr(sqlerrm, 1, 100));
        return;
    end;
  
    --����ж�����Ԥ���������
    begin
      insert into pre_machine_cmd_info
        (CMD_REC_ID,
         CMD_GROUP,
         MACHIN_CODE,
         MACHIN_TYPE,
         COMMAND_CODE,
         SAMPLE_CODE,
         EFF_TIME,
         EXP_TIME,
         SEND_CMD_TIME, --
         SEND_STATUS,
         TRY_CNT,
         INSERT_TIME,
         OP_CODE,
         REMARK)
      values
        (seq_machin_cmd_id.nextval,
         iv_groupId,
         'PC03',
         '7', --Ƥ��������
         '4', --4ж��
         v_sampleCode,
         iv_currTime + 2 / 24 / 60, --ֹͣ����֮��2���Ӻ�ж������
         SYSDATE + 1, --24Сʱ
         null,
         '0', --0δ����
         5, --���Է���5��
         sysdate, --insert_time
         '1', --opcode
         null);
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�ύж��Ԥ����������쳣:' ||
                     substr(sqlerrm, 1, 100);
        record_warning('PC03',
                       'CY1',
                       '901',
                       '�ύж��Ԥ����������쳣:' ||
                       substr(sqlerrm, 1, 100));
        return;
    end;
  
    --�����������Ԥ���������
    begin
      select a.sampling_code
        into iv_samplingCode
        from batch_no_info a
       where a.sample_code = v_sampleCode;
    
      insert into pre_machine_cmd_info
        (CMD_REC_ID,
         CMD_GROUP,
         MACHIN_CODE,
         MACHIN_TYPE,
         COMMAND_CODE,
         SAMPLE_CODE,
         EFF_TIME,
         EXP_TIME,
         SEND_CMD_TIME, --
         SEND_STATUS,
         TRY_CNT,
         INSERT_TIME,
         OP_CODE,
         REMARK)
      values
        (seq_machin_cmd_id.nextval,
         iv_groupId,
         'ZY02',
         '2', --������
         '1', --1����
         iv_samplingCode,
         iv_currTime + 12 / 24 / 60, --ж�����һ��ʱ������������
         SYSDATE + 1, --24Сʱ
         null,
         '0', --0δ����
         5, --���Է���5��
         sysdate,
         '1',
         null);
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '�ύ����������Ԥ����������쳣:' ||
                     substr(sqlerrm, 1, 100);
        record_warning('ZY02',
                       'ZY1',
                       '902',
                       '�ύ��������Ԥ����������쳣:' ||
                       substr(sqlerrm, 1, 100));
        return;
    end;
  
    --�ύ
    --commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '���ν�������Ԥ����������쳣:' ||
                   substr(sqlerrm, 1, 100);
  end;

  --��job�����Ĵ���ж��������������Ԥ��������
  --����豸�ڹ涨ʱ���ھ������ʹ�Ԥ�����ת����ʽ����д���
  procedure cmd_process_job is
  
    iv_cnt        number(5);
    iv_tryCnt     number(5);
    iv_cmdRecId   number(12);
    iv_machinCode varchar2(20);
    iv_machinType varchar2(10);
    iv_sampleCode varchar2(20);
    iv_cmdCode    varchar2(10);
    iv_groupId    varchar2(20);
    iv_resCode    varchar2(5);
    iv_resMsg     varchar2(1000);
    iv_cmdName    varchar2(100);
  
  begin
    select count(*)
      into iv_cnt
      from pre_machine_cmd_info a
     where a.send_status = '0'
       and sysdate between a.eff_time and a.exp_time;
  
    --ȷ���д���������������
    if iv_cnt = 0 then
      return;
    end if;
  
    --���鰴�����һ�δ���һ������
    begin
      select t.cmd_rec_id,
             t.machin_code,
             t.machin_type,
             t.sample_code,
             t.command_code,
             t.cmd_group,
             t.try_cnt
        into iv_cmdRecId,
             iv_machinCode,
             iv_machinType,
             iv_sampleCode,
             iv_cmdCode,
             iv_groupId,
             iv_tryCnt
        from (select a.cmd_rec_id,
                     a.machin_code,
                     a.machin_type,
                     a.sample_code,
                     a.command_code,
                     a.cmd_group,
                     a.try_cnt,
                     row_number() over(order by cmd_group, cmd_rec_id) rn
                from pre_machine_cmd_info a
               where a.send_status = '0'
                 and sysdate between a.eff_time and a.exp_time) t
       where t.rn = 1;
    exception
      when others then
        pro_exe_log('cmd_process_job',
                    '1',
                    '��ȡԤ��������ʧ�ܣ�' || substr(sqlerrm, 1, 100),
                    '');
        return;
    end;
  
    --��ȡ��������ƣ���־�͸澯ʹ��
    iv_cmdName := translate_cmd(iv_machinType, iv_cmdCode, iv_sampleCode);
  
    --�Ѿ����Խ�������Ҫ����������ϣ��˹�����
    if iv_tryCnt <= 0 then
      record_warning(iv_machinCode,
                     replace(iv_machinCode, '0', ''), --flowid
                     '1084', --�ݶ�
                     iv_cmdName || '����Զ�η���ʧ�ܣ����˹�����');
    
      pro_exe_log('cmd_process_job',
                  '1',
                  iv_cmdName || '����Զ�η���ʧ�ܣ����˹�����',
                  '');
    
      --������������
      update pre_machine_cmd_info a
         set a.send_cmd_time = sysdate,
             a.send_status   = '2',
             a.remark        = a.remark ||
                               ' ��γ��Է�������ʧ�ܣ�ʣ����������',
             a.update_time   = sysdate
       where a.cmd_group = iv_groupId
         and a.send_status = '0';
    
      commit;
      return;
    end if;
  
    --�ж��豸�Ƿ�߱��·����������
    /*  pk_register.check_device_status(iv_machinCode,
    iv_machinType,
    iv_cmdCode,
    iv_sampleCode,--������,��Ҫȷ��
    iv_resCode,
    iv_resMsg);*/
  
    --���߱��·����������
    if iv_resCode <> '0' then
      update pre_machine_cmd_info a
         set a.send_cmd_time = sysdate,
             a.remark        = '����������������,�ȴ��ٴγ���:' || iv_resMsg,
             a.update_time   = sysdate,
             a.try_cnt       = a.try_cnt - 1
       where a.cmd_rec_id = iv_cmdRecId;
    
      record_warning(iv_machinCode,
                     replace(iv_machinCode, '0', ''),
                     '1085',
                     iv_cmdName || '�����ʧ��:' || iv_resMsg);
    
      pro_exe_log('cmd_process_job',
                  '1',
                  iv_cmdName || '�����ʧ��:' || iv_resMsg,
                  '');
    
      return;
    end if;
  
    --ת����
    begin
      --ֹͣƤ������������, ֹͣ����һ���Է�����,�м���������
      if iv_machinType in ('1', '7') and iv_cmdCode = '2' then
        insert into machin_cmd_info
          (cmd_rec_id,
           machin_code,
           machin_type,
           command_code,
           sample_code,
           send_cmd_time,
           data_status,
           insert_time,
           op_code)
          select a.cmd_rec_id,
                 a.machin_code,
                 a.machin_type,
                 a.command_code,
                 a.sample_code,
                 sysdate,
                 '0',
                 sysdate,
                 '1'
            from pre_machine_cmd_info a
           where a.cmd_group = iv_groupId
             and a.send_status = '0'
             and a.machin_type in ('1', '7')
             and command_code = '2';
      
      else
      
        insert into machin_cmd_info
          (cmd_rec_id,
           machin_code,
           machin_type,
           command_code,
           sample_code,
           send_cmd_time,
           data_status,
           insert_time,
           op_code)
          select a.cmd_rec_id,
                 a.machin_code,
                 a.machin_type,
                 a.command_code,
                 a.sample_code,
                 sysdate,
                 '0',
                 sysdate,
                 '1'
            from pre_machine_cmd_info a
           where a.cmd_rec_id = iv_cmdRecId;
      
      end if;
    exception
      when others then
        rollback;
        pro_exe_log('cmd_process_job',
                    '1',
                    'ת��ʽ�����ʧ�ܣ�' || substr(sqlerrm, 1, 100),
                    '');
        record_warning(iv_machinCode,
                       replace(iv_machinCode, '0', ''),
                       '1086',
                       iv_cmdName || '����ת��ʽ��ʧ��:' || iv_resMsg);
        update pre_machine_cmd_info a
           set a.send_cmd_time = sysdate,
               a.remark        = iv_cmdName || '����ת��ʽ��ʧ��:' ||
                                 iv_resMsg,
               a.update_time   = sysdate,
               a.try_cnt       = a.try_cnt - 1
         where a.cmd_rec_id = iv_cmdRecId;
        commit;
        return;
    end;
  
    --Ԥ����������Ϊ���
    update pre_machine_cmd_info a
       set a.send_cmd_time = sysdate,
           a.send_status   = '1',
           a.remark        = '��ת��ʽ�����',
           a.update_time   = sysdate
     where a.cmd_rec_id = iv_cmdRecId;
  
    pro_exe_log('cmd_process_job',
                '0',
                '���Ԥ�������' || iv_machinCode || ',' || iv_cmdCode,
                '');
    commit;
  exception
    when others then
      rollback;
      pro_exe_log('cmd_process_job',
                  '1',
                  'ִ��ʧ�ܣ�' || substr(sqlerrm, 1, 100),
                  '');
    
      record_warning(nvl(iv_machinCode, '-'),
                     'CY1',
                     '1087',
                     'job��������ת��ʽ��ʧ��:' || substr(sqlerrm, 1, 100));
  end;

  --��¼�澯��Ϣ
  procedure record_warning(v_machinCode in varchar2,
                           v_flowId     in varchar2,
                           v_errorCode  in varchar2,
                           v_errorDesc  in varchar2) is
  
    Pragma Autonomous_Transaction;
  begin
    begin
      insert into device_error_info
        (error_rec_id,
         machin_code,
         flow_id,
         error_code,
         error_time,
         error_dec,
         data_status,
         error_confirm,
         insert_time,
         op_code)
      values
        (seq_device_error_info.nextval,
         v_machinCode,
         v_flowId,
         v_errorCode,
         sysdate,
         v_errorDesc,
         '0',
         '0',
         sysdate,
         '1');
    
      commit;
    exception
      when others then
        rollback;
    end;
  
  end;

  function translate_cmd(v_machineType in varchar2,
                         v_cmdCode     in varchar2,
                         v_sampleCode  in varchar2) return varchar2 is
    iv_desc      varchar2(100);
    iv_batchName varchar2(100);
  begin
    if v_machineType in ('1', '7') then
      select '��' || nvl(max(substr(a.batch_no, 14, 1)), '') || '����'
        into iv_batchName
        from batch_no_info a
       where a.sample_code = v_sampleCode;
    
      if v_cmdCode = '1' then
        iv_desc := '����������(' || iv_batchName || ')';
      elsif v_cmdCode = '2' then
        iv_desc := 'ֹͣ������(' || iv_batchName || ')';
      elsif v_cmdCode = '4' then
        iv_desc := '������ж��(' || iv_batchName || ')';
      end if;
    elsif v_machineType in (2) then
      select '��' || nvl(max(substr(a.batch_no, 14, 1)), '') || '����'
        into iv_batchName
        from batch_no_info a
       where a.sampling_code = v_sampleCode;
    
      if v_cmdCode = '1' then
        iv_desc := '����������(' || iv_batchName || ')';
      elsif v_cmdCode = '4' then
        iv_desc := '����Ͷ����(' || iv_batchName || ')';
      end if;
    end if;
  
    return iv_desc;
  exception
    when others then
      return 'δ֪';
  end;

  --���õ�ǰжú��
  procedure setCurrentShip(v_shipRecID in varchar2,
                           v_opCode    in varchar2,
                           v_resCode   out varchar2,
                           v_resMsg    out varchar2) is
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --��ԭ��������Ϊ�ǵ�ǰ
    update ship_record a set a.status = '6' where a.status = '5';
  
    update ship_record a
       set a.status      = '5',
           a.update_code = v_opCode,
           a.update_time = sysdate,
           a.remark      = a.remark || to_char(sysdate, 'yyyymmddhh24miss') ||
                           '-����Ϊ��ǰжú��'
     where a.ship_rec_id = to_number(v_shipRecID);
  
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '���õ�ǰжú���쳣��' || substr(sqlerrm, 1, 100);
    
  end;

  --��������ú������Ϣ
  procedure operate_Batch_Status(v_jsonStr in varchar2,
                                 v_opCode  in varchar2,
                                 v_resCode out varchar2,
                                 v_resMsg  out varchar2) is
    iv_jsonParm     json;
    iv_sampleCode   varchar2(20);
    iv_status       varchar2(5);
    iv_totalNetQty  number(19, 6);
    iv_totalQty     number(19, 6);
    iv_batchTickQty number(19, 6);
    iv_batchNo      varchar2(30);
    iv_beginQty     number(19, 6);
    iv_cnt          number(5);
    CONF_BELT_NO    varchar2(10);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    iv_jsonParm   := json(v_jsonStr);
    iv_sampleCode := json_ext.get_string(iv_jsonParm, 'sampleCode');
    iv_status     := json_ext.get_string(iv_jsonParm, 'status');
  
    begin
      select batch_no, nvl(a.all_tick_qty, 0)
        into iv_batchNo, iv_batchTickQty
        from batch_no_info a
       where a.sample_code = iv_sampleCode;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := 'ǰ̨��������ʱ����ȡ���������쳣��' ||
                     substr(sqlerrm, 1, 100);
        return;
    end;
    --����˵�� 1:������2:��ʼ�������3:��ͣ��9:ɾ��
    --����״̬˵�� 1���ѽ�����2�������У�3����ͣ��
    --�����������Σ������
    if iv_status = '1' then
      -------------------------------------------------������Σ�ֹͣ��ж������
    
      update ship_batch_qty a
         set a.status   = '1',
             a.end_time = sysdate,
             a.remark   = a.remark || '|ǰ̨�����������μ�����'
       where a.sample_code = iv_sampleCode
         and a.status = '2';
    
      select NVL(SUM(end_weight), 0) - NVL(SUM(begin_weight), 0)
        into iv_totalNetQty
        from ship_batch_qty a
       where a.sample_code = iv_sampleCode;
    
      update batch_no_info a
         set a.all_net_qty = iv_totalNetQty,
             a.update_code = v_opCode,
             a.update_time = sysdate,
             a.remark      = a.remark || '|��¼���ν���жú��λ(' ||
                             v_opCode || ',' ||
                             to_char(sysdate, 'yyyymmddhh24miss') || ')'
       where a.sample_code = iv_sampleCode;
      --������commit,�Է���ʧ
      commit;
    
      update batch_no_info a
         set a.is_valid    = '1',
             a.update_code = v_opCode,
             a.update_time = sysdate,
             a.remark      = a.remark || '|ǰ̨�����������Ρ�'
       where a.sample_code = iv_sampleCode;
    
      --�������ύ��������
      commit_xy_Pre_Command(iv_sampleCode, v_resCode, v_resMsg);
    elsif iv_status = '2' then
      ------------------------------------------------������ʼ
    
      --���ˮ�˲������豸״̬,add by zhangwj--20170331
      /*  pk_register.check_device_status('PC03',
      '7',
      '1',
      iv_sampleCode,
      v_resCode,
      v_resMsg);*/
      if v_resCode <> '0' then
        -- return;
        null;
      end if;
    
      --�����������ڽ����е�����
      begin
        update batch_no_info a
           set a.is_valid    = '3', --�����е�������ͣ hxl20160810
               a.update_code = v_opCode,
               a.update_time = sysdate,
               a.remark      = a.remark || '|������ͣ����(' || v_opCode || ',' ||
                               to_char(sysdate, 'yyyymmddhh24miss') || ')'
         where a.is_valid = '2'
           and a.sample_code <> iv_sampleCode;
      
        update ship_batch_qty a
           set a.status   = '3', --�����е�������ͣ hxl20160810
               a.end_time = sysdate,
               a.remark   = a.remark || '|������ͣ����(' || v_opCode || ',' ||
                            to_char(sysdate, 'yyyymmddhh24miss') || ')'
         where a.status = '2';
        --  and a.sample_code <> iv_sampleCode; �κν��е����ζ�����ͣ
      
        --��ʼ(����)���Σ���ʼ������������
        update batch_no_info a
           set a.is_valid    = '2',
               a.update_code = v_opCode,
               a.update_time = sysdate,
               a.remark      = a.remark || '|ǰ̨������ʼ���δ���'
         where a.sample_code = iv_sampleCode;
      
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := 'ǰ̨��ʼ������������޸�����״̬�쳣:' ||
                       substr(sqlerrm, 1, 100);
      end;
    
      --��ʼ�͸�����������������
      begin
        insert into machin_cmd_info
          (CMD_REC_ID,
           MACHIN_CODE,
           MACHIN_TYPE,
           COMMAND_CODE,
           SAMPLE_CODE,
           DATA_STATUS,
           INSERT_TIME,
           OP_CODE)
        values
          (seq_machin_cmd_id.nextval,
           'PC03',
           '7', --7Ƥ��
           '1', --1����  2ֹͣ 3��ͣ 4ж��
           iv_sampleCode,
           '0', --0δ����
           sysdate, --insert_time
           '1');
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�ύֹͣ������Ԥ����������쳣:' ||
                       substr(sqlerrm, 1, 100);
          record_warning('CY01',
                         'CY1',
                         '900',
                         '�ύ����������������쳣:' ||
                         substr(sqlerrm, 1, 100));
          return;
      end;
    elsif iv_status = '3' then
      -------------------------------------------------��ͣ����
      update batch_no_info a
         set a.is_valid    = '3',
             a.update_code = v_opCode,
             a.update_time = sysdate,
             a.remark      = a.remark || '|ǰ̨������ͣ���Ρ�'
       where a.sample_code = iv_sampleCode;
      update ship_batch_qty a
         set a.status   = '3', --�����е�������ͣ hxl20160810
             a.end_time = sysdate,
             a.remark   = a.remark || '|ǰ̨������ͣ����(' || v_opCode || ',' ||
                          to_char(sysdate, 'yyyymmddhh24miss') || ')'
       where a.sample_code = iv_sampleCode
         and a.status = '2';
    
      --��ͣ�͸���������ֹͣ���� add by zwj
      begin
        insert into machin_cmd_info
          (CMD_REC_ID,
           MACHIN_CODE,
           MACHIN_TYPE,
           COMMAND_CODE,
           SAMPLE_CODE,
           DATA_STATUS,
           INSERT_TIME,
           OP_CODE)
        values
          (seq_machin_cmd_id.nextval,
           'PC03',
           '7', --7Ƥ��
           '2', --1����  2ֹͣ 3��ͣ 4ж��
           '',
           '0', --0δ����
           sysdate, --insert_time
           '1');
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '�ύֹͣ������������쳣:' ||
                       substr(sqlerrm, 1, 100);
          record_warning('CY01',
                         'CY1',
                         '900',
                         '�ύֹͣ������������쳣:' ||
                         substr(sqlerrm, 1, 100));
          return;
      end;
    
      /*     --�������ύ��������
      commit_xy_Pre_Command(iv_sampleCode,
                            v_resCode,
                            v_resMsg);*/
    
    elsif iv_status = '9' then
      -------------------------------------------------ɾ������
      --ɾ��������
      delete from batch_no_info a where a.sample_code = iv_sampleCode;
    end if;
  
    commit;
  exception
    when others then
      rollback;
      v_rescode := '1';
      v_resmsg  := '����ˮ��ú�����쳣��' || substr(sqlerrm, 1, 100);
      return;
  end;

  --���Ե��ó���ʹ��
  procedure test is
    iv_pdc_data   varchar2(300);
    iv_sampleCode varchar2(30);
    iv_resCode    varchar2(30);
    iv_resMsg     varchar2(300);
  begin
    iv_pdc_data := '{"DEVICE": "SHIP_BELT_DATA",' || '"SAMPLE_CODE": "",' ||
                   '"VALUE":[{"BELT_NO": "A1",' || '"FLASH_QTY": "500.2",' ||
                  --'"CUMU_QTY": "30000"},'||
                  --'{"BELT_NO": "A2",'||
                  --'"FLASH_QTY": "500.2",'||
                  --'"CUMU_QTY": "30000"},'||
                  --'{"BELT_NO": "B1",'||
                  --'"FLASH_QTY": "500.2",'||
                  --'"CUMU_QTY": "30000"},'||
                  --'{"BELT_NO": "B2",'||
                  --'"FLASH_QTY": "500.2",'||
                   '"CUMU_QTY": "1430874"}]}';
  
    ship_schedule(iv_pdc_data, iv_sampleCode, iv_resCode, iv_resMsg);
    dbms_output.put_line('pdc_data:' || iv_pdc_data);
    dbms_output.put_line('sampleCode:' || iv_sampleCode);
    dbms_output.put_line('resCode:' || iv_resCode);
    dbms_output.put_line('resMsg:' || iv_resMsg);
  
  end;

  procedure ship_schedule_job is
    iv_pdc_data   varchar2(300);
    iv_sampleCode varchar2(30);
    iv_flash_qty  varchar2(30);
    iv_cumu_qty   varchar2(30);
    iv_resCode    varchar2(30);
    iv_resMsg     varchar2(300);
  begin
  
    select nvl(max(state_value), '0')
      into iv_cumu_qty
      from device_current_status a
     where a.device_code = 'PDC_M0_LJ';
  
    select nvl(max(state_value), '0')
      into iv_flash_qty
      from device_current_status a
     where a.device_code = 'PDC_M0_SS';
  
    iv_pdc_data := '{
  	"DEVICE": "SHIP_BELT_DATA",
  	"SAMPLE_CODE": "",
  	"VALUE": [{
  		"BELT_NO": "B1",
  		"FLASH_QTY": "' || iv_flash_qty || '",
  		"CUMU_QTY": "' || iv_cumu_qty || '"
  	}]}';
  
    /*    {
    "DEVICE": "SHIP_BELT_DATA",
    "SAMPLE_CODE": "",
    "VALUE": [{
      "BELT_NO": "B1",
      "FLASH_QTY": "0.00",
      "CUMU_QTY": "348118.00"
    }]}*/
  
    ship_schedule(iv_pdc_data, iv_sampleCode, iv_resCode, iv_resMsg);
  
  exception
    when others then
      rollback;
      iv_resCode := '1';
      iv_resCode := '���˵����쳣��' || substr(sqlerrm, 1, 100);
      return;
  end;

end pk_ship;
/
