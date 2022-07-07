package com.rollin.controller;


import com.rollin.aspect.TokenRequired;
import com.rollin.config.SecurityService;
import com.rollin.model.UserDto;
import com.rollin.model.UserEntity;
import com.rollin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Service
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserDto userDto;
    @GetMapping
    public List<UserEntity> getUser() {
        return userService.getAllUser();
    }
    @GetMapping("/{id}")
    public Optional<UserEntity> getUserById(@PathVariable String id){
        return userService.getUserById(Integer.valueOf(id));
    }

    @GetMapping("/me")
    @TokenRequired
    public Optional<UserEntity> getUserByMe(){
        Integer id= securityService.getIdAtToken();
        return userService.getUserById(id);
    }

    @PostMapping
    public Boolean insert(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity);
    }

    @PostMapping("/Id")
    public long idCheck(@RequestBody UserEntity userEntity){

        return userService.idCheck(userEntity);
    }

    @PostMapping("/login")
    public Optional<UserEntity> LoginCheck(@RequestBody  UserEntity userEntity){
        return userService.LoginCheck(userEntity);
    }

    @PostMapping("/kaologin")
    public List<UserEntity> kaoCheck(@RequestBody UserEntity userEntity){
        return userService.kaoCheck(userEntity);
    }

    @PostMapping("/Login")
    public Optional<UserEntity> loginUser(@RequestBody UserEntity userEntity){

        Optional<UserEntity> returnUser =userService.serviceLogin(userEntity); //쿼리 입력 후 결과값
        log.info(String.valueOf(returnUser));
        returnUser.ifPresent(selectUser->{
            selectUser.setUserId(selectUser.getUserId());
            selectUser.setId(selectUser.getId());
            selectUser.setName(selectUser.getName()); //반값으로 형성된 곳에 하나씩 채워진다.
            selectUser.setImg(selectUser.getImg());
            selectUser.setPcnt(selectUser.getPcnt());
            userDto.setId(selectUser.getId());
            userDto.setName(selectUser.getName());
            userDto.setImg(selectUser.getImg());
            log.info("idididididid: "+userDto.getId().toString());
            String token = securityService.createToken(userDto); //받아온 값을 셋토큰에 넣어준다.
            selectUser.setToken(token);
        });
        return returnUser;

//        loginDto.setToken(returnDto.getToken());



// =======
//     public LoginDto loginUser(@RequestBody UserDto userDto){
//         UserDto returnDto =userService.serviceLogin(userDto); //쿼리 입력 후 결과값
//         LoginDto loginDto = new LoginDto();
//         loginDto.setUserId(returnDto.getUserId());
//         loginDto.setId(returnDto.getId());
//         loginDto.setName(returnDto.getName()); //반값으로 형성된 곳에 하나씩 채워진다.
//         loginDto.setImg(returnDto.getImg());
//         loginDto.setPcnt(returnDto.getPcnt());
// //        loginDto.setToken(returnDto.getToken());
//         String token = securityService.createToken(returnDto.getId().toString()); //받아온 값을 셋토큰에 넣어준다.
//         loginDto.setToken(token);

//         return loginDto;
// >>>>>>> gift
        //리액트에 던져준다
    }


}
