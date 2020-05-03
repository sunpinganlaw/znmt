------------------------------------------------------
-- Export file for user ZNRL                        --
-- Created by Administrator on 2015-02-07, 18:41:46 --
------------------------------------------------------

spool znrl.log

prompt
prompt Creating package APPROVE_CORE
prompt =============================
prompt
create or replace package znrl.approve_core is

  -- Author  : xieyt
  -- Created : 2010-6-4 10:01:52
  -- Purpose : 审批流程通用存储过程
  
    --从节点中取值,字符串
  Function Getvaluebyxpath(Innode In Xmldom.Domnode, Inxpath In Varchar2)
    Return Varchar2;

  --根据Xpath从dom节点中取值,字符串
  Function Getstringbyxpath(Innode In Xmldom.Domnode, Inxpath In Varchar2)
    Return Varchar2;

  --根据节点的成员数量
  Function Getdomnodelength(Innodes In Xmldom.Domnodelist) Return Integer;
  
  Null_Str                  Constant Varchar2(10) := Null;
  APPREVENT_TYPE_GETSAMPLE  Constant Varchar2(2) := '1';--取样
  APPREVENT_TYPE_DROPSAMPLE  Constant Varchar2(2) := '2';--弃样

  APPRNODE_TYPE_HEAD        CONSTANT NUMBER(5) := 1; --审批节点的类型：首节点
  APPRNODE_TYPE_MIDD        CONSTANT NUMBER(5) := 2; --审批节点的类型：首节点
  APPRNODE_TYPE_TAIL        CONSTANT NUMBER(5) := 3; --审批节点的类型：尾节点
  APPRNODE_TYPE_ONLY        CONSTANT NUMBER(5) := 4; --审批节点的类型：独节点


  --新增加审批请求
  procedure addApproveReq(v_apprEventKeyId  in varchar2,
                         v_apprEventType   in varchar2,
                         v_apprEventDesc   in varchar2,
                         v_staffid         in varchar2,
                         v_rescode         out varchar2,
                         v_resmsg          out varchar2);

                               

  --保存审批结果
  procedure saveApproveResult(v_jsonStr  in varchar2,
                              v_StaffId  in varchar2,
                              v_isOk     in varchar2,
                              v_apprDesc in varchar2,
                              v_rescode  out varchar2,
                              v_resmsg   out varchar2);
                              
                                --保存审批结果
  procedure saveSingleApproveResult(v_approveId         in varchar2,
                                    v_StaffId           in varchar2,
                                    v_approveResult     in varchar2,
                                    v_apprDescription   in varchar2,
                                    v_rescode           out varchar2,
                                    v_resmsg            out varchar2);
                                    
                                    
  procedure writeBackApproveEvent(v_apprEventType in number,
                                  v_eventId       in number,
                                  v_approveResult in varchar2,
                                  v_rescode       out varchar2,
                                  v_resmsg        out varchar2);
end approve_core;
/

prompt
prompt Creating package JSON_EXT
prompt =========================
prompt
create or replace package znrl.json_ext as
  /*
  Copyright (c) 2009 Jonas Krogsboell

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
  */

  /* This package contains extra methods to lookup types and
     an easy way of adding date values in json - without changing the structure */
  function parsePath(json_path varchar2) return json_list;

  --JSON Path getters
  function get_json_value(obj json, v_path varchar2) return json_value;
  function get_string(obj json, path varchar2) return varchar2;
  function get_number(obj json, path varchar2) return number;
  function get_json(obj json, path varchar2) return json;
  function get_json_list(obj json, path varchar2) return json_list;
  function get_bool(obj json, path varchar2) return boolean;

  --JSON Path putters
  procedure put(obj in out nocopy json, path varchar2, elem varchar2);
  procedure put(obj in out nocopy json, path varchar2, elem number);
  procedure put(obj in out nocopy json, path varchar2, elem json);
  procedure put(obj in out nocopy json, path varchar2, elem json_list);
  procedure put(obj in out nocopy json, path varchar2, elem boolean);
  procedure put(obj in out nocopy json, path varchar2, elem json_value);

  procedure remove(obj in out nocopy json, path varchar2);

  --Pretty print with JSON Path
  function pp(obj json, v_path varchar2) return varchar2;
  procedure pp(obj json, v_path varchar2); --using dbms_output.put_line
  procedure pp_htp(obj json, v_path varchar2); --using htp.print

  --extra function checks if number has no fraction
  function is_integer(v json_value) return boolean;

  format_string varchar2(30 char) := 'yyyy-mm-dd hh24:mi:ss';
  --extension enables json to store dates without comprimising the implementation
  function to_json_value(d date) return json_value;
  --notice that a date type in json is also a varchar2
  function is_date(v json_value) return boolean;
  --convertion is needed to extract dates
  --(json_ext.to_date will not work along with the normal to_date function - any fix will be appreciated)
  function to_date2(v json_value) return date;
  --JSON Path with date
  function get_date(obj json, path varchar2) return date;
  procedure put(obj in out nocopy json, path varchar2, elem date);

  --experimental support of binary data with base64
  function base64(binarydata blob) return json_list;
  function base64(l json_list) return blob;

end json_ext;
/

prompt
prompt Creating package JSON_PARSER
prompt ============================
prompt
create or replace package znrl.json_parser as
  /*
  Copyright (c) 2009 Jonas Krogsboell

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
  */
  /* scanner tokens:
    '{', '}', ',', ':', '[', ']', STRING, NUMBER, TRUE, FALSE, NULL
  */
  type rToken IS RECORD (
    type_name VARCHAR2(6),
    line PLS_INTEGER,
    col PLS_INTEGER,
    data VARCHAR2(4000)); -- limit a string to 4000 bytes

  type lTokens is table of rToken index by pls_integer;
  type json_src is record (len number, offset number, src varchar2(32767), s_clob clob);

  json_strict boolean := false;

  function next_char(indx number, s in out nocopy json_src) return varchar2;
  function next_char2(indx number, s in out nocopy json_src, amount number default 1) return varchar2;

  function prepareClob(buf in clob) return json_parser.json_src;
  function prepareVarchar2(buf in varchar2) return json_parser.json_src;
  function lexer(jsrc in out nocopy json_src) return lTokens;
  procedure print_token(t rToken);

  function parser(str varchar2) return json;
  function parse_list(str varchar2) return json_list;
  function parse_any(str varchar2) return json_value;
  function parser(str clob) return json;
  function parse_list(str clob) return json_list;
  function parse_any(str clob) return json_value;
  procedure remove_duplicates(obj in out nocopy json);
  function get_version return varchar2;

end json_parser;
/

prompt
prompt Creating package JSON_PRINTER
prompt =============================
prompt
create or replace package znrl.json_printer as
  /*
  Copyright (c) 2009 Jonas Krogsboell

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
  */
  indent_string varchar2(10 char) := '  '; --chr(9); for tab
  newline_char varchar2(2 char) := chr(13)||chr(10); -- Windows style
  --newline_char varchar2(2) := chr(10); -- Mac style
  --newline_char varchar2(2) := chr(13); -- Linux style
  ascii_output boolean := true;

  function pretty_print(obj json, spaces boolean default true) return varchar2;
  function pretty_print_list(obj json_list, spaces boolean default true) return varchar2;
  function pretty_print_any(json_part json_value, spaces boolean default true) return varchar2;
  procedure pretty_print(obj json, spaces boolean default true, buf in out nocopy clob);
  procedure pretty_print_list(obj json_list, spaces boolean default true, buf in out nocopy clob);
  procedure pretty_print_any(json_part json_value, spaces boolean default true, buf in out nocopy clob);
end json_printer;
/

prompt
prompt Creating package PK_REGISTER
prompt ============================
prompt
create or replace package znrl.pk_register is

  -- Author  : xieyt
  -- Created : 2015-01-04 16:07:02
  -- Purpose : 车入厂登记注册信息
  
  procedure p_train_register(v_insertStr in varchar2,
                             v_updateStr in varchar2,
                             v_deleteStr in varchar2,
                             v_publicStr in varchar2,
                             v_opCodeStr in varchar2,
                             v_resCode   out varchar2,
                             v_resMsg    out varchar2);
                             
                             
  --校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
  procedure dealBatchInfo(v_trainNo in varchar2, --车次
                       v_opCode  in varchar2,
                       v_resCode out varchar2,
                       v_resMsg  out varchar2);
                       
                       
  --汽车来煤登记
  procedure p_car_add_transRec(v_jsonString in varchar2,
                               v_resCode    out varchar2,
                               v_resMsg     out varchar2);
                               
 --汽车分批次处理 校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
 procedure dealCarBatchInfo(v_recordNo in varchar2, --车次
                            v_opCode  in varchar2,
                            v_resCode out varchar2,
                            v_resMsg  out varchar2);

end pk_register;
/

prompt
prompt Creating package PK_SAMPLE_STORE
prompt ================================
prompt
create or replace package znrl.pk_sample_store is

  -- Author  : xieyt
  -- Created : 2015-01-25 20:13:51
  -- Purpose : 存储样柜相关业务处理的过程包
  
  procedure submit_approve(v_jsonStr     in varchar2,
                           v_dealType    in varchar2,
                           v_destination in varchar2,
                           v_remark      in varchar2,
                           v_opCode      in varchar2,
                           v_resCode     out varchar2,
                           v_resMsg      out varchar2);
                           
                           
  procedure cabinet_op_record(v_eventKeyId in varchar2,
                              v_rescode    out varchar2,
                              v_resmsg     out varchar2);
                              

end pk_sample_store;
/

prompt
prompt Creating package SYN_DATA_TASK
prompt ==============================
prompt
create or replace package znrl.syn_data_task is

  -- Author  : GAOXP
  -- Created : 2014/12/26 20:22:49
  -- Purpose : 
  
  -- Public type declarations
  PROCESS_OK     CONSTANT VARCHAR2(4) := '0';  --处理完成
  PROCESS_ERROR  CONSTANT VARCHAR2(4) := '9';  --程序处理异常
  PROCESS_NORMAL CONSTANT VARCHAR2(4) := '1';  --处理正常
  
  procedure syn_weight_info;
  
  procedure recordSampleInfo(v_recordId      in varchar2,--中间表记录id
                             v_batchNo       in varchar2,--批次号
                             v_venNo         in varchar2,--供应商编码 
                             v_coalNo        in varchar2,--煤种编码
                             v_sampleCode    in varchar2,--采样编码
                             v_samplingCode  in varchar2,--制样编码
                             v_resCode       out varchar2,
                             v_resMsg        out varchar2);
  

 --校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
 procedure checkParams(v_trainNo       in varchar2,--车次
                        v_carId        in varchar2,--车号
                        v_venName      in varchar2,--供应商
                        v_coalName     in varchar2,--煤种
                        iv_netQty      in number,--净重
                        iv_tickQty     in number,--票重
                        v_venNo        out varchar2,--供应商编码
                        v_coalNo       out varchar2,--煤种编码
                        v_batchNo      out varchar2,--批次号
                        v_sampleCode   out varchar2,--采样编码
                        v_samplingCode out varchar2,--制样编码
                        v_resCode     out varchar2,
                        v_resMsg      out varchar2);
                        
  --取轨道衡数据到中间表
  PROCEDURE initMiddleTable(v_resCode    out varchar2,
                            v_resMsg     out varchar2);

end syn_data_task;
/

prompt
prompt Creating package TRANS_DATA_TASK
prompt ================================
prompt
create or replace package znrl.trans_data_task is

  -- Author  : GAOXP
  -- Created : 2014/12/26 20:22:49
  -- Purpose : 
  
  -- Public type declarations
  PROCESS_OK     CONSTANT VARCHAR2(4) := '0';  --处理完成
  PROCESS_ERROR  CONSTANT VARCHAR2(4) := '9';  --程序处理异常
  PROCESS_NORMAL CONSTANT VARCHAR2(4) := '1';  --处理正常
  
  procedure syn_weight_info;
  
  procedure recordSampleInfo(v_recordId      in varchar2,--中间表记录id
                             v_batchNo       in varchar2,--批次号
                             v_venNo         in varchar2,--供应商编码 
                             v_coalNo        in varchar2,--煤种编码
                             v_sampleCode    in varchar2,--采样编码
                             v_samplingCode  in varchar2,--制样编码
                             v_resCode       out varchar2,
                             v_resMsg        out varchar2);
  

 --校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
 procedure checkParams(v_trainNo       in varchar2,--车次
                        v_carId        in varchar2,--车号
                        v_venName      in varchar2,--供应商
                        v_coalName     in varchar2,--煤种
                        iv_netQty      in number,--净重
                        v_venNo        out varchar2,--供应商编码
                        v_coalNo       out varchar2,--煤种编码
                        v_batchNo      out varchar2,--批次号
                        v_sampleCode   out varchar2,--采样编码
                        v_samplingCode out varchar2,--制样编码
                        v_resCode     out varchar2,
                        v_resMsg      out varchar2);
                        
  --取轨道衡数据到中间表
  PROCEDURE initMiddleTable(v_resCode    out varchar2,
                            v_resMsg     out varchar2);

end trans_data_task;
/

prompt
prompt Creating type IN_TYPE_ARR
prompt =========================
prompt
CREATE OR REPLACE TYPE ZNRL.in_type_arr IS TABLE OF VARCHAR2(4000)
/

prompt
prompt Creating type TY_STR_SPLIT
prompt ==========================
prompt
CREATE OR REPLACE TYPE ZNRL.ty_str_split IS TABLE OF VARCHAR2 (4000)
/

prompt
prompt Creating function FN_SPLIT
prompt ==========================
prompt
CREATE OR REPLACE FUNCTION ZNRL.FN_SPLIT(P_STR IN VARCHAR2, P_DELIMITER IN VARCHAR2)
    RETURN TY_STR_SPLIT IS
    J         INT := 0;
    I         INT := 1;
    LEN       INT := 0;
    LEN1      INT := 0;
    STR       VARCHAR2(4000);
    STR_SPLIT TY_STR_SPLIT := TY_STR_SPLIT();
BEGIN
    LEN  := LENGTH(P_STR);
    LEN1 := LENGTH(P_DELIMITER);

    WHILE J < LEN LOOP
        J := INSTR(P_STR, P_DELIMITER, I);

        IF J = 0 THEN
            J   := LEN;
            STR := SUBSTR(P_STR, I);
            STR_SPLIT.EXTEND;
            STR_SPLIT(STR_SPLIT.COUNT) := STR;

            IF I >= LEN THEN
                EXIT;
            END IF;
        ELSE
            STR := SUBSTR(P_STR, I, J - I);
            I   := J + LEN1;
            STR_SPLIT.EXTEND;
            STR_SPLIT(STR_SPLIT.COUNT) := STR;
        END IF;
    END LOOP;

    RETURN STR_SPLIT;
END FN_SPLIT;
/

prompt
prompt Creating function GETCARFLOWINFO
prompt ================================
prompt
create or replace function znrl.getCarFlowInfo(v_recordNo in number,
                                          v_tag      in varchar2)
  return varchar2 is

  iv_curSortNum  number(3);
  iv_nextSortNum number(3);
  iv_Result      varchar2(50);
begin

  select nvl(max(t.cur_flow), 999), nvl(max(t.next_flow), 999)
    into iv_curSortNum, iv_nextSortNum
    from (select b.sort_num cur_flow,
                 b.sort_num + 1 next_flow,
                 row_number() over(order by b.sort_num desc) floworder
            from rlcarstamst a, rlflowmst b
           where a.record_no = v_recordNo
             and a.flow_id = b.flow_id) t
   where t.floworder = 1;

  if v_tag = 'CUR' then
    select decode(iv_curSortNum,
                  1,
                  '入厂',
                  2,
                  '采样',
                  3,
                  '称重',
                  4,
                  '卸煤',
                  5,
                  '称轻',
                  6,
                  '出厂',
                  7,
                  '—',
                  '未知')
      into iv_result
      from dual;
  elsif v_tag = 'NEXT' then
    select decode(iv_nextSortNum,
                  1,
                  '入厂',
                  2,
                  '采样',
                  3,
                  '称重',
                  4,
                  '卸煤',
                  5,
                  '称轻',
                  6,
                  '出厂',
                  7,
                  '—',
                  '未知')
      into iv_result
      from dual;
  end if;

  return iv_Result;
end getCarFlowInfo;
/

prompt
prompt Creating function GETCARINFO
prompt ============================
prompt
create or replace function znrl.getCarInfo(v_CardId      in varchar2,
                                      v_keyTypeName in varchar2)
  return varchar2 is
  v_Result varchar2(500);
begin

  if v_keyTypeName = 'CAR_TYPE' then
    select nvl(max(decode(a.CAR_TYP, '0', '半挂', '1', '外挂','未知')), '')
      into v_Result
      From RLCARMST a
     where a.car_id = v_CardId;
  elsif v_keyTypeName = 'BOTTOM_HEIGHT' then
    select nvl(max(TO_CHAR(CAR1TOF_LENGTH)), '')
      into v_Result
      From RLCARMST a
     where a.car_id = v_CardId;
  end if;

  return v_Result;
end getCarInfo;
/

prompt
prompt Creating function GETCARSTATUSINFO
prompt ==================================
prompt
create or replace function znrl.getCarStatusInfo(v_recordNo in number)
  return varchar2 is
  
  v_Result     varchar2(500);
  iv_sampleTag number(3);
  iv_weightTag number(3);
  iv_lightTag  number(3);
begin
  v_Result := 0;--状态未知
  --1.采样未称重
  --2.称重未称轻
  --3.已经称轻
  
  select count(1)
    into iv_sampleTag
    from TAKE_SAMPLE_REC a
   where a.record_no = v_recordNo;

  select count(1)
    into iv_weightTag
    from RLCARCZMST a
   where a.record_no = v_recordNo
     and CZ_DTM is not null;

  select count(1)
    into iv_lightTag
    from RLCARCZMST a
   where a.record_no = v_recordNo
     and JQ_DTM is not null;
  
  if iv_sampleTag >= 0 and iv_weightTag = 0 and iv_lightTag = 0 then
    v_Result := '1';
  elsif iv_sampleTag >= 0 and iv_weightTag >= 0 and iv_lightTag = 0 then
    v_Result := '2';
  elsif iv_sampleTag >= 0 and iv_weightTag >= 0 and iv_lightTag >= 0 then
    v_Result := '3';
  else
    v_Result := '0';
  end if;
  
  return v_Result;
  
end getCarStatusInfo;
/

prompt
prompt Creating function GETSAMPLEINFO
prompt ===============================
prompt
create or replace function znrl.getSampleInfo(v_recordNo    in number,
                                         v_keyTypeName in varchar2)
  return varchar2 is
  v_Result varchar2(500);
begin

  if v_keyTypeName = 'SAMPLE_TIME' then
    select nvl(max(to_char(a.start_time, 'yyyy-mm-dd hh24:mi:ss')), '')
      into v_Result
      From TAKE_SAMPLE_REC a
     where a.RECORD_NO = v_recordNo;
  elsif v_keyTypeName = 'MACHINE_CODE' then
    select nvl(max(MACHINE_CODE), '')
      into v_Result
      From TAKE_SAMPLE_REC a
     where a.RECORD_NO = v_recordNo;
  end if;

  return v_Result;
end getSampleInfo;
/

prompt
prompt Creating function GETUNLOADINFO
prompt ===============================
prompt
create or replace function znrl.getUnloadInfo(v_recordNo    in number,
                                         v_keyTypeName in varchar2)
  return varchar2 is
  v_Result varchar2(500);
begin

  if v_keyTypeName = 'KD_QTY' then
    select nvl(max(to_char(a.kd_qty)), '')
      into v_Result
      From RLCARXMMST a
     where a.RECORD_NO = v_recordNo;
  end if;

  return v_Result;
end getUnloadInfo;
/

prompt
prompt Creating function GET_EMPTY_NUM
prompt ===============================
prompt
CREATE OR REPLACE FUNCTION ZNRL.GET_EMPTY_NUM(V_SAMPLE_CODE IN VARCHAR2)
  RETURN NUMBER IS
  V_RESULT NUMBER(10);
BEGIN

  SELECT COUNT(1)
    INTO V_RESULT
    FROM RLRECORDMSTHY A
   WHERE A.SAMPLE_CODE = V_SAMPLE_CODE
     AND A.EMPTY_FLG IS NULL
      OR A.EMPTY_FLG = '0';
  RETURN V_RESULT;
END GET_EMPTY_NUM;
/

prompt
prompt Creating function GET_NET_QTY_NUM
prompt =================================
prompt
CREATE OR REPLACE FUNCTION ZNRL.GET_NET_QTY_NUM(V_SAMPLE_CODE IN VARCHAR2)
  RETURN NUMBER IS
  V_RESULT NUMBER(10);
BEGIN
  SELECT COUNT(1)
    INTO V_RESULT
    FROM RLRECORDMSTHY A
   WHERE A.SAMPLE_CODE = V_SAMPLE_CODE
     AND A.NET_QTY IS NOT NULL;
  RETURN V_RESULT;
END GET_NET_QTY_NUM;
/

prompt
prompt Creating function GET_TABLE_COLUMN_BY_COLUMN
prompt ============================================
prompt
CREATE OR REPLACE FUNCTION ZNRL.GET_TABLE_COLUMN_BY_COLUMN(IV_SHOW_NAME     IN VARCHAR2, --显示字段
                                                      IV_TABLE_NAME    IN VARCHAR2, --表名
                                                      IV_COLUMN_NAME   IN VARCHAR2, --字段名
                                                      IV_COLUMN_VALUE  IN VARCHAR2, --字段值
                                                      IV_COLUMN_DOWHAT IN VARCHAR2, --操作符
                                                      IV_COLUMN_TYPE   IN VARCHAR2, --数据类型：1,字符型，2，数字型
                                                      IV_COLUMN_COUNT  IN NUMBER) --字段个数
 RETURN VARCHAR2 IS
  RESULT            VARCHAR2(1000);
  V_SQL             VARCHAR2(1000);
  V_COUNT           NUMBER;
  V_TAG             VARCHAR2(10);
  ARR_COLUMNS_NAME  IN_TYPE_ARR := IN_TYPE_ARR();
  ARR_COLUMN_VALUE  IN_TYPE_ARR := IN_TYPE_ARR();
  ARR_COLUMN_TYPE   IN_TYPE_ARR := IN_TYPE_ARR(); /*1,字符型，2，数字型*/
  ARR_COLUMN_DOWHAT IN_TYPE_ARR := IN_TYPE_ARR(); /**/
  /*
  SELECT GET_TABLE_COLUMN_BY_COLUMN('SERV_NAME',
                                'SERV_MSG',
                                'SERV_ID^_^STATE^_^LATN_ID',
                                '53351497^_^F0X,F1X,F0H^_^71',
                                '=^_^NOT IN^_^=',
                                '2^_^1^_^2',
                                3)
  FROM DUAL;
  */
