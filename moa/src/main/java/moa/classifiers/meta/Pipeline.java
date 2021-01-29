package moa.classifiers.meta;

import com.github.javacliparser.ListOption;
import com.github.javacliparser.Option;
import com.yahoo.labs.samoa.instances.Instance;
import moa.capabilities.CapabilitiesHandler;
import moa.classifiers.AbstractClassifier;
import moa.classifiers.Classifier;
import moa.classifiers.MultiClassClassifier;
import moa.core.Measurement;
import moa.core.ObjectRepository;
import moa.options.ClassOption;
import moa.options.OptionHandler;
import moa.streams.filters.StreamFilter;
import moa.tasks.TaskMonitor;

import java.util.logging.Filter;

public class Pipeline extends AbstractClassifier implements MultiClassClassifier,
        CapabilitiesHandler  {

    @Override
    public String getPurposeString() {
        return "Implementation of a pipeline that can process the stream with multiple filters before actually using" +
                "the base classifier.";
    }

    public ListOption basefiltersOption = new ListOption("baseFilters", 'f',
            "The filters used by the pipeline before submitting an instance to the classifier.",
            new ClassOption("filter", ' ', "", StreamFilter.class,
                    "AddNoiseFilter"),
            new Option[] {
                    new ClassOption("", ' ', "", StreamFilter.class, "AddNoiseFilter"),
                    new ClassOption("", ' ', "", StreamFilter.class, "AddNoiseFilter"),
                    new ClassOption("", ' ', "", StreamFilter.class, "AddNoiseFilter")
            },
            ',');

    public ClassOption baseLearnerOption = new ClassOption("baseLearner", 'l',
            "Classifier to train.", Classifier.class, "bayes.NaiveBayes");

    protected Classifier classifier;

    protected StreamFilter[] filters;

    @Override
    public void prepareForUseImpl(TaskMonitor monitor,
                                  ObjectRepository repository) {
        Option[] filterOptions = this.basefiltersOption.getList();
        this.filters = new StreamFilter[filterOptions.length];
        for (int i = 0; i < filterOptions.length; i++) {
            System.out.println("filter " + filterOptions[i]);
            monitor.setCurrentActivity("Materializing filter " + (i + 1) + "...",
                    -1.0);
            this.filters[i] = (StreamFilter) ((ClassOption) filterOptions[i]).materializeObject(monitor, repository);
            ((OptionHandler) this.filters[i]).prepareForUse(monitor, repository);
            System.out.println(this.filters[i].getClass());
            if (monitor.taskShouldAbort()) {
                return;
            }
        }
        super.prepareForUseImpl(monitor, repository);
    }

    @Override
    public void resetLearningImpl() {
        this.classifier = ((Classifier) getPreparedClassOption(this.baseLearnerOption)).copy();
        this.classifier.resetLearning();
    }

    @Override
    public void trainOnInstanceImpl(Instance inst) {
        for (int i = 0; i < this.filters.length; i++) {
            inst = this.filters[i].filterInstance(inst);
        }
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
        return null;
        //return this.classifier.getModelMeasurements();
    }

}
