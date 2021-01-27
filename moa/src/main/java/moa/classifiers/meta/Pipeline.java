package moa.classifiers.meta;

import com.yahoo.labs.samoa.instances.Instance;
import moa.capabilities.CapabilitiesHandler;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.classifiers.core.driftdetection.ChangeDetector;
import moa.core.Measurement;
import moa.options.ClassOption;

public class Pipeline extends AbstractClassifier implements MultiClassClassifier,
        CapabilitiesHandler  {

    @Override
    public String getPurposeString() {
        return "Implementation of a pipeline that can process the stream with multiple filters before actually using" +
                "the base classifier.";
    }

    public ClassOption baseLearnerOption = new ClassOption("baseLearner", 'l',
            "Classifier to train.", Classifier.class, "bayes.NaiveBayes");

    protected Classifier classifier;

    @Override
    public void resetLearningImpl() {
        this.classifier = ((Classifier) getPreparedClassOption(this.baseLearnerOption)).copy();
        this.classifier.resetLearning();
    }

    @Override
    public void trainOnInstanceImpl(Instance inst) {
        this.classifier.trainOnInstance(inst);
    }

    @Override
    public double[] getVotesForInstance(Instance instance) {
        return(this.classifier.getVotesForInstance(instance));
    }

    @Override
    public boolean isRandomizable() {
        return false;
    }


    @Override
    public void getModelDescription(StringBuilder arg0, int arg1) {
    }

    @Override
    protected Measurement[] getModelMeasurementsImpl() {
        return this.classifier.getModelMeasurements();
    }

}
