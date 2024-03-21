package megamaker.jpapractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import megamaker.jpapractice.domain.Role;
import megamaker.jpapractice.domain.Student;
import megamaker.jpapractice.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.util.Map;

@Slf4j
@RestController
public class TestController {

    // 쿼리 파라미터
    @GetMapping("/qTest1")
    public String qTest1(@RequestParam(value = "name") String n, @RequestParam(value = "age") int a) {
        log.info("name={}, age={}", n, a);
        return "test";
    }

    @GetMapping("/qTest2")
    public String qTest2(@RequestParam String name, @RequestParam int age) {
        log.info("name={}, age={}", name, age);
        return "test";
    }

    @GetMapping("/qTest3")
    public String qTest3(String name, int age) {  // @RequestParam 생략
        log.info("name={}, age={}", name, age);
        return "test";
    }

    @GetMapping("/qTest4")
    public String qTest4(HttpServletRequest request) {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("name={}, age={}", name, age);
        return "test";
    }

    @GetMapping("/qTest5")
    public String qTest5(@RequestParam Map<String, String> paramMap) {
        String name = paramMap.get("name");
        int age = Integer.parseInt(paramMap.get("age"));
        log.info("name={}, age={}", name, age);
        return "test";
    }

    // post form
    @PostMapping("/fTest1")
    public String fTest1(@RequestParam(value = "name") String n, @RequestParam(value = "age") int a) {
        log.info("name={}, age={}", n, a);
        return "test";
    }

    @PostMapping("/fTest2")
    public String fTest2(@RequestParam String name, @RequestParam int age) {
        log.info("name={}, age={}", name, age);
        return "test";
    }

    @PostMapping("/fTest3")
    public String fTest3(String name, int age) {  // @RequestParam 생략
        log.info("name={}, age={}", name, age);
        return "test";
    }

    // HTTP body
    @PostMapping("/hTest1")
    public String hTest1(HttpServletRequest request) throws IOException {
        Student student = new ObjectMapper().readValue(request.getInputStream(), Student.class);
        log.info("name={}, age={}", student.getName(), student.getAge());
        return "test";
    }

    @PostMapping("/hTest2")
    public String hTest2(InputStream body) throws IOException {
        Student student = new ObjectMapper().readValue(body, Student.class);
        log.info("name={}, age={}", student.getName(), student.getAge());
        return "test";
    }

    @PostMapping("/hTest3")
    public String hTest3(@RequestBody Student student) {
        log.info("name={}, age={}", student.getName(), student.getAge());
        return "test";
    }

    // 응답

    @PostMapping("/rTest1")
    public void rTest1(@RequestBody Student student, HttpServletResponse response) throws IOException {
        log.info("name={}, age={}", student.getName(), student.getAge());
        response.getWriter().write("test");
    }

    @ResponseBody
    @PostMapping("/rTest2")
    public String rTest2(@RequestBody Student student) {
        log.info("name={}, age={}", student.getName(), student.getAge());
        return "test";
    }

    @ResponseBody
    @PostMapping("/rTest3")
    public Student rTest3(@RequestBody Student student) {  // 직접 정의한 객체 리턴 가능
        log.info("name={}, age={}", student.getName(), student.getAge());
        return student;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/rTest4")
    public String rTest4(@RequestBody Student student) {
        log.info("name={}, age={}", student.getName(), student.getAge());
        return "test";
    }

    @PostMapping("/rTest5")
    public ResponseEntity<String> rTest5(@RequestBody Student student) {
        log.info("name={}, age={}", student.getName(), student.getAge());
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @PostMapping("/rTest6")
    public ResponseEntity<Student> rTest6(@RequestBody Student student) {
        log.info("name={}, age={}", student.getName(), student.getAge());
        return ResponseEntity.ok(student);
    }
}