BEGIN
  V_SQL   := '';
  V_COUNT := 1;
  V_TAG   := '0';
  V_SQL   := 'SELECT ' || IV_SHOW_NAME || ' FROM ' || IV_TABLE_NAME;
  FOR T IN (SELECT *
              FROM TABLE(CAST(FN_SPLIT(IV_COLUMN_NAME, '^_^') AS
                              TY_STR_SPLIT))) LOOP
    ARR_COLUMNS_NAME.EXTEND;
    ARR_COLUMNS_NAME(ARR_COLUMNS_NAME.COUNT) := T.COLUMN_VALUE;
  END LOOP;
  FOR T IN (SELECT *
              FROM TABLE(CAST(FN_SPLIT(IV_COLUMN_VALUE, '^_^') AS
                              TY_STR_SPLIT))) LOOP
    ARR_COLUMN_VALUE.EXTEND;
    ARR_COLUMN_VALUE(ARR_COLUMN_VALUE.COUNT) := T.COLUMN_VALUE;
  END LOOP;
  FOR T IN (SELECT *
              FROM TABLE(CAST(FN_SPLIT(IV_COLUMN_TYPE, '^_^') AS
                              TY_STR_SPLIT))) LOOP
    ARR_COLUMN_TYPE.EXTEND;
    ARR_COLUMN_TYPE(ARR_COLUMN_TYPE.COUNT) := T.COLUMN_VALUE;
  END LOOP;
  FOR T IN (SELECT *
              FROM TABLE(CAST(FN_SPLIT(IV_COLUMN_DOWHAT, '^_^') AS
                              TY_STR_SPLIT))) LOOP
    ARR_COLUMN_DOWHAT.EXTEND;
    ARR_COLUMN_DOWHAT(ARR_COLUMN_DOWHAT.COUNT) := T.COLUMN_VALUE;
  END LOOP;
  WHILE V_COUNT <= IV_COLUMN_COUNT LOOP
    IF V_COUNT = 1 THEN
      V_SQL := V_SQL || ' WHERE ' || ARR_COLUMNS_NAME(V_COUNT);
    ELSE
      V_SQL := V_SQL || ' AND ' || ARR_COLUMNS_NAME(V_COUNT);
    END IF;
  
    IF INSTR(ARR_COLUMN_DOWHAT(V_COUNT), 'IN') = 0 THEN
      V_SQL := V_SQL || ARR_COLUMN_DOWHAT(V_COUNT);
    ELSE
      V_SQL := V_SQL || ' ' || ARR_COLUMN_DOWHAT(V_COUNT) || ' (';
      V_TAG := '1';
    END IF;
  
    IF ARR_COLUMN_TYPE(V_COUNT) = '2' THEN
      V_SQL := V_SQL || ARR_COLUMN_VALUE(V_COUNT);
    ELSIF ARR_COLUMN_TYPE(V_COUNT) = '1' THEN
      FOR R IN (SELECT *
                  FROM TABLE(CAST(FN_SPLIT(ARR_COLUMN_VALUE(V_COUNT), ',') AS
                                  TY_STR_SPLIT))) LOOP
        V_SQL := V_SQL || '''' || R.COLUMN_VALUE || ''',';
      END LOOP;
      V_SQL := SUBSTR(V_SQL, 1, LENGTH(V_SQL) - 1);
    END IF;
    V_COUNT := V_COUNT + 1;
    IF V_TAG = '1' THEN
      V_SQL := V_SQL || ') ';
      V_TAG := '0';
    END IF;
  END LOOP;
  BEGIN
    EXECUTE IMMEDIATE V_SQL
      INTO RESULT;
    ARR_COLUMNS_NAME.DELETE;
    ARR_COLUMN_VALUE.DELETE;
    ARR_COLUMN_TYPE.DELETE;
    ARR_COLUMN_DOWHAT.DELETE;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      RESULT := '';
    WHEN OTHERS THEN
      RESULT := ' 数据异常！';
  END;
  RETURN(RESULT);
END GET_TABLE_COLUMN_BY_COLUMN;
/

prompt
prompt Creating function QRYCOALINFO
prompt =============================
prompt
create or replace function znrl.qryCoalInfo return varchar2 is
  iv_res varchar2(3000);

  iv_json_head varchar2(300) := '{"DEVICE":"SUMMARY_INFO","VALUE":""';
  iv_json_tail varchar2(300) := '}';
  iv_json_cont varchar2(3000);

  iv_cnt number(6);
  iv_qty number(10, 6);
begin
  iv_json_cont := '';

  --来煤车数
  select count(1), nvl(SUM(a.net_qty), 0)
    into iv_cnt, iv_qty
    from RLRECORDMSTQY a
   where a.record_dtm between trunc(sysdate) and
         trunc(sysdate) + 1 - 0.00001;

  iv_json_cont := iv_json_cont || ',"CARS_CNT":"' || iv_cnt || '"';
  iv_json_cont := iv_json_cont || ',"SUM_NET_QTY":"' || iv_qty || '"';

  --来煤批次数
  select count(1)
    into iv_cnt
    from (select distinct car_batch_no
            from RLRECORDMSTQY a
           where a.record_dtm between trunc(sysdate) and
                 trunc(sysdate) + 1 - 0.00001);

  iv_json_cont := iv_json_cont || ',"CARS_BAT_CNT":"' || iv_cnt || '"';

  --已化验批次数         TODO,怎么取数据，待小普确定
  select 7 into iv_cnt from dual;
  iv_json_cont := iv_json_cont || ',"HY_BAT_CNT":"' || iv_cnt || '"';

  --采样车数
  select count(1)
    into iv_cnt
    from take_sample_rec a
   where a.start_time >= trunc(sysdate)
     and a.end_time < trunc(sysdate) + 1;

  iv_json_cont := iv_json_cont || ',"CY_CARS_CNT":"' || iv_cnt || '"';

  --制样批次数
  select count(1)
    into iv_cnt
    from prepar_sampling_rec a
   where a.start_time >= trunc(sysdate)
     and a.end_time < trunc(sysdate) + 1;

  iv_json_cont := iv_json_cont || ',"ZY_BAT_CNT":"' || iv_cnt || '"';

  --存煤数     TODO,怎么取数据，待小普确定 
  select nvl(SUM(a.net_qty), 0)
    into iv_qty
    from RLRECORDMSTQY a
   where a.record_dtm between trunc(sysdate) and
         trunc(sysdate) + 1 - 0.00001;
         
  iv_json_cont := iv_json_cont || ',"STORE_QTY":"' || iv_qty || '"';
  
  --上仓     TODO,怎么取数据，待小普确定 
  select nvl(SUM(a.net_qty), 0)
    into iv_qty
    from RLRECORDMSTQY a
   where a.record_dtm between trunc(sysdate) and
         trunc(sysdate) + 1 - 0.00001;
         
  iv_json_cont := iv_json_cont || ',"SC_QTY":"' || iv_qty || '"';
  
  
  --组成最后结果并返回
  iv_res := iv_json_head || iv_json_cont || iv_json_tail;

  return iv_res;
end qryCoalInfo;
/

prompt
prompt Creating procedure PRO_HELLO
prompt ============================
prompt
create or replace procedure znrl.pro_hello(v_user_name1 in varchar2,
                                      v_user_name2 in varchar2,
                                      v_result1    out varchar2,
                                      v_result2    out varchar2) is
begin
  v_result1 := 'hello,' || v_user_name1 || ',' || v_user_name2;
  v_result2 := 'hi,' || v_user_name2 || ',' || v_user_name1;
end;
/

prompt
prompt Creating procedure PRO_HELLO_XIEYT
prompt ==================================
prompt
create or replace procedure znrl.pro_hello_xieyt is
begin
  null;
end;
/

prompt
prompt Creating procedure P_TRAIN_REGISTER_DROP
prompt ========================================
prompt
create or replace procedure znrl.p_train_register_drop(v_insertStr in varchar2,
                                             v_updateStr in varchar2,
                                             v_deleteStr in varchar2,
                                             v_publicStr in varchar2,
                                             v_opCodeStr in varchar2,
                                             v_resCode   out varchar2,
                                             v_resMsg    out varchar2) is
  --business
  iv_insert_json_list json_list;
  iv_update_Json_list json_list;
  iv_delete_Json_list json_list;
  iv_public_Json      json;
  iv_tmp_jsonObj      json;
  iv_jv               json_value;
  iv_trainNo          varchar2(20);
  iv_recordDtm        varchar2(30);
  iv_remark           varchar2(500);
  
  iv_cnt              number(5);
begin
  v_resCode := '0';
  v_resMsg  := 'ok';
  
  --解析公共部分信息
  begin
    iv_public_Json := json(v_publicStr);
    iv_trainNo := json_ext.get_string(iv_public_Json, 'trainNo');
    iv_recordDtm := json_ext.get_string(iv_public_Json, 'recordDtm');
    iv_remark := json_ext.get_string(iv_public_Json, 'remark');
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '新增入厂记录异常：' || substr(sqlerrm, 1, 100);
      return;
  end;
  
  --处理只更新了公共信息的情况
  if (v_insertStr is null or length(v_insertStr) = 0) and
    (v_updateStr is null or length(v_updateStr) = 0) and
    (v_deleteStr is null or length(v_deleteStr) = 0) and
    (v_publicStr is not null and length(v_publicStr) > 0) then
    
    select count(*)
      into iv_cnt
      from RLRECORDMSTHY a
     where a.train_no = iv_trainNo;
    
    if iv_cnt > 0 then
      update RLRECORDMSTHY
         set record_dtm = to_date(iv_recordDtm, 'yyyy-mm-dd hh24:mi:ss'),
             remark     = iv_remark
       where train_no = iv_trainNo;
    
    end if;
  
  
  end if;
  --处理新增数据
  begin
    if v_insertStr is not null and length(v_insertStr) > 0 then
      iv_insert_json_list := json_list(v_insertStr);
      for i in 1 .. iv_insert_json_list.count loop
      
        --取json数组里的一个元素
        iv_jv          := iv_insert_json_list.get_elem(i);
        iv_tmp_jsonObj := json(iv_jv);
        --插入表
        insert into RLRECORDMSTHY
          (record_id,
           record_no, --TODO 待确认 入厂流水号哪里输入
           --oldCarId,--原车号 TODO 表无此字段。。
           train_no,
           car_id,
           ven_no,
           colry_no,
           coal_no,
           leave_dtm,--发货日期是哪个字段？
           record_dtm,
           tick_qty,
           mz_qty,
           pz_qty,
           net_qty,
           yd_qty, --盈亏  TODO 需要确认是这个字段吗？
           loss_qty,
           --xm_dtm, --TODO 是火车的翻车意思吗？
           train_batch_no,
           cz_balance_no, --TODO 表里只有称重和称轻的轨道，用哪个？
           car_typ,
           remark,
           insert_time,
           op_code)
        values
          (SEQ_RLRECORDMSTHY.Nextval, --记录ID
           SEQ_RLRECORDMSTHY.NEXTVAL, --TODO 待确认 入厂流水号
           --json_ext.get_string(json(iv_jv), 'oldCarId'),  --TODO
           iv_trainNo,
           json_ext.get_string(iv_tmp_jsonObj, 'carId'), --车号
           json_ext.get_string(iv_tmp_jsonObj, 'venNo'), --供应商
           json_ext.get_string(iv_tmp_jsonObj, 'colryNo'), --矿点
           json_ext.get_string(iv_tmp_jsonObj, 'coalNo'), --煤质类型
           to_date(json_ext.get_string(iv_tmp_jsonObj, 'leaveDtm'),'yyyy-mm-dd'), --发货日期
           to_date(iv_recordDtm,'yyyy-mm-dd hh24:mi:ss'),
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'tickQty')), --票重
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'mzQty')), --毛重
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'pzQty')), --皮重
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'netQty')), --净重
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'ydQty')), --盈亏  TODO 需要确认
           to_number(json_ext.get_string(iv_tmp_jsonObj, 'lossQty')), --运损
           --to_date(json_ext.get_string(iv_tmp_jsonObj, 'fcTime'),'yyyy-mm-dd'), --翻车时间
           json_ext.get_string(iv_tmp_jsonObj, 'batchNo'), --批次号
           json_ext.get_string(iv_tmp_jsonObj, 'balanceNo'), --轨道号
           json_ext.get_string(iv_tmp_jsonObj, 'carTyp'), --车型
           iv_remark,
           sysdate,
           v_opCodeStr);
      
      end loop;
    end if;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '新增火车入厂记录异常：' || substr(sqlerrm, 1, 100);
      return;
  end;

  --处理更新
  begin
    if v_updateStr is not null and length(v_updateStr) > 0 then
      iv_update_Json_list := json_list(v_updateStr);
      for i in 1 .. iv_update_Json_list.count loop
      
        --取json数组里的一个元素
        iv_jv          := iv_update_Json_list.get_elem(i);
        iv_tmp_jsonObj := json(iv_jv);
        
        --update
        update RLRECORDMSTHY a
           set --oldCarId = json_ext.get_string(json(iv_jv), 'oldCarId'),  --TODO
               a.car_id = json_ext.get_string(iv_tmp_jsonObj,'carId'), --车号
               a.ven_no = json_ext.get_string(iv_tmp_jsonObj, 'venNo'),
               a.colry_no = json_ext.get_string(iv_tmp_jsonObj,'colryNo'), --矿点
               a.coal_no = json_ext.get_string(iv_tmp_jsonObj,'coalNo'), --煤质类型
               a.leave_dtm = to_date(json_ext.get_string(iv_tmp_jsonObj,'leaveDtm'),'yyyy-mm-dd'), --发货日期
               a.tick_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'tickQty')), --票重
               a.mz_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'mzQty')), --毛重
               a.pz_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'pzQty')), --皮重
               a.net_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'netQty')), --净重
               a.yd_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'ydQty')), --盈亏  TODO 需要确认
               a.loss_qty = to_number(json_ext.get_string(iv_tmp_jsonObj,'lossQty')), --运损
               --a.xm_dtm = to_date(json_ext.get_string(iv_tmp_jsonObj,'fcTime'),'yyyy-mm-dd'), --翻车时间
               a.train_batch_no = json_ext.get_string(iv_tmp_jsonObj,'batchNo'), --批次号
               a.cz_balance_no = json_ext.get_string(iv_tmp_jsonObj, 'balanceNo'), --轨道号
               a.car_typ = json_ext.get_string(iv_tmp_jsonObj,'carTyp'), --车型
               a.record_dtm = to_date(iv_recordDtm,'yyyy-mm-dd hh24:mi:ss'),
               a.train_no = iv_trainNo,
               a.remark = iv_remark,
               a.update_time = sysdate,
               a.update_code = v_opCodeStr
         where a.record_no = to_number(json_ext.get_string(iv_tmp_jsonObj, 'recordNo'));
      end loop;
    end if;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '更新火车入厂记录异常：' || substr(sqlerrm, 1, 100);
      return;
  end;

  --处理删除
  begin
    if v_deleteStr is not null and length(v_deleteStr) > 0 then
      iv_delete_Json_list := json_list(v_deleteStr);
      for i in 1 .. iv_delete_Json_list.count loop
        --取json数组里的一个元素
        iv_jv          := iv_delete_Json_list.get_elem(i);
        iv_tmp_jsonObj := json(iv_jv);
        --删除 TODO update状态（无状态字段）还是delete，待定
        delete from RLRECORDMSTHY a
         where a.record_no =
               to_number(json_ext.get_string(iv_tmp_jsonObj, 'recordNo'));
      end loop;
    end if;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '更新火车入厂记录异常：' || substr(sqlerrm, 1, 100);
      return;
  end;

  commit;  
exception
  when others then
    rollback;
    v_resCode := '1';
    v_resMsg  := '提交火车入厂登记记录异常：' || substr(sqlerrm, 1, 100);
    return;
end;
/

prompt
prompt Creating procedure SENDWARNINGMSG
prompt =================================
prompt
create or replace procedure znrl.sendWarningMsg(v_warning_event_id in number,
                                           v_warning_type     in varchar2,
                                           v_warning_content  in varchar2
                                           ) is
  /*
    描述: 公用告警过程
    提示: 本过程不提交回滚事务，所有事物控制都在调用该过程的地方
    1.主动告警：当发生某些敏感业务操作时需要主动发出告警信息，如高额奖励积分、物品上下架等操作时主动发出告警（插入告警表，就是插入短信表和邮件信息表）
    2.被动告警：根据发生业务的日志记录、设定的阀值，形成查询语句，如对账文件上传下载监控等，如果超过阀值则进行告警（插入告警表，就是插入短信表和邮件信息表）
    2014-03-11 gaoxp
    v_warning_event_id  --告警事件id，配置表为 msg_send_trigevent
    v_warning_type      --告警类型 0:自检(如查JOB是否正常等)，1:一般，2:紧急';用于区分告警对象，配置表为 warning_method_config
    v_warning_content   --告警内容，可空; 如为空，则取告警事件配置表内容拼装组成，配置表为 msg_send_trigevent
  */

  iv_resCode         varchar2(3);
  iv_resMsg          varchar2(300);
  v_cnt              number(4) := 0;

  iv_warning_phone   varchar2(100);
  iv_warning_email   varchar2(100);
  iv_warning_content varchar2(4000);
  iv_trig_desc       varchar2(1000);
  iv_warning_method  varchar2(3);
  v_errmsg           varchar2(4000);
  iv_area_code       varchar2(10);

begin
  iv_resCode := '0';
  iv_resMsg  := 'ok';

  --取告警事件描述信息
  select name
    into iv_trig_desc
    from msg_send_trigevent
   where msg_event_trigid = v_warning_event_id;
  --如果告警内容不空，取传入告警内容直接做为告警信息发送
  if nvl(length(v_warning_content), 0) > 0 then
    iv_warning_content := v_warning_content;
  end if;

  --循环取告警信息配置表数据，对指定的告警发送对象发送短信或邮件
  for rec_w in (select *
                  from warning_method_config
                 where state = '0'
                   and warning_type = v_warning_type) loop
    begin
       --如果指定了告警发送时间，则要进行判断是否在允许时间段内
       if (rec_w.isneedtimelimit is not null and rec_w.isneedtimelimit = 'Y') then
         if(not ( sysdate between to_date(to_char(sysdate,'yyyy-mm-dd')||' '||rec_w.limit_begin_time,'yyyy-mm-dd hh24:mi:ss')
             and to_date(to_char(sysdate,'yyyy-mm-dd')||' '||rec_w.limit_end_time,'yyyy-mm-dd hh24:mi:ss'))) then
            goto over;
         end if;
       end if;

      iv_warning_phone := rec_w.warning_phone;--告警对象电话
      iv_warning_email := rec_w.warning_email;--告警对象邮箱地址
      iv_warning_method := rec_w.warning_method;--告警方式
      iv_area_code := rec_w.zone_number;--告警对象电话所属区号

      --如果告警内容为空，系统生成告警内容
      if nvl(length(v_warning_content), 0) <= 0 then
        --系统自检告警（表明系统正常，只是做为系统的提示信息）
        if rec_w.warning_type = '0' then
          iv_warning_content := '积分系统' ||to_char(sysdate, 'hh24:mi:ss') || '自检：积分系统[' || iv_trig_desc ||
                                ']监控程序还活着!';
        else
          --普通告警和紧急告警
          iv_warning_content := '积分系统' ||
                                to_char(sysdate, 'hh24:mi:ss') || '告警：' ||
                                iv_trig_desc || '；节假日请值班人员处理';
        end if;
      end if;


      --如果发送方式包含邮件发送方式，则判断半天内是否已经发过，若发送过则不重复发送
      if iv_warning_method in ('2','3') then
       select count(1)
         into v_cnt
         from email_buffer_his t
        where t.email_addr = iv_warning_email
          and substr(t.email_content, 16) = substr(iv_warning_content, 16)
          and deal_flag = '1'
          and t.insert_dt > sysdate - 0.5;
       end if;

      --发送短信告警
      if iv_warning_method = '1' then
        --短信表
        insert into msg_buffer
          (msg_id,
           fco_id,
           party_id,
           area_code,
           msg_event_trigid,
           send_nbr,
           nbr_spec,
           batch_id,
           is_approved,
           msg_content,
           start_dt,
           expire_dt,
           deal_flag,
           insert_dt,
           staff_id,
           seq)
          select getSeqID('seq_msg_to_send'),
                 null,
                 null,
                 iv_area_code,
                 v_warning_event_id,
                 iv_warning_phone,
                 '379',
                 null,
                 'Y',
                 iv_warning_content,
                 sysdate,
                 sysdate + 0.5,
                 '0',
                 sysdate,
                 '1001',
                 '1'
            from dual;
        --发送邮件告警
      elsif iv_warning_method = '2' then
        --邮件表(如果发送方式包含邮件发送方式，则判断半天内是否已经发过，若发送过则不重复发送)
        if v_cnt = 0 then
           insert into email_buffer
            (email_id,
             email_event_trigid,
             email_attach_id,
             email_addr,
             email_subject,
             email_content,
             eff_dt,
             exp_dt,
             insert_dt,
             staff_id,
             deal_flag,
             deal_desc,
             send_dt,
             ext_attach_files)
            select getSeqID('seq_email_buffer'),
                   v_warning_event_id,
                   null,
                   iv_warning_email,
                   '积分告警['||iv_trig_desc||']',--邮件主题
                   iv_warning_content,
                   sysdate,
                   sysdate + 0.5,
                   sysdate,
                   '1001',
                   '0',
                   null,
                   null,
                   null
              from dual;
        end if;

      --发送短信和邮件告警
      else
        --短信表
        insert into msg_buffer
          (msg_id,
           fco_id,
           party_id,
           area_code,
           msg_event_trigid,
           send_nbr,
           nbr_spec,
           batch_id,
           is_approved,
           msg_content,
           start_dt,
           expire_dt,
           deal_flag,
           insert_dt,
           staff_id,
           seq)
          select getSeqID('seq_msg_to_send'),
                 null,
                 null,
                 iv_area_code,
                 v_warning_event_id,
                 iv_warning_phone,
                 '379',
                 null,
                 'Y',
                 iv_warning_content,
                 sysdate,
                 sysdate + 0.5,
                 '0',
                 sysdate,
                 '1001',
                 '1'
            from dual;

        --邮件表(如果发送方式包含邮件发送方式，则判断半天内是否已经发过，若发送过则不重复发送)
        if v_cnt = 0 then
           insert into email_buffer
              (email_id,
               email_event_trigid,
               email_attach_id,
               email_addr,
               email_subject,
               email_content,
               eff_dt,
               exp_dt,
               insert_dt,
               staff_id,
               deal_flag,
               deal_desc,
               send_dt,
               ext_attach_files)
              select getSeqID('seq_email_buffer'),
                     v_warning_event_id,
                     null,
                     iv_warning_email,--邮件地址
                     '积分告警['||iv_trig_desc||']',--邮件主题
                     iv_warning_content,--邮件内容
                     sysdate,
                     sysdate + 0.5,--失效时间暂时未12小时
                     sysdate,
                     '1001',
                     '0',
                     null,
                     null,
                     null
                from dual;
          end if;
      end if;

    exception
      when others then
         v_errmsg := substr(sqlerrm, 1, 200);
    end;
    <<over>>null;
  end loop;
 -- commit;

