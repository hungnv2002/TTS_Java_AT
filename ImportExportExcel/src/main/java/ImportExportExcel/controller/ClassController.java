package ImportExportExcel.controller;

import ImportExportExcel.exception.ClassImportException;
import ImportExportExcel.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassService classService;

    // Nhập lớp học từ file Excel
    @PostMapping("/import")
    public ResponseEntity<List<String>> importClasses(@RequestParam("file") MultipartFile file) {
        try {
            List<String> errors = classService.importClassesFromExcel(file);
            return ResponseEntity.ok(errors);
        } catch (ClassImportException e) {
            throw e;
        } catch (IOException e) {

            throw new RuntimeException("Error reading the file", e);
        }
    }
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportClassesToExcel() {
        try {
            byte[] excelData = classService.exportClassesToExcel();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "classes.xlsx");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelData);
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(("Error exporting classes to Excel: " + e.getMessage()).getBytes());
        }
    }
    @GetMapping("/export-errors")
    public ResponseEntity<byte[]> exportClassesErrorToExcel() throws IOException {
        byte[] excelFile = classService.exportClassesErrorToExcel();

        // Thiết lập các header cho file Excel (ví dụ: Content-Type, Content-Disposition)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=class_errors.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelFile, headers, HttpStatus.OK);
    }
}
