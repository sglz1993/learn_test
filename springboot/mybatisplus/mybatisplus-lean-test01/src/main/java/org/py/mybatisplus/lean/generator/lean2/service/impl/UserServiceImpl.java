package org.py.mybatisplus.lean.generator.lean2.service.impl;

import org.py.mybatisplus.lean.generator.lean2.entity.User;
import org.py.mybatisplus.lean.generator.lean2.mapper.UserMapper;
import org.py.mybatisplus.lean.generator.lean2.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyue.du
 * @since 2020-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
