public class Code {
    public static void main(String[] args) {
        System.out.println("Hello");
        
        // data-types
        
        int a = 10; //(int)
        boolean b = true;
        boolean c = false;
        
        String d = "Java";
        float e = 2.5f;
        // java specific data types
        double f = 2.5; //also for decimal numbers
        char g = 'a'; //single quotes and only one letter

        //array is similar to list in python
        
        // what is an array? it allows you to save multiple data (same type) in a 
        //single variable
        
        //for example, let's create an int array
        // whole_numbers = [10,20,30]
        
        int[] wholeNumbers = {10,20,30}; //camelCase naming
        boolean[] values = {true, true, false};
        double[] decimalNumbers = {2.1, 5.9, 1.4};
        char[] character = {'a', 'b', 'c'};
        String[] words ={"Moose", "Pizza", "Dictionary"};
        float[] floatingPoints = {3.1f, 9.4f, 4.6f};
    }
}
