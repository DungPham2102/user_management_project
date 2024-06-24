package com.springsecuritywithjwt.springsecuritywithjwt.controller;


import com.springsecuritywithjwt.springsecuritywithjwt.dto.SearchUserDto;
import com.springsecuritywithjwt.springsecuritywithjwt.entity.User;
import com.springsecuritywithjwt.springsecuritywithjwt.service.ImportExportService;
import com.springsecuritywithjwt.springsecuritywithjwt.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Admin", description = "Admin API")
public class AdminController {

    private final UserService userService;
    private final ImportExportService importExportService;


    // cái annotation này để tùy chỉnh lại swagger được hiển thị trong UI
//    @Operation(
//            description = "Get endpoint for admin",
//            summary = "Summary for admin get endpoint",
//            responses = {
//                    @ApiResponse(
//                            description = "Success",
//                            responseCode = "200"
//                    ),
//                    @ApiResponse(
//                            description = "Unauthorize / Invalid Token",
//                            responseCode = "403"
//                    )
//            }
//    )

    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    // hiện ra user theo id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(params = "name")
    public ResponseEntity<Page<User>> getUserByName(@RequestParam(required = false) String name,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getUserByName(name, pageable);
        return ResponseEntity.ok(users);
    }

    // update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // @Hidden sẽ ẩn đi API này trong swagger UI
//    @Hidden
    // xóa user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // export dữ liệu user ra file excel
    @GetMapping("/download")
    public void exportToExcel(HttpServletResponse response) throws Exception {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=users.xls";

        response.setHeader(headerKey, headerValue);

        importExportService.generateExcel(response);

    }

    // import dữ liệu từ file excel vào database
    @PostMapping("/upload")
    public void importFromExcel(HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=status.xls";

        response.setHeader(headerKey, headerValue);

        userService.saveCustomerToDatabaseFromExcel(response, file);

    }

    // filter các bản ghi
    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUser(@RequestBody SearchUserDto searchUserDto,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.searchUser(searchUserDto, pageable);
        return ResponseEntity.ok(users);
    }

}
