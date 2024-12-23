
package Project_Sem2;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    
    public User(){
        userId = 0;
        userName = "";
        userPassword = "";
    }
     public User(int userId, String userName, String userPassword){
         this.userId = userId;
         this.userName = userName;
         this.userPassword = userPassword;
     }
     
     public int getId() {
         return this.userId;
     }
     
     public String getName() {
         return this.userName;
     }
     
     public String getPassword(){
         return this.userPassword;
     }
}

