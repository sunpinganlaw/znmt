create or replace package business_core_pck is
  /**
    1��json����
       jsonOjb --public���֣�  mainBranch��childBranch���Լ�������������
               --���ݲ���  ��  ���ݵ����ݲ��֣�����Ϊ��������
                               "paramList1":[{},{}]  ,paramList2":[{},{}] ,obj1":{},obj2":{},
    1����processComp��process�еķ�֧�У����Ӵ����߼���֧��
       case when iv_branch ='add_new_contract' then

       �������ζ��Ƚϴ���ѡ��processComp������ѡ��process��
    2) �ο�����brc_sample,ֱ��copy�����޸�
  */

  PUBLIC_NODE CONSTANT VARCHAR2(100) := 'public';
  MAIN_BRANCH_NODE CONSTANT VARCHAR2(100) := 'mainBranch';
  CHILD_BRANCH_NODE CONSTANT VARCHAR2(100) := 'childBranch';
  INVOKE_PKG_NAME  CONSTANT VARCHAR2(100) := 'InvokePkgName';
  procedure process(v_business_str in clob,
                    v_result     in out varchar2,
                    v_res_code   out varchar2,
                    v_res_msg    out varchar2,
                    v_op_id in varchar2);

  procedure processComp(v_business_str in clob,
                        v_result in out clob,
                        v_res_code   out varchar2,
                        v_res_msg    out varchar2,
                        v_op_id in varchar2) ;

  procedure processCompClob(
                  v_business_json in clob,
                  v_business_clob in clob,
                  v_result_json   in out clob,
                  v_result_clob   in out clob,
                  v_res_code   out varchar2,
                  v_res_msg    out varchar2,
                  v_op_id in varchar2);
