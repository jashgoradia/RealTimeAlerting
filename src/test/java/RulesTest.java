import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RulesTest {

    Rules rules = new Rules();

    @org.junit.Test
    public void stage1_rule_set() {
        /*
        * Alerting rules implemented correctly
        */
        Assert.assertEquals("000",rules.rule_set(new Integer[]{80,70,55}));
        Assert.assertEquals("001",rules.rule_set(new Integer[]{80,70,61}));
        Assert.assertEquals("010",rules.rule_set(new Integer[]{80,76,55}));
        Assert.assertEquals("011",rules.rule_set(new Integer[]{80,76,61}));
        Assert.assertEquals("100",rules.rule_set(new Integer[]{86,70,55}));
        Assert.assertEquals("101",rules.rule_set(new Integer[]{86,70,61}));
        Assert.assertEquals("110",rules.rule_set(new Integer[]{86,76,55}));
        Assert.assertEquals("111",rules.rule_set(new Integer[]{86,76,61}));
    }

    @org.junit.Test
    public void stage2_get_message() {
        /*
        * Alert message is correctly generated
        */
        Assert.assertEquals("(No Alert, 500)",rules.get_message("000",500));
        Assert.assertEquals("(Alert, 500, DISK_UTILIZATION VIOLATED)",rules.get_message("001",500));
        Assert.assertEquals("(Alert, 500, MEMORY_UTILIZATION VIOLATED)",rules.get_message("010",500));
        Assert.assertEquals("(Alert, 500, MEMORY_UTILIZATION VIOLATED, DISK_UTILIZATION VIOLATED)",rules.get_message("011",500));
        Assert.assertEquals("(Alert, 500, CPU_UTILIZATION VIOLATED)",rules.get_message("100",500));
        Assert.assertEquals("(Alert, 500, CPU_UTILIZATION VIOLATED, DISK_UTILIZATION VIOLATED)",rules.get_message("101",500));
        Assert.assertEquals("(Alert, 500, CPU_UTILIZATION VIOLATED, MEMORY_UTILIZATION VIOLATED)",rules.get_message("110",500));
        Assert.assertEquals("(Alert, 500, CPU_UTILIZATION VIOLATED, MEMORY_UTILIZATION VIOLATED, DISK_UTILIZATION VIOLATED)",rules.get_message("111",500));
    }

}