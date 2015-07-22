package org.shizuku_take.UI.ProcessorTransformer

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.shizuku_take.Simulator.AverageSimulationResult
import org.shizuku_take.Simulator.DataInstances
import org.shizuku_take.Simulator.SimulationResult
import org.shizuku_take.Simulator.SimulationResultCalculationHelper
import org.shizuku_take.UI.Parts.InputForm
import org.shizuku_take.UI.Processor.SimulateProcessor
import spark.ResponseTransformer

/**
 * Created by Shizuku Taketatsu on 7/17/15.
 */
class SimulateTransformer implements ResponseTransformer
{
    String renderSimulateProcessor(SimulateProcessor sp)
    {
        assert sp != null
        assert sp.runed
        SimulationResult[] results = sp.simulationResults;
        SimulationResult[] successResults = results.findAll {it.succeededCount != 0}
        SimulationResult maxEffort = successResults.max { it.itemValueConsumed }
        SimulationResult minEffort = successResults.min { it.itemValueConsumed }
        AverageSimulationResult average = SimulationResultCalculationHelper.calculateAverage(successResults)

        StreamingMarkupBuilder builder = new StreamingMarkupBuilder()
        InputForm inputForm = new InputForm()
        inputForm.refinementType = sp.refinementType
        inputForm.trialLimit = sp.trialLimit
        inputForm.numberOfRuns = sp.numberOfRuns
        inputForm.startLevel = sp.startLevel
        inputForm.endLevel = sp.endLevel

        def r = builder.bind() {doc ->
            html {
                body {
                    println inputForm
                    // show the input form
                    inputForm.build(doc, DataInstances.dataMap.keySet() as String[])
                    hr ""
                    // show the results
                    if (maxEffort == null)
                    {
                        assert minEffort == null
                        p {
                            mkp.yield "All fail :("
                            br ""
                            mkp.yield "Maybe try to relax trialLimit parameter ?"
                        }
                    }
                    else
                    {
                        // max count
                        p "Max cost: ${maxEffort.itemValueConsumed}"
                        // min cost
                        p "Min cost: ${minEffort.itemValueConsumed}"
                    }
                    // average cost
                    p "Average cost: ${average.itemValueConsumed} (in ${average.totalTrialRunCount} trials)"
                }
            }
        }
        return XmlUtil.serialize(r)
    }

    String renderErrorPage(Exception e)
    {
        StreamingMarkupBuilder builder = new StreamingMarkupBuilder()
        def r = builder.bind {
            html {
                body {
                    p "ERROR: while processing input parameters, we get error ${e.class.name}"
                    p e.message.toString().replace('\n', '<br/>')
                    p {
                        a href: "/", "back"
                    }
                }
            }
        }
        return XmlUtil.serialize(r)
    }

    @Override
    String render(Object model) throws Exception
    {
        if (model in SimulateProcessor)
        {
            SimulateProcessor sp = model
            if (sp.runed)
                renderSimulateProcessor(model)
            else
                renderErrorPage(sp.exception)
        }
        else
        {
            return "<p>INTERNAL ERROR: render type mismatch (${model.getClass()} vs ${SimulateProcessor})</p>"
        }
    }
}
