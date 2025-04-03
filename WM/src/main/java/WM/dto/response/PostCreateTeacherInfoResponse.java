package WM.dto.response;

import WM.dto.TeacherCommon;

public class PostCreateTeacherInfoResponse extends TeacherCommon {
    public PostCreateTeacherInfoResponse(String teacherCode, String name) {
        super(teacherCode, name);
    }

    public PostCreateTeacherInfoResponse(int id, String teacherCode, String name) {
        super(id, teacherCode, name);
    }
}