exception
  when others then
    iv_resCode := '1';
    iv_resMsg  := '公用发告警信息处理过程异常：' || substr(sqlerrm, 1, 200);
end;
/

prompt
prompt Creating package body APPROVE_CORE
prompt ==================================
prompt
create or replace package body znrl.approve_core is
   -- xieyt
   --从节点中取值,字符串
  Function Getvaluebyxpath(Innode In Xmldom.Domnode, Inxpath In Varchar2)
    Return Varchar2 Is
    l_Temp Varchar2(1000);
  Begin
    l_Temp := Xslprocessor.Valueof(Innode, Inxpath);
    Return l_Temp;
  Exception
    When Others Then
      Return 'error';
  End;

  --根据Xpath从dom节点中取值,字符串
  Function Getstringbyxpath(Innode In Xmldom.Domnode, Inxpath In Varchar2)
    Return Varchar2 Is
    l_Temp Varchar2(1000);
  Begin
    l_Temp := Getvaluebyxpath(Innode, Inxpath);
    If (l_Temp Is Null) Or (l_Temp = 'error') Then
      Return Null_Str;
    End If;
    Return l_Temp;
  End;

  --取节点的成员数量
  Function Getdomnodelength(Innodes In Xmldom.Domnodelist) Return Integer Is
    l_Number Integer;
  Begin
    l_Number := 0;
    If Not Xmldom.Isnull(Innodes) Then
      l_Number := Xmldom.Getlength(Innodes);
    End If;
    Return l_Number;
  End;


  --新增加审批请求
  procedure addApproveReq(v_apprEventKeyId  in varchar2,
                         v_apprEventType   in varchar2,
                         v_apprEventDesc   in varchar2,
                         v_staffid         in varchar2,
                         v_rescode         out varchar2,
                         v_resmsg          out varchar2) is

    iv_appr_event_id number(12);
    iv_approve_id    number(12);

    iv_count         number(12);


  begin
    v_rescode := '0';
    v_resmsg  := 'ok';

   --预判断审批流程
  begin
     select count(1)
       into iv_count
       from approve_node_config a
      where a.appr_event_type_cd = v_apprEventType;
      
    if iv_count = 0 then
       v_rescode := '1';
       v_resmsg  :='没有找到审批流程，请先配置！';
       return;
    end if;

  exception
    when others then
      rollback;
      v_rescode := '1';
      v_resmsg  := '预判断审批流程出错:' || substr(sqlerrm, 1, 100);
      return;
   end;
   
    begin
      iv_appr_event_id := SEQ_APPROVE.NEXTVAL;
      insert into approve_event_mark
        (event_id,
         approve_event_id,
         appr_event_type_cd,
         appr_event_desc)
      values
        (iv_appr_event_id,
         v_apprEventKeyId,
         v_apprEventType,
         v_apprEventDesc);
    exception
      when others then
        rollback;
        v_rescode := '1';
        v_resmsg  := '插入审批数据出错:' || substr(sqlerrm, 1, 100);
        return;
    end;

    begin
      for cur in (select a.approve_node_cd,
                         case
                           when a.node_type in
                                (APPRNODE_TYPE_HEAD, APPRNODE_TYPE_ONLY) then
                            'Y'
                           else
                            'N'
                         end is_currentNode
                    from approve_node_config a
                   where a.appr_event_type_cd = v_apprEventType
                     and a.node_status = 1
                   order by a.node_order) loop

        iv_approve_id := SEQ_APPROVE.NEXTVAL;

        --插入审批信息
        insert into APPROVE_INFO
          (approve_id,
           approve_node_cd,
           event_id,
           is_current,
           is_approved,
           approve_initiator,
           approve_req_dt,
           appr_status)
        values
          (iv_approve_id,
           cur.approve_node_cd,
           iv_appr_event_id,
           cur.is_currentNode,
           'N',
           v_staffid,
           sysdate,
           '0');

      end loop;

    exception
      when others then
        rollback;
        v_rescode := '1';
        v_resmsg  := '插入审批节点数据出错:' || substr(sqlerrm, 1, 100);
        return;
    end;
  end;

  

--保存审批结果
procedure saveApproveResult(v_jsonStr  in varchar2,
                            v_StaffId  in varchar2,
                            v_isOk     in varchar2,
                            v_apprDesc in varchar2,
                            v_rescode  out varchar2,
                            v_resmsg   out varchar2) is
  iv_jsonstr_list json_list;
  iv_jv           json_value;
  iv_apprId       varchar2(16);

begin
  v_rescode := '0';
  v_resmsg  := 'ok';
  
  iv_jsonstr_list := json_list(v_jsonStr);
  
  for i in 1 .. iv_jsonstr_list.count loop
  
    iv_jv := iv_jsonstr_list.get_elem(i);
  
    iv_apprId := iv_jv.get_string();
  
    saveSingleApproveResult(iv_apprId,
                            v_StaffId,
                            v_isOk,
                            v_apprDesc,
                            v_rescode,
                            v_resmsg);
  
    if v_resCode <> '0' then
      rollback;
      return;
    end if;
  
  end loop;
  
  commit;
exception
  when others then
    rollback;
    v_rescode := '0';
    v_resmsg  := 'ok';
    return;
end;



  
  --保存审批结果
  procedure saveSingleApproveResult(v_approveId         in varchar2,
                                    v_StaffId           in varchar2,
                                    v_approveResult     in varchar2,
                                    v_apprDescription   in varchar2,
                                    v_rescode           out varchar2,
                                    v_resmsg            out varchar2) is

    iv_approveId     number(12);
    iv_apprNodeCd    number(12);
    iv_eventId       number(12);
    iv_nodeType      number(1);
    iv_nodeOrder     number(4);
    iv_nextNodeOrder number(4);
    iv_nextApproveId number(12);
    iv_apprEventType number(5);

    
  begin
    v_rescode := '0';
    v_resmsg  := 'ok';

    begin
      select a.approve_id,
             a.approve_node_cd,
             a.event_id,
             b.node_type,
             b.node_order,
             b.appr_event_type_cd
        into iv_approveId,
             iv_apprNodeCd,
             iv_eventId,
             iv_nodeType,
             iv_nodeOrder,
             iv_apprEventType
        from approve_info a, approve_node_config b
       where a.approve_node_cd = b.approve_node_cd
         and a.approve_id = v_approveId;
    exception
      when others then
        v_rescode := '1';
        v_resmsg  := '查询审批数据出错:' || substr(sqlerrm, 1, 100);
        return;
    end;

    begin
      update approve_info a
         set a.is_current   = 'N',
             a.is_ok        = v_approveResult,
             a.is_approved  = 'Y',
             a.appr_status  = '0',
             a.approve_desc = v_apprDescription,
             a.approved_dt  = sysdate
       where a.approve_id = v_approveId;
    exception
      when others then
        rollback;
        v_rescode := '1';
        v_resmsg  := '修改审批数据出错:' || substr(sqlerrm, 1, 100);
        return;
    end;

    if v_approveResult = 'N' then
      --审批未通过，剩下的节点都作废
      update approve_info a
         set a.is_current   = 'N',
             a.appr_status  = '4',
             a.approve_desc = '审批未通过，系统自动作废该节点'
       where a.event_id = iv_eventId
         and a.is_approved  = 'N';
      
      --如果审批未通过，则直接回写数据
      writeBackApproveEvent(iv_apprEventType,
                            iv_eventId,
                            v_approveResult,
                            v_rescode,
                            v_resmsg);
                            
      if v_rescode <> '0' then
        rollback;
        v_rescode := '1';
        v_resmsg  := '回写审批业务数据失败:' || substr(sqlerrm, 1, 100);
        return;
      end if;
      
      commit;
      return;--到此可以结束了
    end if;


    if iv_nodeType in (APPRNODE_TYPE_TAIL, APPRNODE_TYPE_ONLY) then
      --如果是尾节点，审批完成，回写被审批数据
      writeBackApproveEvent(iv_apprEventType,
                            iv_eventId,
                            v_approveResult,
                            v_rescode,
                            v_resmsg);
                            
      if v_rescode <> '0' then
        rollback;
        v_rescode := '1';
        v_resmsg  := '回写审批业务数据失败1:' || substr(sqlerrm, 1, 100);
        return;
      end if;
    else
      --查下个审批节点
      begin
        select min(b.node_order)
          into iv_nextNodeOrder
          from approve_info a, approve_node_config b
         where a.event_id = iv_eventId
           and a.approve_node_cd = b.approve_node_cd
           and b.node_order > iv_nodeOrder;

        select a.approve_id
          into iv_nextApproveId
          from approve_info a, approve_node_config b
         where a.event_id = iv_eventId
           and a.approve_node_cd = b.approve_node_cd
           and b.node_order = iv_nextNodeOrder;
      exception
        when others then
          v_rescode := '1';
          v_resmsg  := '准备下一步审批数据出错:' || substr(sqlerrm, 1, 100);
          return;
      end;

      begin
        update approve_info a
           set a.is_current = 'Y'
         where a.approve_id = iv_nextApproveId;
      exception
        when others then
          rollback;
          v_rescode := '1';
          v_resmsg  := '修改下一步审批数据出错:' || substr(sqlerrm, 1, 100);
          return;
      end;

    end if;

    commit;
  end;
  
  
  --回写被审批数据，修改其状态
  procedure writeBackApproveEvent(v_apprEventType in number,
                                  v_eventId       in number,
                                  v_approveResult in varchar2,
                                  v_rescode       out varchar2,
                                  v_resmsg        out varchar2) is
    iv_eventKeyId      number(20);
  begin
    v_rescode := '0';
    v_resmsg  := 'ok';
    begin
      select a.approve_event_id
        into iv_eventKeyId
        from approve_event_mark a
       where a.event_id = v_eventId
         and a.appr_event_type_cd = v_apprEventType;
    exception
      when others then
        v_rescode := '1';
        v_resmsg  := '查询被审批数据出错:' || substr(sqlerrm, 1, 100);
        return;
    end;

    if v_apprEventType IN (APPREVENT_TYPE_DROPSAMPLE, APPREVENT_TYPE_GETSAMPLE) then
      --取样、弃样
      begin
        if v_approveResult = 'Y' then
          --记录样品操作记录
          pk_sample_store.cabinet_op_record(iv_eventKeyId,
                                            v_rescode,
                                            v_resmsg);
        else
          update appr_req_data_buffer a
             set a.appr_res = 'N' --标识审批未通过了
           where a.param1 = iv_eventKeyId;
        end if;
      exception
        when others then
          rollback;
          v_rescode := '1';
          v_resmsg  := '回写新增子俱乐部被审批数据状态出错:' || substr(sqlerrm, 1, 100);
          return;
      end;

    end if;
  end;
  
  
end approve_core;
/

prompt
prompt Creating package body JSON_EXT
prompt ==============================
prompt
create or replace package body znrl.json_ext as
  scanner_exception exception;
  pragma exception_init(scanner_exception, -20100);
  parser_exception exception;
  pragma exception_init(parser_exception, -20101);
  jext_exception exception;
  pragma exception_init(jext_exception, -20110);

  --extra function checks if number has no fraction
  function is_integer(v json_value) return boolean as
    myint number(38); --the oracle way to specify an integer
  begin
    if(v.is_number) then
      myint := v.get_number;
      return (myint = v.get_number); --no rounding errors?
    else
      return false;
    end if;
  end;

  --extension enables json to store dates without comprimising the implementation
  function to_json_value(d date) return json_value as
  begin
    return json_value(to_char(d, format_string));
  end;

  --notice that a date type in json is also a varchar2
  function is_date(v json_value) return boolean as
    temp date;
  begin
    temp := json_ext.to_date2(v);
    return true;
  exception
    when others then
      return false;
  end;

  --convertion is needed to extract dates
  function to_date2(v json_value) return date as
  begin
    if(v.is_string) then
      return to_date(v.get_string, format_string);
    else
      raise_application_error(-20110, 'Anydata did not contain a date-value');
    end if;
  exception
    when others then
      raise_application_error(-20110, 'Anydata did not contain a date on the format: '||format_string);
  end;

  --Json Path parser
  function parsePath(json_path varchar2) return json_list as
    build_path varchar2(32767) := '[';
    buf varchar2(4);
    endstring varchar2(1);
    indx number := 1;

    procedure next_char as
    begin
      if(indx <= length(json_path)) then
        buf := substr(json_path, indx, 1);
        indx := indx + 1;
      else
        buf := null;
      end if;
    end;
    --skip ws
    procedure skipws as begin while(buf in (chr(9),chr(10),chr(13),' ')) loop next_char; end loop; end;

  begin
    next_char();
    while(buf is not null) loop
      if(buf = '.') then
        next_char();
        if(buf is null) then raise_application_error(-20110, 'JSON Path parse error: . is not a valid json_path end'); end if;
        if(not regexp_like(buf, '^[[:alnum:]\_ ]+', 'c') ) then
          raise_application_error(-20110, 'JSON Path parse error: alpha-numeric character or space expected at position '||indx);
        end if;

        if(build_path != '[') then build_path := build_path || ','; end if;
        build_path := build_path || '"';
        while(regexp_like(buf, '^[[:alnum:]\_ ]+', 'c') ) loop
          build_path := build_path || buf;
          next_char();
        end loop;
        build_path := build_path || '"';
      elsif(buf = '[') then
        next_char();
        skipws();
        if(buf is null) then raise_application_error(-20110, 'JSON Path parse error: [ is not a valid json_path end'); end if;
        if(buf in ('1','2','3','4','5','6','7','8','9')) then
          if(build_path != '[') then build_path := build_path || ','; end if;
          while(buf in ('0','1','2','3','4','5','6','7','8','9')) loop
            build_path := build_path || buf;
            next_char();
          end loop;
        elsif (regexp_like(buf, '^(\"|\'')', 'c')) then
          endstring := buf;
          if(build_path != '[') then build_path := build_path || ','; end if;
          build_path := build_path || '"';
          next_char();
          if(buf is null) then raise_application_error(-20110, 'JSON Path parse error: premature json_path end'); end if;
          while(buf != endstring) loop
            build_path := build_path || buf;
            next_char();
            if(buf is null) then raise_application_error(-20110, 'JSON Path parse error: premature json_path end'); end if;
            if(buf = '\') then
              next_char();
              build_path := build_path || '\' || buf;
              next_char();
            end if;
          end loop;
          build_path := build_path || '"';
          next_char();
        else
          raise_application_error(-20110, 'JSON Path parse error: expected a string or an positive integer at '||indx);
        end if;
        skipws();
        if(buf is null) then raise_application_error(-20110, 'JSON Path parse error: premature json_path end'); end if;
        if(buf != ']') then raise_application_error(-20110, 'JSON Path parse error: no array ending found. found: '|| buf); end if;
        next_char();
        skipws();
      elsif(build_path = '[') then
        if(not regexp_like(buf, '^[[:alnum:]\_ ]+', 'c') ) then
          raise_application_error(-20110, 'JSON Path parse error: alpha-numeric character or space expected at position '||indx);
        end if;
        build_path := build_path || '"';
        while(regexp_like(buf, '^[[:alnum:]\_ ]+', 'c') ) loop
          build_path := build_path || buf;
          next_char();
        end loop;
        build_path := build_path || '"';
      else
        raise_application_error(-20110, 'JSON Path parse error: expected . or [ found '|| buf || ' at position '|| indx);
      end if;

    end loop;

    build_path := build_path || ']';
    build_path := replace(replace(replace(replace(replace(build_path, chr(9), '\t'), chr(10), '\n'), chr(13), '\f'), chr(8), '\b'), chr(14), '\r');
    return json_list(build_path);
  end parsePath;

  --JSON Path getters
  function get_json_value(obj json, v_path varchar2) return json_value as
    path json_list;
    ret json_value;
    o json; l json_list;
  begin
    path := parsePath(v_path);
    ret := obj.to_json_value;
    if(path.count = 0) then return ret; end if;

    for i in 1 .. path.count loop
      if(path.get_elem(i).is_string()) then
        --string fetch only on json
        o := json(ret);
        ret := o.get(path.get_elem(i).get_string());
      else
        --number fetch on json and json_list
        if(ret.is_array()) then
          l := json_list(ret);
          ret := l.get_elem(path.get_elem(i).get_number());
        else
          o := json(ret);
          l := o.get_values();
          ret := l.get_elem(path.get_elem(i).get_number());
        end if;
      end if;
    end loop;

    return ret;
  exception
    when scanner_exception then raise;
    when parser_exception then raise;
    when jext_exception then raise;
    when others then return null;
  end get_json_value;

  --JSON Path getters
  function get_string(obj json, path varchar2) return varchar2 as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not temp.is_string) then
      return null;
    else
      return temp.get_string;
    end if;
  end;

  function get_number(obj json, path varchar2) return number as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not temp.is_number) then
      return null;
    else
      return temp.get_number;
    end if;
  end;

  function get_json(obj json, path varchar2) return json as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not temp.is_object) then
      return null;
    else
      return json(temp);
    end if;
  end;

  function get_json_list(obj json, path varchar2) return json_list as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not temp.is_array) then
      return null;
    else
      return json_list(temp);
    end if;
  end;

  function get_bool(obj json, path varchar2) return boolean as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not temp.is_bool) then
      return null;
    else
      return temp.get_bool;
    end if;
  end;

  function get_date(obj json, path varchar2) return date as
    temp json_value;
  begin
    temp := get_json_value(obj, path);
    if(temp is null or not is_date(temp)) then
      return null;
    else
      return json_ext.to_date2(temp);
    end if;
  end;

  /* JSON Path putter internal function */
  procedure put_internal(obj in out nocopy json, v_path varchar2, elem json_value) as
    val json_value := elem;
    path json_list;
    backreference json_list := json_list();

    keyval json_value; keynum number; keystring varchar2(4000);
    temp json_value := obj.to_json_value;
    obj_temp  json;
    list_temp json_list;
    inserter json_value;
  begin
    path := json_ext.parsePath(v_path);
    if(path.count = 0) then raise_application_error(-20110, 'JSON_EXT put error: cannot put with empty string.'); end if;

    --build backreference
    for i in 1 .. path.count loop
      --backreference.print(false);
      keyval := path.get_elem(i);
      if (keyval.is_number()) then
        --nummer index
        keynum := keyval.get_number();
        if((not temp.is_object()) and (not temp.is_array())) then
          if(val is null) then return; end if;
          backreference.remove_last;
          temp := json_list().to_json_value();
          backreference.add_elem(temp);
        end if;

        if(temp.is_object()) then
          obj_temp := json(temp);
          if(obj_temp.count < keynum) then
            if(val is null) then return; end if;
            raise_application_error(-20110, 'JSON_EXT put error: access object with to few members.');
          end if;
          temp := obj_temp.get(keynum);
        else
          list_temp := json_list(temp);
          if(list_temp.count < keynum) then
            if(val is null) then return; end if;
            --raise error or quit if val is null
            for i in list_temp.count+1 .. keynum loop
              list_temp.add_elem(json_value.makenull);
            end loop;
            backreference.remove_last;
            backreference.add_elem(list_temp);
          end if;

          temp := list_temp.get_elem(keynum);
        end if;
      else
        --streng index
        keystring := keyval.get_string();
        if(not temp.is_object()) then
          --backreference.print;
          if(val is null) then return; end if;
          backreference.remove_last;
          temp := json().to_json_value();
          backreference.add_elem(temp);
          --raise_application_error(-20110, 'JSON_ext put error: trying to access a non object with a string.');
        end if;
        obj_temp := json(temp);
        temp := obj_temp.get(keystring);
      end if;

      if(temp is null) then
        if(val is null) then return; end if;
        --what to expect?
        keyval := path.get_elem(i+1);
        if(keyval is not null and keyval.is_number()) then
          temp := json_list().to_json_value;
        else
          temp := json().to_json_value;
        end if;
      end if;
      backreference.add_elem(temp);
    end loop;

  --  backreference.print(false);
  --  path.print(false);

    --use backreference and path together
    inserter := val;
    for i in reverse 1 .. backreference.count loop
  --    inserter.print(false);
      if( i = 1 ) then
        keyval := path.get_elem(1);
        if(keyval.is_string()) then
          keystring := keyval.get_string();
        else
          keynum := keyval.get_number();
          declare
            t1 json_value := obj.get(keynum);
          begin
            keystring := t1.mapname;
          end;
        end if;
        if(inserter is null) then obj.remove(keystring); else obj.put(keystring, inserter); end if;
      else
        temp := backreference.get_elem(i-1);
        if(temp.is_object()) then
          keyval := path.get_elem(i);
          obj_temp := json(temp);
          if(keyval.is_string()) then
            keystring := keyval.get_string();
          else
            keynum := keyval.get_number();
            declare
              t1 json_value := obj_temp.get(keynum);
            begin
              keystring := t1.mapname;
            end;
          end if;
          if(inserter is null) then
            obj_temp.remove(keystring);
            if(obj_temp.count > 0) then inserter := obj_temp.to_json_value; end if;
          else
            obj_temp.put(keystring, inserter);
            inserter := obj_temp.to_json_value;
          end if;
        else
          --array only number
          keynum := path.get_elem(i).get_number();
          list_temp := json_list(temp);
          list_temp.remove_elem(keynum);
          if(not inserter is null) then
            list_temp.add_elem(inserter, keynum);
            inserter := list_temp.to_json_value;
          else
            if(list_temp.count > 0) then inserter := list_temp.to_json_value; end if;
          end if;
        end if;
      end if;

    end loop;

  end put_internal;

  /* JSON Path putters */
  procedure put(obj in out nocopy json, path varchar2, elem varchar2) as
  begin
    put_internal(obj, path, json_value(elem));
  end;

  procedure put(obj in out nocopy json, path varchar2, elem number) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, json_value(elem));
  end;

  procedure put(obj in out nocopy json, path varchar2, elem json) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, elem.to_json_value);
  end;

  procedure put(obj in out nocopy json, path varchar2, elem json_list) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, elem.to_json_value);
  end;

  procedure put(obj in out nocopy json, path varchar2, elem boolean) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, json_value(elem));
  end;

  procedure put(obj in out nocopy json, path varchar2, elem json_value) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, elem);
  end;

  procedure put(obj in out nocopy json, path varchar2, elem date) as
  begin
    if(elem is null) then raise_application_error(-20110, 'Cannot put null-value'); end if;
    put_internal(obj, path, json_ext.to_json_value(elem));
  end;

  procedure remove(obj in out nocopy json, path varchar2) as
  begin
    json_ext.put_internal(obj,path,null);
--    if(json_ext.get_json_value(obj,path) is not null) then
--    end if;
  end remove;

    --Pretty print with JSON Path
  function pp(obj json, v_path varchar2) return varchar2 as
    json_part json_value;
  begin
    json_part := json_ext.get_json_value(obj, v_path);
    if(json_part is null) then
      return '';
    else
      return json_printer.pretty_print_any(json_part); --escapes a possible internal string
    end if;
  end pp;

  procedure pp(obj json, v_path varchar2) as --using dbms_output.put_line
  begin
    dbms_output.put_line(pp(obj, v_path));
  end pp;

  -- spaces = false!
  procedure pp_htp(obj json, v_path varchar2) as --using htp.print
    json_part json_value;
  begin
    json_part := json_ext.get_json_value(obj, v_path);
    if(json_part is null) then htp.print; else
      htp.print(json_printer.pretty_print_any(json_part, false));
    end if;
  end pp_htp;

  function base64(binarydata blob) return json_list as
    obj json_list := json_list();
    c clob := empty_clob();
    benc blob;

    v_blob_offset NUMBER := 1;
    v_clob_offset NUMBER := 1;
    v_lang_context NUMBER := DBMS_LOB.DEFAULT_LANG_CTX;
    v_warning NUMBER;
    v_amount PLS_INTEGER;
