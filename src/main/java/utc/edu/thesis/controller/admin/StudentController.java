package utc.edu.thesis.controller.admin;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utc.edu.thesis.common.ExcelCM;
import utc.edu.thesis.domain.dto.SearchDto;
import utc.edu.thesis.domain.dto.StudentDto;
import utc.edu.thesis.domain.entity.Student;
import utc.edu.thesis.domain.entity.StudentClass;
import utc.edu.thesis.exception.request.BadRequestException;
import utc.edu.thesis.service.FacultyService;
import utc.edu.thesis.service.StudentClassService;
import utc.edu.thesis.service.StudentService;
import utc.edu.thesis.util.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final FacultyService facultyService;
    private final StudentClassService classService;

    @PostMapping("/add-student")
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.addStudent(dto));
    }

    @PostMapping("/delete-student/{id}")
    public ResponseEntity<Boolean> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PostMapping("/edit-student")
    public ResponseEntity<StudentDto> editStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.editStudent(dto));
    }

    @PostMapping("/get-student")
    public ResponseEntity<List<StudentDto>> getStudent(@RequestBody SearchDto payload) {
        return ResponseEntity.ok(studentService.getStudent(payload));
    }

    @PostMapping(value = "/import-student", consumes = {"multipart/form-data"})
    public ResponseEntity<List<StudentDto>> addStudent(@RequestParam("fileExcel") MultipartFile fileExcel) {
        List<StudentDto> res = new ArrayList<>();
        try {
            JSONArray dataExcel = ExcelCM.readExcelAll(fileExcel);
            for (Object dataOne : dataExcel) {
                JSONArray dataOneArr = (JSONArray) dataOne;
                String[] sClass = dataOneArr.getString(2).split("\\.");
                Integer course = Integer.valueOf(sClass[0].substring(1));
                StudentClass studentClass = classService.findByNameAndCourse(sClass[1], course);

                Student student = Student.builder()
                        .code(dataOneArr.getString(0))
                        .fullName(dataOneArr.getString(1))
                        .studentClass(studentClass)
                        .gender(Integer.parseInt(dataOneArr.getString(3)))
                        .phone(dataOneArr.getString(4))
                        .dob(DateUtil.toLocalDate(dataOneArr.getString(5)))
                        .email(dataOneArr.getString(6))
                        .address(dataOneArr.getString(7))
                        .build();

                try {
                    res.add(studentService.addStudent(StudentDto.of(student)));
                } catch (BadRequestException e) {
                    res.add(studentService.editStudent(StudentDto.of(student)));
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(res);
    }
}
