package moa.classifiers;

import moa.core.Example;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.MultiLabelInstance;
import com.yahoo.labs.samoa.instances.Prediction;

public abstract class AbstractMultiLabelLearner extends AbstractClassifier implements MultiLabelLearner {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    abstract public void trainOnInstanceImpl(MultiLabelInstance instance) throws Exception;

    @Override
    public void trainOnInstanceImpl(Instance instance) throws Exception {
        trainOnInstanceImpl((MultiLabelInstance) instance);
    }

    @Override
    public Prediction getPredictionForInstance(Example<Instance> example) throws Exception {
        return getPredictionForInstance(example.getData());
    }

    @Override
    public Prediction getPredictionForInstance(Instance inst) throws Exception {
        return getPredictionForInstance((MultiLabelInstance) inst);
    }

    abstract public Prediction getPredictionForInstance(MultiLabelInstance inst) throws Exception;

    @Override
    public double[] getVotesForInstance(Instance inst) throws Exception {
        Prediction pred = getPredictionForInstance(inst);
        if (pred != null) {
            return pred.getVotes();
        } else {
            return new double[]{0}; //for compatibility with single target code
        }
    }

}