--    temp varchar2(32767);

    FUNCTION encodeBlob2Base64(pBlobIn IN BLOB) RETURN BLOB IS
      vAmount NUMBER := 45;
      vBlobEnc BLOB := empty_blob();
      vBlobEncLen NUMBER := 0;
      vBlobInLen NUMBER := 0;
      vBuffer RAW(45);
      vOffset NUMBER := 1;
    BEGIN
      dbms_output.put_line('Start base64 encoding.');
      vBlobInLen := dbms_lob.getlength(pBlobIn);
      dbms_output.put_line('<BlobInLength>' || vBlobInLen);
      dbms_lob.createtemporary(vBlobEnc, TRUE);
      LOOP
        IF vOffset >= vBlobInLen THEN
          EXIT;
        END IF;
        dbms_lob.read(pBlobIn, vAmount, vOffset, vBuffer);
        BEGIN
          dbms_lob.append(vBlobEnc, utl_encode.base64_encode(vBuffer));
        EXCEPTION
          WHEN OTHERS THEN
          dbms_output.put_line('<vAmount>' || vAmount || '<vOffset>' || vOffset || '<vBuffer>' || vBuffer);
          dbms_output.put_line('ERROR IN append: ' || SQLERRM);
          RAISE;
        END;
        vOffset := vOffset + vAmount;
      END LOOP;
      vBlobEncLen := dbms_lob.getlength(vBlobEnc);
      dbms_output.put_line('<BlobEncLength>' || vBlobEncLen);
      dbms_output.put_line('Finshed base64 encoding.');
      RETURN vBlobEnc;
    END encodeBlob2Base64;
  begin
    benc := encodeBlob2Base64(binarydata);
    dbms_lob.createtemporary(c, TRUE);
    v_amount := DBMS_LOB.GETLENGTH(benc);
    DBMS_LOB.CONVERTTOCLOB(c, benc, v_amount, v_clob_offset, v_blob_offset, 1, v_lang_context, v_warning);

    v_amount := DBMS_LOB.GETLENGTH(c);
    v_clob_offset := 1;
    --dbms_output.put_line('V amount: '||v_amount);
    while(v_clob_offset < v_amount) loop
      --dbms_output.put_line(v_offset);
      --temp := ;
      --dbms_output.put_line('size: '||length(temp));
      obj.add_elem(dbms_lob.SUBSTR(c, 4000,v_clob_offset));
      v_clob_offset := v_clob_offset + 4000;
    end loop;
    dbms_lob.freetemporary(benc);
    dbms_lob.freetemporary(c);
  --dbms_output.put_line(obj.count);
  --dbms_output.put_line(obj.get_last().to_char);
    return obj;

  end base64;


  function base64(l json_list) return blob as
    c clob := empty_clob();
    b blob := empty_blob();
    bret blob;

    v_blob_offset NUMBER := 1;
    v_clob_offset NUMBER := 1;
    v_lang_context NUMBER := DBMS_LOB.DEFAULT_LANG_CTX;
    v_warning NUMBER;
    v_amount PLS_INTEGER;

    FUNCTION decodeBase642Blob(pBlobIn IN BLOB) RETURN BLOB IS
      vAmount NUMBER := 256;--32;
      vBlobDec BLOB := empty_blob();
      vBlobDecLen NUMBER := 0;
      vBlobInLen NUMBER := 0;
      vBuffer RAW(256);--32);
      vOffset NUMBER := 1;
    BEGIN
      dbms_output.put_line('Start base64 decoding.');
      vBlobInLen := dbms_lob.getlength(pBlobIn);
      dbms_output.put_line('<BlobInLength>' || vBlobInLen);
      dbms_lob.createtemporary(vBlobDec, TRUE);
      LOOP
        IF vOffset >= vBlobInLen THEN
          EXIT;
        END IF;
        dbms_lob.read(pBlobIn, vAmount, vOffset, vBuffer);
        BEGIN
          dbms_lob.append(vBlobDec, utl_encode.base64_decode(vBuffer));
        EXCEPTION
          WHEN OTHERS THEN
          dbms_output.put_line('<vAmount>' || vAmount || '<vOffset>' || vOffset || '<vBuffer>' || vBuffer);
          dbms_output.put_line('ERROR IN append: ' || SQLERRM);
          RAISE;
        END;
        vOffset := vOffset + vAmount;
      END LOOP;
      vBlobDecLen := dbms_lob.getlength(vBlobDec);
      dbms_output.put_line('<BlobDecLength>' || vBlobDecLen);
      dbms_output.put_line('Finshed base64 decoding.');
      RETURN vBlobDec;
    END decodeBase642Blob;
  begin
    dbms_lob.createtemporary(c, TRUE);
    for i in 1 .. l.count loop
      dbms_lob.append(c, l.get_elem(i).to_char(false));
    end loop;
    v_amount := DBMS_LOB.GETLENGTH(c);
--    dbms_output.put_line('L C'||v_amount);

    dbms_lob.createtemporary(b, TRUE);
    DBMS_LOB.CONVERTTOBLOB(b, c, v_amount, v_clob_offset, v_blob_offset, 1, v_lang_context, v_warning);
    dbms_lob.freetemporary(c);
    v_amount := DBMS_LOB.GETLENGTH(b);
--    dbms_output.put_line('L B'||v_amount);

    bret := decodeBase642Blob(b);
    dbms_lob.freetemporary(b);
    return bret;

  end base64;


end json_ext;
/

prompt
prompt Creating package body JSON_PARSER
prompt =================================
prompt
CREATE OR REPLACE PACKAGE BODY ZNRL."JSON_PARSER" as
  /*
  Copyright (c) 2009 Jonas Krogsboell

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
  */

  /*type json_src is record (len number, offset number, src varchar2(10), s_clob clob); */
  function next_char(indx number, s in out nocopy json_src) return varchar2 as
  begin
    if(indx > s.len) then return null; end if;
    --right offset?
    if(indx > 4000 + s.offset or indx < s.offset) then
    --load right offset
      s.offset := indx - (indx mod 4000);
      s.src := dbms_lob.substr(s.s_clob, 4000, s.offset+1);
    end if;
    --read from s.src
    return substr(s.src, indx-s.offset, 1);
  end;

  function next_char2(indx number, s in out nocopy json_src, amount number default 1) return varchar2 as
    buf varchar2(32767) := '';
  begin
    for i in 1..amount loop
      buf := buf || next_char(indx-1+i,s);
    end loop;
    return buf;
  end;

  function prepareClob(buf clob) return json_parser.json_src as
    temp json_parser.json_src;
  begin
    temp.s_clob := buf;
    temp.offset := 0;
    temp.src := dbms_lob.substr(buf, 4000, temp.offset+1);
    temp.len := dbms_lob.getlength(buf);
    return temp;
  end;

  function prepareVarchar2(buf varchar2) return json_parser.json_src as
    temp json_parser.json_src;
  begin
    temp.s_clob := buf;
    temp.offset := 0;
    temp.src := substr(buf, 1, 4000);
    temp.len := length(buf);
    return temp;
  end;

  procedure debug(text varchar2) as
  begin
    dbms_output.put_line(text);
  end;

  procedure print_token(t rToken) as
  begin
    dbms_output.put_line('Line: '||t.line||' - Column: '||t.col||' - Type: '||t.type_name||' - Content: '||t.data);
  end print_token;

  /* SCANNER FUNCTIONS START */
  procedure s_error(text varchar2, line number, col number) as
  begin
    raise_application_error(-20100, 'JSON Scanner exception @ line: '||line||' column: '||col||' - '||text);
  end;

  procedure s_error(text varchar2, tok rToken) as
  begin
    raise_application_error(-20100, 'JSON Scanner exception @ line: '||tok.line||' column: '||tok.col||' - '||text);
  end;

  function mt(t varchar2, l pls_integer, c pls_integer, d varchar2) return rToken as
    token rToken;
  begin
    token.type_name := t;
    token.line := l;
    token.col := c;
    token.data := d;
    return token;
  end;

  function lexNumber(jsrc in out nocopy json_src, tok in out nocopy rToken, indx in out nocopy pls_integer) return pls_integer as
    numbuf varchar2(4000) := '';
    buf varchar2(4);
    checkLoop boolean;
  begin
    buf := next_char(indx, jsrc);
    if(buf = '-') then numbuf := '-'; indx := indx + 1; end if;
    buf := next_char(indx, jsrc);
    --0 or [1-9]([0-9])*
    if(buf = '0') then
      numbuf := numbuf || '0'; indx := indx + 1;
      buf := next_char(indx, jsrc);
    elsif(buf >= '1' and buf <= '9') then
      numbuf := numbuf || buf; indx := indx + 1;
      --read digits
      buf := next_char(indx, jsrc);
      while(buf >= '0' and buf <= '9') loop
        numbuf := numbuf || buf; indx := indx + 1;
        buf := next_char(indx, jsrc);
      end loop;
    end if;
    --fraction
    if(buf = '.') then
      numbuf := numbuf || buf; indx := indx + 1;
      buf := next_char(indx, jsrc);
      checkLoop := FALSE;
      while(buf >= '0' and buf <= '9') loop
        checkLoop := TRUE;
        numbuf := numbuf || buf; indx := indx + 1;
        buf := next_char(indx, jsrc);
      end loop;
      if(not checkLoop) then
        s_error('Expected: digits in fraction', tok);
      end if;
    end if;
    --exp part
    if(buf in ('e', 'E')) then
      numbuf := numbuf || buf; indx := indx + 1;
      buf := next_char(indx, jsrc);
      if(buf = '+' or buf = '-') then
        numbuf := numbuf || buf; indx := indx + 1;
        buf := next_char(indx, jsrc);
      end if;
      checkLoop := FALSE;
      while(buf >= '0' and buf <= '9') loop
        checkLoop := TRUE;
        numbuf := numbuf || buf; indx := indx + 1;
        buf := next_char(indx, jsrc);
      end loop;
      if(not checkLoop) then
        s_error('Expected: digits in exp', tok);
      end if;
    end if;

    tok.data := numbuf;
    return indx;
  end lexNumber;

  -- [a-zA-Z]([a-zA-Z0-9])*
  function lexName(jsrc in out nocopy json_src, tok in out nocopy rToken, indx in out nocopy pls_integer) return pls_integer as
    varbuf varchar2(4000) := '';
    buf varchar(4);
    num number;
  begin
    buf := next_char(indx, jsrc);
    while(REGEXP_LIKE(buf, '^[[:alnum:]\_]$', 'i')) loop
      varbuf := varbuf || buf;
      indx := indx + 1;
      buf := next_char(indx, jsrc);
      if (buf is null) then
        goto retname;
        --debug('Premature string ending');
      end if;
    end loop;
    <<retname>>

    --could check for reserved keywords here

    --debug(varbuf);
    tok.data := varbuf;
    return indx-1;
  end lexName;

  function lexString(jsrc in out nocopy json_src, tok in out nocopy rToken, indx in out nocopy pls_integer, endChar char) return pls_integer as
    varbuf varchar2(32767) := '';
    buf varchar(4);
    wrong boolean;
  begin
    indx := indx +1;
    buf := next_char(indx, jsrc);
    while(buf != endChar) loop
      if(buf = Chr(13) or buf = CHR(9) or buf = CHR(10)) then
        s_error('Control characters not allowed (CHR(9),CHR(10)CHR(13))', tok);
      end if;
      if(buf = '\') then
        --varbuf := varbuf || buf;
        indx := indx + 1;
        buf := next_char(indx, jsrc);
        case
          when buf in ('\') then
            varbuf := varbuf || buf || buf;
            indx := indx + 1;
            buf := next_char(indx, jsrc);
          when buf in ('"', '/') then
            varbuf := varbuf || buf;
            indx := indx + 1;
            buf := next_char(indx, jsrc);
          when buf = '''' then
            if(json_strict = false) then
              varbuf := varbuf || buf;
              indx := indx + 1;
              buf := next_char(indx, jsrc);
            else
              s_error('strictmode - expected: " \ / b f n r t u ', tok);
            end if;
          when buf in ('b', 'f', 'n', 'r', 't') then
            --backspace b = U+0008
            --formfeed  f = U+000C
            --newline   n = U+000A
            --carret    r = U+000D
            --tabulator t = U+0009
            case buf
            when 'b' then varbuf := varbuf || chr(8);
            when 'f' then varbuf := varbuf || chr(13);
            when 'n' then varbuf := varbuf || chr(10);
            when 'r' then varbuf := varbuf || chr(14);
            when 't' then varbuf := varbuf || chr(9);
            end case;
            --varbuf := varbuf || buf;
            indx := indx + 1;
            buf := next_char(indx, jsrc);
          when buf = 'u' then
            --four hexidecimal chars
            declare
              four varchar2(4);
            begin
              four := next_char2(indx+1, jsrc, 4);
              wrong := FALSE;
              if(upper(substr(four, 1,1)) not in ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f')) then wrong := TRUE; end if;
              if(upper(substr(four, 2,1)) not in ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f')) then wrong := TRUE; end if;
              if(upper(substr(four, 3,1)) not in ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f')) then wrong := TRUE; end if;
              if(upper(substr(four, 4,1)) not in ('0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f')) then wrong := TRUE; end if;
              if(wrong) then
                s_error('expected: " \u([0-9][A-F]){4}', tok);
              end if;
--              varbuf := varbuf || buf || four;
              varbuf := varbuf || '\'||four;--chr(to_number(four,'XXXX'));
              indx := indx + 5;
              buf := next_char(indx, jsrc);
              end;
          else
            s_error('expected: " \ / b f n r t u ', tok);
        end case;
      else
        varbuf := varbuf || buf;
        indx := indx + 1;
        buf := next_char(indx, jsrc);
      end if;
    end loop;

    if (buf is null) then
      s_error('string ending not found', tok);
      --debug('Premature string ending');
    end if;

    --debug(varbuf);
    --dbms_output.put_line(varbuf);
    tok.data := unistr(varbuf);
    return indx;
  end lexString;

  /* scanner tokens:
    '{', '}', ',', ':', '[', ']', STRING, NUMBER, TRUE, FALSE, NULL
  */
  function lexer(jsrc in out nocopy json_src) return lTokens as
    tokens lTokens;
    indx pls_integer := 1;
    tok_indx pls_integer := 1;
    buf varchar2(4);
    lin_no number := 1;
    col_no number := 0;
  begin
    while (indx <= jsrc.len) loop
      --read into buf
      buf := next_char(indx, jsrc);
      col_no := col_no + 1;
      --convert to switch case
      case
        when buf = '{' then tokens(tok_indx) := mt('{', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = '}' then tokens(tok_indx) := mt('}', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = ',' then tokens(tok_indx) := mt(',', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = ':' then tokens(tok_indx) := mt(':', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = '[' then tokens(tok_indx) := mt('[', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = ']' then tokens(tok_indx) := mt(']', lin_no, col_no, null); tok_indx := tok_indx + 1;
        when buf = 't' then
          if(next_char2(indx, jsrc, 4) != 'true') then
            if(json_strict = false and REGEXP_LIKE(buf, '^[[:alpha:]]$', 'i')) then
              tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
              indx := lexName(jsrc, tokens(tok_indx), indx);
              col_no := col_no + length(tokens(tok_indx).data) + 1;
              tok_indx := tok_indx + 1;
            else
              s_error('Expected: ''true''', lin_no, col_no);
            end if;
          else
            tokens(tok_indx) := mt('TRUE', lin_no, col_no, null); tok_indx := tok_indx + 1;
            indx := indx + 3;
            col_no := col_no + 3;
          end if;
        when buf = 'n' then
          if(next_char2(indx, jsrc, 4) != 'null') then
            if(json_strict = false and REGEXP_LIKE(buf, '^[[:alpha:]]$', 'i')) then
              tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
              indx := lexName(jsrc, tokens(tok_indx), indx);
              col_no := col_no + length(tokens(tok_indx).data) + 1;
              tok_indx := tok_indx + 1;
            else
              s_error('Expected: ''null''', lin_no, col_no);
            end if;
          else
            tokens(tok_indx) := mt('NULL', lin_no, col_no, null); tok_indx := tok_indx + 1;
            indx := indx + 3;
            col_no := col_no + 3;
          end if;
        when buf = 'f' then
          if(next_char2(indx, jsrc, 5) != 'false') then
            if(json_strict = false and REGEXP_LIKE(buf, '^[[:alpha:]]$', 'i')) then
              tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
              indx := lexName(jsrc, tokens(tok_indx), indx);
              col_no := col_no + length(tokens(tok_indx).data) + 1;
              tok_indx := tok_indx + 1;
            else
              s_error('Expected: ''false''', lin_no, col_no);
            end if;
          else
            tokens(tok_indx) := mt('FALSE', lin_no, col_no, null); tok_indx := tok_indx + 1;
            indx := indx + 4;
            col_no := col_no + 4;
          end if;
        /*   -- 9 = TAB, 10 = \n, 13 = \r (Linux = \n, Windows = \r\n, Mac = \r */
        when (buf = Chr(10)) then --linux newlines
          lin_no := lin_no + 1;
          col_no := 0;

        when (buf = Chr(13)) then --Windows or Mac way
          lin_no := lin_no + 1;
          col_no := 0;
          if(jsrc.len >= indx +1) then -- better safe than sorry
            buf := next_char(indx+1, jsrc);
            if(buf = Chr(10)) then --\r\n
              indx := indx + 1;
            end if;
          end if;

        when (buf = CHR(9)) then null; --tabbing
        when (buf in ('-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')) then --number
          tokens(tok_indx) := mt('NUMBER', lin_no, col_no, null);
          indx := lexNumber(jsrc, tokens(tok_indx), indx)-1;
          col_no := col_no + length(tokens(tok_indx).data);
          tok_indx := tok_indx + 1;
        when buf = '"' then --number
          tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
          indx := lexString(jsrc, tokens(tok_indx), indx, '"');
          col_no := col_no + length(tokens(tok_indx).data) + 1;
          tok_indx := tok_indx + 1;
        when buf = '''' and json_strict = false then --number
          tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
          indx := lexString(jsrc, tokens(tok_indx), indx, '''');
          col_no := col_no + length(tokens(tok_indx).data) + 1;
          tok_indx := tok_indx + 1;
        when json_strict = false and REGEXP_LIKE(buf, '^[[:alpha:]]$', 'i') then
          tokens(tok_indx) := mt('STRING', lin_no, col_no, null);
          indx := lexName(jsrc, tokens(tok_indx), indx);
          col_no := col_no + length(tokens(tok_indx).data) + 1;
          tok_indx := tok_indx + 1;
        when json_strict = false and buf||next_char(indx+1, jsrc) = '/*' then --strip comments
          indx := indx + 1;
          loop
            indx := indx + 1;
            buf := next_char(indx, jsrc)||next_char(indx+1, jsrc);
            exit when buf = '*/';
            exit when buf is null;
          end loop;
          indx := indx + 1;
        when buf = ' ' then null; --space
        else
          s_error('Unexpected char: '||buf, lin_no, col_no);
      end case;

      indx := indx + 1;
    end loop;

    return tokens;
  end lexer;

  /* SCANNER END */

  /* PARSER FUNCTIONS START*/
  procedure p_error(text varchar2, tok rToken) as
  begin
    raise_application_error(-20101, 'JSON Parser exception @ line: '||tok.line||' column: '||tok.col||' - '||text);
  end;

  function parseObj(tokens lTokens, indx in out nocopy pls_integer) return json;

  function parseArr(tokens lTokens, indx in out nocopy pls_integer) return json_list as
    e_arr json_value_array := json_value_array();
    ret_list json_list := json_list();
    v_count number := 0;
    tok rToken;
  begin
    --value, value, value ]
    if(indx > tokens.count) then p_error('more elements in array was excepted', tok); end if;
    tok := tokens(indx);
    while(tok.type_name != ']') loop
      e_arr.extend;
      v_count := v_count + 1;
      case tok.type_name
        when 'TRUE' then e_arr(v_count) := json_value(true);
        when 'FALSE' then e_arr(v_count) := json_value(false);
        when 'NULL' then e_arr(v_count) := json_value;
        when 'STRING' then e_arr(v_count) := json_value(tok.data);
        when 'NUMBER' then
          declare rev varchar2(10); begin
            --stupid countries with , as decimal point
            SELECT VALUE into rev FROM NLS_SESSION_PARAMETERS WHERE PARAMETER = 'NLS_NUMERIC_CHARACTERS';
            if(rev = ',.') then
              e_arr(v_count) := json_value( to_number(replace(tok.data, '.',',')));
            else
              e_arr(v_count) := json_value( to_number(tok.data ));
            end if;
          end;
        when '[' then
          declare e_list json_list; begin
            indx := indx + 1;
            e_list := parseArr(tokens, indx);
            e_arr(v_count) := e_list.to_json_value;
          end;
        when '{' then
          indx := indx + 1;
          e_arr(v_count) := parseObj(tokens, indx).to_json_value;
        else
          p_error('Expected a value', tok);
      end case;
      indx := indx + 1;
      if(indx > tokens.count) then p_error('] not found', tok); end if;
      tok := tokens(indx);
      if(tok.type_name = ',') then --advance
        indx := indx + 1;
        if(indx > tokens.count) then p_error('more elements in array was excepted', tok); end if;
        tok := tokens(indx);
        if(tok.type_name = ']') then --premature exit
          p_error('Premature exit in array', tok);
        end if;
      elsif(tok.type_name != ']') then --error
        p_error('Expected , or ]', tok);
      end if;

    end loop;
    ret_list.list_data := e_arr;
    return ret_list;
  end parseArr;

  function parseMem(tokens lTokens, indx in out pls_integer, mem_name varchar2, mem_indx number) return json_value as
    mem json_value;
    tok rToken;
  begin
    tok := tokens(indx);
    case tok.type_name
      when 'TRUE' then mem := json_value(true);
      when 'FALSE' then mem := json_value(false);
      when 'NULL' then mem := json_value;
      when 'STRING' then mem := json_value( tok.data );
      when 'NUMBER' then
        declare rev varchar2(10); begin
          --stupid countries with , as decimal point - like my own
          SELECT VALUE into rev FROM NLS_SESSION_PARAMETERS WHERE PARAMETER = 'NLS_NUMERIC_CHARACTERS';
          if(rev = ',.') then
            mem := json_value( to_number(replace(tok.data, '.',',')));
          else
            mem := json_value( to_number(tok.data ));
          end if;
        end;
      when '[' then
        declare
          e_list json_list;
        begin
          indx := indx + 1;
          e_list := parseArr(tokens, indx);
          mem := e_list.to_json_value;
        end;
      when '{' then
        indx := indx + 1;
        mem := parseObj(tokens, indx).to_json_value;
      else
        p_error('Found '||tok.type_name, tok);
    end case;
    mem.mapname := mem_name;
    mem.mapindx := mem_indx;

    indx := indx + 1;
    return mem;
  end parseMem;

  /*procedure test_duplicate_members(arr in json_member_array, mem_name in varchar2, wheretok rToken) as
  begin
    for i in 1 .. arr.count loop
      if(arr(i).member_name = mem_name) then
        p_error('Duplicate member name', wheretok);
      end if;
    end loop;
  end test_duplicate_members;*/

  function parseObj(tokens lTokens, indx in out nocopy pls_integer) return json as
    type memmap is table of number index by varchar2(4000); -- i've read somewhere that this is not possible - but it is!
    mymap memmap;
    nullelemfound boolean := false;

    obj json;
    tok rToken;
    mem_name varchar(4000);
    arr json_value_array := json_value_array();
  begin
    --what to expect?
    while(indx <= tokens.count) loop
      tok := tokens(indx);
      --debug('E: '||tok.type_name);
      case tok.type_name
      when 'STRING' then
        --member
        mem_name := tok.data;
        begin
          if(mem_name is null) then
            if(nullelemfound) then
              p_error('Duplicate empty member: ', tok);
            else
              nullelemfound := true;
            end if;
          elsif(mymap(mem_name) is not null) then
            p_error('Duplicate member name: '||mem_name, tok);
          end if;
        exception
          when no_data_found then mymap(mem_name) := 1;
        end;

        indx := indx + 1;
        if(indx > tokens.count) then p_error('Unexpected end of input', tok); end if;
        tok := tokens(indx);
        indx := indx + 1;
        if(indx > tokens.count) then p_error('Unexpected end of input', tok); end if;
        if(tok.type_name = ':') then
          --parse
          declare
            jmb json_value;
            x number;
          begin
            x := arr.count + 1;
            jmb := parseMem(tokens, indx, mem_name, x);
            arr.extend;
            arr(x) := jmb;
          end;
        else
          p_error('Expected '':''', tok);
        end if;
        --move indx forward if ',' is found
        if(indx > tokens.count) then p_error('Unexpected end of input', tok); end if;

        tok := tokens(indx);
        if(tok.type_name = ',') then
          --debug('found ,');
          indx := indx + 1;
          tok := tokens(indx);
          if(tok.type_name = '}') then --premature exit
            p_error('Premature exit in json object', tok);
          end if;
        elsif(tok.type_name != '}') then
           p_error('A comma seperator is probably missing', tok);
        end if;
      when '}' then
        obj := json();
        obj.json_data := arr;
        return obj;
      else
        p_error('Expected string or }', tok);
      end case;
    end loop;

    p_error('} not found', tokens(indx-1));

    return obj;

  end;

  function parser(str varchar2) return json as
    tokens lTokens;
    obj json;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    jsrc := prepareVarchar2(str);
    tokens := lexer(jsrc);
    if(tokens(indx).type_name = '{') then
      indx := indx + 1;
      obj := parseObj(tokens, indx);
    else
      raise_application_error(-20101, 'JSON Parser exception - no { start found');
    end if;
    if(tokens.count != indx) then
      p_error('} should end the JSON object', tokens(indx));
    end if;

    return obj;
  end parser;

  function parse_list(str varchar2) return json_list as
    tokens lTokens;
    obj json_list;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    jsrc := prepareVarchar2(str);
    tokens := lexer(jsrc);
    if(tokens(indx).type_name = '[') then
      indx := indx + 1;
      obj := parseArr(tokens, indx);
    else
      raise_application_error(-20101, 'JSON List Parser exception - no [ start found');
    end if;
    if(tokens.count != indx) then
      p_error('] should end the JSON List object', tokens(indx));
    end if;

    return obj;
  end parse_list;

  function parse_list(str clob) return json_list as
    tokens lTokens;
    obj json_list;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    jsrc := prepareClob(str);
    tokens := lexer(jsrc);
    if(tokens(indx).type_name = '[') then
      indx := indx + 1;
      obj := parseArr(tokens, indx);
    else
      raise_application_error(-20101, 'JSON List Parser exception - no [ start found');
    end if;
    if(tokens.count != indx) then
      p_error('] should end the JSON List object', tokens(indx));
    end if;

    return obj;
  end parse_list;

  function parser(str clob) return json as
    tokens lTokens;
    obj json;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    --dbms_output.put_line('Using clob');
    jsrc := prepareClob(str);
    tokens := lexer(jsrc);
    if(tokens(indx).type_name = '{') then
      indx := indx + 1;
      obj := parseObj(tokens, indx);
    else
      raise_application_error(-20101, 'JSON Parser exception - no { start found');
    end if;
    if(tokens.count != indx) then
      p_error('} should end the JSON object', tokens(indx));
    end if;

    return obj;
  end parser;

  function parse_any(str varchar2) return json_value as
    tokens lTokens;
    obj json_list;
    ret json_value;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    jsrc := prepareVarchar2(str);
    tokens := lexer(jsrc);
    tokens(tokens.count+1).type_name := ']';
    obj := parseArr(tokens, indx);
    if(tokens.count != indx) then
      p_error('] should end the JSON List object', tokens(indx));
    end if;

    return obj.get_elem(1);
  end parse_any;

  function parse_any(str clob) return json_value as
    tokens lTokens;
    obj json_list;
    indx pls_integer := 1;
    jsrc json_src;
  begin
    jsrc := prepareClob(str);
    tokens := lexer(jsrc);
    tokens(tokens.count+1).type_name := ']';
    obj := parseArr(tokens, indx);
    if(tokens.count != indx) then
      p_error('] should end the JSON List object', tokens(indx));
    end if;

    return obj.get_elem(1);
  end parse_any;

  /* last entry is the one to keep */
  procedure remove_duplicates(obj in out nocopy json) as
    type memberlist is table of json_value index by varchar2(4000);
    members memberlist;
    nulljsonvalue json_value := null;
    validated json := json();
    indx varchar2(4000);
  begin
    for i in 1 .. obj.count loop
      if(obj.get(i).mapname is null) then
        nulljsonvalue := obj.get(i);
      else
        members(obj.get(i).mapname) := obj.get(i);
      end if;
    end loop;

    validated.check_duplicate(false);
    indx := members.first;
    loop
      exit when indx is null;
      validated.put(indx, members(indx));
      indx := members.next(indx);
    end loop;
    if(nulljsonvalue is not null) then
      validated.put('', nulljsonvalue);
    end if;

    validated.check_for_duplicate := obj.check_for_duplicate;

    obj := validated;
  end;

  function get_version return varchar2 as
  begin
    return 'PL/JSON v0.9.1';
  end get_version;

