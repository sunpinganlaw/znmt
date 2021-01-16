create or replace package BUSINESS_GROUP_INTF_PCK is

  SENDER_CODE   CONSTANT VARCHAR2(40) := '513'; --���ͷ��̶�����
  RECEIVER_CODE CONSTANT VARCHAR2(40) := '513'; --���շ�������)�̶�����

  BUSI_TYPE CONSTANT VARCHAR2(40) := '1'; --����ͷ�̶�Ϊ1(��װ�䣩

  procedure P_PROCESS_0X21(v_dataInfo in varchar2,
                           v_cmdInfo  out varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2);

  procedure P_PROCESS_0X23(v_dataInfo in varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2);

  ---�ͺ��ؽ������ĵ������
  procedure main(v_reportInfo in varchar2,
                 v_reportAttr out varchar2,
                 v_reportXml  out clob,
                 v_resCode    out varchar2,
                 v_resMsg     out varchar2);

  --- ��װ�䱨��
  procedure P_INTF_STORAGE(v_reportInfo in json,
                           v_reportAttr out json,
                           v_reportXml  out clob,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2);

  procedure append_xml(v_clob in out clob, v_str varchar2);

  procedure add_TransConta(msgString in clob,
                           v_resCode out varchar2,
                           v_resMsg  out varchar2);

  --- ��������������¼
  procedure P_PRINT_RECORD(v_reportInfo in varchar2,
                           v_reportJson out varchar2,
                           v_reportXml  out varchar2,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2);

end BUSINESS_GROUP_INTF_PCK;
/
create or replace package body BUSINESS_GROUP_INTF_PCK is

  procedure P_PROCESS_0X21(v_dataInfo in varchar2,
                           v_cmdInfo  out varchar2,
                           v_resCode  out varchar2,
                           v_resMsg   out varchar2) is
  
    iv_AREA_ID           varchar2(30); --��վ��
    iv_CHNL_NO           varchar2(30); --ͨ����
    iv_I_E_TYPE          varchar2(30); --��������ʶ:I������E����
    iv_SEQ_NO            varchar2(30); --���к�
    iv_DR_IC_NO          varchar2(30); --IC����
    iv_GROSS_WT          varchar2(30); --����
    iv_FILE_TIME         varchar2(30); --ʱ��
    iv_CONTA_ID_F        varchar2(30); --ǰ���
    iv_CONTA_ID_B        varchar2(30); --�����
    iv_CAR_EC_NO         varchar2(30); --���ӳ���
    iv_VE_NAME           varchar2(30); --���ƺ�
    iv_BAR_CODE          varchar2(300); --������ֵ
    iv_PHOTO_GUID        varchar2(3000); --ͼƬ���
    iv_PHOTO_PERSPECTIVE varchar2(3000); --ͼƬ���
  
    iv_CHK_RESULT     varchar2(30); --���н����Y���У�N������
    iv_LED_INFO       varchar2(3000); --������ʾ
    iv_BL_NO          varchar2(30); --�ᵥ��
    iv_GOODS_PROPERTY varchar2(30); --����ó��ʶ��NM/WM��
    iv_IM_CUSTOM      varchar2(30); --�Ƿ���Ҫ���͸����أ���óY,��óN��
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
    iv_stockName  varchar2(30);
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
  
    -----����ʱ����в���
    select to_char(sysdate, 'yyyy-MM-dd HH24:mi:ss')
      into iv_timeString
      from dual;
  
    --��������������Ϣ
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
        v_resMsg  := '��������21�����쳣��' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('SEQ_NO', iv_SEQ_NO);                    --- add by jiaruya
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        iv_cmd_jsonObj.put('IN_TIME',
                           to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
      
        iv_cmd_jsonObj.put('OUT_TIME',
                           to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
        v_cmdInfo := iv_cmd_jsonObj.to_char();
        pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
        return;
    end;
    
    iv_cmd_jsonObj.put('SEQ_NO', iv_SEQ_NO);                    --- add by jiaruya
   
    ---����δʶ��,����
    if length(iv_VE_NAME) = 0 then
      v_resCode     := '1001';
      v_resMsg      := '����δʶ��';
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      iv_cmd_jsonObj.put('IN_TIME',
                         to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
      iv_cmd_jsonObj.put('OUT_TIME',
                         to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      return;
    end if;
  
    --��������ע����Ϣ���в�ѯ������׼������Ϣ---
    select nvl(max(a.std_qty) * 1000, 99.99)
      into iv_std_qty
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    --����ע����в����������ᵥ��Ϣ
    select nvl(max(a.trade_type), '99'),
           nvl(max(a.tick_qty), 0),
           nvl(max(a.BILL_NO), 99) ��nvl(max(a.SAMPLE_TYPE), 99)
      into iv_trade_type, iv_netAll, iv_BL_NO��iv_sampleType
      from ship_cargo_record a, transmst b
     where b.car_id = iv_VE_NAME
       and a.RECORD_NO = b.batch_no
       and a.rec_status in ('1');
  
    if iv_trade_type not in ('0', '1', '2', '3') then
    
      v_resCode := '1002';
      v_resMsg  := '��ǰ���Ų鲻���������ᵥ����';
    
      iv_CHK_RESULT := 'N';
      iv_LED_INFO   := v_resMsg;
      iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
      iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('IN_TIME',
                         to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
      iv_cmd_jsonObj.put('OUT_TIME',
                         to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', iv_CHK_RESULT, v_cmdInfo, v_dataInfo);
      return;
    end if;
  
    select nvl(max(a.org_no), 99)
      into iv_carrierNo
      from rlcarmst a
     where a.car_id = iv_VE_NAME;
  
    if iv_trade_type in ('0', '1') then
      --��ó
      iv_GOODS_PROPERTY := 'WM';
      iv_IM_CUSTOM      := 'Y';
    
    end if;
  
    if iv_trade_type in ('2', '3') then
      --��ó
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
     where B.TICK_NO = iv_BL_NO;
  
    ---�ͻ�����-------------
    select nvl(max(a.VEN_NAME), 99), nvl(max(a.VEN_NO), 99)
      into iv_vendorNmae, iv_vendorNo
      from vendor_info a, ship_cargo_record b
     where a.VEN_NO = b.VEN_NO
       and b.BILL_NO = iv_BL_NO;
  
    ---������������-------------
    --- stockArea  ---> stockNmae
    select nvl(max(a.GOOD_NAME), 99), nvl(max(b.STOCK_NAME), 99)
      into iv_goodName, iv_stockName
      from good_info a, ship_cargo_record b
     where a.GOOD_NO = b.GOOD_NO
       and b.BILL_NO = iv_BL_NO;
  
    ---�ջ���λ���------------
    select nvl(max(b.CUSTOMER_NO), 99)
      into iv_customerNo
      from ship_cargo_record b
     where b.BILL_NO = iv_BL_NO;
  
    --Add by jiaruya:�ᵥ������Ϣ
    iv_cmd_jsonObj.put('OWNER', iv_vendorNmae); --����
    iv_cmd_jsonObj.put('GOOD_TYPE', iv_goodName); --ú��
    iv_cmd_jsonObj.put('POSITION', iv_stockName); --��λ
    iv_cmd_jsonObj.put('BL_WEIGHT', to_char(iv_netAll / 1000)); --�ᵥ������
  
    --End
  
    begin
      ---����ͳһ����
    
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
    
      ---����ͨ�� ����ظ������к� Ҫ����ɾ��ԭ��������Ϣ
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
    
      ----����������������ж�����ͣ��س����룩----------------------------
      if iv_I_E_TYPE = 'I' and iv_sampleType = '1' then
      
        ---���ϴ���excel������Ϣ����һ�����õ�������Ϣ-------------
        select nvl(max(a.point_tag), '99')
          into iv_BAR_CODE
          from point_cfg a, ship_cargo_record b
         where a.point_name = b.RECORD_NO
           and a.STATUS = '0'
           and b.BILL_NO = iv_BL_NO;
      
        if iv_BAR_CODE = '99' then
        
          v_resCode := '1002';
          v_resMsg  := '�޿��õ�������Ϣ';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        iv_recodNo := SEQ_RLRECORDMSTQY.NEXTVAL;
      
        insert into rlrecordmstqy
          (record_no, --TODO ��ȷ�� �볧��ˮ����������
           TICK_NO�� CARD_TYP,
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
          (iv_recodNo, --TODO ��ȷ�� �볧��ˮ��
           iv_BL_NO,
           iv_cardTyp,
           iv_BAR_CODE,
           iv_VE_NAME,
           iv_vendorNo,
           iv_customerNo,
           iv_shipId,
           iv_goodNo�� iv_carrierNo,
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
    
      ----����������������ж�����ͣ��ᳵ��ȥ��----------------------------
      if iv_I_E_TYPE = 'E' and iv_sampleType = '1' then
      
        select nvl(max(a.record_no), 0)
          into iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
          and a.leave_flg  in ('0','N')
          and a.cfm_flg='Y'
          and a.cz_dtm>= trunc(sysdate - 1);
      
        select nvl(max(a.MZ_QTY), 0), nvl(max(a.CARD_ID), 99)
          into iv_temWeight, iv_BAR_CODE
          from RLRECORDMSTQY a
         where a.record_no = iv_recNo;
      
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := 'û�н�����¼';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        end if;
      
        if (iv_temWeight - to_number(iv_GROSS_WT)) <= 0 then
          v_resCode := '202';
          v_resMsg  := '�������Ͳ����������������ڽ�������';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
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
          v_resMsg  := '�����ᵥʣ���������';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
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
      
        ----ֱ�ӽ����ᵥ��ʣ������С��100��----
        if (iv_netAll - iv_netNow + to_number(iv_GROSS_WT) - iv_temWeight) = 0 then
          update ship_cargo_record a
             set REC_STATUS = '3', fact_time = sysdate
           where a.BILL_NO = iv_BL_NO;
        
        end if;
      
        iv_recodNo := iv_recNo;
      
      end if;
    
      ----���������������������ͣ��ᳵ���룩----------------------------
    
      if iv_I_E_TYPE = 'I' and iv_sampleType = '0' then
      
        ---���ϴ���excel������Ϣ����һ�����õ�������Ϣ-------------
        select nvl(max(a.point_tag), '99')
          into iv_BAR_CODE
          from point_cfg a, ship_cargo_record b
         where a.point_name = b.RECORD_NO
           and a.STATUS = '0'
           and b.BILL_NO = iv_BL_NO;
      
        if iv_BAR_CODE = '99' then
        
          v_resCode := '1002';
          v_resMsg  := '�޿��õ�������Ϣ';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        iv_recodNo := SEQ_RLRECORDMSTQY.NEXTVAL;
        insert into rlrecordmstqy
          (record_no, --TODO ��ȷ�� �볧��ˮ����������
           TICK_NO�� CARD_TYP,
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
          (iv_recodNo, --TODO ��ȷ�� �볧��ˮ��
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
    
      ----���������������������ͣ��س���ȥ��----------------------------
      if iv_I_E_TYPE = 'E' and iv_sampleType = '0' then
      
        select nvl(max(a.record_no), 0)
          into iv_recNo
          from RLRECORDMSTQY a
         where a.car_id = iv_VE_NAME
           and a.leave_flg  in ('0','N')
           and  a.CFM_FLG='Y'
           and a.jq_dtm >= trunc(sysdate - 1);
      
        select nvl(max(a.PZ_QTY), 0), nvl(max(a.CARD_ID), 99)
          into iv_temWeight, iv_BAR_CODE
          from RLRECORDMSTQY a
         where a.record_no = iv_recNo;
      
        /*  select nvl(max(a.PZ_QTY), 0), nvl(max(a.record_no), 99),nvl(max(a.CARD_ID), 99)
         into iv_temWeight, iv_recNo,iv_BAR_CODE
         from RLRECORDMSTQY a
        where a.car_id = iv_VE_NAME
          and a.LEAVE_FLG = '0'
          and rownum = 1
        order by a.insert_time desc;*/
      
        if iv_temWeight = 0 then
        
          v_resCode := '201';
          v_resMsg  := 'û�н�����¼';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        end if;
      
        if (to_number(iv_GROSS_WT) - iv_temWeight) <= 0 then
          v_resCode := '202';
          v_resMsg  := '��������С�ڽ�������';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
          return;
        
        end if;
      
        if (iv_netAll - iv_netNow) <
           (to_number(iv_GROSS_WT) - iv_temWeight) then
        
          /*  update ship_cargo_record a
            set REC_STATUS = '3', fact_time = sysdate
          where a.BILL_NO = iv_BL_NO;*/
        
          v_resCode := '203';
          v_resMsg  := '�����ᵥʣ���������';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          v_cmdInfo := iv_cmd_jsonObj.to_char();
          pro_exe_log('P_PROCESS_0X21',
                      iv_CHK_RESULT,
                      v_cmdInfo,
                      v_dataInfo);
        
          commit;
          return;
        end if;
      
        ----��������������ȥ-------
        if to_number(iv_GROSS_WT) > iv_std_qty then
          v_resCode := '204';
          v_resMsg  := '�����������ڱ�׼����';
        
          iv_CHK_RESULT := 'N';
          iv_LED_INFO   := v_resMsg;
          iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
          iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
          iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
          iv_cmd_jsonObj.put('IN_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
          iv_cmd_jsonObj.put('OUT_TIME',
                             to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
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
      
        ----ֱ�ӽ����ᵥ��ʣ������С��100��----
        if (iv_netAll - iv_netNow - to_number(iv_GROSS_WT) + iv_temWeight) = 0 then
          update ship_cargo_record a
             set REC_STATUS = '3', fact_time = sysdate
           where a.BILL_NO = iv_BL_NO;
        
        end if;
      
      end if;
    
      ---���ύһ��������Ϣ----
      commit;
    
      ---�ٲ�һ����εĹ�����Ϣ----
      select *
        into vrow
        from rlrecordmstqy i
       where i.RECORD_NO = iv_recodNo;
    
      select sum(B.NET_QTY)
        into iv_netNow
        from RLRECORDMSTQY B
       where B.TICK_NO = iv_BL_NO;
    
      --Add by jiaruya     ������Ϣ
      iv_cmd_jsonObj.put('BARE_WEIGHT', to_char(vrow.pz_qty / 1000));
      iv_cmd_jsonObj.put('GROSS_WEIGHT', to_char(vrow.mz_qty / 1000));
      iv_cmd_jsonObj.put('NET_WEIGHT', to_char(vrow.net_qty / 1000));
    
      iv_cmd_jsonObj.put('LEAVE_WEIGHT',
                         to_char((iv_netAll - iv_netNow) / 1000)); --�ᵥʣ��������
      iv_cmd_jsonObj.put('BAR_CODE', iv_BAR_CODE); --������Ϣ      
      --End
    
      iv_cmd_jsonObj.put('CHK_RESULT', 'Y');
      iv_cmd_jsonObj.put('LED_INFO', '����ͨ��');
      iv_cmd_jsonObj.put('BL_NO', iv_BL_NO);
      iv_cmd_jsonObj.put('GOODS_PROPERTY', iv_GOODS_PROPERTY);
      iv_cmd_jsonObj.put('IM_CUSTOM', iv_IM_CUSTOM);
      iv_cmd_jsonObj.put('BUSI_TYPE', BUSI_TYPE);
      iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
      iv_cmd_jsonObj.put('IN_TIME',
                         to_char(vrow.insert_time, 'YYYY-MM-DD hh24:mi:ss'));
      iv_cmd_jsonObj.put('OUT_TIME',
                         to_char(vrow.update_time, 'YYYY-MM-DD hh24:mi:ss'));
      v_cmdInfo := iv_cmd_jsonObj.to_char();
      pro_exe_log('P_PROCESS_0X21', 'Y', v_cmdInfo, v_dataInfo);
    
      --��������Ϣ����Ϊ��ʹ��
    /*  update point_cfg set STATUS = '1' where POINT_TAG = iv_BAR_CODE;*/
       select nvl(max(b.RECORD_NO),0) 
         into  iv_recordNo
          from ship_cargo_record b 
          where b.BILL_NO = iv_BL_NO;
      update point_cfg set STATUS = '1' where POINT_TAG = iv_BAR_CODE and POINT_NAME=to_char(iv_recordNo);
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '����21����ʧ�ܣ�' || substr(sqlerrm, 1, 100);
      
        iv_CHK_RESULT := 'N';
        iv_LED_INFO   := v_resMsg;
        iv_cmd_jsonObj.put('CHK_RESULT', iv_CHK_RESULT);
        iv_cmd_jsonObj.put('LED_INFO', iv_LED_INFO);
        iv_cmd_jsonObj.put('REC_TIME', iv_timeString);
        iv_cmd_jsonObj.put('IN_TIME',
                           to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
        iv_cmd_jsonObj.put('OUT_TIME',
                           to_char(sysdate, 'YYYY-MM-DD hh24:mi:ss'));
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
    iv_AREA_ID        varchar2(30); --��վ��
    iv_CHNL_NO        varchar2(30); --ͨ����
    iv_I_E_TYPE       varchar2(30); --��������ʶ:I������E����
    iv_SEQ_NO         varchar2(30); --���к�
    iv_DR_IC_NO       varchar2(30); --IC����
    iv_EXECUTE_RESULT varchar2(30); --ǰ�˿���ִ�н��
    iv_ERROR_INFO     varchar2(180); --������Ϣ
    iv_FILE_TIME      varchar2(30); --ʱ��
  
    iv_dataInfo_Json json;
    iv_cnt           number(3);
  begin
  
    v_resCode        := '0';
    v_resMsg         := 'ok';
    iv_cnt           := 0;
    iv_dataInfo_Json := json(v_dataInfo);
  
    --��������������Ϣ
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
        v_resMsg  := '��������23�����쳣��' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    begin
      ---����ͳһ����
    
      if iv_I_E_TYPE = 'I' then
        select count(1)
          into iv_cnt
          from rlrecordmstqy a
         where a.IN_NO = iv_SEQ_NO;
      
        if iv_cnt = 0 then
          v_resCode := '1';
          v_resMsg  := iv_SEQ_NO || '�Ҳ���������¼';
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
          v_resMsg  := iv_SEQ_NO || '�Ҳ���������¼';
          pro_exe_log('P_PROCESS_0X23', v_resCode, v_resMsg, v_dataInfo);
          return;
        end if;
      
        update RLRECORDMSTQY
           set LEAVE_FLG = iv_EXECUTE_RESULT,
               REMARK    = iv_ERROR_INFO,
               LEAVE_DTM = to_date(iv_FILE_TIME, 'yyyy-mm-dd hh24:mi:ss')
         where OUT_NO = iv_SEQ_NO;
      
      end if;
    
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '����23����ʧ�ܣ�' || substr(sqlerrm, 1, 100);
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

  ---�ͺ��ؽ������ĵ������

  procedure MAIN(v_reportInfo in varchar2,
                 v_reportAttr out varchar2,
                 v_reportXml  out clob,
                 v_resCode    out varchar2,
                 v_resMsg     out varchar2) is
    iv_reportInfoJson  json; --����
    iv_reportParamJSON json; --ʵ�ʴ�����
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

  --��װ�䱨��
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
  
    --����Ӧ������ȡ�������ɵ����ݣ�ͬһ��BussinessId)
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
    --����xml���ģ���v_reportXml����
  
    --���Գ�����������
    iv_priKeyJsonList.add_elem(v_business_id);
    v_reportAttr.put('reportCode', 'intf_storage');
    v_reportAttr.put('priKeyList', iv_priKeyJsonList);
  
    --XMLͷ������
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
  
    --XML���ݾ��壨���ڶ�����¼��������Щ��¼��Ӧ��v_business_id��ͬһ��������ĸ����ں�̨���ɵ�ʱ��������ã�
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
           where a.BATCH_NO != iv_batchNo
              or a.BATCH_NO is null;
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
           op_code)
        values
          (SEQ_RLRECORDMSTQY.NEXTVAL, --TODO ��ȷ�� �볧��ˮ��
           iv_batchNo,
           iv_carID,
           iv_carType,
           iv_tradeType,
           iv_shipName,
           iv_billNo,
           iv_fixShipNo,
           iv_areaCode,
           sysdate,
           '1');
      
      end loop;
    exception
      when others then
        rollback;
        v_resCode := '2009';
        v_resMsg  := '��Ӽ�װ����Ϣ�쳣';
        return;
    end;
  
    commit;
  exception
    when others then
      rollback;
      v_resCode := '2009';
      v_resMsg  := '��Ӽ�װ����Ϣ�쳣';
      return;
  end;

  --���������¼
  procedure P_PRINT_RECORD(v_reportInfo in varchar2,
                           v_reportJson out varchar2,
                           v_reportXml  out varchar2,
                           v_resCode    out varchar2,
                           v_resMsg     out varchar2) is
  
    v_record_no       number(19);
    vrow              rlrecordmstqy%rowtype;
    iv_reportJson     json;
    iv_in_CHNL_NO     varchar2(30); --ͨ����
    iv_out_CHNL_NO    varchar2(30); --ͨ����
    iv_CHNL_NO        varchar2(30); --ͨ����
    iv_vendorName     varchar2(30); --�ͻ�����
    iv_goodName       varchar2(30); --��������
    iv_stockArea      varchar2(30); --�ѳ�����
    iv_inNo           varchar2(30); --�������
    iv_outNo          varchar2(30); --������Ҫ
    iv_no             varchar2(30); --���
    iv_tickQty        number(19, 6);
    iv_netNow         number(19, 6);
    iv_type           varchar2(30); --������ʶ
    iv_GOODS_PROPERTY varchar2(30); --���
    iv_IM_CUSTOM      varchar2(30); --���
    iv_trade_type     varchar2(30); --���
    iv_count          number(19);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    iv_reportJson := json();
    v_reportXml   := ' ';
  
    --����Ӧ������ȡ�������ɵ����ݣ�ͬһ��BussinessId)
    begin
    
      select a.record_no
        into v_record_no
        from ticket a
       where a.STATE = '0'
         and rownum < 2;
    
      select count(*)
        into iv_count
        from rlrecordmstqy i
       where i.RECORD_NO = v_record_no;
    
      if iv_count = 0 then
        update ticket
           set state = '1', UPDATE_TIME = sysdate, STR_JSON = v_reportJson
         where record_no = v_record_no;
      
        v_resCode := 2;
        v_resMsg  := 'nodata';
        commit;
        return;
      
      end if;
    
      select *
        into vrow
        from rlrecordmstqy i
       where i.RECORD_NO = v_record_no;
    
      ---��ȡͨ���ţ��Լ��ͻ�������Ϣ----
      select nvl(max(a.in_door_no), '99'),
             nvl(max(a.out_door_no), '99'),
             nvl(max(b.VEN_NAME), '99') �� nvl(max(a.in_no), '99'),
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
    
      ---��ȡ��������---
      select nvl(max(b.good_name), '99')
        into iv_goodName
        from rlrecordmstqy a, good_info b
       where b.good_no = a.good_no
         and a.RECORD_NO = v_record_no;
    
      ---��ȡ����ѳ�����  �ᵥ����---
      select nvl(max(b.STOCK_AREA), '99') ��nvl(max(b.tick_qty), '99'),
             nvl(max(b.trade_type), '99')
        into iv_stockArea, iv_tickQty, iv_trade_type
        from rlrecordmstqy a, ship_cargo_record b
       where b.bill_no = a.tick_no
         and a.RECORD_NO = v_record_no;
    
      ---��ȡ��ǰ�ᵥ�Ѿ���ɵ�������--     
      select sum(B.NET_QTY)
        into iv_netNow
        from RLRECORDMSTQY B
       where B.TICK_NO = vrow.tick_no;
    
      ---��ȡó������--      
      if iv_trade_type in ('0', '1') then
        --��ó
        iv_GOODS_PROPERTY := 'WM';
        iv_IM_CUSTOM      := 'Y';
      
      end if;
    
      if iv_trade_type in ('2', '3') then
        --��ó
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
    
      append_xml(v_reportXml, '<CHK_RESULT>' || 'N' || '</CHK_RESULT>');
    
      append_xml(v_reportXml,
                 '<LED_INFO>' || '����,��˲�СƱ' || '</LED_INFO>');
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
    
      -----��֯�·���ӡ�ı�����Ϣ----     
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

end BUSINESS_GROUP_INTF_PCK;
/
