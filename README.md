# ClassRegistrationDatabase
Procedures

Show tables Procedures:
show_STUDENTS(c_res OUT SYS_REFCURSOR);
show_PREREQUISITES(c_res OUT SYS_REFCURSOR);
show_LOGS(c_res OUT SYS_REFCURSOR);
show_GRADES(c_res OUT SYS_REFCURSOR);
show_ENROLLMENTS(c_res OUT SYS_REFCURSOR);
show_COURSE_CREDIT(c_res OUT SYS_REFCURSOR);
show_COURSES(c_res OUT SYS_REFCURSOR);
show_CLASSES(c_res OUT SYS_REFCURSOR);

input:  NULL
output: Return a cursor of table of students;

insert_a_student (
si IN students.sid%type,
fna IN students.firstname%type,
lna IN students.lastname%type,
sts IN students.status%type,
gp IN students.gpa%type,
em IN students.email%type,
res OUT SYS_REFCURSOR
);

get_student_classes (
sid_in IN students.sid%type,
res_student OUT SYS_REFCURSOR,
res_classes OUT SYS_REFCURSOR,
err OUT varchar2
);

get_prerequisite (
dc IN PREREQUISITES.dept_code%type,
cn IN PREREQUISITES.course_no%type,
res_classes OUT SYS_REFCURSOR
);

get_enrolledOf (
cid_in IN classes.classid%type,
class OUT SYS_REFCURSOR,
students OUT SYS_REFCURSOR,
err OUT varchar2
);

enroll_std2cla (
sid_in IN students.SID%type,
cid_in IN classes.CLASSID%type,
err OUT varchar2
);

prerequisite_ok(
sid_in IN students.sid%type,
deptcode_in IN courses.dept_code%type,
course_no_in IN courses.course_no%type,
all_pre_ok OUT BOOLEAN
);

drop_class (
sid_in IN students.sid%type,
cid_in IN classes.CLASSID%type,
mes OUT varchar2
);

ok_to_drop (
sid_in IN students.sid%type,
deptcode_in IN classes.dept_code%type,
course_no_in IN classes.course_no%type,
res OUT BOOLEAN
);

delete_student (
sid_in IN students.SID%type,
mes OUT VARCHAR2
);



Sequences
seq_log : generate sequence of index numbers that start from 100 and end 999.
Triggers
classes_update_enrl:
classes_update_drop:
update_logs_add_std :
update_logs_delete_std :
update_logs_enro :
update_logs_drop :
update_students_delete :
