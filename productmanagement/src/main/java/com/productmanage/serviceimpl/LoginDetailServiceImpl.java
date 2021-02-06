package com.productmanage.serviceimpl;

import com.productmanage.bean.LoginDetail;
import com.productmanage.bean.User;
import com.productmanage.mapper.LoginDetailMapper;
import com.productmanage.mapper.UserMapper;
import com.productmanage.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoginDetailServiceImpl implements com.productmanage.service.LoginDetailService {

    @Autowired
    private LoginDetailMapper detailMapper;
    @Autowired
    private UserMapper userMapper;

    // 新增一条登录记录，用户登录时自动添加
    @Override
    public User insertLoginDetail(LoginDetail detail, User user) {
        // 判断用户输入的账号是否存在
        User user1 = userMapper.selectUserById(user.getUId());
        if(user1 == null){
            return null;
        }
        // 获取对应用户解密后的密码
        String pwd1 = StringUtil.decrypt(user1.getPassword());
        // 检验密码是否正确
        boolean checkPwd = user.getPassword().equals(pwd1);
        if(checkPwd) {
            // 判断该用户是否处于登录状态，防止重复登录
            if (detailMapper.isLogin(user.getUId()) != null) {
                detailMapper.updateLoginDetail(user.getUId(), null);
            }
            detailMapper.insertLoginDetail(detail);
            return user1;
        }
        return null;
    }

    // 删除登录记录，仅管理员可操作
    @Override
    @CacheEvict(value = "detail", key = "#detailId")
    public int deleteLoginDetailById(String detailId) {
        return detailMapper.deleteLoginDetailById(detailId);
    }

    @Override
    public int deleteLoginDetailByUid(String uId) {
        return detailMapper.deleteLoginDetailByUid(uId);
    }

    // 用户退出时，修改退出时间，并清除所有缓存
    @Override
    @CacheEvict(cacheNames = {"user","product","detail","item","notification","turnOver"}, allEntries = true)
    public String updateLoginDetail(String uId, String detailId) {
        User user = userMapper.selectUserById(uId);
        LoginDetail detail = detailMapper.selectLoginDetailById(detailId);
        if(user != null && detail != null) {
            detailMapper.updateLoginDetail(uId, detailId);
            return "退出成功";
        }
        return "退出失败";
    }

    @Override
    public LoginDetail isLogin(String uId) {
        return detailMapper.isLogin(uId);
    }

    // 获取一条最新的记录编号
    @Override
    public String selectNewDetailId() {
        StringBuffer detailId = new StringBuffer(detailMapper.selectNewDetailId());
        detailId.append(StringUtil.getIdStr(3));
        return detailId.toString();
    }

    // 获取单条登录记录
    @Override
    @Cacheable(cacheNames = {"detail"}, key = "#detailId", unless = "#result == null")
    public LoginDetail selectLoginDetailById(String detailId) {
        LoginDetail detail = detailMapper.selectLoginDetailById(detailId);
        return detail;
    }

    // 获取多条登录记录，可获取单个用户的所有登录记录，可根据登录时间进行排序
    @Override
    public List<LoginDetail> selctLoginDetailByPage(String uId, String sortField, String sortOrder, String date,
                                                    Integer pageIndex, Integer pageSize) {
        uId = (uId == null) ? "" : uId.trim();
        date = (date == null) ? "" : date.trim();
        pageIndex *= pageSize;
        List<LoginDetail> detailList = detailMapper.selctLoginDetailByPage(uId, sortField, sortOrder, date,pageIndex, pageSize);
        return detailList;
    }

    @Override
    public List<LoginDetail> selectLoginDetailBySheet() {
        return detailMapper.selectLoginDetailBySheet();
    }

    // 获取记录数量
    @Override
    public int findLength(String uId, String date) {
        uId = (uId == null) ? "" : uId.trim();
        date = (date == null) ? "" : date.trim();
        int total = detailMapper.findLength(uId, date);
        return total;
    }

    @Override
    public String selectExitLoginById(String detailId) {
        if(detailMapper.selectLoginDetailById(detailId).getExitLogin() == null){
            return "login";
        }
        return "logout";
    }

    @Override
    public String quitByAdmin(String detailId) {
        if(detailMapper.updateLoginDetail(null, detailId) > 0){
            return "操作成功！！";
        }
        return "操作失败！！";
    }
}
