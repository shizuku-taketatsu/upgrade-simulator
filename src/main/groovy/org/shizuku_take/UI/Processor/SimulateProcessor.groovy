package org.shizuku_take.UI.Processor

import groovy.transform.ToString
import org.shizuku_take.Simulator.DataInstances
import org.shizuku_take.Simulator.SimulationResult
import org.shizuku_take.Simulator.Simulator
import org.shizuku_take.Simulator.DefaultParameter
import spark.Request

/**
 * Created by Shizuku Taketatsu on 7/17/15.
 */
@ToString
class SimulateProcessor
{
    int startLevel
    int endLevel
    int trialLimit
    int numberOfRuns
    String refinementType
    Exception exception
    SimulationResult[] simulationResults

    SimulateProcessor()
    {
        DefaultParameter.setDefaultParameter(this)
        exception = null
        simulationResults = null
    }

    static SimulateProcessor fromRequest(Request req)
    {
        SimulateProcessor sp = new SimulateProcessor();
        try
        {
            sp.startLevel = Integer.parseInt(req.queryParams("startLevel"))
            sp.endLevel = Integer.parseInt(req.queryParams("endLevel"))
            sp.trialLimit = Integer.parseInt(req.queryParams("trialLimit"))
            sp.numberOfRuns = Integer.parseInt(req.queryParams("numberOfRuns"))
            sp.refinementType = req.queryParams("refinementType")

            if (sp.refinementType == null) throw new NullPointerException();
        }
        catch(ex)
        {
            sp.exception = ex
        }
        return sp
    }

    boolean getRuned()
    {
        return simulationResults != null
    }

    SimulateProcessor run()
    {
        if (this.exception == null)
        {
            Simulator s = new Simulator()
            simulationResults =
                    s.runBatchSimulation(startLevel, endLevel, DataInstances.dataMap[refinementType],
                            numberOfRuns, trialLimit)
        }
        return this
    }
}