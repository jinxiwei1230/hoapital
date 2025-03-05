package hospitol;


import java.util.HashMap;
import java.util.Map;

public class UserOperate {

    public static Map<String, User> disapproveUsers; 
    
    public UserOperate() {

        this.disapproveUsers = new HashMap<>();
    }
    
 // 添加新的未审批用户
    public static void addDisapprove(User user) {
    	disapproveUsers.put(user.getIdNumber(), user);
    }
   
}
