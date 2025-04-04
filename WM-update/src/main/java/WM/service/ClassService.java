package WM.service;

import WM.dto.request.PostCreateClassInforRequestBody;
import WM.dto.response.PostCreateClassInforResponseBody;
import WM.exception.VmException;
import WM.exception.VmListException;
import jakarta.servlet.http.HttpServletRequest;


import java.io.IOException;
import java.util.List;

public interface ClassService {

    PostCreateClassInforResponseBody postCreateClassInfo(PostCreateClassInforRequestBody requestBody, HttpServletRequest request) throws VmException, IOException;
//    ClassDTO updateClass(int classId, ClassDTO classDTO);
//    void deleteClass(int classId);
//    List<ClassEntity> searchClasses(String name, String code, int page, int size);
//    List<ClassEntity> getAllClasses(int page, int size);
}
