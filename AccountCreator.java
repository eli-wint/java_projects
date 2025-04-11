// Useless until I find a use... (potential for making saveable accounts?)
public class AccountCreator {
    private static String username;
    private static String password;
    private static int accountID;

    public AccountCreator(String user, String pword) {
        username = user;
        password = pword;
    }
    public String getAccountUser() {
        return username;
    }
    public String getAccountPassword() {
        return password;
    }
    public int getAccountID() {
        return accountID;
    }
    public static int makeAccountID() {
        int count = 0;
        for (Object el: AccountCreatorPanel.accountNames) {if (el != null) count++;}
        return count;
    }
}
