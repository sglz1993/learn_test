package org.py.mybatisplus.lean.generator.lean.controller;


import com.google.common.collect.Lists;
import org.py.mybatisplus.lean.generator.lean.entity.User;
import org.py.mybatisplus.lean.generator.lean.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pengyue.du
 * @since 2020-11-24
 */
@RestController
@RequestMapping("/lean/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping("/list")
    public List<User> list() {
        return iUserService.list();
    }

//    @RequestMapping("/page")
//    public IPage<User> page() {
//        return iUserService.page(new User());
//    }

//    **************************************** save ***********************************************

    @RequestMapping("/save")
    public boolean save() {
        return iUserService.save(new User("test", 1, System.currentTimeMillis() + ""));
    }

    @RequestMapping("/bath")
    public boolean bath() {
        List<User> users = Lists.newArrayList();
        for (int i =0; i< 5; i++) {
            User test = new User("test", 1, System.currentTimeMillis() + "");
            users.add(test);
        }
        return iUserService.saveBatch(users);
    }

    @RequestMapping("/bathSize")
    public boolean bathSize() {
        List<User> users = Lists.newArrayList();
        for (int i =0; i< 5; i++) {
            User test = new User("test", 1, System.currentTimeMillis() + "");
            users.add(test);
        }
        return iUserService.saveBatch(users, 2);
    }
//    // TableId 注解存在更新记录，否插入一条记录
//    boolean saveOrUpdate(T entity);
//    // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
//    boolean saveOrUpdate(T entity, Wrapper<T> updateWrapper);
//    // 批量修改插入
//    boolean saveOrUpdateBatch(Collection<T> entityList);
//    // 批量修改插入
//    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);
//    **************************************** save ***********************************************

    @RequestMapping("/saveOrUpdate")
    public boolean saveOrUpdate() {
        iUserService.saveOrUpdate(new User("testSU", 2, System.currentTimeMillis() + ""));
        return iUserService.saveOrUpdate(new User(5L,"testSU", 2, System.currentTimeMillis() + ""));
    }

//    **************************************** saveOrUpdate ***********************************************


//    **************************************** saveOrUpdate ***********************************************

//    **************************************** remove ***********************************************

//    **************************************** remove ***********************************************

//    **************************************** update ***********************************************

//    **************************************** update ***********************************************

//    **************************************** get ***********************************************

//    **************************************** get ***********************************************

//    **************************************** list ***********************************************

//    **************************************** list ***********************************************

//    **************************************** page ***********************************************

//    **************************************** page ***********************************************

//    **************************************** count ***********************************************

//    **************************************** count ***********************************************

//    **************************************** chain ***********************************************

//    **************************************** chain ***********************************************

}

