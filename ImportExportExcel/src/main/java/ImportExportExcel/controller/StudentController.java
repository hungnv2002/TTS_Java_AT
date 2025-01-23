package ImportExportExcel.controller;

import ImportExportExcel.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @PostMapping("/import")
    public ResponseEntity<String> importStudentsFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            studentService.importStudentsFromExcel(file);
            return new ResponseEntity<>("File Excel đã được nhập thành công.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Lỗi khi nhập file Excel.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/export-errors")
    public ResponseEntity<byte[]> exportStudentsErrorToExcel() throws IOException {
        byte[] excelFile = studentService.exportStudentsErrorToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=student_errors.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}