end business_core_pck;

 
/
create or replace package body business_core_pck is
  procedure process(v_business_str in clob,
                    v_result     in out varchar2,
                    v_res_code   out varchar2,
                    v_res_msg    out varchar2,
                    v_op_id in varchar2) is
    iv_json_in      json;
    iv_public_Json  json;

    iv_json_list    json_list;
    iv_tmp_json     json;
    iv_tmp_jv       json_value;

    iv_main_branch  varchar2(100);
    iv_child_branch varchar2(100);
    iv_pkg_conf     varchar2(200):=null;

    iv_json_out  json:= json();
    iv_is_log       varchar2(10):= 'N';
  begin
    v_res_code := '0';
    v_res_msg  := 'ok';

    --����JSON
    iv_json_in := json(v_business_str);
    iv_public_Json := json_ext.get_json(iv_json_in,PUBLIC_NODE);

    iv_main_branch := json_ext.get_string(iv_public_Json,MAIN_BRANCH_NODE);
    iv_child_branch := json_ext.get_string(iv_public_Json,CHILD_BRANCH_NODE);

    if(iv_public_Json is not null) then
       iv_pkg_conf := json_ext.get_string(iv_public_Json, INVOKE_PKG_NAME);
    end if;
    iv_pkg_conf := trim(iv_pkg_conf);

    if(iv_pkg_conf is null or length(iv_pkg_conf) <= 0)then
      begin
        select pc.pkg_conf
          into iv_pkg_conf
          from pkg_config pc
         where pc.mainbranch = iv_main_branch
           and pc.childbranch = iv_child_branch
           and pc.conf_state = 'C';
      exception when others then
        iv_pkg_conf := null;
      end;
      if(iv_pkg_conf is null) then
        v_res_code := '1';
        v_res_msg  := 'δ֪��'||iv_main_branch||'.'||iv_child_branch;
      end if;
    end if;

    execute immediate 'begin '||iv_pkg_conf||'(:1,:2,:3,:4,:5,:6); end; ' using iv_json_in, iv_public_Json,out iv_json_out,out v_res_code,out v_res_msg,v_op_id;

    v_result := iv_json_out.to_char();
  exception
    when others then
      v_res_code := '1';
      v_res_msg  := '�����쳣��'||iv_main_branch||'.'||iv_child_branch|| substr(sqlerrm, 1, 200);
  end;

  procedure processComp(v_business_str in clob,
                        v_result in out clob,
                        v_res_code   out varchar2,
                        v_res_msg    out varchar2,
                        v_op_id in varchar2) is
    iv_json_in      json;
    iv_public_Json  json;

    iv_json_list    json_list;
    iv_tmp_json     json;
    iv_tmp_jv       json_value;

    iv_main_branch  varchar2(100);
    iv_child_branch varchar2(100);
    iv_pkg_conf     varchar2(200):=null;
    iv_json_out  json:= json();
    iv_temp  varchar2(4000);
    iv_is_log       integer := 0;   
  begin
    v_res_code := '0';
    v_res_msg  := 'ok';
    
    --�������ã��Ƿ��¼��־
    select count(1) into iv_is_log from constant_data_config cd 
    where CONST_KEY = 'BUSINESS_CORE_PCK_LOG_1' and cd.const_value = 'Y' ;
    if(iv_is_log > 0)then
      begin
      record_log('processComp','beforeLog',null,v_business_str,null,null,null);
      --mainbranch,childbranch,pkg_conf,in_param,out_param,in_param_varchar,out_param_varchar
      exception when others then
        null;
      end;
    end if;
    
    --����JSON
    iv_json_in := json(v_business_str);
    iv_public_Json := json_ext.get_json(iv_json_in,PUBLIC_NODE);
    iv_main_branch := json_ext.get_string(iv_public_Json,MAIN_BRANCH_NODE);
    iv_child_branch := json_ext.get_string(iv_public_Json,CHILD_BRANCH_NODE);

    if(iv_public_Json is not null) then
       iv_pkg_conf := json_ext.get_string(iv_public_Json, INVOKE_PKG_NAME);
    end if;
    iv_pkg_conf := trim(iv_pkg_conf);

    if(iv_pkg_conf is null or length(iv_pkg_conf) <= 0)then
      begin
        select pc.pkg_conf
          into iv_pkg_conf
          from pkg_config pc
         where pc.mainbranch = iv_main_branch
           and pc.childbranch = iv_child_branch
           and pc.conf_state = 'C';
      exception when others then
        iv_pkg_conf := null;
      end;
      if(iv_pkg_conf is null) then
        v_res_code := '1';
        v_res_msg  := 'δ֪��'||iv_main_branch||'.'||iv_child_branch;
      end if;
    end if;

    execute immediate 'begin '||iv_pkg_conf||'(:1,:2,:3,:4,:5,:6); end; ' using iv_json_in, iv_public_Json,out iv_json_out,out v_res_code,out v_res_msg,v_op_id;
    /*case when iv_branch ='add_new_contract' then
    else
    end case;*/


   --ת����clob��ʽ
   dbms_lob.createtemporary(v_result, TRUE, dbms_lob.session);
   iv_json_out.to_clob(v_result);
   
       --�������ã��Ƿ��¼��־
    select count(1) into iv_is_log from constant_data_config cd 
    where CONST_KEY = 'BUSINESS_CORE_PCK_LOG_2' and cd.const_value = 'Y' ;
    if(iv_is_log > 0)then
      begin
      record_log('processComp',null,iv_pkg_conf,v_business_str,v_result,null,null);
      --mainbranch,childbranch,pkg_conf,in_param,out_param,in_param_varchar,out_param_varchar
      exception when others then
        null;
      end;
    end if;
  exception
    when others then
      v_res_code := '1';
      v_res_msg  := '�����쳣��'||iv_main_branch||'.'||iv_child_branch||','|| substr(sqlerrm, 1, 200);
  end;

  procedure processCompClob(v_business_json in clob,
                  v_business_clob in clob,
                  v_result_json   in out clob,
                  v_result_clob   in out clob,
                  v_res_code   out varchar2,
                  v_res_msg    out varchar2,
                  v_op_id in varchar2) is
    iv_json_in      json;
    iv_public_Json  json;

    iv_json_list    json_list;
    iv_tmp_json     json;
    iv_tmp_jv       json_value;

    iv_main_branch  varchar2(100);
    iv_child_branch varchar2(100);
    iv_pkg_conf     varchar2(200):=null;
    iv_json_out  json:= json();
    iv_temp  varchar2(4000);
  begin
    v_res_code := '0';
    v_res_msg  := 'ok';
    record_log('processComp','',null,v_result_json||'-'||v_result_clob,null,null,null);

    --����JSON
    iv_json_in := json(v_business_json);
    iv_public_Json := json_ext.get_json(iv_json_in,PUBLIC_NODE);
    iv_main_branch := json_ext.get_string(iv_public_Json,MAIN_BRANCH_NODE);
    iv_child_branch := json_ext.get_string(iv_public_Json,CHILD_BRANCH_NODE);

    if(iv_public_Json is not null) then
       iv_pkg_conf := json_ext.get_string(iv_public_Json, INVOKE_PKG_NAME);
    end if;
    iv_pkg_conf := trim(iv_pkg_conf);
    if(iv_pkg_conf is null or length(iv_pkg_conf) <= 0)then
      begin
        select pc.pkg_conf
          into iv_pkg_conf
          from pkg_config pc
         where pc.mainbranch = iv_main_branch
           and pc.childbranch = iv_child_branch
           and pc.conf_state = 'C';
      exception when others then
        iv_pkg_conf := null;
      end;
      if(iv_pkg_conf is null) then
        v_res_code := '1';
        v_res_msg  := 'δ֪��'||iv_main_branch||'.'||iv_child_branch;
      end if;
    end if;

    execute immediate 'begin '||iv_pkg_conf||'(:1,:2,:3,:4,:5,:6,:7,:8); end; '
    using iv_json_in, iv_public_Json,v_business_clob,out iv_json_out,out v_result_clob,out v_res_code,out v_res_msg,v_op_id;

   --ת����clob��ʽ
   dbms_lob.createtemporary(v_result_json, TRUE, dbms_lob.session);
   iv_json_out.to_clob(v_result_json);
  exception
    when others then
      v_res_code := '1';
      v_res_msg  := '�����쳣��'||iv_main_branch||'.'||iv_child_branch||','|| substr(sqlerrm, 1, 200);
  end;

  --��ΪprocessCompClob֧����clob in��out������processCompClob2�����ˣ����ǵ������ԣ�Ŀǰ���š�
  procedure processCompClob2(
                        v_main_branch in varchar2,
                        v_child_branch in varchar2,
                        v_business_str in clob,
                        v_result in out clob,
                        v_res_code   out varchar2,
                        v_res_msg    out varchar2,
                        v_op_id in varchar2) is

    iv_pkg_conf     varchar2(200):=null;
    iv_temp  varchar2(4000);
  begin
    v_res_code := '0';
    v_res_msg  := 'ok';
    record_log(v_main_branch,v_child_branch,null,v_business_str,null,null,null);

    begin
      select pc.pkg_conf
        into iv_pkg_conf
        from pkg_config pc
       where pc.mainbranch = v_main_branch
         and pc.childbranch = v_child_branch
         and pc.conf_state = 'C';
    exception when others then
      iv_pkg_conf := null;
    end;
    if(iv_pkg_conf is null) then
      v_res_code := '1';
      v_res_msg  := 'δ֪��'||v_main_branch||'.'||v_child_branch;
    end if;

    execute immediate 'begin '||iv_pkg_conf||'(:1,:2,:3,:4,:5); end; ' using v_business_str, out v_result,out v_res_code,out v_res_msg,v_op_id;

  exception
    when others then
      v_res_code := '1';
      v_res_msg  := '�����쳣��'||v_main_branch||'.'||v_child_branch||','|| substr(sqlerrm, 1, 200);
  end;

end business_core_pck;
/
