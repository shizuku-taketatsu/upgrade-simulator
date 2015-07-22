package org.shizuku_take.Simulator

import groovy.transform.Immutable
import groovy.transform.ToString

/**
 * Created by Shizuku Taketatsu on 7/15/15.
 */
@Immutable
@ToString(includeNames = true)
class Data
{
    final int percentage = 0
    final int value = 1
    final boolean checkpoint = false
    final boolean unsure = false
}

class DataInstances
{
    static equipmentRefinementData =
    [
        [percentage: 90],
        [percentage: 75],
        [percentage: 60],
        [percentage: 40],
        [percentage: 35, value: 4, checkpoint: true ],
        [percentage: 25, value: 4 ],
        [percentage: 20, value: 4 ],
        [percentage: 15, value: 16, checkpoint: true ],
        [percentage: 10, value: 16, unsure: true ],
        [percentage: 5, value: 16, unsure: true]
    ] as Data[]

    static sealRefinementData =
    [
        [ percentage: 90],
        [ percentage: 70],
        [ percentage: 50],
        [ percentage: 30],
        [ percentage: 20, checkpoint: true ],
        [ percentage: 15],
        [ percentage: 13, unsure: true ],
        [ percentage: 10, unsure: true, checkpoint: true ],
        [ percentage: 8, unsure: true ],
        [ percentage: 5, unsure: true, checkpoint: true ]
    ] as Data[]

    static mountRefinementData =
    [
        [ percentage: 95],
        [ percentage: 90],
        [ percentage: 85],
        [ percentage: 75],
        [ percentage: 70],
        [ percentage: 65],
        [ percentage: 50],
        [ percentage: 30],
        [ percentage: 20],
        [ percentage: 95, unsure: true ],
    ] as Data[]

    static petEvolveData =
    [
        [ percentage: 90],
        [ percentage: 70],
        [ percentage: 60],
        [ percentage: 50],
        [ percentage: 40, unsure: true ],
        [ percentage: 30, checkpoint: true ],
        [ percentage: 25],
        [ percentage: 20, unsure: true ],
        [ percentage: 15, unsure: true ],
        [ percentage: 10, unsure: true ]
    ] as Data[]

    static Map<String, Data[]> dataMap =
            [
                    equipment: equipmentRefinementData,
                    seal: sealRefinementData,
                    mount: mountRefinementData,
                    pet: petEvolveData
            ]
}