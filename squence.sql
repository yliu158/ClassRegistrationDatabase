/*
Use a sequence to generate the values
for logid automatically when new log records are
inserted into the logs table. All logid values
should have 3 digits.
*/

set serveroutput on
drop sequence seq_log;
CREATE SEQUENCE seq_log
MAXVALUE 999
START WITH 100
increment BY 1
CACHE 10;
