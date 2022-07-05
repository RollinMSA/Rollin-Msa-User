package com.rollin.service;



import com.rollin.model.UserEntity;
import com.rollin.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Boolean insertUser(UserEntity userEntity) {
        UserEntity new_user = userRepository.save(userEntity);
        if (new_user.getId() != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long idCheck(UserEntity userEntity) {
        return userRepository.countByUserId(userEntity.getUserId());
    }

    @Override
    public Optional<UserEntity> LoginCheck(UserEntity userEntity) {
        return userRepository.findByUserIdAndPassword(userEntity.getUserId(), userEntity.getPassword());
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserEntity> kaoCheck(UserEntity userEntity) {
        return userRepository.findByUserId(userEntity.getUserId());
    }

    @Override
    public Optional<UserEntity> serviceLogin(UserEntity userEntity) {
        return userRepository.findByUserIdAndPassword(userEntity.getUserId(), userEntity.getPassword());
    }

    @Override
    public List<UserEntity> findReceiversNotUserId(Integer id) {
        return userRepository.findAllByNotId(id);
    }
}
