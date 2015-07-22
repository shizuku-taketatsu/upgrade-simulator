package org.shizuku_take.Simulator

import spock.lang.Specification

import java.security.InvalidParameterException
/**
*  Created by Shizuku Taketatsu on 7/16/15.
*/
class SimulatorTest extends Specification
{
    Data[] data =
            [
                    [percentage: 90, value: 1],
                    [percentage: 80, value: 2],
                    [percentage: 70, value: 3, checkpoint: true],
                    [percentage: 60, value: 4],
                    [percentage: 50, value: 5]
            ] as Data[];

    void setup()
    {
    }

    def "Run Batch Simulation (1, pass)"()
    {
        setup:
        def simulator = new Simulator({-> 0} as RandomProvider);

        when:
        SimulationResult[] results = simulator.runBatchSimulation(0, 5, data, 1)

        then:
        def elem =
                [numberOfRefinementTry: 5, itemValueConsumed: 15, totalTrialRunCount: 1,
                 succeededCount: 1] as SimulationResult

        def expected = [elem]
        assert results == expected
    }

    def "Run Batch Simulation (10, allPass)"()
    {
        setup:
        def simulator = new Simulator({-> 0} as RandomProvider);

        when:
        SimulationResult[] results = simulator.runBatchSimulation(0, 5, data, 10)

        then:
        def elem =
                [numberOfRefinementTry: 5, itemValueConsumed: 15, totalTrialRunCount: 1,
                 succeededCount: 1] as SimulationResult

        def expected = []
        10.times {expected << elem}
        assert results == expected
    }

    def "Run Single Simulation (pass)"()
    {
        setup:
        def simulator = new Simulator({-> 0} as RandomProvider);

        when:
        SimulationResult result = simulator.runSingleSimulation(0, 5, data)

        then:
        assert result ==
                [numberOfRefinementTry: 5, itemValueConsumed: 15, totalTrialRunCount: 1,
                 succeededCount: 1] as SimulationResult
    }

    def "Run single simulation (always fail)"()
    {
        setup:
        def simulator = new Simulator({-> 99} as RandomProvider)

        when:
        SimulationResult result = simulator.runSingleSimulation(0, 5, data, 10)

        then:
        assert result ==
                [numberOfRefinementTry: 10, itemValueConsumed: 10, totalTrialRunCount: 1,
                succeededCount: 0] as SimulationResult
    }

    def "Run Single Simulation (checkpoint)"()
    {
        setup:
        def simulator = new Simulator({-> 79} as RandomProvider)

        when:
        SimulationResult result = simulator.runSingleSimulation(0, 5, data, 10)

        then:
        assert result ==
                [numberOfRefinementTry: 10, itemValueConsumed: 27, totalTrialRunCount: 1,
                 succeededCount: 0] as SimulationResult
    }

    def "Run single simulation (bad arg)"()
    {
        setup:
        def simulator = new Simulator()

        when:
        simulator.runSingleSimulation(8, 15, data);

        then:
        thrown(InvalidParameterException)
    }

    def "Run single simulation (no need)"()
    {
        setup:
        def simulator = new Simulator()

        when:
        def result = simulator.runSingleSimulation(2, 1, data);

        then:
        assert result == null
    }
}
