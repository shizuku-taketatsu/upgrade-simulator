package org.shizuku_take.Simulator

import spock.lang.Specification

/**
 * Created by Shizuku Taketatsu on 7/16/15.
 */
class SimulationResultCalculationHelperTest extends Specification
{
    SimulationResult[] data

    void setup()
    {
        data =
                [
                        [numberOfRefinementTry: 300, itemValueConsumed: 100, totalTrialRunCount: 1, succeededCount: 1],
                        [numberOfRefinementTry: 200, itemValueConsumed: 200, totalTrialRunCount: 1, succeededCount: 1],
                        [numberOfRefinementTry: 1000, itemValueConsumed: 3000, totalTrialRunCount: 4, succeededCount: 3]
                ] as SimulationResult[];
    }

    def "sum calculation"()
    {
        when:
        SimulationResult result = SimulationResultCalculationHelper.calculateSum(data)

        then:
        assert result ==
                [numberOfRefinementTry: 1500, itemValueConsumed: 3300, totalTrialRunCount: 6
                , succeededCount: 5] as SimulationResult
    }

    def "average calculation"()
    {
        when:
        AverageSimulationResult result = SimulationResultCalculationHelper.calculateAverage(data)

        then:
        assert result ==
                [numberOfRefinementTry: 250f, itemValueConsumed: 550f, totalTrialRunCount: 6,
                averageSucceedCount: 5f / 6f] as AverageSimulationResult
    }
}
