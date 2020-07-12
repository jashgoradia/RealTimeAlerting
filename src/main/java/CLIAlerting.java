import java.util.Arrays;

public class CLIAlerting {
    public static void main(String args[]) throws InputValidationException {
        StatusInput st_in = new StatusInput();
        Rules rules = new Rules();

        //read and parse command line input
        String status = st_in.get_cli_input();
        st_in.input_parser(status);

        //get server_id and resource stats
        int server_id = st_in.get_server_id();
        Integer resource_status[] = st_in.get_resource_status();

        //verify against alerting rule set and show appropriate message
        String violation = rules.rule_set(resource_status);
        System.out.println(rules.get_message(violation,server_id));
    }
}