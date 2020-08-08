create tablespace TBS_ZNRL1 datafile 'D:\app\Administrator\oradata\orcl\data1.dbf' size 500M autoextend on next 50m maxsize 20480m ;

create user znmt_app identified by znmt_app_new default tablespace TBS_ZNRL1;
create  directory dmpdir as 'D:\app\dmpdir';

GRANT read, write ON DIRECTORY dmpdir TO znmt_app;
grant create session to znmt_app;
grant create table to znmt_app;
grant create view to znmt_app;
grant create any synonym to znmt_app;
grant create public DATABASE LINK to znmt_app;
grant create DATABASE LINK to znmt_app;
grant resource,connect to znmt_app;
grant create any index to znmt_app;
grant create any synonym to znmt_app;
grant create any sequence to znmt_app;
grant alter any table to znmt_app;
grant debug connect session to znmt_app;
grant unlimited tablespace to znmt_app;
grant select, insert, update, delete, index on WEIGHT_INFO_INTF to USER_CZ;

impdp znmt_app/znmt_app@orcl directory=dmpdir dumpfile= TZ_ZNRL0510bak.DMP remap_schema=znmt_app:znmt_app logfile=imp.log;

expdp  znmt_app/znmt_app@orcl directory=dmpdir dumpfile= TZ_ZNRL0510bak.DMP schemas=znmt_app logfile=imp.log;

exp %user%/%password%@%host%:%port%/%dbName% file=%backupPath%\%backupFileName%%dateTime%.dmp log=%backupPath%\%backupFileName%%dateTime%.log owner=%owner%
imp znrl_hf/znrl_hf@127.0.0.1/ORCL file=D:\app\dmpdir\znrl_app_20191105182913.dmp fromuser=znrl_app touser=znrl_hf