package null;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("count")
	private int count;

	@SerializedName("status")
	private boolean status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
		return result;
	}

	public void setCount(int count){
		this.count = count;
	}

	public int getCount(){
		return count;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResSubsItem{" +
			"result = '" + result + '\'' + 
			",count = '" + count + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}