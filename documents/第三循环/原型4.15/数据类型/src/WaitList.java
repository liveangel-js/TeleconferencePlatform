import java.util.ArrayList;

public class WaitList {
	ArrayList<User> userList; 
	
	public User getFirst(){//得到笔序中第一个人
		return null;
	}
	public boolean removeFirst(){//第一个人得到画笔后去除;
		return false;
	}
	public boolean insertFirst(){ //插队
		return false;
	}
	public boolean removeUser(User u){//有人取消等待
		return false;
	}
	public boolean insertEnd(){//加入排队
		return false;
	}
}
