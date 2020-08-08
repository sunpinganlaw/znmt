create tablespace TBS_ZNRL_NEW datafile 'D:\app\Administrator\oradata\orcl\ZNMT.dbf' size 50M autoextend on next 50m maxsize 20480m ;

create user znmt_app_new identified by znmt_app_new default tablespace TBS_ZNRL_NEW;
create  directory znrl as 'D:\app\znrl';

GRANT read, write ON DIRECTORY znrl TO znmt_app_new;
grant create session to znmt_app_new;
grant create table to znmt_app_new;
grant create view to znmt_app_new;
grant create any synonym to znmt_app_new;
grant create public DATABASE LINK to znmt_app_new;
grant create DATABASE LINK to znmt_app_new;
grant resource,connect to znmt_app_new;
grant create any index to znmt_app_new;
grant create any synonym to znmt_app_new;
grant create any sequence to znmt_app_new;
grant alter any table to znmt_app_new;
grant debug connect session to znmt_app_new;
grant unlimited tablespace to znmt_app_new;

impdp znmt_app_new/znmt_app_new@orcl directory=znrl dumpfile= TZ_ZNRL0621bak.DMP remap_schema=znmt_app:znmt_app_new remap_tablespace=TBS_ZNRL1:TBS_ZNRL_NEW logfile=imp.log;

expdp  znmt_app/znmt_app@orcl directory=dmpdir dumpfile= TZ_ZNRL0621bak.DMP schemas=znmt_app logfile=imp.log;

exp %user%/%password%@%host%:%port%/%dbName% file=%backupPath%\%backupFileName%%dateTime%.dmp log=%backupPath%\%backupFileName%%dateTime%.log owner=%owner%
imp znrl_hf/znrl_hf@127.0.0.1/ORCL file=D:\app\dmpdir\znrl_app_20191105182913.dmp fromuser=znrl_app touser=znrl_hf