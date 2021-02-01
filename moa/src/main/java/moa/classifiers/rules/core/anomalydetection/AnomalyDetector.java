/*
 *    AnomalyDetector.java
 *    Copyright (C) 2017 University of Porto, Portugal
 *    @author J. Duarte, J. Gama
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *
 */

package moa.classifiers.rules.core.anomalydetection;
import moa.options.OptionHandler;

import com.yahoo.labs.samoa.instances.MultiLabelInstance;


/**
 *  Anomaly Detector interface to implement methods that detects change.
 *
 * @author João Duarte (joaomaiaduarte at gmail dot com)
 * @version $Revision: 1$
 */
public interface AnomalyDetector extends OptionHandler {


    /**
     * Adding an instance to the anomaly detector<br><br>
     *
     * @return true if anomaly is detected and false otherwise
     */
    public boolean updateAndCheckAnomalyDetection(MultiLabelInstance instance) throws Exception;

    public double getAnomalyScore();
    
    @Override
    public AnomalyDetector copy();
}