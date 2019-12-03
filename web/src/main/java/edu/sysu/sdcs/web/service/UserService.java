package edu.sysu.sdcs.web.service;

import edu.sysu.sdcs.web.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserService {
    @Resource
    UserRepo userRepo;


}
