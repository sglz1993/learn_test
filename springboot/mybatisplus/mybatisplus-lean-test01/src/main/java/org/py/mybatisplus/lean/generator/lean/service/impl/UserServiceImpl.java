package org.py.mybatisplus.lean.generator.lean.service.impl;

import org.py.mybatisplus.lean.generator.lean.entity.User;
import org.py.mybatisplus.lean.generator.lean.mapper.UserMapper;
import org.py.mybatisplus.lean.generator.lean.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pengyue.du
 * @since 2020-11-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
