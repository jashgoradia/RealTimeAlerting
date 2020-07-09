import java.util.Arrays;

public class CLIAlerting {
    public static void main(String args[]) throws InputValidationException {
        StatusInput st_in = new StatusInput();
        Rules rules = new Rules();
        String status = st_in.get_status_message();
        st_in.input_parser(status);
        int server_id = st_in.get_server_id();
        Integer resource_status[] = st_in.get_resource_status();
        String violation = rules.rule_set(resource_status);
        System.out.println(rules.get_message(violation,server_id));
    }
}