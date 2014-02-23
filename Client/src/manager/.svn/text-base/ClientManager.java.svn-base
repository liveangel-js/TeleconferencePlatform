package manager;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClientManager {
	private static String username="moqifa";
	public static void setSystemLookAndFeel(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
        public static void setUserName(String name){
            username=name;
        }
        
        public static String getUserName(){
            return username;
        }

}
