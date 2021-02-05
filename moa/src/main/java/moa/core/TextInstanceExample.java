package moa.core;

public class TextInstanceExample implements Example<weka.core.Instance> {

	public weka.core.Instance instance;

  	public TextInstanceExample(weka.core.Instance inst)
   	{                             
		this.instance = inst;    
  	}  

	@Override
	public weka.core.Instance getData() {
		return this.instance;
	}
	
	@Override
	public double weight() {
		return this.instance.weight();
	}

	@Override
	public void setWeight(double w) {
		this.instance.setWeight(w);
	}

	@Override
	public Example copy() {
		return new TextInstanceExample((weka.core.Instance) instance.copy());
	}

	@Override
  	public String toString() {
  	  return this.instance.toString();
	}

} 
