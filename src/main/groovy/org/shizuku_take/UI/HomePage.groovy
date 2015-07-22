package org.shizuku_take.UI

import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.shizuku_take.UI.Parts.*
import org.shizuku_take.Simulator.DataInstances

/**
 * Created by Shizuku Taketatsu on 7/17/15.
 */
class HomePage
{
    String cache;

    HomePage()
    {
        cache = null;
    }

    @Override
    String toString()
    {
        if (cache == null)
        {
            StreamingMarkupBuilder builder = new StreamingMarkupBuilder()
            InputForm inputForm = new InputForm()
            def b = builder.bind {doc ->
                html {
                    body {
                        // If something's worng in the following line,
                        // an exception stats "org.shizuku_take.UI.HomePage.html" cannot handle parameter ...
                        // will be shown
                        inputForm.build(doc, DataInstances.dataMap.keySet().toArray() as String[])
                    }
                }
            }
            cache = XmlUtil.serialize(b)
        }
        return cache
    }
}
