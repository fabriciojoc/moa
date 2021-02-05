package moa.streams.filters;

import com.yahoo.labs.samoa.instances.Instance;
import com.yahoo.labs.samoa.instances.Instances;
import com.yahoo.labs.samoa.instances.InstancesHeader;
import com.yahoo.labs.samoa.instances.WekaToSamoaInstanceConverter;
import com.yahoo.labs.samoa.instances.SamoaToWekaInstanceConverter;
import moa.MOAObject;
import moa.capabilities.Capabilities;
import moa.capabilities.ImmutableCapabilities;
import moa.core.Example;
import moa.streams.ExampleStream;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class StringToWordVectorFilter extends AbstractStreamFilter {

    private StringToWordVector stw;

    @Override
    public void getDescription(StringBuilder sb, int indent) {
    }

    @Override
    public InstancesHeader getHeader() {
        return null;
    }

    @Override
    protected void restartImpl() {
        this.stw = new StringToWordVector();
    }

    public Instance filterInstance(weka.core.Instance i) {
        //SamoaToWekaInstanceConverter samoaToWeka = new SamoaToWekaInstanceConverter();
        //weka.core.Instance i = samoaToWeka.wekaInstance(inst);
        try {
            this.stw.setInputFormat(i.dataset());
            this.stw.input(i);
            this.stw.batchFinished();
        } catch (Exception e) {
            e.printStackTrace();
        }
        weka.core.Instance processed = this.stw.output();
        WekaToSamoaInstanceConverter wekaToSamoa = new WekaToSamoaInstanceConverter();
        return(wekaToSamoa.samoaInstance(processed));
    }
}
