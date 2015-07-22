package org.shizuku_take.Simulator

import groovy.transform.EqualsAndHashCode

import java.security.InvalidParameterException

/**
 * Created by Shizuku Taketatsu on 7/16/15.
 */
class Simulator
{
    RandomProvider randomProvider = null

    Simulator()
    {
        randomProvider = new DefaultRandomProvider()
    }

    Simulator(RandomProvider randomProvider_)
    {
        randomProvider = randomProvider_;
    }

    Simulator(Object randomProvider_)
    {
        if (randomProvider_.metaClass.respondsTo(randomProvider_, "next", [] as Object[]))
        {
            this.randomProvider = randomProvider_ as RandomProvider
        }
    }

    List<SimulationResult> runBatchSimulation(int startLevel, int endLevel, Data[] dataArray,
                                              int repeatTime = 1, int trialLimit = Integer.MAX_VALUE)
    {
        def results = [];
        repeatTime.times {results << runSingleSimulation(startLevel, endLevel, dataArray, trialLimit)}
        return results;
    }

    SimulationResult runSingleSimulation(int startLevel, int endLevel, Data[] dataArray,
                                             int trialLimit = Integer.MAX_VALUE)
    {
        // Argument check
        if (startLevel > dataArray.length || endLevel > dataArray.length
        || startLevel < 0 || endLevel < 0 )
            throw new InvalidParameterException()

        /* No need to calculate */
        if (startLevel >= endLevel)
            return null

        int currentLevel = startLevel
        SimulationResult result = new SimulationResult()
        result.totalTrialRunCount = 1
        while(currentLevel != endLevel && result.numberOfRefinementTry < trialLimit)
        {
            // consume the item
            result.itemValueConsumed += dataArray[currentLevel].value
            result.numberOfRefinementTry += 1

            // roll the dice
            int threshold = dataArray[currentLevel].percentage
            int dice = randomProvider.next()

            // leveling
            if (dice < threshold)
            {
                currentLevel += 1
            }
            else
            {
                if (!dataArray[currentLevel].checkpoint)
                {
                    currentLevel = currentLevel == 0 ? 0 : currentLevel - 1
                }
            }
        }

        if (currentLevel == endLevel) result.succeededCount = 1
        return result;
    }
}

class SimulationResultCalculationHelper
{
    static SimulationResult calculateSum(SimulationResult[] results)
    {
        SimulationResult r = new SimulationResult()
        r.numberOfRefinementTry = results.sum {it -> it.numberOfRefinementTry}
        r.itemValueConsumed = results.sum {it -> it.itemValueConsumed}
        r.totalTrialRunCount = results.sum {it ->it.totalTrialRunCount}
        r.succeededCount = results.sum{it -> it.succeededCount}
        return r
    }

    static AverageSimulationResult calculateAverage(SimulationResult[] results)
    {
        SimulationResult result = calculateSum(results)
        AverageSimulationResult avgResult = new AverageSimulationResult()
        avgResult.numberOfRefinementTry = result.numberOfRefinementTry / (float)result.totalTrialRunCount
        avgResult.itemValueConsumed = result.itemValueConsumed / (float)result.totalTrialRunCount
        avgResult.averageSucceedCount = result.succeededCount / (float)result.totalTrialRunCount
        avgResult.totalTrialRunCount = result.totalTrialRunCount
        return avgResult
    }
}

@EqualsAndHashCode
class SimulationResult
{
    int numberOfRefinementTry = 0
    int itemValueConsumed = 0
    int totalTrialRunCount = 0
    int succeededCount = 0
}

@EqualsAndHashCode
class AverageSimulationResult
{
    float numberOfRefinementTry = 0.0f
    float itemValueConsumed = 0.0f
    float averageSucceedCount = 0.0f
    int totalTrialRunCount = 0
}

interface RandomProvider
{
    int next()
}

class DefaultRandomProvider implements RandomProvider
{
    final Random random
    DefaultRandomProvider()
    {
        random = new Random();
    }

    int next()
    {
        random.nextInt(100)
    }
}

class DefaultParameter
{
    static void setDefaultParameter(Object o)
    {
        o.startLevel = 0
        o.endLevel = 10
        o.trialLimit = Integer.MAX_VALUE
        o.numberOfRuns = 10
        o.refinementType = "equipment"
    }
}