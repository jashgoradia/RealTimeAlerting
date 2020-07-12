import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatusInputTest {
    StatusInput si = new StatusInput();

    @Test
    public void stage1_input_parser() throws InputValidationException {
        /*
        * Opening and closing brackets must be present
        */
        try { si.input_parser("500,50,50,40"); fail("Expected Exception Not Thrown");}catch (InputValidationException e){ }
        try { si.input_parser("500,50,50,40)"); fail("Expected Exception Not Thrown");}catch (InputValidationException e){ }
        try { si.input_parser("(500,50,50,40"); fail("Expected Exception Not Thrown");}catch (InputValidationException e){ }
        try { si.input_parser("(500,50,50,40)"); }catch (InputValidationException e){ fail("Unexpected Exception Thrown");}

        /*
        * White spaces between fields are permitted
        */
        try{ si.input_parser("(500, 60,  70, 80)");}catch (InputValidationException e){fail("Unexpected Exception Thrown");}
        try{ si.input_parser("(500,60   ,70 ,80  )");}catch (InputValidationException e){fail("Unexpected Exception Thrown");}
        try{ si.input_parser("(500,60,70,80)");}catch (InputValidationException e){fail("Unexpected Exception Thrown");}

        /*
        * Input must have 4 attributes (SERVER_ID, CPU_UTILIZATION, MEMORY UTILIZATION, DISK_UTILISATION)
        */
        try{ si.input_parser("(500, 60,  70, 80, 60)"); fail("Expected Exception Not Thrown");}catch (InputValidationException e){}
        try{ si.input_parser("(500, 60,  70 )"); fail("Expected Exception Not Thrown");}catch (InputValidationException e){}

        /*
        * Input must be numeric only
        */
        try{ si.input_parser("(test,60,70,80)");fail("Expected Exception Not Thrown");}catch (InputValidationException e){}
        try{ si.input_parser("(500,test,70,80)");fail("Expected Exception Not Thrown");}catch (InputValidationException e){}

        /*
        * Resource usage status must be < 100
        */
        try{ si.input_parser("(500,50,60,70)");}catch(InputValidationException e){ fail("Unexpected Exception Thrown");}
        try{ si.input_parser("(500,50,60,170)");fail("Expected Exception Not Thrown");}catch(InputValidationException e){}
        try{ si.input_parser("(500,50,160,70)");fail("Expected Exception Not Thrown");}catch(InputValidationException e){}
        try{ si.input_parser("(500,150,60,70)");fail("Expected Exception Not Thrown");}catch(InputValidationException e){}
    }

    @Test
    public void stage2_get_server_id() throws InputValidationException {
        si.input_parser("(500,40,60,50)");
        /*
        * Server_ID is extracted correctly from the status message
        */
        Assert.assertEquals(500,si.get_server_id());
    }

    @Test
    public void stage3_get_resource_status() throws InputValidationException {
        si.input_parser("(500,40,60,50)");
        /*
        * Resource usage status is extracted correctly from status message
        */
        Assert.assertArrayEquals((new Integer[]{40,60,50}),si.get_resource_status());
    }
}