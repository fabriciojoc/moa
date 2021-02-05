package moa.streams;

import com.github.javacliparser.FileOption;
import com.github.javacliparser.FlagOption;
import com.github.javacliparser.IntOption;
import com.opencsv.CSVReader;
import com.yahoo.labs.samoa.instances.*;
import moa.capabilities.CapabilitiesHandler;
import moa.core.Example;
import moa.core.InputStreamProgressMonitor;
import moa.core.InstanceExample;
import moa.core.ObjectRepository;
import moa.options.AbstractOptionHandler;
import moa.streams.clustering.ClusterEvent;
import moa.streams.generators.cd.ConceptDriftGenerator;
import moa.tasks.TaskMonitor;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stream reader of CSV text files.
 *
 * @author Fabrício Ceschin (fjoceschin@inf.ufpr.br)
 * @version $Revision: $
 */

public class SimpleTextCSVStream extends AbstractOptionHandler implements
        InstanceStream, ConceptDriftGenerator, CapabilitiesHandler {

    public FileOption csvFileOption = new FileOption("csvFile", 'f',
            "CSV file to load.", null, "csv", false);

    public IntOption classIndexOption = new IntOption(
            "classIndex",
            'c',
            "Class index of data. 0 for none or -1 for last attribute in file.",
            -1, -1, Integer.MAX_VALUE);

    public FlagOption hasHeaderOption = new FlagOption("hasHeader", 'h', "Check if CSV has a header");

    protected weka.core.Instances instances = null;

    protected weka.core.Instance lastInstance;

    protected weka.core.Instance header;

    protected CSVReader fileReader;

    protected boolean hasHeader;

    protected boolean firstRow = true;

    protected boolean hitEndOfFile;

    protected InstanceExample lastInstanceRead;

    protected int numInstancesRead;

    protected InputStreamProgressMonitor fileProgressMonitor;

    protected ArrayList<ClusterEvent> clusterEvents;

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
        restart();
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
        if (this.fileReader != null) {
            this.fileReader.close();
        }
        this.fileReader = new CSVReader(new FileReader(this.csvFileOption.getFile()));
        int classIndex = this.classIndexOption.getValue();
        this.hasHeader = this.hasHeaderOption.isSet();

        // get csv header
        if (this.firstRow && this.hasHeader) {
            System.out.println("FIRST ROW & HAS HEADER");
            ArrayList<weka.core.Attribute> atts = this.readAttributes();
            this.instances = new weka.core.Instances("data", atts, 1);
            this.header = instanceFromAttributes(atts, this.instances);
            this.firstRow = false;
        }
        if (this.instances == null) {
            System.out.println("INSTANCES NULL");
            ArrayList<weka.core.Attribute> atts = this.readAttributes();
            this.instances = new weka.core.Instances("data", atts, 1);
            this.lastInstance = instanceFromAttributes(atts, this.instances);
        }
        else {
            // get last instance
            this.lastInstance = this.readInstance(this.instances);
        }
        if (classIndex < 0) {
            this.instances.setClassIndex(this.instances.numAttributes() - 1);
        } else if (classIndex > 0) {
            this.instances.setClassIndex(this.classIndexOption.getValue() - 1);
        }

        this.lastInstance.setDataset(this.instances);
        this.numInstancesRead = 1;





            /*this.instances = new Instances(this.fileReader, 1, classIndex);
            if (classIndex < 0) {
                this.instances.setClassIndex(this.instances.numAttributes() - 1);
            } else if (this.classIndexOption.getValue() > 0) {
                this.instances.setClassIndex(this.classIndexOption.getValue() - 1);
            }
            this.numInstancesRead = 0;
            this.lastInstanceRead = null;
            this.hitEndOfFile = !readNextInstanceFromFile();
        } catch (IOException ioe) {
            throw new RuntimeException("SimpleTextCSVStream restart failed.", ioe);
        }
        this.clusterEvents = new ArrayList<ClusterEvent>();*/
    }


    public ArrayList<weka.core.Attribute> readAttributes() throws IOException {
        String[] line = this.fileReader.readNext();
        // initialize atts
        ArrayList<weka.core.Attribute> atts = new ArrayList<weka.core.Attribute>();
        // add to instance each att
        for (int i = 0; i < line.length; i++) {
            weka.core.Attribute att = new weka.core.Attribute(line[i], true);
            atts.add(att);
        }
        System.out.println(atts);
        return atts;
    }

    public weka.core.Instance instanceFromAttributes(ArrayList<weka.core.Attribute> atts, weka.core.Instances dataset) throws IOException {
        weka.core.DenseInstance inst = new weka.core.DenseInstance(atts.size());
        inst.setDataset(dataset);
        for (int i = 0; i < atts.size(); i++) {
            System.out.println(i);
            weka.core.Attribute att = atts.get(i);
            inst.setValue(i, att.name());
        }
        return inst;
    }

    public weka.core.Instance readInstance(weka.core.Instances dataset) throws IOException {
        String[] line = this.fileReader.readNext();
        // initialize instance
        weka.core.DenseInstance inst = new weka.core.DenseInstance(line.length);
        inst.setDataset(dataset);
        // add to instance each value
        for (int i = 0; i < line.length; i++) {
            inst.setValue(i, line[i].replace("[", "").replace("]", "").replace("'", "").replace("'", "")
                    .replace(",", ""));
        }
        return inst;
    }

    /*protected boolean readNextInstanceFromFile() {
        /*try {
            if (this.instances.readInstance(this.fileReader)) {
                this.lastInstanceRead = new InstanceExample(this.instances.instance(0));
                this.instances.delete(); // keep instances clean
                this.numInstancesRead++;
                return true;
            }
            if (this.fileReader != null) {
                this.fileReader.close();
                this.fileReader = null;
            }
            return false;
        } catch (IOException ioe) {
            throw new RuntimeException(
                    "SimpleTextCSVStream failed to read instance from stream.", ioe);
        }
    }*/

    @Override
    public ArrayList<ClusterEvent> getEventsList() {
        return null;
    }
}
