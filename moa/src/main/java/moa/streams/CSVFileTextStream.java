package moa.streams;

import com.github.javacliparser.FileOption;
import com.github.javacliparser.FlagOption;
import com.github.javacliparser.IntOption;
import com.opencsv.CSVReader;
import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import moa.capabilities.CapabilitiesHandler;
import moa.core.Example;
import moa.core.InputStreamProgressMonitor;
import moa.core.InstanceExample;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.streams.clustering.ClusterEvent;
import moa.streams.generators.cd.ConceptDriftGenerator;
import moa.tasks.TaskMonitor;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Stream reader of CSV text files.
 *
 * @author Fabrício Ceschin (fjoceschin@inf.ufpr.br)
 * @version $Revision: $
 */

public class CSVFileTextStream extends AbstractOptionHandler implements
        InstanceStream, ConceptDriftGenerator, CapabilitiesHandler {

    public FileOption csvFileOption = new FileOption("csvFile", 'f',
            "CSV file to load.", null, "csv", false);

    public IntOption classIndexOption = new IntOption(
            "classIndex",
            'c',
            "Class index of data. 0 for none or -1 for last attribute in file.",
            -1, -1, Integer.MAX_VALUE);

    public FlagOption hasHeaderOption = new FlagOption("hasHeader", 'h', "Check if CSV has a header");

    protected Instances instances;

    protected CSVReader fileReader;

    protected boolean hitEndOfFile;

    protected InstanceExample lastInstanceRead;

    protected int numInstancesRead;

    protected InputStreamProgressMonitor fileProgressMonitor;

    /*
    public CSVFileTextStream() {
    }

    public CSVFileTextStream(String csvFileName, int classIndex, boolean hasHeader) throws Exception {
        this.csvFileOption.setValue(csvFileName);
        this.classIndexOption.setValue(classIndex);
        this.hasHeaderOption.setValue(hasHeader);
        restart();
    }*/

    @Override
    public void getDescription(StringBuilder sb, int indent) throws Exception {

    }

    @Override
    protected void prepareForUseImpl(TaskMonitor monitor, ObjectRepository repository) throws Exception {

    }

    @Override
    public InstancesHeader getHeader() {
        return null;
    }

    @Override
    public long estimatedRemainingInstances() {
        return 0;
    }

    @Override
    public boolean hasMoreInstances() {
        return false;
    }

    @Override
    public Example<Instance> nextInstance() throws Exception {
        return null;
    }

    @Override
    public boolean isRestartable() {
        return false;
    }

    @Override
    public void restart() throws Exception {

    }

    @Override
    public ArrayList<ClusterEvent> getEventsList() {
        return null;
    }
}
