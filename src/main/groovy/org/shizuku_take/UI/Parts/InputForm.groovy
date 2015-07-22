package org.shizuku_take.UI.Parts

import groovy.transform.ToString
import org.shizuku_take.Simulator.DefaultParameter

/**
 * Created by Shizuku Taketatsu on 7/17/15.
 */
@ToString
class InputForm
{
    int startLevel
    int endLevel
    int trialLimit
    int numberOfRuns
    String refinementType

    InputForm()
    {
        DefaultParameter.setDefaultParameter(this)
    }

    void build(doc, String[] refinementTypes)
    {
        doc.form (method: 'get', action: './simulate') {
            span "Refinement type"
            select (name: "refinementType"){
                refinementTypes.each
                        {
                            item ->
                                if (item == this.refinementType)
                                    option value: item, selected: 'true', item
                                else
                                    option value: item, item
                        }
            }
            br ""

            span "Start level"
            input type: 'text', name: 'startLevel', value: "${startLevel.toString()}"
            br ""

            span "Target level"
            input type: 'text', name: 'endLevel', value: "${endLevel.toString()}"
            br ""

            span "Trial limit for each run"
            input (type: 'text', name: 'trialLimit', value: "${trialLimit.toString()}")
            br ""

            span "Number of simulations"
            input (type: "text", name: "numberOfRuns", value: "${numberOfRuns.toString()}")
            br ""

            input (type: 'submit', value:'Simulate')
            br ""
        }
    }
}
