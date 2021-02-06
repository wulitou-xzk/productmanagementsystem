package com.productmanage.serviceimpl;

import com.productmanage.bean.User;
import com.productmanage.mapper.*;
import com.productmanage.service.UserService;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LoginDetailMapper detailMapper;

    // 添加一个用户 / 员工
    @Override
    public String insertUser(User user) {
        // 添加员工前先对其初始密码加密
        user.setPassword(StringUtil.encrypt("11111"));
        if(userMapper.insertUser(user) > 0){
            return "员工" + user.getUserName() + "添加成功";
        }else{
            return "员工" + user.getUserName() + "添加失败";
        }
    }

    // 删除一个员工
    @Override
    @CacheEvict(value = "user", key = "#uId")
    public int deleteUserById(String uId) {
        return userMapper.deleteUserById(uId);
    }

    // 管理员更新员工数据
    @Override
    @CachePut(value = "user", key = "#user.uId", unless = "#result == null")
    public User updateUserByAdmin(User user) {
        if(userMapper.updateUserByAdmin(user) > 0){
            return user;
        }else{
            return null;
        }
    }

    @Override
    @CachePut(value = "user", key = "#user.uId", unless = "#result == null")
    public User editPhoneByUser(User user) {
        String oldPhone = user.getPhone().split("-")[0];
        String phone = user.getPhone().split("-")[1];
        if(userMapper.updateUserPhoneByUser(user.getUId(), user.getUserName(), oldPhone, phone) > 0){
            return userMapper.selectUserById(user.getUId());
        }else{
            return null;
        }
    }

    // 员工修改密码
    @Override
    @CachePut(value = "user", key = "#uId", unless = "#result == null")
    public User editPwdByUser(User user, String uId, String detailId) {
        String oldPwd = user.getPassword().split("-")[0];
        // 校验原密码是否输入错误
        User user1 = userMapper.selectUserById(user.getUId());
        boolean checkPwd = oldPwd.equals(StringUtil.decrypt(user1.getPassword()));
        if(checkPwd){
            String newPwd = StringUtil.encrypt(user.getPassword().split("-")[1]);
            user.setPassword(newPwd);
            if(userMapper.editPwdByUser(user.getUId(), user.getPhone(), user.getUserName(), newPwd) > 0){
                // 密码修改成功后自动退出登录，需要更新退出时间
                detailMapper.updateLoginDetail(uId, detailId);
                return user1;
            }
        }
        return null;
    }

    // 获取一条最新的员工编号
    @Override
    public String selectNewUid(Integer userLevel) {
        String userType = "";
        if(userLevel == 0){     // 货物补给员工
            userType = "depot";
        }
        if(userLevel == 1){     // 货物清点员
            userType = "inventory";
        }
        if(userLevel == 2){     // 管理员
            userType = "admin";
        }
        StringBuffer newUid = new StringBuffer(userMapper.selectNewUid(userType, userLevel));
        return newUid.append(StringUtil.getIdStr(1)).toString();
    }

    @Override
    public String selectPwd(User user) {
        String result = userMapper.findPwd(user);
        if(result != null && !"".equals(result)){
            return "您的密码是：" + StringUtil.decrypt(result);
        }
        return "请检查员工编号/姓名/职位/预留号码是否有误";
    }

    @Override
    public List<User> selectUserIdName(String type) {
        List<User> userList = userMapper.selectUserIdName(type);
        return userList;
    }

    // 根据用户（员工）编号查询
    @Override
    @Cacheable(cacheNames = {"user"}, key = "#uId", unless = "#result == null")
    public User selectUserById(String uId) {
        User user = userMapper.selectUserById(uId);
        return user;
    }

    // 查询多个用户（员工）信息
    @Override
    public List<User> selectUserByPage(String userName, String sortField, String sortOrder,
                                       Integer userLevel, Integer pageIndex, Integer pageSize) {
        userName = (userName == null) ? "" : userName.trim();
        pageIndex *= pageSize;
        List<User> userList = userMapper.selectUserByPage(userName, sortField, sortOrder, userLevel,pageIndex, pageSize);
        return userList;
    }

    @Override
    public List<User> selectUserBySheet() {
        return userMapper.selectUserBySheet();
    }

    @Override
    public int findLength(String userName, Integer userLevel) {
        userName = (userName == null) ? "" : userName.trim();
        int total = userMapper.findLength(userName, userLevel);
        return total;
    }

}
