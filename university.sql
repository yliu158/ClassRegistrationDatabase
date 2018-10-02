set serveroutput on
CREATE OR REPLACE PACKAGE university
AS
PROCEDURE show_STUDENTS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_PREREQUISITES(c_res OUT SYS_REFCURSOR);
PROCEDURE show_LOGS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_ENROLLMENTS(c_res OUT SYS_REFCURSOR);
PROCEDURE show_COURSES(c_res OUT SYS_REFCURSOR);
PROCEDURE show_CLASSES(c_res OUT SYS_REFCURSOR);

PROCEDURE insert_a_student (
  si IN students.sid%type,
  fna IN students.firstname%type,
  lna IN students.lastname%type,
  sts IN students.status%type,
  gp IN students.gpa%type,
  em IN students.email%type,
  res OUT SYS_REFCURSOR
);

PROCEDURE get_student_classes (
  sid_in IN students.sid%type,
  res_student OUT SYS_REFCURSOR,
  res_classes OUT SYS_REFCURSOR,
  err OUT varchar2
);

PROCEDURE get_prerequisite (
  dc IN PREREQUISITES.dept_code%type,
  cn IN PREREQUISITES.course_no%type,
  res_classes OUT SYS_REFCURSOR
);

PROCEDURE get_enrolledOf (
  cid_in IN classes.classid%type,
  class OUT SYS_REFCURSOR,
  students OUT SYS_REFCURSOR,
  err OUT varchar2
);

PROCEDURE enroll_std2cla (
  sid_in IN students.SID%type,
  cid_in IN classes.CLASSID%type,
  err OUT varchar2
);

PROCEDURE prerequisite_ok(
  sid_in IN students.sid%type,
  deptcode_in IN courses.dept_code%type,
  course_no_in IN courses.course_no%type,
  all_pre_ok OUT BOOLEAN
);

PROCEDURE drop_class (
  sid_in IN students.sid%type,
  cid_in IN classes.CLASSID%type,
  mes OUT varchar2
);

PROCEDURE ok_to_drop (
  sid_in IN students.sid%type,
  deptcode_in IN classes.dept_code%type,
  course_no_in IN classes.course_no%type,
  res OUT BOOLEAN
);

PROCEDURE delete_student (
  sid_in IN students.SID%type,
  mes OUT VARCHAR2
);

END;
/

