package test.hyb;

/**
 * Created by admin on 2017/12/11.
 */
public class UserServiceProxy implements UserService{
    private UserService userService;
    @Override
    public void saveUser() {
        this.openConn();
        userService.saveUser();
        this.closeConn();
    }

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    public void openConn(){
        System.out.println("1.打开连接 ...");
    }
    public void closeConn(){

        System.out.println("3.关闭连接....");
    }
}