end json_parser;
/

prompt
prompt Creating package body JSON_PRINTER
prompt ==================================
prompt
create or replace package body znrl."JSON_PRINTER" as

  function escapeString(str varchar2) return varchar2 as
    sb varchar2(32767) := '';
    buf varchar2(40);
    num number;
  begin
    if(str is null) then return '""'; end if;
    for i in 1 .. length(str) loop
      buf := substr(str, i, 1);
      --backspace b = U+0008
      --formfeed  f = U+000C
      --newline   n = U+000A
      --carret    r = U+000D
      --tabulator t = U+0009
      case buf
      when chr( 8) then buf := '\b';
      when chr( 9) then buf := '\t';
      when chr(10) then buf := '\n';
      when chr(13) then buf := '\f';
      when chr(14) then buf := '\r';
      when chr(34) then buf := '\"';
      when chr(47) then buf := '\/';
      when chr(92) then buf := '\\';
      else
        if(ascii(buf) < 32) then
          buf := '\u'||replace(substr(to_char(ascii(buf), 'XXXX'),2,4), ' ', '0');
        elsif (ascii_output) then
          buf := replace(asciistr(buf), '\', '\u');
        end if;
      end case;

      sb := sb || buf;
    end loop;

    return '"'||sb||'"';
  end escapeString;

  function newline(spaces boolean) return varchar2 as
  begin
    if(spaces) then return newline_char; else return ''; end if;
  end;

/*  function get_schema return varchar2 as
  begin
    return sys_context('userenv', 'current_schema');
  end;
*/
  function tab(indent number, spaces boolean) return varchar2 as
    i varchar(200) := '';
  begin
    if(not spaces) then return ''; end if;
    for x in 1 .. indent loop i := i || indent_string; end loop;
    return i;
  end;

  function getCommaSep(spaces boolean) return varchar2 as
  begin
    if(spaces) then return ', '; else return ','; end if;
  end;

  function getMemName(mem json_value, spaces boolean) return varchar2 as
  begin
    if(spaces) then
      return escapeString(mem.mapname) || ' : ';
    else
      return escapeString(mem.mapname) || ':';
    end if;
  end;

/* Clob method start here */
  procedure add_to_clob(buf_lob in out nocopy clob, buf_str in out nocopy varchar2, str varchar2) as
  begin
    if(length(str) > 32767 - length(buf_str)) then
      dbms_lob.append(buf_lob, buf_str);
      buf_str := str;
    else
      buf_str := buf_str || str;
    end if;
  end add_to_clob;

  procedure flush_clob(buf_lob in out nocopy clob, buf_str in out nocopy varchar2) as
  begin
    dbms_lob.append(buf_lob, buf_str);
  end flush_clob;

  procedure ppObj(obj json, indent number, buf in out nocopy clob, spaces boolean, buf_str in out nocopy varchar2);

  procedure ppEA(input json_list, indent number, buf in out nocopy clob, spaces boolean, buf_str in out nocopy varchar2) as
    elem json_value;
    arr json_value_array := input.list_data;
  begin
    for y in 1 .. arr.count loop
      elem := arr(y);
      if(elem is not null) then
      case elem.get_type
        when 'number' then
          add_to_clob(buf, buf_str, to_char(elem.get_number, 'TM', 'NLS_NUMERIC_CHARACTERS=''.,'''));
        when 'string' then
          if(elem.num = 1) then
            add_to_clob(buf, buf_str, escapeString(elem.get_string));
          else
            add_to_clob(buf, buf_str, elem.get_string);
          end if;
        when 'bool' then
          if(elem.get_bool) then
            add_to_clob(buf, buf_str, 'true');
          else
            add_to_clob(buf, buf_str, 'false');
          end if;
        when 'null' then
          add_to_clob(buf, buf_str, 'null');
        when 'array' then
          add_to_clob(buf, buf_str, '[');
          ppEA(json_list(elem), indent, buf, spaces, buf_str);
          add_to_clob(buf, buf_str, ']');
        when 'object' then
          ppObj(json(elem), indent, buf, spaces, buf_str);
        else add_to_clob(buf, buf_str, elem.get_type);
      end case;
      end if;
      if(y != arr.count) then add_to_clob(buf, buf_str, getCommaSep(spaces)); end if;
    end loop;
  end ppEA;

  procedure ppMem(mem json_value, indent number, buf in out nocopy clob, spaces boolean, buf_str in out nocopy varchar2) as
  begin
    add_to_clob(buf, buf_str, tab(indent, spaces) || getMemName(mem, spaces));
    case mem.get_type
      when 'number' then
        add_to_clob(buf, buf_str, to_char(mem.get_number, 'TM', 'NLS_NUMERIC_CHARACTERS=''.,'''));
      when 'string' then
        if(mem.num = 1) then
          add_to_clob(buf, buf_str, escapeString(mem.get_string));
        else
          add_to_clob(buf, buf_str, mem.get_string);
        end if;
      when 'bool' then
        if(mem.get_bool) then
          add_to_clob(buf, buf_str, 'true');
        else
          add_to_clob(buf, buf_str, 'false');
        end if;
      when 'null' then
        add_to_clob(buf, buf_str, 'null');
      when 'array' then
        add_to_clob(buf, buf_str, '[');
        ppEA(json_list(mem), indent, buf, spaces, buf_str);
        add_to_clob(buf, buf_str, ']');
      when 'object' then
        ppObj(json(mem), indent, buf, spaces, buf_str);
      else add_to_clob(buf, buf_str, mem.get_type);
    end case;
  end ppMem;

  procedure ppObj(obj json, indent number, buf in out nocopy clob, spaces boolean, buf_str in out nocopy varchar2) as
  begin
    add_to_clob(buf, buf_str, '{' || newline(spaces));
    for m in 1 .. obj.json_data.count loop
      ppMem(obj.json_data(m), indent+1, buf, spaces, buf_str);
      if(m != obj.json_data.count) then
        add_to_clob(buf, buf_str, ',' || newline(spaces));
      else
        add_to_clob(buf, buf_str, newline(spaces));
      end if;
    end loop;
    add_to_clob(buf, buf_str, tab(indent, spaces) || '}'); -- || chr(13);
  end ppObj;

  procedure pretty_print(obj json, spaces boolean default true, buf in out nocopy clob) as
    buf_str varchar2(32767);
  begin
    ppObj(obj, 0, buf, spaces, buf_str);
    flush_clob(buf, buf_str);
  end;

  procedure pretty_print_list(obj json_list, spaces boolean default true, buf in out nocopy clob) as
    buf_str varchar2(32767);
  begin
    add_to_clob(buf, buf_str, '[');
    ppEA(obj, 0, buf, spaces, buf_str);
    add_to_clob(buf, buf_str, ']');
    flush_clob(buf, buf_str);
  end;

  procedure pretty_print_any(json_part json_value, spaces boolean default true, buf in out nocopy clob) as
    buf_str varchar2(32767) := '';
  begin
    case json_part.get_type
      when 'number' then
        add_to_clob(buf, buf_str, to_char(json_part.get_number, 'TM', 'NLS_NUMERIC_CHARACTERS=''.,'''));
      when 'string' then
        if(json_part.num = 1) then
          add_to_clob(buf, buf_str, escapeString(json_part.get_string));
        else
          add_to_clob(buf, buf_str, json_part.get_string);
        end if;
      when 'bool' then
	      if(json_part.get_bool) then
          add_to_clob(buf, buf_str, 'true');
        else
          add_to_clob(buf, buf_str, 'false');
        end if;
      when 'null' then
        add_to_clob(buf, buf_str, 'null');
      when 'array' then
        pretty_print_list(json_list(json_part), spaces, buf);
        return;
      when 'object' then
        pretty_print(json(json_part), spaces, buf);
        return;
      else add_to_clob(buf, buf_str, 'unknown type:'|| json_part.get_type);
    end case;
    flush_clob(buf, buf_str);
  end;

/* Clob method end here */

/* Varchar2 method start here */

  procedure ppObj(obj json, indent number, buf in out nocopy varchar2, spaces boolean);

  procedure ppEA(input json_list, indent number, buf in out varchar2, spaces boolean) as
    elem json_value;
    arr json_value_array := input.list_data;
  begin
    for y in 1 .. arr.count loop
      elem := arr(y);
      if(elem is not null) then
      case elem.get_type
        when 'number' then
          buf := buf || to_char(elem.get_number, 'TM', 'NLS_NUMERIC_CHARACTERS=''.,''');
        when 'string' then
          if(elem.num = 1) then
            buf := buf || escapeString(elem.get_string);
          else
            buf := buf || elem.get_string;
          end if;
        when 'bool' then
          if(elem.get_bool) then
            buf := buf || 'true';
          else
            buf := buf || 'false';
          end if;
        when 'null' then
          buf := buf || 'null';
        when 'array' then
          buf := buf || '[';
          ppEA(json_list(elem), indent, buf, spaces);
          buf := buf || ']';
        when 'object' then
          ppObj(json(elem), indent, buf, spaces);
        else buf := buf || elem.get_type; /* should never happen */
      end case;
      end if;
      if(y != arr.count) then buf := buf || getCommaSep(spaces); end if;
    end loop;
  end ppEA;

  procedure ppMem(mem json_value, indent number, buf in out nocopy varchar2, spaces boolean) as
  begin
    buf := buf || tab(indent, spaces) || getMemName(mem, spaces);
    case mem.get_type
      when 'number' then
        buf := buf || to_char(mem.get_number, 'TM', 'NLS_NUMERIC_CHARACTERS=''.,''');
      when 'string' then
        if(mem.num = 1) then
          buf := buf || escapeString(mem.get_string);
        else
          buf := buf || mem.get_string;
        end if;
      when 'bool' then
        if(mem.get_bool) then
          buf := buf || 'true';
        else
          buf := buf || 'false';
        end if;
      when 'null' then
        buf := buf || 'null';
      when 'array' then
        buf := buf || '[';
        ppEA(json_list(mem), indent, buf, spaces);
        buf := buf || ']';
      when 'object' then
        ppObj(json(mem), indent, buf, spaces);
      else buf := buf || mem.get_type; /* should never happen */
    end case;
  end ppMem;

  procedure ppObj(obj json, indent number, buf in out nocopy varchar2, spaces boolean) as
  begin
    buf := buf || '{' || newline(spaces);
    for m in 1 .. obj.json_data.count loop
      ppMem(obj.json_data(m), indent+1, buf, spaces);
      if(m != obj.json_data.count) then buf := buf || ',' || newline(spaces);
      else buf := buf || newline(spaces); end if;
    end loop;
    buf := buf || tab(indent, spaces) || '}'; -- || chr(13);
  end ppObj;

  function pretty_print(obj json, spaces boolean default true) return varchar2 as
    buf varchar2(32767) := '';
  begin
    ppObj(obj, 0, buf, spaces);
    return buf;
  end pretty_print;

  function pretty_print_list(obj json_list, spaces boolean default true) return varchar2 as
    buf varchar2(32767) := '[';
  begin
    ppEA(obj, 0, buf, spaces);
    buf := buf || ']';
    return buf;
  end;

  function pretty_print_any(json_part json_value, spaces boolean default true) return varchar2 as
    buf varchar2(32767) := '';
  begin
    case json_part.get_type
      when 'number' then
        buf := to_char(json_part.get_number(), 'TM', 'NLS_NUMERIC_CHARACTERS=''.,''');
      when 'string' then
        if(json_part.num = 1) then
          buf := buf || escapeString(json_part.get_string);
        else
          buf := buf || json_part.get_string;
        end if;
      when 'bool' then
      	if(json_part.get_bool) then buf := 'true'; else buf := 'false'; end if;
      when 'null' then
        buf := 'null';
      when 'array' then
        buf := pretty_print_list(json_list(json_part), spaces);
      when 'object' then
        buf := pretty_print(json(json_part), spaces);
      else buf := 'weird error: '|| json_part.get_type;
    end case;
    return buf;
  end;

end json_printer;
/

prompt
prompt Creating package body PK_REGISTER
prompt =================================
prompt
create or replace package body znrl.pk_register is

  -- Author  : xieyt
  -- Created : 2015-01-04 16:07:02
  -- Purpose : 车入厂登记注册信息

  --火车注册登记
  procedure p_train_register(v_insertStr in varchar2,
                             v_updateStr in varchar2,
                             v_deleteStr in varchar2,
                             v_publicStr in varchar2,
                             v_opCodeStr in varchar2,
                             v_resCode   out varchar2,
                             v_resMsg    out varchar2) is
    --business
    iv_insert_json_list json_list;
    iv_update_Json_list json_list;
    iv_delete_Json_list json_list;
    iv_public_Json      json;
    iv_tmp_jsonObj      json;
    iv_jv               json_value;
    iv_trainNo          varchar2(20);
    iv_recordDtm        varchar2(30);
    iv_remark           varchar2(500);
  
    iv_cnt number(5);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --解析公共部分信息
    begin
      iv_public_Json := json(v_publicStr);
      iv_trainNo     := json_ext.get_string(iv_public_Json, 'trainNo');
      iv_recordDtm   := json_ext.get_string(iv_public_Json, 'recordDtm');
      iv_remark      := json_ext.get_string(iv_public_Json, 'remark');
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '新增入厂记录异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    --处理只更新了公共信息的情况
    if (v_insertStr is null or length(v_insertStr) = 0) and
       (v_updateStr is null or length(v_updateStr) = 0) and
       (v_deleteStr is null or length(v_deleteStr) = 0) and
       (v_publicStr is not null and length(v_publicStr) > 0) then
    
      select count(*)
        into iv_cnt
        from RLRECORDMSTHY a
       where a.train_no = iv_trainNo;
    
      if iv_cnt > 0 then
        update RLRECORDMSTHY
           set record_dtm = to_date(iv_recordDtm, 'yyyy-mm-dd hh24:mi:ss'),
               remark     = iv_remark
         where train_no = iv_trainNo;
      
      end if;
    end if;
  
    --处理新增数据
    begin
      if v_insertStr is not null and length(v_insertStr) > 0 then
        iv_insert_json_list := json_list(v_insertStr);
        for i in 1 .. iv_insert_json_list.count loop
        
          --取json数组里的一个元素
          iv_jv          := iv_insert_json_list.get_elem(i);
          iv_tmp_jsonObj := json(iv_jv);
          --插入表
          insert into RLRECORDMSTHY
            (record_id,
             record_no, --TODO 待确认 入厂流水号哪里输入
             --oldCarId,--原车号 TODO 表无此字段。。
             train_no,
             car_id,
             ven_no,
             colry_no,
             coal_no,
             leave_dtm, --发货日期是哪个字段？
             record_dtm,
             tick_qty,
             mz_qty,
             pz_qty,
             net_qty,
             yd_qty, --盈亏  TODO 需要确认是这个字段吗？
             loss_qty,
             --xm_dtm, --TODO 是火车的翻车意思吗？
             train_batch_no,
             cz_balance_no, --TODO 表里只有称重和称轻的轨道，用哪个？
             car_typ,
             remark,
             insert_time,
             op_code)
          values
            (SEQ_RLRECORDMSTHY.Nextval, --记录ID
             SEQ_RLRECORDMSTHY.NEXTVAL, --TODO 待确认 入厂流水号
             --json_ext.get_string(json(iv_jv), 'oldCarId'),  --TODO
             iv_trainNo,
             json_ext.get_string(iv_tmp_jsonObj, 'carId'), --车号
             json_ext.get_string(iv_tmp_jsonObj, 'venNo'), --供应商
             json_ext.get_string(iv_tmp_jsonObj, 'colryNo'), --矿点
             json_ext.get_string(iv_tmp_jsonObj, 'coalNo'), --煤质类型
             to_date(json_ext.get_string(iv_tmp_jsonObj, 'leaveDtm'),
                     'yyyy-mm-dd'), --发货日期
             to_date(iv_recordDtm, 'yyyy-mm-dd hh24:mi:ss'),
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'tickQty')), --票重
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'mzQty')), --毛重
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'pzQty')), --皮重
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'netQty')), --净重
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'ydQty')), --盈亏  TODO 需要确认
             to_number(json_ext.get_string(iv_tmp_jsonObj, 'lossQty')), --运损
             --to_date(json_ext.get_string(iv_tmp_jsonObj, 'fcTime'),'yyyy-mm-dd'), --翻车时间
             json_ext.get_string(iv_tmp_jsonObj, 'batchNo'), --批次号
             json_ext.get_string(iv_tmp_jsonObj, 'balanceNo'), --轨道号
             json_ext.get_string(iv_tmp_jsonObj, 'carTyp'), --车型
             iv_remark,
             sysdate,
             v_opCodeStr);
        
        end loop;
      end if;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '新增火车入厂记录异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    --处理更新
    begin
      if v_updateStr is not null and length(v_updateStr) > 0 then
        iv_update_Json_list := json_list(v_updateStr);
        for i in 1 .. iv_update_Json_list.count loop
        
          --取json数组里的一个元素
          iv_jv          := iv_update_Json_list.get_elem(i);
          iv_tmp_jsonObj := json(iv_jv);
        
          --update
          update RLRECORDMSTHY a
             set --oldCarId = json_ext.get_string(json(iv_jv), 'oldCarId'),  --TODO
                   a.car_id = json_ext.get_string(iv_tmp_jsonObj, 'carId'), --车号
                 a.ven_no    = json_ext.get_string(iv_tmp_jsonObj, 'venNo'),
                 a.colry_no  = json_ext.get_string(iv_tmp_jsonObj, 'colryNo'), --矿点
                 a.coal_no   = json_ext.get_string(iv_tmp_jsonObj, 'coalNo'), --煤质类型
                 a.leave_dtm = to_date(json_ext.get_string(iv_tmp_jsonObj,
                                                           'leaveDtm'),
                                       'yyyy-mm-dd'), --发货日期
                 a.tick_qty  = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'tickQty')), --票重
                 a.mz_qty    = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'mzQty')), --毛重
                 a.pz_qty    = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'pzQty')), --皮重
                 a.net_qty   = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'netQty')), --净重
                 a.yd_qty    = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'ydQty')), --盈亏  TODO 需要确认
                 a.loss_qty  = to_number(json_ext.get_string(iv_tmp_jsonObj,
                                                             'lossQty')), --运损
                 --a.xm_dtm = to_date(json_ext.get_string(iv_tmp_jsonObj,'fcTime'),'yyyy-mm-dd'), --翻车时间
                 a.train_batch_no = json_ext.get_string(iv_tmp_jsonObj,
                                                        'batchNo'), --批次号
                 a.cz_balance_no  = json_ext.get_string(iv_tmp_jsonObj,
                                                        'balanceNo'), --轨道号
                 a.car_typ        = json_ext.get_string(iv_tmp_jsonObj,
                                                        'carTyp'), --车型
                 a.record_dtm     = to_date(iv_recordDtm,
                                            'yyyy-mm-dd hh24:mi:ss'),
                 a.train_no       = iv_trainNo,
                 a.remark         = iv_remark,
                 a.update_time    = sysdate,
                 a.update_code    = v_opCodeStr
           where a.record_no =
                 to_number(json_ext.get_string(iv_tmp_jsonObj, 'recordNo'));
        end loop;
      end if;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '更新火车入厂记录异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    --处理删除
    begin
      if v_deleteStr is not null and length(v_deleteStr) > 0 then
        iv_delete_Json_list := json_list(v_deleteStr);
        for i in 1 .. iv_delete_Json_list.count loop
          --取json数组里的一个元素
          iv_jv          := iv_delete_Json_list.get_elem(i);
          iv_tmp_jsonObj := json(iv_jv);
          --删除 TODO update状态（无状态字段）还是delete，待定
          delete from RLRECORDMSTHY a
           where a.record_no =
                 to_number(json_ext.get_string(iv_tmp_jsonObj, 'recordNo'));
        end loop;
      end if;
    exception
      when others then
        rollback;
        v_resCode := '1';
        v_resMsg  := '更新火车入厂记录异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    --记录批次，采样信息，制样信息
    dealBatchInfo(iv_trainNo, --车次
                  v_opCodeStr,
                  v_resCode,
                  v_resMsg);
  
    if v_resCode <> 0 then
      rollback;
      v_resCode := '1';
      v_resMsg  := '记录批次信息和采样制样信息异常:' || v_resMsg;
      return;
    end if;
  
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '提交火车入厂登记记录异常：' || substr(sqlerrm, 1, 100);
      return;
  end;

  --火车 校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
  procedure dealBatchInfo(v_trainNo in varchar2, --车次
                          v_opCode  in varchar2,
                          v_resCode out varchar2,
                          v_resMsg  out varchar2) is
  
    iv_cnt         number(5);
    iv_recCnt      number(5);
    iv_count       number(5);
    iv_dayBatchNum number(5);
    iv_randomValue number(5);
    i              number(12);
  
    iv_carIdss varchar2(3000) := '';
  
    v_batchNo      varchar2(40); --批次号
    v_sampleCode   varchar2(40); --采样编码
    v_samplingCode varchar2(40); --制样编码
  
    iv_tmpSmplCd varchar2(40);
    iv_allNetQty number(19, 6);
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --记录批次，采样信息，制样信息
    for cur in (select a.record_no,
                       a.train_no,
                       a.ven_no,
                       a.colry_no,
                       a.coal_no,
                       a.car_id
                  from RLRECORDMSTHY a
                 where a.train_no = v_trainNo) loop
      --判断是否已进行分批：取采样编码、制样编码,生成批次号
      select count(1)
        into iv_cnt
        from batch_no_info b
       where b.is_valid = '0'
         and b.train_no = v_trainNo
         and b.ven_no = cur.ven_no
         and b.coal_no = cur.coal_no
         and b.mine_no = cur.colry_no;
    
      if iv_cnt = 0 then
        --未生成批次号，进行分批处理
        --生成批次号
        select nvl(max(day_batch_num), 0)
          into iv_dayBatchNum
          from batch_no_info b
         where insert_time between trunc(sysdate) and
               trunc(sysdate) + 1 - 0.00001; --TODO 用insert time有点问题，待定
      
        iv_dayBatchNum := iv_dayBatchNum + 1;
      
        v_batchNo := 'HC-' || to_char(sysdate, 'yyyymmdd') || '-' ||
                     lpad(iv_dayBatchNum, 2, '0');
      
        --生成采样编码 
        for i in 1 .. 100 loop
          select lpad(dbms_random.value(100, 999), 3, '0')
            into iv_randomValue
            from dual;
        
          v_sampleCode := to_char(sysdate, 'yyyymmdd') || iv_randomValue;
        
          select count(1)
            into iv_count
            from batch_no_info b
           where b.sample_code = v_sampleCode;
        
          Exit When iv_count = 0;
        
        end loop;
      
        --生成制样编码 
        for i in 1 .. 100 loop
          select lpad(dbms_random.value(100, 999), 3, '0')
            into iv_randomValue
            from dual;
        
          v_samplingCode := to_char(sysdate, 'yyyymmdd') || iv_randomValue;
        
          select count(1)
            into iv_count
            from batch_no_info b
           where b.sampling_code = v_samplingCode;
        
          Exit When iv_count = 0;
        end loop;
      
        --记录批次号信息表
        insert into batch_no_info
          (batch_no,
           batch_no_type,
           train_no,
           ven_no,
           coal_no,
           mine_no,
           sample_code,
           sampling_code,
           labor_code,
           day_batch_num,
           car_ids,
           car_num,
           --all_net_qty,
           is_valid,
           insert_time,
           update_time,
           op_code,
           update_code)
        values
          (v_batchNo,
           '1',
           v_trainNo,
           cur.ven_No,
           cur.coal_No,
           cur.colry_No,
           v_sampleCode,
           v_samplingCode,
           null,
           iv_dayBatchNum,
           cur.car_Id,
           1,
           --iv_netQty,
           '0',
           sysdate,
           sysdate,
           v_opCode,
           v_opCode);
      
        --记录采样信息接口表 
        insert into take_sample_intf
          (sample_intf_rec_id,
           --record_no,
           --car_id,
           sample_code,
           train_batch_no,
           coal_type,
           sample_typ,
           data_status,
           insert_time,
           op_code,
           update_time,
           update_code)
        values
          (SEQ_TAKE_SAMPLE_INTF.NEXTVAL,
           --iv_recordNo,
           --iv_carId,
           v_sampleCode,
           v_batchNo,
           cur.coal_No,
           '0',
           '0',
           sysdate,
           v_opCode,
           sysdate,
           v_opCode);
      
        --记录制样信息接口表         
        insert into prepar_sampling_intf
          (sampling_intf_rec_id,
           sample_code,
           sampling_code,
           coal_type,
           data_status,
           insert_time,
           op_code,
           update_time,
           update_code)
        values
          (SEQ_PREPAR_SAMPLING_INTF.NEXTVAL,
           v_sampleCode,
           v_samplingCode,
           cur.coal_No,
           '0',
           sysdate,
           v_opCode,
           sysdate,
           v_opCode);
      elsif iv_cnt > 0 then
        --已生成批次号，取已存在分批信息
        select b.batch_no, b.sample_code
          into v_batchNo, v_sampleCode
          from batch_no_info b
         where b.is_valid = '0'
           and b.train_no = v_trainNo
           and b.ven_no = cur.ven_No
           and b.coal_no = cur.coal_No
           and b.mine_no = cur.colry_No;
      
      end if;
    
      --回填入厂记录批次号,采样编码
      update RLRECORDMSTHY a
         set a.train_batch_no = v_batchNo, a.sample_code = v_sampleCode
       where a.record_no = cur.record_no;
    end loop;
  
    --回写批次信息 
    --每个车次的批次信息    
    for sum_cur in (select distinct a.train_batch_no
                      from RLRECORDMSTHY a
                     where a.train_no = v_trainNo) loop
    
      --每个批次多少个车厢 
      select count(*)
        into iv_recCnt
        from RLRECORDMSTHY a
       where a.train_batch_no = sum_cur.train_batch_no;
    
      --每个批次的车厢号串起来
      iv_carIdss := '';
      for sum_cardscur in (select a.car_id
                             from RLRECORDMSTHY a
                            where a.train_batch_no = sum_cur.train_batch_no) loop
        iv_carIdss := iv_carIdss || '  ' || sum_cardscur.car_id || '  ';
      end loop;
    
      --一起记录到批次号信息表里
      update batch_no_info b
         set b.car_num = iv_recCnt, b.car_ids = iv_carIdss
       where b.batch_no = sum_cur.train_batch_no;
    
      --回填采样接口表的车数和净重
      select sample_code
        into iv_tmpSmplCd
        from batch_no_info a
       where a.batch_no = sum_cur.train_batch_no;
    
      select count(*), NVL(sum(a.net_qty), 0)
        into iv_recCnt, iv_allNetQty
        from RLRECORDMSTHY a
       where a.sample_code = iv_tmpSmplCd;
    
      update take_sample_intf a
         set a.car_num = iv_recCnt, a.all_net_qty = iv_allNetQty
       where a.sample_code = iv_tmpSmplCd;
    
    end loop;
  
    --TODO 删除批次信息表，采样中间表，制样中间表中在来煤明细表中没有的记录
    -- 待和小普商量后再确定
  
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '处理批次信息异常: ' || substr(sqlerrm, 1, 100);
      return;
  end;

  --汽车来煤登记
  procedure p_car_add_transRec(v_jsonString in varchar2,
                               v_resCode    out varchar2,
                               v_resMsg     out varchar2) is
  
    iv_doActionTag       varchar(5);
    iv_cardId            varchar(20);
    iv_cardTyp           varchar(20);
    iv_carId             varchar(20);
    iv_mineNo            varchar(20);
    iv_coalNo            varchar(20);
    iv_carrierNo         varchar(20);
    iv_venNo             varchar(20);
    iv_coalWater         varchar(20);
    iv_tickNo            varchar(20);
    iv_tickQty           varchar(20);
    iv_sampleTyp         varchar(20);
    iv_opCode            varchar(20);
    iv_remark            varchar(500);
    iv_transRecInfo_Json json;
    iv_recordNo          number(12);
    iv_recordId          number(12);
  
    iv_batchNo    varchar2(30);
    iv_sampleCode varchar2(30);
  
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --解析公共部分信息
    begin
      iv_transRecInfo_Json := json(v_jsonString);
      iv_doActionTag       := json_ext.get_string(iv_transRecInfo_Json,
                                                  'doActionTag'); --动作标识
      iv_recordNo          := json_ext.get_string(iv_transRecInfo_Json,
                                                  'recordNo');
      iv_cardId            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'cardId');
      iv_cardTyp           := json_ext.get_string(iv_transRecInfo_Json,
                                                  'cardTyp');
      iv_carId             := json_ext.get_string(iv_transRecInfo_Json,
                                                  'carId');
      iv_mineNo            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'mineNo');
      iv_coalNo            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'coalNo');
      iv_carrierNo         := json_ext.get_string(iv_transRecInfo_Json,
                                                  'orgNo');
      iv_venNo             := json_ext.get_string(iv_transRecInfo_Json,
                                                  'venNo');
      iv_coalWater         := json_ext.get_string(iv_transRecInfo_Json,
                                                  'coalWater');
      iv_tickNo            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'tickNo');
      iv_tickQty           := json_ext.get_string(iv_transRecInfo_Json,
                                                  'tickQty');
      iv_sampleTyp         := json_ext.get_string(iv_transRecInfo_Json,
                                                  'sampleTyp');
      iv_opCode            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'opCode');
      iv_remark            := json_ext.get_string(iv_transRecInfo_Json,
                                                  'remark');
      iv_recordNo          := json_ext.get_string(iv_transRecInfo_Json,
                                                  'recordNo');
    
    exception
      when others then
        v_resCode := '1';
        v_resMsg  := '解析汽车来煤信息异常：' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    if iv_doActionTag = 'ADD' then
      iv_recordId := SEQ_RLRECORDMSTHY.Nextval;
      iv_recordNo := SEQ_RLRECORDMSTHY.Nextval;
    
      begin
        insert into RLRECORDMSTQY
          (record_id,
           record_no,
           car_id,
           card_id,
           card_typ,
           colry_no,
           coal_no,
           carrier_no,
           ven_no,
           coal_water,
           tick_no,
           tick_qty,
           sample_typ,
           op_code,
           insert_time,
           record_dtm,
           remark)
        values
          (iv_recordId,
           iv_recordNo,
           iv_carId,
           iv_cardId,
           iv_cardTyp,
           iv_mineNo,
           iv_coalNo,
           iv_carrierNo,
           iv_venNo,
           iv_coalWater,
           iv_tickNo,
           iv_tickQty,
           iv_sampleTyp,
           iv_opCode,
           sysdate,
           sysdate,
           iv_remark);
      exception
        when others then
          rollback;
          v_resCode := '1';
          v_resMsg  := '新增汽车来煤信息异常：' || substr(sqlerrm, 1, 100);
          return;
      end;
    elsif iv_doActionTag = 'MOD' then
      update RLRECORDMSTQY
         set colry_no = iv_mineNo,
             coal_no = iv_coalNo,
             ven_no = iv_venNo,
             coal_water = iv_coalWater,
             tick_no = iv_tickNo,
             tick_qty = iv_tickQty,
             sample_typ = iv_sampleTyp,
             update_code = iv_opCode,
             update_time = sysdate,
             remark = iv_remark
       where record_no = iv_recordNo;
       
    elsif iv_doActionTag = 'DEL' then
    
      --记录批次，采样信息，制样信息
      select a.car_id, a.car_batch_no, a.sample_code
        into iv_carId, iv_batchNo, iv_sampleCode
        from rlrecordmstqy a
       where a.record_no = iv_recordNo;
    
      --重新刷新批次信息和
      update batch_no_info b
         set b.car_num = b.car_num - 1,
             b.car_ids = REPLACE(b.car_ids, iv_carId, '')
       where b.is_valid = '0'
         and b.batch_no = iv_batchNo;
    
      update take_sample_intf a
         set a.car_num = a.car_num - 1
       where a.sample_code = iv_sampleCode;
       
      delete from rlrecordmstqy a where a.record_no = iv_recordNo;
    end if;
  
    if iv_doActionTag in ('ADD', 'MOD') then
      --记录批次，采样信息，制样信息
      dealCarBatchInfo(iv_recordNo,
                       iv_opCode,
                       v_resCode,
                       v_resMsg);
    
      if v_resCode <> 0 then
        rollback;
        v_resCode := '1';
        v_resMsg  := '记录批次信息和采样制样信息异常:' || v_resMsg;
        return;
      end if;
    end if;
    
    --提交
    commit;
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '新增汽车来煤信息发生异常：' || substr(sqlerrm, 1, 100);
      return;
  end;

  --汽车分批次处理 校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
  procedure dealCarBatchInfo(v_recordNo    in varchar2, --车次
                             v_opCode      in varchar2,
                             v_resCode     out varchar2,
                             v_resMsg      out varchar2) is
  
    iv_cnt         number(5);
    iv_count       number(5);
    iv_dayBatchNum number(5);
    iv_randomValue number(5);
    i              number(12);
  
    v_batchNo      varchar2(40); --批次号
    v_sampleCode   varchar2(40); --采样编码
    v_samplingCode varchar2(40); --制样编码
  
    iv_recordNo   number(10);
    iv_insertTime date;
    iv_colryNo    number(10);
    iv_carrierNo  number(10);
    iv_venNo      varchar2(20);
    iv_coalNo     varchar2(20);
    iv_carId      varchar2(1000);
    iv_sampleTyp  varchar2(5);
  
    iv_carIdss varchar2(1000);
    
    iv_oldBatchNo  varchar2(30);
    iv_oldSampleCode  varchar2(30);
  begin
    v_resCode := '0';
    v_resMsg  := 'ok';
  
    --记录批次，采样信息，制样信息
    select a.record_no,
           a.insert_time,
           a.colry_no,
           a.carrier_no,
           a.ven_no,
           a.coal_no,
           a.car_id,
           a.sample_typ,
           a.car_batch_no,
           a.sample_code
      into iv_recordNo,
           iv_insertTime,
           iv_colryNo,
           iv_carrierNo,
           iv_venNo,
           iv_coalNo,
           iv_carId,
           iv_sampleTyp,
           iv_oldBatchNo,
           iv_oldSampleCode
      from rlrecordmstqy a
     where a.record_no = v_recordNo;
  
    --判断是否已进行分批：小普说：对于汽车：同一天、同一矿点、同一运输单位 为一批次
    --取采样编码、制样编码,生成批次号
    select count(1)
      into iv_cnt
      from batch_no_info b
     where b.is_valid = '0'
       and trunc(b.insert_time) = trunc(iv_insertTime)
       and b.carrier_no = iv_carrierNo
       and b.mine_no = iv_colryNo;
  
    if iv_cnt = 0 then
      --未生成批次号，进行分批处理
      --生成批次号
      select nvl(max(day_batch_num), 0)
        into iv_dayBatchNum
        from batch_no_info b
       where insert_time between trunc(sysdate) and
             trunc(sysdate) + 1 - 0.00001; --TODO 用insert time有点问题，待定
    
      iv_dayBatchNum := iv_dayBatchNum + 1;
    
      v_batchNo := 'QC-' || to_char(sysdate, 'yyyymmdd') || '-' ||
                   lpad(iv_dayBatchNum, 2, '0');
    
      --生成采样编码 
      for i in 1 .. 100 loop
        select lpad(dbms_random.value(100, 999), 3, '0')
          into iv_randomValue
          from dual;
      
        v_sampleCode := to_char(sysdate, 'yyyymmdd') || iv_randomValue;
      
        select count(1)
          into iv_count
          from batch_no_info b
         where b.sample_code = v_sampleCode;
      
        Exit When iv_count = 0;
      
      end loop;
    
      --生成制样编码 
      for i in 1 .. 100 loop
        select lpad(dbms_random.value(100, 999), 3, '0')
          into iv_randomValue
          from dual;
      
        v_samplingCode := to_char(sysdate, 'yyyymmdd') || iv_randomValue;
      
        select count(1)
          into iv_count
          from batch_no_info b
         where b.sampling_code = v_samplingCode;
      
        Exit When iv_count = 0;
      end loop;
    
      --记录批次号信息表
      insert into batch_no_info
        (batch_no,
         batch_no_type,
         ven_no,
         coal_no,
         mine_no,
         sample_code,
         sampling_code,
         carrier_no,
         labor_code,
         day_batch_num,
         car_ids,
         car_num,
         is_valid,
         insert_time,
         update_time,
         op_code,
         update_code)
      values
        (v_batchNo,
         '0',
         iv_venNo,
         iv_coalNo,
         iv_colryNo,
         v_sampleCode,
         v_samplingCode,
         iv_carrierNo,
         null,
         iv_dayBatchNum,
         iv_carId,
         1,
         '0',
         sysdate,
         sysdate,
         v_opCode,
         v_opCode);
    
      --记录采样信息接口表 
      insert into take_sample_intf
        (sample_intf_rec_id,
         --record_no,
         --car_id,
         sample_code,
         train_batch_no,
         coal_type,
         car_num,
         sample_typ,
         data_status,
         insert_time,
         op_code,
         update_time,
         update_code)
      values
        (SEQ_TAKE_SAMPLE_INTF.NEXTVAL,
         --iv_recordNo,
         --iv_carId,
         v_sampleCode,
         v_batchNo,
         iv_coalNo,
         1, --新增加批次第一条记录填1
         iv_sampleTyp,
         '0',
         sysdate,
         v_opCode,
         sysdate,
         v_opCode);
    
      --记录制样信息接口表         
      insert into prepar_sampling_intf
        (sampling_intf_rec_id,
         sample_code,
         sampling_code,
         coal_type,
         data_status,
         insert_time,
         op_code,
         update_time,
         update_code)
      values
        (SEQ_PREPAR_SAMPLING_INTF.NEXTVAL,
         v_sampleCode,
         v_samplingCode,
         iv_coalNo,
         '0',
         sysdate,
         v_opCode,
         sysdate,
         v_opCode);
         
      --回填入厂记录批次号,采样编码
      update rlrecordmstqy a
         set a.car_batch_no = v_batchNo, 
             a.sample_code = v_sampleCode
       where a.record_no = iv_recordNo;
      
      
      --如果原来的批次号不为空，说明该recordNo是老的，本次操作是mod，需要刷新老的批次号
      if iv_oldBatchNo is not null then
        --重新刷新批次信息和
        iv_count := 0;
        for sum_cardscur in (select a.car_id, sample_code
                             from rlrecordmstqy a
                            where a.car_batch_no = iv_oldBatchNo) loop
          iv_carIdss := iv_carIdss || '  ' || sum_cardscur.car_id || '  ';
          iv_count   := iv_count + 1;
        end loop;
      
        update batch_no_info b
           set b.car_num = iv_count, 
               b.car_ids = iv_carIdss,
               b.update_time = sysdate,
               b.update_code = v_opCode
         where b.is_valid = '0'
           and b.batch_no = v_batchNo;
      
        update take_sample_intf a
           set a.car_num = iv_count
         where a.sample_code = iv_oldSampleCode;
      
        update prepar_sampling_intf a
           set a.coal_type = iv_coalNo
         where a.sample_code = iv_oldSampleCode;
      end if;
    
    elsif iv_cnt > 0 then
    
      --已生成批次号，取已存在分批信息
      select b.batch_no, b.sample_code
        into v_batchNo, v_sampleCode
        from batch_no_info b
       where b.is_valid = '0'
         and trunc(b.insert_time) = trunc(iv_insertTime)
         and b.carrier_no = iv_carrierNo
         and b.mine_no = iv_colryNo;
         
      --回填入厂记录批次号,采样编码
      update rlrecordmstqy a
         set a.car_batch_no = v_batchNo, 
             a.sample_code = v_sampleCode,
             a.update_time = sysdate,
             a.update_code = v_opCode
       where a.record_no = iv_recordNo;
      
      --重新刷新批次信息和
      iv_count := 0;
      for sum_cardscur in (select a.car_id
                             from rlrecordmstqy a
                            where a.car_batch_no = v_batchNo) loop
        iv_carIdss := iv_carIdss || '  ' || sum_cardscur.car_id || '  ';
        iv_count   := iv_count + 1;
      end loop;
    
      update batch_no_info b
         set b.car_num = iv_count, 
             b.car_ids = iv_carIdss,
             b.update_time = sysdate,
             b.update_code = v_opCode
       where b.is_valid = '0'
         and b.batch_no = v_batchNo;
    
      update take_sample_intf a
         set a.car_num = iv_count
       where a.sample_code = v_sampleCode;
    
      update prepar_sampling_intf a
         set a.coal_type = iv_coalNo
       where a.sample_code = v_sampleCode;
    
    end if;

  
  exception
    when others then
      rollback;
      v_resCode := '1';
      v_resMsg  := '处理批次信息异常: ' || substr(sqlerrm, 1, 100);
      return;
  end;

