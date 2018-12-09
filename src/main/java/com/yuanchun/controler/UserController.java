package com.yuanchun.controler;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanchun.common.BaseClass;
import com.yuanchun.common.BaseDTO;
import com.yuanchun.dao.UserMapper;
import com.yuanchun.entry.User;
import com.yuanchun.vo.UserVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @GetMapping = @RequestMapping(method = RequestMethod.GET)
 * @PostMapping = @RequestMapping(method = RequestMethod.POST)
 * @PutMapping = @RequestMapping(method = RequestMethod.PUT)
 * @DeleteMapping = @RequestMapping(method = RequestMethod.DELETE)
 */


@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseClass {

    @Resource
    private UserMapper userMapper;

    /**
     * 分页查询用户集合
     * http://localhost:8080/user/getPage
     * 需要注意的是PageHelper是从1开始分页，而Hibernate/Jpa是从0开始分页的
     *
     * @return
     */
    @PostMapping("/getPage")
//    @Transactional(readOnly = true)
    public BaseDTO getPage(@RequestBody UserVo userVo) {
        logger.debug("============================getPage=========================");
        BaseDTO baseDTO = new BaseDTO();

        /**
         * 分页并查询
         * Mapper接口方式的调用，推荐这种使用方式。
         */
//        PageHelper.startPage(1,3);
//        List<User> users = userMapper.selectListUser();
//        PageInfo pageInfo = new PageInfo(users);
//        System.out.println("==============================getPage.pageInfo==========================" + pageInfo);

        /**
         * 分页并查询
         * jdk6,7用法，创建接口
         */
//        PageInfo<User> pageInfo = PageHelper.startPage(0, 3,true).doSelectPageInfo(new ISelect() {
//            @Override
//            public void doSelect(){
//                userMapper.selectListUser();
//            }
//        });
//        System.out.println("==============================getPage.pageInfo==========================" + pageInfo);

//        PageInfo<User> pageInfo = baseMapper.selectPage(
//                "com.yuanchun.dao.UserMapper.selectListUser", userVo,
//                userVo.getIndex(), Const.PER_PAGE_COUNT);
//        System.out.println("==============================getPage.pageInfo1==========================" + pageInfo);

        /**
         * 分页并查询
         * jdk8 lambda用法
         */

        PageInfo<User> pageInfo = PageHelper.startPage(1, 5).doSelectPageInfo(() -> userMapper.selectListUser());
        System.out.println("==============================getPage.pageInfo==========================" + pageInfo);

        //获取分页信息演示, 实际项目中一般会封装为自己的返回体。
        baseDTO.setData(pageInfo);
        return baseDTO;
    }

   /**
     * 保存单个用户
     * http://localhost:8080/user/save
     * {"nickName":"kingboy","age":"26","sex":"BOY","phoneNumber":"13132296607","birthday":"2011-12-12 12:12","status":"UNLOCK"}
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/save")
    public BaseDTO save(@RequestBody User user) {
        BaseDTO baseDTO = new BaseDTO();
        userMapper.saveUser(user);
        String ex = null;
        ex.toString();
        baseDTO.setData(user);
        return baseDTO;
    }

    /**
     * 批量保存
     * http://localhost:8080/user/saveList
     * [
     * {"nickName":"kingboy","age":"26","sex":"BOY","phoneNumber":"13132296607","birthday":"2011-12-12 12:12","status":"UNLOCK"},
     * {"nickName":"kingboy","age":"26","sex":"BOY","phoneNumber":"13132296607","birthday":"2011-12-12 12:12","status":"UNLOCK"},
     * {"nickName":"kingboy","age":"26","sex":"BOY","phoneNumber":"13132296607","birthday":"2011-12-12 12:12","status":"UNLOCK"}
     * ]
     *
     * @param users
     * @return
     */
    @PostMapping(value = "/saveList")
    public BaseDTO saveList(@RequestBody List<User> users) {
        logger.debug("=======saveList=======");
        BaseDTO baseDTO = new BaseDTO();
        userMapper.saveUserList(users);
        baseDTO.setData(users);
        return baseDTO;
    }

    /**
     * 更新单个用户
     * http://localhost:8080/user/update
     * {"id":"1","nickName":"kingboy","age":"26","sex":"BOY","phoneNumber":"13132296607","birthday":"2011-12-12 12:12","status":"UNLOCK"}
     *
     * @return
     */
    @PutMapping(value = "/update")
    public BaseDTO update(@RequestBody User user) {
        logger.debug("=======update=======");
        BaseDTO baseDTO = new BaseDTO();
        userMapper.updateUser(user);
        baseDTO.setData(user);
        return baseDTO;
    }

    /**
     * 删除用户
     * http://localhost:8080/user/remove/1
     *
     * @return
     * @Pathvariable注解可以绑定占位符传过来的值到方法的参数上。
     */
    @DeleteMapping("/remove/{id}")
    public BaseDTO remove(@PathVariable String id) {
        logger.debug("=======remove=======");
        BaseDTO baseDTO = new BaseDTO();
        //软删除
        userMapper.remove(id, "LOCK");
        //硬删除
        //userMapper.delete(id);
        baseDTO.setData(id);
        return baseDTO;
    }

    /**
     * 通过ID获取用户
     * http://localhost:8080/user/get/1
     *
     * @return
     */
    @GetMapping("/get/{id}")
    public BaseDTO get(@PathVariable Long id) {
        logger.debug("=======get=======");
        BaseDTO baseDTO = new BaseDTO();
        User user = userMapper.get(id);
        baseDTO.setData(user);
        return baseDTO;
    }

    /**
     * 通过id集合查询用户,这里就不做分页了。
     * http://localhost:8080/user/ids
     * [1,3]
     *
     * @return
     *//*
    @PostMapping("/ids")
    public BaseDTO getUserByIds(@RequestBody List<String> ids) {
        BaseDTO baseDTO = new BaseDTO();
        List<User> users = userMapper.listUserByIds(ids);
        baseDTO.setData(users);
        return baseDTO;
    }

    /**
     * 通过查询条件赖查询用户，这里也不做分页了
     * http://localhost:8080/user/query
     * {
     * "nickName":"i",
     * "fromBirthday":"1999-12-31 12:12",
     * }
     *
     * @param user
     * @return
     */
    @PostMapping("/query")
    public BaseDTO queryUser(@RequestBody User user) {
        BaseDTO baseDTO = new BaseDTO();
        List<User> users = userMapper.queryByCondition(user);
        baseDTO.setData(users);
        return baseDTO;
    }

    /**
     * 根据条件的顺序来查询用户
     * http://localhost:8080/user/query?age=24
     * 演示choose标签的用法:如果传入age就按年龄查询用户，age没有传入就按照sex赖查询用户。如果两者都没有传入，那就查询所有status没有冻结的用户
     * 类似如下：
     * switch(value) {
     * case age:
     * //查询age
     * break;
     * case sex:
     * //查询sex
     * break;
     * default:
     * //查询status
     * break;
     * }
     */
    @GetMapping("/query")
    public BaseDTO findUserByOrderCondition(@RequestParam(required = false) String age, @RequestParam(required = false) String sex) {
        BaseDTO baseDTO = new BaseDTO();
        List<User> users = userMapper.getByOrderCondition(age, sex);
        baseDTO.setData(users);
        return baseDTO;
    }


}
