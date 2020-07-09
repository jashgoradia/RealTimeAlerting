import jdk.internal.util.xml.impl.Input;

import javax.jnlp.IntegrationService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class StatusInput {
    Integer input_vals[];
    public StatusInput(){
    }
    public String get_status_message(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter status");
        String status = sc.nextLine();
        return status;
    }

    public void input_parser(String status) throws InputValidationException{
        try {
            status = status.substring(status.indexOf('(') + 1, status.lastIndexOf(')'));
        }catch (Exception e){
            throw new InputValidationException("brackets");
        }
        try {
             input_vals = Arrays.stream(status.split(",")).map(String::trim).map(Integer::valueOf).toArray(Integer[]::new);
        }catch(Exception e){
            throw new InputValidationException("string");
        }
        if(input_vals.length!=4) {
            throw new InputValidationException("length");
        }

        HashMap<Integer,Integer[]> server_details = new HashMap<Integer, Integer[]>();
        server_details.put(input_vals[0],Arrays.stream(input_vals).skip(1).limit(3).toArray(Integer[]::new));
    }

    public int get_server_id(){
        return input_vals[0];
    }

    public Integer[] get_resource_status(){
        return Arrays.stream(input_vals).skip(1).limit(3).toArray(Integer[]::new);
    }
}
