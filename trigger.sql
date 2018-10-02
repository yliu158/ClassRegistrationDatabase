-- triggers

CREATE OR REPLACE TRIGGER update_students_delete
before delete on students
for each row
BEGIN
  dbms_output.put_line('GO UPDATE STUDENTS.' || :old.sid);
  delete from enrollments where sid = :old.sid;
END;
/

/*
Write triggers to add tuples to the logs
table automatically whenever a student is added to or deleted from the students table,
or when a student is successfully enrolled into or dropped
from a class (i.e., when a tuple is inserted into or deleted from the enrollments table).
*/


CREATE OR REPLACE TRIGGER update_logs_add_std
before insert on students
for each row
DECLARE
time_of_insert DATE;
LOGID NUMBER;
BEGIN
  dbms_output.put_line('OK TO UPDATE');
  time_of_insert := LOCALTIMESTAMP;
  LOGID := seq_log.nextval;
  insert into logs values (LOGID, 'user', time_of_insert, 'students', 'INSERT', :new.sid);
END;
/

-- execute insert into students values ('B009', 'Yang', 'Liu', 'graduate', 4, 'yang@bu.edu' );
-- execute insert into students values ('B010', 'Alex', 'Liu', 'graduate', 4, 'alex@bu.edu' );

CREATE OR REPLACE TRIGGER update_logs_delete_std
before delete on students
for each row
DECLARE
time_of_delete DATE;
LOGID NUMBER;
BEGIN
  dbms_output.put_line('OK TO UPDATE');
  time_of_delete := LOCALTIMESTAMP;
  LOGID := seq_log.nextval;
  insert into logs values (LOGID, 'user', time_of_delete, 'students', 'DELETE', :old.sid);
END;
/
--
-- execute delete_student('B011');
-- execute delete_student('B009');


CREATE OR REPLACE TRIGGER update_logs_enro
before insert on enrollments
for each row
DECLARE
time_of_enroll DATE;
LOGID NUMBER;
BEGIN
  time_of_enroll := LOCALTIMESTAMP;
  LOGID := seq_log.nextval;
  insert into logs values(LOGID, 'user', time_of_enroll, 'enrollments', 'INSERT', :new.SID);
END;
/

-- execute enroll_std2cla('B004', 'c0007');


CREATE OR REPLACE TRIGGER update_logs_drop
before delete on enrollments
for each row
DECLARE
time_of_drop DATE;
LOGID NUMBER;
BEGIN
  time_of_drop := LOCALTIMESTAMP;
  LOGID := seq_log.nextval;
  insert into logs values(LOGID, 'user', time_of_drop, 'enrollments', 'DELETE', :old.SID);
END;
/

-- execute drop_class('B004','c0007');


CREATE OR REPLACE TRIGGER classes_update_enrl
before insert on enrollments
for each row
BEGIN
  dbms_output.put_line(' GO UPDATE. ' || :new.CLASSID);
  update classes set class_size = class_size + 1 where CLASSID = :new.CLASSID;
END;
/


CREATE OR REPLACE TRIGGER classes_update_drop
before delete on enrollments
for each row
BEGIN
  dbms_output.put_line('go update: ' || :old.CLASSID);
  update classes set class_size = class_size - 1 where CLASSID = :old.CLASSID;
END;
/
