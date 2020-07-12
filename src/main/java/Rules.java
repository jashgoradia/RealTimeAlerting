public class Rules {

    /*
    * Resource consumption alerting rule set
    * */
    public String rule_set(Integer resource_status[]){
        String violation = "";
        int cpu = resource_status[0];
        int memory = resource_status[1];
        int disk = resource_status[2];
        violation = violation + ((cpu>85)?"1":"0");
        violation = violation + ((memory>75)?"1":"0");
        violation = violation + ((disk>60)?"1":"0");
        return violation;
    }

    /*
    * Alert message generation
    * */
    public String get_message(String violation, int server_id){
        String msg = "";
        if(violation.equals("000")){
            msg = ("("+"No Alert, "+ server_id+")");
        }
        else{
            msg = "(Alert, "+server_id+",";
            msg = msg + ((violation.charAt(0)=='1')?" CPU_UTILIZATION VIOLATED,":"");
            msg = msg + ((violation.charAt(1)=='1')?" MEMORY_UTILIZATION VIOLATED,":"");
            msg = msg + ((violation.charAt(2)=='1')?" DISK_UTILIZATION VIOLATED,":"");
            msg = msg.substring(0,msg.length()-1);
            msg = msg+")";
        }
        return msg;
    }
}
