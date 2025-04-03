package WM.service;

import WM.dto.request.PostCreateTeacherInfoByFileRequest;
import WM.dto.request.PostCreateTeacherInfoRequestBody;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.exception.VmException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    PostCreateTeacherInfoResponse postCreateTeacherInfo(PostCreateTeacherInfoRequestBody requestBody, HttpServletRequest httpServletRequest) throws VmException, IOException;
   // deleteTeacher(int teacherId);
//    Page<TeacherDTO> searchTeacher(String name, int page, int size);
//    List<TeacherDTO> getAllTeachers(int page, int size);
   PostCreateClassInfoByFileResponse importTeachersFromExcel(PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest) throws IOException, VmException;
}