end pk_register;
/

prompt
prompt Creating package body PK_SAMPLE_STORE
prompt =====================================
prompt
create or replace package body znrl.pk_sample_store is

  procedure submit_approve(v_jsonStr     in varchar2,
                           v_dealType    in varchar2,
                           v_destination in varchar2,
                           v_remark      in varchar2,
                           v_opCode      in varchar2,
                           v_resCode     out varchar2,
                           v_resMsg      out varchar2) is
  
    iv_cabinetRecId varchar2(16);
    iv_jsonstr_list json_list;
    iv_jv           json_value;
    iv_cnt          number(6)  := 0;
    iv_keyId        varchar2(16);
    iv_description  varchar2(500);
    iv_dest         varchar2(30);
  begin
    v_rescode := '0';
    v_resmsg  := 'ok';
  
    begin
      iv_jsonstr_list := json_list(v_jsonStr);
      iv_keyId        := seq_common_id.nextval;
    
      for i in 1 .. iv_jsonstr_list.count loop
      
        iv_jv := iv_jsonstr_list.get_elem(i);
      
        iv_cabinetRecId := iv_jv.get_string();
      
        insert into appr_req_data_buffer
          (appr_req_id,
           param1,
           param2, --dealtype
           param3, --destination
           param4, --cabinetRecId
           remark,
           staff_id,
           appr_res,
           create_dt)
        values
          (SEQ_appr_req_buffer.Nextval,
           iv_keyId,
           v_dealType,
           v_destination,
           iv_cabinetRecId,
           v_remark,
           v_opCode,
           'W',
           SYSDATE);
        iv_cnt := iv_cnt + 1;
      
      end loop;
    
    exception
      when others then
        rollback;
        v_rescode := '1';
        v_resMsg  := '审批数据插入缓存表异常:' || substr(sqlerrm, 1, 100);
        return;
    end;
  
    if v_destination = '1' then
      iv_dest := '全自动取样工作站';
    elsif v_destination = '2' then
      iv_dest := '半自动存取样工作站';
    end if;
  
    iv_description := '提交审批数据：' || to_char(iv_cnt) || '条；  目的地：' ||
                      iv_dest || '； 备注信息：' || v_remark;
  
    begin
      approve_core.addApproveReq(iv_keyId, --eventkeyId
                                 v_dealType, --eventType
                                 iv_description,
                                 v_opCode,
                                 v_rescode,
                                 v_resmsg);
    
      if v_resCode <> '0' then
        rollback;
        return;
      end if;
    
    exception
      when others then
        rollback;
        v_rescode := '1';
        v_resMsg  := '审批请求异常:' || substr(sqlerrm, 1, 100);
        return;
    end;
  
  exception
    when others then
      rollback;
      v_rescode := '1';
      v_resmsg  := '审批发生异常:' || substr(sqlerrm, 1, 100);
      return;
  end;



