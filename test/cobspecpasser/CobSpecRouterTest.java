package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.*;

public class CobSpecRouterTest {

    @Test
    public void testAddRouteGetResponder() throws Exception {
        CobSpecRouter router = new CobSpecRouter();
        OptionsResponder responder = new OptionsResponder();
        router.addRoute("PUT /some-place", () -> responder);
        assertTrue(responder.equals(router.getResponder("PUT /some-place")));
    }

    @Test
    public void testGetResponderUnknownRouteName() throws Exception {
        CobSpecRouter router = new CobSpecRouter();
        Class classOfResponder = router.getResponder("GET /unknown").getClass();
        assertTrue(httpserver.NotFoundResponder.class.equals(classOfResponder));
    }

}