CREATE OR REPLACE PACKAGE BODY university
AS

  PROCEDURE show_STUDENTS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from STUDENTS;
  END;

  PROCEDURE show_PREREQUISITES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from PREREQUISITES;
  END;

  PROCEDURE show_LOGS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from LOGS;
  END;

  PROCEDURE show_ENROLLMENTS (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from ENROLLMENTS;
  END;

  PROCEDURE show_COURSES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select DEPT_CODE, COURSE_NO, TITLE from COURSES;
  END;

  PROCEDURE show_CLASSES (c_res OUT SYS_REFCURSOR)
  AS
  BEGIN
    open c_res for select * from CLASSES;
  END;

  PROCEDURE insert_a_student (
    si IN students.sid%type,
    fna IN students.firstname%type,
    lna IN students.lastname%type,
    sts IN students.status%type,
    gp IN students.gpa%type,
    em IN students.email%type,
    res OUT SYS_REFCURSOR)
  AS
  BEGIN
    insert into students (sid, firstname, lastname, status, gpa, email)
    values (si, fna, lna, sts, gp, em);
    open res for select * from students where sid = si;
  END;

  PROCEDURE get_student_classes (
    sid_in IN students.sid%type,
    res_student OUT SYS_REFCURSOR,
    res_classes OUT SYS_REFCURSOR,
    err OUT varchar2)
  AS
  sid char(4); fna varchar2(15); lna varchar2(15);
  sta varchar2(10); gpa number; eml varchar2(20);
  CLASSID_ CHAR(5); DEPT_CODE_ VARCHAR2(4); COURSE_NO_ NUMBER(3); SECT_NO_ NUMBER(2);
  YEAR_ NUMBER(4); SEMESTER_ VARCHAR2(6); LIMIT_ NUMBER(3); CLASS_SIZE_ NUMBER(3);
  BEGIN
    open res_student for select * from students where sid = sid_in;
    open res_classes for select * from classes where CLASSID in (
      select CLASSID from enrollments where sid = sid_in
    );

    fetch res_student into sid, fna, lna, sta, gpa, eml;
    if res_student%notfound then
      err := 'The sid is invalid';
    else
      fetch res_classes into CLASSID_, DEPT_CODE_, COURSE_NO_, SECT_NO_, YEAR_, SEMESTER_, LIMIT_, CLASS_SIZE_;
      if res_classes%notfound then
        err := 'The student doesnt have any classes.';
      end if;
    end if;

    open res_student for select * from students where sid = sid_in;
    open res_classes for select * from classes where CLASSID in (
      select CLASSID from enrollments where sid = sid_in
    );
  END;

  PROCEDURE get_prerequisite (
    dc IN PREREQUISITES.dept_code%type,
    cn IN PREREQUISITES.course_no%type,
    res_classes OUT SYS_REFCURSOR
  )
  AS
  BEGIN
    open res_classes for select PRE_DEPT_CODE, PRE_COURSE_NO from PREREQUISITES
    start with dept_code = dc and course_no = cn
    CONNECT BY PRIOR PRE_DEPT_CODE = DEPT_CODE and PRIOR PRE_COURSE_NO = COURSE_NO;
  END;

  PROCEDURE get_enrolledOf (
    cid_in IN classes.classid%type,
    class OUT SYS_REFCURSOR,
    students OUT SYS_REFCURSOR,
    err OUT varchar2)
  AS
  cid CHAR(5); dpc VARCHAR2(4); cnb NUMBER(3); sen NUMBER(2);
  yea NUMBER(4); sem VARCHAR2(6);lim NUMBER(3); csi NUMBER(3);
  sid char(4); fna varchar2(15); lna varchar2(15);
  sta varchar2(10); gpa number; eml varchar2(20);
  BEGIN
    open class for select * from classes where CLASSID = cid_in;
    open students for select * from students where sid in (
      select sid from enrollments where enrollments.CLASSID = cid_in);
    fetch class into cid, dpc, cnb, sen, yea, sem, lim, csi;


    if class%notfound then
      err := 'The cid is invalid.';
    else
      fetch students into sid, fna, lna, sta, gpa, eml;
      if students%notfound then
        err := 'No student taking this class.';
      end if;
    end if;

    open class for select * from classes where CLASSID = cid_in;
    open students for select * from students where sid in (
      select sid from enrollments where enrollments.CLASSID = cid_in);
  END;

  PROCEDURE enroll_std2cla (
    sid_in IN students.SID%type,
    cid_in IN classes.CLASSID%type,
    err OUT varchar2)
  AS
  cursor c_std is select * from students where students.sid = sid_in;
  a_std c_std%rowtype;
  cursor c_cla is select * from classes where classes.CLASSID = cid_in;
  a_cla c_cla%rowtype;
  cursor c_enr is select * from enrollments where enrollments.sid = sid_in and enrollments.CLASSID = cid_in;
  a_enr c_enr%rowtype;
  cursor c_allcla is select * from classes where CLASSID in (select CLASSID from enrollments where enrollments.sid = sid_in);
  a_allcla c_allcla%rowtype;
  count_cla PLS_INTEGER;
  ok_to_register BOOLEAN;
  BEGIN
  if not c_std%isopen then open c_std;
  end if;
  fetch c_std into a_std;
  if c_std%found then
    if not c_cla%isopen then open c_cla;
    end if;
    fetch c_cla into a_cla;
    if c_cla%found then
      if a_cla.limit <= a_cla.class_size then
        err := 'The class is closed.';
      else
        if not c_enr%isopen then open c_enr;
        end if;
        fetch c_enr into a_enr;
        if c_enr%found then
        err := 'The student is already in the class.';
        else
          -- check if enrolled in more than two classes in the same semester of a year
          if not c_allcla%isopen then open c_allcla;
          end if;
          fetch c_allcla into a_allcla;
          count_cla := 0;
          while c_allcla%found loop
            if a_cla.semester = a_allcla.semester and a_cla.year = a_allcla.year then
            count_cla := count_cla +1;
            end if;
            fetch c_allcla into a_allcla;
          end loop;
          close c_allcla;
          if count_cla >= 3 then
            err := 'Students cannot be enrolled in more than three classes in the same semester.';
          elsif count_cla = 2 then
            err := 'You are overloaded.';
            ok_to_register := TRUE;
            prerequisite_ok(sid_in, a_cla.dept_code, a_cla.course_no, ok_to_register);
            if ok_to_register then
              insert into enrollments values (sid_in, cid_in, ' ');
              err := 'Done with enrolling.';
            else
              err := 'Not fulfilled prerequisites.';
            end if;
          else
            ok_to_register := TRUE;
            prerequisite_ok(sid_in, a_cla.dept_code, a_cla.course_no, ok_to_register);
            if ok_to_register then
              insert into enrollments values (sid_in, cid_in, ' ');
              err := 'Done with enrolling.';
            else
              err := 'Not fulfilled prerequisites.';
            end if;

          end if;
        end if;
        close c_enr;
      end if;
    else err := 'The classid is invalid.';
    end if;
    close c_cla;
  else err := 'The sid is invalid.';
  end if;
  close c_std;
  END;


  PROCEDURE prerequisite_ok(
    sid_in IN students.sid%type,
    deptcode_in IN courses.dept_code%type,
    course_no_in IN courses.course_no%type,
    all_pre_ok OUT BOOLEAN)
  AS
  cursor all_pre is select pre_dept_code, pre_course_no from PREREQUISITES
  start with dept_code = deptcode_in and course_no = course_no_in
  CONNECT BY PRIOR pre_dept_code = dept_code and PRIOR pre_course_no = course_no;
  one_pre all_pre%rowtype;

  cursor all_taken is select * from
  classes join (select * from enrollments where sid = sid_in) e on classes.CLASSID = e.CLASSID;
  one_taken all_taken%rowtype;

  found BOOLEAN;
  BEGIN
    all_pre_ok := TRUE;
    open all_pre;
    fetch all_pre into one_pre;
    while all_pre%found loop
      open all_taken;
      found := FALSE;
      fetch all_taken into one_taken;

      while all_taken%found loop
        if one_taken.dept_code = one_pre.pre_dept_code and one_taken.course_no = one_pre.pre_course_no and
          (one_taken.LGRADE = 'A' OR one_taken.LGRADE = 'B' OR one_taken.LGRADE = 'C') then
           found := TRUE;
        end if;
        fetch all_taken into one_taken;
      end loop;

      if not found then
        all_pre_ok := FALSE; EXIT;
      end if;
      close all_taken;
      fetch all_pre into one_pre;
    end loop;
    close all_pre;
  END;

  PROCEDURE drop_class (
    sid_in IN students.sid%type,
    cid_in IN classes.CLASSID%type,
    mes OUT varchar2)
  AS
  cursor c_std is select sid from students where sid = sid_in;
  cursor c_cla is select * from classes where CLASSID = cid_in;
  a_std c_std%rowtype;
  a_cla c_cla%rowtype;
  cursor c_enr is select * from enrollments where sid = sid_in and CLASSID = cid_in;
  a_enr c_enr%rowtype;
  cursor allothercla is select * from enrollments where sid = sid_in;
  anothercla allothercla%rowtype;
  cursor allotherstd is select * from enrollments where classid = cid_in;
  anotherstd allotherstd%rowtype;
  drop_ok BOOLEAN;
  BEGIN
    open c_std;
    open c_cla;
    open c_enr;
    fetch c_std into a_std;
    fetch c_cla into a_cla;
    fetch c_enr into a_enr;
    if not c_std%found then
      mes := 'The sid is invalid.';
    else
      if not c_cla%found then
        mes := 'The classid is invalid.';
      else
        if not c_enr%found then
           mes := 'The student is not enrolled in the class';
        else
          drop_ok := TRUE;
          ok_to_drop(sid_in, a_cla.dept_code, a_cla.course_no, drop_ok);
          if drop_ok then
            -- drop the class
            delete from enrollments where CLASSID = cid_in and SID = sid_in;
            open allothercla;
            fetch allothercla into anothercla;
            open allotherstd;
            fetch allotherstd into anotherstd;
            if allothercla%notfound then
              if allotherstd%notfound then
                mes := 'The student is not enrolled in any class.\n The class has no student';
              else
                mes := 'The student is not enrolled in any class.';
              end if;
            else
              if allotherstd%notfound then
                mes := 'The class has no student';
              else
                mes := 'Done with dropping.';
              end if;
            end if;
            close allothercla;
            close allotherstd;
          else
            mes:= 'The drop is not permitted because another class uses it as a prerequisite.';
          end if;
        end if;
      end if;
    end if;
    close c_std;
    close c_cla;
    close c_enr;
  END;

  PROCEDURE ok_to_drop (
    sid_in IN students.sid%type,
    deptcode_in IN classes.dept_code%type,
    course_no_in IN classes.course_no%type,
    res OUT BOOLEAN)
  AS
  cursor all_pos is select dept_code, course_no from PREREQUISITES
  start with pre_dept_code = deptcode_in and pre_course_no = course_no_in
  connect by PRIOR dept_code = pre_dept_code and PRIOR course_no = pre_course_no;
  one_pos all_pos%rowtype;

  cursor all_taken is select dept_code, course_no from
  classes where CLASSID in (select CLASSID from enrollments where sid = sid_in);
  one_taken all_taken%rowtype;

  found BOOLEAN;
  BEGIN
    res := TRUE;
    open all_pos;
    fetch all_pos into one_pos;
    while all_pos%found loop
        open all_taken;
        fetch all_taken into one_taken;
        while all_taken%found loop
          if one_taken.dept_code = one_pos.dept_code and one_taken.course_no = one_pos.course_no then
            res := FALSE; EXIT;
          end if;
          fetch all_taken into one_taken;
        end loop;
        close all_taken;
      fetch all_pos into one_pos;
    end loop;
    close all_pos;
  END;

  PROCEDURE delete_student (
    sid_in IN students.SID%type,
    mes OUT VARCHAR2)
  AS
  cursor c_std is select * from students where sid = sid_in;
  a_std c_std%rowtype;
  BEGIN
    open c_std;
    fetch c_std into a_std;
    if c_std%found then
      delete from students where sid = sid_in;
      mes := 'Done with deletion.';
    else
      mes := 'The sid is invalid.';
    end if;
    close c_std;
  END;

END;
/

SHOW ERROR