procedure cabinet_op_record(v_eventKeyId in varchar2,
                            v_rescode    out varchar2,
                            v_resmsg     out varchar2) is

begin
  v_rescode := '0';
  v_resmsg  := 'ok';

  insert into cabinet_op_rec
    (op_rec_id,
     cabinet_rec_id, --
     op_type,
     op_dest, --
     op_audit, --
     operator, --
     data_status,
     insert_time,
     op_code)
    select seq_cabinet_op_rec.nextval,
           to_number(a.param4), --cabinetRecId
           a.param2, --dealtype
           a.param3, --destination
           '9',
           a.staff_id,
           '0',
           sysdate,
           a.staff_id
      from appr_req_data_buffer a
     where a.param1 = v_eventKeyId;

  update appr_req_data_buffer a
     set a.appr_res = 'Y' --标识审批过了
   where a.param1 = v_eventKeyId;

  commit;
exception
  when others then
    rollback;
    v_rescode := '1';
    v_resMsg  := '回写审批结果时插入样品操作记录表异常:' ||
                 substr(sqlerrm, 1, 100);
    return;
end;
  
end pk_sample_store;
/

prompt
prompt Creating package body SYN_DATA_TASK
prompt ===================================
prompt
create or replace package body znrl.syn_data_task is

 --取称重数据生成批次处理
  procedure syn_weight_info Is
   
    iv_trainNo  varchar2(50);--车次
    iv_carId    varchar2(50);--车号
    iv_venName  varchar2(50);--供应商
    iv_coalName varchar2(50);--煤种
    iv_netQty   number(19,6);--净重 
    v_venNo     varchar2(50);--供应商编码
    v_coalNo    varchar2(50);--煤种编码
    v_batchNo   varchar2(50);--批次号
    v_sampleCode   varchar2(50);--采样编码
    v_samplingCode varchar2(50);--制样编码
    v_resCode    varchar2(2);
    v_resMsg     varchar2(500);
    iv_ccZcs     number(10);
    iv_tickQty     number(19,6);--票重 

  begin

    v_resCode := '0';
    v_resMsg  := 'ok';
    
    --初始化同步轨道衡数据
    initMiddleTable(v_resCode,v_resMsg);
                     
    if v_resCode <> '0' then
      --如果没用新数据则返回
      if v_resCode = PROCESS_NORMAL then
          return;
      else
          Rollback;
          return;--进行报警
      end if;
    end if; 
     
    --处理轨道衡数据，记录来煤明细表、采样接口表、制样接口表
    For cur In (select *
                  from weight_info_intf_middle wi
                 where wi.deal_flag = '0') Loop
      Begin

        iv_trainNo     := cur.car_batchno;--车次
        iv_carId     := cur.car_id;--车号
        iv_venName  := cur.fh_unit;--供应商
        iv_coalName := cur.hm_name;--煤种
        iv_netQty := cur.net_qty;--净重
        iv_tickQty := cur.tick_qty;--票重
        
         --校验数据参数、获取生成批次来煤批次，并返回供应商、采样编码等信息         
        checkParams(iv_trainNo,--车次
                    iv_carId,--车号
                    iv_venName,--供应商
                    iv_coalName,--煤种
                    iv_netQty,--净重
                    iv_tickQty,--票重
                    v_venNo,--供应商编码
                    v_coalNo,--煤种编码
                    v_batchNo,--批次号
                    v_sampleCode,--采样编码
                    v_samplingCode,--制样编码
                    v_resCode,
                    v_resMsg);     
        
        if v_resCode <> '0' then  
              Rollback;
             --更新中间表处理标志   
              update weight_info_intf_middle
                 set deal_flag = '9', 
                     deal_disc = '处理失败:'||v_resMsg 
              where record_id = cur.record_id;
              --更新接口表处理标志
              update weight_info_intf
                 set deal_flag = '9', 
                     deal_disc = '处理失败:'||v_resMsg 
              where record_id = cur.record_id;
              Commit;
         else
         
          --插入翻车单数据表
           recordSampleInfo(cur.record_id,  --中间表记录id
                             v_batchNo,     --批次号
                             v_venNo,       --供应商编码 
                             v_coalNo,      --煤种编码
                             v_sampleCode,  --采样编码
                             v_samplingCode,--制样编码
                             v_resCode,
                             v_resMsg);
                                              
            if v_resCode <> '0' then
                Rollback;
                 --更新中间表处理标志   
                update weight_info_intf_middle
                   set deal_flag = '9', 
                       deal_disc = '处理失败:'||v_resMsg 
                where record_id = cur.record_id;
                --更新接口表处理标志
                update weight_info_intf
                   set deal_flag = '9', 
                       deal_disc = '处理失败:'||v_resMsg 
                where record_id = cur.record_id;                
                Commit;     
             else
                 --更新中间表处理标志   
                update weight_info_intf_middle
                   set deal_flag = '1', 
                       deal_disc = '处理成功' 
                where record_id = cur.record_id;
                --更新接口表处理标志
                update weight_info_intf
                   set deal_flag = '1', 
                       deal_disc = '处理成功'
                where record_id = cur.record_id;
               Commit;
           end if; 
              
        end if;

      Exception
        When Others Then
          Rollback;
         v_resMsg := substr(sqlerrm, 1, 200);
      End;

    End Loop;
    
    --写入最终的采样信息表、翻车单表车辆数、预计总重和车辆数字段  
    For cur In (select *
                  from batch_no_info b
                 where b.is_valid = '0') Loop
      Begin 
      
         --更新采样信息表车辆数、预计总重字段  
          update take_sample_intf t
             set t.car_num = cur.car_num, 
                 t.all_net_qty = cur.all_net_qty,
                 t.all_tick_qty = cur.all_tick_qty,
                 t.update_time = sysdate,
                 t.update_code = 'sys'
           where t.sample_code = cur.sample_code;
          Commit; 
      
          --更新翻车单数据表车辆数、总重字段           
         select count(1)
          into iv_ccZcs
          from rlfanchedan t
         where t.cc_id = cur.train_no;        
          
          update rlfanchedan t
             set t.cc_zcs = iv_ccZcs, 
                 t.ticket_qty = cur.all_tick_qty,
                 t.pc_zcs = cur.car_num
           where t.sample_id = cur.sample_code;
          Commit;

      Exception
        When Others Then
          Rollback;
         v_resMsg := substr(sqlerrm, 1, 200);
      End;
    End Loop;
    
    --更新批次号信息表，置有效标志位生效 
     update batch_no_info b
        set b.is_valid = '1',
            b.update_time = sysdate,
            b.update_code = 'sys'
      where b.is_valid  = '0';
     Commit;

  Exception
    When Others Then
      Rollback;
     v_resMsg := substr(sqlerrm, 1, 200);
  end;
 
 --校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
 procedure checkParams(v_trainNo       in varchar2,--车次
                        v_carId        in varchar2,--车号
                        v_venName      in varchar2,--供应商
                        v_coalName     in varchar2,--煤种
                        iv_netQty      in number,--净重
                        iv_tickQty     in number,--票重
                        v_venNo        out varchar2,--供应商编码
                        v_coalNo       out varchar2,--煤种编码
                        v_batchNo      out varchar2,--批次号
                        v_sampleCode   out varchar2,--采样编码
                        v_samplingCode out varchar2,--制样编码
                        v_resCode     out varchar2,
                        v_resMsg      out varchar2) is
                        
  iv_cnt          number(5);
  iv_count        number(5);
  iv_dayBatchNum  number(5);
  iv_randomValue  number(5);
  i              number;

  begin
    v_resCode := '0';
    v_resMsg  := 'ok';

    if nvl(length(v_trainNo), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '车次为空';
      return;
    end if;

    if nvl(length(v_carId), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '车号为空';
      return;
    end if;

    if nvl(length(v_venName), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '供应商为空';
      return;
    end if;


    if nvl(length(v_coalName), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '煤种为空';
      return;
    end if;
    
    --取供应商编码
    select count(1)
      into iv_cnt
      from vendor_info v
     where v.ven_name = v_venName;
    
    if iv_cnt = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '未查询到供应商编码';
      return;
    elsif iv_cnt > 1 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '供应商编码不能唯一定位';
      return;
    end if;
    
    select ven_no
      into v_venNo
      from vendor_info v
     where v.ven_name = v_venName;
    
    --取煤种编码
    select count(1)
      into iv_cnt
      from coal_type c
     where c.coal_name = v_coalName;
    
    if iv_cnt = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '未查询到煤种编码';
      return;
    elsif iv_cnt > 1 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '煤种编码不能唯一定位';
      return;
    end if;
    
    select coal_no
      into v_coalNo
      from coal_type v
     where v.coal_name = v_coalName;
     
    --判断是否已进行分批：取采样编码、制样编码,生成批次号
    select count(1)
      into iv_cnt
      from batch_no_info b
     where b.is_valid = '0'
       and b.train_no = v_trainNo
       and b.ven_no = v_venNo
       and b.coal_no = v_coalNo;
        
    if iv_cnt = 0 then
      --未生成批次号，进行分批处理
      --生成批次号
       select nvl(max(day_batch_num),0)
        into iv_dayBatchNum
        from batch_no_info b
       where to_char(insert_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd');  
       iv_dayBatchNum := iv_dayBatchNum+1;
       
       v_batchNo := 'HC-'||to_char(sysdate,'yyyymmdd')||'-'||lpad(iv_dayBatchNum,2,'0');
      
      --生成采样编码 
      for i in 1..100 loop 
        select lpad(dbms_random.value(100, 999), 3, '0')  
         into iv_randomValue
        from dual;
                 
        v_sampleCode := to_char(sysdate,'yyyymmdd')||iv_randomValue; 
                 
        select count(1)
        into iv_count
        from batch_no_info b
       where b.sample_code = v_sampleCode; 
         
       Exit When iv_count = 0;   
        
      end loop;
      
       --生成制样编码 
      for i in 1..100 loop 
        select lpad(dbms_random.value(100, 999), 3, '0') 
         into iv_randomValue
        from dual;
            
        v_samplingCode := to_char(sysdate,'yyyymmdd')||iv_randomValue; 
                 
        select count(1)
        into iv_count
        from batch_no_info b
       where b.sampling_code = v_samplingCode; 
       
        Exit When iv_count = 0;
      end loop;
        
      --记录批次号信息表
      insert into batch_no_info
        (batch_no,
         batch_no_type,
         train_no,
         ven_no,
         coal_no,
         sample_code,
         sampling_code,
         labor_code,
         day_batch_num,
         car_ids,
         car_num,
         all_net_qty,
         all_tick_qty,
         is_valid,
         insert_time,
         update_time,
         op_code,
         update_code)
      values
        (v_batchNo,
         '1',
         v_trainNo,
         v_venNo,
         v_coalNo,
         v_sampleCode,
         v_samplingCode,
         null,
         iv_dayBatchNum,
         v_carId,
         1,
         iv_netQty,
         iv_tickQty,--票重
         '0',
         sysdate,
         sysdate,
         'sys',
         'sys');
      
    elsif iv_cnt = 1 then
      --已生成批次号，取已存在分批信息
      select batch_no, sample_code, sampling_code
        into v_batchNo, v_sampleCode, v_samplingCode
        from batch_no_info b
       where b.is_valid = '0'
         and b.train_no = v_trainNo
         and b.ven_no = v_venNo
         and b.coal_no = v_coalNo;
       
       update batch_no_info b
          set b.car_num     = b.car_num + 1,
              b.all_net_qty = b.all_net_qty + iv_netQty,
              b.all_tick_qty = b.all_tick_qty+ iv_tickQty,
              b.car_ids    = b.car_ids || ',' || v_carId
        where b.sample_code = v_sampleCode;
     else    
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '批次号重复，请检查批次号';
      return;    
    end if;
 
  exception
    when others then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '校验供应商、煤种参数,获取生成批次异常: ' || substr(sqlerrm, 1, 100);
      return;
  end;
  
  
   --记录来煤明细表，采样信息中间表，制样信息中间表
  procedure recordSampleInfo(v_recordId      in varchar2,--中间表记录id
                             v_batchNo       in varchar2,--批次号
                             v_venNo         in varchar2,--供应商编码 
                             v_coalNo        in varchar2,--煤种编码
                             v_sampleCode    in varchar2,--采样编码
                             v_samplingCode  in varchar2,--制样编码
                             v_resCode       out varchar2,
                             v_resMsg        out varchar2) is
                             
    iv_cnt      number(5);
    iv_carBatchno varchar2(50);
    iv_carId      varchar2(50);
    iv_carTyp     varchar2(50);
    iv_mzQty      number(19,6);
    iv_tickQty    number(19,6);
    iv_pzQty      number(19,6);
    iv_netQty     number(19,6);
    iv_lossQty    number(19,6);
    iv_ydQty      number(19,6);
    iv_kdQty      number(19,6);
    iv_koudQty    number(19,6);
    iv_czDtm       varchar2(50);
    iv_jqDtm      varchar2(50);
    iv_czBalanceNo varchar2(50);
    iv_jqBalanceNo varchar2(50);
    iv_ghNo       varchar2(50);
    iv_speed      varchar2(50);
    iv_hmName     varchar2(50);
    iv_fzPlace    varchar2(50);
    iv_dzPlace    varchar2(50);
    iv_fhUnit     varchar2(50);
    iv_dhUnit     varchar2(50);
    iv_recordId   number(10);
    iv_recordNo   number(10);
    iv_xhNum      number(5);
    iv_sampleIntfIid number(10);
    iv_samplingIntfIid number(10);
    iv_recordGid   number(20);
    
    
  begin
    v_resCode     := '0';
    v_resMsg      := 'ok';
    
    select count(1)
      into iv_cnt
      from weight_info_intf_middle wi
     where wi.record_id = v_recordId;
        
     if iv_cnt = 0 then
        v_resCode := PROCESS_ERROR;
        v_resMsg  := '没有找到称重数据';
        return;
      elsif iv_cnt > 1 then
        v_resCode := PROCESS_ERROR;
        v_resMsg  := '称重数据不能唯一定位';
        return;
    end if;
    
    select car_batchno,car_id,car_typ,mz_qty,tick_qty,pz_qty,net_qty,loss_qty,
           yd_qty,kd_qty,koud_qty,cz_dtm,jq_dtm,cz_balance_no,jq_balance_no,
           gh_no,speed,hm_name,fz_place,dz_place,fh_unit,dh_unit,xh_num
      into iv_carBatchno,iv_carId,iv_carTyp,iv_mzQty,iv_tickQty,iv_pzQty,iv_netQty,iv_lossQty,
           iv_ydQty,iv_kdQty,iv_koudQty,iv_czDtm,iv_jqDtm,iv_czBalanceNo,iv_jqBalanceNo,iv_ghNo,
           iv_speed,iv_hmName,iv_fzPlace,iv_dzPlace,iv_fhUnit,iv_dhUnit,iv_xhNum
      from weight_info_intf_middle wi
     where wi.record_id = v_recordId;
     
     
     select SEQ_RLRECORDMSTHY.NEXTVAL 
      into iv_recordId 
     from dual;
       
     select SEQ_RLRECORDMSTHY.NEXTVAL 
      into iv_recordNo 
     from dual;

    --来煤明细记录
    insert into rlrecordmsthy
       (record_id,      
        record_no,        
        train_no, --车次       
        car_id,   --车号      
        car_typ,  --车型      
        xh_num,  --车厢序号       
        ven_no,  --供应商        
        --carrier_no,     
        coal_no,  --煤种       
        --colry_no, --煤种      
        --coal_size,      
        --coal_water,    
        tick_qty,--票重       
        mz_qty,  --毛重       
        pz_qty,  --皮重       
        net_qty, --净重      
        loss_qty,--运损       
        kd_qty,  --扣吨
        kud_qty, --亏吨       
        yd_qty,  --盈吨        
        cz_dtm,  --称重时间       
        jq_dtm,  --检轻时间       
        --xm_dtm,  --卸煤时间       
        car_spd, --车速       
        --cz_balance_no,  
        --jq_balance_no,  
        --gh_no,          
        train_batch_no,--批次号
        sample_code,   --采样编码
        sample_typ,    --采样方式
        empty_flg,     --是否空车
        --leave_flg,     --是否出厂 
        --door_no,       --门号 
        record_dtm,     
        leave_dtm,      
        --fcj_code,       
        cfm_flg,        
        start_place, --发站   
        final_place, --到站   
        insert_time,    
        update_time,    
        op_code,        
        update_code,    
        remark)
    values
      (iv_recordId,
       iv_recordNo,
       iv_carBatchno,
       iv_carId,
       iv_carTyp,
       iv_xhNum,
       v_venNo,
       v_coalNo,
       iv_tickQty,
       iv_mzQty,
       iv_pzQty,
       iv_netQty,
       iv_lossQty,
       iv_koudQty,
       iv_kdQty,
       iv_ydQty,
       to_date(iv_czDtm, 'yyyy-mm-ddhh24:mi:ss'),
       to_date(iv_jqDtm, 'yyyy-mm-ddhh24:mi:ss'),
       to_number(iv_speed),
       v_batchNo,
       v_sampleCode,
       '0',
       '1',
       sysdate,
       sysdate,
       '1',
       iv_fzPlace,
       iv_dzPlace,
       sysdate,
       sysdate,
       'sys',
       'sys',
       '系统自动后台生成批次');
       
       --记录采样信息接口表
       select SEQ_TAKE_SAMPLE_INTF.NEXTVAL 
         into iv_sampleIntfIid 
       from dual;
        
       insert into take_sample_intf
         (sample_intf_rec_id,
          record_no,
          car_id,
          sample_code,
          train_batch_no,
          coal_type,
          sample_typ,
          data_status,
          insert_time,
          op_code,
          update_time,
          update_code)
       values
         (iv_sampleIntfIid,
          iv_recordNo,
          iv_carId,
          v_sampleCode,
          v_batchNo,
          v_coalNo,
          '0',
          '0',
          sysdate,
          'sys',
          sysdate,
          'sys');
          
       --记录制样信息接口表
       select SEQ_PREPAR_SAMPLING_INTF.NEXTVAL 
         into iv_samplingIntfIid 
       from dual; 
         
        insert into prepar_sampling_intf
          (sampling_intf_rec_id,
           sample_code,
           sampling_code,
           coal_type,
           data_status,
           insert_time,
           op_code,
           update_time,
           update_code)
        values
          (iv_samplingIntfIid,
           v_sampleCode,
           v_samplingCode,
           v_coalNo,
           '0',
           sysdate,
           'sys',
           sysdate,
           'sys');
     
         
        select SEQ_RLFANCHEDAN_GID.NEXTVAL 
          into iv_recordGid 
         from dual;           
      --记录翻车单数据
      insert into rlfanchedan
        (gid, --记录主键，唯一标识
         --dp_id, --大票编号
         cc_id, --车次
         xh_num, --序号
         ch_id, --车号
         --kd_nam, --矿点名称
         --ysdw_nam, --运输单位名称
         gys_nam, --供应商名称
         mz_typ, --煤质类型
         fz_nam, --发站名称
         --kf_dtm, --矿发时间
         rc_dtm, --入厂时间
         pz_qty, --票重
         --ticket_qty, --批次大票总重量
         ys_qty, --运损
         --ggqch_id, --更改前车号
         bz_not, --备注
         dq_flg, --是否已读取
         pc_id, --批次号
         sample_id, --采样编码
         sample_type, --采样类型。大水分旁路；机器采样；人工采样
         qrr_nam, --确认人
         qr_dtm, --确认时间
         --kd_code, --矿点编码
         --ysdw_code, --运输单位编码
         gys_code, --供应商编码
         --fz_code, --发站编码
         cjsj --记录创建时间
         --pc_zcs, --批次总车数
         --cc_zcs
         ) --车次总车数
      values
        (iv_recordGid,
         iv_carBatchno,
         iv_xhNum,
         iv_carId,
         iv_fhUnit,
         v_coalNo,
         iv_fzPlace,
         sysdate,
         iv_tickQty,
         iv_lossQty,
         '系统自动后台生成批次',
         '0',
         v_batchNo,
         v_sampleCode,
         '机器采样',
         'sys',
         sysdate,
         v_venNo,
         sysdate);
                     
  exception
    when others then
      v_resCode := '1';
      v_resMsg  := '记录来煤明细/翻车单数据异常: ' ||
                   substr(sqlerrm, 1, 100);
  end;
  

  PROCEDURE initMiddleTable(v_resCode    out varchar2,
                            v_resMsg     out varchar2) IS
    VC_SQL       VARCHAR2(200);
    IV_CNT      NUMBER(5);
  BEGIN
    
    v_resCode := '0'; 
    v_resMsg  := 'ok';

    --查看轨道衡数据是否已经生成。
    SELECT COUNT(*)
      INTO IV_CNT
      FROM WEIGHT_INFO_INTF W
     WHERE W.DEAL_FLAG = '0';
    IF IV_CNT = 0 THEN
      v_resCode := PROCESS_NORMAL;
      v_resMsg  := '轨道衡数据未生成';
      RETURN;
    END IF;

    /*转换成源表*/
    /*VC_SQL := 'TRUNCATE TABLE WEIGHT_INFO_INTF_MIDDLE';
    EXECUTE IMMEDIATE VC_SQL;*/

    SELECT COUNT(*)
      INTO IV_CNT
      FROM ALL_CONSTRAINTS A
     WHERE A.OWNER = 'ZNRL'
       AND A.TABLE_NAME = 'WEIGHT_INFO_INTF_MIDDLE'
       AND A.CONSTRAINT_TYPE = 'P';
    IF IV_CNT > 0 THEN
      VC_SQL := 'ALTER TABLE WEIGHT_INFO_INTF_MIDDLE DROP CONSTRAINT PK_WEIGHT_INFO_INTF_MIDDLE CASCADE';
      EXECUTE IMMEDIATE VC_SQL;
    END IF;

    VC_SQL := 'INSERT INTO WEIGHT_INFO_INTF_MIDDLE NOLOGGING
              SELECT  *
              FROM WEIGHT_INFO_INTF WHERE DEAL_FLAG = 0';
    EXECUTE IMMEDIATE VC_SQL;

    VC_SQL := 'ALTER TABLE WEIGHT_INFO_INTF_MIDDLE ADD CONSTRAINT PK_WEIGHT_INFO_INTF_MIDDLE PRIMARY KEY (RECORD_ID) USING INDEX';
    EXECUTE IMMEDIATE VC_SQL;
      

  EXCEPTION
    WHEN OTHERS THEN
     v_resCode := PROCESS_ERROR;
     v_resMsg := '同步轨道衡接口数据异常'||substr(sqlerrm, 1, 100);
     return;
  END INITMIDDLETABLE;
  
end syn_data_task;
/

prompt
prompt Creating package body TRANS_DATA_TASK
prompt =====================================
prompt
create or replace package body znrl.trans_data_task is

 --取称重数据生成批次处理
  procedure syn_weight_info Is
   
    iv_trainNo  varchar2(50);--车次
    iv_carId    varchar2(50);--车号
    iv_venName  varchar2(50);--供应商
    iv_coalName varchar2(50);--煤种
    iv_netQty   number(19,6);--净重 
    v_venNo     varchar2(50);--供应商编码
    v_coalNo    varchar2(50);--煤种编码
    v_batchNo   varchar2(50);--批次号
    v_sampleCode   varchar2(50);--采样编码
    v_samplingCode varchar2(50);--制样编码
    v_resCode    varchar2(2);
    v_resMsg     varchar2(500);

  begin

    v_resCode := '0';
    v_resMsg  := 'ok';
    
    --初始化同步轨道衡数据
    initMiddleTable(v_resCode,v_resMsg);
                     
    if v_resCode <> '0' then
      --如果没用新数据则返回
      if v_resCode = PROCESS_NORMAL then
          return;
      else
          return;--进行报警
      end if;
    end if; 
     
    --处理轨道衡数据，记录来煤明细表、采样接口表、制样接口表
    For cur In (select *
                  from weight_info_intf_middle wi
                 where wi.deal_flag = '0') Loop
      Begin

        iv_trainNo     := cur.car_batchno;--车次
        iv_carId     := cur.car_id;--车号
        iv_venName  := cur.fh_unit;--供应商
        iv_coalName := cur.hm_name;--煤种
        iv_netQty := cur.net_qty;--净重
        
         --校验数据参数、获取生成批次来煤批次，并返回供应商、采样编码等信息         
        checkParams(iv_trainNo,--车次
                    iv_carId,--车号
                    iv_venName,--供应商
                    iv_coalName,--煤种
                    iv_netQty,--净重
                    v_venNo,--供应商编码
                    v_coalNo,--煤种编码
                    v_batchNo,--批次号
                    v_sampleCode,--采样编码
                    v_samplingCode,--制样编码
                    v_resCode,
                    v_resMsg);     
        
        if v_resCode <> '0' then  
              Rollback;
             --更新中间表处理标志   
              update weight_info_intf_middle
                 set deal_flag = '1', 
                     deal_disc = '处理失败:'||v_resMsg 
              where record_id = cur.record_id;
              --更新接口表处理标志
              update weight_info_intf
                 set deal_flag = '1', 
                     deal_disc = '处理失败:'||v_resMsg 
              where record_id = cur.record_id;
              Commit;
         else
         
          --插入来煤明细表、采样记录表、制样记录表
           recordSampleInfo(cur.record_id,  --中间表记录id
                             v_batchNo,     --批次号
                             v_venNo,       --供应商编码 
                             v_coalNo,      --煤种编码
                             v_sampleCode,  --采样编码
                             v_samplingCode,--制样编码
                             v_resCode,
                             v_resMsg);
                                              
            if v_resCode <> '0' then
                Rollback;
                 --更新中间表处理标志   
                update weight_info_intf_middle
                   set deal_flag = '1', 
                       deal_disc = '处理失败:'||v_resMsg 
                where record_id = cur.record_id;
                --更新接口表处理标志
                update weight_info_intf
                   set deal_flag = '1', 
                       deal_disc = '处理失败:'||v_resMsg 
                where record_id = cur.record_id;                
                Commit;     
             else
                 --更新中间表处理标志   
                update weight_info_intf_middle
                   set deal_flag = '1', 
                       deal_disc = '处理成功' 
                where record_id = cur.record_id;
                --更新接口表处理标志
                update weight_info_intf
                   set deal_flag = '1', 
                       deal_disc = '处理成功'
                where record_id = cur.record_id;
               Commit;
           end if; 
              
        end if;

      Exception
        When Others Then
          Rollback;
         v_resMsg := substr(sqlerrm, 1, 200);
      End;

    End Loop;
    
    --写入最终的采样信息表车辆数、预计总重字段  
    For cur In (select *
                  from batch_no_info b
                 where b.is_valid = '0') Loop
      Begin  
          --更新采样信息表车辆数、预计总重字段  
          update take_sample_intf t
             set t.car_num = cur.car_num, 
                 t.all_net_qty = cur.all_net_qty,
                 t.update_time = sysdate,
                 t.update_code = 'sys'
           where t.sample_code = cur.sample_code;
          Commit;

      Exception
        When Others Then
          Rollback;
         v_resMsg := substr(sqlerrm, 1, 200);
      End;
    End Loop;
    
    --更新批次号信息表，置有效标志位生效 
     update batch_no_info b
        set b.is_valid = '1',
            b.update_time = sysdate,
            b.update_code = 'sys'
      where b.is_valid  = '0';
     Commit;

  Exception
    When Others Then
      Rollback;
     v_resMsg := substr(sqlerrm, 1, 200);
  end;
 
 --校验输入参数,获取/生成批次，返回供应商、煤种编码、批次号、采样编码、制样编码
 procedure checkParams(v_trainNo       in varchar2,--车次
                        v_carId        in varchar2,--车号
                        v_venName      in varchar2,--供应商
                        v_coalName     in varchar2,--煤种
                        iv_netQty      in number,--净重
                        v_venNo        out varchar2,--供应商编码
                        v_coalNo       out varchar2,--煤种编码
                        v_batchNo      out varchar2,--批次号
                        v_sampleCode   out varchar2,--采样编码
                        v_samplingCode out varchar2,--制样编码
                        v_resCode     out varchar2,
                        v_resMsg      out varchar2) is
                        
  iv_cnt          number(5);
  iv_count        number(5);
  iv_dayBatchNum  number(5);
  iv_randomValue  number(5);
  i              number;

  begin
    v_resCode := '0';
    v_resMsg  := 'ok';

    if nvl(length(v_trainNo), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '车次为空';
      return;
    end if;

    if nvl(length(v_carId), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '车号为空';
      return;
    end if;

    if nvl(length(v_venName), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '供应商为空';
      return;
    end if;


    if nvl(length(v_coalName), 0) = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '煤种为空';
      return;
    end if;
    
    --取供应商编码
    select count(1)
      into iv_cnt
      from vendor_info v
     where v.ven_name = v_venName;
    
    if iv_cnt = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '未查询到供应商编码';
      return;
    elsif iv_cnt > 1 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '供应商编码不能唯一定位';
      return;
    end if;
    
    select ven_no
      into v_venNo
      from vendor_info v
     where v.ven_name = v_venName;
    
    --取煤种编码
    select count(1)
      into iv_cnt
      from coal_type c
     where c.coal_name = v_coalName;
    
    if iv_cnt = 0 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '未查询到煤种编码';
      return;
    elsif iv_cnt > 1 then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '煤种编码不能唯一定位';
      return;
    end if;
    
    select coal_no
      into v_coalNo
      from coal_type v
     where v.coal_name = v_coalName;
     
    --判断是否已进行分批：取采样编码、制样编码,生成批次号
    select count(1)
      into iv_cnt
      from batch_no_info b
     where b.is_valid = '0'
       and b.train_no = v_trainNo
       and b.ven_no = v_venNo
       and b.coal_no = v_coalNo;
        
    if iv_cnt = 0 then
      --未生成批次号，进行分批处理
      --生成批次号
       select nvl(max(day_batch_num),0)
        into iv_dayBatchNum
        from batch_no_info b
       where to_char(insert_time,'yyyymmdd') = to_char(sysdate,'yyyymmdd');  
       iv_dayBatchNum := iv_dayBatchNum+1;
       
       v_batchNo := 'HC-'||to_char(sysdate,'yyyymmdd')||'-'||lpad(iv_dayBatchNum,2,'0');
      
      --生成采样编码 
      for i in 1..100 loop 
        select lpad(dbms_random.value(100, 999), 3, '0')  
         into iv_randomValue
        from dual;
                 
        v_sampleCode := to_char(sysdate,'yyyymmdd')||iv_randomValue; 
                 
        select count(1)
        into iv_count
        from batch_no_info b
       where b.sample_code = v_sampleCode; 
         
       Exit When iv_count = 0;   
        
      end loop;
      
       --生成制样编码 
      for i in 1..100 loop 
        select lpad(dbms_random.value(100, 999), 3, '0') 
         into iv_randomValue
        from dual;
            
        v_samplingCode := to_char(sysdate,'yyyymmdd')||iv_randomValue; 
                 
        select count(1)
        into iv_count
        from batch_no_info b
       where b.sampling_code = v_samplingCode; 
       
        Exit When iv_count = 0;
      end loop;
        
      --记录批次号信息表
      insert into batch_no_info
        (batch_no,
         batch_no_type,
         train_no,
         ven_no,
         coal_no,
         sample_code,
         sampling_code,
         labor_code,
         day_batch_num,
         card_ids,
         car_num,
         all_net_qty,
         is_valid,
         insert_time,
         update_time,
         op_code,
         update_code)
      values
        (v_batchNo,
         '1',
         v_trainNo,
         v_venNo,
         v_coalNo,
         v_sampleCode,
         v_samplingCode,
         null,
         iv_dayBatchNum,
         v_carId,
         1,
         iv_netQty,
         '0',
         sysdate,
         sysdate,
         'sys',
         'sys');
      
    elsif iv_cnt = 1 then
      --已生成批次号，取已存在分批信息
      select batch_no, sample_code, sampling_code
        into v_batchNo, v_sampleCode, v_samplingCode
        from batch_no_info b
       where b.is_valid = '0'
         and b.train_no = v_trainNo
         and b.ven_no = v_venNo
         and b.coal_no = v_coalNo;
       
       update batch_no_info b
          set b.car_num     = b.car_num + 1,
              b.all_net_qty = b.all_net_qty + iv_netQty,
              b.card_ids    = b.card_ids || ',' || v_carId
        where b.sample_code = v_sampleCode;
     else    
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '批次号重复，请检查批次号';
      return;    
    end if;
 
  exception
    when others then
      v_resCode := PROCESS_ERROR;
      v_resMsg  := '校验供应商、煤种参数,获取生成批次异常: ' || substr(sqlerrm, 1, 100);
      return;
  end;
  
  
   --记录来煤明细表，采样信息中间表，制样信息中间表
  procedure recordSampleInfo(v_recordId      in varchar2,--中间表记录id
                             v_batchNo       in varchar2,--批次号
                             v_venNo         in varchar2,--供应商编码 
                             v_coalNo        in varchar2,--煤种编码
                             v_sampleCode    in varchar2,--采样编码
                             v_samplingCode  in varchar2,--制样编码
                             v_resCode       out varchar2,
                             v_resMsg        out varchar2) is
                             
    iv_cnt      number(5);
    iv_carBatchno varchar2(50);
    iv_carId      varchar2(50);
    iv_carTyp     varchar2(50);
    iv_mzQty      number(19,6);
    iv_tickQty    number(19,6);
    iv_pzQty      number(19,6);
    iv_netQty     number(19,6);
    iv_lossQty    number(19,6);
    iv_ydQty      number(19,6);
    iv_kdQty      number(19,6);
    iv_koudQty    number(19,6);
    iv_czDtm       varchar2(50);
    iv_jqDtm      varchar2(50);
    iv_czBalanceNo varchar2(50);
    iv_jqBalanceNo varchar2(50);
    iv_ghNo       varchar2(50);
    iv_speed      varchar2(50);
    iv_hmName     varchar2(50);
    iv_fzPlace    varchar2(50);
    iv_dzPlace    varchar2(50);
    iv_fhUnit     varchar2(50);
    iv_dhUnit     varchar2(50);
    iv_recordId   number(10);
    iv_recordNo   number(10);
    iv_xhNum      number(5);
    iv_sampleIntfIid number(10);
    iv_samplingIntfIid number(10);
    
    
  begin
    v_resCode     := '0';
    v_resMsg      := 'ok';
    
    select count(1)
      into iv_cnt
      from weight_info_intf_middle wi
     where wi.record_id = v_recordId;
        
     if iv_cnt = 0 then
        v_resCode := PROCESS_ERROR;
        v_resMsg  := '没有找到称重数据';
        return;
      elsif iv_cnt > 1 then
        v_resCode := PROCESS_ERROR;
        v_resMsg  := '称重数据不能唯一定位';
        return;
    end if;
    
    select car_batchno,car_id,car_typ,mz_qty,tick_qty,pz_qty,net_qty,loss_qty,
           yd_qty,kd_qty,koud_qty,cz_dtm,jq_dtm,cz_balance_no,jq_balance_no,
           gh_no,speed,hm_name,fz_place,dz_place,fh_unit,dh_unit,xh_num
      into iv_carBatchno,iv_carId,iv_carTyp,iv_mzQty,iv_tickQty,iv_pzQty,iv_netQty,iv_lossQty,
           iv_ydQty,iv_kdQty,iv_koudQty,iv_czDtm,iv_jqDtm,iv_czBalanceNo,iv_jqBalanceNo,iv_ghNo,
           iv_speed,iv_hmName,iv_fzPlace,iv_dzPlace,iv_fhUnit,iv_dhUnit,iv_xhNum
      from weight_info_intf_middle wi
     where wi.record_id = v_recordId;
     
     select SEQ_RLRECORDMSTHY.NEXTVAL 
      into iv_recordId 
     from dual;
       
     select SEQ_RLRECORDMSTHY.NEXTVAL 
      into iv_recordNo 
     from dual;

    --来煤明细记录
    insert into rlrecordmsthy
       (record_id,      
        record_no,        
        train_no, --车次       
        car_id,   --车号      
        car_typ,  --车型      
        xh_num,  --车厢序号       
        ven_no,  --供应商        
        --carrier_no,     
        coal_no,  --煤种       
        --colry_no, --煤种      
        --coal_size,      
        --coal_water,    
        tick_qty,--票重       
        mz_qty,  --毛重       
        pz_qty,  --皮重       
        net_qty, --净重      
        loss_qty,--运损       
        kd_qty,  --扣吨
        kud_qty, --亏吨       
        yd_qty,  --盈吨        
        cz_dtm,  --称重时间       
        jq_dtm,  --检轻时间       
        --xm_dtm,  --卸煤时间       
        car_spd, --车速       
        --cz_balance_no,  
        --jq_balance_no,  
        --gh_no,          
        train_batch_no,--批次号
        sample_code,   --采样编码
        sample_typ,    --采样方式
        empty_flg,     --是否空车
        --leave_flg,     --是否出厂 
        --door_no,       --门号 
        record_dtm,     
        leave_dtm,      
        --fcj_code,       
        cfm_flg,        
        start_place, --发站   
        final_place, --到站   
        insert_time,    
        update_time,    
        op_code,        
        update_code,    
        remark)
    values
      (iv_recordId,
       iv_recordNo,
       iv_carBatchno,
       iv_carId,
       iv_carTyp,
       iv_xhNum,
       v_venNo,
       v_coalNo,
       iv_tickQty,
       iv_mzQty,
       iv_pzQty,
       iv_netQty,
       iv_lossQty,
       iv_koudQty,
       iv_kdQty,
       iv_ydQty,
       to_date(iv_czDtm, 'yyyy-mm-ddhh24:mi:ss'),
       to_date(iv_jqDtm, 'yyyy-mm-ddhh24:mi:ss'),
       to_number(iv_speed),
       v_batchNo,
       v_sampleCode,
       '0',
       '1',
       sysdate,
       sysdate,
       '1',
       iv_fzPlace,
       iv_dzPlace,
       sysdate,
       sysdate,
       'sys',
       'sys',
       '系统自动后台生成批次');
       
       --记录采样信息接口表
       select SEQ_TAKE_SAMPLE_INTF.NEXTVAL 
         into iv_sampleIntfIid 
       from dual;
        
       insert into take_sample_intf
         (sample_intf_rec_id,
          record_no,
          car_id,
          sample_code,
          train_batch_no,
          coal_type,
          sample_typ,
          data_status,
          insert_time,
          op_code,
          update_time,
          update_code)
       values
         (iv_sampleIntfIid,
          iv_recordNo,
          iv_carId,
          v_sampleCode,
          v_batchNo,
          v_coalNo,
          '0',
          '0',
          sysdate,
          'sys',
          sysdate,
          'sys');
          
       --记录制样信息接口表
       select SEQ_PREPAR_SAMPLING_INTF.NEXTVAL 
         into iv_samplingIntfIid 
       from dual; 
         
        insert into prepar_sampling_intf
          (sampling_intf_rec_id,
           sample_code,
           sampling_code,
           coal_type,
           data_status,
           insert_time,
           op_code,
           update_time,
           update_code)
        values
          (iv_samplingIntfIid,
           v_sampleCode,
           v_samplingCode,
           v_coalNo,
           '0',
           sysdate,
           'sys',
           sysdate,
           'sys');
                   
  exception
    when others then
      v_resCode := '1';
      v_resMsg  := '记录来煤明细/采样接口表异常: ' ||
                   substr(sqlerrm, 1, 100);
  end;
  

  PROCEDURE initMiddleTable(v_resCode    out varchar2,
                            v_resMsg     out varchar2) IS
    VC_SQL       VARCHAR2(200);
    IV_CNT      NUMBER(5);
  BEGIN
    
    v_resCode := '0'; 
    v_resMsg  := 'ok';

    --查看轨道衡数据是否已经生成。
    SELECT COUNT(*)
      INTO IV_CNT
      FROM WEIGHT_INFO_INTF W
     WHERE W.DEAL_FLAG = '0';
    IF IV_CNT = 0 THEN
      v_resCode := PROCESS_NORMAL;
      v_resMsg  := '轨道衡数据未生成';
      RETURN;
    END IF;

    /*转换成源表*/
    /*VC_SQL := 'TRUNCATE TABLE WEIGHT_INFO_INTF_MIDDLE';
    EXECUTE IMMEDIATE VC_SQL;*/

    SELECT COUNT(*)
      INTO IV_CNT
      FROM ALL_CONSTRAINTS A
     WHERE A.OWNER = 'ZNRL'
       AND A.TABLE_NAME = 'WEIGHT_INFO_INTF_MIDDLE'
       AND A.CONSTRAINT_TYPE = 'P';
    IF IV_CNT > 0 THEN
      VC_SQL := 'ALTER TABLE WEIGHT_INFO_INTF_MIDDLE DROP CONSTRAINT PK_WEIGHT_INFO_INTF_MIDDLE CASCADE';
      EXECUTE IMMEDIATE VC_SQL;
    END IF;

    VC_SQL := 'INSERT INTO WEIGHT_INFO_INTF_MIDDLE NOLOGGING
              SELECT  *
              FROM WEIGHT_INFO_INTF WHERE DEAL_FLAG = 0';
    EXECUTE IMMEDIATE VC_SQL;

    VC_SQL := 'ALTER TABLE WEIGHT_INFO_INTF_MIDDLE ADD CONSTRAINT PK_WEIGHT_INFO_INTF_MIDDLE PRIMARY KEY (RECORD_ID) USING INDEX';
    EXECUTE IMMEDIATE VC_SQL;
      

  EXCEPTION
    WHEN OTHERS THEN
     v_resCode := PROCESS_ERROR;
     v_resMsg := '同步轨道衡接口数据异常'||substr(sqlerrm, 1, 100);
     return;
  END INITMIDDLETABLE;
  
end trans_data_task;
/


spool off
