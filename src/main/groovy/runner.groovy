import org.shizuku_take.UI.Processor.SimulateProcessor
import org.shizuku_take.UI.ProcessorTransformer.SimulateTransformer
import org.shizuku_take.UI.*

import static spark.Spark.*

/**
 * Created by Shizuku Taketatsu on 7/16/15.
 */

class MainClass
{
    static void main (String[] args)
    {
        HomePage homePage = new HomePage()
        port(8080)
        get("/", {req, res -> homePage})
        get("/simulate",
            {req, res ->
                SimulateProcessor.fromRequest(req).run()
            },
            new SimulateTransformer()
        )
        get("/exit", {req, res -> stop(); System.exit(0)})
        println("Sparks serves at port 8080")
    }
}