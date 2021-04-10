import java.util.ArrayList;

public class Main {

    public static void main(String args[]) {

        Controller controller = new Controller();

        
        //Test getUserName fucntion
        System.out.println(controller.getUserName(1));
        /*
        System.out.println("getUserName():");
        try {
            System.out.print("\tGetting first row... : ");
            controller.getUserName(1).equals("Quentin Audinet");
            System.out.println("Success !");

        }
        catch (Exception e){
            System.out.println("\tFailed: " + e.toString());
        }
         */
    }
}
