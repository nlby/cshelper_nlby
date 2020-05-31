package service;

import dao.IUserDao;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.MDUtils;
import utils.MailUtils;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserDao userDao;

    /**
     * 添加用户
     * @param user
     * @return
     */
    public User register(User user) throws Exception{
        User user2 = userDao.findUserByName(user.getUsername());
        user.setSuccess();
        if(user2!=null){
            user.setFail("用户名已经存在");
            return user;
        }
        List<User> userList = userDao.findUserByMail(user.getMail());
        if(userList!=null && userList.size()>0){
            user.setFail("邮箱已经存在");
            return user;
        }
        user2=user;
        user2.setSuccess();
        try {
            //先在论坛用户表中添加记录
            userDao.addBBSUser(user.getMail(), user.getUsername(), user.getPassword());
            //将明文密码转换成MD5存储
            user2.setPassword(MDUtils.encodeMD2ToStr(user.getPassword()));
            //生成UUID作为id
            UUID uuid = UUID.randomUUID();
            String id = uuid+"";
            user2.setId(id);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        userDao.addUser(user2);
        int count = userDao.getCount();
        user2.setMessage("注册成功，您是本应用第" + count + "位用户");
        return user2;
    }

    /**
     * 用户登录检测
     * @param user
     * @return
     */
    public User checkLogin(User user) throws Exception{

        User user2 = userDao.findUserByName(user.getUsername());

        if (user2==null){
            user.setFail("用户不存在");
            return user;
        }else{
            try {
                if(user2.getPassword().equals(MDUtils.encodeMD2ToStr(user.getPassword()))){
                    user2.setSuccess();
                    return user2;
                }else{
                    user.setFail("密码错误");
                    return user;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        user.setFail("未知错误");
        return user;
    }


}
