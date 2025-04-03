package WM.service;

import WM.dto.request.CreateTeacherInforRequestBody;
import WM.dto.request.PostCreateTeacherInfoByFileRequest;
import WM.dto.request.PostCreateTeacherInfoRequestBody;
import WM.dto.response.CreateTeacherInforRespone;
import WM.dto.response.PostCreateClassInfoByFileResponse;
import WM.dto.response.PostCreateTeacherInfoResponse;
import WM.exception.BadRequestException;
import WM.exception.VmException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface TeacherService {
    CreateTeacherInforRespone postCreateTeacherInfo(@Valid CreateTeacherInforRequestBody requestBody, HttpServletRequest httpServletRequest) throws IOException, BadRequestException;
   // deleteTeacher(int teacherId);
//    Page<TeacherDTO> searchTeacher(String name, int page, int size);
  Page<PostCreateTeacherInfoResponse> getAllTeachers(Pageable pageable);
   PostCreateClassInfoByFileResponse importTeachersFromExcel(PostCreateTeacherInfoByFileRequest request, HttpServletRequest httpServletRequest) throws IOException, VmException;
}
