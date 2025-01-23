package school.service;

import school.dto.ClassDTO;
import school.entity.ClassEntity;

import java.util.List;

public interface ClassService {

    ClassDTO updateClass(int classId, ClassDTO classDTO);
    void deleteClass(int classId);
    List<ClassEntity> searchClasses(String name, String code, int page, int size);
    List<ClassEntity> getAllClasses(int page, int size);
}
