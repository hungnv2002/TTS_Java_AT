package WM.dto.request;

import WM.dto.TeacherCommon;

public class PostCreateTeacherInfoRequestBody extends TeacherCommon {
    public PostCreateTeacherInfoRequestBody(String teacherCode, String name) {
        super(teacherCode, name);
    }

    public PostCreateTeacherInfoRequestBody(int id, String teacherCode, String name) {
        super(id, teacherCode, name);
    }
}
