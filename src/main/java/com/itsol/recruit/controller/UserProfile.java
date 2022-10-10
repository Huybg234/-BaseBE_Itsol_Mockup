package com.itsol.recruit.controller;


import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import com.itsol.recruit.service.mapper.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.soap.SOAPBinding;

@RestController
@RequestMapping("/api/public/user/user-profile")
public class UserProfile {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IStorageService storageSevice;


//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable("id") long id){
//        User user=userService.findById(id);
//
//
//        if(user==null){
//
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//
//        }
//        else {
//            user.setPassword(null);
//            return ResponseEntity.ok(user);
//        }
//    }

    @GetMapping(value="/{userName}")
    public ResponseEntity<User> findUserByUserName(@PathVariable String userName){
        User user;
        try {
            user=userService.findUserByUserName(userName);
        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }


       return ResponseEntity.ok(user);
    }

    @PutMapping()
    public ResponseEntity<String> updateUserProfile(@RequestBody User updateUser){
        try {
            userService.save(updateUser);
            return ResponseEntity.status(HttpStatus.OK).body("UPDATE THÀNH CÔNG");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy user");
        }

    }



    @PutMapping("/new-pass")
    public ResponseEntity<String> updateUserPass(@RequestBody User updateUser){
        try {

            updateUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            userService.save(updateUser);
            return ResponseEntity.status(HttpStatus.OK).body("UPDATE PASS THÀNH CÔNG");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy user");
        }
    }

    //upload image

    @PostMapping("new-avatar/{id}")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable long id) {
        try {
            String generatedFileName = storageSevice.storeFile(file);
            int capNhat = userService.updateUserAvatarName(generatedFileName,id);
            System.out.println(generatedFileName);
            return  ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok","Thanh cong", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("false",e.getMessage(),""));
        }
    }

    @GetMapping("avatar/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName){
        try{
            System.out.println("haha");
            byte[] bytes = storageSevice.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        }catch (Exception e){
            return ResponseEntity.noContent().build();
        }
    }
}
