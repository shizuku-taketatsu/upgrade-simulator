import org.shizuku_take.UI.Processor.SimulateProcessor
import org.shizuku_take.UI.ProcessorTransformer.SimulateTransformer
import org.shizuku_take.UI.*

import java.awt.Desktop

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
        awaitInitialization();
        println("Sparks serves at port 8080")

        if(Desktop.isDesktopSupported())
        {
            println("Desktop is supported.. starting your browser")
            Desktop.getDesktop().browse(new URI("http://localhost:8080/"))
            println("Done")
        }
    }
}