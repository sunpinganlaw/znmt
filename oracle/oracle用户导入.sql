create tablespace TBS_ZNRL1 datafile 'D:\app\Administrator\oradata\orcl\data1.dbf' size 500M autoextend on next 50m maxsize 20480m ;

create user znrl_tz identified by znrl_tz default tablespace TBS_ZNRL1;
create  directory dmpdir as 'D:\app\dmpdir';

GRANT read, write ON DIRECTORY dmpdir TO znrl_tz;
grant create session to znrl_tz;
grant create table to znrl_tz;
grant create view to znrl_tz;
grant create any synonym to znrl_tz;
grant create public DATABASE LINK to znrl_tz;
grant create DATABASE LINK to znrl_tz;
grant resource,connect to znrl_tz;
grant create any index to znrl_tz;
grant create any synonym to znrl_tz;
grant create any sequence to znrl_tz;
grant alter any table to znrl_tz;
grant debug connect session to znrl_tz;
grant unlimited tablespace to znrl_tz;

impdp znrl_tz/znrl_tz@orcl directory=dmpdir dumpfile= TZ_ZNRL1020.DMP remap_schema=znrl_app:znrl_tz logfile=imp.log;

expdp znrl_tz/znrl_tz@orcl directory=dmpdir dumpfile= TZ_ZNRL1020.DMP schemas=znrl_tz logfile=imp.log;

exp %user%/%password%@%host%:%port%/%dbName% file=%backupPath%\%backupFileName%%dateTime%.dmp log=%backupPath%\%backupFileName%%dateTime%.log owner=%owner%
imp znrl_hf/znrl_hf@127.0.0.1/ORCL file=D:\app\dmpdir\znrl_app_20191105182913.dmp fromuser=znrl_app touser=znrl_hf