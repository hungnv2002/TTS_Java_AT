package ImportExportExcel.controller;

import ImportExportExcel.entity.Teacher;
import ImportExportExcel.service.TeacherService;
import ImportExportExcel.exception.InvalidInputException;
import ImportExportExcel.exception.TeacherImportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @PostMapping("/import")
    public ResponseEntity<List<String>> importTeachersFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<String> errorMessages = teacherService.importTeachersFromExcel(file);
            if (!errorMessages.isEmpty()) {
                return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidInputException | IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TeacherImportException e) {
            return new ResponseEntity<>(e.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportTeachersToExcel() {
        try {
            byte[] excelData = teacherService.exportTeachersToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=teachers.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/export-errors")
    public ResponseEntity<byte[]> exportTeachersErrorToExcel() {
        try {
            byte[] excelData = teacherService.exportTeachersErrorToExcel();
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=teachers-with-errors.xlsx")
                    .body(excelData);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
