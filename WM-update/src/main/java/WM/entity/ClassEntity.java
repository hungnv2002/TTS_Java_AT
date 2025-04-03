package WM.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ClassEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column( unique = true)
        private String classCode;

        @Column()
        private String name;

        @ManyToOne // Thay @OneToOne thành @ManyToOne nếu nhiều lớp có thể có cùng một giáo viên
        @JoinColumn(name = "teacher_code", referencedColumnName = "teacherCode") // Liên kết đến teacherCode của Teacher
        private Teacher teacher;

        @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Student> students;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClassCode() {
            return classCode;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Teacher getTeacher() {
            return teacher;
        }

        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }

        public List<Student> getStudents() {
            return students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
        }
}


