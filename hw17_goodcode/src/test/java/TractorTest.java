import com.tractor.Orientation;
import com.tractor.Tractor;
import com.tractor.TractorInDitchException;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * @author Ben
 *
 */
public class TractorTest extends TestCase {

    @Test
    public void testShouldMoveForward(){
        Tractor tractor = new Tractor();
        tractor.moveNew("F");
        assertEquals(0, tractor.getPositionX());
        assertEquals(1, tractor.getPositionY());
    }

    @Test
    public void testShouldTurn(){
        Tractor tractor = new Tractor();
        tractor.moveNew("T");
        assertEquals(Orientation.EAST, tractor.getOrientation());
        tractor.moveNew("T");
        assertEquals(Orientation.SOUTH, tractor.getOrientation());
        tractor.moveNew("T");
        assertEquals(Orientation.WEST, tractor.getOrientation());
        tractor.moveNew("T");
        assertEquals(Orientation.NORTH, tractor.getOrientation());
    }

    @Test
    public void testShouldTurnAndMoveInTheRightDirection(){
        Tractor tractor = new Tractor();
        tractor.moveNew("T");
        tractor.moveNew("F");
        assertEquals(1, tractor.getPositionX());
        assertEquals(0, tractor.getPositionY());
        tractor.moveNew("T");
        tractor.moveNew("F");
        assertEquals(1, tractor.getPositionX());
        assertEquals(-1, tractor.getPositionY());
        tractor.moveNew("T");
        tractor.moveNew("F");
        assertEquals(0, tractor.getPositionX());
        assertEquals(-1, tractor.getPositionY());
        tractor.moveNew("T");
        tractor.moveNew("F");
        assertEquals(0, tractor.getPositionX());
        assertEquals(0, tractor.getPositionY());
    }

    @Test
    public void testShouldThrowExceptionIfFallsOffPlateau(){
        Tractor tractor = new Tractor();
        tractor.moveNew("F");
        tractor.moveNew("F");
        tractor.moveNew("F");
        tractor.moveNew("F");
        tractor.moveNew("F");
        try{
            tractor.moveNew("F");
            fail("Tractor was expected to fall off the plateau");
        }catch(TractorInDitchException expected){
        }
    }
}