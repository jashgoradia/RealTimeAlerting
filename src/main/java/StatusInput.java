import jdk.internal.util.xml.impl.Input;

import javax.jnlp.IntegrationService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class StatusInput {
    Integer input_vals[];

    public StatusInput(){
    }

    /*
    * Read input from command line
    * */
    public String get_cli_input(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter status");
        String status = sc.nextLine();
        return status;
    }

    /*
    * Input validation
    * */
    public void input_parser(String status) throws InputValidationException{

        //Status message syntax - proper brackets
        int open_bracket = status.indexOf('(');
        if(open_bracket==-1){
            throw new InputValidationException("Invalid Input Syntax - Brackets Missing");
        }
        try {
            status = status.substring(open_bracket + 1, status.indexOf(')'));
        }catch (Exception e){
            throw new InputValidationException("Invalid Status Syntax - Brackets missing");
        }

        //Only numeric input is permitted
        try {
             input_vals = Arrays.stream(status.split(",")).map(String::trim).map(Integer::valueOf).toArray(Integer[]::new);
        }catch(Exception e){
            throw new InputValidationException("Invalid Input In Status - Non Numeric Input");
        }

        //Input must contain exactly 4 attributes - (SERVER_ID, CPU_UTILIZATION, MEMORY UTILIZATION, DISK_UTILISATION)
        if(input_vals.length!=4) {
            throw new InputValidationException("Invalid Status Length - Status Message Does Not Have Exactly 4 Attributes");
        }

        //Resource usage stats must not be greater than 100%
        for(int i = 1; i<input_vals.length; i++){
            if (input_vals[i]>100){
                throw new InputValidationException("Invalid Resource Usage - Resource Usage > 100");
            }
        }
    }

    /*
    * Extract server_id
    * */
    public int get_server_id(){
        return input_vals[0];
    }

    /*
    * Extract resource status
    * */
    public Integer[] get_resource_status(){
        return Arrays.stream(input_vals).skip(1).limit(3).toArray(Integer[]::new);
    }
}
